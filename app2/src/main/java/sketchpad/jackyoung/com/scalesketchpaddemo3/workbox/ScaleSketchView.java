package sketchpad.jackyoung.com.scalesketchpaddemo3.workbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


/**
 * Created by JackYoung on 2017/8/10.
 *
 * this view  can  double finger scale,double finger translution,and single finger event
 * will give to pathview  to draw path
 *
 */

public class ScaleSketchView extends RelativeLayout {


    private static final float MAX_SCALE = 10.0F;
    private static final float MIN_SCALE = 1.0f;
    private float mBorderX, mBorderY;
    private float[] mMatrixValus = new float[9];
    private PathView pathView;
    private boolean isTranslate;

    private float mOldDistance;
    private PointF mOldPointer;

    public ScaleSketchView(Context context) {
        super(context);
        LayoutParams pathViewParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        pathView=new PathView(getContext());
        addView(pathView,pathViewParams);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()&MotionEvent.ACTION_MASK){
            case  MotionEvent.ACTION_DOWN:
                return pathView.onTouchEvent(ev);
            case MotionEvent.ACTION_POINTER_DOWN:
                isTranslate=true;
                mOldDistance = TouchEventUtil.spacingOfTwoFinger(ev);
                mOldPointer = TouchEventUtil.middleOfTwoFinger(ev);
                Log.i("action test","scale view action_pointer_down");
                break;
            case  MotionEvent.ACTION_MOVE:
                if(!isTranslate)return  pathView.onTouchEvent(ev);
                if(ev.getPointerCount()==2){
                    float newDistance = TouchEventUtil.spacingOfTwoFinger(ev);
                    float scaleFactor = newDistance / mOldDistance;
                    scaleFactor = checkingScale(pathView.getScaleX(), scaleFactor);
                    pathView.setScaleX(pathView.getScaleX() * scaleFactor);
                    pathView.setScaleY(pathView.getScaleY() * scaleFactor);
                    mOldDistance = newDistance;

                    PointF newPointer = TouchEventUtil.middleOfTwoFinger(ev);
                    pathView.setX(pathView.getX() + newPointer.x - mOldPointer.x);
                    pathView.setY(pathView.getY() + newPointer.y - mOldPointer.y);
                    mOldPointer = newPointer;
                    checkingBorder();
                    Log.i("action test","scale view action_move");

                }
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_UP:
                if (!isTranslate) return pathView.onTouchEvent(ev);
                pathView.getMatrix().getValues(mMatrixValus);
                pathView.setScaleAndOffset(pathView.getScaleX(), mMatrixValus[2], mMatrixValus[5]);
                isTranslate = false;
                Log.i("action test","scale view action_up");
                break;
        }

        return true;

    }
    private float checkingScale(float scale, float scaleFactor) {
        if ((scale <= MAX_SCALE && scaleFactor > 1.0) || (scale >= MIN_SCALE && scaleFactor < 1.0)) {
            if (scale * scaleFactor < MIN_SCALE) {
                scaleFactor = MIN_SCALE / scale;
            }

            if (scale * scaleFactor > MAX_SCALE) {
                scaleFactor = MAX_SCALE / scale;
            }

        }

        return scaleFactor;
    }

    private void checkingBorder() {
        PointF offset = offsetBorder();
        pathView.setX(pathView.getX() + offset.x);
        pathView.setY(pathView.getY() + offset.y);
        if (pathView.getScaleX() == 1) {
            pathView.setX(0);
            pathView.setY(0);
        }
    }

    private PointF offsetBorder() {
        PointF offset = new PointF(0, 0);
        if (pathView.getScaleX() > 1) {
            pathView.getMatrix().getValues(mMatrixValus);
            if (mMatrixValus[2] > -(mBorderX * (pathView.getScaleX() - 1))) {
                offset.x = -(mMatrixValus[2] + mBorderX * (pathView.getScaleX() - 1));
            }

            if (mMatrixValus[2] + pathView.getWidth() * pathView.getScaleX() - mBorderX * (pathView.getScaleX() - 1) < getWidth()) {
                offset.x = getWidth() - (mMatrixValus[2] + pathView.getWidth() * pathView.getScaleX() - mBorderX * (pathView.getScaleX() - 1));
            }

            if (mMatrixValus[5] > -(mBorderY * (pathView.getScaleY() - 1))) {
                System.out.println("offsetY:" + mMatrixValus[5] + " borderY:" + mBorderY + " scale:" + getScaleY() + " scaleOffset:" + mBorderY * (getScaleY() - 1));
                offset.y = -(mMatrixValus[5] + mBorderY * (pathView.getScaleY() - 1));
            }

            if (mMatrixValus[5] + pathView.getHeight() * pathView.getScaleY() - mBorderY * (pathView.getScaleY() - 1) < getHeight()) {
                offset.y = getHeight() - (mMatrixValus[5] + pathView.getHeight() * pathView.getScaleY() - mBorderY * (pathView.getScaleY() - 1));
            }
        }

        return offset;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }


    public void useEraser() {
        pathView.useEraser();
    }

    public void setPaintWidth(int position) {
        pathView.setPaintWidth(position);
    }

    public void undoPath() {
        pathView.undoPath();
    }

    public void setPaintColor(int position) {
        pathView.setPaintColor(position);
    }

    public void addBackgroudImage(Bitmap resultBimtap) {
        pathView.addBackgroudImage(resultBimtap);
    }
}
