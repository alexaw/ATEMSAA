package com.example.yogis.atemsaa_fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.Cliente.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogis on 23/12/2016.
 */
public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    private LayoutInflater inflater;
    private List<Cliente> data;

    public ClientAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        data = new ArrayList<>();

    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.templete_cliente, parent, false);
        ClientViewHolder viewHolder =new ClientViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {

        Cliente cliente = data.get(position);
        holder.cedula.setText(cliente.getCedula());
        holder.nombres.setText(cliente.getNombres());
        holder.apellidos.setText(cliente.getApellidos());
        holder.direccion.setText(cliente.getDireccion());
        holder.barrio.setText(cliente.getBarrio());
        holder.celular.setText(cliente.getCelular());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Cliente> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class ClientViewHolder extends RecyclerView.ViewHolder{

        TextView cedula, nombres, apellidos, direccion, barrio, estrato, email, celular;

        public ClientViewHolder(View itemView) {
            super(itemView);

            cedula = (TextView) itemView.findViewById(R.id.cedula);
            nombres = (TextView) itemView.findViewById(R.id.nombres);
            apellidos = (TextView) itemView.findViewById(R.id.apellidos);
            direccion = (TextView) itemView.findViewById(R.id.direccion);
            barrio = (TextView) itemView.findViewById(R.id.barrio);
            celular = (TextView) itemView.findViewById(R.id.celular);
        }
    }
}
