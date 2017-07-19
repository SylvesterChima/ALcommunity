package com.andela.sylvester.alcommunity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andela.sylvester.alcommunity.data.model.Post;

import java.util.List;

/**
 * Created by Sylvester on 04/05/2017.
 */
public class ListAdapter extends ArrayAdapter<Post> {
    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<Post> items) {
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

        Post p = getItem(position);

        if (p != null) {
            TextView lblName = (TextView) v.findViewById(R.id.lblListName);
            TextView lblPhone = (TextView) v.findViewById(R.id.lblListPhone);

            if (lblName != null) {
                lblName.setText(p.getName());
            }

            if (lblPhone != null) {
                lblPhone.setText(p.getPhone());
            }

        }

        return v;
    }

}
