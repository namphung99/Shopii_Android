package com.example.shopii.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shopii.R;
import com.example.shopii.model.Giohang;
import com.example.shopii.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarChitietsp;
    ImageView imageViewChitiet;
    TextView ten, gia , mota;
    Spinner spinner;
    Button addBtn;
    int id = 0;
    String TenChitiet = "";
    int giaChitiet = 0;
    String hinhanh = "";
    String motaSP = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        
        InitView();
        ActionToolBart();
        getInfo(); // lấy thông tin sản phẩm truyền qua
        CatchSpinnerEvent();
        EventBtn(); // bat sk them gio hang
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

    private void EventBtn() {
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(MainActivity.mangGiohang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());

                    boolean exit = false;
                    for (int i =0;i<MainActivity.mangGiohang.size();i++){
                        if(MainActivity.mangGiohang.get(i).getIdsp() == id){
                            MainActivity.mangGiohang.get(i).setSoluong(MainActivity.mangGiohang.get(i).getSoluong() + sl); // cap nhap neu da co trong gio hang
                            if(MainActivity.mangGiohang.get(i).getSoluong() >= 10){
                                MainActivity.mangGiohang.get(i).setSoluong(10); // chi cho mua 10 sp
                            }
                            MainActivity.mangGiohang.get(i).setGiasp(giaChitiet * MainActivity.mangGiohang.get(i).getSoluong()); // set gia cho sp trong gio hang
                            exit = true;
                        }
                    }
                    if (exit == false){ // truong hop san pham khong ton tai trong gio hang
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Tongtien = soluong * giaChitiet;
                        MainActivity.mangGiohang.add(new Giohang(id,TenChitiet, Tongtien,hinhanh, soluong));
                    }

                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Tongtien = soluong * giaChitiet;
                    MainActivity.mangGiohang.add(new Giohang(id,TenChitiet, Tongtien,hinhanh, soluong));
                }
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchSpinnerEvent() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void getInfo() { // lay thong tin san pham truyen qua va gan vao view

//        get DL
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongTinSanPham");

//        gan dl
        id = sanpham.getID();
        TenChitiet = sanpham.getTensanpham();
        giaChitiet = sanpham.getGiasanpham();
        hinhanh =  sanpham.getHinhanhsanpham();
        motaSP = sanpham.getMotasanpham();


        ten.setText(TenChitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        gia.setText("Giá: " + decimalFormat.format(giaChitiet) + "VNĐ");
        mota.setText(motaSP);
        Picasso.with(getApplicationContext()).load(hinhanh)
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(imageViewChitiet);

    }

    private void ActionToolBart() { // custom toolBar
        setSupportActionBar(toolbarChitietsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitietsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void InitView() {
        toolbarChitietsp =  findViewById(R.id.toolbarChitietSP);
        imageViewChitiet = findViewById(R.id.ImgchitietSP);
        ten =  findViewById(R.id.tenchitietSP);
        gia = findViewById(R.id.giachitietSP);
        mota =  findViewById(R.id.textviewMotachitiet);
        spinner = findViewById(R.id.spinner);
        addBtn = findViewById(R.id.addBtn);
    }
}