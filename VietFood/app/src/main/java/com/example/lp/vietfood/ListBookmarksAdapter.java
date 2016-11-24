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

public class ListBookmarksAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    private final String[] imageFood;
    public ListBookmarksAdapter(Activity context, String[] name, String[] imageFood) {
        super(context, R.layout.itembookmarks, name);
        this.context = context;
        this.name = name;
        this.imageFood = imageFood;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.itembookmarks, null, true);
        TextView Buoc = (TextView) rowView.findViewById(R.id.txtitemnamebookmarks);
        ImageView imgStep = (ImageView) rowView.findViewById(R.id.imgitembookmarks);
        UrlImageViewHelper.setUrlDrawable(imgStep, imageFood[position]);
        Buoc.setText(name[position]);

        return rowView;
    }
}