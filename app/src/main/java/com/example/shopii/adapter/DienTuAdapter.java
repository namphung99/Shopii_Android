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

public class DienTuAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayDienTu;

    public DienTuAdapter(Context context, ArrayList<Sanpham> arrayDienTu) {
        this.context = context;
        this.arrayDienTu = arrayDienTu;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Sanpham> getArrayDienTu() {
        return arrayDienTu;
    }

    public void setArrayDienTu(ArrayList<Sanpham> arrayDienTu) {
        this.arrayDienTu = arrayDienTu;
    }

    @Override
    public int getCount() {
        return arrayDienTu.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDienTu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenDienTu, txtGiaDienTu, txtMoTaDienTu;
        public ImageView imgDienTu;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dientu, null);
            viewHolder.txtTenDienTu = (TextView) view.findViewById(R.id.textviewTenDienTu);
            viewHolder.txtGiaDienTu = (TextView) view.findViewById(R.id.textViewGiaDienTu);
            viewHolder.txtMoTaDienTu = (TextView) view.findViewById(R.id.textViewMoTaDienTu);
            viewHolder.imgDienTu = (ImageView) view.findViewById(R.id.imageViewDienTu);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txtTenDienTu.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaDienTu.setText("Giá: " +decimalFormat.format(sanpham.getGiasanpham()) + " VNĐ");
        viewHolder.txtMoTaDienTu.setMaxLines(2);
        viewHolder.txtMoTaDienTu.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaDienTu.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(viewHolder.imgDienTu);
        return view;
    }
}
