package com.infowithvijay.triviaquizapp2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TakeMoneyDialog {

    private Context mContext;
    private Dialog takeMoneyDialog;

    public TakeMoneyDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void takeMoneyDialog(String money){

        takeMoneyDialog = new Dialog(mContext);
        takeMoneyDialog.setContentView(R.layout.takemoney_dialog);

        final Button btTakeMoney = takeMoneyDialog.findViewById(R.id.bt_take_money);
        final TextView txtTakeMoney = takeMoneyDialog.findViewById(R.id.txtTakeMoney);

        txtTakeMoney.setText(money);

        btTakeMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                takeMoneyDialog.dismiss();
                Intent intent = new Intent(mContext, PlayScreen.class);
                mContext.startActivity(intent);
            }
        });

        takeMoneyDialog.show();
        takeMoneyDialog.setCancelable(false);
        takeMoneyDialog.setCanceledOnTouchOutside(false);
        takeMoneyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }
}
