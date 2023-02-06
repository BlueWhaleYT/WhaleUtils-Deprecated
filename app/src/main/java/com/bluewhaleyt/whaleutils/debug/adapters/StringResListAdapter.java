package com.bluewhaleyt.whaleutils.debug.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bluewhaleyt.whaleutils.debug.models.StringResModel;
import com.bluewhaleyt.whaleutils.R;

import java.util.ArrayList;

public class StringResListAdapter extends RecyclerView.Adapter<StringResListAdapter.ViewHolder> {

    ArrayList<StringResModel> list;

    public StringResListAdapter(ArrayList<StringResModel> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_debug_string_res_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StringResModel model = list.get(position);
        holder.stringCount.setText(model.getCount()+"");
        holder.stringPreview.setText(model.getStringPreview());
        holder.stringRes.setText(model.getStringRes());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView stringCount, stringPreview, stringRes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stringCount = itemView.findViewById(R.id.tvCount);
            stringPreview = itemView.findViewById(R.id.tvStringPreview);
            stringRes = itemView.findViewById(R.id.tvStringRes);

        }
    }

}
