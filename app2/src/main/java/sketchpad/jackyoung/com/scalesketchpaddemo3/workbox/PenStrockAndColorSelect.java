package sketchpad.jackyoung.com.scalesketchpaddemo3.workbox;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by JackYoung on 2017/8/12.
 */
public class PenStrockAndColorSelect extends RelativeLayout {
    private static final int CANCEL_BUTTON_ID = 0x0020;
    public static final int COLOR_TYPE = 0x0021;
    public static final int STROCK_TYPE = 0x0022;

    public void setCURRENT_TYPE(int CURRENT_TYPE) {
        this.CURRENT_TYPE = CURRENT_TYPE;
    }

    private int CURRENT_TYPE = COLOR_TYPE;

    private int m_penPosition = 0;
    private int m_penPositionTemp = 0;
    private ColorSelectorCallback m_callback = null;

    private OnClickListener m_clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case CANCEL_BUTTON_ID:
                    PenStrockAndColorSelect.this.setVisibility(View.GONE);
                    m_penPosition = m_penPositionTemp;
                    if (null != m_callback) {
                        m_callback.onColorSelectCancel(PenStrockAndColorSelect.this);
                    }
                    break;

                default:
                    Object tag = v.getTag();
                    if (null != tag && tag instanceof Integer) {
                        m_penPosition = ((Integer) tag).intValue();
                    }

                    if (null != m_callback) {
                        m_callback.onColorSelectChange(PenStrockAndColorSelect.this);
                    }
                    break;
            }
        }
    };

    public PenStrockAndColorSelect(Context context) {
        this(context, null);
    }

    public PenStrockAndColorSelect(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PenStrockAndColorSelect(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


        initialize(context);
    }

    public void initialize(Context context) {
        this.setBackgroundColor(Color.WHITE);

        final int width = 90;
        final int height = 90;
        final int margin = 13;
        final int length = PointPath.mPathColors.length;
        int left = 10;

        for (int i = 0; i < length; ++i) {
            int color = PointPath.mPathColors[i];

            ImageView imgBtn = new ImageView(context);
            if (CURRENT_TYPE == STROCK_TYPE) {
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
                Canvas canvas = new Canvas(bitmap);
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(PointPath.mPenStrock[i]);
                canvas.drawLine(0, height, width, 0, paint);
                imgBtn.setImageBitmap(bitmap);
                imgBtn.setBackgroundColor(Color.WHITE);

            } else {
                imgBtn.setBackgroundColor(color);

            }


            imgBtn.setOnClickListener(m_clickListener);
            imgBtn.setTag(i);

            LayoutParams params = new LayoutParams(width, height);
            params.setMargins(left, 30, 0, 0);
            params.addRule(Gravity.CENTER_VERTICAL);
            left += (margin + width);

            this.addView(imgBtn, params);
        }


        // Cancel button.
        Button btnCancel = new Button(context);
        btnCancel.setText("cancel");
        btnCancel.setId(CANCEL_BUTTON_ID);
        btnCancel.setTextSize(8);
        btnCancel.setOnClickListener(m_clickListener);
        LayoutParams btnCancelparams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        btnCancelparams.addRule(Gravity.CENTER);
        left += 10;
        btnCancelparams.setMargins(left, 30, 0, 0);
        this.addView(btnCancel, btnCancelparams);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void setCallback(ColorSelectorCallback callback) {
        m_callback = callback;
    }

    public int getPenPosition() {
        return m_penPosition;
    }

    public int getCURRENT_TYPE() {
        return CURRENT_TYPE;
    }

    public interface ColorSelectorCallback {
        public void onColorSelectCancel(PenStrockAndColorSelect sender);

        public void onColorSelectChange(PenStrockAndColorSelect sender);
    }


    public void setStrockORColor(int type) throws Exception {
        try {
            CURRENT_TYPE = type;
            postInvalidateDelayed(200);
        } catch (Exception e) {
            throw new Exception("need COLOR_TYPE or STROCK_TYPE");
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initialize(getContext());
    }
}
