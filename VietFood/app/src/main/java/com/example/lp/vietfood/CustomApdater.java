package com.example.lp.vietfood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lp.vietfood.Helper.RecipeHelper;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomApdater extends BaseAdapter {
    private Context c;
    List<Recipe> recipes;
    String[] FoodName;
    String[] images;

    public CustomApdater(Context c, List<Recipe> recipes) {
        this.c = c;
        this.FoodName = RecipeHelper.getNameFromRecipes(recipes);
        this.images = RecipeHelper.getImageLinkFromRecipes(recipes);
        this.recipes = recipes;
    }




    @Override
    public int getCount() {
        return FoodName.length;
    }

    @Override
    public Object getItem(int pos) {
        return FoodName[pos];
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        final Recipe recipe = recipes.get(position);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.itemgrid, null);
        }
        TextView foodName = (TextView) v.findViewById(R.id.foodName);
        ImageView img = (ImageView) v.findViewById(R.id.imggr);


        foodName.setText(FoodName[position]);
        //img.setImageResource(images[position]);
        UrlImageViewHelper.setUrlDrawable(img, images[position]);

        // Bắt sự kiện nhấn chọn item trong Gridview
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, FoodDetail.class);
                i.putExtra("recipe", recipes.get(position));
                c.startActivity(i);
            }
        });
        return v;

    }

}

