package com.alii.adminclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fatora extends AppCompatActivity {
    private ListView listView;
    private String stat =  "";
    ImageView setting;
    ImageView print ;
    View main;
    private String key ;
    private String phone;
    private String dep;
    private TextView back , size;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_fatora);
        dep = getIntent().getExtras().getString("dep");
       setting = findViewById(R.id.setting_fatora);
        print = findViewById(R.id.prit_image);
        main = findViewById(R.id.fatrora_screen);
        key = getIntent().getExtras().getString("key");
        phone= getIntent().getExtras().get("phone").toString();
        size = findViewById(R.id.totalsizefatora);
        back = findViewById(R.id.back_to_h);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(dep)){
                    Intent intent = new Intent(fatora.this,Cpanal.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(fatora.this,Cpanal2Activity.class);
                    intent.putExtra("dep",dep);
                    startActivity(intent);
                }

            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent printInent = new Intent(fatora.this,Printer_fatora.class);
                    printInent.putExtra("key",getIntent().getExtras().get("key").toString());
                    startActivity(printInent);



                }catch (Exception e){
                    Toast.makeText(fatora.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
       reference = FirebaseDatabase.getInstance().getReference().child("Calls").child(getIntent().getExtras().get("key").toString());
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //----------------
                stat = dataSnapshot.child("static").getValue().toString();
                final GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                ArrayList<String> ingredients = dataSnapshot.child("Names").getValue(t);
                ArrayList<String> cou = dataSnapshot.child("Counts").getValue(t);
                ArrayList<String> price = dataSnapshot.child("Prices").getValue(t);
                ArrayList<String> one = dataSnapshot.child("Ones").getValue(t);
                final ArrayList<String> unites = dataSnapshot.child("Units").getValue(t);
                ArrayList<String> totales = dataSnapshot.child("Ptotal").getValue(t);

                listView = findViewById(R.id.listvi);
                Mobile mobile;
                ArrayList<Mobile> mobiles = new ArrayList<Mobile>();

                for(int i = 0 ; i<ingredients.size() ; i++) {
                    mobile = new Mobile();
                    String[] mStringArray = new String[ingredients.size()];
                    mStringArray = ingredients.toArray(mStringArray);
                    //
                    String[] pricess = new String[price.size()];
                    pricess = price.toArray(pricess);

                    //
                    String[] unite = new String[unites.size()];
                    unite = unites.toArray(unite);
                    //
                    String[] totel = new String[totales.size()];
                    totel = totales.toArray(totel);
                    //
                    String[] ones = new String[one.size()];
                    ones = one.toArray(ones);
                    //

                    String[] tStringArray = new String[cou.size()];
                    tStringArray = cou.toArray(tStringArray);
                    //
                    mobile.setNames(mStringArray);
                    mobile.setCounts(tStringArray);
                    mobile.setOnes(ones);
                    mobile.setUnits(unite);
                    mobile.setPrices(pricess);
                    mobile.setPtotal(totel);
                    mobiles.add(mobile);
                }


                listView.setAdapter(new adapterlistview(fatora.this, mobiles));

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(fatora.this, String.valueOf(position + 1), Toast.LENGTH_SHORT).show();


                    }
                });
                setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(fatora.this);
                        dialog.setTitle("الفاتورة");
                        dialog.setContentView(R.layout.changestatic);
                        dialog.show();
                        Button btn = dialog.findViewById(R.id.close_change);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        final RadioGroup radioSexGroup = dialog.findViewById(R.id.radioFatora);
                        RadioButton radio1 =  dialog.findViewById(R.id.done_seen);
                        RadioButton radio2 =  dialog.findViewById(R.id.done_done);
                        RadioButton radio3 =  dialog.findViewById(R.id.done_stop);

                        if(stat.equals("1")){
                            radio1.setChecked(true);
                        }
                        if(stat.equals("2")){
                            radio2.setChecked(true);
                        }
                        if(stat.equals("3")){
                            radio3.setChecked(true);
                        }



                        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                                RadioButton radioSexButton =  dialog.findViewById(selectedId);
                                if(radioSexButton.getText().equals("تم الأستلام")){
                                    DatabaseReference ch = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
                                    ch.child("Calls").child(key).child("static").setValue("1");
                                    DatabaseReference cals = FirebaseDatabase.getInstance().getReference();
                                    cals.child("Users").child(phone).child("callsover").child(key).child("statics").setValue("1");
                                    reference.child("static").setValue("1");
                                    DatabaseReference ed = FirebaseDatabase.getInstance().getReference().child("callsover");
                                    ed.child(key).child("statics").setValue("1");

                                    dialog.dismiss();
                                }
                                if(radioSexButton.getText().equals("تجميد")){
                                    DatabaseReference ch = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
                                    ch.child("Calls").child(key).child("static").setValue("3");
                                    reference.child("static").setValue("3");
                                    DatabaseReference cals = FirebaseDatabase.getInstance().getReference();
                                    cals.child("Users").child(phone).child("callsover").child(key).child("statics").setValue("3");
                                    DatabaseReference ed = FirebaseDatabase.getInstance().getReference().child("callsover");
                                    ed.child(key).child("statics").setValue("3");
                                    dialog.dismiss();
                                }
                                if(radioSexButton.getText().equals("تم التجهيز")){
                                   DatabaseReference ch = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
                                    ch.child("Calls").child(key).child("static").setValue("2");
                                    DatabaseReference cals = FirebaseDatabase.getInstance().getReference();
                                    cals.child("Users").child(phone).child("callsover").child(key).child("statics").setValue("2");
                                    reference.child("static").setValue("2");
                                    DatabaseReference ed = FirebaseDatabase.getInstance().getReference().child("callsover");
                                    ed.child(key).child("statics").setValue("2");
                                    dialog.dismiss();
                                }

                            }
                        });

                    }
                });

                TextView total = findViewById(R.id.totalorder);
                TextView number = findViewById(R.id.numberfatora);
                TextView sta = findViewById(R.id.static_fatora);
                ImageView img_static = findViewById(R.id.img_static);
                if(dataSnapshot.child("static").getValue().toString().equals("2")){
                    sta.setText("تم التجهيز");
                    sta.setTextColor(Color.rgb(0, 204, 0));
                    img_static.setImageResource(R.drawable.donns);
                }
                if(dataSnapshot.child("static").getValue().toString().equals("0")){
                    sta.setText("قيد الأرسال ...");
                    sta.setTextColor(Color.rgb(204, 255, 255));
                    img_static.setImageResource(R.drawable.sending);
                }
                if(dataSnapshot.child("static").getValue().toString().equals("3")){
                    sta.setText("تم تجميدها");
                    sta.setTextColor(Color.rgb(255, 0, 0));
                    img_static.setImageResource(R.drawable.stopp);
                }
                if(dataSnapshot.child("static").getValue().toString().equals("1")){
                    sta.setText("تم الأستلام");
                    sta.setTextColor(Color.rgb(0, 153, 255));
                    img_static.setImageResource(R.drawable.seen);
                }
                total.setText( dataSnapshot.child("Price").getValue().toString() + "$");
                number.setText( "رقم الفاتورة :  "+ dataSnapshot.child("number").getValue().toString() );
                TextView note = findViewById(R.id.note_fatora);
                note.setText("ملاحظات : " + dataSnapshot.child("note").getValue().toString());
                TextView time = findViewById(R.id.time_fatora);
                time.setText(dataSnapshot.child("time").getValue().toString());
                size.setText("عدد عناصر الفاتورة : "  + String.valueOf(listView.getAdapter().getCount()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
