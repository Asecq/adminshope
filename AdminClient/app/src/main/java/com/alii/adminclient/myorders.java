package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class myorders extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatabaseReference rootPro;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<Cells> options;
    private FirebaseRecyclerAdapter<Cells, d_holder> adapter;
    private EditText sea_text;
    private ImageView searchBtn;
    private String searchhow = "1";
    private Spinner selected ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        Toolbar toolbar = findViewById(R.id.toolbar_order);
        toolbar.setTitle("فاتورتي");
        selected = findViewById(R.id.selected_search);
        sea_text = findViewById(R.id.text_searchorder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rootPro = FirebaseDatabase.getInstance().getReference().child("callsover");

        ArrayList<String> items = new ArrayList<>();
        String[] plants = new String[]{
                "الرقم",
                "الملاحظة"
                ,
                "أسم المستخدم"
                ,
                "قيد الأرسال"
                ,
                "المستلمات"
                ,
                "المجهزات"
                ,
                "المجمدات"
                ,
                "كل الفواتير"
        };
        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   R.layout.support_simple_spinner_dropdown_item, plantsList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selected.setOnItemSelectedListener(this);
        selected.setAdapter(spinnerArrayAdapter);
        recyclerView = findViewById(R.id.res_fatora);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setPadding(0,0,0,0);
        options = new FirebaseRecyclerOptions.Builder<Cells>().setQuery(rootPro, Cells.class).build();
        adapter = new FirebaseRecyclerAdapter<Cells, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, final int i, @NonNull final Cells dons) {
                d_holder.username.setText(dons.getUser());
                if (dons.getStatics().equals("0")){
                    d_holder.sta.setBackgroundColor(Color.WHITE);
                    d_holder.txt_static.setText("(" + "قيد الأرسال" + ")");
                    d_holder.txt_static.setTextColor(Color.BLACK);
                }
                if (dons.getStatics().equals("1")){
                    d_holder.sta.setBackgroundColor(Color.BLUE);
                    d_holder.txt_static.setText("(" + "تم الأستلام" + ")");
                    d_holder.txt_static.setTextColor(Color.BLUE);
                }
                if (dons.getStatics().equals("2")){
                    d_holder.sta.setBackgroundColor(Color.GREEN);
                    d_holder.txt_static.setText("(" + "تم التجهيز" + ")");
                    d_holder.txt_static.setTextColor(Color.GREEN);
                }
                if (dons.getStatics().equals("3")){
                    d_holder.sta.setBackgroundColor(Color.RED);
                    d_holder.txt_static.setText("(" + "تم تجميدها" + ")");
                    d_holder.txt_static.setTextColor(Color.RED);
                }
                if(!TextUtils.isEmpty(dons.getSaller().toString())){
                    d_holder.saller.setText(dons.getSaller());
                    d_holder.mandop.setVisibility(View.VISIBLE);
                }
                d_holder.dep.setText(dons.getDep());
                d_holder.code.setText(dons.getNumber());
                d_holder.note.setText(dons.getNote());
                d_holder.time.setText(dons.getTime());
                d_holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(myorders.this,fatora.class);
                        intent.putExtra("key",String.valueOf(dons.getNumber()));
                        intent.putExtra("phone",String.valueOf(dons.getPhone()));
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fatora,parent,false);
                return new d_holder(view);
            }
        };


        sea_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(sea_text.length() == 0){

                }else {
                    if(searchhow.equals("1")){
                        searchok(s.toString());
                    }else if(searchhow.equals("2")){
                        searcnote(s.toString());
                    }else if (searchhow.equals("3")){
                        seachuser(s.toString());
                    }
                }
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(sea_text.getWindowToken(), 0);


            }
        });

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if(position == 0){
            searchhow = "1";
            sea_text.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else if(position == 1){
            searchhow = "2";
            sea_text.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if (position == 2){
            searchhow = "3";
            sea_text.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if(position == 3){
            result("0");
        }else if(position == 4){
            result("1");
        }else if(position == 5){
            result("2");
        }else if(position == 6){
            result("3");
        }else if(position == 7){
            options = new FirebaseRecyclerOptions.Builder<Cells>().setQuery(rootPro, Cells.class).build();
            adapter = new FirebaseRecyclerAdapter<Cells, d_holder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final d_holder d_holder, final int i, @NonNull final Cells dons) {
                    d_holder.username.setText(dons.getUser());
                    if (dons.getStatics().equals("0")){
                        d_holder.sta.setBackgroundColor(Color.WHITE);
                        d_holder.txt_static.setText("(" + "قيد الأرسال" + ")");
                        d_holder.txt_static.setTextColor(Color.BLACK);
                    }
                    if (dons.getStatics().equals("1")){
                        d_holder.sta.setBackgroundColor(Color.BLUE);
                        d_holder.txt_static.setText("(" + "تم الأستلام" + ")");
                        d_holder.txt_static.setTextColor(Color.BLUE);
                    }
                    if (dons.getStatics().equals("2")){
                        d_holder.sta.setBackgroundColor(Color.GREEN);
                        d_holder.txt_static.setText("(" + "تم التجهيز" + ")");
                        d_holder.txt_static.setTextColor(Color.GREEN);
                    }
                    if (dons.getStatics().equals("3")){
                        d_holder.sta.setBackgroundColor(Color.RED);
                        d_holder.txt_static.setText("(" + "تم تجميدها" + ")");
                        d_holder.txt_static.setTextColor(Color.RED);
                    }
                    if(!TextUtils.isEmpty(dons.getSaller().toString())){
                        d_holder.saller.setText(dons.getSaller());
                        d_holder.mandop.setVisibility(View.VISIBLE);
                    }
                    d_holder.dep.setText(dons.getDep());
                    d_holder.code.setText(String.valueOf(dons.getNumber()));
                    d_holder.note.setText(dons.getNote());
                    d_holder.time.setText(dons.getTime());
                    d_holder.cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(myorders.this,fatora.class);
                            intent.putExtra("key",String.valueOf(dons.getNumber()));
                            intent.putExtra("phone",String.valueOf(dons.getPhone()));
                            startActivity(intent);
                        }
                    });
                }

                @NonNull
                @Override
                public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fatora,parent,false);
                    return new d_holder(view);
                }
            };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }
    }

    private void result(String s) {
        Query query = rootPro.orderByChild("statics").equalTo(s);
        options = new FirebaseRecyclerOptions.Builder<Cells>().setQuery(query, Cells.class).build();
        adapter = new FirebaseRecyclerAdapter<Cells, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, final int i, @NonNull final Cells dons) {
                d_holder.username.setText(dons.getUser());
                if (dons.getStatics().equals("0")){
                    d_holder.sta.setBackgroundColor(Color.WHITE);
                    d_holder.txt_static.setText("(" + "قيد الأرسال" + ")");
                    d_holder.txt_static.setTextColor(Color.BLACK);
                }
                if (dons.getStatics().equals("1")){
                    d_holder.sta.setBackgroundColor(Color.BLUE);
                    d_holder.txt_static.setText("(" + "تم الأستلام" + ")");
                    d_holder.txt_static.setTextColor(Color.BLUE);
                }
                if (dons.getStatics().equals("2")){
                    d_holder.sta.setBackgroundColor(Color.GREEN);
                    d_holder.txt_static.setText("(" + "تم التجهيز" + ")");
                    d_holder.txt_static.setTextColor(Color.GREEN);
                }
                if (dons.getStatics().equals("3")){
                    d_holder.sta.setBackgroundColor(Color.RED);
                    d_holder.txt_static.setText("(" + "تم تجميدها" + ")");
                    d_holder.txt_static.setTextColor(Color.RED);
                }
                if(!TextUtils.isEmpty(dons.getSaller().toString())){
                    d_holder.saller.setText(dons.getSaller());
                    d_holder.mandop.setVisibility(View.VISIBLE);
                }
                d_holder.dep.setText(dons.getDep());
                d_holder.code.setText(String.valueOf(dons.getNumber()));
                d_holder.note.setText(dons.getNote());
                d_holder.time.setText(dons.getTime());
                d_holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(myorders.this,fatora.class);
                        intent.putExtra("key",String.valueOf(dons.getNumber()));
                        intent.putExtra("phone",String.valueOf(dons.getPhone()));
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fatora,parent,false);
                return new d_holder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public static class d_holder extends RecyclerView.ViewHolder {

        private TextView time  , code  , note , username  , txt_static , saller , dep;
        private CardView cardView;
        private View sta;
        private RelativeLayout mandop;




        public d_holder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_fattora);
            code = itemView.findViewById(R.id.num_fatoraorder);
            note = itemView.findViewById(R.id.note_fatoraorder);
            time = itemView.findViewById(R.id.data_fatoraorder);
            sta = itemView.findViewById(R.id.static_order);

            username = itemView.findViewById(R.id.user_fatora);
            txt_static = itemView.findViewById(R.id.text_static);
            mandop = itemView.findViewById(R.id.from_mandob);
            saller = itemView.findViewById(R.id.name_mandop);
            dep = itemView.findViewById(R.id.de_show);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

    private void searchok(final String texts){
        DatabaseReference dsee = FirebaseDatabase.getInstance().getReference();
        dsee.child("callsover").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String movieNamee = ds.child("number").getValue(String.class);
                    if(movieNamee.contains(texts)){
                        Query fireb = rootPro.orderByChild("number").startAt(texts).endAt(texts + "\uf9ff");
                        options = new FirebaseRecyclerOptions.Builder<Cells>().setQuery(fireb, Cells.class).build();
                        adapter = new FirebaseRecyclerAdapter<Cells, d_holder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull final d_holder d_holder, final int i, @NonNull final Cells dons) {

                                d_holder.code.setText(String.valueOf(dons.getNumber()));
                                d_holder.note.setText(dons.getNote());
                                d_holder.username.setText(dons.getUser());
                                if (dons.getStatics().equals("0")){
                                    d_holder.sta.setBackgroundColor(Color.WHITE);
                                    d_holder.txt_static.setText("(" + "قيد الأرسال" + ")");
                                    d_holder.txt_static.setTextColor(Color.BLACK);
                                }
                                if (dons.getStatics().equals("1")){
                                    d_holder.sta.setBackgroundColor(Color.BLUE);
                                    d_holder.txt_static.setText("(" + "تم الأستلام" + ")");
                                    d_holder.txt_static.setTextColor(Color.BLUE);
                                }
                                if (dons.getStatics().equals("2")){
                                    d_holder.sta.setBackgroundColor(Color.GREEN);
                                    d_holder.txt_static.setText("(" + "تم التجهيز" + ")");
                                    d_holder.txt_static.setTextColor(Color.GREEN);
                                }
                                if (dons.getStatics().equals("3")){
                                    d_holder.sta.setBackgroundColor(Color.RED);
                                    d_holder.txt_static.setText("(" + "تم تجميدها" + ")");
                                    d_holder.txt_static.setTextColor(Color.RED);
                                }
                                if(!TextUtils.isEmpty(dons.getSaller().toString())){
                                    d_holder.saller.setText(dons.getSaller());
                                    d_holder.mandop.setVisibility(View.VISIBLE);
                                }
                                d_holder.dep.setText(dons.getDep());
                                d_holder.time.setText(dons.getTime());
                                d_holder.cardView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(myorders.this,fatora.class);
                                        intent.putExtra("key",String.valueOf(dons.getNumber()));
                                        intent.putExtra("phone",String.valueOf(dons.getPhone()));
                                        startActivity(intent);
                                    }
                                });
                            }

                            @NonNull
                            @Override
                            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fatora,parent,false);
                                return new d_holder(view);
                            }
                        };
                        recyclerView.setAdapter(adapter);
                        adapter.startListening();
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void searcnote(final String text){
        DatabaseReference dse = FirebaseDatabase.getInstance().getReference();
        dse.child("callsover").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String movieName = ds.child("note").getValue(String.class);
                        Query fireb = rootPro.orderByChild("note").startAt(text).endAt(text + "\uf9ff");
                        options = new FirebaseRecyclerOptions.Builder<Cells>().setQuery(fireb, Cells.class).build();
                        adapter = new FirebaseRecyclerAdapter<Cells, d_holder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull final d_holder d_holder, final int i, @NonNull final Cells dons) {
                                String phone = dons.getPhone().toString();
                                String num = dons.getNumber().toString();
                                DatabaseReference use = FirebaseDatabase.getInstance().getReference();
                                use.child("Users").child(phone).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        d_holder.username.setText(dataSnapshot.getValue().toString());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                DatabaseReference numbersdata = FirebaseDatabase.getInstance().getReference();
                                numbersdata.child("Calls").child(num).child("static").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getValue().toString().equals("0")){
                                            d_holder.sta.setBackgroundColor(Color.WHITE);
                                            d_holder.txt_static.setText("(" + "قيد الأرسال" + ")");
                                            d_holder.txt_static.setTextColor(Color.BLACK);
                                        }
                                        if (dataSnapshot.getValue().toString().equals("1")){
                                            d_holder.sta.setBackgroundColor(Color.BLUE);
                                            d_holder.txt_static.setText("(" + "تم الأستلام" + ")");
                                            d_holder.txt_static.setTextColor(Color.BLUE);
                                        }
                                        if (dataSnapshot.getValue().toString().equals("2")){
                                            d_holder.sta.setBackgroundColor(Color.GREEN);
                                            d_holder.txt_static.setText("(" + "تم التجهيز" + ")");
                                            d_holder.txt_static.setTextColor(Color.GREEN);
                                        }
                                        if (dataSnapshot.getValue().toString().equals("3")){
                                            d_holder.sta.setBackgroundColor(Color.RED);
                                            d_holder.txt_static.setText("(" + "تم تجميدها" + ")");
                                            d_holder.txt_static.setTextColor(Color.RED);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                if(!TextUtils.isEmpty(dons.getSaller().toString())){
                                    d_holder.saller.setText(dons.getSaller());
                                    d_holder.mandop.setVisibility(View.VISIBLE);
                                }
                                d_holder.dep.setText(dons.getDep());
                                d_holder.code.setText(String.valueOf(dons.getNumber()));
                                d_holder.note.setText(dons.getNote());
                                d_holder.time.setText(dons.getTime());
                                d_holder.cardView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(myorders.this,fatora.class);
                                        intent.putExtra("key",String.valueOf(dons.getNumber()));
                                        intent.putExtra("phone",String.valueOf(dons.getPhone()));
                                        startActivity(intent);
                                    }
                                });
                            }

                            @NonNull
                            @Override
                            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fatora,parent,false);
                                return new d_holder(view);
                            }
                        };
                        recyclerView.setAdapter(adapter);
                        adapter.startListening();
                    }


                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*String query = text.toString();
        Query fireb = rootPro.orderByChild("note").startAt(query).endAt(query);
        options = new FirebaseRecyclerOptions.Builder<Calls>().setQuery(fireb, Calls.class).build();
        adapter = new FirebaseRecyclerAdapter<Calls, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, final int i, @NonNull final Calls dons) {

                d_holder.code.setText(String.valueOf(dons.getNumber()));
                d_holder.note.setText(dons.getNote());
                d_holder.time.setText(dons.getTime());
                d_holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(myorders.this,AboutActivity.class);
                        intent.putExtra("key",String.valueOf(dons.getNumber()));
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fatora,parent,false);
                return new d_holder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
