package ak.sh.ay.oblique;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by akshay on 21/3/17.
 */

public class Config {
    private GradientAngle angle = GradientAngle.LEFT_TO_RIGHT;
    private int baseColor = Color.TRANSPARENT;
    private float startAngle, endAngle;
    private float elevation;
    private int type = 0;
    private float radius = 0f;
    private int startColor, endColor;


    public Config(Context context, AttributeSet attrs) {
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ObliqueView, 0, 0);
        try {
            startAngle = attributes.getFloat(R.styleable.ObliqueView_starting_slant_angle, 90f);
            endAngle = attributes.getFloat(R.styleable.ObliqueView_ending_slant_angle, 90f);
            baseColor = attributes.getColor(R.styleable.ObliqueView_basecolor, Color.TRANSPARENT);
            radius = attributes.getFloat(R.styleable.ObliqueView_radius, 0f);
            type = attributes.getInt(R.styleable.ObliqueView_type, 0);
            startColor = attributes.getColor(R.styleable.ObliqueView_startcolor, Color.TRANSPARENT);
            endColor = attributes.getColor(R.styleable.ObliqueView_endcolor, Color.TRANSPARENT);
            elevation = attributes.getFloat(R.styleable.ObliqueView_shadow, 0f);
            int gradientangle = attributes.getInteger(R.styleable.ObliqueView_angle, 0);
            setupAngle(gradientangle);

        } finally {
            attributes.recycle();
        }
    }

    public float getShadow() {

        return elevation > 0 ? (elevation + 10) : 0;
    }

    public Config setElevation(float elevation) {
        this.elevation = elevation;
        return this;
    }

    public int getStartColor() {
        return startColor;
    }

    public Config setStartColor(int startColor) {
        this.startColor = startColor;
        return this;
    }

    public int getEndColor() {
        return endColor;
    }

    public Config setEndColor(int endColor) {
        this.endColor = endColor;
        return this;
    }

    public GradientAngle getAngle() {
        return angle;
    }

    public Config setAngle(GradientAngle gradientAngle) {
        this.angle = gradientAngle;
        return this;
    }

    public float getRadius() {
        return radius;
    }

    public Config setRadius(@FloatRange(from = 0, to = 60) float radius) {
        this.radius = radius;
        return this;
    }

    public int getBaseColor() {
        return baseColor;
    }

    public Config setBaseColor(int baseColor) {
        this.baseColor = baseColor;
        return this;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public Config setStartAngle(@FloatRange(from = 0, to = 180) float startAngle) {
        this.startAngle = startAngle;
        return this;
    }

    public float getEndAngle() {
        return endAngle;
    }

    public Config setEndAngle(@FloatRange(from = 0, to = 180) float endAngle) {
        this.endAngle = endAngle;
        return this;
    }


    private int getTanWithOutConflict(float h, float w, float angle, double hype) {
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

    public Path getPath(float h, float w) {
        double hyp = Math.hypot(w, h);
        int left_base = getTanWithOutConflict(h, w, startAngle, hyp);
        int right_base = getTanWithOutConflict(h, w, endAngle, hyp);
        float a1 = 0, a2 = 0, b1 = w, b2 = 0, c1 = w, c2 = h, d1 = 0, d2 = h;
        try {
            if (startAngle > 90 & startAngle <= 180) {
                if (startAngle > 145) {
                    d1 = w;
                    d2 = (float) Math.ceil(w * Math.tan(Math.toRadians(180 - startAngle)));
                } else {
                    d1 = Math.abs(left_base);
                }
            } else {
                a1 = Math.abs(left_base);
                if (a1 == 0) {
                    a1 = w;
                }
                if (startAngle < 45)
                    a2 = h - (float) Math.ceil(w * Math.tan(Math.toRadians(startAngle)));
            }

            if (endAngle > 90) {

                if (endAngle > 135) {
                    b1 = 0;
                    b2 = h - (float) Math.ceil(w * Math.tan(Math.toRadians(180 - endAngle)));
                } else {
                    b1 = Math.abs(w - right_base);//w - right_base;
                }

            } else if (endAngle <= 180) {
                if (endAngle < 45) {
                    c2 = (float) Math.floor(w * Math.tan(Math.toRadians(endAngle)));
                    c1 = 0;
                } else {
                    c1 = w - Math.abs(right_base);//w - right_base;
                }
            }

        /*    Log.e("startAngle " + startAngle + "  right_angle " + right_angle,
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

    public Path getPathShadow(float h, float w) {
        int MARGIN = 10;
        double hyp = Math.hypot(w, h);
        int left_base = getTanWithOutConflict(h, w, startAngle, hyp);
        int right_base = getTanWithOutConflict(h, w, endAngle, hyp);
        float a1 = 0, a2 = 0, b1 = w, b2 = 0, c1 = w, c2 = h, d1 = 0, d2 = h;
        try {
            if (startAngle > 90 & startAngle <= 180) {
                if (startAngle > 145) {
                    d1 = w;
                    d2 = (float) Math.ceil(w * Math.tan(Math.toRadians(180 - startAngle))) - MARGIN;
                } else {
                    d1 = Math.abs(left_base) - MARGIN;
                }


            } else {
                a1 = Math.abs(left_base);
                if (a1 == 0) {
                    a1 = w;
                }
                if (startAngle < 45)
                    a2 = h - (float) Math.ceil(w * Math.tan(Math.toRadians(startAngle))) - MARGIN;
            }

            if (endAngle > 90) {
                if (endAngle > 135) {
                    b1 = 0;
                    b2 = h - (float) Math.ceil(w * Math.tan(Math.toRadians(180 - endAngle))) - MARGIN;
                } else {
                    b1 = Math.abs(w - right_base) - MARGIN;//w - right_base;
                }

            } else if (endAngle <= 180) {
                if (endAngle < 45) {
                    c2 = (float) Math.floor(w * Math.tan(Math.toRadians(endAngle)));
                    c1 = 0;
                } else {
                    c1 = w - Math.abs(right_base) - MARGIN;//w - right_base;
                }
            }

        /*    Log.e("startAngle " + startAngle + "  right_angle " + right_angle,
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


    public int getType() {
        return type;
    }

    public Config setType(Type type) {
        int i = 0;
        switch (type) {
            case SOLID_COLOR:
                i = 0;
                break;
            case LINEAR_GRADIENT:
                i = 1;
                break;
            case RADIAL_GRADIENT:
                i = 2;
                break;
            case IMAGE:
                i = 3;
                break;
            default:
                i = 0;
        }
        this.type = i;
        return this;
    }

    public Shader getRadialGradient(float width, float height) {
        float radius = Math.max(width, height) / 2;
        return new RadialGradient(width / 2, height / 2, radius, startColor, endColor, Shader.TileMode.CLAMP);
    }

    public Shader getLinearGradient(GradientAngle gradientAngle, float width, float height) {
        float x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        switch (gradientAngle) {
            case LEFT_TO_RIGHT:
                x2 = width;
                break;
            case RIGHT_TO_LEFT:
                x1 = width;
                break;
            case TOP_TO_BOTTOM:
                y2 = height;
                break;
            case BOTTOM_TO_TOP:
                y1 = height;
                break;
            case LEFT_TOP_TO_RIGHT_BOTTOM:
                x2 = width;
                y2 = height;
                break;
            case RIGHT_BOTTOM_TO_LEFT_TOP:
                x1 = width;
                x2 = height;
                break;
            case LEFT_BOTTOM_TO_RIGHT_TOP:
                x2 = width;
                y1 = height;
                break;
            case RIGHT_TOP_TO_LEFT_BOTTOM:
                x1 = width;
                y2 = height;
                break;
        }
        return new LinearGradient(x1, y1, x2, y2, startColor, endColor, Shader.TileMode.CLAMP);
    }

    public void setupAngle(int angle) {
        switch (angle) {
            case 0:
                this.angle = GradientAngle.LEFT_TO_RIGHT;
                break;
            case 1:
                this.angle = GradientAngle.RIGHT_TO_LEFT;
                break;
            case 2:
                this.angle = GradientAngle.TOP_TO_BOTTOM;
                break;
            case 3:
                this.angle = GradientAngle.BOTTOM_TO_TOP;
                break;
            case 4:
                this.angle = GradientAngle.LEFT_TOP_TO_RIGHT_BOTTOM;
                break;
            case 5:
                this.angle = GradientAngle.RIGHT_TOP_TO_LEFT_BOTTOM;
                break;
            case 6:
                this.angle = GradientAngle.RIGHT_BOTTOM_TO_LEFT_TOP;
                break;
            case 7:
                this.angle = GradientAngle.LEFT_BOTTOM_TO_RIGHT_TOP;
                break;
            default:
                this.angle = GradientAngle.LEFT_TO_RIGHT;
        }
    }


}
