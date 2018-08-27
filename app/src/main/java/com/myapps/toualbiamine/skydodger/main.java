package com.myapps.toualbiamine.skydodger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class main extends Activity {

    private TextView scoreLabel;
    private TextView startLabel;
    public static ImageView plane; //PLANE STANDS HERE FOR THE VEHICLE USED.
    private ImageView cloud;
    private ImageView tallSkyscraper;
    private ImageView shortSkyscraper;
    private ImageView airbus;
    private TextView finalScoreLabel;
    private TextView topScoreLabel;
    private Button playAgain;
    private TextView gameOver;
    private Button vehicle;
    private Button pause;
    private ImageView pauseImg;
    private ImageView realCloud;

    public static int score = 0;
    public static int topScore = 1;

    private int screenHeight;
    private int screenWidth;
    private int frameHeight;
    private int planeSize = 250;

    private int planeY;
    private int realCloudX;
    private int realCloudY;
    private int cloudX;
    private int cloudY;
    private int tallSkyX;
    private int shortSkyX;
    private int airbusX;
    private int airbusY;

    private int realCloudSpeed = 10;
    private int cloudSpeed = 12;
    private int planeSpeed = 13;
    private int airbusSpeed = 15;
    private int skyscraperSpeed = 8;

    private Timer timer = new Timer();
    private Handler handler = new Handler();
    public static SharedPreferences settings;
    private SoundPlayer sound;

    private static boolean action_flg = false;
    private static boolean start_flg = true;
    private static boolean pause_flg = false;
    private static boolean over = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get screen size.
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;


        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        cloud = (ImageView) findViewById(R.id.cloud);

        if(screenHeight > 1184) {

            tallSkyscraper = (ImageView) findViewById(R.id.tallSkyscraper);
            shortSkyscraper = (ImageView) findViewById(R.id.shortSkyscraper);
            findViewById(R.id.smallScreenSmallSky).setVisibility(View.INVISIBLE);
            findViewById(R.id.smallScreenTallSky).setVisibility(View.INVISIBLE);

        }
        else{

            tallSkyscraper = (ImageView) findViewById(R.id.smallScreenTallSky);
            shortSkyscraper = (ImageView) findViewById(R.id.smallScreenSmallSky);
            findViewById(R.id.tallSkyscraper).setVisibility(View.INVISIBLE);
            findViewById(R.id.shortSkyscraper).setVisibility(View.INVISIBLE);

        }

        airbus = (ImageView) findViewById(R.id.airbus);
        finalScoreLabel = (TextView) findViewById(R.id.finalScoreLabel);
        topScoreLabel = (TextView) findViewById(R.id.topScoreLabel);
        gameOver = (TextView) findViewById(R.id.gameOver);
        playAgain = (Button) findViewById(R.id.playAgain);
        vehicle = (Button) findViewById(R.id.vehicle);
        pause = (Button) findViewById(R.id.pause);
        pauseImg = (ImageView) findViewById(R.id.pauseImg);
        realCloud = (ImageView) findViewById(R.id.realCloud);


        //Vehicle => plane.
        if(com.myapps.toualbiamine.skydodger.vehicle.vehicleChoice == 1){

            plane = (ImageView) findViewById(R.id.plane);
            findViewById(R.id.helicop).setVisibility(View.INVISIBLE);
            findViewById(R.id.ironman).setVisibility(View.INVISIBLE);

        }


        //Vehicle => helicopter.
        if(com.myapps.toualbiamine.skydodger.vehicle.vehicleChoice == 2){

            plane = (ImageView) findViewById(R.id.helicop);
            findViewById(R.id.plane).setVisibility(View.INVISIBLE);
            findViewById(R.id.ironman).setVisibility(View.INVISIBLE);

        }

        //Vehicle => ironman.
        if(com.myapps.toualbiamine.skydodger.vehicle.vehicleChoice == 3){

            plane = (ImageView) findViewById(R.id.ironman);
            findViewById(R.id.plane).setVisibility(View.INVISIBLE);
            findViewById(R.id.helicop).setVisibility(View.INVISIBLE);

        }

        //Set invisible nodes.
        finalScoreLabel.setVisibility(View.INVISIBLE);
        topScoreLabel.setVisibility(View.INVISIBLE);
        gameOver.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        vehicle.setVisibility(View.INVISIBLE);
        pauseImg.setVisibility(View.INVISIBLE);


//        //Get screen size.
//        WindowManager wm = getWindowManager();
//        Display disp   = wm.getDefaultDisplay();
//        Point size = new Point();
//        disp.getSize(size);
//        screenWidth = size.x;
//        screenHeight = size.y;

        //Optimize speeds for different devices
        //Nexus5X width: 1080 height: 1184
        //Speeds: plane = 13 airbus = 15 skyscraper = 8 monsgolfiere = 12 cloud = 10 // CHANGE IN THE VALUES MADE CUZ FLEMME
        planeSpeed = Math.round(screenHeight / 115F); //1184 / 90 = 13.55..
        airbusSpeed = Math.round(screenWidth / 78F); //1184 / 78 = 15.18..
        skyscraperSpeed = Math.round(screenWidth / 150F); //1184 / 150 = 7.89..
        cloudSpeed = Math.round(screenWidth / 99F); //1184 / 99 = 11.96..
        realCloudSpeed = Math.round(screenWidth / 120F); //1184 / 120 = 9.86..

        Log.v("SPEED_PLANE", planeSpeed+"");
        Log.v("SPEED_AIRBUS", airbusSpeed+"");
        Log.v("SPEED_SKYSCRAPER", skyscraperSpeed+"");
        Log.v("SPEED_MONSGOLFIERE", cloudSpeed+"");
        Log.v("SPEED_CLOUD", realCloudSpeed+"");

        Log.v("DIMENSIONS", screenHeight+"");


        //Set cloud, airbus, skyscrapers out of window.
        cloud.setX(10000);
        cloud.setY(10000);
        airbus.setX(10000);
        airbus.setY(10000);
        tallSkyscraper.setX(10000);
        shortSkyscraper.setX(10000);
        realCloud.setX(10000);
        realCloud.setY(10000);


        //Set initial score to 0.
        scoreLabel.setText("Distance: 0 m");

        //Starting coordinates of nodes.
        if(screenHeight <= 1200) {
            planeY = 300;
        }
        else{
            planeY = 700;
        }
        cloudX = screenWidth;
        cloudY = 150;
        airbusX = screenWidth;
        airbusY = 400;
        tallSkyX = screenWidth;
        shortSkyX = screenWidth + 400;
        realCloudY = 300;
        realCloudX = screenWidth + 100;

        //Score saving.
        settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        topScore = settings.getInt("Top Score", 1);

        //Sound.
        sound = new SoundPlayer(this);

    }

    public void move(){

        hitCheck();

        //Real Cloud movement.
        realCloudX -= realCloudSpeed;
        if(realCloudX < -300){
            realCloudX = screenWidth + 300;
            realCloudY = (int)(Math.random()*500);
        }
        realCloud.setX(realCloudX);
        realCloud.setY(realCloudY);

        //Balloon movement.
        cloudX -= cloudSpeed;
        if(cloudX < -300){
            cloudX = screenWidth + 200;
            cloudY = (int)(Math.random() * 400);
        }
        cloud.setX(cloudX);
        cloud.setY(cloudY);

        //Airbus movement.
        airbusX -= airbusSpeed;
        if(airbusX < -300){
            airbusX = screenWidth + 600;
            airbusY = (int)(Math.random() * 500 + 100);
        }
        airbus.setX(airbusX);
        airbus.setY(airbusY);

        //TallSkyScraper movement.
        tallSkyX -= skyscraperSpeed;
        if(tallSkyX < -300){
            tallSkyX = screenWidth + 300;
        }
        tallSkyscraper.setX(tallSkyX);

        //ShortSkyScraper movement.
        shortSkyX -= skyscraperSpeed;
        if(shortSkyX < - 100){
            shortSkyX = screenWidth + 100;
        }
        shortSkyscraper.setX(shortSkyX);

        //Plane movement.
        if(action_flg == true && over == false){ //Pressing

            planeY -= planeSpeed;

        }

        else if(action_flg == false && over == false) { //Releasing

            planeY += planeSpeed;

        }

        if(planeY <= 0){ //Upper boundary.

            planeY = 0;

        }

        if(planeY + planeSize - 130 > frameHeight){ //Lower boundary.

            planeY = frameHeight - planeSize + 130;

        }

        //Move plane up & down.
        plane.setY(planeY);


    }

    public void hitCheck() {

        //Centers.
        int airbusCenterX = airbusX + airbus.getWidth() / 2;
        int airbusCenterY = airbusY + airbus.getHeight() / 2;
        int cloudCenterX = cloudX + cloud.getWidth() / 2;
        int cloudCenterY = cloudY + cloud.getHeight() / 2;
        int tallCenterX = tallSkyX + tallSkyscraper.getWidth() / 2;
        int tallCenterY = (screenHeight - tallSkyscraper.getHeight()) + tallSkyscraper.getHeight() / 2;
        int shortCenterX = shortSkyX + shortSkyscraper.getWidth() / 2;
        int shortCenterY = (screenHeight - shortSkyscraper.getHeight()) + shortSkyscraper.getHeight() / 2;


        //Crash on floor
        if (planeY + planeSize >= screenHeight - 96) { //96px = size of android buttons

            //Stop timer.
            timer.cancel();
            over = true;
            Log.v("CRASH: ", "FLOOR");

            //Show invisible nodes.
            finalScoreLabel.setVisibility(View.VISIBLE);
            topScoreLabel.setVisibility(View.VISIBLE);
            gameOver.setVisibility(View.VISIBLE);
            playAgain.setVisibility(View.VISIBLE);
            vehicle.setVisibility(View.VISIBLE);

        }

        //Hit Airbus
        if(airbusCenterY >= planeY && airbusCenterY <= planeY + planeSize &&
                airbusCenterX <= planeSize && airbusCenterX >= 0){

            over = true;

            Log.v("CRASH: ", "AIRBUS");

        }

        //Hit Cloud
        if(cloudCenterY >= planeY && cloudCenterY<= planeY + planeSize &&
                cloudCenterX <= planeSize && cloudCenterX >= 0){

            over = true;

            Log.v("CRASH: ", "MONSGOLFIERE");

        }

        //Hit tallSkyScraper
        if (tallCenterY - tallSkyscraper.getHeight() / 2 <= planeY &&
                tallCenterX + tallSkyscraper.getWidth() / 2 - tallSkyscraper.getWidth() /  4
                        <= planeSize &&
                tallCenterX + tallSkyscraper.getWidth() >= 0){

            over = true;

            Log.v("CRASH: ", "TALL");

    }

        //Hit smallSkyScraper
        if (shortCenterY - shortSkyscraper.getHeight() / 2 <= planeY &&
                shortCenterX + shortSkyscraper.getWidth() / 2 - shortSkyscraper.getWidth() /  4
                        <= planeSize &&
                shortCenterX + shortSkyscraper.getWidth() >= 0){

            over = true;

            Log.v("CRASH: ", "SMALL");

        }

        if(over == true){ //GAME OVER => hide pause button.

            pause.setVisibility(View.GONE);
            sound.playOverSound();

            try { //Plane crashing.
                timer.schedule(new TimerTask() { //Thread.
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                planeY += 1;
                            }
                        });
                    }
                }, 0, 20);

            }
            catch(IllegalStateException e){
            }

            if(planeY >= screenHeight - 300) { //When plane hits the floor.
                //Stop timer.
                timer.cancel();

                //Show invisible nodes.
                finalScoreLabel.setVisibility(View.VISIBLE);
                topScoreLabel.setVisibility(View.VISIBLE);
                gameOver.setVisibility(View.VISIBLE);
                playAgain.setVisibility(View.VISIBLE);
                vehicle.setVisibility(View.VISIBLE);

            }

            if(score > topScore){

                Log.v("SCORE > TOP SCORE", "TRUE");

                topScore = score;

                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Top Score", score);
                editor.commit();

                Log.v("SCORE TOP :", topScore+"");

            }


            //Show score & top score.
            finalScoreLabel.setText("Distance: "+score+" m");
            finalScoreLabel.setTypeface(Typeface.defaultFromStyle((Typeface.BOLD)));
            topScoreLabel.setText("Distance max: "+topScore+" m");
            topScoreLabel.setTypeface(Typeface.defaultFromStyle((Typeface.BOLD)));

        }

        else{

            score++;
            scoreLabel.setText("Distance: "+score+" m");

        }

    }


    public boolean onTouchEvent(MotionEvent me) {

        int action = me.getAction();

        if (start_flg == true) { //Start timer when game starts on click.

            start_flg = false;

            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            startLabel.setVisibility(View.GONE); //Hide "TAP TO START"

                timer.schedule(new TimerTask() { //Thread.
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                move();
                            }
                        });
                    }
                }, 0, 20); //Changes position every 20 ms.
            }




         else if (start_flg == false) {

            if (action == MotionEvent.ACTION_DOWN) { //Pressing.

                action_flg = true;


            } else if (action == MotionEvent.ACTION_UP) { //Releasing.

                action_flg = false;


            }

        }

        return true;
    }

    public void playAgain(View view){ //Play Again button.

        Intent again = new Intent(getApplicationContext(), main.class);
        startActivity(again);
        start_flg = true;
        over = false;
        action_flg = false;
        pause_flg = false;
        score = 0;

    }

    public void chooseVehicle(View view){ //Vehicle button.

        Intent vehicle = new Intent(getApplicationContext(), vehicle.class);
        startActivity(vehicle);
        over = false;
        start_flg = true;
        action_flg = false;
        score = 0;

    }


    public void pause(View view){ //Pause button.

        if(pause_flg == false && over == false){ //Pauses the timer.

            pause_flg = true;
            pauseImg.setVisibility(View.VISIBLE);
            this.timer.cancel();
        }
        else if(pause_flg == true && over == false) { //Starts timer.

            pause_flg = false;
            pauseImg.setVisibility(View.GONE);
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() { //Thread.
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            move();
                        }
                    });
                }
            }, 0, 20);

        }




    }

    //Disable return button.
    public boolean dispatchKeyEvent (KeyEvent event){ //dispatchKeyEvent = 1ST METHOD CALLED BY SYSTEM.

        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(event.getKeyCode()){

                case KeyEvent.KEYCODE_BACK:
                    return true;

            }
        }

        return super.dispatchKeyEvent(event);

    }


}


