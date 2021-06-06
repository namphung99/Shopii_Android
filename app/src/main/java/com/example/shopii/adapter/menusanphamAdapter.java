package com.example.shopii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopii.R;
import com.example.shopii.model.listMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class menusanphamAdapter extends BaseAdapter {

    ArrayList<listMenu> arrayListLoaisp;
    Context context;

    public menusanphamAdapter(ArrayList<listMenu> arrayListLoaisp, Context context) {
        this.arrayListLoaisp = arrayListLoaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaisp.size();
    }


    public Object getItem(int position) {
        return arrayListLoaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // dl render lan dau => load anh xa
    public  class ViewHolder{
        TextView txtTenLoaiSp;
        ImageView imgLoaiSp;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_product, null);

            viewHolder.txtTenLoaiSp = (TextView) view.findViewById(R.id.textViewLoaisp);
            viewHolder.imgLoaiSp = (ImageView) view.findViewById(R.id.imageViewLoaisp);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        // set san pham vao view
        listMenu listMenu = (listMenu) getItem(position);
        viewHolder.txtTenLoaiSp.setText(listMenu.getTen());
        Picasso.with(context).load(listMenu.getHinhanh())
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(viewHolder.imgLoaiSp);
        return view;
    }
}
