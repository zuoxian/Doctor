package com.yjm.doctor.ui.adapter;

import android.content.Context;
import android.net.ParseException;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yjm.doctor.R;
import com.yjm.doctor.model.Comment;
import com.yjm.doctor.model.SMessage;
import com.yjm.doctor.ui.base.BaseLoadRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by zx on 2017/12/25.
 */

public class MessagesAdapter extends BaseLoadRecyclerAdapter<SMessage> {
    public MessagesAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MessagesAdapter.ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.message_item, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.i("main","micro position " +position);

        MessagesAdapter.ItemViewHolder holder = (MessagesAdapter.ItemViewHolder) viewHolder;
        initItem(position, holder.view);

        if (position == mItems.size()- 1) {
            mListener.onListEnded();
        }
    }

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        return count;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ItemViewHolder(View item_view) {
            super(item_view);
            this.view = item_view;
        }
    }

    private void initItem(int position, View item_view) {
        Log.i("main","micro top  " +position);

        View view = item_view;

        final SMessage item = getItem(position);
        if(null == item)return;

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView content = (TextView) view.findViewById(R.id.content);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView unread_msg_number = (TextView)view.findViewById(R.id.unread_msg_number);
        if(item.isRead()){
            unread_msg_number.setVisibility(View.GONE);
        }else {
            unread_msg_number.setVisibility(View.VISIBLE);
        }

        if(item.getCreateTime()>0) {
            time.setText(longToString(item.getCreateTime(), "MM-dd HH:mm"));
        }
        title.setText(item.getTitle());
        content.setText(item.getPushContent());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListItemClick(item);
            }
        });

    }


    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        return sDateTime;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    private ListAdapterListener mListener;

    public void setListener(ListAdapterListener mListener) {
        this.mListener = mListener;
    }

    public interface ListAdapterListener {
        void onListItemClick(SMessage item);
        void onListEnded();
    }
}
