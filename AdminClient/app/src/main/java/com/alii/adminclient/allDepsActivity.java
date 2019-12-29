package com.alii.adminclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class allDepsActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ListView mListView;
    private ArrayList<String> keys = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_deps);
        mListView = (ListView) findViewById(R.id.listview_deps);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this, simple_list_item_1, keys){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(20);
                mListView.setBackgroundColor(001621);

                return view;
            }
        };
        mListView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Protucts");
        final List<String> keys = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){


                    keys.add(snapshot.getKey());


                }
                adapter.addAll(keys);
                adapter.notifyDataSetChanged();
                mListView.setBackgroundColor(Color.WHITE);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                       final String myKey = keys.get(i);
                        final Dialog delete  = new Dialog(allDepsActivity.this);
                        delete.setTitle("حذف القسم");
                        delete.setContentView(R.layout.deletedeps);
                        delete.show();
                        Button yes = delete.findViewById(R.id.dialog_ok2);
                        Button no = delete.findViewById(R.id.dialog_cancel2);
                       yes.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               databaseReference.child(myKey).removeValue();
                               delete.dismiss();
                               Toast.makeText(allDepsActivity.this, "تم حذف القسم بكل محتوياته", Toast.LENGTH_SHORT).show();
                               adapter.notifyDataSetChanged();
                              finish();
                              startActivity(getIntent());
                           }
                       });
                       no.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               delete.dismiss();
                           }
                       });




                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
