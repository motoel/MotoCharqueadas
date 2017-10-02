package com.motoel.motocharqueadas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        new BkTarefa().execute();

        if(getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }
    }

    private class BkTarefa extends AsyncTask <String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {

            int delayEntreProgresso = 1000;

            //VERIFICA A EXISTENCIA DO BANCO DE DADOS
            //CASO O BANCO NÃO EXISTA ELE COPIA DO ASSET PARA A PASTA DATABASE
            publishProgress(3);

            final SqliteDatabase sql = new SqliteDatabase(getApplicationContext());

            try {
                sql.createDataBase();
            } catch (Exception e) {
                Log.e("DEU-ruim DB", e.toString()) ;
            }
            SystemClock.sleep(delayEntreProgresso);

            //VERIFICA A CONEXÃO COM A INTERNET
            //CASO NÃO HAJA CONEXÃO ABRE A TELA INICIAL
            boolean conn = verificaConexao();
            if (conn) {
                publishProgress(1);

                SystemClock.sleep(delayEntreProgresso);//posso retirar ou colocar um valor baixo

                //VERIFICA A VERSÃO DO APP COM A VERSÃO MAIS RECENTE
                int vonline = downloadFile("http://reluzinfo.com.br/APPversao.txt", null, true);
                int vdb = sql.verificaVersao();

                //Log.d("VERSAO_DO_BD", String.valueOf(vonline) + " <-Vonline  Vdb-> " + String.valueOf(vdb) );

                if (vdb < vonline) { //banco desatualizado... baixa o arquivo e substitui o atual
                    publishProgress(5);

                    downloadFile("http://reluzinfo.com.br/motocharqueadas.db", new File(getCacheDir(), "motocharqueadas.db"), false);

                    try {
                        sql.copyDataBase(false);
                    } catch (Exception e) {
                        publishProgress(101);
                    }
                }

                if (vdb == vonline) { //banco atualizado
                    publishProgress(6);
                }

                if (vonline <0) { //erro ao baixar arquivo online
                    publishProgress(100);
                }

                SystemClock.sleep(delayEntreProgresso);
            } else {
                publishProgress(2);

                SystemClock.sleep(delayEntreProgresso);
            }

            return null;
        }

        public boolean verificaConexao() {
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

        private int downloadFile(String _url, File _file, boolean lerComoInt) {
            String path =_url;
            URL u = null;
            try {
                u = new URL(path);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.connect();

                final InputStream in = c.getInputStream();
                if (lerComoInt) {
                    String s = convertStreamToString(in);
                    return Integer.parseInt(s.trim());
                } else {
                    copyInputStreamToFile(in, _file);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return -1;
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

        private String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
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

        protected void onProgressUpdate(Integer... values) {
            TextView t = (TextView) findViewById(R.id.txtStatus);

            switch (values[0]) {
                case 1:
                    t.setText("Conexão com internet OK... Verificando versão do BD");
                    break;
                case 2:
                    t.setText("SEM conexão com internet... Iniciando APP");
                    break;
                case 3:
                    t.setText("Verificando Banco de Dados");
                    break;
                case 4://sem uso
                    t.setText("Aguarde um momento. Atualizando Banco de dados...");
                    break;
                case 5:
                    t.setText("Banco de dados desatualizado... Atualizando banco de dados... Aguarde...");
                    break;
                case 6:
                    t.setText("Banco de dados atualizado... Iniciando APP");
                    break;
                case 100:
                    t.setText("Erro ao verificar versão... Iniciando APP");
                    break;
                case 101:
                    t.setText("Erro ao atualizar banco de dados... Iniciando APP");
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
