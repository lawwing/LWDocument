package com.lawwing.lwdocument.utils;

import com.lawwing.lwdocument.model.CommentInfoModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by lawwing on 2017/11/24.
 */

public class SortUtils
{
    
    public static void sortData(List<CommentInfoModel> datas)
    {
        if (datas.size() > 0)
        {
            Collections.sort(datas, new Comparator<CommentInfoModel>()
            {
                @Override
                public int compare(CommentInfoModel o1, CommentInfoModel o2)
                {
                    
                    long time1 = o1.getTime();
                    
                    long time2 = o2.getTime();
                    
                    if (time1 != 0 && time2 != 0)
                    {
                        if (time1 == time2)
                        {
                            return 0;
                        }
                        Date date1 = TimeUtils.millsToDate(time1);
                        Date date2 = TimeUtils.millsToDate(time2);
                        /** 对日期字段进行升序，如果欲降序可采用after方法 */
                        if (date1.before(date2))
                        {
                            return 1;
                        }
                        else
                        {
                            return -1;
                        }
                    }
                    
                    return 0;
                    
                }
            });
        }
    }
    
}
