package com.cloth.clothes.storelist.weiget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import java.util.List;

public class SingleChoiceDialogFragment extends DialogFragment {

    private String title;

    private String[] items;

    private DialogInterface.OnClickListener onClickListener;

    private DialogInterface.OnClickListener positiveCallback;

    public void show(String title, List<String> list, DialogInterface.OnClickListener onClickListener,
                     DialogInterface.OnClickListener positiveCallback, FragmentManager fragmentManager) {
        this.title = title;
        items = new String [list.size()];
        list.toArray(items);
        this.onClickListener = onClickListener;
        this.positiveCallback = positiveCallback;
        show(fragmentManager, "SingleChoiceDialogFragment");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setSingleChoiceItems(items, 0, onClickListener)
                .setPositiveButton("确定", positiveCallback);
        return builder.create();
    }


}