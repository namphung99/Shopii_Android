    package com.example.shopii.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shopii.ultil.checkConnection;

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
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

    public class DienTuActivity extends AppCompatActivity {
    Toolbar toolbardt;
    ListView lvdt;
    DienTuAdapter dienTuAdapter;
    ArrayList<Sanpham> mangdt;
    int idDT = 0;
    int page = 1;
    View footerView; // thanh processbar
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_tu);
        InitView();
        if(checkConnection.haveNetworkConnection(getApplicationContext())){
            getIDLoaisp();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }
        else{
            checkConnection.showToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối Internet");
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

        private void LoadMoreData() {

            lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(),ChiTietSanPhamActivity.class);
                    intent.putExtra("thongTinSanPham", mangdt.get(position));
                    startActivity(intent);
                }
            });
            lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                // kéo xem thêm sp
                public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
//                    kiểm tra còn dl thì gọi
                    if(firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && limitData == false){
                        isLoading = true;
                        ThreadData threadData = new ThreadData();
                        threadData.start();
                    }
                }
            });
        }

        private void GetData(int Page) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL_DT = Server.DuongDanDienTu+String.valueOf(Page); // add page vào đường dẫn
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DT, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    int id =0;
                    String Tendt = "";
                    int gia = 0;
                    String Hinhanhdt = "";
                    String Mota = "";
                    int Idspdt = 0;

                    if(response != null && response.length() != 2){
                        lvdt.removeFooterView(footerView);
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
                    }else{
                        limitData = true;
                        lvdt.removeFooterView(footerView);
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

                    param.put("idsanpham",String.valueOf(idDT)); // put thêm id sản phẩm vào đường đẫn

                    return param;
                }
            };
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
            idDT = getIntent().getIntExtra("idloaisanpham", -1);
        }

        private void InitView() {
            toolbardt = findViewById(R.id.toolbarDienTu);
            lvdt = findViewById(R.id.listViewDienTu);
            mangdt = new ArrayList<>();
            //  gửi dữ liệu qua dienTuAdapter để truyền vào activity.xml
            dienTuAdapter = new DienTuAdapter(getApplicationContext(), mangdt);
            lvdt.setAdapter(dienTuAdapter);

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            footerView = inflater.inflate(R.layout.progressbar, null);

            mHandler = new mHandler();
        }

        public class mHandler extends Handler{ // Handler phân bổ luồng cho Thread
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 0:
                        lvdt.addFooterView(footerView);
                        break;
                    case 1:
                        GetData(++page);
                        isLoading = false; // is loading =  false để load thêm dl
                        break;
                }
                super.handleMessage(msg);
            }
        }

        public class ThreadData extends Thread{
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0); // ban đầu start gán = 0
                try {
                    Thread.sleep(3000); // sau 3s
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = mHandler.obtainMessage(1); // load thêm dl gán = 1
                mHandler.sendMessage(message);
                super.run();
            }
        }
    }