package com.dollop.exam101.main.validation;

import android.widget.TextView;

/**
 * Created by Anil on 6/july/2022.
 */
public class ResultReturn {
    public Validation.Type type;
    public boolean aBoolean;
    public String errorMessage;
    public String parameter;

    public ResultReturn(Validation.Type type, boolean aBoolean, String errorMessage, String parameter, TextView errorTextView) {
        this.type = type;
        this.aBoolean = aBoolean;
        this.errorMessage = errorMessage;
        this.parameter = parameter;
        this.errorTextView = errorTextView;
    }

    public TextView errorTextView;



}
