package com.example.shopii.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shopii.R;
import com.example.shopii.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThoiTrangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayThoiTrang;

    public ThoiTrangAdapter(Context context, ArrayList<Sanpham> arrayThoiTrang) {
        this.context = context;
        this.arrayThoiTrang = arrayThoiTrang;
    }

//    public Context getContext() {
//        return context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }

//    public ArrayList<Sanpham> getArrayThoiTrang() {
//        return arrayThoiTrang;
//    }
//
//    public void setArrayThoiTrang(ArrayList<Sanpham> arrayThoiTrang) {
//        this.arrayThoiTrang = arrayThoiTrang;
//    }

    @Override
    public int getCount() {
        return arrayThoiTrang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayThoiTrang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenThoiTrang, txtGiaThoiTrang, txtMoTaThoiTrang;
        public ImageView imgThoiTrang;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_sanpham, null);
            viewHolder.txtTenThoiTrang = (TextView) view.findViewById(R.id.textviewTen);
            viewHolder.txtGiaThoiTrang = (TextView) view.findViewById(R.id.textViewGia);
            viewHolder.txtMoTaThoiTrang = (TextView) view.findViewById(R.id.textViewMoTa);
            viewHolder.imgThoiTrang = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txtTenThoiTrang.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaThoiTrang.setText("Giá: " +decimalFormat.format(sanpham.getGiasanpham()) + " VNĐ");
        viewHolder.txtMoTaThoiTrang.setMaxLines(2);
        viewHolder.txtMoTaThoiTrang.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaThoiTrang.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(viewHolder.imgThoiTrang);
        return view;
    }
}
