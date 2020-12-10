package com.example.foodkcal_app;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.Gravity.CENTER;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class Fr_Nut extends Fragment {

    public Fr_Nut() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fr__nut, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        final Context context = getActivity();
        SharedPreferences sp = context.getSharedPreferences("MYSP", Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
        File storageDir = context.getFilesDir();
        LinearLayout layout = getView().findViewById(R.id.ll_nut);
        layout.removeAllViews();

        String timestamp = new SimpleDateFormat("yyyy. MM. dd.").format(new Date());

        //날짜 바
        TextView tv_day = new TextView(context);
        tv_day.setPadding(10, 10, 10, 10);
        tv_day.setText(timestamp);
        tv_day.setTextSize(25f);
        layout.addView(tv_day);


        timestamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int N = sp.getInt(timestamp, 0);

        if(N == 0){
            TextView temptv = new TextView(context);
            temptv.setText("오늘 먹은 음식이 없습니다");
            temptv.setTextSize(25f);
            temptv.setPadding(10, 10, 10, 10);
            temptv.setGravity(CENTER);
            layout.addView(temptv);
        }

        else {
            float total_kcal = 0f;
            float total_carb = 0f;
            float total_prot = 0f;
            float total_fat = 0f;


            for (int j = 0; j < N; j++) {
                File file = new File(storageDir, "foodkcal_" + timestamp + "_" + j + ".data");
                String tempstr = "";
                try (FileReader fr = new FileReader(file)) {
                    int content;
                    while ((content = fr.read()) != -1) {
                        tempstr += (char) content;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String fooddatas[] = tempstr.split(" ");
                String foodkcal[] = fooddatas[1].split("k");    // "313", "cal"
                String foodcarb[] = fooddatas[4].split("g");    // "31.13", ""
                String foodprot[] = fooddatas[5].split("g");    // "14.48", ""
                String foodfat[] = fooddatas[6].split("g");     // "14.85", ""
                String foodamount[] = fooddatas[7].split("\\(");  // "1", "인분)"


                float amount = Float.parseFloat(foodamount[0]);
                total_kcal += Float.parseFloat(foodkcal[0]) * amount;
                total_carb += Float.parseFloat(foodcarb[0]) * amount;
                total_prot += Float.parseFloat(foodprot[0]) * amount;
                total_fat += Float.parseFloat(foodfat[0]) * amount;
            }



            //원형차트
            PieChart pieChart = new PieChart(context);
            pieChart.setPadding(10, 10, 10, 10);
            pieChart.setUsePercentValues(false);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.setTransparentCircleRadius(50f);


            ArrayList<PieEntry> Entries = new ArrayList<PieEntry>();
            Entries.add(new PieEntry(total_carb, "탄수화물(g)"));
            Entries.add(new PieEntry(total_prot, "단백질(g)"));
            Entries.add(new PieEntry(total_fat, "지방(g)"));

            PieDataSet dataSet = new PieDataSet(Entries, "");
            dataSet.setColors(ColorTemplate.PASTEL_COLORS);


            PieData data = new PieData(dataSet);
            data.setValueTextSize(20f);
            data.setValueTextColor(Color.BLACK);

            pieChart.setData(data);

            layout.addView(pieChart);
            pieChart.getLayoutParams().width = MATCH_PARENT;
            pieChart.getLayoutParams().height = 1000;
            pieChart.requestLayout();

            //이후...
            float req_kcal = 2400f;
            float req_carb = 500f;
            float req_prot = 70f;
            float req_fat = 40f;
            boolean needmore = false;

            TextView needtv = new TextView(context);
            needtv.setText("");
            needtv.setTextSize(25f);
            needtv.setPadding(10, 40, 10, 10);
            needtv.setGravity(CENTER);
            if( total_kcal < req_kcal ){
                needmore = true;
                needtv.append("열량 " + String.format("%.1f", (req_kcal - total_kcal)) + "kcal 추가 섭취 권장\n");
            }
            if (total_carb < req_carb) {
                needmore = true;
                needtv.append("탄수화물 " + String.format("%.1f", (req_carb - total_carb)) + "g 추가 섭취 권장\n");
            }
            if (total_prot < req_prot) {
                needmore = true;
                needtv.append("단백질 " + String.format("%.1f", (req_prot - total_prot)) + "g 추가 섭취 권장\n");
            }
            if (total_fat < req_fat) {
                needmore = true;
                needtv.append("지방 " + String.format("%.1f", (req_fat - total_fat)) + "g 추가 섭취 권장\n");
            }
            if(needmore == false){
                needtv.setTextSize(30f);
                needtv.setText("추가 섭취 필요분 없음");
            }

            layout.addView(needtv);

        }
    }
}