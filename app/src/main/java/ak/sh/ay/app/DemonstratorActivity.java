package ak.sh.ay.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;
import android.widget.TextView;

import ak.sh.ay.oblique.ObliqueView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DemonstratorActivity extends AppCompatActivity {
    @BindView(R.id.seek1)
    AppCompatSeekBar seek1;
    @BindView(R.id.seek2)
    AppCompatSeekBar seek2;
    @BindView(R.id.txt1)
    TextView txt1;
    @BindView(R.id.txt2)
    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demonstrator);
        ButterKnife.bind(this);
        final ObliqueView obliqueView = (ObliqueView) findViewById(R.id.obliqueView);
        seek1.setMax(180);
        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                obliqueView.setStartAngle(i);
                txt1.setText("Starting side Slanting angle " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek2.setMax(180);
        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                obliqueView.setEndAngle(i);
                txt2.setText("Ending side Slanting angle " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
