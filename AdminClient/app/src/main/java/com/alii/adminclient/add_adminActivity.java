package com.alii.adminclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class add_adminActivity extends AppCompatActivity {

    private DatabaseReference root;
    private EditText input_user , input_pass;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
       root = FirebaseDatabase.getInstance().getReference().child("Admins");
        btn = findViewById(R.id.creat_adminnew);
        input_pass = findViewById(R.id.admin_pass_new);
        input_user = findViewById(R.id.admin_name_new);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(input_user.getText().toString())){
                    Toast.makeText(add_adminActivity.this, "أكتب رقم تسجيل الدخول", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(input_pass.getText().toString())){
                    Toast.makeText(add_adminActivity.this, "أكتب كلمة السر", Toast.LENGTH_SHORT).show();
                }else {
                    root.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(input_user.getText().toString().trim()).exists()){
                                Toast.makeText(add_adminActivity.this, "الحساب موجود ...!", Toast.LENGTH_SHORT).show();
                            }else {
                                HashMap<String , Object> newacc = new HashMap<>();

                                newacc.put("phone",input_user.getText().toString().trim());
                                newacc.put("pass",input_pass.getText().toString().trim());
                                RadioButton  radio1 = (RadioButton) findViewById(R.id.Aradio_1);
                                RadioButton  radio2 = (RadioButton) findViewById(R.id.Aradio_2);
                                if(radio1.isChecked()){
                                    newacc.put("type","1");
                                }else if(radio2.isChecked()){
                                    newacc.put("type","2");
                                }
                                root.child(input_user.getText().toString().trim()).updateChildren(newacc).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(add_adminActivity.this, "تم أنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(add_adminActivity.this,Cpanal.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                });
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
