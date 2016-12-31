package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.Producto.Producto;
import com.example.yogis.atemsaa_fragments.net.api.Producto.ProductoApi;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductoBDFragment extends Fragment implements ProductoApi.OnProductoList {

    ProductoApi productoApi;
    MainActivity activity;
    OnChangeFragment changeFragment;


    public ProductoBDFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeFragment = (OnChangeFragment) context;
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vistaProducto = inflater.inflate(R.layout.fragment_producto_bd, container, false);

        productoApi = new ProductoApi(getActivity(), null);
        productoApi.getAll(this);

        return vistaProducto;
    }

    @Override
    public void onProductoList(List<Producto> data) {
        Log.i("tamanio producto", ""+data.size());

    }
}
