package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class postssActivity extends AppCompatActivity {
    private DatabaseReference rootPro;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<Protucts> options;
    private EditText search;
    private FirebaseRecyclerAdapter<Protucts, d_holder> adapter;
    private String dep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postss);
        dep = getIntent().getExtras().getString("dep");
        search = findViewById(R.id.search_posts);
        rootPro = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.re_allposts1);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setPadding(0,0,0,0);
        options = new FirebaseRecyclerOptions.Builder<Protucts>().setQuery(rootPro.child("Protucts").child(dep),Protucts.class).build();
        adapter = new FirebaseRecyclerAdapter<Protucts, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, int i, @NonNull final Protucts protucts) {

                d_holder.post_title.setText(protucts.getPname());
                d_holder.dep.setText(protucts.getPprice()+ " $");
                d_holder.code.setText(protucts.getDescrption());
                Picasso.get().load(protucts.getImage()).into(d_holder.post_image);
                Glide.with(postssActivity.this).load(protucts.getImage()).into(d_holder.post_image);

                d_holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(postssActivity.this, "ليس لديك صلاحيات لحذف المادة", Toast.LENGTH_SHORT).show();
                    }
                });
                d_holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(postssActivity.this, "ليس لديك صلاحيات لتعديل المادة", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @NonNull
            @Override
            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_allposts,parent,false);

                return new d_holder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().matches("")){
                    options = new FirebaseRecyclerOptions.Builder<Protucts>().setQuery(rootPro.child("Protucts").child(dep),Protucts.class).build();
                    adapter = new FirebaseRecyclerAdapter<Protucts, d_holder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final d_holder d_holder, int i, @NonNull final Protucts protucts) {

                            d_holder.post_title.setText(protucts.getPname());
                            d_holder.dep.setText(protucts.getPprice()+ " $");
                            d_holder.code.setText(protucts.getDescrption());
                            Picasso.get().load(protucts.getImage()).into(d_holder.post_image);
                            Glide.with(postssActivity.this).load(protucts.getImage()).into(d_holder.post_image);


                        }

                        @NonNull
                        @Override
                        public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_allposts,parent,false);

                            return new d_holder(view);
                        }
                    };
                    recyclerView.setAdapter(adapter);
                    adapter.startListening();
                    adapter.notifyDataSetChanged();
                }else {
                    reslut(s.toString());
                }
            }
        });
    }

    private void reslut(String s) {
        Query query = rootPro.child("Protucts").child(dep).orderByChild("pname").startAt(s).endAt(s + "\uf9ff");
        options = new FirebaseRecyclerOptions.Builder<Protucts>().setQuery(query,Protucts.class).build();
        adapter = new FirebaseRecyclerAdapter<Protucts, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, int i, @NonNull final Protucts protucts) {
                d_holder.post_title.setText(protucts.getPname());
                d_holder.dep.setText(protucts.getPprice()+ " $");
                d_holder.code.setText(protucts.getDescrption());
                Picasso.get().load(protucts.getImage()).into(d_holder.post_image);
                Glide.with(postssActivity.this).load(protucts.getImage()).into(d_holder.post_image);


            }

            @NonNull
            @Override
            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_allposts,parent,false);

                return new d_holder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    public static class d_holder extends RecyclerView.ViewHolder {

        private TextView post_title  , dep  ,  code  ;
        private ImageView post_image , delete , edit;
        public d_holder(@NonNull View itemView) {
            super(itemView);

            post_image = itemView.findViewById(R.id.image_allposts);
            post_title = itemView.findViewById(R.id.name_allposts);
            code = itemView.findViewById(R.id.code_allposts);
            dep = itemView.findViewById(R.id.dep_allposts);
            delete = itemView.findViewById(R.id.delete_Allposts);
            edit = itemView.findViewById(R.id.edit_allposts);


        }
    }
}
