package com.bluewhaleyt.whaleutils.debug.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bluewhaleyt.component.dialog.DialogUtil;
import com.bluewhaleyt.debug.SystemResourceUtil;
import com.bluewhaleyt.filemanagement.FileUtil;
import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.debug.models.ColorResModel;
import com.bluewhaleyt.whaleutils.debug.models.DrawableResModel;
import com.google.android.material.card.MaterialCardView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class DrawableResListAdapter extends RecyclerView.Adapter<DrawableResListAdapter.ViewHolder> {

    ArrayList<DrawableResModel> list;
    DrawableResModel model;

    public DrawableResListAdapter(ArrayList<DrawableResModel> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_debug_drawable_res_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        model = list.get(position);
        holder.stringCount.setText(model.getCount()+"");
        holder.drawableRes.setText(model.getDrawableRes());
        holder.drawablePreview.setImageResource(model.getDrawablePreview());

        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            try {
                var temp = "";
                DialogUtil dialog = new DialogUtil(context, holder.drawableRes.getText().toString(), temp);
                dialog.build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stringCount, drawableRes;
        ImageView drawablePreview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stringCount = itemView.findViewById(R.id.tvCount);
            drawableRes = itemView.findViewById(R.id.tvDrawableRes);
            drawablePreview = itemView.findViewById(R.id.ivDrawablePreview);

        }
    }

}