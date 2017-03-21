package ak.sh.ay.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ak.sh.ay.oblique.ObliqueView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // final ObliqueView obliqueView = (ObliqueView) findViewById(R.id.obliqueView);
       /* AppCompatSeekBar seekbar = (AppCompatSeekBar) findViewById(R.id.seekbar);
        seekbar.setMax(180);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                obliqueView.setStartAngle(i);
                obliqueView.setEndAngle(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
    }

    @OnClick(R.id.sample)
    public void sample(View v) {
        startActivity(new Intent(MainActivity.this, ListSampleActivity.class));
    }
}
