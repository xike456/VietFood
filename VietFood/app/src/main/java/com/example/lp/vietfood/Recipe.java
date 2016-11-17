package com.example.lp.vietfood;

/**
 * Created by LP on 11/1/2016.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable{
    public long view = 0;
    public String id;
    public String path = "/recipes/all/";
    public String recipeName;
    public String review;
    public String category;
    public Long time;
    public List<Long> rate;
    public List<Ingredient> ingredients;
    public List<Step> steps;
    public String difficulty;
    public String demoImage;
    public List<Comment> comment = new ArrayList<>();

    public Recipe(){}

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", review='" + review + '\'' +
                ", category='" + category + '\'' +
                ", time=" + time +
                ", rate=" + rate +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", difficulty='" + difficulty + '\'' +
                ", demoImage='" + demoImage + '\'' +
                ", comment=" + comment +
                '}';
    }
}
