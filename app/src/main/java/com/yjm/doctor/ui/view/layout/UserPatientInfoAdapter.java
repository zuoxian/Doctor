package com.yjm.doctor.ui.view.layout;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.R;
import com.yjm.doctor.model.User;
import com.yjm.doctor.model.UserPatientInfo;
import com.yjm.doctor.model.UserPatientInfos;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zs on 2017/12/14.
 */

public class UserPatientInfoAdapter extends BaseAdapter {

    private Context mContext;
    private List<UserPatientInfo> mList = new ArrayList<UserPatientInfo>();


    public UserPatientInfoAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<UserPatientInfo> list){
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_user_patient_info_layout, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final UserPatientInfo model=mList.get(position);


        String address = "";

        if (null != model && !TextUtils.isEmpty(model.getAppointAddress())){
            address = model.getAppointAddress();
        }

        String time = "";

        if (null != model && !TextUtils.isEmpty(model.getAppointTime())){
            time = model.getAppointTime();
        }

        holder.mTvTitle.setText(time +"  "+address);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != model)
                    mClickListener.OnItemClick(position,model);
            }
        });

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;


        ViewHolder(View view) { ButterKnife.bind(this, view);}
    }

    public interface OnListItemOnClickListener{
        void OnItemClick(int position, UserPatientInfo model);
    }

    private OnListItemOnClickListener mClickListener;
    public void setOnListItemOnClickListener(OnListItemOnClickListener mlistener){
        this.mClickListener=mlistener;
    }

}
