package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Register extends AppCompatActivity{
    private Button btn_register;
    private EditText input_name  , input_pass;
    private ProgressDialog loading;
    private  String type;
    private Boolean botrou;
    private Spinner spinner;
    private DatabaseReference lastt;
    private ArrayList<String> keys = new ArrayList<>();
    public ArrayList<String> names = new ArrayList<>();
    private EditText typeid ;
    private Dialog dialog;
    private int lastid;
    private Button copy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        typeid = findViewById(R.id.id_register);
        typeid.setEnabled(true);
        copy = findViewById(R.id.copy_id);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(String.valueOf(lastid));
                Toast.makeText(Register.this, "تم النسخ", Toast.LENGTH_SHORT).show();
            }
        });

        lastt = FirebaseDatabase.getInstance().getReference().child("lastuser");
        lastt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lastid = Integer.parseInt(dataSnapshot.getValue().toString()) ;
                typeid.setText(String.valueOf(lastid));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        spinner = findViewById(R.id.sp_reg);
        spinner.setBackgroundColor(Color.WHITE);
        final ArrayAdapter<CharSequence> sadapter = ArrayAdapter.createFromResource(this,R.array.adapter_regester, R.layout.support_simple_spinner_dropdown_item);
        sadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(sadapter);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(parent.getItemIdAtPosition(position) == 0){
                  type = "user";
                  botrou = false;
                  lastt = FirebaseDatabase.getInstance().getReference().child("lastuser");

                  lastt.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          lastid = Integer.parseInt(dataSnapshot.getValue().toString()) ;
                          typeid.setText(String.valueOf(lastid));
                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                  });
              }else if (parent.getItemIdAtPosition(position) == 1){
                  type = "depr";
                  botrou = true;
                  lastt = FirebaseDatabase.getInstance().getReference().child("lastdepr");
                  lastt.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          lastid = Integer.parseInt(dataSnapshot.getValue().toString()) ;
                          typeid.setText(String.valueOf(lastid));
                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                  });
              }else if (parent.getItemIdAtPosition(position) == 2){
                  type = "admin";
                  botrou = false;
                  lastt = FirebaseDatabase.getInstance().getReference().child("lastadmin");
                  lastt.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          lastid = Integer.parseInt(dataSnapshot.getValue().toString()) ;
                          typeid.setText(String.valueOf(lastid));
                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                  });
              }else if(parent.getItemIdAtPosition(position) == 3){
                  type = "saller";
                  botrou = false;
                  lastt = FirebaseDatabase.getInstance().getReference().child("lastsaller");
                  lastt.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          lastid = Integer.parseInt(dataSnapshot.getValue().toString()) ;
                          typeid.setText(String.valueOf(lastid));
                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                  });
              }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        btn_register = (Button)findViewById(R.id.register_btn);
        input_name = (EditText)findViewById(R.id.name_register);
        input_pass = (EditText)findViewById(R.id.pass_register);
        loading = new ProgressDialog(this);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                creatAccount();
            }
        });
    }
    private void creatAccount() {
        final String name = input_name.getText().toString();
        final String password = input_pass.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "اكتب الاسم", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(String.valueOf(lastid))){
            Toast.makeText(this, "مشكلة في رقم الحساب", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "اكتب كلمة السر", Toast.LENGTH_SHORT).show();
        }else{
            loading.setTitle("انشاء حساب");
            loading.setCanceledOnTouchOutside(false);
            loading.setMessage("الرجاءالأنتظار ...");
            loading.show();
            if(botrou){

                dialog =  new Dialog(Register.this);
                dialog.setContentView(R.layout.markesss);
                dialog.setTitle("الأقسام");
                dialog.show();
                TextView close = dialog.findViewById(R.id.close_marke);

                final ListView mListView = dialog.findViewById(R.id.listview_marka);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Protucts");

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(dialog.getContext(), 17367043, keys) {
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
                mListView.setAdapter(adapter);
                final List<String> keys2 = new ArrayList<>();
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ArrayList access$000 = Register.this.names;
                            StringBuilder sb = new StringBuilder();
                            access$000.add(sb.toString());
                            keys2.add(snapshot.getKey());
                        }
                        adapter.addAll(keys2);
                        adapter.notifyDataSetChanged();
                        mListView.setBackgroundColor(-1);
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String myKey = (String) keys2.get(i);
                                //  putc.putExtra("department", myKey);
                                dialog.dismiss();
                                keys2.clear();
                                VaidataPhoneNumber(name,String.valueOf(lastid),password,myKey);


                            }
                        });
                    }


                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Register.this, "أختر قسم..!", Toast.LENGTH_SHORT).show();

                    }
                });
            }else {
                VaidataPhoneNumber(name,String.valueOf(lastid),password , null);
            }


        }


    }

    private void VaidataPhoneNumber(final String name, final String phone, final String password , final String dep) {

        final DatabaseReference rootRf;

        rootRf = FirebaseDatabase.getInstance().getReference();
        rootRf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Users").child(String.valueOf(lastid)).exists()){

                    final HashMap<String ,Object> userdata = new HashMap<>();
                    userdata.put("username",name);
                    userdata.put("phone",String.valueOf(lastid));
                    userdata.put("pass",String.valueOf(password));
                    userdata.put("online","off");
                    userdata.put("type",type);
                    if(botrou){
                        userdata.put("dep",dep);
                    }
                    final DatabaseReference dzpon = FirebaseDatabase.getInstance().getReference().child("zpoon");
                    rootRf.child("Users").child(String.valueOf(lastid)).updateChildren(userdata)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        if(type.equals("user")){
                                            dzpon.child(String.valueOf(lastid)).updateChildren(userdata);
                                        }
                                        Toast.makeText(Register.this, "تم انشاء الحساب", Toast.LENGTH_SHORT).show();
                                        lastt.setValue(lastid + 1);
                                        loading.dismiss();
                                        Intent intent = new Intent(Register.this,Cpanal.class);
                                        startActivity(intent);
                                    }else {
                                        loading.dismiss();
                                        Toast.makeText(Register.this, "خطأ في الشبكة حاول في وقت لاحق", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }else if(!dataSnapshot.child("Users").child(String.valueOf(lastid)).child("type").equals(type)){
                    final HashMap<String ,Object> userdata = new HashMap<>();
                    userdata.put("username",name);
                    userdata.put("phone",String.valueOf(lastid));
                    userdata.put("pass",String.valueOf(password));
                    userdata.put("online","off");
                    userdata.put("type",type);
                    if(type == ("depr")){

                    }
                    rootRf.child("Users").child(String.valueOf(lastid)).updateChildren(userdata)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "تم انشاء الحساب", Toast.LENGTH_SHORT).show();
                                        lastt.setValue(lastid + 1);
                                        loading.dismiss();
                                        Intent intent = new Intent(Register.this,Cpanal.class);
                                        startActivity(intent);
                                    }else {
                                        loading.dismiss();
                                        Toast.makeText(Register.this, "خطأ في الشبكة حاول في وقت لاحق", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                } else {
                    loading.dismiss();
                    Toast.makeText(Register.this, "رقم الحساب  موجود ...", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
