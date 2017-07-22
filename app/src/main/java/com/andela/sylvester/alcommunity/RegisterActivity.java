package com.andela.sylvester.alcommunity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andela.sylvester.alcommunity.data.model.Post;
import com.andela.sylvester.alcommunity.data.remote.APIService;
import com.andela.sylvester.alcommunity.data.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText txtCoderName;
    EditText txtName;
    EditText txtEmail;
    EditText txtPhone;
    EditText txtPassword;
    EditText txtRegion;
    EditText txtState;
    EditText txtAddress;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        btnRegister = (Button)findViewById(R.id.btnRegister);
        txtCoderName = (EditText)findViewById(R.id.txtUserName);
        txtName = (EditText)findViewById(R.id.txtName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPhone = (EditText)findViewById(R.id.txtPhoneNumber);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtRegion = (EditText)findViewById(R.id.txtRegion);
        txtState = (EditText)findViewById(R.id.txtState);
        txtAddress = (EditText)findViewById(R.id.txtUserAddress);
        mAPIService = ApiUtils.getAPIService();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post obj = new Post();
                obj.setAddress(txtAddress.getText().toString());
                obj.setCoderName(txtCoderName.getText().toString());
                /*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                obj.setDateRegistered(cal.getTime());*/
                obj.setEmail(txtEmail.getText().toString());
                obj.setName(txtName.getText().toString());
                obj.setPassword(txtPassword.getText().toString());
                obj.setRegion(txtRegion.getText().toString());
                obj.setState(txtState.getText().toString());
                obj.setPhone(txtPhone.getText().toString());
                if(txtCoderName.getText().toString().matches("") | txtName.getText().toString().matches("") | txtEmail.getText().toString().matches("") | txtPassword.getText().toString().matches("") | txtPhone.getText().toString().matches("") | txtAddress.getText().toString().matches("") | txtRegion.getText().toString().matches("") | txtState.getText().toString().matches("")){
                    AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Alert Dialog");

                    // Setting Dialog Message
                    alertDialog.setMessage("All fields are required!");

                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.ic_error_black_24dp);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
                else{
                    sendPost(obj);
                }
            }
        });
    }

    public void sendPost(Post post) {
        mAPIService.SaveStudent(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    showResponse("Registered successfully!");
                    Intent intent = new Intent(RegisterActivity.this,alc_students.class);
                    startActivity(intent);
                }
                else{
                    showResponse(response.message());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                showResponse(t.getMessage());
            }
        });
    }

    public void showResponse(String response) {
        Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_LONG).show();
    }
}
