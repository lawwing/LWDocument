package com.lawwing.lwdocument.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawwing.lwdocument.MainActivity;
import com.lawwing.lwdocument.R;
import com.lawwing.lwdocument.model.FileBean;

import java.util.ArrayList;

/**
 * Created by lawwing on 2017/11/7.
 */
public class OfficeFileAdapter
        extends RecyclerView.Adapter<OfficeFileAdapter.OfficeFileHolder>
{
    private ArrayList<FileBean> datas;
    
    private Activity activity;
    
    private LayoutInflater inflater;
    
    public OfficeFileAdapter(ArrayList<FileBean> datas, Activity activity)
    {
        this.datas = datas;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }
    
    @Override
    public OfficeFileHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new OfficeFileHolder(
                inflater.inflate(R.layout.file_list_item, parent, false));
    }
    
    @Override
    public void onBindViewHolder(OfficeFileHolder holder, int position)
    {
        final FileBean model = datas.get(position);
        if (model != null)
        {
            holder.fileName.setText(model.getFilename());
            holder.filePath.setText("路径:" + model.getPath());
            holder.bossLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    activity.startActivity(MainActivity.newInstance(activity,
                            model.getPath()));
                }
            });
        }
    }
    
    @Override
    public int getItemCount()
    {
        return datas.size();
    }
    
    public class OfficeFileHolder extends RecyclerView.ViewHolder
    {
        TextView fileName;
        
        TextView filePath;
        
        LinearLayout bossLayout;
        
        public OfficeFileHolder(View itemView)
        {
            super(itemView);
            fileName = (TextView) itemView.findViewById(R.id.fileName);
            filePath = (TextView) itemView.findViewById(R.id.filePath);
            bossLayout = (LinearLayout) itemView.findViewById(R.id.bossLayout);
        }
    }
}
