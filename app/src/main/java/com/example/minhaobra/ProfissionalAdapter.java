package com.example.minhaobra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ProfissionalAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<Profissional> elementos;


    public ProfissionalAdapter(Context context, ArrayList<Profissional> elementos){
        super(context,R.layout.linha,elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha,parent,false);

        TextView nome = rowView.findViewById(R.id.txtNome);
        TextView telefone = rowView.findViewById(R.id.txtTelefone);
        TextView email = rowView.findViewById(R.id.txtEmail);
        TextView especialidade = rowView.findViewById(R.id.txtEspecialidade);
        TextView areas = rowView.findViewById(R.id.txtAreas);
        TextView descricao = rowView.findViewById(R.id.txtDescricao);


        nome.setText(elementos.get(position).getNomeCompleto());
        email.setText(elementos.get(position).getEmail());
        telefone.setText(elementos.get(position).getTelefone());
        especialidade.setText(elementos.get(position).getEspecialidade());
        areas.setText("-");
        descricao.setText(elementos.get(position).getDescricao());

        return rowView;
    }
}
