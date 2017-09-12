package ak.sh.ay.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ak.sh.ay.oblique.GradientAngle;
import ak.sh.ay.oblique.ObliqueView;
import ak.sh.ay.oblique.Type;

public class Sample2Activity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideStatusbar(this);
        setContentView(R.layout.activity_sample2);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.hideStatusbar(this);
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sample2, container, false);
            ObliqueView obliqueView = (ObliqueView) rootView.findViewById(R.id.obliqueView);
            TextView text_oblique = (TextView) rootView.findViewById(R.id.text_oblique);

            /*app:angle="LEFT_BOTTOM_TO_RIGHT_TOP"
            app:basecolor="#2E3192"
            app:endcolor="#FBB03B"
            app:ending_slant_angle="0"
            app:radius="18"
            app:shadow="10"
            app:startcolor="#D4145A"
            app:starting_slant_angle="10"
            app:type="linear_gradient"*/

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    setup_1(obliqueView);
                    break;
                case 2:
                    setup_2(obliqueView);
                    break;
                case 3:
                    setup_1(obliqueView);
                    break;
                default:

                    break;
            }
            // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        private void setup_1(ObliqueView obliqueView) {
            obliqueView.setType(Type.LINEAR_GRADIENT);
            obliqueView.setStartColor(Color.parseColor("#D4145A"));
            obliqueView.setEndColor(Color.parseColor("#FBB03B"));
            obliqueView.setAngle(GradientAngle.LEFT_BOTTOM_TO_RIGHT_TOP);
            obliqueView.setStartAngle(16);
            obliqueView.setEndAngle(0);
            obliqueView.setCornerRadius(15);
            obliqueView.setShadow(10);
        }

        private void setup_2(ObliqueView obliqueView) {
            obliqueView.setType(Type.IMAGE);
            obliqueView.setEndAngle(10);
            obliqueView.setStartAngle(10);
            obliqueView.setCornerRadius(15);
            obliqueView.setShadow(10);
            obliqueView.setImageResource(R.drawable.pretty_1);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
