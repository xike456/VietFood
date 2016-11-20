package com.example.lp.vietfood;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lp.vietfood.Helper.RecipeHelper;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

/**
 * Created by LP on 10/21/2016.
 */
// ADAPTER DUNG CHO SLIDE O DAU TRANG CHU
public class SlideAdapter extends PagerAdapter{

    private Context context;
    String[] imgs;
    String[] textSlide;
    List<Recipe> recipes;
    public SlideAdapter(Context context, List<Recipe> recipes)
    {
        this.context = context;
        this.imgs = RecipeHelper.getImageLinkFromRecipes(recipes);
        this.textSlide = RecipeHelper.getNameFromRecipes(recipes);
        this.recipes = recipes;
    }
    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.swipe,container,false);
        ImageView img = (ImageView)v.findViewById(R.id.imageView);
        //img.setImageResource(imgs[position]);
        UrlImageViewHelper.setUrlDrawable(img, imgs[position]);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        TextView foodName = (TextView) v.findViewById(R.id.textSlide);
        foodName.setText(textSlide[position]);

        // Bắt sự kiện nhấn chọn item trong Slide
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodDetail.class);
                i.putExtra("recipe", recipes.get(position));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }


}
