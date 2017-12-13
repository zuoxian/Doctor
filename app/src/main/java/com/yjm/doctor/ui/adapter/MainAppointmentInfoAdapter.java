package com.yjm.doctor.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.R;
import com.yjm.doctor.model.AppointmentInfo;
import com.yjm.doctor.model.Customer;
import com.yjm.doctor.model.User;
import com.yjm.doctor.ui.base.BaseLoadRecyclerAdapter;


/**
 * Created by zx on 2017/12/10.
 */
public class MainAppointmentInfoAdapter extends BaseLoadRecyclerAdapter<AppointmentInfo> {



    private class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ItemViewHolder(View item_view) {
            super(item_view);
            this.view = item_view;
        }
    }


    public MainAppointmentInfoAdapter(Context context) {
        super(context);

    }

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        return count;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_appiontment_item, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        Log.i("main","micro position " +position);

            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            initItem(position, holder.view);

        if (position == mItems.size()- 1) {
            mListener.onListEnded();
        }

    }

    private void initItem(int position, View item_view) {
        Log.i("main","micro top  " +position);

            View view = item_view;

            final AppointmentInfo item = getItem(position);

            SimpleDraweeView sdvImage = (SimpleDraweeView) view.findViewById(R.id.user_icon);
            TextView username = (TextView) view.findViewById(R.id.username);
            TextView sex_age = (TextView) view.findViewById(R.id.sex_age);
            TextView time = (TextView) view.findViewById(R.id.time);
        TextView address = (TextView) view.findViewById(R.id.address);
        User user = item.getUser();
        if(user!= null ) {
            if (!TextUtils.isEmpty(user.getPic()))
                    sdvImage.setImageURI(Uri.parse(user.getPic()));
            Customer customer = user.getCustomer();
            Log.i("log",username+"");
            if(null != customer) {
                if (!TextUtils.isEmpty(customer.getRealName())) {
                    username.setText(customer.getRealName());
                }
            }

            if(null != customer){
                String sex =  "女";
                if(1==customer.getSex()){
                    sex =  "男";
                }
                sex_age.setText(sex+"   "+customer.getAge()+"岁" );
            }
        }

        if(!TextUtils.isEmpty(item.getAppointTime())){
            time.setText("预约时间："+item.getAppointTime());
        }
        String address1 = "预约地点： ";
        if(!TextUtils.isEmpty(item.getAppointAddress()))
            address1 = address1 + item.getAppointAddress();
            address.setText(address1);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onListItemClick(item);
                }
            });

    }

    private ListAdapterListener mListener;

    public void setListener(ListAdapterListener mListener) {
        this.mListener = mListener;
    }

    public interface ListAdapterListener {
        void onListItemClick(AppointmentInfo item);
        void onListEnded();
    }

}
