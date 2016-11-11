package com.example.lp.vietfood.Helper;

import com.example.lp.vietfood.Ingredient;
import com.example.lp.vietfood.Recipe;
import com.example.lp.vietfood.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xquan on 11/3/2016.
 */

public class RecipeHelper {
    public static String[] getNameFromRecipes(List<Recipe> recipes){
        List<String> temp = new ArrayList<String>();
        for (Recipe r:recipes) {
            temp.add(r.recipeName);
        }
        return temp.toArray(new String[0]);
    }

    public static String[] getImageLinkFromRecipes(List<Recipe> recipes){
        List<String> temp = new ArrayList<String>();
        for (Recipe r:recipes) {
            temp.add(r.demoImage);
        }
        return temp.toArray(new String[0]);
    }

    public static String[] getIngeNameFromRecipe(Recipe recipe){
        List<String> temp = new ArrayList<String>();
        for (Ingredient r:recipe.ingredients) {
            temp.add(r.name);
        }
        return temp.toArray(new String[0]);
    }

    public static String[] getIngeAmountFromRecipe(Recipe recipe){
        List<String> temp = new ArrayList<String>();
        for (Ingredient r:recipe.ingredients) {
            temp.add(r.amount);
        }
        return temp.toArray(new String[0]);
    }

    public static String[] getStepFromRecipe(Recipe recipe){
        List<String> temp = new ArrayList<String>();
        for (Step r:recipe.steps) {
            temp.add(r.step);
        }
        return temp.toArray(new String[0]);
    }

    public static String[] getStepImgFromRecipe(Recipe recipe){
        List<String> temp = new ArrayList<String>();
        for (Step r:recipe.steps) {
            temp.add(r.image);
        }
        return temp.toArray(new String[0]);
    }

    public static String[] getReviewFromRecipes(List<Recipe> recipes){
        List<String> temp = new ArrayList<String>();
        for (Recipe r:recipes) {
            temp.add(r.review);
        }
        return temp.toArray(new String[0]);
    }
}
