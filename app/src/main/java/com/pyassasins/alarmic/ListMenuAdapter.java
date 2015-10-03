package com.pyassasins.alarmic;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by lakshmanaram on 2/10/15.
 */
public class ListMenuAdapter extends ArrayAdapter<String> {
    public ListMenuAdapter(Context context, String[] objects) {
        super(context, R.layout.listmenuadapter, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater r = LayoutInflater.from(getContext());
        View v = r.inflate(R.layout.listmenuadapter, parent, false);
        TextView menuname=(TextView)v.findViewById(R.id.menuname);
        ImageView img = (ImageView)v.findViewById(R.id.img);
        String temp=getItem(position);
        menuname.setText(temp);
        if(temp.equals("Text")){

        }
        else if(temp.equals("Picture")){

        }
        else if(temp.equals("Video")){

        }
        return v;
    }
}
