package com.example.foodkcal_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class FoodEntry extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_entry);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        //data example : "burgers 313kcal Per 100g 31.13g 14.48g 14.85g 1(인분)"

        String fooddatas[] = data.split(" ");
        String foodname = fooddatas[0];                         // "burgers"
        String foodkcal[] = fooddatas[1].split("k");    // "313", "cal"
        String foodunit = fooddatas[3];                         // "100g"
        String foodcarb[] = fooddatas[4].split("g");    // "31.13", ""
        String foodprot[] = fooddatas[5].split("g");    // "14.48", ""
        String foodfat[] = fooddatas[6].split("g");     // "14.85", ""
        String foodamount[] = fooddatas[7].split("\\(");  // "1", "인분)"

        TextView tv = findViewById(R.id.textView_test);
        tv.setGravity(Gravity.CENTER);
        //tv.setText(data);
        float amount = Float.parseFloat(foodamount[0]);
        float total_kcal = Float.parseFloat(foodkcal[0]) * amount;
        float total_carb = Float.parseFloat(foodcarb[0]) * amount;
        float total_prot = Float.parseFloat(foodprot[0]) * amount;
        float total_fat = Float.parseFloat(foodfat[0]) * amount;

        tv.setText(amount + "인분 섭취\n" +
                "총 열량 : " + (int)total_kcal + "kcal\n" +
                "총 탄수화물 : " + String.format("%.2f", total_carb) + "g\n" +
                "총 단백질 : " + String.format("%.2f", total_prot) + "g\n" +
                "총 지방 : " + String.format("%.2f", total_fat) + "g");

    }
}