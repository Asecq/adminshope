package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.print.PrintHelper;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SymbolTable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.FactoryConfigurationError;

public class fatora extends AppCompatActivity {
    private ListView listView;
    private String stat =  "";
    ImageView setting;
    ImageView print ;
    View main;
    private ArrayList<String> keys = new ArrayList<>();
    public ArrayList<String> names = new ArrayList<>();
    private String downloadImageUrl;
  //  private ArrayList<Dialog> dialogArrayList = new ArrayList<Dialog>();
    private ProgressDialog alert;
    private String textnoting = "";
    private String username , saller , codene ;
    //Dialog fatoring_dialog = new Dialog(fatora.this);
    private String key ;
    int up = 0;
    private DatabaseReference donechangde;
    int k =0;
    private String notyy;
    private RelativeLayout sentdep , alidep;
    private ArrayList<String> names_img;
    ArrayList<String> listItemsnew = new ArrayList<>();
    private ArrayList<String> urls_img;
    private String phone;
    private Uri ImageUri;
    private String dep;
    Dialog dialogmain ;
    ArrayList<String> listItems = new ArrayList<String>();
    private static final int GalleryPick = 1;
    private String timef;
    private String newDepfor;
    private ArrayAdapter<String> adapter1;
    private TextView back , size , statictext , added , alitext;
    private ArrayList<Uri> imgurl = new ArrayList<Uri>();
    private DatabaseReference reference;
    String newsta;
    private TextView showdoc;
    StorageReference ProductImagesRef;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_fatora);
        newsta = getIntent().getExtras().getString("newstatic");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        statictext = findViewById(R.id.static_text);
        alidep = findViewById(R.id.show_staticfinal);
        print = findViewById(R.id.prit_image);
        print.setVisibility(View.INVISIBLE);
        main = findViewById(R.id.fatrora_screen);
        key = getIntent().getExtras().getString("key");
        alitext = findViewById(R.id.static_textfinal);
        alert = new ProgressDialog(this);
        alert.setMessage("يتم الأن تجهيز الفاتورة ...");
        showdoc = findViewById(R.id.fatoring_showing);
        //ProductImagesRef = FirebaseStorage.getInstance().getReference().child("DataImages");
        added = findViewById(R.id.imgs_added);
        phone= getIntent().getExtras().get("phone").toString();
        sentdep = findViewById(R.id.show_static);
        dep = getIntent().getExtras().getString("dep");
        donechangde = FirebaseDatabase.getInstance().getReference().child("Calls").child(key);
        added.setVisibility(View.VISIBLE);
        alidep.setVisibility(View.VISIBLE);
        alidep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog alid = new Dialog(fatora.this);
                alid.setContentView(R.layout.selectstaticdeper);
                alid.setTitle("الحالة");
                alid.show();
                final RadioGroup radioSexGroup = alid.findViewById(R.id.radioFatorafinal);
                final RadioButton radio1 =  alid.findViewById(R.id.done_seenfinal);
                final RadioButton radio2 =  alid.findViewById(R.id.done_donefinal);
                final RadioButton radio3 =  alid.findViewById(R.id.done_stopfinal);
                final Button cloali = alid.findViewById(R.id.close_changefinal);
                cloali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alid.dismiss();
                    }
                });
                final DatabaseReference sta = FirebaseDatabase.getInstance().getReference().child("Calls").child(key);
                sta.child("StorStatus").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getValue().equals("1")){
                           radio1.setVisibility(View.INVISIBLE);
                            radio2.setVisibility(View.VISIBLE);
                            radio3.setVisibility(View.VISIBLE);
                            added.setVisibility(View.VISIBLE);
                            print.setVisibility(View.VISIBLE);
                        }
                        else if(dataSnapshot.getValue().equals("0")){
                            radio2.setVisibility(View.INVISIBLE);
                            radio3.setVisibility(View.INVISIBLE);
                            radio1.setVisibility(View.VISIBLE);
                            added.setVisibility(View.VISIBLE);
                            print.setVisibility(View.INVISIBLE);

                        }
                        else if(dataSnapshot.getValue().equals("2")){
                            radio2.setChecked(true);
                            radio2.setVisibility(View.VISIBLE);
                            radio3.setVisibility(View.VISIBLE);
                            added.setVisibility(View.VISIBLE);
                            radio1.setVisibility(View.INVISIBLE);
                            print.setVisibility(View.VISIBLE);

                        }else if (dataSnapshot.getValue().equals("3")){
                            radio3.setChecked(true);

                            radio2.setVisibility(View.VISIBLE);
                            added.setVisibility(View.VISIBLE);
                            radio3.setVisibility(View.VISIBLE);
                            radio1.setVisibility(View.INVISIBLE);
                            print.setVisibility(View.VISIBLE);
                        }else if(dataSnapshot.getValue().equals("4")){
                            radio3.setChecked(true);
                            radio2.setVisibility(View.VISIBLE);
                            added.setVisibility(View.VISIBLE);
                            radio3.setVisibility(View.VISIBLE);
                            radio1.setVisibility(View.INVISIBLE);
                            print.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                radio1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference dred = FirebaseDatabase.getInstance().getReference().child("Calls");
                        dred.child(key).child("StorStatus").setValue("1");
                        radio2.setVisibility(View.VISIBLE);
                        radio3.setVisibility(View.VISIBLE);
                        radio1.setVisibility(View.INVISIBLE);
                        print.setVisibility(View.VISIBLE);
                        added.setVisibility(View.VISIBLE);
                    }
                });
                radio2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference dred = FirebaseDatabase.getInstance().getReference().child("Calls");
                        dred.child(key).child("StorStatus").setValue("2");
                        radio2.setVisibility(View.VISIBLE);
                        radio3.setVisibility(View.VISIBLE);
                        radio1.setVisibility(View.INVISIBLE);
                        added.setVisibility(View.VISIBLE);
                        print.setVisibility(View.VISIBLE);
                        //   DatabaseReference donefatoras = FirebaseDatabase.getInstance().getReference();
                        if(imgurl.size() > 0){
                            alert.show();
                            upimage();
                        }else {
                            Toast.makeText(fatora.this, "تم تجهيز الفاتورة بدون مرفقات", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                radio3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference dred = FirebaseDatabase.getInstance().getReference().child("Calls");
                        dred.child(key).child("StorStatus").setValue("3");
                        radio2.setVisibility(View.VISIBLE);
                        added.setVisibility(View.VISIBLE);
                        radio3.setVisibility(View.VISIBLE);
                        final Dialog dialogedit = new Dialog(fatora.this);
                        dialogedit.setTitle("طلب تعديل");
                        dialogedit.setContentView(R.layout.msg_edit);
                        dialogedit.show();
                        TextView cnoting = dialogedit.findViewById(R.id.closed_noteing);
                        final EditText noting = dialogedit.findViewById(R.id.edit_fatora_note);
                        final Button sendnoting = dialogedit.findViewById(R.id.btn_sent_note_to_admin);
                        sendnoting.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!TextUtils.isEmpty(noting.getText().toString())){
                                    sendnoting(noting.getText().toString());
                                    dialogedit.dismiss();
                                    Toast.makeText(fatora.this, "جاري الأرسال ..", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(fatora.this, "أكتب الملاحظات .!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        cnoting.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogedit.dismiss();
                            }
                        });
                        radio1.setVisibility(View.INVISIBLE);
                        print.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
        showdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showingimages();
            }
        });
        setting = findViewById(R.id.setting_fatora);
        if(newsta.equals("no")){
            setting.setVisibility(View.INVISIBLE);
            sentdep.setVisibility(View.INVISIBLE);
            alidep.setVisibility(View.VISIBLE);
            added.setVisibility(View.VISIBLE);

        }else if(newsta.equals("yes")){
            setting.setVisibility(View.VISIBLE);
            sentdep.setVisibility(View.VISIBLE);
            alidep.setVisibility(View.INVISIBLE);
            added.setVisibility(View.VISIBLE);
        }
        DatabaseReference checknoting = FirebaseDatabase.getInstance().getReference().child("Calls").child(key);
        checknoting.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("noting").exists()){
                    if(newsta.equals("yes")){
                        sentdep.setVisibility(View.VISIBLE);
                        statictext.setText("طلب تعديل");

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //-------------------
        DatabaseReference checkimages = FirebaseDatabase.getInstance().getReference().child("Calls");
        checkimages.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("images").exists()){
                    if(newsta.equals("yes")){
                        showdoc.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(imgurl.size() > 0){
            added.setText("المرفقات");
        }else {
            added.setText("المرفقات");
        }
        if(newsta.equals("yes")){
            print.setVisibility(View.VISIBLE);
        }
        added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgurl.size() > 0){
                    added.setText("المرفقات");
                }else {
                    added.setText("المرفقات");
                }
                if(imgurl.size() > 0){
                    dialogmain =   new Dialog(fatora.this);
                    dialogmain.setContentView(R.layout.listv_imges);
                    dialogmain.setTitle("اضافة مرفقات");
                    dialogmain.show();
                    TextView newadded = dialogmain.findViewById(R.id.addnew_images);
                    newadded.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent galleryIntent = new Intent();
                            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                            galleryIntent.setType("image/*");
                            startActivityForResult(galleryIntent, GalleryPick);
                        }
                    });
                    final ListView listpictor = dialogmain.findViewById(R.id.listv_imgesadded);
                    final ArrayAdapter<String> adapternew=new ArrayAdapter<String>(fatora.this,
                            android.R.layout.simple_list_item_1,
                            listItemsnew);
                    listpictor.setAdapter(adapternew);
                    ImageView closes = dialogmain.findViewById(R.id.exit_xxx);
                    closes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogmain.dismiss();
                        }
                    });
                    listpictor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            try {
                                final Dialog showing = new Dialog(fatora.this);
                                showing.setTitle("عرض صورة");
                                showing.setContentView(R.layout.showimage);
                                showing.show();
                                ImageView imageViews = showing.findViewById(R.id.showing_img);
                                ImageView closto = showing.findViewById(R.id.showing_close);
                                TextView deleted = showing.findViewById(R.id.delete_img);
                                deleted.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        imgurl.remove(position);
                                        showing.dismiss();
                                        listItemsnew.remove(position);
                                        dialogmain.dismiss();

                                    }
                                });
                                closto.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showing.dismiss();
                                    }
                                });
                                InputStream inputStream = getContentResolver().openInputStream(imgurl.get(position));
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                imageViews.setImageBitmap(bitmap);
                            }catch (Exception e){
                                Toast.makeText(fatora.this, "Erorr : " + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, GalleryPick);
                }

            }
        });




        //---------------

        try{

            DatabaseReference newfatorastatc = FirebaseDatabase.getInstance().getReference();
            newfatorastatc.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("Calls").child(key).exists()){
                        if(dataSnapshot.child("Calls").child(key).child("StorStatus").getValue().toString().equals("1")){
                            if(newsta.equals("no")){
                                statictext.setText("تم تجهيزها ؟");
                                added.setVisibility(View.VISIBLE);
                            }else if(newsta.equals("yes")){
                                statictext.setText("قيد الأرسال");
                                added.setVisibility(View.VISIBLE);
                            }
                        }else if(dataSnapshot.child("Calls").child(key).child("StorStatus").getValue().toString().equals("4")){
                            if(newsta.equals("yes")){
                                sentdep.setVisibility(View.VISIBLE);
                                statictext.setText("تم التعديل");
                                alidep.setVisibility(View.VISIBLE);

                            }else if(newsta.equals("no")){
                                sentdep.setVisibility(View.INVISIBLE);
                                alidep.setVisibility(View.VISIBLE);
                                alitext.setText("تم التعديل");
                            }
                        }

                        else if(dataSnapshot.child("Calls").child(key).child("StorStatus").getValue().toString().equals("3")){
                            if(newsta.equals("yes")){
                                statictext.setText("طلب تعديل");
                            }else if(newsta.equals("no")){
                                sentdep.setVisibility(View.VISIBLE);
                                alidep.setVisibility(View.INVISIBLE);
                                statictext.setText("قيد التعديل");

                            }
                        }

                        else if(dataSnapshot.child("Calls").child(key).child("StorStatus").getValue().toString().equals("2")){

                            if(newsta.equals("no")){
                                alitext.setText("تم تجهيزها");

                            }else if(newsta.equals("yes")){
                                statictext.setText("تم تجهيزها");
                            }
                        } else {
                            statictext.setText("أرسال");
                        }
                    }else {
                        statictext.setText("أرسال");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }catch (Exception e){
            return;
        }

                        sentdep.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(newsta.equals("yes")){
                                    if(statictext.getText().equals("أرسال")){
                                        HashMap<String , Object> ne = new HashMap<>();
                                        ne.put("number",key);
                                        ne.put("dep",newDepfor);
                                        ne.put("phone",phone);
                                        ne.put("user",username);
                                        ne.put("time",timef);
                                        ne.put("note",notyy);
                                        ne.put("saller",saller);
                                        ne.put("code",codene);
                                        ne.put("statics","0");
                                        ne.put("staticadmin","0");
                                        DatabaseReference dred = FirebaseDatabase.getInstance().getReference().child("Calls");
                                                dred.child(key).child("StorStatus").setValue("0");
                                        DatabaseReference newfatorastatc = FirebaseDatabase.getInstance().getReference();
                                        newfatorastatc.child("DeptF").child(dep).child(key).updateChildren(ne).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                DatabaseReference ch = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
                                                ch.child("Calls").child(key).child("static").setValue("1");
                                                Toast.makeText(fatora.this, "تم الأرسال الى أمين المخزن", Toast.LENGTH_SHORT).show();
                                                statictext.setText("قيد الأرسال ...");

                                            }
                                        });
                                    }else if(statictext.getText().equals("تم تجهيزها")){
                                        Toast.makeText(fatora.this, "تم تجهيز الفاتورة", Toast.LENGTH_SHORT).show();
                                    }else if(statictext.getText().equals("طلب تعديل")){


                                        final Dialog showmsg = new Dialog(fatora.this);
                                        showmsg.setContentView(R.layout.dialognoting);
                                        showmsg.setTitle("الملاحظات");
                                        showmsg.show();
                                        TextView closmsg = showmsg.findViewById(R.id.closed_noteing_done);
                                        closmsg.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showmsg.dismiss();
                                            }
                                        });
                                        final EditText notingfinal = showmsg.findViewById(R.id.edit_fatora_note_done);
                                        DatabaseReference getote = FirebaseDatabase.getInstance().getReference().child("Calls").child(key).child("noting");
                                        getote.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                notingfinal.setText(dataSnapshot.getValue().toString());
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        Button donenoting = showmsg.findViewById(R.id.btn_sent_note_to_deper_done);
                                        donenoting.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                               donechangde.child("staticadmin").setValue("4");
                                                DatabaseReference dred = FirebaseDatabase.getInstance().getReference().child("Calls");
                                                dred.child(key).child("StorStatus").setValue("4");
                                               showmsg.dismiss();
                                               statictext.setText("تم التعديل");

                                            }
                                        });

                                    }
                                }else if(newsta.equals("no")){

                                    if(statictext.getText().equals("طلب تعديل ")){
                                        Toast.makeText(fatora.this, "قيد العمل عليها ...!", Toast.LENGTH_SHORT).show();
                                    }else if(statictext.getText().equals("تم تجهيزها ؟")){
                                        added.setVisibility(View.INVISIBLE);
                                        DatabaseReference donefatora = FirebaseDatabase.getInstance().getReference();
                                        if(imgurl.size() > 0){
                                            alert.show();
                                            upimage();
                                        }else {
                                            Toast.makeText(fatora.this, "تم تجهيز الفاتورة بدون مرفقات", Toast.LENGTH_SHORT).show();
                                        }
                                        donefatora.child("Calls").child(key).child("StorStatus").setValue("2");
                                        DatabaseReference dred = FirebaseDatabase.getInstance().getReference().child("Calls");
                                        dred.child(key).child("StorStatus").setValue("2");
                                        statictext.setText("طلب تعديل ");
                                        //Toast.makeText(fatora.this, "تم تجهيز الفاتورة ..!", Toast.LENGTH_SHORT).show();

                                    }
                                }
            }
        });
        size = findViewById(R.id.totalsizefatora);
        back = findViewById(R.id.back_to_h);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsta.equals("yes")){
                    Intent intent = new Intent(fatora.this,Cpanal.class);
                    startActivity(intent);
                }else if(newsta.equals("no")) {
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
                stat = dataSnapshot.child("Sfatora").getValue().toString();
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
                                    ch.child("Calls").child(key).child("Sfatora").setValue("1");
                                    DatabaseReference cals = FirebaseDatabase.getInstance().getReference();
                                    cals.child("Users").child(phone).child("callsover").child(key).child("statics").setValue("1");
                                    reference.child("Sfatora").setValue("1");
                                    DatabaseReference ed = FirebaseDatabase.getInstance().getReference().child("callsover");
                                    ed.child(key).child("statics").setValue("1");

                                    dialog.dismiss();
                                }
                                if(radioSexButton.getText().equals("تجميد")){
                                    DatabaseReference ch = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
                                    ch.child("Calls").child(key).child("Sfatora").setValue("3");
                                    reference.child("Sfatora").setValue("3");
                                    DatabaseReference cals = FirebaseDatabase.getInstance().getReference();
                                    cals.child("Users").child(phone).child("callsover").child(key).child("statics").setValue("3");
                                    DatabaseReference ed = FirebaseDatabase.getInstance().getReference().child("callsover");
                                    ed.child(key).child("statics").setValue("3");
                                    dialog.dismiss();
                                }
                                if(radioSexButton.getText().equals("تم التجهيز")){
                                   DatabaseReference ch = FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
                                    ch.child("Calls").child(key).child("Sfatora").setValue("2");
                                    DatabaseReference cals = FirebaseDatabase.getInstance().getReference();
                                    cals.child("Users").child(phone).child("callsover").child(key).child("statics").setValue("2");
                                    reference.child("Sfatora").setValue("2");
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
                if(dataSnapshot.child("Sfatora").getValue().toString().equals("2")){
                    sta.setText("تم التجهيز");
                    sta.setTextColor(Color.rgb(0, 204, 0));
                    img_static.setImageResource(R.drawable.donns);
                }
                if(dataSnapshot.child("Sfatora").getValue().toString().equals("0")){
                    sta.setText("قيد الأرسال ...");
                    sta.setTextColor(Color.rgb(204, 255, 255));
                    img_static.setImageResource(R.drawable.sending);
                }
                if(dataSnapshot.child("Sfatora").getValue().toString().equals("3")){
                    sta.setText("تم تجميدها");
                    sta.setTextColor(Color.rgb(255, 0, 0));
                    img_static.setImageResource(R.drawable.stopp);
                }
                if(dataSnapshot.child("Sfatora").getValue().toString().equals("1")){
                    sta.setText("تم الأستلام");
                    sta.setTextColor(Color.rgb(0, 153, 255));
                    img_static.setImageResource(R.drawable.seen);
                }
                total.setText( dataSnapshot.child("Price").getValue().toString() + "$");
                number.setText( "رقم الفاتورة :  "+ dataSnapshot.child("number").getValue().toString() );
                TextView note = findViewById(R.id.note_fatora);
                note.setText("ملاحظات : " + dataSnapshot.child("note").getValue().toString());
                TextView time = findViewById(R.id.time_fatora);
                username = dataSnapshot.child("user").getValue().toString();
                codene = dataSnapshot.child("code").getValue().toString();
                saller = dataSnapshot.child("saller").getValue().toString();
                notyy = dataSnapshot.child("note").getValue().toString();
                timef = dataSnapshot.child("time").getValue().toString();
                time.setText(dataSnapshot.child("time").getValue().toString());
                newDepfor = dataSnapshot.child("dep").getValue().toString();
                size.setText("عدد عناصر الفاتورة : "  + String.valueOf(listView.getAdapter().getCount()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showingimages() {
        final Dialog shodi = new Dialog(fatora.this);
        shodi.setContentView(R.layout.list_images_fatoraing);
        shodi.setTitle("المرفقات");
        shodi.show();

        final ListView lisim = shodi.findViewById(R.id.faotring_list);
        DatabaseReference referencet = FirebaseDatabase.getInstance().getReference().child("Calls").child(key).child("images");

        final ArrayAdapter<String> aadapter = new ArrayAdapter<String>(shodi.getContext(), 17367043, keys) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);
                lisim.setBackgroundColor(001621);
                return view;
            }
        };
        referencet.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    aadapter.add("صورة رقم : " + String.valueOf(aadapter.getCount() + 1));
                    names.add(snapshot.child("url").getValue().toString());
                }
                aadapter.notifyDataSetChanged();
                lisim.setBackgroundColor(-1);
            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        final ImageView clos = shodi.findViewById(R.id.exit_fatoring);
        clos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aadapter.clear();
                shodi.dismiss();
            }
        });
        lisim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Dialog mdialog = new Dialog(fatora.this);
                mdialog.setTitle("الصورة");
                mdialog.setContentView(R.layout.show_fatora_av);
                mdialog.show();
               ImageView clp = mdialog.findViewById(R.id.close_fatoring);
               clp.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       mdialog.dismiss();
                   }
               });
                TextView saveimg = mdialog.findViewById(R.id.save_fatoring);
                final ImageView display = mdialog.findViewById(R.id.img_fatoring2);
              //  final DatabaseReference urldisplay = FirebaseDatabase.getInstance().getReference().child("Calls");


                Glide.with(fatora.this).load(names.get(position)).into(display);
                saveimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saving(display);
                    }
                });
            }
        });

        shodi.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                aadapter.clear();
            }
        });

        lisim.setAdapter(aadapter);







    }

    private void saving(ImageView display) {

        BitmapDrawable draw = (BitmapDrawable) display.getDrawable();
        Bitmap bitmap = draw.getBitmap();

        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/MainProgram");
        dir.mkdirs();
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        try {
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(fatora.this, "تم حفظ الصورة", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(outFile));
        sendBroadcast(intent);

    }

    private void upimage() {
        StorageReference filepath = FirebaseStorage.getInstance().getReference().child("gpic");

        while (up < imgurl.size()){

            Random random = new Random();

// generate a random integer from 0 to 899, then add 100
            int x = random.nextInt(900) + 100;
            filepath.child(imgurl.get(k).getLastPathSegment() + String.valueOf(x)).putFile(imgurl.get(k)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadURL = taskSnapshot.getDownloadUrl();
                    SaveonData(downloadURL );
                   //Toast.makeText(fatora.this, downloadURL.toString(), Toast.LENGTH_SHORT).show();

                }
            });
            up++;
            k++;

        }

    }

    private void SaveonData(Uri uri) {

        DatabaseReference newimgs =  FirebaseDatabase.getInstance().getReference().child("Calls").child(key);
        HashMap<String , Object> ups = new HashMap<>();
        ups.put("url",String.valueOf(uri));
        newimgs.child("images").push().setValue(ups).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(fatora.this, "تم تجهيز الفاتورة بنجاح ..", Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            if(imgurl.size() > 0 ){
                ImageUri = data.getData();
                imgurl.add(ImageUri);
                final File f = new File(ImageUri.getPath());
                listItemsnew.add(f.getName());
                dialogmain.dismiss();
            }else {
                final Dialog dialog = new Dialog(fatora.this);
                dialog.setContentView(R.layout.listv_imges);
                dialog.setTitle("اضافة مرفقات");
                dialog.show();
                TextView savedialog = dialog.findViewById(R.id.save_imgsnew_admin);

                TextView newadded = dialog.findViewById(R.id.addnew_images);
                newadded.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent();
                        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, GalleryPick);
                    }
                });
                ImageUri = data.getData();
                imgurl.add(ImageUri);
                final File f = new File(ImageUri.getPath());
                listItemsnew.add(f.getName());
                final ListView listpictor = dialog.findViewById(R.id.listv_imgesadded);
                final ArrayAdapter<String> adapternew=new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        listItemsnew);
                listpictor.setAdapter(adapternew);
                dialog.dismiss();

                if(imgurl.size() > 0){
                    added.setText("المرفقات");
                }else {
                    added.setText("المرفقات");
                }
                ImageView closes = dialog.findViewById(R.id.exit_xxx);
                closes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if(imgurl.size() > 0){
                            added.setText("المرفقات");
                        }else {
                            added.setText("المرفقات");
                        }
                    }
                });
                listpictor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        try {
                            if(imgurl.size() > 0){
                                added.setText("المرفقات");
                            }else {
                                added.setText("المرفقات");
                            }
                            final Dialog showing = new Dialog(fatora.this);
                            showing.setTitle("عرض صورة");
                            showing.setContentView(R.layout.showimage);
                            showing.show();
                            ImageView imageViews = showing.findViewById(R.id.showing_img);
                            ImageView closto = showing.findViewById(R.id.showing_close);
                            TextView deleted = showing.findViewById(R.id.delete_img);
                            deleted.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imgurl.remove(position);
                                    showing.dismiss();
                                    listItemsnew.remove(position);
                                    dialog.dismiss();

                                }
                            });
                            closto.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showing.dismiss();
                                }
                            });
                            InputStream inputStream = getContentResolver().openInputStream(imgurl.get(position));
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            imageViews.setImageBitmap(bitmap);
                        }catch (Exception e){
                            Toast.makeText(fatora.this, "Erorr : " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            }


        }

    @Override
    protected void onStart() {
        super.onStart();

    }
    private void  sendnoting(String text){
        DatabaseReference Calses = FirebaseDatabase.getInstance().getReference().child("Calls").child(key);
        Calses.child("noting").setValue(text).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(fatora.this, "تم ارسال الملاحظات ...", Toast.LENGTH_SHORT).show();
                alitext.setText("قيد التعديل");
            }
        });
    }
}
