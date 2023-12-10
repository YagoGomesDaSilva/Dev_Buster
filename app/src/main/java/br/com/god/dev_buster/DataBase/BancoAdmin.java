package br.com.god.dev_buster.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoAdmin extends SQLiteOpenHelper {
    public BancoAdmin(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase banco) {
        banco.execSQL("CREATE TABLE PRODUTOS (ID INT PRIMARY KEY, NOME TEXT, DURATION INT, GENRE TEXT, IMAGE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase banco, int i, int i1) {

    }
}
