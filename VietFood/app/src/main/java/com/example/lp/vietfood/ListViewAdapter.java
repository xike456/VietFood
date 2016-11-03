package com.example.lp.vietfood;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lp.vietfood.R;

public class ListViewAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] nguyenlieu;
    private final String[] soluong;
    public ListViewAdapter(Activity context,
                      String[] nguyenlieu, String[] soluong) {
        super(context, R.layout.item_nguyenlieu, nguyenlieu);
        this.context = context;
        this.nguyenlieu = nguyenlieu;
        this.soluong = soluong;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_nguyenlieu, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtNguyenLieu);
        TextView txtsoluong = (TextView) rowView.findViewById(R.id.txtSoluong) ;

        txtTitle.setText(nguyenlieu[position]);
        txtsoluong.setText(soluong[position]);

        return rowView;
    }
}