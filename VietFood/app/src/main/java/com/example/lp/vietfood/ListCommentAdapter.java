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

public class ListCommentAdapter extends ArrayAdapter<Comment> {

    private final Activity context;
    private final List<Comment> listComment;
    int layoutId;

    public ListCommentAdapter(Activity context, int layoutId,
                              List<Comment> listComment) {
        super(context, R.layout.itemcomment, listComment);
        this.listComment = listComment;
        this.layoutId = layoutId;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        view=inflater.inflate(layoutId, null);

        TextView user = (TextView) view.findViewById(R.id.username);
        TextView cmt = (TextView) view.findViewById(R.id.comment) ;

        user.setText(listComment.get(position).email);
        cmt.setText(listComment.get(position).comment);

        return view;
    }
}