package com.andela.sylvester.alcommunity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andela.sylvester.alcommunity.data.model.Post;
import com.andela.sylvester.alcommunity.data.model.student;
import com.andela.sylvester.alcommunity.data.remote.APIService;
import com.andela.sylvester.alcommunity.data.remote.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class alc_students extends AppCompatActivity {

    ListView listView = null;
    private APIService mAPIService;
    private List<student> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alc_students);
        listView = (ListView)findViewById(R.id.listStudents);
        mAPIService = ApiUtils.getAPIService();
        getStudents();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                student pt = results.get(position);
                Intent intent = new Intent(alc_students.this, student_details.class);
                intent.putExtra("mStudents", pt);
                startActivity(intent);
            }
        });


    }

    public void getStudents(){
        final ProgressDialog loading = ProgressDialog.show(this," ","Please wait...",false,false);
        //Callback<List<student>> ml = mAPIService.fetchStudent();
        mAPIService.getStudent().enqueue(new Callback<List<student>>() {
            @Override
            public void onResponse(Call<List<student>> call, Response<List<student>> response) {
                if(response.isSuccessful()){
                    loading.dismiss();
                    results = response.body();
                    studentAdapter customAdapter = new studentAdapter(alc_students.this, R.layout.student_list_item,results);
                    listView.setAdapter(customAdapter);
                    customAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(alc_students.this, response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<student>> call, Throwable t) {
                Toast.makeText(alc_students.this, t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
