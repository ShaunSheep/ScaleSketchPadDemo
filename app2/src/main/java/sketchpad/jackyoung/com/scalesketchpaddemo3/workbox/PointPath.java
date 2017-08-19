package sketchpad.jackyoung.com.scalesketchpaddemo3.workbox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * Created by JackYoung on 2017/8/10.
 */

public class PointPath {

    private  Path mPath=null;
    private  PointF mPrevPoint=null;
    private Paint mPaint=null;

    private static volatile PointPath mPointPath=null;
    private  PathType mCurrentType= PathType.PEN_1;
    private static PorterDuffXfermode sClearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    private float currentWidth;

    public void setCurrentWidth(float currentWidth) {
        this.currentWidth = currentWidth;
    }

    public static enum PathType{
        PEN_1,
        PEN_2,
        PEN_3,
        PEN_4,
        PEN_5,
        PEN_6,
        PEN_7,
        PEN_8,
        ERASER
    }
    public static final int[] mPenStrock =
            {
                    22,
                    25,
                    30,
                    35,
                    45,
                    55,
                    65,
                    80

            };
    public static final float NORMAL_LINE_WIDTH = mPenStrock[0];

    public static final int[] mPathColors =
            {
                    Color.argb(128, 32, 79, 140),
                    Color.argb(128, 48, 115, 170),
                    Color.argb(128, 139, 26, 99),
                    Color.argb(128, 112, 101, 89),
                    Color.argb(128, 40, 36, 37),
                    Color.argb(128, 226, 226, 226),
                    Color.argb(128, 219, 88, 50),
                    Color.argb(128, 129, 184, 69)
            };
     PointPath() {
         mPath=new Path();
         mPaint=new Paint();
    }

    public  synchronized static  PointPath newPointPathInstance(PointF pointF){
        mPointPath=new PointPath();
        mPointPath.mPath.moveTo(pointF.x,pointF.y);
        mPointPath.mPrevPoint=pointF;
        return  mPointPath;
    }

    public void savePointToPath(PointF mCurrentPoint) {
            mPath.quadTo(mPrevPoint.x,mPrevPoint.y,mCurrentPoint.x,mCurrentPoint.y);
            mPrevPoint=mCurrentPoint;
    }


    /**
     * the draw function is belong to PointPath ，is not Canvas
     * @param canvas
     */
    public void disPlayPath(Canvas canvas) {
        if(mPaint==null){
            mPaint=new Paint();
        }

        int color=ensureColor(mCurrentType);

        //because of the xx(maybe Xfermode proterty)，we need reset paint proterties
        if(mCurrentType== PathType.ERASER){
            mPaint.setColor(color);
            mPaint.setXfermode(sClearMode);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setStrokeWidth(currentWidth);

        }else{
            mPaint.setColor(color);
            mPaint.setXfermode(null);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setStrokeWidth(currentWidth);

        }

        canvas.drawPath(mPath,mPaint);
    }

    private int ensureColor(PathType mCurrentType) {
        switch (mCurrentType){
            case  PEN_1:
                return mPathColors[0];
            case  PEN_2:
                return mPathColors[1];
            case  PEN_3:
                return mPathColors[2];
            case  PEN_4:
                return mPathColors[3];
            case  PEN_5:
                return mPathColors[4];
            case  PEN_6:
                return mPathColors[5];
            case  PEN_7:
                return mPathColors[6];
            case PEN_8:
                return mPathColors[7];
            case ERASER:
                return Color.TRANSPARENT;
            default:
                break;
        }
        return -1;
    }

    public void setCurrentPathType(PathType currentPathType) {
        mCurrentType=currentPathType;
    }


}
