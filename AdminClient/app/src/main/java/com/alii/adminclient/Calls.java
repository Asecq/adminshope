package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Calls extends AppCompatActivity {

    private DatabaseReference rootPro;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<Orders> options;
    private FirebaseRecyclerAdapter<Orders, d_holder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);
        rootPro = FirebaseDatabase.getInstance().getReference().child("Calls");
        recyclerView = findViewById(R.id.rec_orders);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setPadding(0,0,0,0);
        options = new FirebaseRecyclerOptions.Builder<Orders>().setQuery(rootPro,Orders.class).build();
        adapter = new FirebaseRecyclerAdapter<Orders, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, int i, @NonNull final Orders orders) {

                d_holder.user.setText( "اسم الزبون : " + orders.getUser());
                d_holder.price.setText("السعر الكلي : " + orders.getPrice());
                d_holder.count.setText("العدد : " + orders.getCount());
                d_holder.code.setText("رقم الطلب : " + orders.getCode());
                d_holder.phone.setText(orders.getPhone());
                d_holder.time.setText(orders.getTime());
                //=========================================
                //Buttons >_<
                d_holder.done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference rootdone  = FirebaseDatabase.getInstance().getReference().child("DoneOrders");
                        HashMap<String , Object> done = new HashMap<>();
                       done.put("user",orders.getUser());
                       done.put("code",orders.getCode());
                       done.put("count",orders.getCount());
                       done.put("price",orders.getPrice());
                       done.put("time",orders.getTime());
                       done.put("phone",orders.getPhone());
                       rootdone.child(orders.getCode()).updateChildren(done).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               Toast.makeText(Calls.this, "تم حفظ الطلب في سجل الطلبات المكتملة", Toast.LENGTH_LONG).show();
                               rootPro.child(orders.getCode()).removeValue();
                           }
                       });
                    }
                });
                d_holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(Calls.this);
                        dialog.setTitle("حذف الطلب");
                        dialog.setContentView(R.layout.makesure);
                        dialog.show();
                        Button ok , no;
                        ok = dialog.findViewById(R.id.dialog_ok);
                        no = dialog.findViewById(R.id.dialog_cancel);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rootPro.child(orders.getCode()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.dismiss();
                                        Toast.makeText(Calls.this, "تم حذف الطلب !", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                    }
                });

            }

            @NonNull
            @Override
            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordersrow,parent,false);
                return new d_holder(view);
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
    public static class d_holder extends RecyclerView.ViewHolder {

        private TextView user  , code  ,  price , count ,phone , time ;
        private ImageView delete , done;
        public d_holder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.infoorder_user);
            code = itemView.findViewById(R.id.infoorder_code);
            price = itemView.findViewById(R.id.infoorder_price);
            count = itemView.findViewById(R.id.infoorder_count);
            phone = itemView.findViewById(R.id.infoorder_phoneuser);
            time = itemView.findViewById(R.id.infoorder_time);
            done = itemView.findViewById(R.id.infoorder_done);
            delete = itemView.findViewById(R.id.infoorder_delete);




        }
    }
}
