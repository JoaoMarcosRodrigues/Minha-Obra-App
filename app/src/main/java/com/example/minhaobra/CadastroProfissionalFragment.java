package com.example.minhaobra;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CadastroProfissionalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroProfissionalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageButton imageButton;
    EditText editNome;
    MaskEditText editTelefone;
    MaskEditText editDataNascimento;
    EditText editEmail;
    MaskEditText editCpf;
    EditText editEspecialidade;
    Button btnCadastrar;

    // PARA PEGAR APENAS OS NÚMEROS SEM A MÁSCARA USA-SE getRawText()

    Profissional p;
    List<Profissional> listaProfissionais;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CadastroProfissionalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CadastroProfissionalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadastroProfissionalFragment newInstance(String param1, String param2) {
        CadastroProfissionalFragment fragment = new CadastroProfissionalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_profissional, container, false);;

        imageButton = view.findViewById(R.id.imagemPerfil);

        btnCadastrar = view.findViewById(R.id.btnCadastrar);

        editCpf = view.findViewById(R.id.editCPF);
        editDataNascimento = view.findViewById(R.id.editDataNascimento);
        editEmail = view.findViewById(R.id.editEmail);
        editNome = view.findViewById(R.id.editNomeCompleto);
        editEspecialidade = view.findViewById(R.id.editEspecialidade);
        editTelefone = view.findViewById(R.id.editTelefone);

        p = new Profissional();
        listaProfissionais = new ArrayList<Profissional>();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNome.length() == 0){
                    editNome.setError("Campo obrigatório!");
                }else if(editCpf.length() == 0){
                    editCpf.setError("Campo obrigatório!");
                }else if(editDataNascimento.length() == 0){
                    editDataNascimento.setError("Campo obrigatório!");
                }else if(editTelefone.length() == 0){
                    editTelefone.setError("Campo obrigatório!");
                }else if(editEmail.length() == 0){
                    editEmail.setError("Campo obrigatório!");
                }else if(editEspecialidade.length() == 0){
                    editEspecialidade.setError("Campo obrigatório!");
                }else{
                    cadastrarProfissional();
                    Toast.makeText(getContext(),"Profissional cadastrado!",Toast.LENGTH_SHORT).show();
                    limparCampos();
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void limparCampos() {
        editTelefone.setText("");
        editEspecialidade.setText("");
        editNome.setText("");
        editEmail.setText("");
        editDataNascimento.setText("");
        editCpf.setText("");
    }

    private void cadastrarProfissional() {
        p.nomeCompleto = editNome.getText().toString();
        p.cpf = editCpf.getRawText();
        p.dataNascimento = editDataNascimento.getRawText();
        p.email = editEmail.getText().toString();
        p.telefone = editTelefone.getRawText();
        p.especialidade = editEspecialidade.getText().toString();

        listaProfissionais.add(p);
    }
}