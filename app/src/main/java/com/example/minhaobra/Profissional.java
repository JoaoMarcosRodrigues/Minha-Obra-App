package com.example.minhaobra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Profissional {
    String nomeCompleto;
    String dataNascimento;
    String cpf;
    String telefone;
    String email;
    List<String> areas;
    String senha;
    String especialidade;
    String descricao;
    byte[] imagem;

    Bitmap avatar;
    String urlGravatar;
    boolean excluir;

    private Context context;

    public Profissional(Context context){
        this.context = context;
        cpf = "-1";
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
        if(this.imagem != null){
            this.avatar = Auxilio.getImagemBytes(imagem);
        }
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.urlGravatar = String.format("https://s.gravatar.com/avatar/%s?s=200",Auxilio.md5Hex(this.email));
    }

    public List<String> getAreas() {
        return areas;
    }

    public void setAreas(List<String> areas) {
        this.areas = areas;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getUrlGravatar() {
        return urlGravatar;
    }

    public void setUrlGravatar(String urlGravatar) {
        this.urlGravatar = urlGravatar;
    }

    public boolean isExcluir() {
        return excluir;
    }

    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    // CRUD
    public boolean excluir(){
        DBHelper dbHelper = null;
        SQLiteDatabase sqLiteDatabase = null;
        try{
            dbHelper = new DBHelper(context);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            sqLiteDatabase.delete("profissional","cpf = ?",new String[]{String.valueOf(cpf)});
            excluir = true;

            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.endTransaction();
            return false;
        }finally {
            if(sqLiteDatabase != null)
                sqLiteDatabase.close();
            if(dbHelper != null)
                dbHelper.close();
        }
    }

    public boolean salvar(){
        DBHelper dbHelper = null;
        SQLiteDatabase sqLiteDatabase = null;
        try{
            dbHelper = new DBHelper(context);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            String sql = "";
            if(Integer.parseInt(cpf) == 1){
                sql = "INSERT INTO profissional(cpf,nome,email,telefone,especialidade,data_nascimento,senha,imagem,descricao) VALUES(?,?,?,?,?,?,?,?,?)";
            }else{
                sql = "UPDATE profissional SET cpf = ?, nome = ?, email = ?, telefone = ?, especialidade = ?, data_nascimento = ?, senha = ?, imagem = ?, descricao = ? WHERE cpf = ?";
            }
            sqLiteDatabase.beginTransaction();
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindString(1,cpf);
            sqLiteStatement.bindString(2,nomeCompleto);
            sqLiteStatement.bindString(3,email);
            sqLiteStatement.bindString(4,telefone);
            sqLiteStatement.bindString(5,especialidade);
            sqLiteStatement.bindString(6,dataNascimento);
            sqLiteStatement.bindString(7,senha);
            if(imagem != null)
                sqLiteStatement.bindBlob(8,imagem);
            sqLiteStatement.bindString(9,descricao);

            sqLiteStatement.executeInsert();
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.endTransaction();
            return false;
        }finally {
            if(sqLiteDatabase != null)
                sqLiteDatabase.close();
            if(dbHelper != null)
                dbHelper.close();
        }
    }

    public ArrayList<Profissional> getProfissionais(){
        DBHelper dbHelper = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<Profissional> profissionais = new ArrayList<>();

        try{
            dbHelper = new DBHelper(context);
            sqLiteDatabase = dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query("profissional",null,null,null,null,null,null);

            while(cursor.moveToNext()){
                Profissional p = new Profissional(context);
                p.cpf = cursor.getString(cursor.getColumnIndex("cpf"));
                p.nomeCompleto = cursor.getString(cursor.getColumnIndex("nome"));
                p.email = cursor.getString(cursor.getColumnIndex("email"));
                p.telefone = cursor.getString(cursor.getColumnIndex("telefone"));
                p.especialidade = cursor.getString(cursor.getColumnIndex("especialidade"));
                p.dataNascimento = cursor.getString(cursor.getColumnIndex("data_nascimento"));
                p.descricao = cursor.getString(cursor.getColumnIndex("descricao"));
                p.senha = cursor.getString(cursor.getColumnIndex("senha"));
                p.imagem = cursor.getBlob(cursor.getColumnIndex("imagem"));
                profissionais.add(p);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
            if(sqLiteDatabase != null)
                sqLiteDatabase.close();
            if(dbHelper != null)
                dbHelper.close();
        }

        return profissionais;
    }

    public void carregaProfissionalCPF(String cpf){
        DBHelper dbHelper = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;

        try{
            dbHelper = new DBHelper(context);
            sqLiteDatabase = dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query("profissional",null,"cpf = ?",new String[]{String.valueOf(cpf)},null,null,null);
            excluir = true;
            while(cursor.moveToNext()){
                this.cpf = cursor.getString(cursor.getColumnIndex("cpf"));
                nomeCompleto = cursor.getString(cursor.getColumnIndex("nome"));
                setEmail(cursor.getString(cursor.getColumnIndex("email")));
                telefone = cursor.getString(cursor.getColumnIndex("telefone"));
                especialidade = cursor.getString(cursor.getColumnIndex("especialidade"));
                dataNascimento = cursor.getString(cursor.getColumnIndex("data_nascimento"));
                descricao = cursor.getString(cursor.getColumnIndex("descricao"));
                senha = cursor.getString(cursor.getColumnIndex("senha"));
                setImagem(cursor.getBlob(cursor.getColumnIndex("imagem")));
                excluir = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
            if(sqLiteDatabase != null)
                sqLiteDatabase.close();
            if(dbHelper != null)
                dbHelper.close();
        }
    }
}
