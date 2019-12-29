package com.alii.adminclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MarkasActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    private DatabaseReference databaseReference;
    private ArrayList<String> keys = new ArrayList<>();
    /* access modifiers changed from: private */
    public ListView mListView;
    /* access modifiers changed from: private */
    public ArrayList<String> names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markas);
        this.mListView = (ListView) findViewById(R.id.listview_marka);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 17367043, this.keys) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);
                mListView.setBackgroundColor(001621);
                return view;
            }
        };
        this.mListView.setAdapter(adapter);
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child("Marka");
        final List<String> keys2 = new ArrayList<>();
        this.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ArrayList access$000 = MarkasActivity.this.names;
                    StringBuilder sb = new StringBuilder();
                    access$000.add(sb.toString());
                    keys2.add(snapshot.getKey());
                }
                adapter.addAll(keys2);
                adapter.notifyDataSetChanged();
                MarkasActivity.this.mListView.setBackgroundColor(-1);
                MarkasActivity.this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String myKey = (String) keys2.get(i);
                      //  putc.putExtra("department", myKey);
                    }
                });
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
