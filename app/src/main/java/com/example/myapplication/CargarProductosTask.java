package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

//public class CargarProductosTask extends AsyncTask<Void, Void, List<Producto>> {
//    private ProductoAdapter productoAdapter;
//    private int page;
//    private int pageSize;
//
//    public CargarProductosTask(ProductoAdapter productoAdapter, int page, int pageSize) {
//        this.productoAdapter = productoAdapter;
//        this.page = page;
//        this.pageSize = pageSize;
//    }
//
//    @Override
//    protected List<Producto> doInBackground(Void... voids) {
//        List<Producto> productos = ApiClient.obtenerProductos(page, pageSize);
//        if (productos == null) {
//            Log.e("API", "No se obtuvieron productos.");
//        } else {
//            Log.d("API", "Productos obtenidos: " + productos.size());
//
//        }
//        return productos;
//    }
//
//    @Override
//    protected void onPostExecute(List<Producto> productos) {
//        if (productos != null && !productos.isEmpty()) {
//            if (productoAdapter != null) {
//                productoAdapter.notifyDataSetChanged();
//            }
//        } else {
//            Log.e("API", "No se obtuvieron productos para mostrar.");
//        }
//    }
//
//}

