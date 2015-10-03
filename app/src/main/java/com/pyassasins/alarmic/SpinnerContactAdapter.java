package com.pyassasins.alarmic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lakshmanaram on 3/10/15.
 */
public class SpinnerContactAdapter extends ArrayAdapter<Cont> {
    public SpinnerContactAdapter(Context context,ArrayList<Cont> objects){
        super(context,R.layout.contactslistspinner,objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater r = LayoutInflater.from(getContext());
        View v = r.inflate(R.layout.contactslistspinner, parent, false);
        TextView name=(TextView)v.findViewById(R.id.contactname1);
        TextView phone=(TextView)v.findViewById(R.id.contactphone1);
        Cont temp=getItem(position);
        name.setText(temp.getName());
        phone.setText(temp.getPhoneno());
        return v;
    }
}
