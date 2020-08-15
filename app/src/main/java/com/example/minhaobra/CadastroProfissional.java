package com.example.minhaobra;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.santalu.maskedittext.MaskEditText;

public class CadastroProfissional extends AppCompatActivity {

    ImageButton imageButton;
    EditText editNome;
    MaskEditText editTelefone;
    MaskEditText editDataNascimento;
    EditText editEmail;
    MaskEditText editCpf;
    EditText editEspecialidade;
    EditText editSenha;
    EditText editResenha;
    EditText editDescricao;
    Button btnCadastrar;

    DBHelper dbHelper;

    // PARA PEGAR APENAS OS NÚMEROS SEM A MÁSCARA USA-SE getRawText()

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_profissional);

        imageButton = findViewById(R.id.fotoPerfil);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        editDescricao = findViewById(R.id.editDescricao);
        editCpf = findViewById(R.id.editCPF);
        editDataNascimento = findViewById(R.id.editDataNascimento);
        editEmail = findViewById(R.id.editEmail);
        editNome = findViewById(R.id.editNomeCompleto);
        editEspecialidade = findViewById(R.id.editEspecialidade);
        editTelefone = findViewById(R.id.editTelefone);
        editSenha = findViewById(R.id.editSenha);
        editResenha = findViewById(R.id.editResenha);

        dbHelper = new DBHelper(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    /*
                    if(permissionCheck == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);
                    }else{

                     */
                    pegarImagemGaleria();
                } else{
                    pegarImagemGaleria();
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpf = editCpf.getRawText().toString();
                String nome = editNome.getText().toString();
                String telefone = editTelefone.getRawText().toString();
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                String especialidade = editEspecialidade.getText().toString();
                String data_nascimento = editDataNascimento.getRawText().toString();
                String descricao = editDescricao.getText().toString();

                if(nome.length() == 0){
                    editNome.setError("Campo obrigatório!");
                }else if(cpf.length() == 0){
                    editCpf.setError("Campo obrigatório!");
                }else if(data_nascimento.length() == 0){
                    editDataNascimento.setError("Campo obrigatório!");
                }else if(telefone.length() == 0){
                    editTelefone.setError("Campo obrigatório!");
                }else if(email.length() == 0){
                    editEmail.setError("Campo obrigatório!");
                }else if(especialidade.length() == 0){
                    editEspecialidade.setError("Campo obrigatório!");
                }else if(senha.length() == 0){
                    editSenha.setError("Campo obrigatório!");
                }else if(descricao.length() == 0){
                    editDescricao.setError("Campo obrigatório!");
                }else if(!editSenha.getText().toString().equals(editResenha.getText().toString())) {
                    editResenha.setError("Senha não confere!");
                }else{
                    // Verificar se o profissional já tem cadastro
                    addLista(cpf,nome,data_nascimento,telefone,email,especialidade,descricao,senha);
                    //limparCampos();
                    Intent intent = new Intent(CadastroProfissional.this,Login.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageButton.setImageURI(data.getData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pegarImagemGaleria();
                }else{
                    Toast.makeText(this,"Permissão negada!",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void pegarImagemGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    private void limparCampos() {
        editTelefone.setText("");
        editEspecialidade.setText("");
        editNome.setText("");
        editEmail.setText("");
        editDataNascimento.setText("");
        editCpf.setText("");
        editSenha.setText("");
        editResenha.setText("");
        editDescricao.setText("");
    }

    private void addLista(String cpf, String nome, String data_nascimento, String telefone, String email, String especialidade, String descricao, String senha) {
        boolean insert = dbHelper.addProfissional(cpf,nome,data_nascimento,telefone,email,especialidade,descricao,senha);

        if(insert==true){
            Toast.makeText(this,"Profissional cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Desculpe, houve um erro!",Toast.LENGTH_SHORT).show();
        }
    }
}