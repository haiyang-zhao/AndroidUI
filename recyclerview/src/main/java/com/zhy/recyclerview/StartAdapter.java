package com.zhy.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StartAdapter extends RecyclerView.Adapter<StartAdapter.StartViewHolder> {

    private Context context;
    private List<Start> startList;

    public StartAdapter(Context context, List<Start> startList) {
        this.context = context;
        this.startList = startList;
    }

    @NonNull
    @Override
    public StartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_list, null);
        return new StartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StartViewHolder holder, int position) {
        holder.tvStart.setText(startList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return startList.size();
    }


    public boolean isGroupHeader(int position) {
        if (position == 0) {
            return true;
        } else {
            return !getGroupName(position).equals(getGroupName(position - 1));
        }
    }

    public String getGroupName(int position) {
        return startList.get(position).getGroupName();
    }

    public String getName(int position) {
        return startList.get(position).getName();
    }

    public static class StartViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStart;

        public StartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStart = itemView.findViewById(R.id.tv_name);
        }
    }

}
