package com.motoel.motocharqueadas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marcelo on 22/09/2017.
 */

public class SqliteDatabase extends SQLiteOpenHelper {


    private static String DB_PATH = "/data/data/com.motoel.motocharqueadas/databases/";
    private static String DB_NAME = "motocharqueadas.db";
    private static String PREFIXO_HTML = "www.motoel.com/motocharqueadas/app/";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public SqliteDatabase(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            Log.d("BD_DEBUG", "DB EXITE!??");
            //do nothing - database already exist
        }else{
            Log.d("BD_DEBUG", "DB Ñ EXISTE");

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase(true);

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public int verificaVersao() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM Versao", null);
        cursor.moveToFirst();
        return Integer.parseInt(cursor.getString(0));
    }

    public String proximoEvento() {
        String ret = "deu ruim";
        SQLiteDatabase database = this.getReadableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDateandTime = sdf.format(new Date());

        String query = "SELECT * FROM programacao " +
                "WHERE datetime(dataHora) > " +
                "datetime('"+currentDateandTime+"') " +
                "ORDER BY datetime(dataHora) ASC limit 1";


        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()){
            ret = cursor.getString(0) + ";" + cursor.getString(1) + ";" + cursor.getString(2)+ ";" + cursor.getString(3);
        }

        return ret;
    }

    public String[] preencheEventos(int dia) {
        SQLiteDatabase database = this.getReadableDatabase();

        String query = "SELECT * FROM programacao " +
                "WHERE datetime(dataHora) BETWEEN " +
                "datetime('2017-11-"+String.valueOf(dia)+" 06:00') AND datetime('2017-11-"+String.valueOf(dia+1)+" 06:00') " +
                "ORDER BY datetime(dataHora) ASC";

        Cursor cursor = database.rawQuery(query, null);
        int nroResults = cursor.getCount();
        int valorAtual=0;
        String[] retorno = new String[nroResults];

        if (cursor != null && cursor.moveToFirst()){
            do{
                retorno[valorAtual] = cursor.getString(0) + ";" + cursor.getString(1) + ";" + cursor.getString(2)+ ";" + cursor.getString(3);

                valorAtual++;
            } while (cursor.moveToNext());
        }

        return retorno;
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){

            Log.e("DEU-ruim DB", e.toString());

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    public void copyDataBase(boolean useAsset) throws IOException {

        //Open your local db as the input stream
        InputStream myInput;
        if (useAsset) {
            myInput = myContext.getAssets().open(DB_NAME);
        } else {
            myInput = new FileInputStream(new File(myContext.getCacheDir(), DB_NAME));
        }

        // Path to the just created empty db
        File outFileName = myContext.getDatabasePath(DB_NAME);

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(DB_PATH+DB_NAME);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
