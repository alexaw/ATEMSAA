package com.example.yogis.atemsaa_fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.PlcMms.PlcMms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogis on 23/12/2016.
 */
public class PlcMmsAdapter extends RecyclerView.Adapter<PlcMmsAdapter.PlcMmsViewHolder>{

    private LayoutInflater inflater;
    private List<PlcMms> data;

    public PlcMmsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        data = new ArrayList<>();
    }

    @Override
    public PlcMmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.templete_plcmms, parent, false);
        PlcMmsViewHolder viewHolder = new PlcMmsViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlcMmsViewHolder holder, int position) {
        PlcMms plcMms = data.get(position);
        holder.idPlcMms.setText(plcMms.getIdPlcMms());
        holder.macPlcMms.setText(plcMms.getMacPlcMms());
        holder.estado.setText(plcMms.getEstado());
        holder.gtx.setText(plcMms.getGtx());
        holder.grx.setText(plcMms.getGrx());
        holder.bps.setText(plcMms.getBps());
        holder.rtx.setText(plcMms.getRtx());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<PlcMms> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class PlcMmsViewHolder extends RecyclerView.ViewHolder{

        TextView idPlcMms, macPlcMms, versionFw, estado, gtx, grx, bps,rtx, numeroCelular;;

        public PlcMmsViewHolder(View itemView) {
            super(itemView);

            idPlcMms = (TextView) itemView.findViewById(R.id.idPlcMms);
            macPlcMms = (TextView) itemView.findViewById(R.id.macPlcMms);
            estado = (TextView) itemView.findViewById(R.id.estadoPlcMms);
            gtx = (TextView) itemView.findViewById(R.id.gtxPlcMms);
            grx = (TextView) itemView.findViewById(R.id.grxPlcMms);
            bps = (TextView) itemView.findViewById(R.id.tasaPlcMms);
            rtx = (TextView) itemView.findViewById(R.id.retardoPlcMms);


        }
    }
}
