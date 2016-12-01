package com.example.lp.vietfood;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LP on 10/23/2016.
 */
public class GridTab extends BaseAdapter{
    private Context c;
    String[] items;
    public GridTab(Context c, String[] items)
    {
        this.c = c;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int pos) {
        return items[pos];
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        if(v == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.itemtab, null);
        }
        TextView item = (TextView) v.findViewById(R.id.itemText);
        item.setText(items[position]);


        // Bắt sự kiện nhấn chọn item trong Gridview
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(c,"Gridtab so : " + items[position], Toast.LENGTH_SHORT).show();
                Filter_Fragment filter_fragment = new Filter_Fragment();
                Bundle bund = new Bundle();
                bund.putString("filter", items[position]);
                filter_fragment.setArguments(bund);
                android.support.v4.app.FragmentTransaction fragmentTransaction = ((FragmentActivity) c ).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, filter_fragment);
                fragmentTransaction.commit();
            }
        });
        return v;
    }
}
