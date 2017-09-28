package com.motoel.motocharqueadas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import me.relex.circleindicator.CircleIndicator;

public class frag_principal extends Fragment {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] DESTAQUES= {R.drawable.atracao_principal_01,R.drawable.atracao_principal_02,R.drawable.atracao_principal_03,R.drawable.atracao_principal_04,R.drawable.atracao_principal_05};
    private ArrayList<Integer> DESTAQUESArray = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_principal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("MOTO CHARQUEADAS");

       //initSlider();
    }

    private void initSlider() {
        for(int i=0;i<DESTAQUES.length;i++)
            DESTAQUESArray.add(DESTAQUES[i]);

        mPager = (ViewPager) getView().findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(getActivity(), DESTAQUESArray));
        //CircleIndicator indicator = (CircleIndicator) getView().findViewById(R.id.indicator);
        //indicator.setViewPager(mPager);

        /*
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == DESTAQUES.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 7500, 7500);
*/
    }
}
