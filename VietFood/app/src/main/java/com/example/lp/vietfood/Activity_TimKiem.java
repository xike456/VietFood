package com.example.lp.vietfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static com.example.lp.vietfood.R.drawable.bookmark;
import static java.security.AccessController.getContext;

public class Activity_TimKiem extends AppCompatActivity {

    ListView lv;
    SearchView sv;
    List<String> listName =new ArrayList<>();

    ArrayAdapter<String> adapter;

    List<Recipe> recipes = new ArrayList<Recipe>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timkiem_layout);

        sv=(SearchView) findViewById(R.id.searchView1);
        lv=(ListView) findViewById(R.id.listView1);

        adapter=new ArrayAdapter<String>(Activity_TimKiem.this, android.R.layout.simple_list_item_1, listName);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name =(String) parent.getAdapter().getItem(position);
                Intent i = new Intent(getApplicationContext(), FoodDetail.class);
                i.putExtra("recipe", recipes.get(listName.indexOf(name)));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
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

    }

    public void LoadRecipe() {
        final ProgressDialog progress = new ProgressDialog(Activity_TimKiem.this);
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
