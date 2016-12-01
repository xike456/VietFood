package com.example.lp.vietfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class Timkiem_Fragment extends Fragment {

    ListView lv;
    SearchView sv;
    List<String> listName =new ArrayList<>();

    ArrayAdapter<String> adapter;

    List<Recipe> recipes = new ArrayList<Recipe>();
    View myView;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.timkiem_layout, container, false);
        sv=(SearchView) myView.findViewById(R.id.searchView1);
        lv=(ListView) myView.findViewById(R.id.listView1);

        adapter=new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_list_item_1, listName);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name =(String) parent.getAdapter().getItem(position);
                Intent i = new Intent(getContext(), FoodDetail.class);
                i.putExtra("recipe", recipes.get(listName.indexOf(name)));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
        LoadRecipe();

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
        return myView;
    }

    public void LoadRecipe() {
        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
        FirebaseDatabase.getInstance().getReference("recipes/all").orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listName.clear();
                for (DataSnapshot dt: dataSnapshot.getChildren()) {
                    Recipe recipe = (Recipe) dt.getValue(Recipe.class);
                    String id = dt.getKey();
                    recipe.id = id;
                    recipe.path = "/recipes/all/";
                    recipes.add(recipe);
                    listName.add(recipe.recipeName);
                }
                progress.hide();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progress.hide();
            }
        });
    }
}
