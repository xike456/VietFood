package com.example.lp.vietfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xquan on 11/24/2016.
 */

public class ListRecipeAdapter extends ArrayAdapter<Recipe> {
    List<Recipe> recipes = new ArrayList<>();
    int layoutId;
    Context context;
    public ListRecipeAdapter(Context context, int resource, List<Recipe> recipes) {
        super(context, resource, recipes);
        this.recipes = recipes;
        this.layoutId = resource;
        this.context = context;
    }

    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        convertView=inflater.inflate(layoutId, null);

        TextView tv = (TextView) convertView.findViewById(R.id.recipeName);
        tv.setText(recipes.get(position).recipeName);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FoodDetail.class);
                i.putExtra("recipe", recipes.get(position));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        return convertView;
    }
}
