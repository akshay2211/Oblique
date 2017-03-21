package ak.sh.ay.oblique;

/**
 * Created by akshay on 21/3/17.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.widget.ImageView;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class ObliqueView extends ImageView {
    private final Paint colorPaint = new Paint(ANTI_ALIAS_FLAG);
    private Paint bitmapPaint = new Paint(ANTI_ALIAS_FLAG);
    private Bitmap bitmap = null;
    private BitmapShader bitmapShader;
    private Matrix shaderMatrix = new Matrix();
    private int baseColor = Color.TRANSPARENT;
    private float startAngle, endAngle, width, height;
    private Config config = new Config();


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
        config = new Config();
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
        setupBitmap(this, width, height);
        invalidate();
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
        this.baseColor = baseColor;
        colorPaint.setColor(baseColor);
        colorPaint.setAlpha(255);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //  super.onDraw(canvas);
        Path path = config.getPath(height, width, startAngle, endAngle);
        if (bitmap != null) {
            canvas.drawPath(path, bitmapPaint);
        }
        if (baseColor != Color.TRANSPARENT) {
            canvas.drawPath(path, colorPaint);
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        setupBitmap(this, width, height);
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setupBitmap(this, width, height);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setupBitmap(this, width, height);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        setupBitmap(this, width, height);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == ScaleType.CENTER_CROP || scaleType == ScaleType.FIT_XY)
            super.setScaleType(scaleType);
        else
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    public void setupBitmap(ImageView imageView, float width, float height) {
        Drawable drawable = imageView.getDrawable();
        if (drawable == null) {
            return;
        }
        try {

            bitmap = (drawable instanceof BitmapDrawable) ?
                    ((BitmapDrawable) drawable).getBitmap()
                    :
                    Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap == null) {
            imageView.invalidate();
            return;
        }
        bitmapPaint = new Paint(ANTI_ALIAS_FLAG);
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapPaint.setShader(bitmapShader);
        if (imageView.getScaleType() != ImageView.ScaleType.CENTER_CROP && imageView.getScaleType() != ImageView.ScaleType.FIT_XY)
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setUpScaleType(imageView, width, height);
        imageView.invalidate();
    }


    private void setUpScaleType(ImageView iv, float width, float height) {
        float scaleX = 1, scaleY = 1, dx = 0, dy = 0;
        if (bitmap == null || shaderMatrix == null)
            return;
        shaderMatrix.set(null);
        if (iv.getScaleType() == ImageView.ScaleType.CENTER_CROP) {
            if (width != bitmap.getWidth()) {
                scaleX = width / bitmap.getWidth();
            }
            if (scaleX * bitmap.getHeight() < height) {
                scaleX = height / bitmap.getHeight();
            }
            dy = (height - bitmap.getHeight() * scaleX) * 0.5f;
            dx = (width - bitmap.getWidth() * scaleX) * 0.5f;
            shaderMatrix.setScale(scaleX, scaleX);
        } else {
            scaleX = width / bitmap.getWidth();
            scaleY = height / bitmap.getHeight();
            dy = (height - bitmap.getHeight() * scaleY) * 0.5f;
            dx = (width - bitmap.getWidth() * scaleX) * 0.5f;
            shaderMatrix.setScale(scaleX, scaleY);
        }
        shaderMatrix.postTranslate(dx + 0.5f, dy + 0.5f);
        bitmapShader.setLocalMatrix(shaderMatrix);
    }


}