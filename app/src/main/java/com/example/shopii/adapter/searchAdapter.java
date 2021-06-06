package com.example.shopii.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopii.R;
import com.example.shopii.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class searchAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraySanPham;

    public searchAdapter(Context context, ArrayList<Sanpham> arraySanPham) {
        this.context = context;
        this.arraySanPham = arraySanPham;
    }

    @Override
    public int getCount() {
        return arraySanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return arraySanPham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTen, txtGia, txtMoTa;
        public ImageView img;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_sanpham, null);
            viewHolder.txtTen = (TextView) view.findViewById(R.id.textviewTen);
            viewHolder.txtGia = (TextView) view.findViewById(R.id.textViewGia);
            viewHolder.txtMoTa = (TextView) view.findViewById(R.id.textViewMoTa);
            viewHolder.img = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txtTen.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGia.setText("Giá: " +decimalFormat.format(sanpham.getGiasanpham()) + " VNĐ");
        viewHolder.txtMoTa.setMaxLines(2);
        viewHolder.txtMoTa.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTa.setText(sanpham.getMotasanpham());

        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(viewHolder.img);
        return view;
    }
}
