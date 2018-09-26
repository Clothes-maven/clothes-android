package com.cloth.clothes.clothesdetail.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cloth.clothes.R;

public class InputDialogBuilder extends AlertDialog.Builder {
    public InputDialogBuilder(Context context) {
        super(context);
        init(context);
    }

    private TextView mTitleTv, mCancelTv, mPositiveTv,mMessageTv;
    private EditText mNumberEd, mCostEd;
    private AlertDialog mAlertDialog;
    private Listener mListener;

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_input, null, false);
        setView(view);
        mTitleTv = view.findViewById(R.id.txt_dialog_title);
        mNumberEd = view.findViewById(R.id.number_dialog_tip);
        mCostEd = view.findViewById(R.id.txt_dialog_tip);
        mCancelTv = view.findViewById(R.id.btn_selectNegative);
        mPositiveTv = view.findViewById(R.id.btn_selectPositive);
        mMessageTv = view.findViewById(R.id.message_dialog_tip);
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        mPositiveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.numberCost(mNumberEd.getText().toString().trim(), mCostEd.getText().toString().trim());
                }
                mAlertDialog.dismiss();
            }
        });
    }

    public InputDialogBuilder setTitle(String str) {
        mTitleTv.setText(str);
        return this;
    }

    public InputDialogBuilder setMessage(String message) {
        mMessageTv.setText(message);
        return this;
    }

    public InputDialogBuilder setListener(Listener onClickListener) {
        mListener = onClickListener;
        return this;
    }

    public void showDialog() {
        mAlertDialog = create();
        mAlertDialog.show();
    }

    public interface Listener {
        void numberCost(String number, String cost);
    }
}
