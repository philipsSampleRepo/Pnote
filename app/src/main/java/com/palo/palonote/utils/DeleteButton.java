package com.palo.palonote.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class DeleteButton {
    private int imageResId, color, pos;
    private RectF clickRegion;
    private CustomButtonListener listener;
    private Context context;
    private Resources resources;

    public DeleteButton(Context context, int imageResId, int color, CustomButtonListener listener) {
        this.context = context;
        this.imageResId = imageResId;
        this.color = color;
        this.listener = listener;
        this.resources = context.getResources();
    }

    public boolean onClick(float x, float y) {
        if (clickRegion != null && clickRegion.contains(x, y)) {
            listener.onClick(pos);
            return true;
        }
        return false;
    }

    public void onDraw(Canvas c, RectF rectF, int pos) {
        Paint p = new Paint();
        p.setColor(color);
        c.drawRect(rectF, p);
        p.setColor(Color.WHITE);

        Rect r = new Rect();
        float cHeight = rectF.height();
        float cWidth = rectF.width();
        p.setTextAlign(Paint.Align.LEFT);

        Drawable d = ContextCompat.getDrawable(context, imageResId);
        Bitmap bitmap = drawableToBitmap(d);

        float bw = bitmap.getWidth() / 2;
        float bh = bitmap.getHeight() / 2;
        c.drawBitmap(bitmap, ((rectF.left + rectF.right) / 2) - bw,
                ((rectF.top + rectF.bottom) / 2 - bh), p);

        clickRegion = rectF;
        this.pos = pos;
    }

    private Bitmap drawableToBitmap(Drawable d) {
        if (d instanceof BitmapDrawable) {
            return Bitmap.createScaledBitmap(((BitmapDrawable) d).getBitmap(),
                    160, 160, true);
        }
        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(),
                d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);

        return bitmap;
    }
}
