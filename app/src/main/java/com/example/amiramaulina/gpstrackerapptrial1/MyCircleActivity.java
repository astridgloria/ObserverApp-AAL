package com.example.amiramaulina.gpstrackerapptrial1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class MyCircleActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;
    RecyclerView.Adapter recycleradapter;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference joinedReference;
    FirebaseAuth auth;
    FirebaseUser user;
    CreateUser createUser;
  //  String memberName,memberStatus,memberLat,memberLng;
    ArrayList<CreateUser> nameList;

  // AddCircle addCircle;


    DatabaseReference usersReference;

    ArrayList<String> circleuser_idList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("My Circle");


        nameList = new ArrayList<>();
        circleuser_idList = new ArrayList<>();
        joinedReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("JoinedCircles");
        usersReference = FirebaseDatabase.getInstance().getReference().child("Users");


        joinedReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameList.clear();

                if(dataSnapshot.exists())
                {
                    for(DataSnapshot dss: dataSnapshot.getChildren())
                    {

                        String memberUserid = dss.child("circlememberid").getValue(String.class);

                        usersReference.child(memberUserid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                createUser = dataSnapshot.getValue(CreateUser.class);
                                //  Toast.makeText(getApplicationContext(),createUser.name,Toast.LENGTH_SHORT).show();
                                nameList.add(createUser);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                    Toast.makeText(getApplicationContext(),"Showing joined circles",Toast.LENGTH_SHORT).show();
                    recycleradapter = new MembersAdapter(nameList,getApplicationContext());
                    recyclerView.setAdapter(recycleradapter);
                    recycleradapter.notifyDataSetChanged();


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You have not joined any circle yet!",Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(null);
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    public void refresh(View v)
    {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}
