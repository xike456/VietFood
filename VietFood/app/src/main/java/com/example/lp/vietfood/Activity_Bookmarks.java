package com.example.lp.vietfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_Bookmarks extends AppCompatActivity {

    ListView lv;
    List<String> bookmark = new ArrayList<>();
    List<String> data = new ArrayList<>();

    ListRecipeAdapter adapter;
    List<Recipe> recipes = new ArrayList<Recipe>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bookmarks);

        //Utils.getDatabase();

        lv=(ListView) findViewById(R.id.bookmarksList);
        adapter = new ListRecipeAdapter(Activity_Bookmarks.this, R.layout.item_recipe, recipes);
        lv.setAdapter(adapter);

        data.add("/recipes/all/8");
        data.add("/recipes/all/9");
        data.add("/recipes/all/10");

        LoadBm(MainActivity.user.bookmarks);
        if(!MainActivity.user.login){
            Toast.makeText(Activity_Bookmarks.this, "Login to use bookmark!", Toast.LENGTH_SHORT).show();
        }

    }

    public void LoadBm(List<String> bm) {
        for (String s: bm) {
            FirebaseDatabase.getInstance().getReference(s).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Recipe k = dataSnapshot.getValue(Recipe.class);
                    k.id = dataSnapshot.getKey();
                    bookmark.add(k.recipeName);
                    recipes.add(k);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}
