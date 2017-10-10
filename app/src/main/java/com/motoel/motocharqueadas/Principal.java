package com.motoel.motocharqueadas;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean atualiza=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_principal);

        new BkTarefa().execute();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (currentFragment != null && currentFragment instanceof frag_principal) {

            //Intent intent = new Intent(this, splash_screen.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // this will clear all the stack
            //intent.putExtra("EXIT", true);
            //startActivity(intent);
            //finish();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);

        }
        else {
            Fragment fragment = new frag_principal();
            if (fragment != null) {
                atualiza=true;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_principal:
                fragment = new frag_principal();
                atualiza=true;
                break;
            case R.id.nav_programacao:
                fragment = new programacao_completa_tabs();
                break;
            case R.id.nav_fotos:
                fragment = new fotos_evento_atual ();
                break;
            case R.id.nav_como_chegar:
                fragment = new como_chegar();
                break;
            case R.id.nav_eventos_passados:
                fragment = new eventos_passados();
                break;
            case R.id.nav_contato:
                fragment = new contato();
                break;
            case R.id.nav_patrocinadores:
                fragment = new patrocinadores();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private class BkTarefa extends AsyncTask<String, Integer, String> {

        TextView txtProximaAtracaoC;
        TextView txtProximaAtracaoT;
        ImageView imgProximaAtracao;
        String img_patch = "";
        String nome_do_evento = "";
        String tempo = "";

        @Override
        protected String doInBackground(String... urls) {

            try {
                while (1 == 1) {
                    Thread.sleep(1000);

                    publishProgress(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private void TimerProximaAtracao() {
            SqliteDatabase sql = new SqliteDatabase(getBaseContext());
            String ret = sql.proximoEvento();

            if (txtProximaAtracaoC==null || atualiza) {
                txtProximaAtracaoC = (TextView) findViewById(R.id.TextProximaAtracaoContador);
                txtProximaAtracaoT = (TextView) findViewById(R.id.TextProximaAtracao);
                imgProximaAtracao = (ImageView) findViewById(R.id.imgProximaAtracao);

                atualiza=false;
            }

            if (ret=="deu ruim") {
                //Log.d("DEBUG_DATA","deu ruim");
                tempo = "";
            } else {
                String ret01[] = ret.split(";");

                img_patch =  ret01[2].substring(0, ret01[2].lastIndexOf("."));
                nome_do_evento = ret01[1];

                Calendar tProximaAtracao = Calendar.getInstance();
                tProximaAtracao.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ret01[0].substring(8,10)));
                tProximaAtracao.set(Calendar.MONTH, Integer.parseInt(ret01[0].substring(5,7)) - 1); // 0-11 so 1 less
                tProximaAtracao.set(Calendar.YEAR, 2017);
                tProximaAtracao.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ret01[0].substring(11,13)));
                tProximaAtracao.set(Calendar.MINUTE, Integer.parseInt(ret01[0].substring(14,16)));
                tProximaAtracao.set(Calendar.SECOND, 0);

                Calendar tAgora = Calendar.getInstance();

                long diff = tAgora.getTimeInMillis() - tProximaAtracao.getTimeInMillis();
                if (diff<0) { diff=diff*-1;}
                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                tempo = String.valueOf(diffDays) + " DIAS(s)" +
                        "  |  " + String.valueOf(diffHours) +
                        " HORA(s)  |  " + String.valueOf(diffMinutes) +
                        " MINUTO(S)  |  " +String.valueOf(diffSeconds) + " SEG.";
            }

        }

        protected void onProgressUpdate(Integer... values) {
            TimerProximaAtracao();

            if (tempo=="") {
                //deu pau ou nao tem atracao
                //carregar atracao padrao
            } else {
                txtProximaAtracaoC.setText(tempo);
                txtProximaAtracaoT.setText(nome_do_evento);
                imgProximaAtracao.setImageResource(getBaseContext().getResources().getIdentifier(img_patch , "drawable", getPackageName()));
            }

        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(Principal.this,
                    Principal.class);
            startActivity(intent);
        }
    }

}
