package com.example.shopii.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopii.R;
import com.example.shopii.adapter.DienTuAdapter;
import com.example.shopii.adapter.ThoiTrangAdapter;
import com.example.shopii.model.Sanpham;
import com.example.shopii.ultil.Server;
import com.example.shopii.ultil.checkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThoiTrangActivity extends AppCompatActivity {
    Toolbar toolbarThoitrang;
    ListView lvthoitrang;
    ThoiTrangAdapter thoiTrangAdapter;
    ArrayList<Sanpham> mangthoitrang;
    int idthoitrang = 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoi_trang);
        if(checkConnection.haveNetworkConnection(getApplicationContext())) {
            InitView();
            getIDLoaisp();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }else{
            checkConnection.showToast_Short(getApplicationContext(), "Kiểm trai lại kết nối Internet!");
            finish();
        }
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
        String URL_DT = Server.DuongDanDienTu+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id =0;
                String TenThoitrang = "";
                int gia = 0;
                String HinhanhThoitrang = "";
                String Mota = "";
                int IdspThoitrang = 0;

                if(response != null && response.length() != 2){
                    lvthoitrang.removeFooterView(footerView);
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
                    limitData = true;
                    lvthoitrang.removeFooterView(footerView);
                    checkConnection.showToast_Short(getApplicationContext(),"Hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idthoitrang));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void LoadMoreData() {

        lvthoitrang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPhamActivity.class);
                intent.putExtra("thongTinSanPham", mangthoitrang.get(position));
                startActivity(intent);
            }
        });
        lvthoitrang.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && limitData == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }
    private void InitView() {
        toolbarThoitrang = findViewById(R.id.toolbarThoiTrang);
        lvthoitrang = findViewById(R.id.listViewThoiTrang);
        mangthoitrang = new ArrayList<>();
        thoiTrangAdapter = new ThoiTrangAdapter(getApplicationContext(), mangthoitrang);
        lvthoitrang.setAdapter(thoiTrangAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);

        mHandler = new mHandler();
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
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvthoitrang.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}