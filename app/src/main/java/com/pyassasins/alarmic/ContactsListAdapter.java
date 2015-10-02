package com.pyassasins.alarmic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakshmanaram on 2/10/15.
 */
public class ContactsListAdapter extends ArrayAdapter<Cont>{

    public ContactsListAdapter(Context context, ArrayList<Cont> objects){
        super(context,R.layout.contactlist, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater r = LayoutInflater.from(getContext());
        View v = r.inflate(R.layout.contactlist, parent, false);
        TextView name=(TextView)v.findViewById(R.id.contactname);
        TextView phone=(TextView)v.findViewById(R.id.contactphone);
        ImageView auth=(ImageView)v.findViewById(R.id.auth);
        Cont temp=getItem(position);
        name.setText(temp.getName());
        phone.setText(temp.getPhoneno());
        return v;
    }
}
