package com.cozydays.assets;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/** Proportional cover, keeping the normalized source focal point near the center. */
public final class FocalCropImageView extends ImageView {
    private float focalX = 0.62f;
    private float focalY = 0.45f;

    public FocalCropImageView(Context context) { this(context, null); }
    public FocalCropImageView(Context context, AttributeSet attrs) { this(context, attrs, 0); }
    public FocalCropImageView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        setScaleType(ScaleType.MATRIX);
    }

    public void setFocalPoint(float x, float y) {
        if (Float.isNaN(x) || Float.isNaN(y)) return;
        focalX = Math.max(0f, Math.min(1f, x));
        focalY = Math.max(0f, Math.min(1f, y));
        updateCrop();
    }

    @Override public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        updateCrop();
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateCrop();
    }

    private void updateCrop() {
        Drawable drawable = getDrawable();
        int w = getWidth() - getPaddingLeft() - getPaddingRight();
        int h = getHeight() - getPaddingTop() - getPaddingBottom();
        if (drawable == null || w <= 0 || h <= 0) return;
        int iw = drawable.getIntrinsicWidth(), ih = drawable.getIntrinsicHeight();
        if (iw <= 0 || ih <= 0) return;
        float scale = Math.max((float) w / iw, (float) h / ih);
        float dx = Math.max(w - iw * scale, Math.min(0f, w / 2f - focalX * iw * scale));
        float dy = Math.max(h - ih * scale, Math.min(0f, h / 2f - focalY * ih * scale));
        Matrix transform = new Matrix();
        transform.setScale(scale, scale);
        transform.postTranslate(dx, dy);
        setImageMatrix(transform);
    }
}
