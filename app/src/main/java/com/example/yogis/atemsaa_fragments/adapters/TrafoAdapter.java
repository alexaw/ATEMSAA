package com.example.yogis.atemsaa_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.Trafo.Trafo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogis on 30/12/2016.
 */
public class TrafoAdapter extends RecyclerView.Adapter<TrafoAdapter.TrafoViewHolder>{

    private LayoutInflater inflater;
    private List<Trafo> data;

    public TrafoAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        data = new ArrayList<>();

    }

    @Override
    public TrafoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.templete_trafo, parent, false);
        TrafoViewHolder viewHolder = new TrafoViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrafoViewHolder holder, int position) {
        Trafo trafo = data.get(position);
        holder.idTrafo.setText(trafo.getIdTrafo());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Trafo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class TrafoViewHolder extends RecyclerView.ViewHolder {

        TextView idTrafo;

        public TrafoViewHolder(View itemView) {
            super(itemView);

            idTrafo = (TextView) itemView.findViewById(R.id.idTrafo);

        }
    }

}
