package com.example.minhaobra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{

    ImageView imageMenu;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavController navController;
    DBHelper dbHelper;
    TextView nomeCompleto,email;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // MENU
        drawerLayout = findViewById(R.id.drawerLayout);
        imageMenu = findViewById(R.id.imageMenu);
        navigationView = findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);

        nomeCompleto = headerView.findViewById(R.id.txtNome);
        email = headerView.findViewById(R.id.txtViewEmail);

        // INICIO NAV HEADER
        Intent intent = getIntent();
        String cpf = intent.getStringExtra("chave_cpf");
        //Toast.makeText(this,cpf,Toast.LENGTH_SHORT).show();

        //Profissional p = dbHelper.retornaProfissional(cpf);

        nomeCompleto.setText("João Marcos");
        email.setText("joao@gmail.com");
        // FIM NAV HEADER

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setItemIconTintList(null);

        navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView,navController);

        // FIM MENU

    }

    // USAR SEARCH VIEW EM UM FRAGMENTO
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
     */
}