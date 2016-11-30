package com.example.lp.vietfood;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lp.vietfood.Helper.RecipeHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Filter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static User user = new User();

    MyAsyncTask myAsyncTask;
    //GridView
    GridView gv;
    CustomApdater adapter;


    //Tab Database
    GridView gvtab1;
    GridView gvtab2;
    GridView gvtab3;
    GridView gvtab4;
    String[] itemsthucdon = {"Món Khai Vị","Món Tráng Miệng","Món Chay","Món Chính","Món Ăn Sáng","Nhanh và Dễ","Thức Uống", "Bánh - Bánh Ngọt"};
    String[] itemsloaimon = {"Salad", "Nước Chấm", "Canh", "Lẩu", "Nộm - Gỏi", "Soup - Cháo", "Nem - Chả"};
    String[] itemsamthuc = {"Việt Nam", "Thái Lan", "Ý", "Hàn Quốc", "Âu", "Nhật", "Trung Quốc", "Ấn Độ"};
    String[] itemsmucdich = {"Ăn Sáng", "Ăn Trưa", "Ăn Kiêng", "Giảm Cân", "Cho Phái Mạnh", "Ăn Vặt", "Tiệc", "Ăn Chay"};

    //Grid database
    String[] FoodName = {"Mực Nướng","Bò Xào","Canh Chua","Thịt Kho","Rau Luộc","Thính Rang"};
    String[] Images;// = {R.drawable.muc,R.drawable.boxao,R.drawable.canh,R.drawable.thit,R.drawable.rau,R.drawable.thinh};
    //Slide Database
    ViewPager viewPager;
    SlideAdapter slideAdapter;
    String[] imgs; //= {R.drawable.daily1,R.drawable.daily2,R.drawable.daily3,R.drawable.daily4};

    TabHost tabHost;

    //Firebase instance
    private static FirebaseDatabase firebaseDatabase;
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    //List recipes
    private List<Recipe> hotRecipes = new ArrayList<Recipe>();
    private List<Recipe> mostViewRecipes = new ArrayList<Recipe>();

    Resources r = Resources.getSystem();
    float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics());

    ProgressDialog hotDialog;
    ProgressDialog mostviewDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Index Fragment When Open App
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        GetIndexFragment();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        HanderIndex();

    }

    //Load data for slide view
    public void LoadHotRecipe(){
        hotDialog = new ProgressDialog(this);
        hotDialog.setMessage("Loading...");
        hotDialog.setCancelable(false);
        hotDialog.show();
        firebaseDatabase.getReference("/recipes/hot/").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dt: dataSnapshot.getChildren()){
                    Recipe a = (Recipe) dt.getValue(Recipe.class);
                    String k = dt.getKey();
                    a.id = k;
                    a.path = "/recipes/hot/";
                    hotRecipes.add(a);
                }
                slideAdapter = new SlideAdapter(getApplicationContext(), hotRecipes);
                viewPager = (ViewPager) findViewById(R.id.view_paper);
                viewPager.setAdapter(slideAdapter);
                hotDialog.hide();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void LoadMostView(final Activity contextParent){
        mostviewDialog = new ProgressDialog(this);
        mostviewDialog.setMessage("Loading...");
        mostviewDialog.setCancelable(false);
        mostviewDialog.show();
        firebaseDatabase.getReference("/recipes/all/").orderByChild("view").limitToLast(9).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dt: dataSnapshot.getChildren()){
                    Recipe a = (Recipe) dt.getValue(Recipe.class);
                    String k = dt.getKey();
                    a.id = k;
                    a.path = "/recipes/all/";
                    mostViewRecipes.add(a);
                }
                gv = (GridView) contextParent.findViewById(R.id.gridView);
                CustomApdater adapter = new CustomApdater(contextParent, mostViewRecipes);
                gv.setAdapter(adapter);
                mostviewDialog.hide();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//MOT SO FRAGMENT LINH TINH
    public void GetIndexFragment(){
        Index_Fragment index_fragment = new Index_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, index_fragment);
        fragmentTransaction.commit();
    }
    public void GetBookmarksFragment(){
        Bookmarks_Fragment bookmarks_fragment = new Bookmarks_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, bookmarks_fragment);
        fragmentTransaction.commit();
    }
    public void GetTimKiemFragment(){
        Timkiem_Fragment timkiem_fragment = new Timkiem_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, timkiem_fragment);
        fragmentTransaction.commit();
    }
    public void GetCongThucFragment(){
        Congthuc_Fragment congthuc_fragment = new Congthuc_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, congthuc_fragment);
        fragmentTransaction.commit();
    }
    public void GetAboutMeFragment(){
        Intent intent = new Intent(this, ActivityAboutme.class);
        startActivity(intent);
    }

    public void GetFilterFragment(String filter){
        Filter_Fragment filter_fragment = new Filter_Fragment();
        Bundle bund = new Bundle();
        bund.putString("filter", filter);
        filter_fragment.setArguments(bund);
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, filter_fragment);
        fragmentTransaction.commit();
    }

    public void HanderIndex(){
        //UI Grid and ViewPager
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                //Gridview
                myAsyncTask = new MyAsyncTask(MainActivity.this);
                //Gọi hàm execute để kích hoạt tiến trình
                myAsyncTask.execute();
                //
            }
        });
    }



