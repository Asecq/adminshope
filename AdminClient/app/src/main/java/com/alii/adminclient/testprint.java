package com.alii.adminclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class testprint extends AppCompatActivity {

    View main;
    ImageView img;
    Button btn;
    Button print;
    private ArrayList<Mobile> mobiles;
    File outFile;
    String fileName;
    private DatabaseReference rootPro;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<Mobile> options;
    private FirebaseRecyclerAdapter<Mobile, d_holder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_testprint);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        print = findViewById(R.id.print_btn);




        main = findViewById(R.id.main_layoutScreen);
        img = findViewById(R.id.img_screenshot);
        btn = findViewById(R.id.screenshot_btn);
        recyclerView = findViewById(R.id.list_screen);
        rootPro = FirebaseDatabase.getInstance().getReference().child("Calls");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setPadding(0,0,0,0);
        //
        options = new FirebaseRecyclerOptions.Builder<Mobile>().setQuery(rootPro, Mobile.class).build();
        adapter = new FirebaseRecyclerAdapter<Mobile, d_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final d_holder d_holder, final int i, @NonNull final Mobile dons) {
            d_holder.tslsl.setText("1");
            d_holder.name.setText(mobiles.get(i).getNames()[i]);



            }

            @NonNull
            @Override
            public d_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_f_test,parent,false);
                return new d_holder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        ///
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = getRecyclerViewScreenshot(recyclerView);
                img.setImageBitmap(b);
                BitmapDrawable draw = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = draw.getBitmap();

                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/MainProgram");
                dir.mkdirs();
                 fileName = String.format("%d.jpg", System.currentTimeMillis());
               outFile = new File(dir, fileName);
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
                Toast.makeText(testprint.this, "تم حفظ الصورة", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(outFile));
                sendBroadcast(intent);
                Toast.makeText(testprint.this, String.valueOf(fileName), Toast.LENGTH_SHORT).show();


            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent galleryIntent = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setDataAndType(Uri.fromFile(new File(fileName)), "image/*");
                    galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(galleryIntent);
                }catch (Exception e){
                    Toast.makeText(testprint.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public static class d_holder extends RecyclerView.ViewHolder {

        private TextView  tslsl , code , name , dep , size , unit , price , total;

        public d_holder(@NonNull View itemView) {
            super(itemView);
            tslsl = itemView.findViewById(R.id.shoot_tslsl);
            code = itemView.findViewById(R.id.shoot_code);
            name = itemView.findViewById(R.id.shoot_name);
            dep = itemView.findViewById(R.id.shoot_deps);
            size = itemView.findViewById(R.id.shoot_number);
            unit = itemView.findViewById(R.id.shoot_unite);
            price = itemView.findViewById(R.id.shoot_price);
            total = itemView.findViewById(R.id.shoot_toal);


        }
    }
    public static Bitmap getRecyclerViewScreenshot(RecyclerView view) {
        int size = view.getAdapter().getItemCount();
        RecyclerView.ViewHolder holder = view.getAdapter().createViewHolder(view, 0);
        view.getAdapter().onBindViewHolder(holder, 0);
        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
        Bitmap bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), holder.itemView.getMeasuredHeight() * size,
                Bitmap.Config.ARGB_8888);
        Canvas bigCanvas = new Canvas(bigBitmap);
        bigCanvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        int iHeight = 0;
        holder.itemView.setDrawingCacheEnabled(true);
        holder.itemView.buildDrawingCache();
        bigCanvas.drawBitmap(holder.itemView.getDrawingCache(), 0f, iHeight, paint);
        holder.itemView.setDrawingCacheEnabled(false);
        holder.itemView.destroyDrawingCache();
        iHeight += holder.itemView.getMeasuredHeight();
        for (int i = 1; i < size; i++) {
            view.getAdapter().onBindViewHolder(holder, i);
            holder.itemView.setDrawingCacheEnabled(true);
            holder.itemView.buildDrawingCache();
            bigCanvas.drawBitmap(holder.itemView.getDrawingCache(), 0f, iHeight, paint);
            iHeight += holder.itemView.getMeasuredHeight();
            holder.itemView.setDrawingCacheEnabled(false);
            holder.itemView.destroyDrawingCache();
        }
        return bigBitmap;
    }
}
