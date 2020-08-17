package com.example.minhaobra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static String NOME_BANCO = "sqliteappminhaobra.db";
    private static final String NOME_TABELA = "profissional";
    private static final String CPF = "cpf";
    private static final String NOME = "nome_completo";
    private static final String DATA_NASCIMENTO = "data_nascimento";
    private static final String TELEFONE = "telefone";
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";
    private static final String ESPECIALIDADE = "especialidade";
    private static final String DESCRICAO = "descricao";

    private static int VERSAO = 2;

    public DBHelper(Context context){
        super(context,NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ NOME_TABELA + " ("+
                CPF+" TEXT PRIMARY KEY," +
                NOME+" TEXT NOT NULL," +
                DATA_NASCIMENTO+" TEXT NOT NULL,"+
                TELEFONE+" TEXT NOT NULL,"+
                EMAIL+" TEXT NOT NULL,"+
                ESPECIALIDADE+" TEXT NOT NULL,"+
                DESCRICAO+" TEXT NOT NULL,"+
                SENHA+" TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+NOME_TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean addProfissional(String cpf, String nome, String data_nascimento, String telefone, String email, String especialidade, String descricao,String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CPF,cpf);
        contentValues.put(NOME,nome);
        contentValues.put(DATA_NASCIMENTO,data_nascimento);
        contentValues.put(TELEFONE,telefone);
        contentValues.put(EMAIL,email);
        contentValues.put(ESPECIALIDADE,especialidade);
        contentValues.put(DESCRICAO,descricao);
        contentValues.put(SENHA,senha);

        long result = db.insert(NOME_TABELA,null,contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllProfissionais(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+NOME_TABELA,null);

        return cursor;
    }

    // VERIFICAR SE ESTÁ CORRETO
    public boolean autenticaProfissional(Profissional p){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM profissional WHERE cpf = "+ "'" + p.getCpf() + "'";
        Cursor cursor = db.rawQuery(sql,null);

        while(cursor.moveToNext()){
            if(p.getCpf().equals(cursor.getString(cursor.getColumnIndex("cpf")))){
                if(p.getSenha().equals(cursor.getString(cursor.getColumnIndex("senha")))) {
                    return true;
                }
            }
        }
        return false;
    }

    // OK
    public boolean redefinirSenha(String cpf, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SENHA,senha);

        int result = db.update(NOME_TABELA,contentValues,"cpf = ?",new String[]{cpf});

        if(result == -1)
            return false;
        else
            return true;
    }
}

