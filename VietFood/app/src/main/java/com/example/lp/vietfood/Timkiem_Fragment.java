package com.example.lp.vietfood;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * Created by LP on 10/22/2016.
 */
public class Timkiem_Fragment extends Fragment {

    ListView lv;
    SearchView sv;
    String[] teams={"Man Utd","Man City","Chelsea","Arsenal","Liverpool","Totenham"};
    ArrayAdapter<String> adapter;

    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.timkiem_layout, container, false);
        sv=(SearchView) myView.findViewById(R.id.searchView1);
        lv=(ListView) myView.findViewById(R.id.listView1);
        adapter=new ArrayAdapter<String>(myView.getContext(), android.R.layout.simple_list_item_1,teams);
        lv.setAdapter(adapter);
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
}
