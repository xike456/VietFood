package com.example.lp.vietfood;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.lp.vietfood.Helper.RecipeHelper;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class FoodDetail extends AppCompatActivity {

    ListView list;
    ListView list2;
    String[] tenNguyenLieu = {
            "Thịt Gà",
            "Trứng",
            "Dầu Ăn",
            "Mắm",
            "Tỏi",
            "Ớt",
            "Đường"
    } ;
    String[] soluongNguyenLieu = {
            "1 kg",
            "2 quả",
            "3 thìa",
            "2 thìa",
            "2 củ",
            "3 quả",
            "2 thìa"
    } ;

    String[] step = {
            "Thịt ba chỉ rửa sạch, để ráo rồi thái miếng vừa ăn.\n" +
                    "Hành tây bóc vỏ, thái miếng nhỏ.\n" +
                    "Gừng, tỏi rửa sạch, đập dập rồi băm nhỏ. Ớt xanh thái miếng vát.",
            "Dùng một chảo lớn, cho tất cả các nguyên liệu vừa sơ chế vào cùng nhau.\n" +
                    "Sau đó thêm tương ớt, bột ớt, đường trắng, nước tương, tiêu xay và dầu mè vào.\n",
            "Đảo chảo thịt cùng các nguyên liệu trên bếp, điều chỉnh cho lửa to, đảo đều tay để các nguyên liệu trộn đều vào nhau.\n" +
                    "Đảo liên tục trong khoảng 10 phút, khi các nguyên liệu đã chín hết, thịt săn lại thì tắt bếp.\n" +
                    "Múc thịt ra đĩa, có thể rắc thêm chút vừng rang cho thơm, đúng điệu Hàn Quốc và dùng kèm cùng cơm nóng.\n" +
                    "Món thịt xào hành tây kiểu Hàn có nhiều gia vị khác hẳn so với món thịt xào thông thường, đem đến cho chúng ta cảm giác lạ miệng, thơm ngon. Chúc các bạn thành công với món thịt xào hành tây kiểu Hàn nhé!"
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        TabHost host = (TabHost)findViewById(R.id.tabHostDetail);
        host.setup();

        Recipe k = (Recipe) getIntent().getSerializableExtra("recipe");

        ImageView imgView = (ImageView) findViewById(R.id.imgHeaderDetail);
        TextView txtView = (TextView) findViewById(R.id.textDetailTenmon);
        UrlImageViewHelper.setUrlDrawable(imgView, k.demoImage);
        txtView.setText(k.recipeName);
        TabHost.TabSpec spec = host.newTabSpec("Nguyên Liệu");
        spec.setContent(R.id.tab_fooddetail1);
        spec.setIndicator("Nguyên Liệu");
        host.addTab(spec);

        ListViewAdapter adapter = new
                ListViewAdapter(this, RecipeHelper.getIngeNameFromRecipe(k), RecipeHelper.getIngeAmountFromRecipe(k));
        list=(ListView)findViewById(R.id.listViewtabFoodDetail);
        list.setAdapter(adapter);

        //Tab 2
        spec = host.newTabSpec("Cách Làm");
        spec.setContent(R.id.tab_fooddetail2);
        spec.setIndicator("Cách Làm");
        host.addTab(spec);

        ListView2Adapter adapter2 = new
                ListView2Adapter(this, RecipeHelper.getStepFromRecipe(k));
        list2=(ListView)findViewById(R.id.listViewtabFoodDetail2);
        list2.setAdapter(adapter2);
    }
}
