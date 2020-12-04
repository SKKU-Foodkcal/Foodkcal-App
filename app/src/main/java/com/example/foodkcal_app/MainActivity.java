package com.example.foodkcal_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodkcal_app.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class MainActivity extends AppCompatActivity {

    private Button btn_cam, btn_load;
    private Context context;
    private File storageDir;
    private String timeStamp;
    private SharedPreferences sp;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        btn_cam = findViewById(R.id.button);
        btn_load = findViewById(R.id.button2);
        layout = findViewById(R.id.myll);
        sp = this.getSharedPreferences("MYSP", Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
        timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        storageDir = context.getFilesDir();

        btn_cam.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,Result.class);
                startActivity(intent);

            }
        });

        btn_load.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                layout.removeAllViews();
                for(int i=0; i<7; i++){
                    final Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -i);
                    Date d = cal.getTime();
                    timeStamp = new SimpleDateFormat("yyyyMMdd").format(d);

                    final TextView dayTextView = new TextView(context);
                    dayTextView.append(timeStamp + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    layout.addView(dayTextView);

                    int N = sp.getInt(timeStamp, 0);

                    for (int j = 0; j < N; j++) {

                        final LinearLayout entryLayout = new LinearLayout(context);
                        entryLayout.setOrientation(LinearLayout.HORIZONTAL);

                        File file = new File(storageDir, "foodkcal_" + timeStamp + "_" + j + ".jpg");
                        BitmapFactory.Options op = new BitmapFactory.Options();
                        Bitmap Bm = BitmapFactory.decodeFile(file.getAbsolutePath(), op);
                        if (Bm != null) {
                            final ImageView rowImageView = new ImageView(context);
                            rowImageView.setImageBitmap(Bitmap.createScaledBitmap(Bm, 250, 250, false));

                            // add the textview to the linearlayout
                            entryLayout.addView(rowImageView);
                        }


                        // create a new textview
                        final TextView rowTextView = new TextView(context);
                        //rowTextView.append(timeStamp + "\n");
                        file = new File(storageDir, "foodkcal_" + timeStamp + "_" + j + ".data");
                        try{
                            Scanner scan = new Scanner(file);
                            while(scan.hasNextLine()){
                                rowTextView.append(scan.nextLine());
                            }
                        }catch (FileNotFoundException e) {
                            // TODO: handle exception
                        }
                        // add the textview to the linearlayout
                        entryLayout.addView(rowTextView);

                        layout.addView(entryLayout);

                    }

                }

            }
        });

    }

}