package ak.sh.ay.oblique;

/**
 * Created by akshay on 21/3/17.
 */


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class ObliqueView extends android.support.v7.widget.AppCompatImageView {

    //Variables
    private Path shadowpath, path;
    private Rect rect;
    private float width, height;
    private Config config = null;
    private Bitmap bitmap = null;
    private Paint paint = new Paint(ANTI_ALIAS_FLAG);
    private BitmapShader bitmapShader;
    private PorterDuffXfermode pdMode;

    //Constructors
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

    //Initialisation method
    private void init(Context context, AttributeSet attrs) {
        config = new Config(context, attrs);
        config.setElevation(ViewCompat.getElevation(this));
        pdMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);

    }

    //Getter and Setter methods
    public float getAngle() {
        return config.getAngle();
    }

    public void setAngle(@FloatRange(from = 0, to = 360) float angle) {
        config.setAngle(angle);
        invalidate();
    }


    public int getStartColor() {
        return config.getStartColor();
    }

    public void setStartColor(int startColor) {
        config.setStartColor(startColor);
        invalidate();
    }


    public int getEndColor() {
        return config.getEndColor();
    }

    public void setEndColor(int endColor) {
        config.setEndColor(endColor);
        invalidate();
    }


    public float getStartAngle() {
        return config.getStartAngle();
    }

    public void setStartAngle(@FloatRange(from = 0, to = 180) float startAngle) {
        config.setStartAngle(startAngle);
        invalidate();
    }


    public float getEndAngle() {
        return config.getEndAngle();
    }

    public void setEndAngle(@FloatRange(from = 0, to = 180) float endAngle) {
        config.setEndAngle(endAngle);
        invalidate();
    }


    public float getCornerRadius() {
        return config.getRadius();
    }

    public void setCornerRadius(@FloatRange(from = 0, to = 60) float radius) {
        config.setRadius(radius <= 60 ? radius : 60);
        invalidate();
    }


    public int getBaseColor() {
        return config.getBaseColor();
    }

    public void setBaseColor(int baseColor) {
        config.setBaseColor(baseColor);
        invalidate();
    }


    //Private functionality methods
    private void setupBitmap(ImageView imageView, float width, float height) {
        Drawable drawable = imageView.getDrawable();
        if (drawable == null) {
            return;
        }
        try {
            bitmap = (drawable instanceof BitmapDrawable) ?
                    ((BitmapDrawable) drawable).getBitmap() :
                    Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap == null) {
            imageView.invalidate();
            return;
        }
        paint = new Paint(ANTI_ALIAS_FLAG);
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        if (imageView.getScaleType() != ImageView.ScaleType.CENTER_CROP && imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        bitmapShader.setLocalMatrix(setUpScaleType(bitmap, imageView, width, height));
        imageView.invalidate();
    }

    private Matrix setUpScaleType(Bitmap bitmap, ImageView iv, float width, float height) {
        float scaleX = 1, scaleY = 1, dx = 0, dy = 0;
        Matrix shaderMatrix = new Matrix();
        if (bitmap == null) {
            return null;
        }
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
        return shaderMatrix;
    }

    //Overriden Methods
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        path = config.getPath(height, width);
        invalidate();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ViewOutlineProvider getOutlineProvider() {
        shadowpath = new Path();
        rect = new Rect(0, 0, (int) width, (int) height);
        RectF r = new RectF(rect);
        shadowpath.addRoundRect(r, config.getRadius(), config.getRadius(), Path.Direction.CCW);
        shadowpath.op(config.getPathShadow(width, height), shadowpath, Path.Op.INTERSECT);
        return new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                if (path.isConvex()) {
                    outline.setConvexPath(shadowpath);
                }
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //  super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);
        switch (config.getType()) {
            case 0:
                paint.setColor(config.getBaseColor());
                break;
            case 1:
                paint.setShader(config.getLinearGradient(width, height));
                break;
            case 2:
                paint.setShader(config.getRadialGradient(width, height));
                break;
            case 3:
                setupBitmap(this, width, height);
                break;
        }
        if (config.getRadius() != 0f) {
            paint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
            paint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
            paint.setPathEffect(new CornerPathEffect(config.getRadius()));
        }
        ViewCompat.setElevation(this, config.getElevation());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && ViewCompat.getElevation(this) > 0f) {

            try {
                setOutlineProvider(getOutlineProvider());
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
                e.printStackTrace();
            }
        }
        paint.setXfermode(pdMode);
        canvas.drawPath(path, paint);
    }
}