*/
    }


    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(myorders.this,Cpanal.class);
        startActivity(intent);
        return true;

    }
    void  seachuser(String text){
        Query query = rootPro.orderByChild("user").startAt(text).endAt(text + "\uf9ff");
        options = new FirebaseRecyclerOptions.Builder<Cells>().setQuery(query, Cells.class).build();
        adapter = new FirebaseRecyclerAdapter<Cells, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, final int i, @NonNull final Cells dons) {

                String phone = dons.getPhone().toString();
                String num = dons.getNumber().toString();
                DatabaseReference use = FirebaseDatabase.getInstance().getReference();
                use.child("Users").child(phone).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        d_holder.username.setText(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                DatabaseReference numbersdata = FirebaseDatabase.getInstance().getReference();
                numbersdata.child("Calls").child(num).child("static").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue().toString().equals("0")){
                            d_holder.sta.setBackgroundColor(Color.WHITE);
                            d_holder.txt_static.setText("(" + "قيد الأرسال" + ")");
                            d_holder.txt_static.setTextColor(Color.BLACK);
                        }
                        if (dataSnapshot.getValue().toString().equals("1")){
                            d_holder.sta.setBackgroundColor(Color.BLUE);
                            d_holder.txt_static.setText("(" + "تم الأستلام" + ")");
                            d_holder.txt_static.setTextColor(Color.BLUE);
                        }
                        if (dataSnapshot.getValue().toString().equals("2")){
                            d_holder.sta.setBackgroundColor(Color.GREEN);
                            d_holder.txt_static.setText("(" + "تم التجهيز" + ")");
                            d_holder.txt_static.setTextColor(Color.GREEN);
                        }
                        if (dataSnapshot.getValue().toString().equals("3")){
                            d_holder.sta.setBackgroundColor(Color.RED);
                            d_holder.txt_static.setText("(" + "تم تجميدها" + ")");
                            d_holder.txt_static.setTextColor(Color.RED);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if(!TextUtils.isEmpty(dons.getSaller().toString())){
                    d_holder.saller.setText(dons.getSaller());
                    d_holder.mandop.setVisibility(View.VISIBLE);
                }
                d_holder.dep.setText(dons.getDep());
                d_holder.code.setText(String.valueOf(dons.getNumber()));
                d_holder.note.setText(dons.getNote());
                d_holder.time.setText(dons.getTime());
                d_holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(myorders.this,fatora.class);
                        intent.putExtra("key",String.valueOf(dons.getNumber()));
                        intent.putExtra("phone",String.valueOf(dons.getPhone()));
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fatora,parent,false);
                return new d_holder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
