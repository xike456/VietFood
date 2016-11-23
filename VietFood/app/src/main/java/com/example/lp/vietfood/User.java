package com.example.lp.vietfood;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xquan on 11/16/2016.
 */

public class User {
    public String name;
    public String id;
    public boolean login;
    public List<String> bookmarks;

    public User(){
        name = "No name";
        id="";
        login = false;
        bookmarks = new ArrayList<>();
    }

    public User(String name){
        this.name = name;
    }
}
