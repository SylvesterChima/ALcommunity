package com.andela.sylvester.alcommunity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.sylvester.alcommunity.data.model.DownloadImageTask;
import com.andela.sylvester.alcommunity.data.model.Post;
import com.andela.sylvester.alcommunity.data.model.student;
import com.andela.sylvester.alcommunity.data.remote.APIService;
import com.andela.sylvester.alcommunity.data.remote.ApiUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.net.URL;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class student_details extends AppCompatActivity {

    ImageView img;
    ImageView btn;
    ProgressBar pbLoading;
    String mediaPath;
    Uri si;
    private APIService mAPIService;
    student pst;


    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        if (shouldAskPermissions()) {
            askPermissions();
        }

        TextView txtName = (TextView)findViewById(R.id.dFullName);
        TextView txtEmail = (TextView)findViewById(R.id.dEmail);
        TextView txtPhone = (TextView)findViewById(R.id.dPhone);
        TextView txtRegion = (TextView)findViewById(R.id.dRegion);
        TextView txtState = (TextView)findViewById(R.id.dState);
        TextView txtAddress = (TextView)findViewById(R.id.dAddress);

        pst = (student)getIntent().getSerializableExtra("mStudents");
        img = (ImageView)findViewById(R.id.imgStudentImage);
        btn = (ImageView)findViewById(R.id.btnEditImage);
        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);
        mAPIService = ApiUtils.getAPIService();

        txtName.setText(pst.getName() + "(" + pst.getCoderName() + ")");
        txtEmail.setText(pst.getEmail());
        txtPhone.setText(pst.getPhone());
        txtRegion.setText(pst.getRegion());
        txtState.setText(pst.getState());
        txtAddress.setText(pst.getAddress());

        if(pst.getPicUrl() != null){
            pbLoading.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(pst.getPicUrl())
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img);
            pbLoading.setVisibility(View.INVISIBLE);
            //new DownloadImageTask(img, pbLoading).execute(pst.getPicUrl());
        }
        else {
            pbLoading.setVisibility(View.INVISIBLE);
            img.setImageResource(R.drawable.ic_person_black_24dp);
        }
        /*pbLoading.setVisibility(View.VISIBLE);
        mAPIService.GetUserPic(pst.getCoderName()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    pbLoading.setVisibility(View.INVISIBLE);
                    Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                    img.setImageBitmap(bm);
                }
                else {
                    pbLoading.setVisibility(View.INVISIBLE);
                    img.setImageResource(R.drawable.ic_person_black_24dp);
                    //Toast.makeText(student_details.this, response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pbLoading.setVisibility(View.INVISIBLE);
                img.setImageResource(R.drawable.ic_person_black_24dp);
                Toast.makeText(student_details.this, t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });*/


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode == 0 && resultCode == RESULT_OK && null != data){
                si = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(si,filePath,null,null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);
                mediaPath = cursor.getString(columnIndex);
                img.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                //img.setImageURI(si);
                cursor.close();


                File file = new File(mediaPath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                pbLoading.setVisibility(View.VISIBLE);
                mAPIService.UploadUserPic(body, pst.getCoderName()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pbLoading.setVisibility(View.INVISIBLE);
                        if(response.isSuccessful()) {
                            Toast.makeText(student_details.this,"Uploaded successfully!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            pbLoading.setVisibility(View.INVISIBLE);
                            Toast.makeText(student_details.this,response.message(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        pbLoading.setVisibility(View.INVISIBLE);
                        Toast.makeText(student_details.this, t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });


                //Toast.makeText(student_details.this,"\n" + si + "\n" + mediaPath,Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(student_details.this,"Make sure you selected a file",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            pbLoading.setVisibility(View.INVISIBLE);
            Toast.makeText(student_details.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
