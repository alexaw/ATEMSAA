package com.example.yogis.atemsaa_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.Medidor.Medidor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogis on 30/12/2016.
 */
public class MedidorAdapter extends RecyclerView.Adapter<MedidorAdapter.MedidorViewHolder> {

    private LayoutInflater inflater;
    private List<Medidor> data;

    public MedidorAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        data = new ArrayList<>();

    }

    @Override
    public MedidorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.templete_medidor, parent, false);
        MedidorViewHolder viewHolder = new MedidorViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MedidorViewHolder holder, int position) {
        Medidor medidor = data.get(position);
        holder.idMedidor.setText(medidor.getIdMedidor());
        holder.estado.setText(medidor.getEstado());
        holder.tipo.setText(medidor.getTipo());
        holder.marca.setText(medidor.getMarca());
        holder.modelo.setText(medidor.getModelo());
        holder.clase.setText(medidor.getClase());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Medidor> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class MedidorViewHolder extends RecyclerView.ViewHolder {

        TextView idMedidor, estado, tipo, marca, modelo, clase;

        public MedidorViewHolder(View itemView) {
            super(itemView);

            idMedidor = (TextView) itemView.findViewById(R.id.idMedidor);
            estado = (TextView) itemView.findViewById(R.id.estadoMedidor);
            tipo = (TextView) itemView.findViewById(R.id.tipoMedidor);
            marca = (TextView) itemView.findViewById(R.id.marcaMedidor);
            modelo = (TextView) itemView.findViewById(R.id.modeloMedidor);
            clase = (TextView) itemView.findViewById(R.id.claseMedidor);
        }
    }
}
