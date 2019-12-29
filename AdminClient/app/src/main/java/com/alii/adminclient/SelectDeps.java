package com.alii.adminclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import static android.R.layout.simple_list_item_1;

public class SelectDeps extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private ListView mListView;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_deps);
        mListView = (ListView) findViewById(R.id.listview_select);
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


                    String barcode = (String) snapshot.child("Descrption").getValue();
                    String name = (String) snapshot.child("category").getValue();
                    String exp = (String) snapshot.child("pid").getValue();
                    String q = (String) snapshot.child("pname").getValue();
                    names.add("Product Name: "+name+"\nExpiry Date: "+exp+ "\nQuantity: "+q+"\nBarcode: "+ barcode );

                    keys.add(snapshot.getKey());


                }
                adapter.addAll(keys);
                adapter.notifyDataSetChanged();
                mListView.setBackgroundColor(Color.WHITE);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        String myKey = keys.get(i);
                        Intent putc = new Intent(SelectDeps.this,AddPost.class);
                        putc.putExtra("department",myKey);
                        startActivity(putc);
                        // Intent intent  = new Intent(Inventory.this,Edit.class);
                        //  intent.putExtra("value",bc);
                        // startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
