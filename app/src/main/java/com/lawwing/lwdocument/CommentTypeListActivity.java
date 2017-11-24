package com.lawwing.lwdocument;

import java.util.ArrayList;
import java.util.List;

import com.lawwing.lwdocument.adapter.CommentTypeListAdapter;
import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.gen.CommentTypeInfoDb;
import com.lawwing.lwdocument.gen.CommentTypeInfoDbDao;
import com.lawwing.lwdocument.model.CommentTypeInfoModel;
import com.lawwing.lwdocument.utils.TimeUtils;
import com.lawwing.lwdocument.utils.TitleBarUtils;
import com.tubb.smrv.SwipeMenuRecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentTypeListActivity extends BaseActivity
{
    @BindView(R.id.commentTypeRecyclerView)
    SwipeMenuRecyclerView commentTypeRecyclerView;
    
    @BindView(R.id.nullTips)
    TextView nullTips;
    
    @BindView(R.id.right_text_btn)
    TextView right_text_btn;
    
    private TitleBarUtils titleBarUtils;
    
    private CommentTypeInfoDbDao mCommentTypeInfoDbDao;
    
    private ArrayList<CommentTypeInfoModel> datas;
    
    private CommentTypeListAdapter adapter;
    
    private String type = "";
    
    public static Intent newInstance(Activity activity, String type)
    {
        Intent intent = new Intent(activity, CommentTypeListActivity.class);
        intent.putExtra("type", type);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initIntentData();
        setContentView(R.layout.activity_comment_type_list);
        ButterKnife.bind(this);
        mCommentTypeInfoDbDao = LWDApp.get()
                .getDaoSession()
                .getCommentTypeInfoDbDao();
        datas = new ArrayList<>();
        titleBarUtils = new TitleBarUtils(CommentTypeListActivity.this);
        titleBarUtils.initTitle("批阅分类列表");
        nullTips.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // 添加分类弹窗
                addTypeDialog();
            }
        });
        right_text_btn.setText("添加");
        right_text_btn.setVisibility(View.VISIBLE);
        right_text_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addTypeDialog();
                
            }
        });
        initRecyclerView();
        
        getDataByDb();
        
    }
    
    private void addTypeDialog()
    {
        // 添加
        final EditText editText = new EditText(CommentTypeListActivity.this);
        new AlertDialog.Builder(CommentTypeListActivity.this)
                .setTitle("请输入类型名！")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (!TextUtils.isEmpty(editText.getText().toString()))
                        {
                            insertCommentType(editText.getText().toString());
                        }
                    }
                    
                })
                .setNegativeButton("取消", null)
                .show();
    }
    
    private void insertCommentType(String name)
    {
        CommentTypeInfoDb commentTypeDb = new CommentTypeInfoDb();
        commentTypeDb.setCreateTime(TimeUtils.getCurTimeMills());
        commentTypeDb.setTypeName(name);
        commentTypeDb.setIsEdit(true);
        commentTypeDb.setIsShow(true);
        mCommentTypeInfoDbDao.insertOrReplace(commentTypeDb);
        getDataByDb();
    }
    
    private void getDataByDb()
    {
        datas.clear();
        List<CommentTypeInfoDb> commentTypes = mCommentTypeInfoDbDao.loadAll();
        for (CommentTypeInfoDb commentType : commentTypes)
        {
            CommentTypeInfoModel model = new CommentTypeInfoModel();
            model.setId(commentType.getId());
            model.setCreateTime(commentType.getCreateTime());
            model.setTypeName(commentType.getTypeName());
            model.setEdit(commentType.getIsEdit());
            model.setShow(commentType.getIsShow());
            model.setCount(commentType.getCommentInfoDbs().size());
            Log.e("test", commentType.getCommentInfoDbs().toString());
            datas.add(model);
        }
        if (datas.size() > 0)
        {
            nullTips.setVisibility(View.GONE);
        }
        else
        {
            nullTips.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }
    
    private void initIntentData()
    {
        Intent intent = getIntent();
        if (intent != null)
        {
            if (intent.hasExtra("type"))
            {
                type = intent.getStringExtra("type");
            }
        }
    }
    
    private void initRecyclerView()
    {
        nullTips.setVisibility(View.VISIBLE);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        commentTypeRecyclerView.setLayoutManager(manager);
        
        adapter = new CommentTypeListAdapter(CommentTypeListActivity.this,
                datas, type);
        commentTypeRecyclerView.setAdapter(adapter);
    }
}
