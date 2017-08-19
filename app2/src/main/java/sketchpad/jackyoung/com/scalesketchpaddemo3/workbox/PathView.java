package sketchpad.jackyoung.com.scalesketchpaddemo3.workbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    private  float mPointWidth;
    private  Canvas tempCanvas=null;
    private Bitmap mBitmap=null;
    private Bitmap mBgBitmap=null;
    private Paint mPaint=null;
    private  float dstImageWidth,dstImageHeight,bitmapFactor,dx,dy;
    private float mScale = 1;
    private PointF mOffset = new PointF(0, 0);
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

            if(mBgBitmap!=null) {
                //contral bitmap border in canvas
                setImageBorder();

                Matrix matrix=new Matrix();
                //set image inner pathview
                matrix.postScale(bitmapFactor,bitmapFactor);
                //set iamge  inner  center
                matrix.postTranslate(dx,dy);
                canvas.drawBitmap(mBgBitmap, matrix, mPaint);
            }

            canvas.drawBitmap(mBitmap,0,0,mPaint);

    }

    private void setImageBorder() {
        float bitmapWidth=(float)mBgBitmap.getWidth();
        float bitmapHeight=(float)mBgBitmap.getHeight();

        if (bitmapWidth > bitmapHeight&&bitmapWidth==bitmapWidth) {
            dstImageWidth=getWidth()-2;
            dstImageHeight=bitmapHeight/bitmapWidth*dstImageWidth;
            bitmapFactor=dstImageHeight/dstImageWidth;
            dy=(getHeight()-(bitmapHeight*bitmapFactor))/2;
            dx=(getWidth()-(bitmapWidth*bitmapFactor))/2;
        }else{
            dstImageHeight=getHeight()-2;
            dstImageWidth=bitmapWidth/bitmapHeight*dstImageHeight;
            bitmapFactor=dstImageWidth/dstImageHeight;
            dy=(getHeight()-(bitmapHeight*bitmapFactor))/2;
            dx=(getWidth()-(bitmapWidth*bitmapFactor))/2;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        mCurrentPoint=new PointF((x-mOffset.x)/mScale,(y-mOffset.y)/mScale);
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
                case 0:
                    mCurrentPathType= PointPath.PathType.PEN_1;
                    break;
                case 1:
                    mCurrentPathType= PointPath.PathType.PEN_2;
                    break;
                case 2:
                    mCurrentPathType= PointPath.PathType.PEN_3;
                    break;
                case 3:
                    mCurrentPathType= PointPath.PathType.PEN_4;
                    break;
                case 4:
                    mCurrentPathType= PointPath.PathType.PEN_5;
                    break;
                case 5:
                    mCurrentPathType= PointPath.PathType.PEN_6;
                    break;
                case 6:
                    mCurrentPathType= PointPath.PathType.PEN_7;
                    break;
                case 7:
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


    public void addBackgroudImage(Bitmap resultBimtap) {
        mBgBitmap=resultBimtap;
    }

    public void setScaleAndOffset(float scaleX, float mMatrixValu, float mMatrixValu1) {
        mScale = scaleX;
        mPointWidth = PointPath.NORMAL_LINE_WIDTH/ mScale;
        mOffset.x = mMatrixValu;
        mOffset.y = mMatrixValu1;
    }
}
