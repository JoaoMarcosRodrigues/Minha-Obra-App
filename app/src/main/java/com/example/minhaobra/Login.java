package com.example.minhaobra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.santalu.maskedittext.MaskEditText;

public class Login extends AppCompatActivity {

    EditText editSenha;
    MaskEditText editUsuario;
    Button btnEntrar,btnCadastrar;
    TextView txtRedefinirSenha;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsuario = findViewById(R.id.editUsuario);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrese);
        txtRedefinirSenha = findViewById(R.id.viewRedefinirSenha);

        dbHelper = new DBHelper(this);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = editUsuario.getRawText();
                String senha = editSenha.getText().toString();

                if(usuario.length() == 0) {
                    editUsuario.setError("Campo obrigatório");
                }else if(senha.length() == 0){
                    editSenha.setError("Campo obrigatório");
                }else{
                    //verificaCpf(usuario,senha);
                    Toast.makeText(Login.this,"Seja Bem-Vindo!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,CadastroProfissional.class);
                startActivity(intent);
            }
        });

        txtRedefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,RedefinirSenha.class);
                startActivity(intent);
            }
        });


    }

    private void verificaCpf(String cpf, String senha) {
        Profissional p = dbHelper.verificaCPF(cpf,senha);

        if(p.getCpf() == cpf && p.getSenha() == senha){
            Toast.makeText(this,"Seja Bem-Vindo!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,HomeFragment.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Usuário ou senha inválido! Tente novamente.",Toast.LENGTH_SHORT).show();
        }
    }
}