package com.motoel.motocharqueadas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");
    }

}
