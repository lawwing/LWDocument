package com.lawwing.lwdocument;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;

import com.lawwing.lwdocument.adapter.CommentRecycleAdapter;
import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.event.RecycleCommentTransTypeEvent;
import com.lawwing.lwdocument.fragment.RecycleCommentTransTypeDialogFragment;
import com.lawwing.lwdocument.gen.CommentInfoDb;
import com.lawwing.lwdocument.gen.CommentInfoDbDao;
import com.lawwing.lwdocument.model.CommentInfoModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentRecycleActivity extends BaseActivity
{
    
    @BindView(R.id.back_btn)
    ImageView backBtn;
    
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    
    @BindView(R.id.managerBtn)
    TextView managerBtn;
    
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    
    @BindView(R.id.deleteCommentBtn)
    TextView deleteCommentBtn;
    
    @BindView(R.id.transTypeBtn)
    TextView transTypeBtn;
    
    private CommentInfoDbDao mCommentInfoDbDao;
    
    private ArrayList<CommentInfoModel> datas;
    
    private ArrayList<CommentInfoModel> selectDatas;
    
    private CommentRecycleAdapter adapter;
    
    private RecycleCommentTransTypeDialogFragment transTypeFragment;
    
    public static Intent newInstance(Activity activity)
    {
        Intent intent = new Intent(activity, CommentRecycleActivity.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_recycle);
        ButterKnife.bind(this);
        mCommentInfoDbDao = LWDApp.get().getDaoSession().getCommentInfoDbDao();
        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        initData();
        initRecyclerView();
        LWDApp.getEventBus().register(this);
    }
    
    @Override
    protected void onDestroy()
    {
        LWDApp.getEventBus().unregister(this);
        super.onDestroy();
    }
    
    private void initData()
    {
        datas = new ArrayList<>();
        selectDatas = new ArrayList<>();
        List<CommentInfoDb> dbs = mCommentInfoDbDao.queryBuilder()
                .where(CommentInfoDbDao.Properties.TypeId.eq(-1))
                .list();
        for (CommentInfoDb commentInfoDb : dbs)
        {
            if (!commentInfoDb.isTrueDelete())
            {
                CommentInfoModel model = new CommentInfoModel("Unknow");
                model.setDocname(commentInfoDb.getDocname());
                model.setDocpath(commentInfoDb.getDocpath());
                model.setId(commentInfoDb.getId());
                model.setName(commentInfoDb.getName());
                model.setPath(commentInfoDb.getPath());
                model.setTime(commentInfoDb.getTime());
                model.setTypeId(commentInfoDb.getTypeId());
                model.setTrueDelete(commentInfoDb.isTrueDelete());
                model.setSelect(false);
                datas.add(model);
            }
        }
    }
    
    private void initRecyclerView()
    {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        
        adapter = new CommentRecycleAdapter(CommentRecycleActivity.this, datas,
                false);
        recyclerView.setAdapter(adapter);
    }
    
    @OnClick({ R.id.managerBtn, R.id.transTypeBtn, R.id.deleteCommentBtn })
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.managerBtn:
                if (bottomLayout.getVisibility() == View.VISIBLE)
                {
                    bottomLayout.setVisibility(View.GONE);
                    adapter.setSelectMode(false);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    bottomLayout.setVisibility(View.VISIBLE);
                    adapter.setSelectMode(true);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.deleteCommentBtn:
                initSelectDatas();
                if (selectDatas.size() > 0)
                {
                    showDeleteDialog();
                }
                else
                {
                    showShortToast("请先选择要删除的批阅文件");
                }
                break;
            case R.id.transTypeBtn:
                initSelectDatas();
                if (selectDatas.size() > 0)
                {
                    showTransDialog();
                }
                else
                {
                    showShortToast("请先选择要转移的批阅文件");
                }
                break;
            default:
                break;
        }
    }
    
    private void showTransDialog()
    {
        transTypeFragment = new RecycleCommentTransTypeDialogFragment(
                selectDatas);
        transTypeFragment.show(getFragmentManager(), "转移分类");
    }
    
    private void showDeleteDialog()
    {
    }
    
    private void initSelectDatas()
    {
        selectDatas.clear();
        for (CommentInfoModel model : datas)
        {
            if (!model.isTrueDelete())
            {
                if (model.isSelect())
                {
                    selectDatas.add(model);
                }
            }
        }
    }
    
    @Subscribe
    public void transComment(RecycleCommentTransTypeEvent event)
    {
        if (event != null)
        {
            for (CommentInfoModel selectData : selectDatas)
            {
                CommentInfoDb db = mCommentInfoDbDao.load(selectData.getId());
                db.setTypeId(event.getSelectTypeBean().getId());
                mCommentInfoDbDao.update(db);
            }
            
            LWDApp.get().setmDaoSession(LWDApp.get().getDaoSession());
            mCommentInfoDbDao = LWDApp.get()
                    .getDaoSession()
                    .getCommentInfoDbDao();
            initData();
            adapter.notifyDataSetChanged();
        }
    }
}
