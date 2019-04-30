package com.bai.signin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bai.signin.R;


/**
 * Created by kangbaibai on 2018/8/29.
 */

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.WeekHolder> {

    private Context mContext;
    private String[] week = {"日", "一", "二", "三", "四", "五", "六"};

    public WeekAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public WeekHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeekHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sign_week, parent, false));
    }

    @Override
    public void onBindViewHolder(WeekHolder holder, int position) {
        holder.tv_date_item.setText(week[position]);
        if (position == 0 || position == 6) {
            holder.tv_date_item.setTextColor(holder.tv_date_item.getResources().getColor(R.color.color5));
        }
    }

    @Override
    public int getItemCount() {
        return week.length;
    }

    class WeekHolder extends RecyclerView.ViewHolder {
        private TextView tv_date_item;

        public WeekHolder(View itemView) {
            super(itemView);
            tv_date_item = itemView.findViewById(R.id.tv_date_item);
        }
    }
}
