package com.example.lp.vietfood;

/**
 * Created by LP on 11/1/2016.
 */

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable{
    public String id;
    public String recipeName;
    public String review;
    public String category;
    public Long time;
    public List<Long> rate;
    public List<Ingredient> ingredients;
    public List<Step> steps;
    public String difficulty;
    public String demoImage;
    public List<Comment> comment;

    public Recipe(){}

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
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
