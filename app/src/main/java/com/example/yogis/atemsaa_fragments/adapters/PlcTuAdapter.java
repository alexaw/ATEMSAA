package com.example.yogis.atemsaa_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.PlcTu.PlcTu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogis on 30/12/2016.
 */
public class PlcTuAdapter extends RecyclerView.Adapter<PlcTuAdapter.PlcTuViewHolder>{

    private LayoutInflater inflater;
    private List<PlcTu> data;

    public PlcTuAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        data = new ArrayList<>();

    }

    @Override
    public PlcTuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.templete_plctu, parent, false);
        PlcTuViewHolder viewHolder = new PlcTuViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlcTuViewHolder holder, int position) {
        PlcTu plcTu = data.get(position);
        holder.idPlcTu.setText(plcTu.getIdPlcTu());
        holder.macPlcTu.setText(plcTu.getMacPlcTu());
        holder.estado.setText(plcTu.getEstado());
        holder.gtx.setText(plcTu.getGtx());
        holder.grx.setText(plcTu.getGrx());
        holder.bps.setText(plcTu.getBps());
        holder.rtx.setText(plcTu.getRtx());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<PlcTu> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class PlcTuViewHolder extends RecyclerView.ViewHolder {

        TextView idPlcTu, macPlcTu, estado, gtx, grx, bps, rtx;

        public PlcTuViewHolder(View itemView) {
            super(itemView);

            idPlcTu = (TextView) itemView.findViewById(R.id.idPlcTu);
            macPlcTu = (TextView) itemView.findViewById(R.id.macPlcTu);
            estado = (TextView) itemView.findViewById(R.id.estadoPlcTu);
            gtx = (TextView) itemView.findViewById(R.id.gtxPlcTu);
            grx = (TextView) itemView.findViewById(R.id.grxPlcTu);
            bps = (TextView) itemView.findViewById(R.id.tasaPlcTu);
            rtx = (TextView) itemView.findViewById(R.id.retardoPlcTu);

        }
    }
}
