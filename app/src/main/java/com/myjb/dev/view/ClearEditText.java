package com.myjb.dev.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.myjb.dev.mygaragesale.R;

public class ClearEditText extends AppCompatEditText implements TextWatcher, View.OnTouchListener, View.OnFocusChangeListener {

    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    public interface OnTextChangeListener {
        void onTextChange(int length);
    }

    Drawable clearDrawable = null;
    OnTextChangeListener listener = null;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        clearDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(getContext(), R.drawable.clear));
        DrawableCompat.setTintList(clearDrawable, getHintTextColors());
        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());

        setClearIconVisible(false);

        addTextChangedListener(this);
        setOnFocusChangeListener(this);
        setOnTouchListener(this);
    }

    private void setClearIconVisible(boolean visible) {
        if (clearDrawable == null)
            return;

        clearDrawable.setVisible(visible, false);
        setCompoundDrawables(null, null, visible ? clearDrawable : null, null);
    }

    public void setOnTextChangeListener(OnTextChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        setClearIconVisible(isFocused() && s.length() > 0);

        if (listener != null)
            listener.onTextChange(s.length());
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setClearIconVisible(hasFocus && getText().toString().length() > 0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[DRAWABLE_RIGHT] == null)
                return false;

            if (event.getRawX() >= (getRight() - getPaddingRight() - getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                setText(null);
                setError(null);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDetachedFromWindow() {
        listener = null;
        clearDrawable = null;
        super.onDetachedFromWindow();
    }
}