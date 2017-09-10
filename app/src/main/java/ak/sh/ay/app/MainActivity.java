package ak.sh.ay.app;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.TextView;

import ak.sh.ay.oblique.ObliqueView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_oblique)
    TextView text_oblique;
    private ObliqueView oblique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        oblique = (ObliqueView) findViewById(R.id.obliqueView);
        final int newLeftMargin = 300;
        Animation a = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                if (interpolatedTime == 1.0f && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.e("interpolatedTime", "" + interpolatedTime);
                    oblique.setShadow(0f);

                    oblique.setCornerRadius(0f);
                }
                interpolatedTime = 1 - interpolatedTime;
                ViewGroup.LayoutParams params = oblique.getLayoutParams();
                if (params instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) params;
                    p.setMargins((int) (newLeftMargin * interpolatedTime)
                            , (int) (newLeftMargin * interpolatedTime),
                            (int) (newLeftMargin * interpolatedTime),
                            (int) (newLeftMargin * interpolatedTime));


                    oblique.requestLayout();

                }
            }
        };
        a.setDuration(3000); // in ms
        a.setInterpolator(new DecelerateInterpolator());
        oblique.startAnimation(a);
    }

    @OnClick(R.id.sample)
    public void sample(View v) {
        startActivity(new Intent(MainActivity.this, ListSampleActivity.class));
    }

    @OnClick(R.id.demonstrator)
    public void demonstrator(View v) {
        startActivity(new Intent(MainActivity.this, DemonstratorActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
