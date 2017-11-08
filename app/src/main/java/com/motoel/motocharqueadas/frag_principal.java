package com.motoel.motocharqueadas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class frag_principal extends Fragment {

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

        lstImages.add(R.drawable.ic_atracao_principal_01);
        lstImages.add(R.drawable.ic_atracao_principal_02);
        lstImages.add(R.drawable.ic_atracao_principal_03);
        lstImages.add(R.drawable.ic_atracao_principal_04);
        lstImages.add(R.drawable.ic_atracao_principal_05);

        HorizontalInfiniteCycleViewPager pager = (HorizontalInfiniteCycleViewPager)getView().findViewById(R.id.hicvp);
        HorizontalPagerAdapter adapter = new HorizontalPagerAdapter(lstImages, getActivity().getBaseContext());
        pager.setAdapter(adapter);
    }
}
