package com.bluewhaleyt.whaleutils.debug.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bluewhaleyt.whaleutils.R;
import com.bluewhaleyt.whaleutils.debug.models.ColorResModel;
import com.bluewhaleyt.whaleutils.debug.models.StringResModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ColorResListAdapter extends RecyclerView.Adapter<ColorResListAdapter.ViewHolder> {

    ArrayList<ColorResModel> list;

    public ColorResListAdapter(ArrayList<ColorResModel> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_debug_color_res_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ColorResModel model = list.get(position);
        holder.stringCount.setText(model.getCount()+"");
        holder.colorRes.setText(model.getColorRes());
        holder.colorHex.setText(model.getColorHex());
        holder.cardView.setCardBackgroundColor(Color.parseColor(model.getColorHex()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stringCount, colorHex, colorRes;
        MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stringCount = itemView.findViewById(R.id.tvCount);
            colorHex = itemView.findViewById(R.id.tvColorHex);
            colorRes = itemView.findViewById(R.id.tvColorRes);
            cardView = itemView.findViewById(R.id.cardViewColorPreview);

        }
    }

}