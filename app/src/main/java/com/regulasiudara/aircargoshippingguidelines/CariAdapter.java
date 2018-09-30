package com.regulasiudara.aircargoshippingguidelines;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CariAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ArticleModel> item;

    public CariAdapter(Activity activity, List<ArticleModel> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.artikel_list, null);

//        TextView txt_nama = (TextView) convertView.findViewById(R.id.txt_nama);
//
//        txt_nama.setText(item.get(position).getNama());

        return convertView;
    }
}
