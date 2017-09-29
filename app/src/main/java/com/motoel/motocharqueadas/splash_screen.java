package com.motoel.motocharqueadas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new BkTarefa().execute();
    }

    private class BkTarefa extends AsyncTask <String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {

            boolean conn = verificaConexao();

            if (conn) {
                publishProgress(1);
            } else {
                publishProgress(2);
            }

            SystemClock.sleep(2500);

            publishProgress(3);

            SqliteDatabase sql = new SqliteDatabase(getBaseContext());

            try {
                sql.createDataBase();
            } catch (Exception e) {
                Log.e("DEU-ruim DB", e.toString()) ;
            }

            SystemClock.sleep(2500);

            return null;
        }

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

        protected void onProgressUpdate(Integer... values) {
            TextView t = (TextView) findViewById(R.id.txtStatus);

            switch (values[0]) {
                case 1:
                    t.setText("Conexão com internet OK");
                    break;
                case 2:
                    t.setText("SEM conexão com internet");
                    break;
                case 3:
                    t.setText("Verificando Banco de Dados");
                    break;
                case 4:
                    t.setText("Aguarde um momento. Atualizando Banco de dados...");
                    break;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(splash_screen.this,
                    Principal.class);
            startActivity(intent);
        }
    }

}
