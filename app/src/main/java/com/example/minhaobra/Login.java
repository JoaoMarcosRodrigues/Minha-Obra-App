package com.example.minhaobra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.santalu.maskedittext.MaskEditText;

public class Login extends AppCompatActivity{

    //private static final String TAG = "tag";
    EditText editSenha;
    MaskEditText editUsuario;
    Button btnEntrar,btnCadastrar;
    TextView txtRedefinirSenha;
    CheckBox checkLembrar;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
        checkLembrar = findViewById(R.id.checkLembrar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // sharedPreferences = getSharedPreference("mydatabase",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        checkSharedPreferences();

        dbHelper = new DBHelper(this);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = editUsuario.getRawText();
                String senha = editSenha.getText().toString();
                Profissional profissional = new Profissional();

                if(checkLembrar.isChecked()){
                    editor.putString(getString(R.string.checkbox),"True");
                    editor.commit();

                    editor.putString(getString(R.string.cpf),usuario);
                    editor.commit();

                    editor.putString(getString(R.string.senha),senha);
                    editor.commit();
                }else{
                    editor.putString(getString(R.string.checkbox),"False");
                    editor.commit();

                    editor.putString(getString(R.string.cpf),"");
                    editor.commit();

                    editor.putString(getString(R.string.senha),"");
                    editor.commit();
                }

                if(usuario.length() == 0) {
                    editUsuario.setError("Campo obrigatório");
                }else if(senha.length() == 0){
                    editSenha.setError("Campo obrigatório");
                }else{
                    autenticaProfissional(usuario,senha);
                    /*
                    Toast.makeText(Login.this,"Seja Bem-Vindo!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);

                     */
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

    private void checkSharedPreferences() {
        String checkbox = sharedPreferences.getString(getString(R.string.checkbox),"False");
        String cpf = sharedPreferences.getString(getString(R.string.cpf),"");
        String senha = sharedPreferences.getString(getString(R.string.senha),"");

        editUsuario.setText(cpf);
        editSenha.setText(senha);

        if(checkbox.equals("True"))
             checkLembrar.setChecked(true);
        else
            checkLembrar.setChecked(false);
    }

    private void autenticaProfissional(String cpf, String senha) {
        Profissional p = new Profissional();
        p.setCpf(cpf);
        p.setSenha(senha);

        boolean ok = dbHelper.autenticaProfissional(p);

        if(ok == true){
            Toast.makeText(this,"Seja Bem-Vindo!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Usuário ou senha inválido! Tente novamente.",Toast.LENGTH_SHORT).show();
        }
    }
}