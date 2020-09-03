package com.example.minhaobra;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    ListView lista;
    ProfissionalAdapter profissionalAdapter;
    ArrayList<Profissional> profissionais;
    private Profissional profissionalEdicao;

    DBHelper dbHelper;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lista = view.findViewById(R.id.listView);
        dbHelper = new DBHelper(getActivity());

        final ArrayList<Profissional> listaProfissionais = new ArrayList<Profissional>();
        Cursor data = dbHelper.getAllProfissionais();

        if(data.getCount() == 0){
            Toast.makeText(getActivity(),"A lista está vazia :(",Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                Profissional p = new Profissional();
                p.setCpf(data.getString(0));
                p.setNomeCompleto(data.getString(1));
                p.setDataNascimento(data.getString(2));
                p.setTelefone(data.getString(3));
                p.setEmail(data.getString(4));
                p.setEspecialidade(data.getString(5));
                p.setDescricao(data.getString(6));
                p.setSenha(data.getString(7));

                listaProfissionais.add(p);
                ListAdapter listAdapter = new ProfissionalAdapter(getActivity(),listaProfissionais);
                lista.setAdapter(listAdapter);
            }
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nomeCompleto = listaProfissionais.get(i).getNomeCompleto();
                String especialidade = listaProfissionais.get(i).getEspecialidade();
                String email = listaProfissionais.get(i).getEmail();
                String telefone = listaProfissionais.get(i).getTelefone();
                String descricao = listaProfissionais.get(i).getDescricao();

                Intent intent = new Intent(getActivity(),PerfilProfissional.class);
                Bundle parametros = new Bundle();

                parametros.putString("chave_nome",nomeCompleto);
                parametros.putString("chave_especialidade",especialidade);
                parametros.putString("chave_email",email);
                parametros.putString("chave_telefone",telefone);
                parametros.putString("chave_descricao",descricao);

                intent.putExtras(parametros);
                startActivity(intent);

                //Toast.makeText(getActivity(),"Nome: "+ listaProfissionais.get(i).getNomeCompleto(),Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}