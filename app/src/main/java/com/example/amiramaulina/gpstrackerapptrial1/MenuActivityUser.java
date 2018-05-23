package com.example.amiramaulina.gpstrackerapptrial1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MenuActivityUser extends AppCompatActivity {


    LatLng friendLatLng;
    String latitude,longitude,name,userid,prevdate;
    Toolbar toolbar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);



        Intent intent = getIntent();
        if(intent!=null)
        {
            latitude=intent.getStringExtra("latitude");
            longitude = intent.getStringExtra("longitude");
            name = intent.getStringExtra("name");
            userid = intent.getStringExtra("userid");
            prevdate = intent.getStringExtra("date");
        }

    }

    public void gotoLocation(View v){
        Intent myIntent = new Intent(MenuActivityUser.this,LiveMapActivity.class);
        myIntent.putExtra("latitude", latitude);
        myIntent.putExtra("longitude", longitude);
        myIntent.putExtra("name", name);
        myIntent.putExtra("userid", userid);
        myIntent.putExtra("date", prevdate);
        startActivity(myIntent);
    }

    //grafik hrstate
    public void gotoHRStateHistory(View v){
        Intent myIntent = new Intent(MenuActivityUser.this,HRStateHistory.class);
        myIntent.putExtra("userid", userid);
        startActivity(myIntent);
    }

    public void gotoFallHistory(View v){
        Intent myIntent = new Intent(MenuActivityUser.this,FallHistory.class);
        myIntent.putExtra("latitude", latitude);
        myIntent.putExtra("longitude", longitude);
        myIntent.putExtra("name", name);
        myIntent.putExtra("userid", userid);
        myIntent.putExtra("date", prevdate);
        startActivity(myIntent);
    }

    // grafik hrvalue
    public void gotoHRStatistics(View v){
        Intent myIntent = new Intent(MenuActivityUser.this,HRStatistics.class);
        myIntent.putExtra("userid", userid);
        startActivity(myIntent);
    }


}
