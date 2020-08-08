package com.example.minhaobra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ProfissionalAdapter extends ArrayAdapter<Profissional> {
    private ArrayList<Profissional> profissionais;

    public ProfissionalAdapter(@NonNull Context context, @NonNull ArrayList<Profissional> profissionais) {
        super(context, 0, profissionais);
        this.profissionais = profissionais;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Profissional profissional = profissionais.get(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.linha,null);

        ImageButton imageButton = convertView.findViewById(R.id.imagemPerfil);
        TextView txtNome = convertView.findViewById(R.id.txtNome);
        TextView txtTelefone = convertView.findViewById(R.id.txtTelefone);
        TextView txtEmail = convertView.findViewById(R.id.txtEmail);
        TextView txtEspecialidade = convertView.findViewById(R.id.txtEspecialidade);
        TextView txtDescricao = convertView.findViewById(R.id.txtDescricao);
        TextView txtArea = convertView.findViewById(R.id.txtAreas);

        txtNome.setText(profissional.getNomeCompleto().toString());
        txtTelefone.setText(profissional.getTelefone().toString());
        txtEmail.setText(profissional.getEmail().toString());
        txtEspecialidade.setText(profissional.getEspecialidade().toString());
        txtDescricao.setText(profissional.getDescricao().toString());
        txtArea.setText(profissional.getAreas().toString());
        if(profissional.getAvatar() != null){
            imageButton.setImageBitmap(profissional.getAvatar());
        }

        return convertView;
    }
}
