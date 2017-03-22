package ak.sh.ay.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ak.sh.ay.app.R;
import butterknife.ButterKnife;

/**
 * Created by akshay on 2/13/16.
 */

public class SampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> mDataset = new ArrayList<>();
    private Context context;

    public SampleAdapter(Context context) {
        this.context = context;

    }

    public void addData() {
        for (int i = 0; i < 4; i++) {
            mDataset.add("Sample txt " + i);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType % 4 == 0) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one, parent, false);
        } else if (viewType % 4 == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_two, parent, false);
        } else if (viewType % 4 == 2) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_three, parent, false);
        } else if (viewType % 4 == 3) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_four, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vH, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}

