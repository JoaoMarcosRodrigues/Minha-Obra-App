package com.example.minhaobra;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import static android.app.Activity.RESULT_OK;

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
    EditText editSenha;
    EditText editResenha;
    EditText editDescricao;
    Button btnCadastrar;
    Button btnExcluir;

    // PARA PEGAR APENAS OS NÚMEROS SEM A MÁSCARA USA-SE getRawText()

    private final Profissional p = new Profissional(getContext());
    private Profissional profissionalEdicao;
    ArrayList<Profissional> profissionais;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

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

        imageButton = view.findViewById(R.id.fotoPerfil);

        btnCadastrar = view.findViewById(R.id.btnCadastrar);
        btnExcluir = view.findViewById(R.id.btnExcluir);

        editDescricao = view.findViewById(R.id.editDescricao);
        editCpf = view.findViewById(R.id.editCPF);
        editDataNascimento = view.findViewById(R.id.editDataNascimento);
        editEmail = view.findViewById(R.id.editEmail);
        editNome = view.findViewById(R.id.editNomeCompleto);
        editEspecialidade = view.findViewById(R.id.editEspecialidade);
        editTelefone = view.findViewById(R.id.editTelefone);
        editSenha = view.findViewById(R.id.editSenha);
        editResenha = view.findViewById(R.id.editResenha);

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
                }else if(editSenha.length() == 0){
                    editSenha.setError("Campo obrigatório!");
                }else if(editDescricao.length() == 0){
                    editDescricao.setError("Campo obrigatório!");
                }else if(!editSenha.getText().toString().equals(editResenha.getText().toString())) {
                    editResenha.setError("Senha não confere!");
                }else{
                    cadastrarProfissional();
                    Toast.makeText(getContext(),"Profissional cadastrado!",Toast.LENGTH_SHORT).show();
                    limparCampos();
                }
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.excluir();
                Toast.makeText(getContext(),"Profissional excluído!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),HomeFragment.class);
                profissionalEdicao = p;
                startActivity(intent);
            }
        });

        if(getActivity().getIntent().getExtras() != null){
            getActivity().setTitle(R.string.titulo_editando);
            String cpf = getActivity().getIntent().getExtras().getString("consulta");
            p.carregaProfissionalCPF(cpf);

            if(p.getAvatar() != null){
                imageButton.setImageBitmap(p.getAvatar());
            }
            editCpf.setText(p.getCpf().toString());
            editNome.setText(p.getNomeCompleto().toString());
            editEmail.setText(p.getEmail().toString());
            editTelefone.setText(p.getTelefone().toString());
            editEspecialidade.setText(p.getEspecialidade().toString());
            editDataNascimento.setText(p.getDataNascimento().toString());
            editDescricao.setText(p.getDescricao().toString());
            editSenha.setText(p.getSenha().toString());
            editResenha.setText(p.getSenha().toString());
        }else{
            getActivity().setTitle(R.string.titulo_inserindo);
        }

        btnExcluir.setEnabled(true);
        if(Integer.parseInt(p.getCpf()) == -1){
            btnExcluir.setEnabled(false);
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
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
                    Toast.makeText(getContext(),"Permissão negada!",Toast.LENGTH_SHORT).show();
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
    }

    private void cadastrarProfissional() {
        p.setCpf(editCpf.getRawText());
        p.setNomeCompleto(editNome.getText().toString());
        p.setEmail(editEmail.getText().toString());
        p.setTelefone(editTelefone.getRawText());
        p.setDataNascimento(editDataNascimento.getRawText());
        p.setEspecialidade(editEspecialidade.getText().toString());
        p.setDescricao(editDescricao.getText().toString());
        carregaImagem();

        p.salvar();
    }

    private void carregaImagem() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                p.setImagem(Auxilio.getImagemBytesFromUrl(p.getUrlGravatar()));
                imageButton.post(new Runnable() {
                    @Override
                    public void run() {
                        imageButton.setImageBitmap(p.getAvatar());
                    }
                });
            }
        });
        thread.start();
        try {
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}