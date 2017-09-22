package com.motoel.motocharqueadas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

public class frag_principal extends Fragment {

    private static final Integer[] DESTAQUES = {R.drawable.atracao_principal_01,R.drawable.atracao_principal_02,R.drawable.atracao_principal_03,R.drawable.atracao_principal_04,R.drawable.atracao_principal_05};
    List<Integer> lstImages = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_principal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("MOTO CHARQUEADAS 2017");

        lstImages.add(R.drawable.atracao_principal_01);
        lstImages.add(R.drawable.atracao_principal_02);
        lstImages.add(R.drawable.atracao_principal_03);

        HorizontalInfiniteCycleViewPager pager = (HorizontalInfiniteCycleViewPager)getView().findViewById(R.id.hicvp);
        HorizontalPagerAdapter adapter = new HorizontalPagerAdapter(lstImages, getActivity().getBaseContext());
        pager.setAdapter(adapter);
    }
}
