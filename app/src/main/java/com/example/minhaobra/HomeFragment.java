package com.example.minhaobra;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    ListView lista;
    ProfissionalAdapter profissionalAdapter;
    ArrayList<Profissional> profissionais;
    private Profissional profissionalEdicao;

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

        profissionais = new Profissional(getContext()).getProfissionais();
        profissionalAdapter = new ProfissionalAdapter(getContext(),profissionais);

        lista.setAdapter(profissionalAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Profissional p = profissionais.get(position);
                Intent intent = new Intent(getContext(),CadastroProfissionalFragment.class);
                intent.putExtra("consulta",p.getCpf());
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(profissionalEdicao!=null){
            profissionalEdicao.carregaProfissionalCPF(profissionalEdicao.getCpf());
            if(profissionalEdicao.isExcluir()){
                profissionais.remove(profissionalEdicao);
            }
            profissionalAdapter.notifyDataSetChanged();
        }
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