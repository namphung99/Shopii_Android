    package com.example.shopii.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shopii.ultil.checkConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_tu);
        InitView();
        if(checkConnection.haveNetworkConnection(getApplicationContext())){
            getIDLoaisp();
            ActionToolBar();
            GetData(page);
        }
        else{
            checkConnection.showToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối Internet");
            finish();
        }
    }

        private void GetData(int Page) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL_DT = Server.DuongDanDienTu+String.valueOf(Page);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DT, new Response.Listener<String>() {
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
                    param.put("idsanpham",String.valueOf(idDT));
                    return param;
                }
            };
            requestQueue.add(stringRequest);
        }

        private void ActionToolBar() {
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
            Log.d("gia tri loai sp", idDT+"");
        }

        private void InitView() {
            toolbardt = findViewById(R.id.toolbarDienTu);
            lvdt = findViewById(R.id.listViewDienTu);
            mangdt = new ArrayList<>();
            dienTuAdapter = new DienTuAdapter(getApplicationContext(), mangdt);
            lvdt.setAdapter(dienTuAdapter);
        }
    }