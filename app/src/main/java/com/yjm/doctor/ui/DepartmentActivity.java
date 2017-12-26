package com.yjm.doctor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.yjm.doctor.Config;
import com.yjm.doctor.R;
import com.yjm.doctor.application.YjmApplication;
import com.yjm.doctor.model.DepartMent;
import com.yjm.doctor.model.EventType;
import com.yjm.doctor.model.Hospital;
import com.yjm.doctor.ui.base.BaseActivity;
import com.yjm.doctor.ui.view.ChoiceView;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by zx on 2017/12/9.
 */

public class DepartmentActivity extends BaseActivity{

    ListView listView ;
    private DepartMent level;


    @Override
    public int initView() {
        YjmApplication.toolFinish = true;
        return R.layout.activity_level;
    }

    @Override
    public void finishButton() {
        if(null != level)
            EventBus.getDefault().post(new EventType(Config.DEPARTMENT_EVENTTYPE,level));
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final List data = (List<Hospital>)intent.getSerializableExtra("list");
        if(null != data && 0 < data.size())
            level = (DepartMent) data.get(0);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ListAdapter adapter = new ArrayAdapter<DepartMent>(this, R.layout.radio_item, data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final ChoiceView view;
                if(convertView == null) {
                    view = new ChoiceView(DepartmentActivity.this);
                } else {
                    view = (ChoiceView)convertView;
                }
                DepartMent level = (DepartMent)getItem(position);
                view.setText(level.getName());

                return view;
            }
        };
        listView.setAdapter(adapter);
        listView.setItemChecked(0,true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                level = (DepartMent) data.get(position);
            }
        });
    }



}
