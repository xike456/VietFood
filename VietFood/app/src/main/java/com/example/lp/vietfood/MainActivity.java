package com.example.lp.vietfood;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //aSYNTASK

    MyAsyncTask myAsyncTask;
    //GridView
    GridView gv;
    CustomApdater adapter;


    //Tab Database
    GridView gvtab1;
    GridView gvtab2;
    GridView gvtab3;
    GridView gvtab4;
    String[] itemsthucdon = {"Món Khai Vị","Món Tráng Miệng","Món Chay","Món Chính","Món Ăn Sáng","Nhanh và Dễ","Thức Uống", "Bánh - Bánh Ngọt","Món Ăn Cho Trẻ"};
    String[] itemsloaimon = {"Salad", "Nước Chấm", "Canh", "Lẩu", "Nộm - Gỏi", "Soup - Cháo", "Nem - Chả", "Bánh Ngọt", "Sinh Tố", "Nước Ép", "Cocktail", "Kem", "Chè", "Mứt", "Đồ Sống", "Snacks", "Cupcake", "Pasta", "Hủ Tiếu", "Bún - Mì - Phở", "Đồ Uống"};
    String[] itemsamthuc = {"Việt Nam", "Thái Lan", "Ý", "Hàn Quốc", "Âu", "Nhật", "Trung Quốc", "Ấn Độ", "Singapore", "Pháp", "Mỹ", "Nga", "Brazil"};
    String[] itemsmucdich = {"Ăn Sáng", "Ăn Trưa", "Ăn Kiêng", "Giảm Cân", "Cho Phái Mạnh", "Ăn Vặt", "Tiệc", "Ăn Chay", "Chữa Bệnh", "Ăn Gia Đình", "Phụ Nữ Sau Khi Sinh", "Phụ Nữ Mang Thai", "Tăng Cân", "Cho trẻ em", "Ăn Tối", "Tốt Cho Tim Mạch"};

    //Grid database
    String[] FoodName = {"Mực Nướng","Bò Xào","Canh Chua","Thịt Kho","Rau Luộc","Thính Rang"};
    String[] Images;// = {R.drawable.muc,R.drawable.boxao,R.drawable.canh,R.drawable.thit,R.drawable.rau,R.drawable.thinh};
    //Slide Database
    ViewPager viewPager;
    SlideAdapter slideAdapter;
    String[] imgs; //= {R.drawable.daily1,R.drawable.daily2,R.drawable.daily3,R.drawable.daily4};
    String[] SlideFoodName = {"Pizza Thập Cẩm","Humberger Bò","Trái Cây Dĩa","Cá Thu Nướng"};

    TabHost tabHost;

    //Firebase instance
    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    //List recipes
    private List<Recipe> hotRecipes = new ArrayList<Recipe>();
    private List<Recipe> mostViewRecipes = new ArrayList<Recipe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Index Fragment When Open App
        GetIndexFragment();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        HanderIndex();
        LoadHotRecipe();
    }

    //Load data for slide view
    public void LoadHotRecipe(){
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
                slideAdapter = new SlideAdapter(getApplicationContext(), hotRecipes);
                viewPager = (ViewPager) findViewById(R.id.view_paper);
                viewPager.setAdapter(slideAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void LoadMostView(final Activity contextParent){
        firebaseDatabase.getReference("/recipes/mostView/").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dt: dataSnapshot.getChildren()){
                    Recipe a = (Recipe) dt.getValue(Recipe.class);
                    String k = dt.getKey();
                    a.id = k;
                    mostViewRecipes.add(a);
                }
                gv = (GridView) contextParent.findViewById(R.id.gridView);
                CustomApdater adapter = new CustomApdater(contextParent, mostViewRecipes);
                gv.setAdapter(adapter);
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
    public void GetVideoFragment(){
        Video_Fragment video_fragment = new Video_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, video_fragment);
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
        Aboutme_Fragment aboutme_fragment = new Aboutme_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, aboutme_fragment);
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
            for (int i = 0; i <= 2; i++) {
                SystemClock.sleep(200);
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
            if(number == 1){
                LoadMostView(contextParent);
//                gv = (GridView) contextParent.findViewById(R.id.gridView);
//                CustomApdater adapter = new CustomApdater(contextParent, FoodName, Images);
//                gv.setAdapter(adapter);
            }
            if(number == 2){
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
                GridTab adaptertab1 = new GridTab(getApplication(), itemsthucdon);
                gvtab1.setAdapter(adaptertab1);

                gvtab2 = (GridView) findViewById(R.id.gridViewFragmentTwo);
                GridTab adaptertab2 = new GridTab(getApplication(), itemsloaimon);
                gvtab2.setAdapter(adaptertab2);

                gvtab3 = (GridView) findViewById(R.id.gridViewFragmentThree);
                GridTab adaptertab3 = new GridTab(getApplication(), itemsamthuc);
                gvtab3.setAdapter(adaptertab3);

                gvtab4 = (GridView) findViewById(R.id.gridViewFragmentFour);
                GridTab adaptertab4 = new GridTab(getApplication(), itemsmucdich);
                gvtab4.setAdapter(adaptertab4);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Hàm này được thực hiện khi tiến trình kết thúc
            //Ở đây mình thông báo là đã "Finshed" để người dùng biết
            Toast.makeText(contextParent, "Okie, Finished", Toast.LENGTH_SHORT).show();
        }
    }



        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        if (id == R.id.action_settings) {
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


        } else if (id == R.id.nav_video) {
            GetVideoFragment();

        } else if (id == R.id.nav_search) {
            GetTimKiemFragment();

        } else if (id == R.id.nav_aboutme) {
            GetAboutMeFragment();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
