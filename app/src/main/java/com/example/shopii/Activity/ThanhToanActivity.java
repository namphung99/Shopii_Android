package com.example.shopii.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shopii.R;
import com.example.shopii.ultil.showToast;

public class ThanhToanActivity extends AppCompatActivity {
    EditText editTenKH, editTextdiachi, editTextsodienthoai;
    Button btnXacNhan, btnTrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        InitView();
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EventBtn(); // click Xac Nhan
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
                    showToast.showToast_Short(getApplicationContext(),"Xác nhận đơn hàng");
                }else{
                    showToast.showToast_Short(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin");
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