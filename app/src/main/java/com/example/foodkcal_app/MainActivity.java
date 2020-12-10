package com.example.foodkcal_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity {

    private ImageButton btn_cam, btn_nut, btn_list;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        btn_cam = findViewById(R.id.button);
        btn_nut = findViewById(R.id.button3);
        btn_list = findViewById(R.id.button4);

//        //최초 실행시 이 부분을 uncomment 해주세요. (과거 날짜 값들 0으로 세팅하는 코드)
//        SharedPreferences sp = this.getSharedPreferences("MYSP", Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
//        SharedPreferences.Editor spe = sp.edit();
//        for(int i=0;i<50;i++){
//            final Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.DATE, -i);
//            Date d = cal.getTime();
//            String timeStamp = new SimpleDateFormat("yyyyMMdd").format(d);
//            spe.putInt(timeStamp, 0);
//            spe.commit();
//        }


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_frame, new Fr_List());
        fragmentTransaction.commit();

        btn_cam.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,Result.class);
                startActivity(intent);

            }
        });

        btn_list.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Fragment fr = new Fr_List();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_frame, fr);
                fragmentTransaction.commit();
            }
        });

        btn_nut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Fragment fr = new Fr_Nut();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_frame, fr);
                fragmentTransaction.commit();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super .onResume();
        Fragment fr = new Fr_List();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame, fr);
        fragmentTransaction.commit();
    }

}