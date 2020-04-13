package ak.sh.ay.app;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ak.sh.ay.app.adapters.SampleAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListSampleActivity extends AppCompatActivity {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    SampleAdapter sampleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideStatusbar(this);
        setContentView(R.layout.activity_list_sample);
        ButterKnife.bind(this);
        sampleAdapter = new SampleAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleview.setLayoutManager(linearLayoutManager);
        recycleview.setAdapter(sampleAdapter);
        sampleAdapter.addData();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.hideStatusbar(this);
    }
}
