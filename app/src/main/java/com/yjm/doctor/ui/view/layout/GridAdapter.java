package com.yjm.doctor.ui.view.layout;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.R;
import com.yjm.doctor.model.GridInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zx on 2017/12/14.
 */

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<GridInfo> mList = new ArrayList<GridInfo>();


    public GridAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<GridInfo> list){
        mList =list;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_grid_layout, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final GridInfo model=mList.get(position);


        if (null != model){
            String time2 = null;
            if(!TextUtils.isEmpty(model.getCloseDate())) {
                String time = model.getCloseDate();
                String time1[] = time.split(" ");

                if (0 < time1.length) {
                    time2 = time1[0];
                }
            }
            String week = null ;
            if(!TextUtils.isEmpty(model.getWeek())){
                week = model.getWeek();
            }
            String time = "夜班";
            //0全天 1 上午 2 下午 3 夜班
            if(0 == model.getTime()){
                time = "全天";
            }else if(1 == model.getTime()){
                time = "上午";
            }else if (2 == model.getTime()){
                time = "下午";
            }

            if(!TextUtils.isEmpty(time2))
                holder.mTvTitle.setText(time2 + " "+week+time);
        }



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.OnItemClick(position,model);
            }
        });

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.img_operation)
        ImageView mImgIcon;

        ViewHolder(View view) { ButterKnife.bind(this, view);}
    }

    public interface OnListItemOnClickListener{
        void OnItemClick(int position, GridInfo model);
    }

    private OnListItemOnClickListener mClickListener;
    public void setOnListItemOnClickListener(OnListItemOnClickListener mlistener){
        this.mClickListener=mlistener;
    }

}
