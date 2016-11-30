package com.example.lp.vietfood;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Bookmarks_Fragment extends Fragment {
    ListView lv;
    List<String> bookmark = new ArrayList<>();
    List<String> data = new ArrayList<>();

    ListRecipeAdapter adapter;
    List<Recipe> recipes = new ArrayList<Recipe>();

    View myView;
    public Bookmarks_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        lv=(ListView) myView.findViewById(R.id.bookmarksList);
        adapter = new ListRecipeAdapter(myView.getContext(), R.layout.item_recipe, recipes);
        lv.setAdapter(adapter);

        data.add("/recipes/all/8");
        data.add("/recipes/all/9");
        data.add("/recipes/all/10");

        LoadBm(data);

        return myView;
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
