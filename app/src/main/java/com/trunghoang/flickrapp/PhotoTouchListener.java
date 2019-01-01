package com.trunghoang.flickrapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class PhotoTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    interface ItemTouchListener {
        void itemSingleTouch(int position);
    }

    private GestureDetector gestureDetector;

    PhotoTouchListener(Context context, final RecyclerView rv, final ItemTouchListener itemTouchListener) {
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if (child != null && itemTouchListener != null) {
                    itemTouchListener.itemSingleTouch(rv.getChildAdapterPosition(child));
                }

                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (gestureDetector != null) {
            return gestureDetector.onTouchEvent(e);
        }
        return false;
    }
}
