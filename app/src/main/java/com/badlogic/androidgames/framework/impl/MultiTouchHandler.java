/**
 * Very similar to B.A.G.
 * Minor changes by Marco Faella, marfaella@gmail.com
 */

package com.badlogic.androidgames.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Pool;

public class MultiTouchHandler implements TouchHandler {

    private static final int MAX_POOL_SIZE = 128;

    private final boolean[] isTouching = new boolean[20];
    private final int[] touchX = new int[20];
    private final int[] touchY = new int[20];
    private final Pool<TouchEvent> touchEventPool;
    private ArrayList<TouchEvent> touchEvents = new ArrayList<>();
    private ArrayList<TouchEvent> touchEventsBuffer = new ArrayList<>();
    private final float scaleX, scaleY;

    public MultiTouchHandler(View view, float scaleX, float scaleY) {
        touchEventPool = new Pool<>(TouchEvent::new, MAX_POOL_SIZE);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public synchronized boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        TouchEvent touchEvent;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                touchEvent = touchEventPool.get();
                touchEvent.type = TouchEvent.TOUCH_DOWN;
                touchEvent.pointer = pointerId;
                touchEvent.x = touchX[pointerId] = (int) (event.getX(pointerIndex) * scaleX);
                touchEvent.y = touchY[pointerId] = (int) (event.getY(pointerIndex) * scaleY);
                isTouching[pointerId] = true;
                touchEventsBuffer.add(touchEvent);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                touchEvent = touchEventPool.get();
                touchEvent.type = TouchEvent.TOUCH_UP;
                touchEvent.pointer = pointerId;
                touchEvent.x = touchX[pointerId] = (int) (event.getX(pointerIndex) * scaleX);
                touchEvent.y = touchY[pointerId] = (int) (event.getY(pointerIndex) * scaleY);
                isTouching[pointerId] = false;
                touchEventsBuffer.add(touchEvent);
                break;

            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    pointerIndex = i;
                    pointerId = event.getPointerId(pointerIndex);

                    touchEvent = touchEventPool.get();
                    touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[pointerId] = (int) (event.getX(pointerIndex) * scaleX);
                    touchEvent.y = touchY[pointerId] = (int) (event.getY(pointerIndex) * scaleY);
                    touchEventsBuffer.add(touchEvent);
                }
                break;
        }

        return true;
    }

    @Override
    public synchronized boolean isTouchDown(int pointer) {
        if (pointer < 0 || pointer >= 20)
            return false;
        else
            return isTouching[pointer];
    }

    @Override
    public synchronized int getTouchX(int pointer) {
        if (pointer < 0 || pointer >= 20)
            return 0;
        else
            return touchX[pointer];
    }

    @Override
    public synchronized int getTouchY(int pointer) {
        if (pointer < 0 || pointer >= 20)
            return 0;
        else
            return touchY[pointer];
    }

    @Override
    public synchronized List<TouchEvent> getTouchEvents() {
        // empty the old list and return the events to the pool
        for (TouchEvent event : touchEvents)
            touchEventPool.free(event);

        touchEvents.clear();

        ArrayList<TouchEvent> temp = touchEvents;
        touchEvents = touchEventsBuffer;
        touchEventsBuffer = temp;

        return touchEvents;
    }

}
