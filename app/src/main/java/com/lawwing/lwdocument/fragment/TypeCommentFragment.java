package com.lawwing.lwdocument.fragment;

import static com.lawwing.lwdocument.base.StaticDatas.CLOSEWHEEL;
import static com.lawwing.lwdocument.base.StaticDatas.OPENWHEEL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;

import com.itheima.wheelpicker.WheelPicker;
import com.lawwing.lwdocument.LWDApp;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.adapter.DateCommentAdapter;
import com.lawwing.lwdocument.base.BaseFragment;
import com.lawwing.lwdocument.event.ChangeTitleContent;
import com.lawwing.lwdocument.event.DeleteCommentEvent;
import com.lawwing.lwdocument.event.OpenWheelEvent;
import com.lawwing.lwdocument.event.TransTypeEvent;
import com.lawwing.lwdocument.gen.CommentInfoDb;
import com.lawwing.lwdocument.gen.CommentInfoDbDao;
import com.lawwing.lwdocument.gen.CommentTypeInfoDb;
import com.lawwing.lwdocument.gen.CommentTypeInfoDbDao;
import com.lawwing.lwdocument.model.CommentInfoModel;
import com.lawwing.lwdocument.model.CommentTypeInfoModel;
import com.lawwing.lwdocument.utils.SortUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.lawwing.homeslidemenu.interfaces.ScreenShotable;

/**
 * Created by lawwing on 2017/11/23.
 */

public class TypeCommentFragment extends BaseFragment implements ScreenShotable
{
    private View containerView;
    
    private Bitmap bitmap;
    
    private WheelPicker wheelPicker;
    
    private RecyclerView recyclerView;
    
    private RelativeLayout wheelLayout;
    
    private TextView cancleType;
    
    private TextView enterType;
    
    private List<CommentTypeInfoModel> datas;
    
    private CommentTypeInfoDbDao mCommentTypeInfoDbDao;
    
    private CommentInfoDbDao mCommentInfoDbDao;
    
    private DateCommentAdapter adapter;
    
    private ArrayList<CommentInfoModel> listDatas;
    
    private int wheelPoi = 0;
    
