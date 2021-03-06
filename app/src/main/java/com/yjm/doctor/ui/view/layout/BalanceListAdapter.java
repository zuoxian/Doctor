package com.yjm.doctor.ui.view.layout;

import android.content.Context;
import android.net.ParseException;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yjm.doctor.R;
import com.yjm.doctor.model.AppointmentInfo;
import com.yjm.doctor.ui.base.BaseLoadRecyclerAdapter;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zs on 2017/12/16.
 */

public class BalanceListAdapter extends BaseLoadRecyclerAdapter<BalanceListBean.ObjBean.RowsBean> {

    private int amountColor=0; //0  橘红色  1  橙色

    public BalanceListAdapter(Context context,int amountColor) {
        super(context);
        this.amountColor=amountColor;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_balance_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder= (ItemViewHolder) holder;
        BalanceListBean.ObjBean.RowsBean bean=getItem(position);

        if (!TextUtils.isEmpty(bean.getRefTypeZh())) {
            viewHolder.mTvRefTypeZh.setText(bean.getRefTypeZh());
        }


        if (amountColor==0) {
            viewHolder.mTvAmount.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        }else if (amountColor==1) {
            viewHolder.mTvAmount.setTextColor(mContext.getResources().getColor(R.color.login_fail_content_title));
        }
        String amount="+"+String.valueOf(bean.getAmount());
        viewHolder.mTvAmount.setText(amount);

        viewHolder.mTvCreateTime.setText(longToString(bean.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));

//        if (bean.isStatus()){
            viewHolder.mTvStatus.setText("成功");
//        }else {
//            viewHolder.mTvStatus.setText("失败");
//        }
        if (position == mItems.size()- 1) {
            mListener.onListEnded();
        }
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


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvRefTypeZh;          //类型
        TextView mTvCreateTime;        //时间
        TextView mTvAmount;           //余额
        TextView mTvStatus;        //状态

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvRefTypeZh=(TextView)itemView.findViewById(R.id.tv_RefTypeZh);
            mTvCreateTime=(TextView)itemView.findViewById(R.id.tv_CreateTime);
            mTvAmount=(TextView)itemView.findViewById(R.id.tv_Amount);
            mTvStatus=(TextView)itemView.findViewById(R.id.tv_Status);
        }

    }

    private ListAdapterListener mListener;

    public void setListener(ListAdapterListener mListener) {
        this.mListener = mListener;
    }

    public interface ListAdapterListener {
        void onListEnded();
    }

}
