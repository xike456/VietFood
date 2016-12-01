package com.example.lp.vietfood;



import android.app.ProgressDialog;
import android.os.Bundle;
        import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ListView;
        import android.widget.Toast;

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
public class Filter_Fragment extends Fragment {
    ListView lv;
    List<String> bookmark = new ArrayList<>();
    List<String> data = new ArrayList<>();

    ListRecipeAdapter adapter;
    List<Recipe> recipes = new ArrayList<Recipe>();
    String filter;

    String[] filters;

    View myView;
    public Filter_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_filter, container, false);
        lv=(ListView) myView.findViewById(R.id.filterList);
        adapter = new ListRecipeAdapter(myView.getContext(), R.layout.item_recipe, recipes);
        lv.setAdapter(adapter);

        Bundle bund = getArguments();
        filter = bund.getString("filter");
        filters = filter.split(" ");

        data.add("/recipes/all/8");
        data.add("/recipes/all/9");
        data.add("/recipes/all/10");

        LoadRecipe(filters);
        return myView;
    }

    public void LoadRecipe(final String[] filters) {
        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Loading...");
        progress.show();
        FirebaseDatabase.getInstance().getReference("recipes/all").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt: dataSnapshot.getChildren()) {
                    Recipe a = (Recipe) dt.getValue(Recipe.class);
                    for (String s: filters) {
                        if(a.category.contains(s)){
                            String k = dt.getKey();
                            a.id = k;
                            a.path = "/recipes/all/";
                            recipes.add(a);
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                progress.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progress.hide();
            }
        });
    }
}
