package com.example.foodkcal_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Result extends AppCompatActivity {

    private static final String TAG = "Result";
    private Context context;
    private String currentPhotoPath;
    private ImageView iv_thumb;
    private Button btn_anaylze, btn_cancel, btn_save;
    private TextView tv_foodName, tv_foodkcal, tv_foodFat, tv_foodCarb, tv_foodProt;
    private EditText et_amount;

    private SharedPreferences sp;
    private SharedPreferences.Editor spe;
    private int index;
    private String timeStamp;
    private File storageDir;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        context = getApplicationContext();
        tv_foodName = findViewById(R.id.foodName);
        tv_foodkcal = findViewById(R.id.kcalPerUnit);
        tv_foodFat = findViewById(R.id.textView_fat);
        tv_foodCarb = findViewById(R.id.textView_carb);
        tv_foodProt = findViewById(R.id.textView_prot);
        iv_thumb = findViewById(R.id.imageView);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_anaylze = findViewById(R.id.analyze);
        btn_save = findViewById(R.id.btn_save);
        et_amount = findViewById(R.id.editText_amount);

        sp = this.getSharedPreferences("MYSP", Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
        spe = sp.edit();
        timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        index = sp.getInt(timeStamp, 0);
        storageDir = context.getFilesDir();

        dispatchTakePictureIntent();


        btn_anaylze.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                File file = new File(currentPhotoPath);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("images", file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file));
                TestService testService = TestService.retrofit.create(TestService.class);
                Call<PostResponse> call = testService.postImage(filePart);
                call.enqueue(new Callback<PostResponse>() {
                    @Override
                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                        String fat = response.body().getFat();
                        String carbs = response.body().getCarbs();
                        String food_Name = response.body().getFood_Name();
                        String unit = response.body().getUnit();
                        String calories = response.body().getCalories();
                        String protein = response.body().getProtein();

                        tv_foodName.setText(food_Name);
                        tv_foodkcal.setText(calories + ' ' + unit);
                        tv_foodCarb.setText(carbs);
                        tv_foodFat.setText(fat);
                        tv_foodProt.setText(protein);
                    }

                    @Override
                    public void onFailure(Call<PostResponse> call, Throwable t) {
                    }
                });

                /*
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        File file = new File(currentPhotoPath);
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("images", file.getName(),
                                RequestBody.create(MediaType.parse("image/*"), file));


                        TestService testService = TestService.retrofit.create(TestService.class);
                        Call<PostResponse> call = testService.postImage(filePart);

                        try {
                            return call.execute().body().toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        TextView textView = (TextView) findViewById(R.id.foodName);
                        textView.setText(s);
                    }
                }.execute();
                 */
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                File file = new File(storageDir, "foodkcal_" + timeStamp + "_" + index + ".data");
                try {
                    BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                    buf.append(tv_foodName.getText() + " " +
                            tv_foodkcal.getText() + " " +
                            tv_foodCarb.getText() + " " +
                            tv_foodProt.getText() + " " +
                            tv_foodFat.getText() + " " +
                            et_amount.getText() + "(인분)");
                    buf.close();
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                spe.putInt(timeStamp, index + 1);
                spe.commit();

                finish();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d(TAG, "dispatchTakePictureIntent: ioexception error");
                Log.d(TAG, "dispatchTakePictureIntent: " + ex.getMessage());
                Log.d(TAG, "dispatchTakePictureIntent: " + ex.getCause());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "foodkcal_" + timeStamp + "_" + index + ".jpg";
        File image = new File(storageDir, imageFileName);
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, "createImageFile: curphotopath : " + currentPhotoPath);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Log.d(TAG, "onActivityResult: Here1111");
            File file = new File(currentPhotoPath);
            BitmapFactory.Options op = new BitmapFactory.Options();
            Bitmap Bm = BitmapFactory.decodeFile(file.getAbsolutePath(), op);
            //Log.d(TAG, "onActivityResult: Here2222");
            iv_thumb.setImageBitmap(Bm);
        }
    }
}
