package com.yk.androidassistant.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by jsgyrcb-yk on 2020/07/16 0016.
 */
@SuppressLint("AppCompatCustomView")
public class ColorTextView extends TextView {

    public ColorTextView(Context context) {
        super(context);
    }

    public ColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableString spannableString = new SpannableString(text);
        if (text.toString().contains("*")) {
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
            spannableString.setSpan(foregroundColorSpan,
                    text.length() - 1, text.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        super.setText(spannableString, type);
    }
}