    private int selectPoi = 0;
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }
    
    public static TypeCommentFragment newInstance()
    {
        TypeCommentFragment contentFragment = new TypeCommentFragment();
        Bundle bundle = new Bundle();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater
                .inflate(R.layout.fragment_type_comment_list, container, false);
        
        wheelPicker = (WheelPicker) rootView.findViewById(R.id.wheelPicker);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        wheelLayout = (RelativeLayout) rootView.findViewById(R.id.wheelLayout);
        cancleType = (TextView) rootView.findViewById(R.id.cancleType);
        enterType = (TextView) rootView.findViewById(R.id.enterType);
        mCommentTypeInfoDbDao = LWDApp.get()
                .getDaoSession()
                .getCommentTypeInfoDbDao();
        mCommentInfoDbDao = LWDApp.get().getDaoSession().getCommentInfoDbDao();
        
        listDatas = new ArrayList<>();
        initWheelPicker();
        initCommentListData();
        initRecyclerView();
        LWDApp.getEventBus().register(this);
        return rootView;
    }
    
    @Override
    public void onDestroy()
    {
        LWDApp.getEventBus().unregister(this);
        super.onDestroy();
    }
    
    @Subscribe
    public void openWheel(OpenWheelEvent event)
    {
        if (event != null)
        {
            if (OPENWHEEL.equals(event.getType()))
            {
                wheelLayout.setVisibility(View.VISIBLE);
            }
            else if (CLOSEWHEEL.equals(event.getType()))
            {
                wheelLayout.setVisibility(View.GONE);
            }
        }
    }
    
    private void initWheelPicker()
    {
        initDbDatas();
        wheelPicker.setData(datas);
        wheelPicker.setSelectedItemPosition(0);
        wheelPicker.setOnItemSelectedListener(
                new WheelPicker.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(WheelPicker wheelPicker,
                            Object o, int i)
                    {
                        wheelPoi = i;
                    }
                });
        cancleType.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                wheelLayout.setVisibility(View.GONE);
            }
        });
        enterType.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectPoi = wheelPoi;
                if (selectPoi == 0)
                {
                    // 加载全部
                    initCommentListData();
                    adapter.notifyDataSetChanged();
                    LWDApp.getEventBus()
                            .post(new ChangeTitleContent("批阅分类-全部 ⇓"));
                    selectTypeId = -1;
                }
                else
                {
                    CommentTypeInfoModel model = datas.get(selectPoi);
                    selectTypeId = model.getId();
                    getDataByTypeId(model.getId());
                    adapter.notifyDataSetChanged();
                    LWDApp.getEventBus().post(new ChangeTitleContent(
                            "批阅分类-" + model.getTypeName() + " ⇓"));
                }
                wheelLayout.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
            }
        });
        wheelLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                wheelLayout.setVisibility(View.GONE);
            }
        });
    }
    
    private long selectTypeId = -1;
    
    private void getDataByTypeId(long typeId)
    {
        listDatas.clear();
        List<CommentTypeInfoDb> dbs = mCommentTypeInfoDbDao.queryBuilder()
                .where(CommentTypeInfoDbDao.Properties.Id.eq(typeId))
                .list();
        for (CommentTypeInfoDb db : dbs)
        {
            List<CommentInfoDb> commentInfoDbs = db.getCommentInfoDbs();
            
            for (CommentInfoDb commentInfoDb : commentInfoDbs)
            {
                CommentInfoModel model = new CommentInfoModel(db.getTypeName());
                model.setDocname(commentInfoDb.getDocname());
                model.setDocpath(commentInfoDb.getDocpath());
                model.setId(commentInfoDb.getId());
                model.setName(commentInfoDb.getName());
                model.setPath(commentInfoDb.getPath());
                model.setTime(commentInfoDb.getTime());
                model.setTypeId(commentInfoDb.getTypeId());
                listDatas.add(model);
            }
        }
        SortUtils.sortData(listDatas);
        
    }
    
    private void initRecyclerView()
    {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        
        adapter = new DateCommentAdapter(listDatas, getActivity(), "类型更多操作");
        recyclerView.setAdapter(adapter);
    }
    
    private void initCommentListData()
    {
        listDatas.clear();
        List<CommentInfoDb> dbs = mCommentInfoDbDao.queryBuilder()
                .orderDesc(CommentInfoDbDao.Properties.Time)
                .list();
        for (CommentInfoDb comment : dbs)
        {
            CommentTypeInfoDb typeInfoDb = mCommentTypeInfoDbDao.queryBuilder()
                    .where(CommentTypeInfoDbDao.Properties.Id
                            .eq(comment.getTypeId()))
                    .list()
                    .get(0);
            CommentInfoModel model;
            if (typeInfoDb != null)
            {
                model = new CommentInfoModel(typeInfoDb.getTypeName());
            }
            else
            {
                model = new CommentInfoModel("Unknow");
            }
            model.setName(comment.getName());
            model.setPath(comment.getPath());
            model.setDocpath(comment.getDocpath());
            model.setDocname(comment.getDocname());
            model.setId(comment.getId());
            model.setTime(comment.getTime());
            model.setTypeId(comment.getTypeId());
            model.setDateId(comment.getDateId());
            listDatas.add(model);
        }
    }
    
    private void initDbDatas()
    {
        datas = new ArrayList<>();
        CommentTypeInfoModel allmodel = new CommentTypeInfoModel();
        allmodel.setCreateTime(0);
        allmodel.setEdit(false);
        allmodel.setTypeName("全部");
        allmodel.setId((long) -1);
        allmodel.setShow(true);
        allmodel.setCount(0);
        datas.add(allmodel);
        // datas.add("全部");
        List<CommentTypeInfoDb> dbs = mCommentTypeInfoDbDao.loadAll();
        for (CommentTypeInfoDb db : dbs)
        {
            CommentTypeInfoModel model = new CommentTypeInfoModel();
            model.setCreateTime(db.getCreateTime());
            model.setEdit(db.getIsEdit());
            model.setTypeName(db.getTypeName());
            model.setId(db.getId());
            model.setShow(db.getIsShow());
            model.setCount(db.getCommentInfoDbs().size());
            datas.add(model);
        }
    }
    
    @Override
    public void loadData()
    {
        
    }
    
    @Override
    public void loadSubData()
    {
        
    }
    
    @Override
    public void takeScreenShot()
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                TypeCommentFragment.this.bitmap = bitmap;
            }
        };
        
        thread.start();
    }
    
    @Override
    public Bitmap getBitmap()
    {
        return bitmap;
    }
    
    @Subscribe
    public void deleteComment(DeleteCommentEvent event)
    {
        if (event != null)
        {
            mCommentInfoDbDao.deleteByKey(event.getSelectCommentInfo().getId());
            listDatas.remove(event.getSelectPoision());
            // 删除卡片操作
            adapter.notifyItemRemoved(event.getSelectPoision());
            adapter.notifyDataSetChanged();
            File file = new File(event.getSelectCommentInfo().getPath());
            if (file.exists())
            {
                file.delete();
            }
        }
    }
    
    @Subscribe
    public void transComment(TransTypeEvent event)
    {
        if (event != null)
        {
            CommentInfoDb commentInfoDb = mCommentInfoDbDao
                    .load(event.getSelectCommentInfo().getId());
            commentInfoDb.setTypeId(event.getSelectTypeBean().getId());
            mCommentInfoDbDao.insertOrReplace(commentInfoDb);
            
            CommentTypeInfoDb commentTypeInfoDb = mCommentTypeInfoDbDao
                    .load(event.getSelectTypeBean().getId());
            commentTypeInfoDb.getCommentInfoDbs().add(commentInfoDb);
            mCommentTypeInfoDbDao.insertOrReplace(commentTypeInfoDb);
            
            if (selectTypeId != -1)
            {
                CommentTypeInfoDb removeTypeInfoDb = mCommentTypeInfoDbDao
                        .load(selectTypeId);
                removeTypeInfoDb.getCommentInfoDbs().remove(commentInfoDb);
                mCommentTypeInfoDbDao.insertOrReplace(removeTypeInfoDb);
                if (selectTypeId != event.getSelectTypeBean().getId())
                {
                    listDatas.remove(event.getSelectPoision());
                    adapter.notifyItemRemoved(event.getSelectPoision());
                    adapter.notifyDataSetChanged();
                }
            }
            else
            {
                CommentTypeInfoDb removeTypeInfoDb = mCommentTypeInfoDbDao
                        .load(event.getSelectCommentInfo().getTypeId());
                removeTypeInfoDb.getCommentInfoDbs().remove(commentInfoDb);
                mCommentTypeInfoDbDao.insertOrReplace(removeTypeInfoDb);
                listDatas.get(event.getSelectPoision())
                        .setTypeId(event.getSelectTypeBean().getId());
                listDatas.get(event.getSelectPoision())
                        .setTypeName(event.getSelectTypeBean().getTypeName());
                adapter.notifyDataSetChanged();
            }
        }
    }
    
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            getDataByTypeId(selectTypeId);
            adapter.notifyDataSetChanged();
            super.handleMessage(msg);
        }
    };
}
