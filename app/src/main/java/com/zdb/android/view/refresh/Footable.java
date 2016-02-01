package com.zdb.android.view.refresh;

import android.graphics.Canvas;

public interface Footable {
    boolean draw(Canvas canvas, int left, int top, int right, int bottom);

    int getHeight();
}
