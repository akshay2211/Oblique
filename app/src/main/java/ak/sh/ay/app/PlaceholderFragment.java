package ak.sh.ay.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ak.sh.ay.oblique.GradientAngle;
import ak.sh.ay.oblique.ObliqueView;
import ak.sh.ay.oblique.Type;

/**
 * Created by akshay on 14/9/17.
 */

public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

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
                setup_3(obliqueView);
                break;
            case 4:
                setup_4(obliqueView);
                break;
            case 5:
                setup_5(obliqueView);
                break;
            default:

                break;
        }
        return rootView;
    }

    private void setup_1(ObliqueView obliqueView) {
        obliqueView.setType(Type.LINEAR_GRADIENT);
        obliqueView.setStartColor(Color.parseColor("#D4145A"));
        obliqueView.setEndColor(Color.parseColor("#FBB03B"));
        obliqueView.setAngle(GradientAngle.LEFT_BOTTOM_TO_RIGHT_TOP);
        obliqueView.setStartAngle(12);
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

    private void setup_3(ObliqueView obliqueView) {
        obliqueView.setType(Type.RADIAL_GRADIENT);
        obliqueView.setEndColor(Color.parseColor("#662D8C"));
        obliqueView.setStartColor(Color.parseColor("#ED1E79"));
        obliqueView.setEndAngle(85);
        obliqueView.setStartAngle(85);
        obliqueView.setCornerRadius(15);
        obliqueView.setShadow(10);
    }

    private void setup_4(ObliqueView obliqueView) {
        obliqueView.setType(Type.IMAGE);
        obliqueView.setEndAngle(99);
        obliqueView.setStartAngle(99);
        obliqueView.setCornerRadius(15);
        obliqueView.setShadow(10);
        obliqueView.setImageResource(R.drawable.pretty_2);
    }

    private void setup_5(ObliqueView obliqueView) {
        obliqueView.setType(Type.LINEAR_GRADIENT);
        obliqueView.setStartColor(Color.parseColor("#2E3192"));
        obliqueView.setEndColor(Color.parseColor("#1BFFFF"));
        obliqueView.setAngle(GradientAngle.RIGHT_BOTTOM_TO_LEFT_TOP);
        obliqueView.setEndAngle(170);
        obliqueView.setStartAngle(180);
        obliqueView.setCornerRadius(15);
        obliqueView.setShadow(10);
    }
}