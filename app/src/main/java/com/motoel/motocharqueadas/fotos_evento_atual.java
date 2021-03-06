package com.motoel.motocharqueadas;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class fotos_evento_atual extends Fragment  {

    private WebView mWebview ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_fotos_evento_atual, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("FOTOS 2017");

        mWebview  = getView().findViewById(R.id.webVisualizador);

        mWebview.setWebViewClient(new WebViewClient());

        mWebview.getSettings().setJavaScriptEnabled(true);

        mWebview.loadUrl("http://www.reluzinfo.com.br/mc2017/index.html");
    }

}
