package com.example.foodkcal_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodkcal_app.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity {

    private Button btn_cam;
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

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super .onResume();
        layout.removeAllViews();
        for(int i=0; i<7; i++){
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            Date d = cal.getTime();
            timeStamp = new SimpleDateFormat("yyyy. MM. dd.").format(d);

            final TextView dayTextView = new TextView(context);
            dayTextView.append(timeStamp);
            dayTextView.setPadding(20, 20, 10, 10);
            dayTextView.setTextSize(20.0f);
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(0x00000000); // Changes this drawbale to use a single color instead of a gradient
            gd.setCornerRadius(5);
            gd.setStroke(5, 0xFF000000);
            dayTextView.setBackground(gd);

            layout.addView(dayTextView);

            timeStamp = new SimpleDateFormat("yyyyMMdd").format(d);

            int N = sp.getInt(timeStamp, 0);

            if(N == 0){
                layout.removeView(dayTextView);
            }

            for (int j = 0; j < N; j++) {

                final LinearLayout entryLayout = new LinearLayout(context);
                entryLayout.setOrientation(LinearLayout.HORIZONTAL);
                entryLayout.setGravity(Gravity.CENTER_VERTICAL);
                entryLayout.setClickable(true);
                gd = new GradientDrawable();
                gd.setColor(0x00000000); // Changes this drawbale to use a single color instead of a gradient
                gd.setCornerRadius(5);
                gd.setStroke(1, 0xFF000000);
                entryLayout.setBackground(gd);


                File file = new File(storageDir, "foodkcal_" + timeStamp + "_" + j + ".jpg");
                BitmapFactory.Options op = new BitmapFactory.Options();
                Bitmap Bm = BitmapFactory.decodeFile(file.getAbsolutePath(), op);
                if (Bm != null) {
                    final ImageView rowImageView = new ImageView(context);
                    rowImageView.setPadding(10, 10, 10, 10);
                    rowImageView.setImageBitmap(Bitmap.createScaledBitmap(Bm, 250, 250, false));

                    // add the textview to the linearlayout
                    entryLayout.addView(rowImageView);
                }

                // create a new textview
                final TextView rowTextView = new TextView(context);
                rowTextView.setPadding(10, 10, 10, 10);
                rowTextView.setTextSize(25.0f);
                rowTextView.setGravity(Gravity.CENTER_VERTICAL);
                file = new File(storageDir, "foodkcal_" + timeStamp + "_" + j + ".data");
                try (FileReader fr = new FileReader(file)) {
                    int content;
                    int cnt = 0;
                    while ((content = fr.read()) != -1) {
                        //System.out.print((char) content);
                        if( (char)content == ' '){
                            cnt++;
                        }
                        if(cnt == 2) break;
                        String tempstr = "" + (char)content;
                        rowTextView.append(tempstr);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // add the textview to the linearlayout
                entryLayout.addView(rowTextView);

                // create a new textview
                final TextView infoTextView = new TextView(context);
                infoTextView.setVisibility(GONE);
                file = new File(storageDir, "foodkcal_" + timeStamp + "_" + j + ".data");
                try {
                    Scanner scan = new Scanner(file);
                    while(scan.hasNextLine()){
                        infoTextView.append(scan.nextLine());
                    }
                }catch (FileNotFoundException e) {
                    // TODO: handle exception
                }
                final String tempstr = infoTextView.getText().toString();
                // add the textview to the linearlayout
                entryLayout.addView(infoTextView);

                layout.addView(entryLayout);

                entryLayout.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Toast.makeText(context, tempstr, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

}