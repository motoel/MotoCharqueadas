package com.motoel.motocharqueadas;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class programacao_completa_tabs extends Fragment {

    private FragmentTabHost mTabHost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        //return inflater.inflate(R.layout.fragment_programacao_completa_tabs, container, false);
        View rootView = inflater.inflate(R.layout.fragment_programacao_completa_tabs,container, false);


        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("Sexta").setIndicator("Sexta"),
                programacao_completa_slide_1.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Sábado").setIndicator("Sábado"),
                programacao_completa_slide_2.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Domingo").setIndicator("Domingo"),
                programacao_completa_slide_3.class, null);

        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);


            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv.setTextColor(getContext().getColor(R.color.white));
            } else {
                //noinspection deprecation
                tv.setTextColor(getContext().getResources().getColor(R.color.white));
            }
        }

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");
    }

}
