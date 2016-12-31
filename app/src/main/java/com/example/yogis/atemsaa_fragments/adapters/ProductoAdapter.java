package com.example.yogis.atemsaa_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.Producto.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogis on 30/12/2016.
 */
public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>{

    private LayoutInflater inflater;
    private List<Producto> data;

    public ProductoAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        data = new ArrayList<>();

    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.templete_producto, parent, false);
        ProductoViewHolder viewHolder = new ProductoViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder holder, int position) {
        Producto producto = data.get(position);
        holder.idProducto.setText(producto.getIdProducto());
        holder.estado.setText(producto.getEstado());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Producto> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {

        TextView idProducto, estado;

        public ProductoViewHolder(View itemView) {
            super(itemView);

            idProducto = (TextView) itemView.findViewById(R.id.idProducto);
            estado = (TextView) itemView.findViewById(R.id.estadoProducto);
        }
    }
}
