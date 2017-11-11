package com.lawwing.lwdocument.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.adapter.OfficeFileAdapter;
import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.model.FileBean;
import com.lawwing.lwdocument.utils.FileUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lawwing on 2017/11/11.
 */

public class PdfFragmnet extends BaseFragment
{
    
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    
    @BindView(R.id.tipsText)
    TextView tipsText;
    
    private OfficeFileAdapter adapter;
    
    private ArrayList<FileBean> datas;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater
                .inflate(R.layout.activity_select_file, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        datas = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new OfficeFileAdapter(datas, getActivity());
        recyclerView.setAdapter(adapter);
        
        getLocalOffice();
        
    }
    
    private void getLocalOffice()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                datas.addAll(FileUtils.getSpecificTypeOfFile(getActivity(),
                        new String[] { "pdf" }));
                handle.sendMessage(new Message());
            }
        }.start();
    }
    
    private Handler handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (datas.size() > 0)
            {
                tipsText.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
            else
            {
                tipsText.setVisibility(View.VISIBLE);
                tipsText.setText("没有找到文件");
            }
        }
    };
    
    @Override
    public void loadData()
    {
        
    }
    
    @Override
    public void loadSubData()
    {
        
    }
}
