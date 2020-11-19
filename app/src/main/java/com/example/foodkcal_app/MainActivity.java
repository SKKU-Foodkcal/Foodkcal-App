package com.example.foodkcal_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodkcal_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView=null;
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
                dispatchTakePictureIntent();
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



        /*
        sendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(imageView.getDrawable() == null){
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "이미지를 넣어주세요", duration);
                    toast.show();
                }
                else{
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "서버로 전송합니다", duration);
                    toast.show();
                }
                //send to server
            }
        });

        camButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dispatchTakePictureIntent();
            }
        });
         */

    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    } */



    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


}