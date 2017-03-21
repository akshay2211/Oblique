package ak.sh.ay.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;

import ak.sh.ay.oblique.ObliqueView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ObliqueView obliqueView = (ObliqueView) findViewById(R.id.obliqueView);
        AppCompatSeekBar seekbar = (AppCompatSeekBar) findViewById(R.id.seekbar);
       /* obliqueView.setStartAngle(20);
        obliqueView.setEndAngle(20);
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
}
