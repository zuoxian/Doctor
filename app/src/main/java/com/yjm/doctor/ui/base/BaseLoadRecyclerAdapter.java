package com.yjm.doctor.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.yjm.doctor.R;

import java.util.List;

/**
 * Created by zx on 2017/12/5.
 */
public abstract class BaseLoadRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    protected boolean mHasEndView = false;

    public BaseLoadRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public void updateItems(List<T> items) {
        if (items == null) return;
        mHasEndView = false;
        this.mItems.clear();
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void setEndView(boolean visibile){
        mHasEndView = visibile;
        notifyDataSetChanged();
    }

    public static class EndViewHolder extends RecyclerView.ViewHolder {
        TextView view ;

        public EndViewHolder(View item_view) {
            super(item_view);
            this.view = (TextView) item_view.findViewById(R.id.end_text);
        }
    }

}
