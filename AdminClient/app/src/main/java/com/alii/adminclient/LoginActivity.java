package com.alii.adminclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference root;
    private EditText pass , user;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        root = FirebaseDatabase.getInstance().getReference().child("Users");
        btn = findViewById(R.id.loging_btn);
        pass = findViewById(R.id.text_pass);
        user = findViewById(R.id.text_phone);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String p = pass.getText().toString().trim();
                final String u = user.getText().toString().trim();
                if(TextUtils.isEmpty(p)){
                    Toast.makeText(LoginActivity.this, "أكتب كلمة السر", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(u)){
                    Toast.makeText(LoginActivity.this, "أكتب رقم الحساب", Toast.LENGTH_SHORT).show();
                }else {
                    root.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(u).exists()){
                             if(dataSnapshot.child(u).child("pass").getValue().toString().equals(p)){

                                 if(dataSnapshot.child(u).child("type").getValue().toString().equals("admin")){
                                     Intent intent = new Intent(LoginActivity.this,Cpanal.class);
                                     startActivity(intent);
                                     finish();
                                 }else if(dataSnapshot.child(u).child("type").getValue().toString().equals("depr")){
                                     Intent intent = new Intent(LoginActivity.this,Cpanal2Activity.class);
                                     intent.putExtra("dep",dataSnapshot.child(u).child("dep").getValue().toString());
                                     startActivity(intent);
                                     finish();
                                 }else {
                                     Toast.makeText(LoginActivity.this, "هناك مشكلة في تسجيل الدخول", Toast.LENGTH_SHORT).show();
                                 }

                             }else {
                                 Toast.makeText(LoginActivity.this, "كلمة السر غير صحيحة", Toast.LENGTH_SHORT).show();
                             }
                            }else {
                                Toast.makeText(LoginActivity.this, "الحساب غير موجود", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }












            }
        });

    }
}
