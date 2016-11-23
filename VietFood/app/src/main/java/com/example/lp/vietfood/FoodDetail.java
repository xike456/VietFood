package com.example.lp.vietfood;

import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lp.vietfood.Helper.RecipeHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDetail extends AppCompatActivity  implements View.OnClickListener{

    FirebaseDatabase firebaseDatabase;
    ImageView btnSend;
    EditText editComment;
    Recipe k;
    ListCommentAdapter commentAdapter;
    ListView commentsLv;
    List<Comment> commentList = new ArrayList<>();
    ImageButton btnBookmark;

    ListView list;
    ListView list2;
    String[] tenNguyenLieu = {
            "Thịt Gà",
            "Trứng",
            "Dầu Ăn",
            "Mắm",
            "Tỏi",
            "Ớt",
            "Đường"
    } ;
    String[] soluongNguyenLieu = {
            "1 kg",
            "2 quả",
            "3 thìa",
            "2 thìa",
            "2 củ",
            "3 quả",
            "2 thìa"
    } ;

    String[] step = {
            "Thịt ba chỉ rửa sạch, để ráo rồi thái miếng vừa ăn.\n" +
                    "Hành tây bóc vỏ, thái miếng nhỏ.\n" +
                    "Gừng, tỏi rửa sạch, đập dập rồi băm nhỏ. Ớt xanh thái miếng vát.",
            "Dùng một chảo lớn, cho tất cả các nguyên liệu vừa sơ chế vào cùng nhau.\n" +
                    "Sau đó thêm tương ớt, bột ớt, đường trắng, nước tương, tiêu xay và dầu mè vào.\n",
            "Đảo chảo thịt cùng các nguyên liệu trên bếp, điều chỉnh cho lửa to, đảo đều tay để các nguyên liệu trộn đều vào nhau.\n" +
                    "Đảo liên tục trong khoảng 10 phút, khi các nguyên liệu đã chín hết, thịt săn lại thì tắt bếp.\n" +
                    "Múc thịt ra đĩa, có thể rắc thêm chút vừng rang cho thơm, đúng điệu Hàn Quốc và dùng kèm cùng cơm nóng.\n" +
                    "Món thịt xào hành tây kiểu Hàn có nhiều gia vị khác hẳn so với món thịt xào thông thường, đem đến cho chúng ta cảm giác lạ miệng, thơm ngon. Chúc các bạn thành công với món thịt xào hành tây kiểu Hàn nhé!"
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        TabHost host = (TabHost)findViewById(R.id.tabHostDetail);
        host.setup();

        k = (Recipe) getIntent().getSerializableExtra("recipe");

        final ImageView imgView = (ImageView) findViewById(R.id.imgHeaderDetail);
        final TextView txtView = (TextView) findViewById(R.id.textDetailTenmon);
        final TextView url = (TextView) findViewById(R.id.Url);
        url.setText( k.recipeName + '\n' + "Viet Food" + '\n'+ '\n'+ k.demoImage);
        UrlImageViewHelper.setUrlDrawable(imgView, k.demoImage);
        txtView.setText(k.recipeName);

        TabHost.TabSpec spec = host.newTabSpec("Nguyên Liệu");
        spec.setContent(R.id.tab_fooddetail1);
        spec.setIndicator("Nguyên Liệu");
        host.addTab(spec);

        ListViewAdapter adapter = new
                ListViewAdapter(this, RecipeHelper.getIngeNameFromRecipe(k), RecipeHelper.getIngeAmountFromRecipe(k));
        list=(ListView)findViewById(R.id.listViewtabFoodDetail);
        list.setAdapter(adapter);

        //Tab 2
        spec = host.newTabSpec("Cách Làm");
        spec.setContent(R.id.tab_fooddetail2);
        spec.setIndicator("Cách Làm");
        host.addTab(spec);

        ListView2Adapter adapter2 = new
                ListView2Adapter(this, RecipeHelper.getStepFromRecipe(k), RecipeHelper.getStepImgFromRecipe(k));
        list2=(ListView)findViewById(R.id.listViewtabFoodDetail2);
        list2.setAdapter(adapter2);

        //Tab 3
        spec = host.newTabSpec("Review");
        spec.setContent(R.id.tab_fooddetail3);
        spec.setIndicator("Review");
        host.addTab(spec);

        TextView review = (TextView) findViewById(R.id.textReview);
        review.setText(k.review);

        firebaseDatabase = FirebaseDatabase.getInstance();
        increaseView(k);
        loadComment();
        btnSend = (ImageView) findViewById(R.id.send);
        editComment = (EditText) findViewById(R.id.edittextcomment);
        editComment.setSelected(false);

        btnSend.setOnClickListener(this);

        host.setCurrentTab(0);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imgButtonShare);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, url.getText());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });

        btnBookmark = (ImageButton) findViewById(R.id.imgButtonBookmark);
        btnBookmark.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSend){
            if(editComment.getText().toString()!=""){
                Comment(k, editComment.getText().toString());
                editComment.setText("");
            }
            else            {
                Toast.makeText(FoodDetail.this, "commend fail",
                        Toast.LENGTH_SHORT).show();
            }
        }

        if(v == btnBookmark){
            Bookmark(k);
        }
    }

    public void Bookmark(Recipe k) {
        if (MainActivity.user.login) {
            DatabaseReference user = firebaseDatabase.getReference("users/" + MainActivity.user.id + "/bookmark");

            for (String s : MainActivity.user.bookmarks) {
                if(s == (k.path + k.id)){
                    Toast.makeText(FoodDetail.this, "Unbookmarked",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            user.push().setValue(k.path + k.id);
            Toast.makeText(FoodDetail.this, "Bookmarked",
                    Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(FoodDetail.this, "Login to bookmark",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void Comment(Recipe k, String comment){
        Map<String,Object> comments = AddComment(k, comment);
        DatabaseReference ref = firebaseDatabase.getReference(k.path);
        DatabaseReference ref2 = ref.child(k.id);
        ref2.updateChildren(comments);
    }

    public Map AddComment(Recipe k, String comment) {
        Map<String,Object> comments = new HashMap<String,Object>();
        List<Comment> listComment = new ArrayList<Comment>();
        if (k.comment != null) {
            for (Comment temp: k.comment) {
                listComment.add(temp);
            }
        }

        Comment c = new Comment();
        c.comment = comment;
        c.email = MainActivity.user.name;
        listComment.add(c);
        k.comment.add(c);
        comments.put("comment", listComment);
        commentList.add(c);
        ((BaseAdapter)commentsLv.getAdapter()).notifyDataSetChanged();
        commentsLv.setSelection(commentsLv.getAdapter().getCount() -1);
        return comments;
    }

    public void increaseView(Recipe k) {
        Map<String, Object> view = new HashMap<>();
        long temp = k.view +=1;
        view.put("view", temp);
        DatabaseReference ref = firebaseDatabase.getReference(k.path);
        DatabaseReference ref2 = ref.child(k.id);
        ref2.updateChildren(view);
    }

    public void loadComment() {
        DatabaseReference ref = firebaseDatabase.getReference(k.path + k.id + "/comment");
        commentList = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dt: dataSnapshot.getChildren()){
                    Comment a = (Comment) dt.getValue(Comment.class);
                    commentList.add(a);
                }
                commentAdapter = new ListCommentAdapter(FoodDetail.this, R.layout.itemcomment, commentList);
                commentsLv = (ListView) findViewById(R.id.listComment);
                commentsLv.setAdapter(commentAdapter);
                commentsLv.setSelection(commentsLv.getAdapter().getCount()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
