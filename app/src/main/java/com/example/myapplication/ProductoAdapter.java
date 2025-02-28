package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {
    private List<Producto> productos;

    public ProductoAdapter(List<Producto> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.idprod.setText("ID: " + producto.getIdprod());
        holder.descripcion.setText("Descripción: " + producto.getDescripcion());
        holder.sku1.setText("SKU: " + producto.getSku());
        holder.precio.setText("Precio: $" + producto.getPrecio());
        holder.cantidad.setText("Cantidad: " + producto.getCantidad());
        holder.subfamilia.setText("Subfamilia: " + producto.getSubfamilia());
        holder.estatus.setText("Estatus: " + producto.getEstatus());
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idprod, descripcion, sku1, precio, cantidad, subfamilia, estatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idprod = itemView.findViewById(R.id.idprod);
            descripcion = itemView.findViewById(R.id.descripcion);
            sku1 = itemView.findViewById(R.id.sku1);
            precio = itemView.findViewById(R.id.precio);
            cantidad = itemView.findViewById(R.id.cantidad);
            subfamilia = itemView.findViewById(R.id.subfamilia);
            estatus = itemView.findViewById(R.id.estatus);
        }
    }
}

