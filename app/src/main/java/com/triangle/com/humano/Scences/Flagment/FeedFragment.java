package com.triangle.com.humano.Scences.Flagment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.triangle.com.humano.Helper.Helper;
import com.triangle.com.humano.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    Helper helper;

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        helper = new Helper(getActivity());

        Toast.makeText(getActivity(),helper.getHostData(),Toast.LENGTH_LONG).show();

        return rootView;
    }

}
