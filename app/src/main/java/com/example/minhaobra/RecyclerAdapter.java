package com.example.minhaobra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    List<String> profissionaisList;
    List<String> profissionaisListAll;
    ArrayList<Profissional> profissionais;

    public RecyclerAdapter(List<String> profissionaisList) {
        this.profissionaisList = profissionaisList;
        this.profissionaisListAll = new ArrayList<>(profissionaisList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.linha,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nomeCompleto.setText(profissionaisList.get(position));
    }

    @Override
    public int getItemCount() {
        return profissionaisList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredList.addAll(profissionaisListAll);
            }else{
                for(String profissional : profissionaisListAll){
                    if(profissional.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(profissional);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }
        // run on ui thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            profissionaisList.clear();
            profissionaisList.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView nomeCompleto;
        TextView telefone;
        TextView email;
        TextView especialidade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagemPerfil);
            nomeCompleto = itemView.findViewById(R.id.txtNome);
            telefone = itemView.findViewById(R.id.txtTelefone);
            email = itemView.findViewById(R.id.txtEmail);
            especialidade = itemView.findViewById(R.id.txtEspecialidade);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    profissionaisList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),profissionaisList.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
        }
    }
}
