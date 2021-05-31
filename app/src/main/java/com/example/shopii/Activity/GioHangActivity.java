package com.example.shopii.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shopii.R;
import com.example.shopii.adapter.GioHangAdapter;
import com.example.shopii.ultil.checkConnection;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    ListView lvGiohang;
    TextView txtThongbao;
    static TextView txtTongtien;
    Button btnThanhToan, btnTieptucmuahang;
    Toolbar toolbarGioHang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        InitView();
        ActionToolBar(); // nut tro ve
        CheckData();  // check xem mang co dl hay chua
        EvenUtil(); // tinh tong tien
        CatchOnItemListView(); // click vao sp trong gio hang de xoa
        EvenBtn(); // mua hang & tro ve
    }

    private void EvenBtn() {
        btnTieptucmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.mangGiohang.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), ThongTinKHActivity.class);
                    startActivity(intent);
                }else{
                    checkConnection.showToast_Short(getApplicationContext(), "Giỏ hàng trống!");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        lvGiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm khỏi giỏ hàng?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.mangGiohang.size() <= 0){
                            txtThongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.mangGiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EvenUtil();
                            if(MainActivity.mangGiohang.size() <= 0){
                                txtThongbao.setVisibility(View.VISIBLE);
                            }
//                            else {
//                                MainActivity.mangGiohang.remove(position);
//                                txtThongbao.setVisibility(View.INVISIBLE);
//                                gioHangAdapter.notifyDataSetChanged();
//                                EvenUtil();
//                            }
                        }
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EvenUtil();
                    }
                });

                builder.show();
                return true;
            }
        });
    }

    public static void EvenUtil() {
        long tongTien = 0;
        for(int i = 0 ;i < MainActivity.mangGiohang.size(); i++){
            tongTien += MainActivity.mangGiohang.get(i).getGiasp();
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongTien) + "VNĐ");
    }

    private void CheckData() {
        if(MainActivity.mangGiohang.size() <= 0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            lvGiohang.setVisibility(View.INVISIBLE);
        }else{
            gioHangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            lvGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void InitView() {
        lvGiohang = findViewById(R.id.listViewGiohang);
        txtThongbao = findViewById(R.id.textViewEmpty);
        txtTongtien = findViewById(R.id.txtTongtien);
        btnThanhToan = findViewById(R.id.btnThanhtoan);
        btnTieptucmuahang = findViewById(R.id.btnTieptucmuahang);
        toolbarGioHang = findViewById(R.id.ToolbarGioHang);

        gioHangAdapter = new GioHangAdapter(GioHangActivity.this, MainActivity.mangGiohang);
        lvGiohang.setAdapter(gioHangAdapter);
    }
}