package com.pyassasins.alarmic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class HomeListAdapter extends ArrayAdapter<Alarmy> {
    public HomeListAdapter(Context context, Alarmy[] objects) {
        super(context, R.layout.home_list_layout, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater r = LayoutInflater.from(getContext());
        View v = r.inflate(R.layout.home_list_layout, parent, false);
        TextView time=(TextView)v.findViewById(R.id.htime);
        TextView to=(TextView)v.findViewById(R.id.hto);
        TextView msg=(TextView)v.findViewById(R.id.hmsg);
        Alarmy temp=getItem(position);
        time.setText(temp.getTime());
        to.setText(temp.getTo());
        msg.setText(temp.getMsg());
        return v;
    }
}
