package com.cloth.kernel.service;

import android.app.Dialog;
import android.content.Context;

import com.kongzue.dialog.v2.DialogSettings;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.TipDialog;
import com.kongzue.dialog.v2.WaitDialog;

public class DialogWrapper {
    static {
        DialogSettings.type = DialogSettings.TYPE_KONGZUE;
        DialogSettings.dialog_theme = DialogSettings.THEME_LIGHT;
        DialogSettings.use_blur = true;                 //设置是否启用模糊
    }

    public static void inputDialog(Context context, String title, String desc,  final InputDialogOkButtonClickListener clickListener) {
        InputDialog.show(context, title, desc, new com.kongzue.dialog.listener.InputDialogOkButtonClickListener() {
            @Override
            public void onClick(Dialog dialog, String inputText) {
                clickListener.onClick(dialog, inputText);
            }
        });
    }

    public interface InputDialogOkButtonClickListener {
        void onClick(Dialog dialog, String inputText);
    }


    public static void tipSuccessDialog(Context context, String msg) {
        TipDialog.show(context, msg, TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_FINISH);
    }
    public static void tipSuccessDialog(Context context) {
        TipDialog.show(context, "完成", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_FINISH);
    }

    public static void tipErrorDialog(Context context, String msg) {
        TipDialog.show(context, msg, TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_ERROR);
    }

    public static void tipWarning(Context context, String msg) {
        TipDialog.show(context, msg, TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
    }

    public static void waitDialog(Context context) {
        WaitDialog.show(context,"加载中...");
    }

    public static void dismissWaitDialog() {
        WaitDialog.dismiss();
    }

}
