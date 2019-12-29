package com.alii.adminclient;

import android.graphics.Bitmap;
import android.view.View;

public class ScreenShot {

    public static Bitmap tekescreenshot(View view){

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return b;

    }
    public  static Bitmap tackscreenshotforRoot(View v){
        return tekescreenshot(v.getRootView());

    }

}
