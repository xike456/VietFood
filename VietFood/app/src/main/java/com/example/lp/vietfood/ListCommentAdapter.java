package com.example.lp.vietfood;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.lp.vietfood.R;

        import java.util.List;

public class ListCommentAdapter extends BaseAdapter {

    private final Activity context;
    private final List<Comment> listComment;
    public ListCommentAdapter(Activity context,
                              List<Comment> listComment) {
        this.listComment = listComment;
        //super(context, R.layout.itemcomment,);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.itemcomment, null, true);
        TextView user = (TextView) rowView.findViewById(R.id.username);
        TextView cmt = (TextView) rowView.findViewById(R.id.comment) ;

        user.setText(listComment.get(position).email);
        cmt.setText(listComment.get(0).comment);

        return rowView;
    }
}