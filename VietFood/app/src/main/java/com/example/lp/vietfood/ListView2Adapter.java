package com.example.lp.vietfood;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lp.vietfood.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class ListView2Adapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] step;
    private final String[] imageStep;
    public ListView2Adapter(Activity context,
                            String[] step, String[] imageStep) {
        super(context, R.layout.item_cachthuchien, step);
        this.context = context;
        this.step = step;
        this.imageStep = imageStep;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_cachthuchien, null, true);
        TextView Buoc = (TextView) rowView.findViewById(R.id.txtBuocThucHien);
        ImageView imgStep = (ImageView) rowView.findViewById(R.id.imgStep);
        UrlImageViewHelper.setUrlDrawable(imgStep, imageStep[position]);
        Buoc.setText(step[position]);

        return rowView;
    }
}