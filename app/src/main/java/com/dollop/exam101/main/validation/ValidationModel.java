package com.dollop.exam101.main.validation;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Anil on 6/july/2022.
 */
public class ValidationModel {
    public Validation.Type type;
    public String field;
    public EditText editText;
    public EditText editText1;
    public String errorMessage;
    public TextView errorTextView;
    public String Parameter;

    public ValidationModel() {

    }

    public ValidationModel(Validation.Type type, EditText editText, EditText editText1) {
        this.type = type;
        this.editText = editText;
        this.editText1 = editText1;
    }

    public ValidationModel(Validation.Type type, String field, String errorMessage) {
        this.type = type;
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public ValidationModel(Validation.Type type, EditText editText) {
        this.type = type;
        this.editText = editText;
    }


    public ValidationModel(Validation.Type type, String field, EditText editText, EditText editText1, String errorMessage, TextView errorTextView, String Parameter) {
        this.type = type;
        this.field = field;
        this.editText = editText;
        this.editText1 = editText1;
        this.errorMessage = errorMessage;
        this.errorTextView = errorTextView;
        this.Parameter = Parameter;
    }

    public ValidationModel(Validation.Type type, EditText editText, EditText editText1, TextView errorTextView) {
        this.type = type;
        this.editText = editText;
        this.editText1 = editText1;
        this.errorTextView = errorTextView;
    }

    public ValidationModel(Validation.Type type, String field, String errorMessage, TextView errorTextView) {
        this.type = type;
        this.field = field;
        this.errorMessage = errorMessage;
        this.errorTextView = errorTextView;
    }

    public ValidationModel(Validation.Type type, EditText editText, TextView errorTextView) {
        this.type = type;
        this.editText = editText;
        this.errorTextView = errorTextView;
    }

    public ValidationModel(Validation.Type type, EditText editText, EditText editText1, TextView errorTextView, String Parameter) {
        this.type = type;
        this.editText = editText;
        this.editText1 = editText1;
        this.errorTextView = errorTextView;
        this.Parameter = Parameter;
    }

    public ValidationModel(Validation.Type type, String field, String errorMessage, TextView errorTextView, String Parameter) {
        this.type = type;
        this.field = field;
        this.errorMessage = errorMessage;
        this.errorTextView = errorTextView;
        this.Parameter = Parameter;
    }

    public ValidationModel(Validation.Type type, EditText editText, TextView errorTextView, String Parameter) {
        this.type = type;
        this.editText = editText;
        this.errorTextView = errorTextView;
        this.Parameter = Parameter;
    }
}
