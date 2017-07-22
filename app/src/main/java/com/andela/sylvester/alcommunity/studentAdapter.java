package com.andela.sylvester.alcommunity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.sylvester.alcommunity.data.model.DownloadImageTask;
import com.andela.sylvester.alcommunity.data.model.student;
import com.andela.sylvester.alcommunity.data.remote.APIService;
import com.andela.sylvester.alcommunity.data.remote.ApiUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;


/**
 * Created by Sylvester on 22/05/2017.
 */
public class studentAdapter   extends ArrayAdapter<student> {
    public studentAdapter(Context context, int resource) {
        super(context, resource);
    }
    public studentAdapter(Context context, int resource, List<student> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.student_list_item, null);
        }

        student p = getItem(position);

        if (p != null) {
            final ImageView picUser = (ImageView) v.findViewById(R.id.picUser);
            TextView lblName = (TextView) v.findViewById(R.id.lblListName);
            TextView lblPhone = (TextView) v.findViewById(R.id.lblListPhone);
            ProgressBar imgLoading = (ProgressBar) v.findViewById(R.id.imgLoading);
            TextView lblRG = (TextView) v.findViewById(R.id.txtRG);

            if(picUser != null){
                if(p.getPicUrl() != null){
                    Glide.with(getContext())
                            .load(p.getPicUrl())
                            .placeholder(R.drawable.ic_person_black_24dp)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(picUser);
                    imgLoading.setVisibility(View.INVISIBLE);
                    //new DownloadImageTask(picUser, imgLoading).execute(p.getPicUrl());
                }
                else {
                    imgLoading.setVisibility(View.INVISIBLE);
                    picUser.setImageResource(R.drawable.ic_person_black_24dp);
                }

            }
            if (lblName != null) {
                lblName.setText(p.getName());
            }
            if (lblPhone != null) {
                lblPhone.setText(p.getPhone());
            }
            if (lblRG != null) {
                lblRG.setText(p.getRegion());
            }

        }

        return v;
    }
}
