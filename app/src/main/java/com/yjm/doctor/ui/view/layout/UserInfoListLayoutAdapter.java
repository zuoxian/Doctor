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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zs on 2017/12/14.
 */

public class UserInfoListLayoutAdapter extends BaseAdapter {

    private Context mContext;
    private List<ListLayoutModel> mList = new ArrayList<ListLayoutModel>();


    public UserInfoListLayoutAdapter(Context context, List<ListLayoutModel> list) {
        mContext = context;
        mList = list;
    }

    public UserInfoListLayoutAdapter(Context context) {
        mContext = context;

    }

    public void setData(List<ListLayoutModel> list){
        mList = list;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_user_info_layout, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ListLayoutModel model=mList.get(position);

        if (model.getIcon() != 0){
            holder.mImgIcon.setImageResource(model.getIcon());
        }else {
            holder.mImgIcon.setVisibility(View.GONE);
        }

        if (model.getTitle() != 0){
            if (model.getTitleColor() !=0){
                holder.mTvTitle.setTextColor(mContext.getResources().getColor(model.getTitleColor()));
            }
            holder.mTvTitle.setText(model.getTitle());
        }else {
            holder.mTvTitle.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model.getMsg())){
            if (model.getMsgColor() != 0){
                holder.mTvMsg.setTextColor(mContext.getResources().getColor(model.getMsgColor()));
            }
            Log.e("speciality adapter ",model.getMsg());
            holder.mTvMsg.setText(model.getMsg());

        }else {
            holder.mTvMsg.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model.getLogo())){
            holder.mUserIcon.setImageURI(Uri.parse(model.getLogo()));
        }else {
            holder.mUserIcon.setVisibility(View.GONE);
        }

        if (model.getImgOperaption() != 0){
            holder.mImgOperations.setImageResource(model.getImgOperaption());
        }else {
            holder.mImgOperations.setVisibility(View.GONE);
        }
        //登录审核通过
        if(model.getImgOperaption() == 0 && model.getTitle() ==  0 && model.getMsg().equals("0")){
            holder.exitButton.setVisibility(View.VISIBLE);
            holder.exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.exitButton();
                }
            });
        }else {
            holder.exitButton.setVisibility(View.GONE);
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
        @BindView(R.id.img_icon)
        ImageView mImgIcon;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.user_icon)
        SimpleDraweeView mUserIcon;
        @BindView(R.id.tv_msg)
        TextView mTvMsg;
        @BindView(R.id.img_operation)
        ImageView mImgOperations;

        @BindView(R.id.exit_button)
        TextView exitButton;

        ViewHolder(View view) { ButterKnife.bind(this, view);}
    }



    public interface OnListItemOnClickListener{
        void OnItemClick(int position, ListLayoutModel model);
        void exitButton();
    }

    private OnListItemOnClickListener mClickListener;
    public void setOnListItemOnClickListener(OnListItemOnClickListener mlistener){
        this.mClickListener=mlistener;
    }


}
