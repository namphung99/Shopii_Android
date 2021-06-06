    package com.example.shopii.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shopii.ultil.showToast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopii.R;
import com.example.shopii.adapter.DienTuAdapter;
import com.example.shopii.model.Sanpham;
import com.example.shopii.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

    public class DienTuActivity extends AppCompatActivity {
    Toolbar toolbardt;
    ListView lvdt;
    DienTuAdapter dienTuAdapter;
    ArrayList<Sanpham> mangdt;
    int idDT = 0;
//    int page = 1;
    View footerView; // thanh processbar
    boolean isLoading = false;
    boolean limitData = false;
//    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_tu);
        InitView();
        getIDLoaisp();
        ActionToolBar();
        GetData(idDT);
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


        private void GetData(int Page) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL_DT = Server.URLSanPham + String.valueOf(idDT); // add page vào đường dẫn
            StringRequest stringRequest = new StringRequest(URL_DT, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int id =0;
                    String Tendt = "";
                    int gia = 0;
                    String Hinhanhdt = "";
                    String Mota = "";
                    int Idspdt = 0;

                    if(response != null){
                        try {
                            JSONArray jsonArray= new JSONArray(response);
                            for (int i = 0; i< jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                Tendt = jsonObject.getString("tensanpham");
                                gia = jsonObject.getInt("giasanpham");
                                Hinhanhdt = jsonObject.getString("hinhanhsanpham");
                                Mota = jsonObject.getString("motasanpham");
                                Idspdt = jsonObject.getInt("idsanpham");
                                mangdt.add(new Sanpham(id,Tendt,gia,Hinhanhdt,Mota,Idspdt));
                                dienTuAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        showToast.showToast_Short(getApplicationContext(),"Kiểm tra lại");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(stringRequest);
        }

        private void ActionToolBar() { // trở về trang trước
            setSupportActionBar(toolbardt);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }

        private void getIDLoaisp() {
            Intent intent = getIntent();
            idDT = getIntent().getIntExtra("idloaisanpham", -1);
        }

        private void InitView() {
            toolbardt = findViewById(R.id.toolbarDienTu);
            lvdt = findViewById(R.id.listViewDienTu);
            mangdt = new ArrayList<>();
            //  gửi dữ liệu qua dienTuAdapter để truyền vào activity.xml
            dienTuAdapter = new DienTuAdapter(getApplicationContext(), mangdt);
            lvdt.setAdapter(dienTuAdapter);

            lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("thongTinSanPham", mangdt.get(position));
                    showToast.showToast_Short(getApplicationContext(), mangdt.get(position).getTensanpham());
                    startActivity(intent);
                }
            });
        }
    }