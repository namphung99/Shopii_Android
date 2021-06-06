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

public class SanPhamMoiNhatAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanPhamMoiNhatAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @Override
    public int getCount() {
        return arraysanpham.size();
    }

    @Override
    public Object getItem(int position) {
        return arraysanpham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txtgiasanpham, txttensanpham, motaSP;
        ImageView imghinhsanpham;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_sanphammoinhat, null);

            viewHolder.txttensanpham = (TextView) view.findViewById(R.id.textviewtensanpham);
            viewHolder.imghinhsanpham = (ImageView) view.findViewById(R.id.imgviewsp);
            viewHolder.txtgiasanpham = (TextView) view.findViewById(R.id.textviewgiasanpham);
            viewHolder.motaSP = (TextView) view.findViewById(R.id.textViewmotasanpham);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        // set san pham vao view
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttensanpham.setText(sanpham.getTensanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(viewHolder.imghinhsanpham);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiasanpham.setText("Giá: " +decimalFormat.format(sanpham.getGiasanpham()) + " VNĐ");

        viewHolder.motaSP.setMaxLines(2);
        viewHolder.motaSP.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.motaSP.setText(sanpham.getMotasanpham());
        return view;

    }
}
