package com.lawwing.lwdocument.event;

import com.lawwing.lwdocument.model.ColorModel;

/**
 * Created by lawwing on 2017/11/18.
 */

public class PickerColorBgEvent {
    private ColorModel model;

    public PickerColorBgEvent(ColorModel model) {
        this.model = model;
    }

    public ColorModel getModel() {
        return model;
    }
}
