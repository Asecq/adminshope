package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rey.material.widget.CheckBox;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EditPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private String CategoryName, Description, Price, Pname, saveCurrentDate, saveCurrentTime,Pcode , Pmodel  , count , Price1,Price2;
    private Button AddNewProductButton , btnMarka ,btndep;
    private ImageView InputProductImage;
    private EditText InputProductName,  InputProductPrice,InputModel,InputCode ,InputPrice3 , Inputdes , Inputcount , InputLocal , InputPrice1,InputPrice2;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private Boolean doneali;
    private Spinner spinner;
    private Boolean res = true;
    private Boolean checkname;
    private Button copy , paste;
    private Boolean checkcode;
    private String productRandomKey, downloadImageUrl;
    private String oldUrl;
    private CheckBox checkpost;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    private ArrayList<String> keys = new ArrayList<>();
    public ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> keysdep = new ArrayList<>();
    private  int spinnerPosition;
    public ArrayList<String> namesdep = new ArrayList<>();
    private String depOld;

    private Dialog dialog;
    private Boolean onMain;
    private String data_price1,data_price2,data_price3 , data_price4,maintype , marka;
    private CheckBox  checkbox1 ,checkbox2,checkbox3 , checkbox4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        btnMarka = findViewById(R.id.post_markaEdit);
        checkpost = findViewById(R.id.check_post);
        copy = findViewById(R.id.copy_edit);
        paste = findViewById(R.id.paste_edit);
        checkname = false;
        checkcode = false;
        data_price1 = "";
        data_price2 = "";
        data_price3 = "";
        data_price4 = "";
        Description = "";
        Pmodel = "";
        count ="";
        marka = "";
        //------------------- include
        CategoryName = getIntent().getExtras().getString("department");
        depOld = getIntent().getExtras().getString("department");
        btndep = findViewById(R.id.post_depEdit);
        final String nameold , priceold , modelold,desold,codeold,price2old,mainold,price4old,price3old,imageold,countold;
        nameold = getIntent().getExtras().getString("namepost");
        priceold = getIntent().getExtras().getString("pricepost1");
        price2old = getIntent().getExtras().getString("pricepost2");
        price3old = getIntent().getExtras().getString("pricepost3");
        price4old = getIntent().getExtras().getString("pricepost4");
        modelold = getIntent().getExtras().getString("modelpost");
        codeold = getIntent().getExtras().getString("codepost");
        marka = getIntent().getExtras().getString("marka");
        productRandomKey = getIntent().getExtras().getString("idpost");
        oldUrl = getIntent().getExtras().getString("imagepost");

        countold = getIntent().getExtras().getString("countpost");
        mainold = getIntent().getExtras().getString("maintype");
        desold = getIntent().getExtras().getString("despost");
        maintype = mainold;
        if(TextUtils.isEmpty(marka)){
            btnMarka.setText("اضافة ماركة");
        }else {
            btnMarka.setText(marka);

        }
        if(TextUtils.isEmpty(CategoryName)){
            btndep.setText("اضافة قسم");
        }else {
            btndep.setText(CategoryName);

        }
        //---------------------------
        //<dep>
        btndep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog =  new Dialog(EditPost.this);
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
                            ArrayList access$000 = EditPost.this.names;
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
                                keys.clear();
                                names.clear();
                                btndep.setText(myKey);
                                CategoryName = myKey;
                            }
                        });
                    }


                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        keys2.clear();
                        keys.clear();
                        names.clear();
                        btndep.setText("اضافة قسم");
                        CategoryName = "";
                    }
                });
            }
        });

        //---------------------------
        //<marka>
        btnMarka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog =  new Dialog(EditPost.this);
                dialog.setContentView(R.layout.markesss);
                dialog.setTitle("الماركات");
                dialog.show();
                TextView close = dialog.findViewById(R.id.close_marke);

                final ListView mListView = dialog.findViewById(R.id.listview_marka);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Marka");

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
                            ArrayList access$000 = EditPost.this.names;
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
                                keys.clear();
                                names.clear();
                                btnMarka.setText(myKey);
                                marka = myKey;
                            }
                        });
                    }


                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        keys2.clear();
                        keys.clear();
                        names.clear();
                        btnMarka.setText("اضافة ماركة");
                        marka = "";
                    }
                });
            }
        });

        //-----------------



        checkpost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkpost.isChecked()){
                    onMain = true;
                }else {
                    onMain = false;
                }
            }
        });

        //-------------------
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference();
        checkbox1 = findViewById(R.id.check_price1Edit);
        checkbox2  = findViewById(R.id.check_price2Edit);
        checkbox3 = findViewById(R.id.check_price3Edit);
        spinner = findViewById(R.id.spinner2Edit);
        checkbox4 = findViewById(R.id.check_price4Edit);
        AddNewProductButton = (Button) findViewById(R.id.btn_add_proEdit);
        InputProductImage = (ImageView) findViewById(R.id.image_proEdit);
        InputProductName = (EditText) findViewById(R.id.text_proEdit);
        InputProductPrice = (EditText) findViewById(R.id.text_priceEdit);
        InputPrice1 = (EditText)findViewById(R.id.text_price2Edit);
        InputPrice2 = (EditText)findViewById(R.id.text_price3Edit);
        InputPrice3 = (EditText)findViewById(R.id.text_price4Edit);
        InputCode = (EditText)findViewById(R.id.text_codeEdit);
        InputModel = (EditText)findViewById(R.id.text_modelEdit);
        Inputcount = (EditText)findViewById(R.id.text_count1Edit) ;
        Inputdes = (EditText)findViewById(R.id.text_desEdit);
        loadingBar = new ProgressDialog(this);

        //---------
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.adapter_price, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinnerPosition = adapter.getPosition(mainold);
        spinner.setSelection(spinnerPosition);
        spinner.setOnItemSelectedListener(EditPost.this);

        //
        if(TextUtils.isEmpty(priceold)){
            checkbox1.setChecked(false);
        }else {
            checkbox1.setChecked(true);
        }
        if(TextUtils.isEmpty(price2old)){
            checkbox2.setChecked(false);
        }else {
            checkbox2.setChecked(true);
        }
        if(TextUtils.isEmpty(price3old)){
            checkbox3.setChecked(false);
        }else {
            checkbox3.setChecked(true);
        }
        if(TextUtils.isEmpty(price4old)){
            checkbox4.setChecked(false);
        }else {
            checkbox4.setChecked(true);
        }
        //------------
        InputProductName.setText(nameold);
        InputCode.setText(codeold);
        Inputcount.setText(countold);
        Inputdes.setText(desold);
        InputModel.setText(modelold);
        InputProductPrice.setText(priceold);
        InputPrice1.setText(price2old);
        InputPrice2.setText(price3old);
        InputPrice3.setText(price4old);
        Picasso.get().load(oldUrl).into(InputProductImage);


        //--------------------------------------------
        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkbox1.isChecked()){
                    InputProductPrice.setFocusableInTouchMode(true);

                }else {
                    InputProductPrice.setFocusable(false);
                    InputProductPrice.setText(null);
                }
            }
        });
        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkbox2.isChecked()){
                    InputPrice1.setFocusableInTouchMode(true);

                }else {
                    InputPrice1.setFocusable(false);
                    InputPrice1.setText(null);
                }
            }
        });
        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkbox3.isChecked()){
                    InputPrice2.setFocusableInTouchMode(true);

                }else {
                    InputPrice2.setFocusable(false);
                    InputPrice2.setText(null);
                }
            }
        });
        checkbox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkbox4.isChecked()){
                    InputPrice3.setFocusableInTouchMode(true);

                }else {
                    InputPrice3.setFocusable(false);
                    InputPrice3.setText(null);
                }
            }
        });
        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        InputProductName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkname = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        InputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkcode = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(TextUtils.isEmpty(CategoryName)){
                    Toast.makeText(EditPost.this, "لم تحدد قسم", Toast.LENGTH_SHORT).show();
                }else {
                    ValidateProductData();
                }
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference copee = FirebaseDatabase.getInstance().getReference().child("copy");
                HashMap<String , Object> cop = new HashMap<>();
                cop.put("name" , InputProductName.getText().toString());
                cop.put("p1" , InputProductPrice.getText().toString());
                cop.put("p2" , InputPrice1.getText().toString());
                cop.put("marka",marka);
                cop.put("p3" , InputPrice2.getText().toString());
                cop.put("p4" , InputPrice3.getText().toString());
                cop.put("main" , spinner.getSelectedItem().toString());
                cop.put("code" , InputCode.getText().toString());
                cop.put("made" , InputModel.getText().toString());
                cop.put("count" , Inputcount.getText().toString());
                cop.put("des" , Inputdes.getText().toString());
                copee.updateChildren(cop).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditPost.this, "تم نسخ البيانات", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference pess = FirebaseDatabase.getInstance().getReference().child("copy");
                pess.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        InputProductName.setText(dataSnapshot.child("name").getValue().toString());
                        Inputcount.setText(dataSnapshot.child("count").getValue().toString());
                        InputModel.setText(dataSnapshot.child("made").getValue().toString());
                        InputCode.setText(dataSnapshot.child("code").getValue().toString());
                        Inputdes.setText(dataSnapshot.child("des").getValue().toString());
                        maintype = dataSnapshot.child("main").getValue().toString();
                        if (maintype != null) {
                            spinner.setSelection(spinnerPosition);
                        }
                        if(!TextUtils.isEmpty(dataSnapshot.child("p1").getValue().toString())){
                            checkbox1.setChecked(true);
                            InputProductPrice.setText(dataSnapshot.child("p1").getValue().toString());
                        }else {
                            checkbox1.setChecked(false);
                            InputProductPrice.setFocusable(false);
                        }
                        if(!TextUtils.isEmpty(dataSnapshot.child("p2").getValue().toString())){
                            checkbox2.setChecked(true);
                            InputPrice1.setText(dataSnapshot.child("p2").getValue().toString());
                        }
                        if(!TextUtils.isEmpty(dataSnapshot.child("p4").getValue().toString())){
                            checkbox4.setChecked(true);
                            InputPrice3.setText(dataSnapshot.child("p4").getValue().toString());
                        }
                        if(!TextUtils.isEmpty(dataSnapshot.child("p3").getValue().toString())){
                            checkbox3.setChecked(true);
                            InputPrice2.setText(dataSnapshot.child("p3").getValue().toString());
                        }
                        if(!TextUtils.isEmpty(dataSnapshot.child("marka").getValue().toString())){
                            marka = dataSnapshot.child("marka").getValue().toString();
                            btnMarka.setText(marka);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
            res = false;
            StoreProductInformation();

        }
    }

    private void ValidateProductData() {
        Pname = InputProductName.getText().toString();
        Pcode = InputCode.getText().toString();
        Pmodel = InputModel.getText().toString();
        Description = Inputdes.getText().toString();
        count = Inputcount.getText().toString();
        if(checkbox1.isChecked()){
            data_price1 = InputProductPrice.getText().toString().trim();
        }
        if(checkbox2.isChecked()) {
            data_price2 = InputPrice1.getText().toString().trim();
        }
        if(checkbox3.isChecked()){
            data_price3 = InputPrice2.getText().toString().trim();
        }
        if(checkbox4.isChecked()){
            data_price4 = InputPrice3.getText().toString().trim();
        }

         if(TextUtils.isEmpty(maintype)){
            Toast.makeText(this, "تأكد من الوحدة الأفتراضية", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(InputProductPrice.getText().toString()) && TextUtils.isEmpty(InputPrice1.getText().toString()) && TextUtils.isEmpty(InputPrice2.getText().toString() ) && TextUtils.isEmpty(InputPrice3.getText().toString() )){
            Toast.makeText(this, "ادخل سعر واحد على الأقل", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(InputProductName.getText().toString()))
        {
            Toast.makeText(this, "اكتب اسم المنتج", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(InputCode.getText().toString())){
            Toast.makeText(this, "ادخل الرمز", Toast.LENGTH_SHORT).show();
        }
        else {
            if(checkname == false || checkcode == false){
                SaveProductInfoToDatabase();
            }else{
                doneali = true;
                checkdata();
            }

         }
        }

    private void checkdata() {
        if(doneali){
            final DatabaseReference checkname = FirebaseDatabase.getInstance().getReference().child("NamesPosts");
            checkname.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.child(Pname).exists()){
                        DatabaseReference checkcode = FirebaseDatabase.getInstance().getReference().child("CodePosts");
                        checkcode.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.child(Pcode).exists()){
                                    SaveProductInfoToDatabase();
                                    doneali = false;
                                }else {
                                    if(doneali){
                                        Toast.makeText(EditPost.this, "الرمز موجود ...", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }else {
                        if(doneali){
                            Toast.makeText(EditPost.this, "اسم المنتج موجود ..", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

}


    private void StoreProductInformation()
    {
        loadingBar.setTitle("حفظ التعديلات");
        loadingBar.setMessage("جاري حفظ التعديلات");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());



        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(EditPost.this, "Erorr : " + message, Toast.LENGTH_LONG).show();
                loadingBar.dismiss();
                finish();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(EditPost.this, "تم رفع الصورة بنجاح...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(EditPost.this, "تم الحصول على رابط الصورة الجديدة..", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        });
    }
    private void SaveProductInfoToDatabase()
    {
        doneali = false;

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        final HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("pdata", saveCurrentDate);
        productMap.put("ptime", saveCurrentTime);
        if(res == false){
            productMap.put("image",downloadImageUrl);
        }else {
            productMap.put("image",oldUrl);
        }
        productMap.put("marke",marka);
        if(CategoryName.equals(depOld)){
            productMap.put("category", depOld);
        }else {
            productMap.put("category", CategoryName);
        }
        productMap.put("category", CategoryName);
        productMap.put("pprice", data_price1);
        productMap.put("pname", Pname);
        productMap.put("Pcode",Pcode);
        productMap.put("Pmodel",Pmodel);
        productMap.put("Descrption",Description);
        productMap.put("count" , count);
        productMap.put("testone",data_price2);
        productMap.put("testtow" , data_price3);
        productMap.put("testthree",data_price4);
        productMap.put("maimtype",maintype);
        productMap.put("IDpost",productRandomKey);

        HashMap<String, Object> Allposts = new HashMap<>();
        Allposts.put("pid", productRandomKey);
        Allposts.put("pdata", saveCurrentDate);
        Allposts.put("ptime", saveCurrentTime);
        if(res == false){
            Allposts.put("image",downloadImageUrl);
        }else {
            Allposts.put("image",oldUrl);
        }
        if(CategoryName.equals(depOld)){
            Allposts.put("category", depOld);
        }else {
            Allposts.put("category", CategoryName);
        }
        Allposts.put("pprice", data_price1);
        Allposts.put("pname", Pname);
        Allposts.put("Pcode",Pcode);
        Allposts.put("Pmodel",Pmodel);
        Allposts.put("Descrption",Description);
        Allposts.put("count" , count);
        Allposts.put("testone",data_price2);
        Allposts.put("testtow" , data_price3);
        Allposts.put("testthree",data_price4);
        Allposts.put("marke",marka);
        Allposts.put("maimtype",maintype);
        Allposts.put("IDpost",productRandomKey);
        DatabaseReference checkname = FirebaseDatabase.getInstance().getReference().child("NamesPosts");
        DatabaseReference checkcode = FirebaseDatabase.getInstance().getReference().child("CodePosts");
        HashMap<String,Object> chedked = new HashMap<>();
        chedked.put("name",Pname);
        checkcode.child(Pcode).updateChildren(chedked);
        checkname.child(Pname).updateChildren(chedked);

                HashMap<String, Object> productMaphome = new HashMap<>();
                productMaphome.put("pid", productRandomKey);
                productMaphome.put("pdata", saveCurrentDate);
                productMaphome.put("ptime", saveCurrentTime);
        if(res == false){
            productMaphome.put("image",downloadImageUrl);
        }else {
            productMaphome.put("image",oldUrl);
        }
        if(CategoryName.equals(depOld)){
            productMaphome.put("category", depOld);
        }else {
            productMaphome.put("category", CategoryName);
        }
                productMaphome.put("pprice", data_price1);
                productMaphome.put("pname", Pname);
                productMaphome.put("Pcode",Pcode);
                productMaphome.put("Pmodel",Pmodel);
                productMaphome.put("Descrption",Description);
                productMaphome.put("count" , count);
                productMaphome.put("testone",data_price2);
                productMaphome.put("testtow" , data_price3);
                productMaphome.put("testthree",data_price4);
                productMaphome.put("marke",marka);

                productMaphome.put("maimtype",maintype);
                productMaphome.put("IDpost",productRandomKey);
                if(onMain == true){
                    ProductsRef.child("MainPage").child(productRandomKey).updateChildren(productMaphome);
                }else{
                                ProductsRef.child("MainPage").child(productRandomKey).removeValue();

                }
                if(depOld.equals(CategoryName)){
                    ProductsRef.child("Protucts").child(CategoryName).child(productRandomKey).updateChildren(productMap);
                }else {
                    final DatabaseReference depsss = FirebaseDatabase.getInstance().getReference().child("Protucts").child(CategoryName);
                    depsss.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                         if(dataSnapshot.child("test").exists()){
                             depsss.child("test").removeValue();
                             ProductsRef.child("Protucts").child(depOld).child(productRandomKey).removeValue();
                             ProductsRef.child("Protucts").child(CategoryName).child(productRandomKey).updateChildren(productMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                     Toast.makeText(EditPost.this, "تم تغيير القسم بنجاح", Toast.LENGTH_SHORT).show();
                                 }
                             });
                         }else {
                             ProductsRef.child("Protucts").child(depOld).child(productRandomKey).removeValue();
                             ProductsRef.child("Protucts").child(CategoryName).child(productRandomKey).updateChildren(productMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                     Toast.makeText(EditPost.this, "تم تغيير القسم بنجاح", Toast.LENGTH_SHORT).show();
                                 }
                             });
                         }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
        ProductsRef.child("AllPost").child(productRandomKey).updateChildren(Allposts).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    doneali = false;
                    Intent intent = new Intent(EditPost.this, Cpanal.class);
                    startActivity(intent);
                    finish();

                    loadingBar.dismiss();
                    Toast.makeText(EditPost.this, "تم الحفظ ..", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(EditPost.this, "خطأ: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemIdAtPosition(position) == 0){
            if(checkbox1.isChecked()){
                maintype = "قطعة";
            }else {
                Toast.makeText(this, "لم تحدد سعر لهذه الوحدة", Toast.LENGTH_SHORT).show();
                maintype = "";
            }
        }
        else if(parent.getItemIdAtPosition(position) == 1){
            if(checkbox2.isChecked()){
                maintype = "درزن";
            }else {
                Toast.makeText(this, "لم تحدد سعر لهذه الوحدة", Toast.LENGTH_SHORT).show();
                maintype = "";
            }
        } else if(parent.getItemIdAtPosition(position) == 2){
            if(checkbox4.isChecked()){
                maintype = "سيت";
            }else {
                Toast.makeText(this, "لم تحدد سعر لهذه الوحدة", Toast.LENGTH_SHORT).show();
                maintype = "";
            }

        } else if(parent.getItemIdAtPosition(position) == 3){
            if(checkbox3.isChecked()){
                maintype = "كارتونة";
            }else {
                Toast.makeText(this, "لم تحدد سعر لهذه الوحدة", Toast.LENGTH_SHORT).show();
                maintype = "";
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onStart() {
        final DatabaseReference check = FirebaseDatabase.getInstance().getReference().child("MainPage");
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(productRandomKey).exists()){
                    checkpost.setChecked(true);
                    onMain = true;
                }else {
                    checkpost.setChecked(false);
                    onMain = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        super.onStart();
    }

    @Override
    protected void onRestart() {
        checkname = false;
        checkcode = false;
        final DatabaseReference check = FirebaseDatabase.getInstance().getReference().child("MainPage");
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(productRandomKey).exists()){
                    checkpost.setChecked(true);
                    onMain = true;
                }else {
                    checkpost.setChecked(false);
                    onMain = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        super.onRestart();

    }
}
