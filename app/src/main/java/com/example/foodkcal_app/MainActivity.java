package com.example.foodkcal_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodkcal_app.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class MainActivity extends AppCompatActivity {

    private Button CamButton;
    private Context context;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        CamButton = findViewById(R.id.button);
        CamButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,Result.class);
                startActivity(intent);

            }
        });

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        String date0 = sdf.format(date);
        text = findViewById(R.id.textView11);
        text.setText(date0);

        date.setTime(date.getTime()+(1000*60*60*24*-1));
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        date0 = sdf.format(date);
        text = findViewById(R.id.textView21);
        text.setText(date0);

        date.setTime(date.getTime()+(1000*60*60*24*-1));
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        date0 = sdf.format(date);
        text = findViewById(R.id.textView31);
        text.setText(date0);

        date.setTime(date.getTime()+(1000*60*60*24*-1));
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        date0 = sdf.format(date);
        text = findViewById(R.id.textView41);
        text.setText(date0);


        date.setTime(date.getTime()+(1000*60*60*24*-1));
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        date0 = sdf.format(date);
        text = findViewById(R.id.textView51);
        text.setText(date0);

        date.setTime(date.getTime()+(1000*60*60*24*-1));
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        date0 = sdf.format(date);
        text = findViewById(R.id.textView61);
        text.setText(date0);

        date.setTime(date.getTime()+(1000*60*60*24*-1));
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        date0 = sdf.format(date);
        text = findViewById(R.id.textView71);
        text.setText(date0);


    }

}