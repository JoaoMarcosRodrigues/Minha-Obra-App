package com.example.minhaobra;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static String NOME = "sqliteappminhaobra.db";
    private static int VERSAO = 1;

    public DBHelper(Context context){
        super(context,NOME,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE [profissional] (\n" +
            "[cpf] VARCHAR(11)  UNIQUE NOT NULL PRIMARY KEY,\n" +
            "[nome] VARCHAR(100)  NOT NULL,\n" +
            "[email] VARCHAR(60)  UNIQUE NULL,\n" +
            "[telefone] varchar(11)  NULL,\n" +
            "[especialidade] varchar(50)  NULL,\n" +
            "[data_nascimento] DATE  NOT NULL,\n" +
            "[senha] varchar(60)  NOT NULL,\n" +
            "[imagem] BLOB  NULL,\n" +
            "[descricao] TEXT  NULL\n" +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
