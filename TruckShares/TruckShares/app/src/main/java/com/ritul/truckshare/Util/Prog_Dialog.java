package com.ritul.truckshare.Util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.ritul.truckshare.R;

/**
 * Created by gopal on 12/24/2015.
 */
public class Prog_Dialog extends Dialog {

    CircularProgressView circularProgressView;

    public Prog_Dialog(Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progressbar);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
    }
}