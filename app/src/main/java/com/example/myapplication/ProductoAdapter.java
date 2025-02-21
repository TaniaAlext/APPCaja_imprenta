package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private List<Producto> listaProductos;
    private List<Producto> listaProductosFull; // Copia completa de los datos

    public ProductoAdapter(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        this.listaProductosFull = new ArrayList<>(listaProductos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);
        holder.txtClave.setText(producto.getClave());
        holder.txtDescripcion.setText(producto.getDescripcion());
        holder.txtSku.setText(producto.getSku());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtClave, txtDescripcion, txtSku;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtClave = itemView.findViewById(R.id.txtClave);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtSku = itemView.findViewById(R.id.txtSku);
        }
    }

    // Método para filtrar la lista
    public void filtrar(String texto) {
        listaProductos.clear();
        if (texto.isEmpty()) {
            listaProductos.addAll(listaProductosFull);
        } else {
            texto = texto.toLowerCase();
            for (Producto producto : listaProductosFull) {
                if (producto.getClave().toLowerCase().contains(texto) ||
                        producto.getDescripcion().toLowerCase().contains(texto) ||
                        producto.getSku().toLowerCase().contains(texto)) {
                    listaProductos.add(producto);
                }
            }
        }
        notifyDataSetChanged();
    }
}