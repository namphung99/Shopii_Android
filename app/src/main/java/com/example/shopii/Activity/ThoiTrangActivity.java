package com.example.shopii.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopii.R;
import com.example.shopii.adapter.ThoiTrangAdapter;
import com.example.shopii.model.Sanpham;
import com.example.shopii.ultil.Server;
import com.example.shopii.ultil.showToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThoiTrangActivity extends AppCompatActivity {
    Toolbar toolbarThoitrang;
    ListView lvthoitrang;
    ThoiTrangAdapter thoiTrangAdapter;
    ArrayList<Sanpham> mangthoitrang;
    int idthoitrang = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoi_trang);

        InitView();
        getIDLoaisp();
        ActionToolBar();
        GetData(idthoitrang);
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
        String URL_TT = Server.URLSanPham + String.valueOf(idthoitrang);
        StringRequest stringRequest = new StringRequest(URL_TT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id =0;
                String TenThoitrang = "";
                int gia = 0;
                String HinhanhThoitrang = "";
                String Mota = "";
                int IdspThoitrang = 0;

                if(response != null){
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        for (int i = 0; i< jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            TenThoitrang = jsonObject.getString("tensanpham");
                            gia = jsonObject.getInt("giasanpham");
                            HinhanhThoitrang = jsonObject.getString("hinhanhsanpham");
                            Mota = jsonObject.getString("motasanpham");
                            IdspThoitrang = jsonObject.getInt("idsanpham");
                            mangthoitrang.add(new Sanpham(id,TenThoitrang,gia,HinhanhThoitrang,Mota,IdspThoitrang));
                            thoiTrangAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
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
    private void InitView() {
        toolbarThoitrang = findViewById(R.id.toolbarThoiTrang);
        lvthoitrang = findViewById(R.id.listViewThoiTrang);
        mangthoitrang = new ArrayList<>();
        thoiTrangAdapter = new ThoiTrangAdapter(getApplicationContext(), mangthoitrang);
        lvthoitrang.setAdapter(thoiTrangAdapter);

        lvthoitrang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongTinSanPham", mangthoitrang.get(position));
                showToast.showToast_Short(getApplicationContext(), mangthoitrang.get(position).getTensanpham());
                startActivity(intent);
            }
        });

    }
    private void getIDLoaisp() {
        idthoitrang = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("gia tri loai sp", idthoitrang+"");
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarThoitrang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThoitrang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}