//ASYNCTASK

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        Activity contextParent;

        public MyAsyncTask(Activity contextParent) {
            this.contextParent = contextParent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Hàm này sẽ chạy đầu tiên khi AsyncTask này được gọi
            //Ở đây mình sẽ thông báo quá trình load bắt đâu "Start"
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Hàm được được hiện tiếp sau hàm onPreExecute()
            //Hàm này thực hiện các tác vụ chạy ngầm
            //Tuyệt đối k vẽ giao diện trong hàm này
            for (int i = 0; i <= 1; i++) {
                SystemClock.sleep(100);
                //khi gọi hàm này thì onProgressUpdate sẽ thực thi
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
            super.onProgressUpdate(values);
            //Thông qua contextCha để lấy được control trong MainActivity
            int number = values[0];
//            if(number == 0) {
//                slideAdapter = new SlideAdapter(getApplicationContext(),imgs, SlideFoodName);
//                viewPager = (ViewPager) findViewById(R.id.view_paper);
//                viewPager.setAdapter(slideAdapter);
//            }
            if(number == 0){
                TabHost host = (TabHost)findViewById(R.id.tabHost);
                host.setup();

                //Tab 1
                TabHost.TabSpec spec = host.newTabSpec("Thực Đơn");
                spec.setContent(R.id.tab1);
                spec.setIndicator("Thực Đơn");
                host.addTab(spec);

                //Tab 2
                spec = host.newTabSpec("Loại Món");
                spec.setContent(R.id.tab2);
                spec.setIndicator("Loại Món");
                host.addTab(spec);

                //Tab 3
                spec = host.newTabSpec("Ẩm Thực");
                spec.setContent(R.id.tab3);
                spec.setIndicator("Ẩm Thực");
                host.addTab(spec);

                //Tab 4
                spec = host.newTabSpec("Mục Đích");
                spec.setContent(R.id.tab4);
                spec.setIndicator("Mục Đích");
                host.addTab(spec);

                host.setCurrentTab(1);

                gvtab1 = (GridView) findViewById(R.id.gridViewFragmentOne);
                GridTab adaptertab1 = new GridTab(MainActivity.this, itemsthucdon);
                gvtab1.setAdapter(adaptertab1);

                gvtab2 = (GridView) findViewById(R.id.gridViewFragmentTwo);
                GridTab adaptertab2 = new GridTab(MainActivity.this, itemsloaimon);
                gvtab2.setAdapter(adaptertab2);

                gvtab3 = (GridView) findViewById(R.id.gridViewFragmentThree);
                GridTab adaptertab3 = new GridTab(MainActivity.this, itemsamthuc);
                gvtab3.setAdapter(adaptertab3);

                gvtab4 = (GridView) findViewById(R.id.gridViewFragmentFour);
                GridTab adaptertab4 = new GridTab(MainActivity.this, itemsmucdich);
                gvtab4.setAdapter(adaptertab4);

                ImageView imgnotif = (ImageView) findViewById(R.id.searchbutton);
                imgnotif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetTimKiemFragment();
                    }
                });

                LoadMostView(contextParent);
                LoadHotRecipe();

            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Hàm này được thực hiện khi tiến trình kết thúc
            //Ở đây mình thông báo là đã "Finshed" để người dùng biết
        }
    }



        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Click Yes To Exit !!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.searchbutton) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_index) {
            GetIndexFragment();
            HanderIndex();
        } else if (id == R.id.nav_search) {
            GetTimKiemFragment();

        } else if (id == R.id.nav_aboutme) {
            GetAboutMeFragment();

        } else if (id == R.id.nav_share) {
            //GetFilterFragment("Chè Cháo");
        }
        else if (id == R.id.nav_bookmarks) {
            GetBookmarksFragment();
        }
        else if (id == R.id.nav_login) {
            startLogin();
        }
        else if (id == R.id.nav_logout) {
            startLogout();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startLogout() {
        if(!user.login){
            Toast.makeText(this, "Login first!", Toast.LENGTH_SHORT).show();
            return;
        }
        user.login = false;
        user.name = "No name";
        user.id = "";
        firebaseAuth.signOut();
        startLogin();
    }

    public void startLogin() {
        if (user.login) {
            Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        finish();
    }
}
