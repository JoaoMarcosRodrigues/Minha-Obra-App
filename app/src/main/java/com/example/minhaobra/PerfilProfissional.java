package com.example.minhaobra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilProfissional extends AppCompatActivity {

    ImageView imageView;
    TextView txtNome,txtEspecialidade;
    EditText editEmail,editTelefone;
    AutoCompleteTextView txtDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_profissional);

        imageView = findViewById(R.id.perfil);
        txtNome = findViewById(R.id.txtNome);
        txtEspecialidade = findViewById(R.id.txtEspecialidade);
        editEmail = findViewById(R.id.editEmail);
        editTelefone = findViewById(R.id.editTelefone);
        txtDescricao = findViewById(R.id.conteudoDescricao);

        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();

        if(parametros!=null){
            String nomeCompleto = parametros.getString("chave_nome");
            String especialidade = parametros.getString("chave_especialidade");
            String email = parametros.getString("chave_email");
            String telefone = parametros.getString("chave_telefone");
            String descricao = parametros.getString("chave_descricao");
            //Byte imagem = parametros.getByte("chave_imagem");

            //imageView.setImageBitmap();
            txtNome.setText(nomeCompleto);
            txtEspecialidade.setText("Especialidade: "+especialidade);
            editEmail.setText(email);
            editTelefone.setText(telefone);
            txtDescricao.setText(descricao);
        }
    }
}