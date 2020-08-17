package com.example.minhaobra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.santalu.maskedittext.MaskEditText;

public class RedefinirSenha extends AppCompatActivity {

    EditText editSenha,editResenha;
    MaskEditText editCPF;
    Button btnRedefinir;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        editCPF = findViewById(R.id.editUsuario);
        editSenha = findViewById(R.id.editSenha);
        editResenha = findViewById(R.id.editResenha);
        btnRedefinir = findViewById(R.id.btnRedefinir);
        dbHelper = new DBHelper(this);

        btnRedefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senha = editSenha.getText().toString();
                String resenha = editResenha.getText().toString();
                String cpf = editCPF.getRawText();

                if(cpf.length() == 0){
                    editCPF.setError("Campo obrigatório");
                }else if(senha.length() == 0){
                    editSenha.setError("Campo obrigatório");
                }else if(!senha.equals(resenha)){
                    editResenha.setError("Senha não confere! Tente novamente");
                }else{
                    redefinirSenha(cpf,senha);
                }
            }
        });
    }

    private void redefinirSenha(String cpf, String senha) {
        boolean ok = dbHelper.redefinirSenha(cpf,senha);

        if(ok){
            Toast.makeText(this,"Senha alterada!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Houve um erro! Tente mais tarde.",Toast.LENGTH_SHORT).show();
        }
    }

}