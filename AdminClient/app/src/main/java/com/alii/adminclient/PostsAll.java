package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostsAll extends AppCompatActivity {
    private DatabaseReference rootPro;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<Protucts> options;
    private EditText search;
    private FirebaseRecyclerAdapter<Protucts, d_holder> adapter;
    private String dep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_all);

        search = findViewById(R.id.search_posts);
        rootPro = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.re_allposts);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setPadding(0,0,0,0);
        options = new FirebaseRecyclerOptions.Builder<Protucts>().setQuery(rootPro.child("AllPost"),Protucts.class).build();
        adapter = new FirebaseRecyclerAdapter<Protucts, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, int i, @NonNull final Protucts protucts) {

                d_holder.post_title.setText(protucts.getPname());
                d_holder.dep.setText(protucts.getPprice()+ " $");
                d_holder.code.setText(protucts.getDescrption());
                Picasso.get().load(protucts.getImage()).into(d_holder.post_image);
                Glide.with(PostsAll.this).load(protucts.getImage()).into(d_holder.post_image);
                d_holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog delete = new Dialog(PostsAll.this);
                        delete.setContentView(R.layout.makesure);
                        delete.setTitle("حذف المنشور");
                        delete.show();
                        Button btndelete = delete.findViewById(R.id.dialog_ok);
                        Button close = delete.findViewById(R.id.dialog_cancel);
                        btndelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rootPro.child("AllPost").child(protucts.getPid()).removeValue();
                                rootPro.child("Protucts").child(protucts.getCategory()).child(protucts.getPid()).removeValue();
                                rootPro.child("MainPage").child(protucts.getPid()).removeValue();
                                StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(protucts.getImage());
                                reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(PostsAll.this, "تم الحذف", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                delete.dismiss();
                            }
                        });
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                delete.dismiss();
                            }
                        });


                    }
                });
                d_holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       Intent Edit = new Intent(PostsAll.this,EditPost.class);
                       Edit.putExtra("department",protucts.getCategory().toString());
                       Edit.putExtra("idpost",protucts.getPid());
                       Edit.putExtra("namepost",protucts.getPname());
                       Edit.putExtra("modelpost",protucts.getPmodel());
                       Edit.putExtra("imagepost",protucts.getImage());
                       Edit.putExtra("codepost",protucts.getPcode());
                       Edit.putExtra("marka" , protucts.getMarke());
                       Edit.putExtra("countpost",protucts.getCount());
                       Edit.putExtra("pricepost1",protucts.getPprice());
                       Edit.putExtra("pricepost2",protucts.getTestone());
                       Edit.putExtra("pricepost3",protucts.getTesttow());
                       Edit.putExtra("pricepost4",protucts.getTestthree());
                       Edit.putExtra("maintype",protucts.getMaimtype());
                       Edit.putExtra("despost",protucts.getDescrption());
                       startActivity(Edit);

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
                    options = new FirebaseRecyclerOptions.Builder<Protucts>().setQuery(rootPro.child("AllPost"),Protucts.class).build();
                    adapter = new FirebaseRecyclerAdapter<Protucts, d_holder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final d_holder d_holder, int i, @NonNull final Protucts protucts) {

                            d_holder.post_title.setText(protucts.getPname());
                            d_holder.dep.setText(protucts.getPprice()+ " $");
                            d_holder.code.setText(protucts.getDescrption());
                            Picasso.get().load(protucts.getImage()).into(d_holder.post_image);
                            Glide.with(PostsAll.this).load(protucts.getImage()).into(d_holder.post_image);
                            d_holder.delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final Dialog delete = new Dialog(PostsAll.this);
                                    delete.setContentView(R.layout.makesure);
                                    delete.setTitle("حذف المنشور");
                                    delete.show();
                                    Button btndelete = delete.findViewById(R.id.dialog_ok);
                                    Button close = delete.findViewById(R.id.dialog_cancel);
                                    btndelete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                           rootPro.child("AllPost").child(protucts.getPid()).removeValue();
                                            rootPro.child("Protucts").child(protucts.getCategory()).child(protucts.getPid()).removeValue();

                                            rootPro.child("MainPage").child(protucts.getPid()).removeValue();
                                            StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(protucts.getImage());
                                            reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(PostsAll.this, "تم الحذف", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            delete.dismiss();
                                        }
                                    });
                                    close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            delete.dismiss();
                                        }
                                    });


                                }
                            });
                            d_holder.edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent Edit = new Intent(PostsAll.this,EditPost.class);
                                    Edit.putExtra("department",protucts.getCategory().toString());
                                    Edit.putExtra("idpost",protucts.getPid());
                                    Edit.putExtra("namepost",protucts.getPname());
                                    Edit.putExtra("modelpost",protucts.getPmodel());
                                    Edit.putExtra("imagepost",protucts.getImage());
                                    Edit.putExtra("codepost",protucts.getPcode());
                                    Edit.putExtra("marka" , protucts.getMarke());
                                    Edit.putExtra("countpost",protucts.getCount());
                                    Edit.putExtra("pricepost1",protucts.getPprice());
                                    Edit.putExtra("pricepost2",protucts.getTestone());
                                    Edit.putExtra("pricepost3",protucts.getTesttow());
                                    Edit.putExtra("pricepost4",protucts.getTestthree());
                                    Edit.putExtra("maintype",protucts.getMaimtype());
                                    Edit.putExtra("despost",protucts.getDescrption());
                                    startActivity(Edit);

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
                }else {
                    reslut(s.toString());
                }
            }
        });
    }

    private void reslut(String s) {
        Query query = rootPro.child("AllPost").orderByChild("pname").startAt(s).endAt(s + "\uf9ff");
                options = new FirebaseRecyclerOptions.Builder<Protucts>().setQuery(query,Protucts.class).build();
        adapter = new FirebaseRecyclerAdapter<Protucts, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, int i, @NonNull final Protucts protucts) {
                d_holder.post_title.setText(protucts.getPname());
                d_holder.dep.setText(protucts.getPprice()+ " $");
                d_holder.code.setText(protucts.getDescrption());
                Picasso.get().load(protucts.getImage()).into(d_holder.post_image);
                Glide.with(PostsAll.this).load(protucts.getImage()).into(d_holder.post_image);
                d_holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog delete = new Dialog(PostsAll.this);
                        delete.setContentView(R.layout.makesure);
                        delete.setTitle("حذف المنشور");
                        delete.show();
                        Button btndelete = delete.findViewById(R.id.dialog_ok);
                        Button close = delete.findViewById(R.id.dialog_cancel);
                        btndelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rootPro.child("AllPost").child(protucts.getPid()).removeValue();
                                rootPro.child("Protucts").child(protucts.getCategory()).child(protucts.getPid()).removeValue();
                                rootPro.child("MainPage").child(protucts.getPid()).removeValue();
                                StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(protucts.getImage());
                                reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(PostsAll.this, "تم الحذف", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                delete.dismiss();
                            }
                        });
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                delete.dismiss();
                            }
                        });


                    }
                });
                d_holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent Edit = new Intent(PostsAll.this,EditPost.class);
                        Edit.putExtra("department",protucts.getCategory().toString());
                        Edit.putExtra("idpost",protucts.getPid());
                        Edit.putExtra("namepost",protucts.getPname());
                        Edit.putExtra("modelpost",protucts.getPmodel());
                        Edit.putExtra("imagepost",protucts.getImage());
                        Edit.putExtra("codepost",protucts.getPcode());
                        Edit.putExtra("marka" , protucts.getMarke());
                        Edit.putExtra("countpost",protucts.getCount());
                        Edit.putExtra("pricepost1",protucts.getPprice());
                        Edit.putExtra("pricepost2",protucts.getTestone());
                        Edit.putExtra("pricepost3",protucts.getTesttow());
                        Edit.putExtra("pricepost4",protucts.getTestthree());
                        Edit.putExtra("maintype",protucts.getMaimtype());
                        Edit.putExtra("despost",protucts.getDescrption());
                        startActivity(Edit);

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
