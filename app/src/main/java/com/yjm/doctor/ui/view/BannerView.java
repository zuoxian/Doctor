package com.yjm.doctor.ui.view;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yjm.doctor.R;
import com.yjm.doctor.model.Banner;
import com.yjm.doctor.model.BannerBean;
import com.yjm.doctor.ui.base.SimplePagerAdapter;
import com.yjm.doctor.util.DensityUtils;

import java.util.List;


/**
 * Created by zx on 2017/12/9.
 */

public class BannerView extends RelativeLayout {

    private static final long click_timeout = 110;
    private static long click_time = 0;
    private ViewPager mViewPager;
    private LinearLayout group;
    private BannerAdapter bannerAdapter;
    private TextView title;
    private List<Banner> view_data;
    private ImageView[] indicators;
    private float click_x = 0;
    private boolean onTouch = false, onShowNext = true;


    private ViewPager.SimpleOnPageChangeListener pagerChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < indicators.length; i++) {
                indicators[i].setImageResource((position % view_data.size() != i) ? R.drawable.check_nor
                : R.drawable.check);
            }
            Banner item = view_data.get(position % view_data.size());
//            title.setText(item.getTitle());
        }
    };

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void showNextPage() {
        if (!onShowNext) {
            if (!onTouch) onShowNext = true;
        } else if (null != mViewPager && null != bannerAdapter && bannerAdapter.getList().size() > 1) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                click_time = System.currentTimeMillis();
                click_x = ev.getX();
                onTouch = true;
                onShowNext = false;
                break;
            case MotionEvent.ACTION_UP:
                onTouch = false;
                if (bannerAdapter != null && mViewPager != null && bannerAdapter.getCount() > mViewPager.getCurrentItem()
                        && click_time + click_timeout >= System.currentTimeMillis() && Math.abs(ev.getX() - click_x) < 50) {

                    int position = (mViewPager.getCurrentItem() % view_data.size());
                    //TODO 跳转 Type 类型

                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void init() {
        int width = Math.min(
                getResources().getDisplayMetrics().widthPixels,
                getResources().getDisplayMetrics().heightPixels);
        LayoutInflater.from(getContext()).inflate(R.layout.banner_home, this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.getLayoutParams().width = width;
        mViewPager.getLayoutParams().height = width / 2;
        group = (LinearLayout) findViewById(R.id.viewGroup);
        title = (TextView) findViewById(R.id.title);
    }

    public void setData(List<Banner> list) {
        if (list != null && list.size() > 0) {
            view_data = list;
//            title.setText(view_data.get(0).getTitle());
            addIndicator(view_data.size());
            mViewPager.setAdapter(bannerAdapter = new BannerAdapter(view_data));
            mViewPager.setOnPageChangeListener(pagerChangeListener);
            mViewPager.setCurrentItem(500 - 500 % view_data.size());
        } else {
            mViewPager.getLayoutParams().height = 1;
        }
    }

    private void addIndicator(int count) {
        indicators = new ImageView[count];
        group.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView textView = new ImageView(getContext());
            int item_width = DensityUtils.dp2px(getContext(), 5);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(item_width, item_width);
            lp.setMargins(item_width, 0, item_width, 0);
            textView.setLayoutParams(lp);
            indicators[i] = textView;
//            indicators[i].setBackgroundColor(getResources().getColor((i == 0) ?
//                    R.color.tab_host_text_sel_color : android.R.color.darker_gray));
//            indicators[i].setImageDrawable(getResources().getDrawable((i == 0) ?
//                    R.drawable.ic_banner_select : R.drawable.ic_banner_normal,null));
            indicators[i].setImageResource((i == 0) ? R.drawable.check :
            R.drawable.check_nor);

            group.addView(textView);
        }
    }

    private class BannerAdapter extends SimplePagerAdapter<Banner> {

        public BannerAdapter(List<Banner> list) {
            super(list);
        }

        @Override
        public int getCount() {
            if (getList() != null && getList().size() > 0) {
                return Integer.MAX_VALUE;
            } else {
                return 0;
            }
        }

        @Override
        public View getItemView(ViewGroup container, int position) {
            position = position % getList().size();
            Banner item = view_data.get(position);
            SimpleDraweeView imageview = new SimpleDraweeView(container.getContext());

            GenericDraweeHierarchy hierarchy = imageview.getHierarchy();
            hierarchy.setPlaceholderImage(R.color.login_background);//默认加载图片

            imageview.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
            imageview.setBackgroundResource(R.color.login_background);//默认加载图片
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            if (!TextUtils.isEmpty(item.getPicUrl()))
                imageview.setImageURI(Uri.parse(item.getPicUrl()));
            return imageview;
        }

    }

}
