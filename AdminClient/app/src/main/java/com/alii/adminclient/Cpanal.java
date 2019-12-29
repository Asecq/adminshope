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

public class Cpanal extends AppCompatActivity {
    private TextView num_users , num_deps ,num_posts , num_orders;
    private DatabaseReference root;
    private DatabaseReference root_users , root_orders;
    private Button add_post , add_user,add_dep , addmake , add_admin , logout;
    private RelativeLayout usersliner , depsliner , postsliner, orders ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpanal);
        logout = findViewById(R.id.logout_deper2);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log = new Intent(Cpanal.this,LoginActivity.class);
                startActivity(log);
                finish();
            }
        });
        usersliner = findViewById(R.id.liner_cpanal2);
        depsliner = findViewById(R.id.liner_cpanal);
        postsliner = findViewById(R.id.liner_cpanal3);
        orders = findViewById(R.id.liner_cpanal4);
        add_admin = findViewById(R.id.add_admin_cpanal);

        add_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oadmin = new Intent(Cpanal.this,add_adminActivity.class);
                startActivity(oadmin);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orders = new Intent(Cpanal.this,myorders.class);
                startActivity(orders);
            }
        });
        usersliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent usser = new Intent(Cpanal.this,InfoUsers.class);
                startActivity(usser);
            }
        });
        depsliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent usser = new Intent(Cpanal.this,allDepsActivity.class);
                startActivity(usser);
            }
        });
        postsliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cpanal.this,PostsAll.class);
                startActivity(intent);
            }
        });
        add_post = (Button)findViewById(R.id.add_post_cpanal);
        add_dep = (Button)findViewById(R.id.add_dep_cpanal);
        add_user = (Button)findViewById(R.id.add_user_cpanal);
        addmake = findViewById(R.id.add_marka_cpanal);
        num_users = (TextView)findViewById(R.id.users_num);
        num_deps = (TextView)findViewById(R.id.dep_num);
        num_posts = findViewById(R.id.posts_num);
        num_orders = findViewById(R.id.orders_num);
        root  = FirebaseDatabase.getInstance().getReference();
        root.child("AllPost").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long xs = dataSnapshot.getChildrenCount();
                num_posts.setText(String.valueOf(xs));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root_users = FirebaseDatabase.getInstance().getReference().child("Users");
        root_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long xs = dataSnapshot.getChildrenCount();
                num_users.setText(String.valueOf(xs));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root.child("Protucts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long xy = dataSnapshot.getChildrenCount();
                num_deps.setText(String.valueOf(xy));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root_orders = FirebaseDatabase.getInstance().getReference().child("callsover");
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
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userintent = new Intent(Cpanal.this,Register.class);
                startActivity(userintent);
            }
        });
       add_dep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent depintent = new Intent(Cpanal.this,AddDeps.class);
                startActivity(depintent);
            }
        });
        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userintent = new Intent(Cpanal.this,AddPost.class);
                startActivity(userintent);
            }
        });
        addmake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userintent = new Intent(Cpanal.this,AddMarkaActivity.class);
                startActivity(userintent);
            }
        });
    }
}
