package com.myapps.toualbiamine.skydodger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

public class start extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }

    public void startGame(View view){

        Intent start = new Intent(getApplicationContext(), main.class);
        startActivity(start);

    }

    public void chooseVehicle(View view){

        Intent vehicle = new Intent(getApplicationContext(), vehicle.class);
        startActivity(vehicle);

    }


}
