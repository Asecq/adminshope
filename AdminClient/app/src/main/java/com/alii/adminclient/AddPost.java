package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AddPost extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private String CategoryName, Description, Price, Pname, saveCurrentDate, saveCurrentTime,Pcode , Pmodel , count , Price1,Price2;
    private Button AddNewProductButton , addMarka , changeDep;
    private ImageView InputProductImage;
    private EditText InputProductName,  InputProductPrice,InputModel,InputCode , Inputdes , Inputcount , InputPrice1,InputPrice2 , InputPrice4;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    ///---------------
    ArrayAdapter<String> arrayAdapter;
    private DatabaseReference databaseReference;
    private ArrayList<String> keys = new ArrayList<>();
    public ArrayList<String> names = new ArrayList<>();
    //--------------------------
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    private String maintype = "قطعة";
    private Spinner spinner;
    private String nameMarka ;
    private Button copy , paste;
    Dialog dialog ;
    private String data_price1,data_price2,data_price3 , data_price4;
    private CheckBox checkBox , checkbox1 ,checkbox2,checkbox3 , checkBox4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        spinner = findViewById(R.id.spinner2);
        addMarka = findViewById(R.id.post_marka);
        nameMarka = "";
        copy = findViewById(R.id.copy_data);
        paste = findViewById(R.id.paste_data);
        changeDep = findViewById(R.id.post_dep_add);
        changeDep.setText("اضافة قسم");
        CategoryName = "";

        //--------------------------------

        changeDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog =  new Dialog(AddPost.this);
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
                            ArrayList access$000 = AddPost.this.names;
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
                                changeDep.setText(myKey);
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
                        changeDep.setText("اضافة قسم");
                        CategoryName = "";
                    }
                });
            }
        });


        //============================




        addMarka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog =  new Dialog(AddPost.this);
                dialog.setContentView(R.layout.markesss);
                dialog.setTitle("الماركات");
                dialog.show();
                TextView close = dialog.findViewById(R.id.close_marke);

                final ListView mListView = dialog.findViewById(R.id.listview_marka);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Marka");

                final ArrayAdapter<String> aadapter = new ArrayAdapter<String>(dialog.getContext(), 17367043, keys) {
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
                mListView.setAdapter(aadapter);
               final List<String> keys2 = new ArrayList<>();
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ArrayList access$000 = AddPost.this.names;
                            StringBuilder sb = new StringBuilder();
                            access$000.add(sb.toString());
                            keys2.add(snapshot.getKey());
                        }
                        aadapter.addAll(keys2);
                        aadapter.notifyDataSetChanged();
                        mListView.setBackgroundColor(-1);
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String myKey = (String) keys2.get(i);
                                //  putc.putExtra("department", myKey);
                                dialog.dismiss();
                                keys2.clear();
                                keys.clear();
                                names.clear();
                                addMarka.setText(myKey);
                                nameMarka = myKey;
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
                        addMarka.setText("اضافة ماركة");
                        nameMarka = "";
                    }
                });
            }
        });




        //========================
        final ArrayAdapter<CharSequence> sadapter = ArrayAdapter.createFromResource(this,R.array.adapter_price, R.layout.support_simple_spinner_dropdown_item);
        sadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(sadapter);
        spinner.setOnItemSelectedListener(AddPost.this);

        //--
        data_price1 = "" ;
        data_price2 = "";
        data_price3 = "";
        count = "";
        data_price4 = "";
        //<Spinner item>====================================================
        //-------------------------
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference();
        checkBox = findViewById(R.id.add_to_home);
        checkbox1 = findViewById(R.id.check_price1);
        checkbox2  = findViewById(R.id.check_price2);
        checkbox3 = findViewById(R.id.check_price3);
        checkBox4 = findViewById(R.id.check_price4);
        AddNewProductButton = (Button) findViewById(R.id.btn_add_pro);
        InputProductImage = (ImageView) findViewById(R.id.image_pro);
        InputProductName = (EditText) findViewById(R.id.text_pro);
        InputProductPrice = (EditText) findViewById(R.id.text_price);
        InputPrice1 = (EditText)findViewById(R.id.text_price2);
        InputPrice2 = (EditText)findViewById(R.id.text_price3);
        InputCode = (EditText)findViewById(R.id.text_code);
        InputModel = (EditText)findViewById(R.id.text_model);
        Inputcount = (EditText)findViewById(R.id.text_count1) ;
        Inputdes = (EditText)findViewById(R.id.text_des);
        InputPrice4 = findViewById(R.id.text_price4);
        loadingBar = new ProgressDialog(this);
        InputPrice1.setFocusable(false);
        InputPrice2.setFocusable(false);
        InputPrice4.setFocusable(false);

        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkbox1.isChecked()){
                    InputProductPrice.setFocusableInTouchMode(true);

                }else {
                    InputProductPrice.setFocusable(false);
                    InputProductPrice.setText("");
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
                    InputPrice1.setText("");
                }
            }
        });
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox4.isChecked()){
                    InputPrice4.setFocusableInTouchMode(true);

                }else {
                    InputPrice4.setFocusable(false);
                    InputPrice4.setText("");
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
                    InputPrice2.setText("");
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


        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();
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
                cop.put("marka",nameMarka);
                cop.put("p3" , InputPrice2.getText().toString());
                cop.put("p4" , InputPrice4.getText().toString());
                cop.put("main" , spinner.getSelectedItem().toString());
                cop.put("code" , InputCode.getText().toString());
                cop.put("made" , InputModel.getText().toString());
                cop.put("count" , Inputcount.getText().toString());
                cop.put("des" , Inputdes.getText().toString());
                copee.updateChildren(cop).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddPost.this, "تم نسخ البيانات", Toast.LENGTH_SHORT).show();

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
                            int spinnerPosition = sadapter.getPosition(maintype);
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
                            checkBox4.setChecked(true);
                            InputPrice4.setText(dataSnapshot.child("p4").getValue().toString());
                        }
                        if(!TextUtils.isEmpty(dataSnapshot.child("p3").getValue().toString())){
                            checkbox3.setChecked(true);
                            InputPrice2.setText(dataSnapshot.child("p3").getValue().toString());
                        }
                        if(!TextUtils.isEmpty(dataSnapshot.child("marka").getValue().toString())){
                            nameMarka = dataSnapshot.child("marka").getValue().toString();
                            addMarka.setText(nameMarka);
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
        }
    }


    private void ValidateProductData()
    {

        Pname = InputProductName.getText().toString();
        Pcode = InputCode.getText().toString();
        Pmodel = InputModel.getText().toString();
        Description = Inputdes.getText().toString();
        //location = InputLocal.getText().toString();
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
        if(checkBox4.isChecked()){
            data_price4 = InputPrice4.getText().toString().trim();
        }


        if (ImageUri == null)
        {

            Toast.makeText(this, "ارفق صورة", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(maintype)){
            Toast.makeText(this, "تأكد من أختيار الوحدة الأفتراضية", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(InputProductPrice.getText().toString()) && TextUtils.isEmpty(InputPrice1.getText().toString()) && TextUtils.isEmpty(InputPrice2.getText().toString()) && TextUtils.isEmpty(InputPrice4.getText().toString())){
            Toast.makeText(this, "ادخل سعر واحد على الأقل", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(CategoryName)){
            Toast.makeText(this, "أختار قسم", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "اكتب اسم المنتج", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Pcode)){
            Toast.makeText(this, "ادخل الرمز", Toast.LENGTH_SHORT).show();
        }
        else
        {
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
                                   StoreProductInformation();
                               }else {
                                   Toast.makeText(AddPost.this, "الرمز موجود ...", Toast.LENGTH_SHORT).show();
                               }
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });

                    }else {
                        Toast.makeText(AddPost.this, "اسم المنتج موجود ..", Toast.LENGTH_SHORT).show();
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
        loadingBar.setTitle("اضافة منشور جديد");
        loadingBar.setMessage("جاري اضافة المنشور.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AddPost.this, "Erorr : " + message, Toast.LENGTH_LONG).show();
                loadingBar.dismiss();
                finish();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AddPost.this, "تم رفع صورة المنشور بنجاح...", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(AddPost.this, "تم الحصول على رابط صورة المنشور...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }



    private void SaveProductInfoToDatabase()
    {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        final HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("pdata", saveCurrentDate);
        productMap.put("ptime", saveCurrentTime);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", CategoryName);
        productMap.put("pprice", data_price1);
        productMap.put("pname", Pname);
        productMap.put("Pcode",Pcode);
        productMap.put("Pmodel",Pmodel);
        productMap.put("Descrption",Description);
        productMap.put("count" , count);
        productMap.put("marke",nameMarka);
        productMap.put("testone",data_price2);
        productMap.put("testtow" , data_price3);
        productMap.put("testthree",data_price4);
        productMap.put("maimtype",maintype);
        productMap.put("IDpost",productRandomKey);

        HashMap<String, Object> Allposts = new HashMap<>();
        Allposts.put("pid", productRandomKey);
        Allposts.put("pdata", saveCurrentDate);
        Allposts.put("ptime", saveCurrentTime);
        Allposts.put("image", downloadImageUrl);
        Allposts.put("category", CategoryName);
        Allposts.put("pprice", data_price1);
        Allposts.put("pname", Pname);
        Allposts.put("Pcode",Pcode);
        Allposts.put("Pmodel",Pmodel);
        Allposts.put("marke",nameMarka);
        Allposts.put("Descrption",Description);
        Allposts.put("maimtype",maintype);
        Allposts.put("count" , count);
        Allposts.put("testone",data_price2);
        Allposts.put("testtow" , data_price3);
        Allposts.put("testthree",data_price4);
        Allposts.put("IDpost",productRandomKey);
        ProductsRef.child("AllPost").child(productRandomKey).updateChildren(Allposts);

        if(checkBox.isChecked()) {
            if(CategoryName != "الصفحة الرئيسية") {
                HashMap<String, Object> productMaphome = new HashMap<>();
                productMaphome.put("pid", productRandomKey);
                productMaphome.put("pdata", saveCurrentDate);
                productMaphome.put("ptime", saveCurrentTime);
                productMaphome.put("image", downloadImageUrl);
                productMaphome.put("category", CategoryName);
                productMaphome.put("pprice", data_price1);
                productMaphome.put("pname", Pname);
                productMaphome.put("marke",nameMarka);
                productMaphome.put("Descrption",Description);
                productMaphome.put("Pcode",Pcode);
                productMaphome.put("Pmodel",Pmodel);
                productMaphome.put("count" , count);
                productMaphome.put("testthree",data_price4);
                productMaphome.put("testone",data_price2);
                productMaphome.put("maimtype",maintype);
                productMaphome.put("testtow" , data_price3);
                productMaphome.put("timestamp",ts);
                productMaphome.put("IDpost",productRandomKey);
                ProductsRef.child("MainPage").child(productRandomKey).updateChildren(productMaphome);
            }

        }
        DatabaseReference checkname = FirebaseDatabase.getInstance().getReference().child("NamesPosts");
        DatabaseReference checkcode = FirebaseDatabase.getInstance().getReference().child("CodePosts");
        HashMap<String,Object> chedked = new HashMap<>();
        chedked.put("name",Pname);
        checkcode.child(Pcode).updateChildren(chedked);
        checkname.child(Pname).updateChildren(chedked);
        ProductsRef.child("Protucts").child(CategoryName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("test").exists()){
                    DatabaseReference rem = FirebaseDatabase.getInstance().getReference().child("Protucts");
                    rem.child(CategoryName).child("test").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ProductsRef.child("Protucts").child(CategoryName).child(productRandomKey).updateChildren(productMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Intent intent = new Intent(AddPost.this, Cpanal.class);
                                                startActivity(intent);

                                                loadingBar.dismiss();
                                                Toast.makeText(AddPost.this, "تم النشر بنجاح..", Toast.LENGTH_SHORT).show();

                                            }
                                            else
                                            {
                                                loadingBar.dismiss();
                                                String message = task.getException().toString();
                                                Toast.makeText(AddPost.this, "خطأ: " + message, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    });
                }else {
                    ProductsRef.child("Protucts").child(CategoryName).child(productRandomKey).updateChildren(productMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Intent intent = new Intent(AddPost.this, Cpanal.class);
                                        startActivity(intent);

                                        loadingBar.dismiss();
                                        Toast.makeText(AddPost.this, "تم النشر بنجاح..", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        String message = task.getException().toString();
                                        Toast.makeText(AddPost.this, "خطأ: " + message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getItemIdAtPosition(i) == 0){
            if(checkbox1.isChecked()){
                maintype = "قطعة";
            }else {
                Toast.makeText(this, "لم تحدد سعر لهذه الوحدة", Toast.LENGTH_SHORT).show();
                maintype = "";
            }

        }
        else if(adapterView.getItemIdAtPosition(i) == 1){
            if(checkbox2.isChecked()){
                maintype = "درزن";
            }else {
                Toast.makeText(this, "لم تحدد سعر لهذه الوحدة", Toast.LENGTH_SHORT).show();
                maintype = "";
            }
        } else if(adapterView.getItemIdAtPosition(i) == 3){
            if(checkbox3.isChecked()){
                maintype = "كارتونة";
            }else {
                Toast.makeText(this, "لم تحدد سعر لهذه الوحدة", Toast.LENGTH_SHORT).show();
                maintype = "";

            }

        } else if(adapterView.getItemIdAtPosition(i) == 2){
            if(checkBox4.isChecked()){
                maintype = "سيت";
            }else {
                Toast.makeText(this, "لم تحدد سعر لهذه الوحدة", Toast.LENGTH_SHORT).show();
                maintype = "";
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
