package com.example.shopii.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopii.R;
import com.example.shopii.adapter.SanPhamMoiNhatAdapter;
import com.example.shopii.adapter.menusanphamAdapter;
import com.example.shopii.model.Giohang;
import com.example.shopii.model.Sanpham;
import com.example.shopii.model.listMenu;
import com.example.shopii.ultil.Server;
import com.example.shopii.ultil.showToast;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView, lvSPmoinhat;
    DrawerLayout drawerLayout;
    ArrayList<listMenu> arrayMenu;
    menusanphamAdapter menusanphamAdapter;

    ArrayList<Sanpham> mangsanpham;
    SanPhamMoiNhatAdapter sanphamAdapter;

    public static ArrayList<Giohang> mangGiohang; // luu cac san pham duoc them vao gio hang truyen den cac man hinh

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitID();
        ActionBar();
        ActionViewFlipper();
        GetDuLieuSPMoiNhat();
        CatchOnItemListView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // tao menu gio hang
        getMenuInflater().inflate(R.menu.menu_giohang, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // bat su kien click gio hang
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    // bắt sự kiên menu gửi qua activity tương ứng
    private void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        Intent intentDT = new Intent(MainActivity.this, DienTuActivity.class);
                        intentDT.putExtra("idloaisanpham",arrayMenu.get(position).getId());
                        startActivity(intentDT);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        Intent intentTT = new Intent(MainActivity.this, ThoiTrangActivity.class);
                        intentTT.putExtra("idloaisanpham",arrayMenu.get(position).getId());
                        startActivity(intentTT);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, SearchActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, LienHeActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 5:
                        startActivity(new Intent(MainActivity.this, ThongTinActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void GetDuLieuSPMoiNhat() {
//        ket noi server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URLSPMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID = 0;
                    String Tensanpham ="";
                    Integer Giasanpham = 0;
                    String Hinhanhsanpham ="";
                    String Motasanpham = "";
                    int IDsanpham = 0;
                    for(int i =0 ; i< response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensanpham");
                            Giasanpham = jsonObject.getInt("giasanpham");
                            Hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                            Motasanpham = jsonObject.getString("motasanpham");
                            IDsanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void ActionViewFlipper() {
        ArrayList<String> Slide = new ArrayList<>();
        Slide.add("https://topmacbook.vn/wp-content/uploads/2020/12/ok.jpg");
        Slide.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTIZVsS7WH9GUwaCir4zVw9DWLoUukuw_DBg&usqp=CAU");
        Slide.add("https://hoanghamobile.com/tin-tuc/wp-content/uploads/2020/05/1-1-16.jpg");
        Slide.add("https://inanaz.com.vn/wp-content/uploads/2020/02/mau-to-roi-quang-cao-shop-quan-ao-4.jpg");

        for(int i =0; i<Slide.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(Slide.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
    }

    private void ActionBar() { // open menu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private  void InitID(){
        toolbar = (Toolbar) findViewById(R.id.toolbarTrangChu);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        lvSPmoinhat = findViewById(R.id.lvSPmoinhat);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        listView = (ListView) findViewById(R.id.listViewTrangChu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        arrayMenu = new ArrayList<>();

        arrayMenu.add(0, new listMenu(0,"Trang Chủ","https://sites.google.com/site/khoivinhphankhoivinhseo/_/rsrc/1489649407204/noi-bat/cac-mau-tick-icon-dep/home.png"));
        arrayMenu.add(1, new listMenu(1,"Công Nghệ","https://baabrand.com/wp-content/uploads/2018/12/icon-thiet-ke-linh-vuc-logo-thuong-hieu-cong-nghe-cao-digital-thong-tin-baa-brand-1.png"));
        arrayMenu.add(2, new listMenu(2,"Thời Trang","https://baabrand.com/wp-content/uploads/2018/12/icon-thiet-ke-linh-vuc-logo-thuong-hieu-thoi-trang-my-pham-lam-dep-spa-baa-brand-1-400x400.png"));
        arrayMenu.add(3, new listMenu(0,"Tìm Kiếm","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWHP1pfR3hQvzBzF75oIJMQq0u-45jw2TXKQ&usqp=CAU"));
        arrayMenu.add(4, new listMenu(0,"Liên Hệ","https://i.pinimg.com/originals/43/d8/21/43d821d6b6d6e6c2424a9415a8e00ed0.png"));
        arrayMenu.add(5, new listMenu(0,"Thông Tin","http://www.click2sciencepd.org/sites/default/files/images/User-Icons-orange-2.png"));

        menusanphamAdapter = new menusanphamAdapter(arrayMenu, getApplicationContext());
        listView.setAdapter(menusanphamAdapter);

        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanPhamMoiNhatAdapter(getApplicationContext(), mangsanpham);
        lvSPmoinhat.setAdapter(sanphamAdapter);
        lvSPmoinhat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongTinSanPham", mangsanpham.get(position));
                showToast.showToast_Short(getApplicationContext(), mangsanpham.get(position).getTensanpham());
                startActivity(intent);
            }
        });

        if(mangGiohang != null){ // khong tao mang moi

        }else{
            mangGiohang =  new ArrayList<>();
        }
    }
}