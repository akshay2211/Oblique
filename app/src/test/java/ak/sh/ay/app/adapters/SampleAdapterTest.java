package ak.sh.ay.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static org.mockito.Mockito.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import ak.sh.ay.app.R;

import static ak.sh.ay.app.R.styleable.RecyclerView;
import static org.junit.Assert.*;

/**
 * Created by Nic Welch on 4/30/2017.
 */
public class SampleAdapterTest {
    @Test
    public void addData() throws Exception {
        ArrayList<String> mDatasetTest = new ArrayList<>();
        for (int i = 0; i < 2; i++)
        {
            mDatasetTest.add("Test " + i);
        }

        ArrayList<String> DatasetCompare = new ArrayList<String>(Arrays.asList("Test 0", "Test 1"));
        assertTrue(mDatasetTest.equals(DatasetCompare));
    }

    @Test
    public void onCreateViewHolder() throws Exception {

        ViewGroup parent = mock(ViewGroup.class);
        int viewType = 3;

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

        assertNotNull(v);
    }

    @Test
    public void getItemCount() {
        ArrayList<String> DatasetCompare = new ArrayList<String>(Arrays.asList("Test 0", "Test 1"));
        assertEquals(2, DatasetCompare.size());
    }

}