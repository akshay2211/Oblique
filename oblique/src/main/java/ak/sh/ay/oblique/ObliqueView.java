package ak.sh.ay.oblique;

/**
 * Created by akshay on 21/3/17.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.widget.ImageView;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class ObliqueView extends ImageView {

    private static int baseColor = Color.TRANSPARENT;
    private final Paint colorPaint = new Paint(ANTI_ALIAS_FLAG);
    private float startAngle, endAngle, width, height;


    public ObliqueView(Context context) {
        super(context);
        init(context, null);
    }

    public ObliqueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ObliqueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    //initialise
    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ObliqueView, 0, 0);
        if (attrs != null) {
            startAngle = a.getFloat(R.styleable.ObliqueView_starting_slant_angle, 90f);
            endAngle = a.getFloat(R.styleable.ObliqueView_ending_slant_angle, 90f);
            baseColor = a.getColor(R.styleable.ObliqueView_basecolor, Color.TRANSPARENT);
            a.recycle();
            colorPaint.setStyle(Paint.Style.FILL);
            colorPaint.setColor(baseColor);
            colorPaint.setAlpha(255);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        Config.setupBitmap(this);
    }

    public void setStartAngle(@FloatRange(from = 0, to = 180) float startAngle) {
        this.startAngle = startAngle;
        invalidate();
    }

    public void setEndAngle(@FloatRange(from = 0, to = 180) float endAngle) {
        this.endAngle = endAngle;
        invalidate();
    }

    public void setBaseColor(int baseColor) {
        ObliqueView.baseColor = baseColor;
        colorPaint.setColor(baseColor);
        colorPaint.setAlpha(255);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = Config.getPath(height, width, startAngle, endAngle);
        if (Config.getBitmap() != null) {
            canvas.drawPath(path, Config.getBitmapPaint());
        }
        if (baseColor != Color.TRANSPARENT) {
            canvas.drawPath(path, colorPaint);
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        Config.setupBitmap(this);
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        Config.setupBitmap(this);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        Config.setupBitmap(this);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        Config.setupBitmap(this);
    }


}