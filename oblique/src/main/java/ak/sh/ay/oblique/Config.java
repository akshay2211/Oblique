package ak.sh.ay.oblique;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by akshay on 21/3/17.
 */

public class Config {
    private static final Paint bitmapPaint = new Paint(ANTI_ALIAS_FLAG);
    private static Bitmap bitmap;
    private static BitmapShader bitmapShader;

    public static int getTanWithOutConflict(float h, float w, float angle, double hype) {
        if (angle > 90) {
            angle = 180 - angle;
        }
        int val = (int) Math.ceil(h / Math.tan(Math.toRadians(angle)));
        if (val <= hype) {
            return val;
        }
        int p = (int) Math.ceil(w * Math.tan(Math.toRadians(angle)));
        return getTanWithOutConflict(p, w, angle, hype);
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static Paint getBitmapPaint() {
        return bitmapPaint;
    }

    public static BitmapShader getBitmapShader() {
        return bitmapShader;
    }

    public static Path getPath(float h, float w, float left_angle, float right_angle) {

        double hyp = Math.hypot(w, h);
        int left_base = getTanWithOutConflict(h, w, left_angle, hyp);
        int right_base = getTanWithOutConflict(h, w, right_angle, hyp);
        float a1 = 0, a2 = 0, b1 = w, b2 = 0, c1 = w, c2 = h, d1 = 0, d2 = h;
        try {
            if (left_angle > 90 & left_angle <= 180) {
                if (left_angle > 145) {
                    d1 = w;
                    d2 = (float) Math.ceil(w * Math.tan(Math.toRadians(180 - left_angle)));
                } else {
                    d1 = Math.abs(left_base);
                }
            } else {
                a1 = Math.abs(left_base);
                if (a1 == 0) {
                    a1 = w;
                }
                if (left_angle < 45)
                    a2 = h - (float) Math.ceil(w * Math.tan(Math.toRadians(left_angle)));
            }

            if (right_angle > 90) {

                if (right_angle > 135) {
                    b1 = 0;
                    b2 = h - (float) Math.ceil(w * Math.tan(Math.toRadians(180 - right_angle)));
                } else {
                    b1 = Math.abs(w - right_base);//w - right_base;
                }

            } else if (right_angle <= 180) {
                if (right_angle < 45) {
                    c2 = (float) Math.floor(w * Math.tan(Math.toRadians(right_angle)));
                    c1 = 0;
                } else {
                    c1 = w - Math.abs(right_base);//w - right_base;
                }
            }

       /*     Log.e("left_angle " + left_angle + "  right_angle " + right_angle,
                    " | a1 " + a1 + " | a2 " + a2 + " | b1 " + b1 + " | b2 " + b2 + " | c1 " + c1 + " | c2 " + c2 + " | d1 " + d1 + " | d2 " + d2);
       */
        } catch (Exception e) {
            Log.e("exception", "" + e.getMessage());
        }
        Path path = new Path();
        path.moveTo(a1, a2);
        path.lineTo(b1, b2);
        path.lineTo(c1, c2);
        path.lineTo(d1, d2);
        path.lineTo(a1, a2);
        path.close();
        return path;
    }


    public static void setupBitmap(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            if (drawable instanceof BitmapDrawable)
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            else {
                try {
                    int COLOR_DRAWABLE_DIMENSIONS = 2;
                    if (drawable instanceof ColorDrawable)
                        bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSIONS, COLOR_DRAWABLE_DIMENSIONS, Bitmap.Config.ARGB_8888);
                    else
                        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

                   /* Canvas canvas = new Canvas(bitmap);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bitmap == null) {
                imageView.invalidate();
                return;
            }
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            bitmapPaint.setShader(bitmapShader);
            imageView.invalidate();
        }
    }
}
