package com.example.setwallpaper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.app.WallpaperManager.FLAG_LOCK;
import static com.example.setwallpaper.R.drawable.ic_launcher_background;

public class MainActivity extends AppCompatActivity {

    Button btnSetIt;
    ImageView imgSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSetIt = findViewById(R.id.btnSetIt);
        imgSample = findViewById(R.id.imgSample);

        InputStream inputStream = getResources().openRawResource(R.raw.wallpaper);
        imgSample.setImageBitmap(BitmapFactory.decodeStream(inputStream));
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/DirName/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File("/sdcard/DirName/", fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBtnSetItClicked(View view) {
        // TODO Auto-generated method stub
        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            myWallpaperManager.setResource(R.raw.wallpaper, FLAG_LOCK);

            Toast.makeText(MainActivity.this, "Wallpaper Set!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBtnClearClicked(View view) {
        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            myWallpaperManager.clear();
            //myWallpaperManager.clear(FLAG_LOCK);

            Toast.makeText(MainActivity.this, "Wallpaper Cleared!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}