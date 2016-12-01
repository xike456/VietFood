package com.example.lp.vietfood;

/**
 * Created by LP on 11/11/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.util.Log;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private static FirebaseDatabase firebaseDatabase;
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private List<Recipe> hotRecipes = new ArrayList<Recipe>();
    private List<Recipe> mostViewRecipes = new ArrayList<Recipe>();
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    MainActivity.user.name = user.getEmail().toString();
                    MainActivity.user.login = true;
                    MainActivity.user.id = user.getUid();
                    DatabaseReference userBookmark = FirebaseDatabase.getInstance().getReference("users/" + firebaseAuth.getCurrentUser().getUid()+ "/bookmark");
                    userBookmark.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            MainActivity.user.bookmarks = new ArrayList<String>();
                            for (DataSnapshot s: dataSnapshot.getChildren()) {
                                MainActivity.user.bookmarks.add(s.getValue(String.class));
                            }
                            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over
                                    // Start your app main activity
                                    startMainActivity();
                                }
                            }, SPLASH_TIME_OUT);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Log.d("Login", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            Intent i = new Intent(SplashScreen.this, Login.class);
                            startActivity(i);
                            // close this activity
                            finish();
                        }
                    }, SPLASH_TIME_OUT);
                    // User is signed out
                    Log.d("Login", "onAuthStateChanged:signed_out");
                }
            }
        };


    }
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and lauch main activity
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            hotRecipes = LoadHotRecipe();
            mostViewRecipes = LoadMostView();
            Bundle bundle = new Bundle();
            i.putExtra("hot",(Serializable)hotRecipes );
            i.putExtra("most", (Serializable)mostViewRecipes);
            startActivity(i);

            // close this activity
            finish();
        }

    }
    public List<Recipe> LoadHotRecipe(){
        firebaseDatabase.getReference("/recipes/hot/").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dt: dataSnapshot.getChildren()){
                    Recipe a = (Recipe) dt.getValue(Recipe.class);
                    String k = dt.getKey();
                    a.id = k;
                    hotRecipes.add(a);
                }
//                SlideFoodName = RecipeHelper.getNameFromRecipes(hotRecipes);
//                imgs = RecipeHelper.getImageLinkFromRecipes(hotRecipes);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return hotRecipes;
    }

    public List<Recipe> LoadMostView(){
        firebaseDatabase.getReference("/recipes/mostView/").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dt: dataSnapshot.getChildren()){
                    Recipe a = (Recipe) dt.getValue(Recipe.class);
                    String k = dt.getKey();
                    a.id = k;
                    mostViewRecipes.add(a);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return mostViewRecipes;
    }
    public void startMainActivity() {
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
}