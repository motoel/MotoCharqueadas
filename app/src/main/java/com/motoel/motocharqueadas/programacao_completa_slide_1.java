package com.motoel.motocharqueadas;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class programacao_completa_slide_1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_programacao_completa_slide_1, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.title_activity_tab_programacao_1);

        SqliteDatabase sql = new SqliteDatabase(getContext());
        String ret[] = sql.preencheEventos(10);
        String texto="";
        TextView txtLista = getView().findViewById(R.id.txtLista);

        for (int i=0;i < ret.length; i++) {
            //ret[i].split(";")[2] img patch
            //ret[i].split(";")[3] é destaque

            if (ret[i].split(";")[3].equals("SIM")) {
                texto = texto + "<font color='red'><b>" + ret[i].split(";")[0].substring(11) + " - " + ret[i].split(";")[1] + "</b></font>" +  "<br>";
            } else {
                texto = texto + ret[i].split(";")[0].substring(11) + " - " + ret[i].split(";")[1] + "<br>";
            }

        }
        if (Build.VERSION.SDK_INT >= 24) {
            txtLista.setText(Html.fromHtml(texto), TextView.BufferType.SPANNABLE);
        } else {
            txtLista.setText(Html.fromHtml(texto));
        }

    }

}
