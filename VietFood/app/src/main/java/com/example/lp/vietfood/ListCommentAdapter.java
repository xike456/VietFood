package com.example.lp.vietfood;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.lp.vietfood.R;

public class ListCommentAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] username;
    private final String[] comment;
    public ListCommentAdapter(Activity context,
                              String[] username, String[] comment) {
        super(context, R.layout.itemcomment, username);
        this.context = context;
        this.username = username;
        this.comment = comment;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.itemcomment, null, true);
        TextView user = (TextView) rowView.findViewById(R.id.username);
        TextView cmt = (TextView) rowView.findViewById(R.id.comment) ;

        user.setText(username[position]);
        cmt.setText(comment[position]);

        return rowView;
    }
}