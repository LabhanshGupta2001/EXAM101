package com.dollop.exam101.main.validation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dollop.exam101.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Anil on 6/july/2022.
 */

public class Validation {

    public EditText EditTextPointer;
    public String errorMessage;
    @SuppressLint("StaticFieldLeak")
    private static Validation validation;

    /**
     * Singleton Object of the Class to access
     */
    public static Validation getInstance() {
        if (validation == null)
            validation = new Validation();
        return validation;
    }

    /**
     * Enum of the Type of error we have
     */
    public enum Type {
        Email(""),
        Phone(""),
        EmptyString(""),
        Amount(""),
        AadhaarNumber(""),
        PasswordMatch(""),
        PasswordStrong(""),
        PAN(""),
        IFSC(""),
        Empty("");
        public String label ;

        Type(String label) {
            this.label = label;
        }
    }

    /**
     * Check Validation
     * @param context  Page Reference
     * @param errorValidationModels List of the Field On which we have to check the error
     * @return ResultReturn  return the Data of the Validation
     */
    public ResultReturn CheckValidation(Context context, @NonNull List<ValidationModel> errorValidationModels){
        boolean validationCheck=false;
        Type type = null;
        String errorMessage = null;
        String parameter = null;
        TextView errorTextView = null;

        for (ValidationModel validationModel : errorValidationModels){
            type= validationModel.type;
            errorMessage= validationModel.errorMessage;
            parameter= validationModel.Parameter;
            errorTextView= validationModel.errorTextView;
            switch (validationModel.type){
                case Phone :
                    validationCheck=  isValidPhoneNumber(context, validationModel.editText);
                    break;
                case Email :
                    validationCheck= isEmailValid(context, validationModel.editText);
                    break;
                case EmptyString:
                    validationCheck= isEmptyString(context, validationModel.field);
                    break;
                case Amount:
                    validationCheck= isValidAmount(context, validationModel.editText);
                    break;
                case AadhaarNumber:
                    validationCheck= isValidAadhaarNumber(context, validationModel.editText);
                    break;
                case PasswordMatch:
                    validationCheck= isPasswordMatch(context, validationModel.editText, validationModel.editText1);
                    break;
                case PasswordStrong:
                    validationCheck= isPasswordStrong(context, validationModel.editText);
                    break;
                case PAN:
                    validationCheck= isValidPAN(context, validationModel.editText);
                    break;
                case IFSC:
                    validationCheck= isValidIFSC(context, validationModel.editText);
                    break;
                case Empty:
                    validationCheck= isEmpty(context, validationModel.editText);
                    break;
            }
            if(!validationCheck){
                break;
            }
        }
        return new ResultReturn(type,validationCheck,errorMessage,parameter,errorTextView);
    }

    /**
     * Password Strong Validation Method
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private boolean isPasswordStrong(Context context, EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            Pattern p = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
            String s = editText.getText().toString().trim();
            Matcher m = p.matcher(s.trim());
            if (m.matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.passwordStrong);
                return false;
            }

        }
    }

    /**
     * Email All Type Validation
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private boolean isEmailValid(@NotNull Context context, @NotNull EditText editText) {
        //add your own logic
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            EditTextPointer = editText;
            errorMessage = context.getString(R.string.empty_error);
            return false;
        } else {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString().trim()).matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.invalid_email);
                return false;
            }
        }
    }

    /**
     * is String Empty
     *
     * @param context  Page Reference
     * @param string string To Check
     * @return true/false
     */
    private boolean isEmptyString(@NotNull Context context,String string) {
        //add your own logic

        if (string == null || TextUtils.isEmpty(string.trim())) {
            errorMessage = context.getString(R.string.empty_error);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Mobile All Type Validation
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private boolean isValidPhoneNumber(@NotNull Context context, @NotNull EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            if (editText.getText().toString().length() != 10) {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.enter_ten_digits_number);
                return false;
            } else {
                if (android.util.Patterns.PHONE.matcher(editText.getText()).matches()) {
                    return true;
                } else {
                    EditTextPointer = editText;
                    errorMessage = context.getString(R.string.valid_number);
                    return false;
                }
            }
        }
    }

    /**
     * Check Is Empty
     *
     * @param context Page Reference
     * @param arg     Multiple Edit Text To Check
     * @return true/false
     */
    private boolean isEmpty(@NotNull Context context, @NonNull EditText... arg) {
        for (EditText editText : arg) {
            if (editText.getText().toString().trim().length() <= 0) {
                EditTextPointer = editText;
                EditTextPointer.requestFocus();
                errorMessage = context.getString(R.string.empty_error);
                return false;
            }

        }
        return true;
    }

    /**
     * Check Is PasswordMatch
     *
     * @param context      Page Reference
     * @param pass         Edit Text To Check
     * @param confirm_pass Edit Text To Check
     * @return true/false
     */
    private boolean isPasswordMatch(@NotNull Context context, @NotNull EditText pass, @NotNull EditText confirm_pass) {
        if (pass.getText() == null || TextUtils.isEmpty(pass.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = pass;
            return false;
        } else if (confirm_pass.getText() == null || TextUtils.isEmpty(confirm_pass.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = confirm_pass;
            return false;
        } else {
            if (!pass.getText().toString().equals(confirm_pass.getText().toString())) {
                EditTextPointer = confirm_pass;
                errorMessage = context.getString(R.string.password_not_match);
                return false;
            }
            return true;
        }
    }


    /**
     * is Valid Aadhaar Number
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private boolean isValidAmount(@NotNull Context context, @NotNull EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            Pattern p = Pattern.compile("^(\\d*\\.)?\\d+$");
            String s = editText.getText().toString().trim();
            Matcher m = p.matcher(s.trim());
            if (m.matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.amount_valid);
                return false;
            }

        }
    }

    /**
     * is Valid Aadhaar Number
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private boolean isValidAadhaarNumber(@NotNull Context context, @NotNull EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            Pattern p = Pattern.compile("^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$");
            String s = editText.getText().toString().replaceAll("....", "$0 ");
            Matcher m = p.matcher(s.trim());
            if (m.matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.aadhaar_valid);
                return false;
            }

        }
    }


    /**
     * is Valid PAN Number
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private boolean isValidPAN(@NotNull Context context, @NotNull EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            Pattern p = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
            Matcher m = p.matcher(editText.getText().toString());
            if (m.matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.pan_card_valid);
                return false;
            }

        }
    }

    /**
     * is Valid IFSC Code
     *
     * @param context  Page Reference
     * @param editText Edit Text To Check
     * @return true/false
     */
    private boolean isValidIFSC(@NotNull Context context, @NotNull EditText editText) {
        if (editText.getText() == null || TextUtils.isEmpty(editText.getText())) {
            errorMessage = context.getString(R.string.empty_error);
            EditTextPointer = editText;
            return false;
        } else {
            Pattern p = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");
            Matcher m = p.matcher(editText.getText().toString());
            if (m.matches()) {
                return true;
            } else {
                EditTextPointer = editText;
                errorMessage = context.getString(R.string.ifsc_valid);
                return false;
            }

        }
    }

}