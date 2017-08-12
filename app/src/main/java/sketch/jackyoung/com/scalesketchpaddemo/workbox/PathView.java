package sketch.jackyoung.com.scalesketchpaddemo.workbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by JackYoung on 2017/8/10.
 */

public class PathView extends View {

    private  PointPath mCurrentPath=null;
    private PointF mCurrentPoint=null;
    private ArrayList<PointPath> mUserAllPaths;
    private  PointPath.PathType mCurrentPathType= PointPath.PathType.PEN_1;
    private  int mPointWidth;
    private  Canvas tempCanvas=null;
    private Bitmap mBitmap=null;
    private Paint mPaint=null;

    public PathView(Context context) {
        super(context);
        mUserAllPaths=new ArrayList<>();
        setBackgroundColor(Color.TRANSPARENT);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        tempCanvas = new Canvas(mBitmap);
        mPaint=new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //PorterDuff is very important
        tempCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            if(mUserAllPaths.size()>=0){
                for (PointPath pointPath : mUserAllPaths) {
                    pointPath.disPlayPath(tempCanvas);
                }
            }


            if(mCurrentPath!=null){
                mCurrentPath.disPlayPath(tempCanvas);
            }

            canvas.drawBitmap(mBitmap,0,0,mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        mCurrentPoint=new PointF(x,y);
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                Log.i("action test","finger down");
                mCurrentPath=PointPath.newPointPathInstance(mCurrentPoint);
                mCurrentPath.setCurrentPathType(mCurrentPathType);
                mCurrentPath.setCurrentWidth(mPointWidth);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("action test","finger move");
                if (mCurrentPath == null ) break;

                mCurrentPath.savePointToPath(mCurrentPoint);
                postInvalidateDelayed(40);
                break;
            case  MotionEvent.ACTION_UP:
                Log.i("action test","finger up");
                if(mCurrentPath!=null) {
                    mCurrentPath.savePointToPath(mCurrentPoint);
                    mUserAllPaths.add(mCurrentPath);
                }
                mCurrentPath = null;//this is important

                invalidate();
                break;
        }
        return true;

    }

    public void useEraser() {
        mCurrentPathType= PointPath.PathType.ERASER;
    }


    public void setPaintColor(int paintColor) {
        switch (paintColor){
                case 1:
                    mCurrentPathType= PointPath.PathType.PEN_1;
                    break;
                case 2:
                    mCurrentPathType= PointPath.PathType.PEN_2;
                    break;
                case 3:
                    mCurrentPathType= PointPath.PathType.PEN_3;
                    break;
                case 4:
                    mCurrentPathType= PointPath.PathType.PEN_4;
                    break;
                case 5:
                    mCurrentPathType= PointPath.PathType.PEN_5;
                    break;
                case 6:
                    mCurrentPathType= PointPath.PathType.PEN_6;
                    break;
                case 7:
                    mCurrentPathType= PointPath.PathType.PEN_7;
                    break;
                case 8:
                    mCurrentPathType= PointPath.PathType.PEN_8;
                    break;
                default:
                    mCurrentPathType= PointPath.PathType.PEN_1;
                    break;
        }
    }

    public void setPaintWidth(int position) {
        mPointWidth =  PointPath.mPenStrock[position];
    }

    public void undoPath() {
        if(mUserAllPaths.size()>0) {
            mUserAllPaths.remove(mUserAllPaths.size() - 1);
        }
        //why not refresh
        invalidate();

    }
}
