package com.example.yogis.atemsaa_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.PlcMc.PlcMc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogis on 30/12/2016.
 */
public class PlcMcAdapter extends RecyclerView.Adapter<PlcMcAdapter.PlcMcViewHolder>{

    private LayoutInflater inflater;
    private List<PlcMc> data;

    public PlcMcAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        data = new ArrayList<>();

    }

    @Override
    public PlcMcViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.templete_plcmc, parent, false);
        PlcMcViewHolder viewHolder = new PlcMcViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlcMcViewHolder holder, int position) {
        PlcMc plcMc = data.get(position);
        holder.idPlcMc.setText(plcMc.getIdPlcMc());
        holder.macPlcMc.setText(plcMc.getMacPlcMc());
        holder.estado.setText(plcMc.getEstado());
        holder.gtx.setText(plcMc.getGtx());
        holder.grx.setText(plcMc.getGrx());
        holder.bps.setText(plcMc.getBps());
        holder.rtx.setText(plcMc.getRtx());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<PlcMc> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class PlcMcViewHolder extends RecyclerView.ViewHolder {

        TextView idPlcMc, macPlcMc, estado, gtx, grx, bps, rtx;

        public PlcMcViewHolder(View itemView) {
            super(itemView);

            idPlcMc = (TextView) itemView.findViewById(R.id.idPlcMc);
            macPlcMc = (TextView) itemView.findViewById(R.id.macPlcMc);
            estado = (TextView) itemView.findViewById(R.id.estadoPlcMc);
            gtx = (TextView) itemView.findViewById(R.id.gtxPlcMc);
            grx = (TextView) itemView.findViewById(R.id.grxPlcMc);
            bps = (TextView) itemView.findViewById(R.id.tasaPlcMc);
            rtx = (TextView) itemView.findViewById(R.id.retardoPlcMc);
        }
    }
}
