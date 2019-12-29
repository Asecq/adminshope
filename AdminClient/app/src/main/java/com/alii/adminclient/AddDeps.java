package com.alii.adminclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddDeps extends AppCompatActivity {
    private TextView create_new;
    private Button create_btn;
    private EditText create_text;
    private DatabaseReference root;
    public String dep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deps);
        root = FirebaseDatabase.getInstance().getReference().child("Protucts");
        create_btn = (Button)findViewById(R.id.create_dep);
        create_new = (TextView)findViewById(R.id.create_post_new);
        create_text = (EditText)findViewById(R.id.department_name);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dep = create_text.getText().toString().trim();
                if(!TextUtils.isEmpty(dep)) {
                    root.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child(dep).exists()) {
                                Toast.makeText(AddDeps.this, "القسم موجود", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference troot = FirebaseDatabase.getInstance().getReference();
                                troot.child("Protucts").child(dep).child("test").setValue("test");
                                Toast.makeText(AddDeps.this, "تمت اضافة القسم بنجاح", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AddDeps.this,Cpanal.class);
                                startActivity(intent);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else {
                    Toast.makeText(AddDeps.this, "اكتب اسم القسم الجديد", Toast.LENGTH_SHORT).show();
                }
            }

        });
        create_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent creat = new Intent(AddDeps.this,SelectDeps.class);
                startActivity(creat);
            }
        });

    }
}
