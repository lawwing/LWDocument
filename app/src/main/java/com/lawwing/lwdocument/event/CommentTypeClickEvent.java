package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.CommentTypeInfoModel;

/**
 * Created by lawwing on 2017/11/19.
 */

public class CommentTypeClickEvent {
    private String type;
    private CommentTypeInfoModel model;

    public CommentTypeClickEvent(String type, CommentTypeInfoModel model) {
        this.type = type;
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public CommentTypeInfoModel getModel() {
        return model;
    }
}
