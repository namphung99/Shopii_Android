package com.example.shopii.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopii.R;
import com.example.shopii.ultil.showToast;

public class FinishActivity extends AppCompatActivity {
    Button btnXacNhan;
    TextView Hoten, txtSDT, txtDC;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        InitView();
        getData();
        ActionBar();
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                showToast.showToast_Short(getApplicationContext(), "Đặt hàng thành công!");
                MainActivity.mangGiohang.clear(); // sau khi mua hàng sẽ xóa dl khỏi giỏ hàng
            }
        });
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        String tenKH = intent.getStringExtra("ten");
        String sdtKH = intent.getStringExtra("sdt");
        String dcKH = intent.getStringExtra("diachi");

        Hoten.setText(tenKH);
        txtSDT.setText(sdtKH);
        txtDC.setText(dcKH);
    }

    private void InitView() {
        btnXacNhan = findViewById(R.id.btnFN);
        Hoten = findViewById(R.id.TenFN);
        txtSDT = findViewById(R.id.sdtFN);
        txtDC = findViewById(R.id.diachiFN);

        toolbar = findViewById(R.id.toolBarFN);
    }
}