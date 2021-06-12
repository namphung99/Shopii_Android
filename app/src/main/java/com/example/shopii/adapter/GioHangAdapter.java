package com.example.shopii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopii.Activity.GioHangActivity;
import com.example.shopii.Activity.MainActivity;
import com.example.shopii.R;
import com.example.shopii.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayGioHang;

    public GioHangAdapter(Context context, ArrayList<Giohang> arrayGioHang) {
        this.context = context;
        this.arrayGioHang = arrayGioHang;
    }

    @Override
    public int getCount() {
        return arrayGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView tenGioHang, giaGioHang, value;
        public ImageView imgGioHang;
        public Button btnminus, btnplus;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();

//            gan layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);

            viewHolder.tenGioHang = view.findViewById(R.id.textviewtenGioHang);
            viewHolder.giaGioHang = view.findViewById(R.id.giasanphamGiohang);
            viewHolder.value = view.findViewById(R.id.txtvalue);
            viewHolder.imgGioHang = view.findViewById(R.id.imgGioHang);
            viewHolder.btnminus = view.findViewById(R.id.btnminus);
            viewHolder.btnplus = view.findViewById(R.id.btnplus);

//            set dl vao viewHolder
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

//        lay dl va gan vao layout
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.tenGioHang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.giaGioHang.setText("Giá: " + decimalFormat.format(giohang.getGiasp()) +"VNĐ");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(viewHolder.imgGioHang);
        viewHolder.value.setText(String.valueOf(giohang.getSoluong()));

//        disable + và -
        int sl = Integer.parseInt(viewHolder.value.getText().toString());
        if(sl >=10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        else if(sl <=1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }
        else if(sl >= 1){
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }

//        bắt sự kiện + -
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() { // click +
            @Override
            public void onClick(View v) { // cập nhật tiền theo sl từng sp
                int slMoiNhat = Integer.parseInt(finalViewHolder.value.getText().toString()) + 1;
                int slHientai = MainActivity.mangGiohang.get(position).getSoluong();
                long giaht = MainActivity.mangGiohang.get(position).getGiasp();
                MainActivity.mangGiohang.get(position).setSoluong(slMoiNhat);

                long giaMoiNhat = (giaht * slMoiNhat) / slHientai;
                MainActivity.mangGiohang.get(position).setGiasp(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.giaGioHang.setText("Giá: " + decimalFormat.format(giaMoiNhat) +"VNĐ");

                GioHangActivity.TotalMoney();
                if(slMoiNhat >= 10){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.value.setText(String.valueOf(slMoiNhat));
                }else{
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.value.setText(String.valueOf(slMoiNhat));
                }
            }
        });

        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() { // click -
            @Override
            public void onClick(View v) {
                int slMoiNhat = Integer.parseInt(finalViewHolder.value.getText().toString()) - 1;
                int slHientai = MainActivity.mangGiohang.get(position).getSoluong();
                long giaht = MainActivity.mangGiohang.get(position).getGiasp();
                MainActivity.mangGiohang.get(position).setSoluong(slMoiNhat);

                long giaMoiNhat = (giaht * slMoiNhat) / slHientai;
                MainActivity.mangGiohang.get(position).setGiasp(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.giaGioHang.setText("Giá: " + decimalFormat.format(giaMoiNhat) +"VNĐ");

                GioHangActivity.TotalMoney();
                if(slMoiNhat <= 1){
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.value.setText(String.valueOf(slMoiNhat));
                }else{
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.value.setText(String.valueOf(slMoiNhat));
                }
            }
        });
        return view;
    }
}
