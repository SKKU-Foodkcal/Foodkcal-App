package com.example.foodkcal_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;


public class Fr_List extends Fragment {

    View v;
    public Fr_List() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: v == " + v);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: called");
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout layout = getView().findViewById(R.id.myll2);

        layout.removeAllViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
        final Context context = getActivity();
        SharedPreferences sp = context.getSharedPreferences("MYSP", Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
        File storageDir = context.getFilesDir();
        LinearLayout layout = getView().findViewById(R.id.myll2);

        layout.removeAllViews();

        for(int i=0; i<20; i++){
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            Date d = cal.getTime();
            String timeStamp = new SimpleDateFormat("yyyy. MM. dd.").format(d);

            final TextView dayTextView = new TextView(context);
            dayTextView.append(timeStamp);
            dayTextView.setPadding(15, 15, 15, 15);
            dayTextView.setTextSize(20.0f);
//            GradientDrawable gd = new GradientDrawable();
//            gd.setColor(0x00000000); // Changes this drawbale to use a single color instead of a gradient
//            gd.setCornerRadius(5);
//            gd.setStroke(5, 0xFF000000);
//            dayTextView.setBackground(gd);

            layout.addView(dayTextView);

            timeStamp = new SimpleDateFormat("yyyyMMdd").format(d);

            int N = sp.getInt(timeStamp, 0);

            if(N == 0){
                layout.removeView(dayTextView);
            }

            for (int j = N-1; j >= 0; j--) {

                final LinearLayout entryLayout = new LinearLayout(context);
                entryLayout.setOrientation(LinearLayout.HORIZONTAL);
                entryLayout.setGravity(Gravity.CENTER_VERTICAL);
                entryLayout.setClickable(true);
                GradientDrawable gd = new GradientDrawable();
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
                String tempstr = "";
                try (FileReader fr = new FileReader(file)) {
                    int content;
                    while ((content = fr.read()) != -1) {
                        tempstr += (char)content;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String fooddatas[] = tempstr.split(" ");
                String foodname = fooddatas[0];                         // "burgers"
                String foodkcal[] = fooddatas[1].split("k");    // "313", "cal"
                String foodamount[] = fooddatas[7].split("\\(");  // "1", "인분)"

                float amount = Float.parseFloat(foodamount[0]);
                float total_kcal = Float.parseFloat(foodkcal[0]) * amount;

                rowTextView.setText(foodname);
                rowTextView.append(" " + (int)total_kcal + "kcal");

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
                final String infostr = infoTextView.getText().toString();
                // add the textview to the linearlayout
                entryLayout.addView(infoTextView);

                layout.addView(entryLayout);

                entryLayout.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        //Toast.makeText(context, tempstr, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,  FoodEntry.class);
                        intent.putExtra("data", infostr);
                        startActivityForResult(intent, 1);

                    }
                });

            }
        }
    }



}