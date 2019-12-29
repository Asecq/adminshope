package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.DownloadManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaticActivity extends AppCompatActivity {
    private DatabaseReference rootPro;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<Dons> options;
    private FirebaseRecyclerAdapter<Dons, d_holder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        rootPro = FirebaseDatabase.getInstance().getReference().child("DoneOrders");
        recyclerView = findViewById(R.id.rec_done);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setPadding(0,0,0,0);
        options = new FirebaseRecyclerOptions.Builder<Dons>().setQuery(rootPro, Dons.class).build();
        adapter = new FirebaseRecyclerAdapter<Dons, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, int i, @NonNull final Dons dons) {

                d_holder.code.setText(dons.getCode());
                d_holder.time.setText(dons.getTime());
                d_holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog info = new Dialog(StaticActivity.this);
                        info.setTitle("معلومات الطلب");
                        info.setContentView(R.layout.info_dons);
                        info.show();
                        TextView user , price , count , code , time , close , phone ;
                        user = info.findViewById(R.id.infoD_username);
                        price = info.findViewById(R.id.infoD_price);
                        code = info.findViewById(R.id.infoD_code);
                        time = info.findViewById(R.id.infoD_time);
                        count = info.findViewById(R.id.infoD_count);
                        close = info.findViewById(R.id.close_infoD);
                        phone  = info.findViewById(R.id.infoD_phone);
                        phone.setText( "رقم الزبون : " + dons.getPhone());
                        user.setText("اسم الزبون : " + dons.getUser());
                        price.setText("السعر الكلي : " + dons.getPrice());
                        code.setText("رقم الطلب : "+ dons.getCode());
                        time.setText("تاريخ الطلب : " + dons.getTime());
                        count.setText("العدد : " + dons.getCount());
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                info.dismiss();
                            }
                        });
                    }
                });




            }

            @NonNull
            @Override
            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dons,parent,false);
               return new d_holder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }
    public static class d_holder extends RecyclerView.ViewHolder {

        private TextView time  , code ;
        private CardView cardView;



        public d_holder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.infodone_time);
            code = itemView.findViewById(R.id.infodone_code);
            cardView = itemView.findViewById(R.id.infocard_done);

        }
    }
}
