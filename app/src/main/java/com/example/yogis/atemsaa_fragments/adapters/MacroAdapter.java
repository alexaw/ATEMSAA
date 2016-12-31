package com.example.yogis.atemsaa_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.Macro.Macro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogis on 30/12/2016.
 */
public class MacroAdapter extends RecyclerView.Adapter<MacroAdapter.MacroViewHolder>{

    private LayoutInflater inflater;
    private List<Macro> data;

    public MacroAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        data = new ArrayList<>();

    }

    @Override
    public MacroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.templete_macro, parent, false);
        MacroViewHolder viewHolder = new MacroViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MacroViewHolder holder, int position) {

        Macro macro = data.get(position);
        holder.idMacro.setText(macro.getIdMacro());
        holder.estado.setText(macro.getEstado());
        holder.tipo.setText(macro.getTipo());
        holder.modelo.setText(macro.getModelo());
        holder.marca.setText(macro.getMarca());
        holder.clase.setText(macro.getClase());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Macro> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class MacroViewHolder extends RecyclerView.ViewHolder {

        TextView idMacro,estado, tipo, modelo, marca, clase;

        public MacroViewHolder(View itemView) {
            super(itemView);

            idMacro = (TextView) itemView.findViewById(R.id.idMacro);
            estado = (TextView) itemView.findViewById(R.id.estadoMacro);
            tipo = (TextView) itemView.findViewById(R.id.tipoMacro);
            modelo = (TextView) itemView.findViewById(R.id.modeloMacro);
            marca = (TextView) itemView.findViewById(R.id.marcaMacro);
            clase = (TextView) itemView.findViewById(R.id.claseMacro);
        }
    }
}
