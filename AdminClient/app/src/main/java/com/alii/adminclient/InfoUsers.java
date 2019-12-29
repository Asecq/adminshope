package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class InfoUsers extends AppCompatActivity {
    private DatabaseReference rootPro;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<Users> options;
    private FirebaseRecyclerAdapter<Users, d_holder> adapter;
    private String user_new , pass_new , phone_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_users);
        rootPro = FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView = findViewById(R.id.re_users);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setPadding(0,0,0,0);
        options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(rootPro,Users.class).build();
        adapter = new FirebaseRecyclerAdapter<Users, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, int i, @NonNull final Users users) {
                d_holder.username.setText(users.getUsername());

                //==========================
                //
                //
                // online or offline
                //
                //
                //==========================

                Picasso.get().load(users.getOnline()).into(d_holder.online);
















                //=========================== end online or offline









                //===========================
                //
                //
                // edit , delete , info ;;
                //
                //
                //===========================


                d_holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final  Dialog editdialog = new Dialog(InfoUsers.this);
                        editdialog.setContentView(R.layout.edit_user);
                        editdialog.setTitle("تعديل المستخدم");
                        editdialog.show();
                        final EditText usernameedite , passwordedit , phoneedit ;
                        Button save , close;
                        save = editdialog.findViewById(R.id.save_user);
                        close = editdialog.findViewById(R.id.close_edituser);
                        usernameedite = editdialog.findViewById(R.id.edit_username);
                        passwordedit = editdialog.findViewById(R.id.edit_password);
                        phoneedit = editdialog.findViewById(R.id.edit_phone);
                        usernameedite.setText(users.getUsername());
                        passwordedit.setText(users.getPass());
                        phoneedit.setText(users.getPhone());
                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SaveChangs();

                            }

                            private void SaveChangs() {
                                user_new = usernameedite.getText().toString().trim();
                                pass_new = passwordedit.getText().toString().trim();
                                phone_new = phoneedit.getText().toString().trim();
                                if (TextUtils.isEmpty(user_new)) {
                                    Toast.makeText(InfoUsers.this, "الأسم فارغ", Toast.LENGTH_SHORT).show();
                                }else if (TextUtils.isEmpty(pass_new)) {
                                    Toast.makeText(InfoUsers.this, "كلمة السر فارغة", Toast.LENGTH_SHORT).show();
                                }else if (TextUtils.isEmpty(phone_new)) {
                                    Toast.makeText(InfoUsers.this, "رقم الهاتف فارغ", Toast.LENGTH_SHORT).show();
                                }else {
                                    HashMap<String , Object> newuser = new HashMap<>();
                                    newuser.put("username",user_new);
                                    newuser.put("pass",pass_new);
                                    newuser.put("phone",phone_new);
                                    rootPro.child(users.getPhone()).updateChildren(newuser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            editdialog.dismiss();
                                            Toast.makeText(InfoUsers.this, "تم حفظ التغييرات", Toast.LENGTH_SHORT).show();
                                        }
                                    });



                                }



                            }
                        });
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editdialog.dismiss();
                            }
                        });
                    }
                });

                d_holder.info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final  Dialog infodialog = new Dialog(InfoUsers.this);
                        infodialog.setContentView(R.layout.infouser);
                        infodialog.setTitle("معلومات المستخدم");
                        infodialog.show();
                        TextView userInfo ,passInfo , phoneInfo;
                        userInfo = infodialog.findViewById(R.id.info_username);
                        passInfo = infodialog.findViewById(R.id.info_pass);
                        phoneInfo = infodialog.findViewById(R.id.info_phone);

                        userInfo.setText(users.getUsername());
                        passInfo.setText(users.getPass());
                        phoneInfo.setText(users.getPhone());
                        Button close = infodialog.findViewById(R.id.btn_close_info);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                infodialog.dismiss();
                            }
                        });
                    }
                });

                d_holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(InfoUsers.this); // Context, this, etc.
                        dialog.setContentView(R.layout.makesure);
                        dialog.setTitle("حذف المستخدم !");
                        dialog.show();

                        Button ok = (Button)dialog.findViewById(R.id.dialog_ok);
                        Button no = (Button)dialog.findViewById(R.id.dialog_cancel);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rootPro.child(users.getPhone()).removeValue();
                                dialog.dismiss();
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

                //=========================== end change


            }



            @NonNull
            @Override
            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users,parent,false);

                return new d_holder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }
    public static class d_holder extends RecyclerView.ViewHolder {

        private TextView username;
        private ImageView delete , info , edit , online , refres;
        public d_holder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            delete = itemView.findViewById(R.id.user_delete);
            info = itemView.findViewById(R.id.user_info);
            edit = itemView.findViewById(R.id.user_edit);
            online = itemView.findViewById(R.id.user_image);




        }
    }

}
