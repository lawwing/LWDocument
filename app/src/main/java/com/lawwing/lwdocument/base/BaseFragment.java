package com.lawwing.lwdocument.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * <pre>
 * 项目名称：surfond
 * 类描述：
 * 创建人：David
 * 创建时间：2016/11/4 16:38
 * 邮箱：zb@clearcom.com.cn
 * </pre>
 */

public abstract class BaseFragment extends Fragment
{
    
    protected Activity mActivity;
    
    /**
     * 页面是否可见
     */
    protected boolean isVisibleToUser;
    
    /**
     * 控件是否初始化完成
     */
    protected boolean isViewInitiated;
    
    /**
     * 数据是否加载
     */
    protected boolean isDataInitiated;
    
    private static final String TAG = BaseFragment.class.getCanonicalName();
    
    private int REQUEST_CODE_PERMISSION = 0x00098;
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData(false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData(false);
    }
    
    public abstract void loadData();
    
    public abstract void loadSubData();
    
    protected void prepareFetchData(boolean forceUpdate)
    {
        if (isVisibleToUser && isViewInitiated
                && (!isDataInitiated || forceUpdate))
        {
            loadData();
            isDataInitiated = true;
        }
        else
        {
            loadSubData();
        }
    }
    
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        mActivity = getActivity();
    }
    
    @Override
    public void onDetach()
    {
        super.onDetach();
    }
    
    protected void showShortToast(String message)
    {
        
        if (!mActivity.isFinishing() && isAdded())
        {
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
        }
        
    }
    
}
