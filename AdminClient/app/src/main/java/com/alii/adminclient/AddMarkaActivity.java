package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddMarkaActivity extends AppCompatActivity {

    private EditText name ;
    private Button create;
    private TextView newpost;
    private DatabaseReference root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marka);
        name = findViewById(R.id.name_makanew);
        create = findViewById(R.id.create_marka);
        newpost = findViewById(R.id.create_post_new_marka);
        root = FirebaseDatabase.getInstance().getReference().child("Marka");

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String marka = name.getText().toString().trim();
                if(TextUtils.isEmpty(marka)){
                    Toast.makeText(AddMarkaActivity.this, "اكتب اسم الماركة", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference checkcode = FirebaseDatabase.getInstance().getReference().child("Marka");
                    checkcode.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.child(marka).exists()){
                                HashMap<String , Object> hashMap = new HashMap<>();
                                hashMap.put("name",name.getText().toString().trim());
                                root.child(marka).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddMarkaActivity.this, "تمت اضافة الماركة بنجاح", Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(AddMarkaActivity.this,Cpanal.class);
                                 startActivity(intent);
                                 finish();
                                   }
                                });
                            }else {
                                Toast.makeText(AddMarkaActivity.this, "الماركة موجودة ...", Toast.LENGTH_SHORT).show();
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
