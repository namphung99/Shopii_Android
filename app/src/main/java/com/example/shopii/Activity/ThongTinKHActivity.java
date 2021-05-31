package com.example.shopii.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopii.R;
import com.example.shopii.ultil.Server;
import com.example.shopii.ultil.checkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKHActivity extends AppCompatActivity {
    EditText editTenKH, editTextdiachi, editTextsodienthoai;
    Button btnXacNhan, btnTrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_k_h);
        InitView();
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(checkConnection.haveNetworkConnection(getApplicationContext())){
            EventBtn(); // click Xac Nhan
        }else {
            checkConnection.showToast_Short(getApplicationContext(), "Kiểm tra lại kết nối Internet!");
        }
    }

    private void EventBtn() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten =editTenKH.getText().toString().trim();
                final String sdt = editTextsodienthoai.getText().toString().trim();
                final String diachi = editTextdiachi.getText().toString().trim();

                if(ten.length() > 0 && sdt.length()>0 && diachi.length() > 0){
                    Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                    intent.putExtra("ten", ten);
                    intent.putExtra("sdt", sdt);
                    intent.putExtra("diachi", diachi);
                    startActivity(intent);
                    checkConnection.showToast_Short(getApplicationContext(),"Xác nhận đơn hàng");
                }else{
                    checkConnection.showToast_Short(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin");
                }
            }
        });
    }

    private void InitView() {
        editTenKH = findViewById(R.id.editTextTenKH);
        editTextdiachi = findViewById(R.id.editTextdiachiKH);
        editTextsodienthoai = findViewById(R.id.editTextsdtKH);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnTrove = findViewById(R.id.btnTroVe);
    }
}