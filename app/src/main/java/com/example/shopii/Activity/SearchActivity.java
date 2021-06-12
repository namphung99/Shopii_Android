package com.example.shopii.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopii.R;
import com.example.shopii.adapter.searchAdapter;
import com.example.shopii.model.Sanpham;
import com.example.shopii.ultil.Server;
import com.example.shopii.ultil.showToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbarSearch;
    TextView thongbao;
    ListView lvSearch;
    EditText editTextSearch;
    Button btnSearch;
    ArrayList<Sanpham> arraySearch;
    searchAdapter searchAdapter;

    Spinner spinnerSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        InitView();
        ActionToolBar();
        ActionSearch();
        CatchSpinnerEvent();
//        onFilter();
    }

    private void onFilter() {
        int giaFilter = Integer.parseInt(spinnerSearch.getSelectedItem().toString());
        arraySearch.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Server.URLGetAll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id =0;
                String Ten = "";
                int gia = 0;
                String Hinhanh = "";
                String Mota = "";
                int Idsp = 0;

                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            gia = jsonObject.getInt("giasanpham");
                            if(gia <= giaFilter) {
                                id = jsonObject.getInt("id");
                                Ten = jsonObject.getString("tensanpham");
                                Hinhanh = jsonObject.getString("hinhanhsanpham");
                                Mota = jsonObject.getString("motasanpham");
                                Idsp = jsonObject.getInt("idsanpham");
                                arraySearch.add(new Sanpham(id,Ten,gia,Hinhanh,Mota,Idsp));
                            }
                        }
                        if(arraySearch.isEmpty()){
                            thongbao.setVisibility(View.VISIBLE);
                        }
                        else{
                            thongbao.setVisibility(View.INVISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    searchAdapter.notifyDataSetChanged();
                }
                else{
                    showToast.showToast_Short(getApplicationContext(),"Kiểm tra lại");
                    thongbao.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast.showToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }

    private void CatchSpinnerEvent() {
        Integer[] gia = new Integer[]{30000000,20000000,10000000,5000000,1000000,500000};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,gia);
        spinnerSearch.setAdapter(arrayAdapter);

        spinnerSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ActionSearch() {
        thongbao.setVisibility(View.INVISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraySearch.clear();
                String valueSearch = editTextSearch.getText().toString().trim();
                if(valueSearch.trim().isEmpty()){
                    editTextSearch.setError("Nhập từ khóa");
                    editTextSearch.requestFocus();
                }
                else{
                    String URL_SEARCH = Server.URLSearch+valueSearch;
                    StringRequest stringRequest = new StringRequest(URL_SEARCH, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            int id =0;
                            String Ten = "";
                            int gia = 0;
                            String Hinhanh = "";
                            String Mota = "";
                            int Idsp = 0;

                            if(response != null){
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i<jsonArray.length(); i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        id = jsonObject.getInt("id");
                                        Ten = jsonObject.getString("tensanpham");
                                        gia = jsonObject.getInt("giasanpham");
                                        Hinhanh = jsonObject.getString("hinhanhsanpham");
                                        Mota = jsonObject.getString("motasanpham");
                                        Idsp = jsonObject.getInt("idsanpham");
                                        arraySearch.add(new Sanpham(id,Ten,gia,Hinhanh,Mota,Idsp));
                                    }
                                    if(arraySearch.isEmpty()){
                                        thongbao.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        thongbao.setVisibility(View.INVISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                searchAdapter.notifyDataSetChanged();
                            }
                            else{
                                showToast.showToast_Short(getApplicationContext(),"Kiểm tra lại");
                                thongbao.setVisibility(View.VISIBLE);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showToast.showToast_Short(getApplicationContext(), error.toString());
                        }
                    });
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void InitView() {
        toolbarSearch = (Toolbar) findViewById(R.id.ToolbarSearch);
        thongbao = findViewById(R.id.txtthongbao);
        editTextSearch = findViewById(R.id.editTextSearch);
        btnSearch = findViewById(R.id.searchbtn);

        lvSearch = findViewById(R.id.lvSearch);
        arraySearch = new ArrayList<>();
        searchAdapter = new searchAdapter(getApplicationContext(), arraySearch);
        lvSearch.setAdapter(searchAdapter);

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("thongTinSanPham", arraySearch.get(position));
                showToast.showToast_Short(getApplicationContext(), arraySearch.get(position).getTensanpham());
                startActivity(intent);
            }
        });

        spinnerSearch = findViewById(R.id.spinnerSearch);
    }
}