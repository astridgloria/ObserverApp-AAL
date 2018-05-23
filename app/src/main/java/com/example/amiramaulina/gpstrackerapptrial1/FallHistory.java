package com.example.amiramaulina.gpstrackerapptrial1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Random;

public class FallHistory extends AppCompatActivity{

    String userid, fallstateValue, fallstateTimestamp;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference ref;
    ArrayList<String> array9; //array untuk fallstate
    ArrayList<String> array10; //array untuk fallstateTimeStamp
    PointsGraphSeries<DataPoint> series ;
    int x=0;
    int i = 1;
    String[] xLabels ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_history);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        final GraphView graph = (GraphView)findViewById(R.id.graphFall);
        series = new PointsGraphSeries<>();
        final StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        graph.addSeries(series);
        Viewport vp = graph.getViewport();
        vp.setXAxisBoundsManual(true);
        vp.setMinX(0);
        vp.setMaxX(3); //yg ditunjukin max berapa
        vp.setYAxisBoundsManual(true);
        vp.setMinY(1);
        vp.setMaxY(200); //yg ditunjukin max berapa
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        //graph.getViewport().setScrollableY(true); // enables vertical scrolling
        //graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        //graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        array9 = new ArrayList<>(); //array untuk fallstate
        array10 = new ArrayList<>(); //array untuk fallstate timeStamp

        Intent intent = getIntent();
        if(intent!=null)
        {

            userid = intent.getStringExtra("userid");

        }


        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        ref.child("fallstate").child("nilaifallstate").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fallstateValue = dataSnapshot.child("fallstate").getValue(String.class);
                fallstateTimestamp = dataSnapshot.child("Timestamp").getValue(String.class);
                Log.i("fallstate value", "fallstate value " + fallstateValue);
                Log.i("fall timestamp", "fall timestamp " + fallstateTimestamp);
                array9.add(fallstateValue);
                array10.add(fallstateTimestamp);
                x = x + 1;
                DataPoint point = new DataPoint(x, 100);
                series.appendData(point, true, 1000);

                xLabels = new String[array10.size()];
                array10.toArray(xLabels);


                graph.getGridLabelRenderer().setHumanRounding(true);
                graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);

                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
                {
                    @Override
                    public String formatLabel(double value, boolean isValueX)
                    {
                        if (isValueX)
                        {
                            return xLabels[(int) value];
                        }
                        return super.formatLabel(value,isValueX);
                    }
                });

                Log.i("xlabels", "xlabels " + xLabels);

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }





    /*GraphView point_graph = (GraphView) findViewById(R.id.graph);

    PointsGraphSeries<DataPoint> point_series = new PointsGraphSeries<DataPoint>(new DataPoint[]{
            new DataPoint(0, 2000),
            new DataPoint(1, 2500),
            new DataPoint(2, 2700),
            new DataPoint(3, 3000),
            new DataPoint(4, 3500),
            new DataPoint(5, 2800),
            new DataPoint(6, 3700),
            new DataPoint(7, 3800),
            new DataPoint(8, 3500),
    });
    point_graph.addSeries(point_series);*/



}
