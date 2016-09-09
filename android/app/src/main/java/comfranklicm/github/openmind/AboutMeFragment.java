package comfranklicm.github.openmind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by FrankLicm on 9/8/2016.
 */
public class AboutMeFragment extends Fragment {
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ActiveDegreeRecyViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
