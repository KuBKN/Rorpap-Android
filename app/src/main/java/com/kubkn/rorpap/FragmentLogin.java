package com.kubkn.rorpap;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by batmaster on 2/24/16 AD.
 */
public class FragmentLogin extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

//        int resourceId = getArguments().getInt(MyActivity.KEY_DRAWABLE_ID);


        return view;
    }

}
