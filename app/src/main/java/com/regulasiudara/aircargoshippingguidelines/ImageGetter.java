package com.regulasiudara.aircargoshippingguidelines;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;

public class ImageGetter implements Html.ImageGetter {
    Context mContext;
    public ImageGetter(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public Drawable getDrawable(String source) {
        int id;

        id = mContext.getResources().getIdentifier(source, "drawable", mContext.getPackageName());

        if (id == 0) {
            // the drawable resource wasn't found in our package, maybe it is a stock android drawable?
            id = mContext.getResources().getIdentifier(source, "drawable", "android");
        }

        if (id == 0) {
            // prevent a crash if the resource still can't be found
            return null;
        }
        else {
            Drawable d = mContext.getResources().getDrawable(id);
            d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
            return d;
        }
    }

}