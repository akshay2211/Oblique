package ak.sh.ay.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import ak.sh.ay.oblique.ObliqueView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private ObliqueView ov_demo, ov_sample1, ov_sample2, ov_sample3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideStatusbar(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ov_demo = (ObliqueView) findViewById(R.id.ov_demo);
        ov_sample1 = (ObliqueView) findViewById(R.id.ov_sample1);
        ov_sample2 = (ObliqueView) findViewById(R.id.ov_sample2);
        ov_sample3 = (ObliqueView) findViewById(R.id.ov_sample3);
        // ov_demo.setOnTouchListener(this);
        // ov_sample1.setOnTouchListener(this);
        // ov_sample2.setOnTouchListener(this);
        // ov_sample3.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.hideStatusbar(this);
    }

    @OnClick(R.id.sample1)
    public void sample1(View v) {
        ov_demo.setShadow(0);
        startActivity(new Intent(MainActivity.this, ListSampleActivity.class));
    }

    @OnClick(R.id.sample2)
    public void sample2(View v) {
        ov_demo.setShadow(0);
        startActivity(new Intent(MainActivity.this, SampleTwoActivity.class));
    }

    @OnClick(R.id.docs)
    public void docs(View v) {

    }

    @OnClick(R.id.demonstrator)
    public void demonstrator(View v) {
        startActivity(new Intent(MainActivity.this, DemonstratorActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("-------------", "   " + v.getId());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (v instanceof ObliqueView) {
                    ((ObliqueView) v).setShadow(0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (v instanceof ObliqueView) {
                    ((ObliqueView) v).setShadow(10);
                }
                break;
            default:
                return false;
        }
        return false;

    }
}
