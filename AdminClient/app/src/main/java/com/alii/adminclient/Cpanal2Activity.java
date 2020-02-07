package com.alii.adminclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cpanal2Activity extends AppCompatActivity {
    private DatabaseReference root;
    private TextView num_posts , num_orders;
    private DatabaseReference root_users , root_orders ;
    private RelativeLayout  postsliner, orders ;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpanal2);
        final String dep = getIntent().getExtras().getString("dep");
        logout = findViewById(R.id.logout_deper);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log = new Intent(Cpanal2Activity.this,LoginActivity.class);
                startActivity(log);
                finish();
            }
        });
        num_posts = findViewById(R.id.posts_num1);
        num_orders = findViewById(R.id.orders_num1);
        orders = findViewById(R.id.liner_cpp5);
        postsliner = findViewById(R.id.liner_cpanal10);

        postsliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orders = new Intent(Cpanal2Activity.this,postssActivity.class);
                orders.putExtra("dep",dep);
                startActivity(orders);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent ge = new Intent(Cpanal2Activity.this,ordersdepActivity.class);
              ge.putExtra("dep",getIntent().getExtras().getString("dep"));
              startActivity(ge);
            }
        });
        root  = FirebaseDatabase.getInstance().getReference();
        root.child("Protucts").child(dep).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long xs = dataSnapshot.getChildrenCount();
                num_posts.setText(String.valueOf(xs));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        root_orders = FirebaseDatabase.getInstance().getReference().child("DeptF").child(dep);
        root_orders.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long xs = dataSnapshot.getChildrenCount();
                num_orders.setText(String.valueOf(xs));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
