package sketch.jackyoung.com.scalesketchpaddemo.workbox;

import android.content.Context;
import android.graphics.Canvas;
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

    public ScaleSketchView(Context context) {
        super(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }


}
