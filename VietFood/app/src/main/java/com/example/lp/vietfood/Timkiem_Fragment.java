package com.example.lp.vietfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import com.example.lp.vietfood.Helper.RecipeHelper;
import com.google.android.gms.signin.internal.RecordConsentRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LP on 10/22/2016.
 */
public class Timkiem_Fragment extends Fragment {

    ListView lv;
    SearchView sv;
    String[] teams={"Canh","Man City","Chelsea","Arsenal","Liverpool","Totenham"};
    RecipeHelper helper = new RecipeHelper();
    List<String> bookmark = new ArrayList<>();
    List<String> data = new ArrayList<>();

    ArrayAdapter<String> adapter;
    View myView;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.timkiem_layout, container, false);
        sv=(SearchView) myView.findViewById(R.id.searchView1);
        lv=(ListView) myView.findViewById(R.id.listView1);

        data.add("/recipes/all/8");
        data.add("/recipes/all/9");


        adapter=new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_list_item_1, data);
        lv.setAdapter(adapter);

        data.add("/recipes/all/10");
        adapter.notifyDataSetChanged();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                // TODO Auto-generated method stub
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });
        LoadBm(data);
        return myView;
    }

    public void LoadBm(List<String> bm) {
        for (String s: bm) {
            FirebaseDatabase.getInstance().getReference(s).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Recipe k = dataSnapshot.getValue(Recipe.class);
                    bookmark.add(k.recipeName);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
