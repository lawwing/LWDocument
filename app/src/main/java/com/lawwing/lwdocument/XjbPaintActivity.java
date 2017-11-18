package com.lawwing.lwdocument;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.Subscribe;

import com.lawwing.lwdocument.adapter.ColorListAdapter;
import com.lawwing.lwdocument.base.BaseActivity;
import com.lawwing.lwdocument.base.StaticDatas;
import com.lawwing.lwdocument.event.ColorAddEvent;
import com.lawwing.lwdocument.event.ColorListEvent;
import com.lawwing.lwdocument.fragment.PickerColorDialogFragment;
import com.lawwing.lwdocument.gen.ColorInfoDb;
import com.lawwing.lwdocument.gen.ColorInfoDbDao;
import com.lawwing.lwdocument.gen.PaintInfoDb;
import com.lawwing.lwdocument.gen.PaintInfoDbDao;
import com.lawwing.lwdocument.model.ColorModel;
import com.lawwing.lwdocument.utils.FileManager;
import com.lawwing.lwdocument.utils.ImageUtils;
import com.lawwing.lwdocument.utils.TimeUtils;
import com.lawwing.lwdocument.widget.PaintView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XjbPaintActivity extends BaseActivity
        implements PaintView.IPaintViewListener
{
    @BindView(R.id.pv)
    PaintView pv;
    
    @BindView(R.id.iv_comment_redcolor)
    TextView mRedColor;
    
    @BindView(R.id.iv_comment_greencolor)
    TextView mGreenColor;
    
    @BindView(R.id.iv_comment_blackcolor)
    TextView mBlackColor;
    
    @BindView(R.id.iv_comment_bluecolor)
    TextView mBlueColor;
    
    @BindView(R.id.iv_comment_whitecolor)
    TextView mWhiteColor;
    
    @BindView(R.id.iv_comment_arrow)
    ImageView mArrow;
    
    @BindView(R.id.iv_comment_text)
    ImageView mText;
    
    @BindView(R.id.iv_comment_rectangle)
    ImageView mRectangle;
    
    @BindView(R.id.iv_comment_freedom)
    ImageView mFreedom;
    
    @BindView(R.id.iv_cancle)
    ImageView mCancleIV;
    
    @BindView(R.id.iv_redo)
    ImageView mRedoIV;
    
    @BindView(R.id.exit)
    TextView mExit;
    
    @BindView(R.id.complete)
    ImageView mComplete;
    
    @BindView(R.id.toolsBtn)
    ImageView toolsBtn;
    
    @BindView(R.id.moreColorRecyclerView)
    RecyclerView moreColorRecyclerView;
    // @BindView(R.id.ll_rightmenu)
    // LinearLayout mRightMenuLayout;
    
    @BindView(R.id.toolsLayout)
    LinearLayout toolsLayout;
    
    @BindView(R.id.seekBar1)
    SeekBar seekBar1;
    
    // 保存图片的路径
    private String path = "";
    
    // 保存图片的bitmap
    private Bitmap bitmap;
    
    private PaintInfoDbDao mPaintInfoDbDao;
    
    private String docname;
    
    private String docpath;
    
    private int width;
    
    private int height;
    
    private int color;
    
    private boolean isShowTools = true;
    
    /**
     * 颜色列表相关
     */
    private ArrayList<ColorModel> colorDatas;
    
    private ColorListAdapter adapter;
    
    private PickerColorDialogFragment fragment;
    
    private ColorInfoDbDao mColorInfoDbDao;
    
    public static Intent newInstance(Activity activity, int color)
    {
        Intent intent = new Intent(activity, XjbPaintActivity.class);
        intent.putExtra("color", color);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_xjb_paint);
        ButterKnife.bind(this);
        WindowManager wm = (WindowManager) XjbPaintActivity.this
                .getSystemService(Context.WINDOW_SERVICE);
        
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        mColorInfoDbDao = LWDApp.get().getDaoSession().getColorInfoDbDao();
        pv.drawBackgroundColor(color);
        pv.setListener(this);
        mPaintInfoDbDao = LWDApp.get().getDaoSession().getPaintInfoDbDao();
        
        StaticDatas.width = seekBar1.getProgress();
        pv.setColorOrType();
        seekBar1.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    
                    @Override
                    public void onStopTrackingTouch(SeekBar arg0)
                    {
                        // TODO Auto-generated method stub
                        
                    }
                    
                    @Override
                    public void onStartTrackingTouch(SeekBar arg0)
                    {
                        // TODO Auto-generated method stub
                        
                    }
                    
                    @Override
                    public void onProgressChanged(SeekBar arg0, int arg1,
                            boolean arg2)
                    {
                        // TODO Auto-generated method stub
                        StaticDatas.width = arg1;
                        pv.setColorOrType();
                    }
                });
        initColorDataByDb();
        initRecyclerView();
        LWDApp.getEventBus().register(this);
    }
    
    @Override
    protected void onDestroy()
    {
        LWDApp.getEventBus().unregister(this);
        super.onDestroy();
    }
    
    private void initRecyclerView()
    {
        GridLayoutManager manager = new GridLayoutManager(XjbPaintActivity.this,
                5);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        moreColorRecyclerView.setLayoutManager(manager);
        adapter = new ColorListAdapter(XjbPaintActivity.this, colorDatas);
        moreColorRecyclerView.setAdapter(adapter);
    }
    
    /**
     * 初始化数据
     */
    private void initData()
    {
        StaticDatas.color = String.format("#%06X",
                (0xFFFFFF & getResources().getColor(R.color.color1)));
        StaticDatas.mode = PaintView.COMMENT_FREEDOM;
        PaintView.type = PaintView.COMMENT_FREEDOM;
        Intent intent = getIntent();
        if (intent != null)
        {
            color = intent.getIntExtra("color", Color.WHITE);
        }
        colorDatas = new ArrayList<>();
    }
    
    /**
     * 顶部菜单的相关点击事件
     *
     * @param view
     */
    @OnClick({ R.id.exit, R.id.complete, R.id.iv_redo, R.id.iv_cancle,
            R.id.toolsBtn })
    public void onTopClick(View view)
    {
        switch (view.getId())
        {
            case R.id.exit:
                initDialog();
                break;
            case R.id.complete:
                // 完成的点击事件
                showMyDialog();
                break;
            case R.id.iv_redo:
                // 重做,有bug
                pv.redo();
                break;
            case R.id.iv_cancle:
                // 撤销
                pv.undo();
                break;
            case R.id.toolsBtn:
                if (isShowTools)
                {
                    toolsBtn.setImageResource(R.mipmap.tools);
                    toolsLayout.setVisibility(View.GONE);
                    isShowTools = !isShowTools;
                }
                else
                {
                    toolsBtn.setImageResource(R.mipmap.tools_dismiss);
                    toolsLayout.setVisibility(View.VISIBLE);
                    isShowTools = !isShowTools;
                }
                break;
        }
    }
    
    /**
     * 取消的弹框
     */
    private void initDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("是否退出当前页面？退出将会丢失您的备注");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        builder.show();
        
    }
    
    private Bitmap getBitmapFromView(View view)
    {
        Bitmap bitmap = null;
        try
        {
            int width = view.getWidth();
            int height = view.getHeight();
            if (width != 0 && height != 0)
            {
                bitmap = Bitmap
                        .createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.layout(0, 0, width, height);
                view.draw(canvas);
            }
        }
        catch (Exception e)
        {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }
    
    /**
     * 点击完成的操作
     */
    private void showMyDialog()
    {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this).setTitle("给你的涂鸦起个名字吧！")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (!TextUtils.isEmpty(editText.getText().toString()))
                        {
                            bitmap = getBitmapFromView(pv);
                            path = saveImageFile(editText.getText().toString());
                            if ("-1".equals(path))
                            {
                                return;
                            }
                            // 保存
                            if (!TextUtils.isEmpty(path))
                            {
                                File file = new File(path);
                                PaintInfoDb model = new PaintInfoDb();
                                model.setName(file.getName());
                                model.setPath(path);
                                model.setTime(TimeUtils.getCurTimeMills());
                                mPaintInfoDbDao.insertOrReplace(model);
                                
                                showLongToast("保存成功");
                                finish();
                            }
                            else
                            {
                                showLongToast("保存失败");
                            }
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
    
    /**
     * 颜色相关的点击事件
     *
     * @param view
     */
    @OnClick({ R.id.iv_comment_bluecolor, R.id.iv_comment_redcolor,
            R.id.iv_comment_greencolor, R.id.iv_comment_blackcolor,
            R.id.iv_comment_whitecolor })
    public void onColorClick(View view)
    {
        switch (view.getId())
        {
            
            case R.id.iv_comment_bluecolor:
                StaticDatas.color = String.format("#%06X",
                        (0xFFFFFF & getResources().getColor(R.color.color2)));
                initColorView(StaticDatas.color);
                pv.setColorOrType();
                break;
            case R.id.iv_comment_redcolor:
                StaticDatas.color = String.format("#%06X",
                        (0xFFFFFF & getResources().getColor(R.color.color1)));
                initColorView(StaticDatas.color);
                pv.setColorOrType();
                break;
            case R.id.iv_comment_greencolor:
                StaticDatas.color = String.format("#%06X",
                        (0xFFFFFF & getResources().getColor(R.color.color3)));
                initColorView(StaticDatas.color);
                pv.setColorOrType();
                break;
            case R.id.iv_comment_blackcolor:
                StaticDatas.color = String.format("#%06X",
                        (0xFFFFFF & getResources().getColor(R.color.color4)));
                initColorView(StaticDatas.color);
                pv.setColorOrType();
                break;
            case R.id.iv_comment_whitecolor:
                StaticDatas.color = String.format("#%06X",
                        (0xFFFFFF & getResources().getColor(R.color.color5)));
                initColorView(StaticDatas.color);
                pv.setColorOrType();
                break;
        }
    }
    
    /**
     * 初始化颜色的选择
     *
     * @param color
     */
    private void initColorView(String color)
    {
        hideOffenColorSelect();
        if (color.equals(String.format("#%06X",
                (0xFFFFFF & getResources().getColor(R.color.color1)))))
        {
            mRedColor.setBackgroundResource(R.drawable.color_bg1_select);
            mBlueColor.setBackgroundResource(R.drawable.color_bg2_unselect);
            mGreenColor.setBackgroundResource(R.drawable.color_bg3_unselect);
            mBlackColor.setBackgroundResource(R.drawable.color_bg4_unselect);
            mWhiteColor.setBackgroundResource(R.drawable.color_bg5_unselect);
        }
        else if (color.equals(String.format("#%06X",
                (0xFFFFFF & getResources().getColor(R.color.color2)))))
        {
            mRedColor.setBackgroundResource(R.drawable.color_bg1_unselect);
            mBlueColor.setBackgroundResource(R.drawable.color_bg2_select);
            mGreenColor.setBackgroundResource(R.drawable.color_bg3_unselect);
            mBlackColor.setBackgroundResource(R.drawable.color_bg4_unselect);
            mWhiteColor.setBackgroundResource(R.drawable.color_bg5_unselect);
        }
        else if (color.equals(String.format("#%06X",
                (0xFFFFFF & getResources().getColor(R.color.color3)))))
        {
            mRedColor.setBackgroundResource(R.drawable.color_bg1_unselect);
            mBlueColor.setBackgroundResource(R.drawable.color_bg2_unselect);
            mGreenColor.setBackgroundResource(R.drawable.color_bg3_select);
            mBlackColor.setBackgroundResource(R.drawable.color_bg4_unselect);
            mWhiteColor.setBackgroundResource(R.drawable.color_bg5_unselect);
        }
        else if (color.equals(String.format("#%06X",
                (0xFFFFFF & getResources().getColor(R.color.color4)))))
        {
            mRedColor.setBackgroundResource(R.drawable.color_bg1_unselect);
            mBlueColor.setBackgroundResource(R.drawable.color_bg2_unselect);
            mGreenColor.setBackgroundResource(R.drawable.color_bg3_unselect);
            mBlackColor.setBackgroundResource(R.drawable.color_bg4_select);
            mWhiteColor.setBackgroundResource(R.drawable.color_bg5_unselect);
        }
        else if (color.equals(String.format("#%06X",
                (0xFFFFFF & getResources().getColor(R.color.color5)))))
        {
            mRedColor.setBackgroundResource(R.drawable.color_bg1_unselect);
            mBlueColor.setBackgroundResource(R.drawable.color_bg2_unselect);
            mGreenColor.setBackgroundResource(R.drawable.color_bg3_unselect);
            mBlackColor.setBackgroundResource(R.drawable.color_bg4_unselect);
            mWhiteColor.setBackgroundResource(R.drawable.color_bg5_select);
        }
    }
    
    /**
     * 右侧菜单栏的点击事件
     *
     * @param view
     */
    @OnClick({ R.id.iv_comment_arrow, R.id.iv_comment_rectangle,
            R.id.iv_comment_text, R.id.iv_comment_freedom })
    public void onRightMenuClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_comment_arrow:
                StaticDatas.mode = PaintView.COMMENT_ARROW;
                initRMenuView(StaticDatas.mode);
                pv.setColorOrType();
                break;
            case R.id.iv_comment_rectangle:
                StaticDatas.mode = PaintView.COMMENT_RECTANGLE;
                initRMenuView(StaticDatas.mode);
                pv.setColorOrType();
                break;
            case R.id.iv_comment_text:
                StaticDatas.mode = PaintView.COMMENT_TEXT;
                initRMenuView(StaticDatas.mode);
                pv.setColorOrType();
                break;
            case R.id.iv_comment_freedom:
                StaticDatas.mode = PaintView.COMMENT_FREEDOM;
                initRMenuView(StaticDatas.mode);
                pv.setColorOrType();
                break;
        }
    }
    
    /**
     * 点击不同的模式（矩形，文字，箭头）
     *
     * @param mode
     */
    private void initRMenuView(int mode)
    {
        switch (mode)
        {
            case PaintView.COMMENT_ARROW:
                mArrow.setImageResource(R.mipmap.comment_arrow_select);
                mText.setImageResource(R.mipmap.comment_text_unselect);
                mRectangle
                        .setImageResource(R.mipmap.comment_rectangle_unselect);
                mFreedom.setImageResource(R.mipmap.comment_freedom_unselect);
                break;
            case PaintView.COMMENT_TEXT:
                mArrow.setImageResource(R.mipmap.comment_arrow_unselect);
                mText.setImageResource(R.mipmap.comment_text_select);
                mRectangle
                        .setImageResource(R.mipmap.comment_rectangle_unselect);
                mFreedom.setImageResource(R.mipmap.comment_freedom_unselect);
                break;
            case PaintView.COMMENT_RECTANGLE:
                mArrow.setImageResource(R.mipmap.comment_arrow_unselect);
                mText.setImageResource(R.mipmap.comment_text_unselect);
                mRectangle.setImageResource(R.mipmap.comment_rectangle_select);
                mFreedom.setImageResource(R.mipmap.comment_freedom_unselect);
                break;
            case PaintView.COMMENT_FREEDOM:
                mArrow.setImageResource(R.mipmap.comment_arrow_unselect);
                mText.setImageResource(R.mipmap.comment_text_unselect);
                mRectangle
                        .setImageResource(R.mipmap.comment_rectangle_unselect);
                mFreedom.setImageResource(R.mipmap.comment_freedom_select);
                break;
        }
    }
    
    /**
     * 控制上下的动画
     *
     * @param animation1
     * @param view
     */
    private void LayoutAnimation(TranslateAnimation animation1, View view)
    {
        AnimationSet set1 = new AnimationSet(true);
        animation1.setDuration(500);
        set1.addAnimation(animation1);
        view.startAnimation(animation1);
    }
    
    /**
     * 保存bitmap到文件，返回路径
     *
     * @return
     */
    private String saveImageFile(String picname)
    {
        if (null != bitmap)
        {
            File file = new File(FileManager.getPaintFolder().getPath() + "/"
                    + picname + ".jpg");
            if (file.exists())
            {
                showShortToast("已有相同的文件名，请重新命名");
                return "-1";
            }
            else
            {
                if (ImageUtils.save(bitmap,
                        FileManager.getPaintFolder().getPath() + "/" + picname
                                + ".jpg",
                        Bitmap.CompressFormat.JPEG))
                {
                    // showShortToast("保存图片成功");
                    return FileManager.getPaintFolder().getPath() + "/"
                            + picname + ".jpg";
                }
                else
                {
                    // showShortToast("保存图片失败");
                    return "";
                }
            }
        }
        else
        {
            // showShortToast("保存图片失败null");
            return "";
        }
        
    }
    
    @Override
    public void onClickText(final float x, final float y)
    {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this).setTitle("请输入文本内容")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (!TextUtils.isEmpty(editText.getText().toString()))
                        {
                            pv.setTextArea(editText.getText().toString(), x, y);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
    
    @Override
    public void onRefluse(int num, int deletenum)
    {
        if (num == 0)
        {
            mCancleIV.setImageResource(R.mipmap.comment_cancle_cannot);
        }
        else
        {
            mCancleIV.setImageResource(R.mipmap.comment_cancle);
        }
        
        if (deletenum == 0)
        {
            mRedoIV.setImageResource(R.mipmap.comment_redo_cannot);
        }
        else
        {
            mRedoIV.setImageResource(R.mipmap.comment_redo);
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // 拦截返回键
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            initDialog();
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Subscribe
    public void onEventMainThread(ColorListEvent event)
    {
        if (event != null)
        {
            if (!TextUtils.isEmpty(event.getType()))
            {
                switch (event.getType())
                {
                    case "add":
                        fragment = new PickerColorDialogFragment();
                        fragment.show(getFragmentManager(), "选择颜色");
                        break;
                    case "select":
                        // 单独设置显示隐藏
                        selectColorDataByDb(event.getModel().getId());
                        adapter.notifyDataSetChanged();
                        // 画笔颜色
                        StaticDatas.color = event.getModel().getColor();
                        pv.setColorOrType();
                        // 隐藏默认颜色
                        hideNormalColorSelect();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
    /**
     * 取消选择五个默认颜色
     */
    private void hideNormalColorSelect()
    {
        mRedColor.setBackgroundResource(R.drawable.color_bg1_unselect);
        mBlueColor.setBackgroundResource(R.drawable.color_bg2_unselect);
        mGreenColor.setBackgroundResource(R.drawable.color_bg3_unselect);
        mBlackColor.setBackgroundResource(R.drawable.color_bg4_unselect);
        mWhiteColor.setBackgroundResource(R.drawable.color_bg5_unselect);
    }
    
    /**
     * 取消选择自定义颜色
     */
    private void hideOffenColorSelect()
    {
        initColorDataByDb();
        adapter.notifyDataSetChanged();
    }
    
    @Subscribe
    public void onEventMainThread(ColorAddEvent event)
    {
        if (event != null)
        {
            if (!TextUtils.isEmpty(event.getColor()))
            {
                // 添加进本地数据库
                ColorInfoDb colorInfoDb = new ColorInfoDb();
                colorInfoDb.setColor(event.getColor());
                colorInfoDb.setCreateTime(TimeUtils.getCurTimeMills());
                mColorInfoDbDao.insertOrReplace(colorInfoDb);
                // 更新数据源,需要更换方法
                initColorDataAfterAdd();
                hideNormalColorSelect();
                // 刷新界面
                adapter.notifyDataSetChanged();
                // 更换画笔
                StaticDatas.color = event.getColor();
                pv.setColorOrType();
                
            }
        }
    }
    
    private void initColorDataAfterAdd()
    {
        colorDatas.clear();
        List<ColorInfoDb> colorBeans = mColorInfoDbDao.queryBuilder()
                .orderDesc(ColorInfoDbDao.Properties.Id)
                .limit(4)
                .list();
        int count = 0;
        for (ColorInfoDb colorBean : colorBeans)
        {
            ColorModel model = new ColorModel();
            model.setId(colorBean.getId());
            model.setColor(colorBean.getColor());
            model.setCreateTime(colorBean.getCreateTime());
            if (count == 0)
            {
                model.setSelect(true);
            }
            else
            {
                model.setSelect(false);
            }
            colorDatas.add(model);
            count++;
        }
    }
    
    /**
     * 初始化数据库
     */
    private void initColorDataByDb()
    {
        colorDatas.clear();
        List<ColorInfoDb> colorBeans = mColorInfoDbDao.queryBuilder()
                .orderDesc(ColorInfoDbDao.Properties.Id)
                .limit(4)
                .list();
        for (ColorInfoDb colorBean : colorBeans)
        {
            ColorModel model = new ColorModel();
            model.setId(colorBean.getId());
            model.setColor(colorBean.getColor());
            model.setCreateTime(colorBean.getCreateTime());
            model.setSelect(false);
            colorDatas.add(model);
        }
    }
    
    private void selectColorDataByDb(Long id)
    {
        colorDatas.clear();
        List<ColorInfoDb> colorBeans = mColorInfoDbDao.queryBuilder()
                .orderDesc(ColorInfoDbDao.Properties.Id)
                .list();
        int count = 0;
        for (ColorInfoDb colorBean : colorBeans)
        {
            if (count < 4)
            {
                ColorModel model = new ColorModel();
                model.setId(colorBean.getId());
                model.setColor(colorBean.getColor());
                model.setCreateTime(colorBean.getCreateTime());
                if (model.getId() == id)
                {
                    model.setSelect(true);
                }
                else
                {
                    model.setSelect(false);
                }
                colorDatas.add(model);
                count++;
            }
        }
    }
}
