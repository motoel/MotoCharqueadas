package com.motoel.motocharqueadas;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileOutputStream;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar ,  0, 0);
        mDrawerToggle.syncState();

        //final TextView proximaAtracao = (TextView) findViewById(R.id.TextProximaAtracaoContador);

        //initSlider();
        */
        //region PROXIMO EVENTO TIMER desabilitado
        /*final Calendar tProximaAtracao = Calendar.getInstance();
        tProximaAtracao.set(Calendar.DAY_OF_MONTH,24);
        tProximaAtracao.set(Calendar.MONTH,6); // 0-11 so 1 less
        tProximaAtracao.set(Calendar.YEAR, 2017);
        tProximaAtracao.set(Calendar.HOUR_OF_DAY, 0);
        tProximaAtracao.set(Calendar.MINUTE, 45);
        tProximaAtracao.set(Calendar.SECOND, 0);

        Calendar tAgora = Calendar.getInstance();

        long diff = tAgora.getTimeInMillis() - tProximaAtracao.getTimeInMillis();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        proximaAtracao.setText("Dias: " + diffDays + " Horas: " + diffHours + " Minutos: " + diffMinutes + " Sec.:" +diffSeconds);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar tAgora = Calendar.getInstance();

                                long diff = tAgora.getTimeInMillis() - tProximaAtracao.getTimeInMillis();
                                long diffSeconds = diff / 1000 % 60;
                                long diffMinutes = diff / (60 * 1000) % 60;
                                long diffHours = diff / (60 * 60 * 1000) % 24;
                                long diffDays = diff / (24 * 60 * 60 * 1000);

                                proximaAtracao.setText("Dias: " + diffDays + " Horas: " + diffHours + " Minutos: " + diffMinutes + " Sec.:" +diffSeconds);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        //t.start();
        */
        //endregion -

        CriarArquivoTexto();

    }


    private void CriarArquivoTexto() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
                break;
            case R.id.nav_programacao:
                fragment = new programacao_completa_tabs();
                break;
            case R.id.nav_fotos:
                fragment = new programacao_completa_slide_2();
                break;
            case R.id.nav_como_chegar:
                fragment = new como_chegar();
                break;
            case R.id.nav_patrocinadores:
                fragment = new patrocinadores();
                break;
            case R.id.nav_contato:
                fragment = new contato();
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

}
