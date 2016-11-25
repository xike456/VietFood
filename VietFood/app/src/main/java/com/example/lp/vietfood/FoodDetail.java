package com.example.lp.vietfood;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;


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
import java.util.Locale;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class FoodDetail extends AppCompatActivity  implements View.OnClickListener, OnInitListener{

    // Text to speech value
    private TextToSpeech myTTS;
    private int MY_DATA_CHECK_CODE = 0;
    // !End Text to speech value

    FirebaseDatabase firebaseDatabase;
    ImageView btnSend;

    ImageButton btnPlay, btnPause, btnNext, btnBack, btnReplay;

    EditText editComment;
    Recipe k;
    ListCommentAdapter commentAdapter;
    ListView commentsLv;
    List<Comment> commentList = new ArrayList<>();
    ImageButton btnBookmark;

    FloatingActionButton speakButton;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    FloatingActionButton fab4;

    private boolean FAB_Status = false;
    private boolean Voice_Status = false;
    private int countStep = 0;

    //Animations
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;
    Animation show_fab_4;
    Animation hide_fab_4;
    ListView list;
    ListView list2;

    ///

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


        //Text to speech Voice
        speakButton = (FloatingActionButton) findViewById(R.id.voiceStepCook);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab_3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab_4);

        speakButton.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);

        //Animations
        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);
        show_fab_4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_show);
        hide_fab_4 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab4_hide);

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
        //!End Text to speech Voice

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

        //Text to speech Voice Button Click

        if (v == speakButton){

            if (FAB_Status == false) {
                //Display FAB menu
                expandFAB();
                FAB_Status = true;
            } else {
                //Close FAB menu
                hideFAB();
                FAB_Status = false;

                myTTS.stop();
                Voice_Status = false;
            }
            //get the text entered
           /* String[] enteredText = RecipeHelper.getStepFromRecipe(k);

            int count = 0;
            for (String temp : enteredText) {
                count++;
                speakWords("Bước " + count + ": " + temp);
            }*/


        }
        String[] enteredText = RecipeHelper.getStepFromRecipe(k);

        if (v==fab1)        {

            if (Voice_Status == false)        {
                Voice_Status = true;
                int count = 0;
            for (String temp : enteredText) {
                count++;
                speakWords("Bước " + count + ": " + temp);
                fab1.setImageResource(R.drawable.ic_stop);
            }
            }
            else
            {
                myTTS.stop();
                Voice_Status = false;
                fab1.setImageResource(R.drawable.ic_play);
            }
        }

        if (v==fab2){
            if (countStep>1) {
                countStep--;
                String temp = "Bước " + countStep + ": " + enteredText[countStep-1];
                myTTS.speak(temp, TextToSpeech.QUEUE_FLUSH, null);
            }
        }

        if (v==fab3){
            if (countStep + 1 <= enteredText.length) {
                countStep++;
                String temp = "Bước " + countStep + ": " + enteredText[countStep - 1];
                myTTS.speak(temp, TextToSpeech.QUEUE_FLUSH, null);
            }
        }

        if (v==fab4){
            if (countStep == 0) return;
            String temp = "Bước " + countStep + ": " + enteredText[countStep-1];
            myTTS.speak(temp, TextToSpeech.QUEUE_FLUSH, null);
        }

        //!End Text to speech Voice
    }

    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 2.5);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.01);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 2.2);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.1);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin += (int) (fab3.getWidth() * 1.2);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.8);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);

        //Floating Action Button 4
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) fab4.getLayoutParams();
        layoutParams4.rightMargin += (int) (fab4.getWidth() * 0.01);
        layoutParams4.bottomMargin += (int) (fab4.getHeight() * 2.2);
        fab4.setLayoutParams(layoutParams4);
        fab4.startAnimation(show_fab_4);
        fab4.setClickable(true);
    }

    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 2.5);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.01);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 2.2);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.1);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 1.2);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.8);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);

        //Floating Action Button 4
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) fab4.getLayoutParams();
        layoutParams4.rightMargin -= (int) (fab4.getWidth() * 0.01);
        layoutParams4.bottomMargin -= (int) (fab4.getHeight() * 2.2);
        fab4.setLayoutParams(layoutParams4);
        fab4.startAnimation(hide_fab_4);
        fab4.setClickable(false);
    }

    //Text to speech Voice play queue
    private void speakWords(String speech) {
        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_ADD, null);
    }

    public void Bookmark(Recipe k) {
        if (MainActivity.user.login) {
            DatabaseReference user = firebaseDatabase.getReference("users/" + MainActivity.user.id + "/bookmark");
            String path = k.path + k.id;
            for (String s : MainActivity.user.bookmarks) {
                if(s.equals(path)){
                    Toast.makeText(FoodDetail.this, "Unbookmarked",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            user.push().setValue(k.path + k.id);
            MainActivity.user.bookmarks.add(k.path + k.id);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this, this);
            }
            else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    public void onInit(int initStatus) {

        Locale locale = new Locale("vi");
        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(locale)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(locale);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
}
