package com.motoel.motocharqueadas;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;


public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String PATH = "/data/data/com.motoel.motocharqueadas/img";  //put the downloaded file here

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


        //final TextView proximaAtracao = (TextView) findViewById(R.id.TextProximaAtracaoContador);

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


        Thread thread = new Thread() {
            @Override
            public void run() {
                downloadFile("http://www.oracle.com/technetwork/java/readme-2-149793.txt", new File(getCacheDir(), "abdede.txt"));
            }
        };

        thread.start();
    }

    private void CriarBD() {
        SqliteDatabase myDbHelper = new SqliteDatabase(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        }catch(SQLException sqle){

           // throw sqle;

        }
    }

    /* Função para verificar existência de conexão com a internet*/
    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    void downloadFile(String _url, File _file) {
        String path =_url;
        URL u = null;
        try {
            u = new URL(path);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.connect();

            final InputStream in = c.getInputStream();

            copyInputStreamToFile(in, _file);
            //final String s = convertStreamToString(in);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                       // TextView t = (TextView) findViewById(R.id.txtteste);
                       // t.setText(s);

                    //copyInputStreamToFile(in, new File(getCacheDir(), "teste.txt"));
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyInputStreamToFile(InputStream in, File file) {
        OutputStream out = null;

        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Ensure that the InputStreams are closed even if there's an exception.
            try {
                if ( out != null ) {
                    out.close();
                }

                // If you want to close the "in" InputStream yourself then remove this
                // from here but ensure that you close it yourself eventually.
                in.close();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

    private static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
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
