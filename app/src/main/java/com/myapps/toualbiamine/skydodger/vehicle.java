package com.myapps.toualbiamine.skydodger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class vehicle extends Activity {

    //marginTop of vehicle.xml of tableLayout was 75dp

    public static int vehicleChoice = 1;
    public ImageView helicopter;
    public ImageView plane;
    public ImageView ironman;
    private TextView planeText;
    private TextView helicoText;
    private TextView ironmText;
    private TextView requireHelico;
    private TextView requireIron;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vehicle);

        helicopter = (ImageView) findViewById(R.id.helicopter);
        plane = (ImageView) findViewById(R.id.plane);
        ironman = (ImageView) findViewById(R.id.ironman);

        helicoText = (TextView) findViewById(R.id.helicoText);
        planeText = (TextView) findViewById(R.id.planeText);
        ironmText = (TextView) findViewById(R.id.ironmText);

        requireHelico = (TextView) findViewById(R.id.requireHelico);
        requireHelico.setVisibility(View.INVISIBLE);

        requireIron = (TextView) findViewById(R.id.requireIron);
        requireIron.setVisibility(View.INVISIBLE);



        helicopter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(main.topScore >= 3000) {

                    vehicleChoice = 2;
                    System.out.println("VEHICLE CHOICE = " + vehicleChoice);
                    helicoText.setTextColor(Color.parseColor("#b9114e12"));
                    planeText.setTextColor(Color.BLACK);
                    ironmText.setTextColor(Color.BLACK);
                }
                else{
                    helicoText.setTextColor(Color.parseColor("#b20000"));
                    requireHelico.setVisibility(View.VISIBLE);

                }

            }
        });

        plane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vehicleChoice = 1;
                System.out.println("VEHICLE CHOICE = "+vehicleChoice);
                planeText.setTextColor(Color.parseColor("#b9114e12"));
                helicoText.setTextColor(Color.BLACK);
                ironmText.setTextColor(Color.BLACK);

            }
        });

        ironman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(main.topScore >= 6000) {

                    vehicleChoice = 3;
                    System.out.println("VEHICLE CHOICE = " + vehicleChoice);
                    ironmText.setTextColor(Color.parseColor("#b9114e12"));
                    planeText.setTextColor(Color.BLACK);
                    helicoText.setTextColor(Color.BLACK);
                }

                else{

                    ironmText.setTextColor(Color.parseColor("#b20000"));
                    requireIron.setVisibility(View.VISIBLE);

                }

            }
        });


    }

    public void back(View view){

        Intent start = new Intent(getApplicationContext(), start.class);
        startActivity(start);

    }

    public void play(View view){

        Intent play = new Intent(getApplicationContext(), main.class);
        startActivity(play);

    }

}
