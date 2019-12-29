package com.alii.adminclient;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Printer_fatora extends AppCompatActivity {

    private ListView listView ;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer_fatora);

        reference = FirebaseDatabase.getInstance().getReference().child("Calls").child(getIntent().getExtras().get("key").toString());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                final GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                ArrayList<String> ingredients = dataSnapshot.child("Names").getValue(t);
                ArrayList<String> cou = dataSnapshot.child("Counts").getValue(t);
                ArrayList<String> price = dataSnapshot.child("Prices").getValue(t);
                ArrayList<String> one = dataSnapshot.child("Ones").getValue(t);
                ArrayList<String> deps = dataSnapshot.child("Deps").getValue(t);
                final ArrayList<String> unites = dataSnapshot.child("Units").getValue(t);
                ArrayList<String> totales = dataSnapshot.child("Ptotal").getValue(t);

                listView = findViewById(R.id.listview_printer);
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
                    String[] depa = new String[deps.size()];
                    depa = deps.toArray(depa);
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
                TextView total = findViewById(R.id.result_fatora_printer);
                TextView number = findViewById(R.id.number_fatora_printer);
                TextView user = findViewById(R.id.user_fatora_printer);
                TextView note = findViewById(R.id.note_fatora_printer);
                TextView data = findViewById(R.id.data_fatora_printer);
                TextView dep = findViewById(R.id.dep_fatora_printer);
                total.setText( dataSnapshot.child("Price").getValue().toString() + "$");
                dep.setText( dataSnapshot.child("dep").getValue().toString());
                number.setText( "رقم الفاتورة :  "+ dataSnapshot.child("number").getValue().toString() );
                note.setText("ملاحظات : " + dataSnapshot.child("note").getValue().toString());
                data.setText("التاريخ : " + dataSnapshot.child("time").getValue().toString());
                user.setText("السيد : " + dataSnapshot.child("user").getValue().toString());


                final adapterlistview adapter = new adapterlistview(Printer_fatora.this, mobiles);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                listView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        new CountDownTimer(1000, 1000) {
                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {
                                View main = findViewById(R.id.main_printer);
                                Toast.makeText(Printer_fatora.this, "جاري الطباعة ... ", Toast.LENGTH_LONG).show();
                                Bitmap bmp = Bitmap.createBitmap(main.getWidth(), main.getHeight(), Bitmap.Config.ARGB_8888);
                                Canvas c = new Canvas(bmp);
                                main.draw(c);
                                PrintHelper photoPrinter = new PrintHelper(Printer_fatora.this);
                                photoPrinter.setOrientation(PrintHelper.ORIENTATION_LANDSCAPE);
                                photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                                photoPrinter.setColorMode(PrintHelper.COLOR_MODE_MONOCHROME);
                                photoPrinter.printBitmap("layout.png", bmp);

                            }
                        }.start();

                      /*      */
                    }
                });






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "تمت الطباعة", Toast.LENGTH_SHORT).show();
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                onBackPressed();
            }


        }.start();
    }

}
