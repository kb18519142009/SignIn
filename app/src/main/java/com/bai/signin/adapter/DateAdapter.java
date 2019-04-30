package com.bai.signin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bai.signin.R;
import com.bai.signin.model.SignDateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangbaibai on 2018/8/27.
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateHolder> {
    private Context mContext;
    private List<SignDateModel> mModels = new ArrayList<>();

    public DateAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<SignDateModel> models) {
        this.mModels = models;
        notifyDataSetChanged();
    }

    @Override
    public DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DateHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sign_date, parent, false));
    }

    @Override
    public void onBindViewHolder(DateHolder holder, int position) {
        SignDateModel model = mModels.get(position);
        switch (model.getStatus()) {
            case SignDateModel.STATUS_CURRENT_DAY:
                holder.tv_date_item.setTextColor(holder.tv_date_item.getResources().getColor(R.color.color1));
                break;
            case SignDateModel.STATUS_HAVE_SIGN:
                holder.tv_date_item.setTextColor(holder.tv_date_item.getResources().getColor(R.color.color9));
                holder.tv_date_item.setBackgroundDrawable(holder.tv_date_item.getResources().getDrawable(R.drawable.shape_sign_date_red_bg));
                break;
            case SignDateModel.STATUS_SAT_OR_SUN:
                holder.tv_date_item.setTextColor(holder.tv_date_item.getResources().getColor(R.color.btime_transparent_40percent));
                break;
            case SignDateModel.STATUS_NO_SIGN:
            default:
                holder.tv_date_item.setTextColor(holder.tv_date_item.getResources().getColor(R.color.btime_transparent_80percent));
                break;
        }
        holder.tv_date_item.setText(mModels.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    class DateHolder extends RecyclerView.ViewHolder {
        private TextView tv_date_item;

        public DateHolder(View itemView) {
            super(itemView);
            tv_date_item = itemView.findViewById(R.id.tv_date_item);
        }
    }
}
