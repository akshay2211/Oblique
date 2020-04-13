package ak.sh.ay.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideStatusbar(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.hideStatusbar(this);
    }

    @OnClick(R.id.sample1)
    public void sample1(View v) {
        startActivity(new Intent(MainActivity.this, ListSampleActivity.class));
    }

    @OnClick(R.id.sample2)
    public void sample2(View v) {
        startActivity(new Intent(MainActivity.this, SampleTwoActivity.class));
    }

    @OnClick(R.id.sample)
    public void docs(View v) {
        startActivity(new Intent(MainActivity.this, SampleOneActivity.class));
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
