package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private Context context;
    private List<Producto> productos;

    public ProductoAdapter(Context context, List<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);

        holder.txtIdProd.setText("ID: " + producto.getIdprod());
        holder.txtDescripcion.setText("Descripción: " + producto.getDescripcion());
        holder.txtSku.setText("SKU: " + producto.getSku());
        holder.txtSku2.setText("SKU2: " + producto.getSku2());
        holder.txtPrecio.setText("Precio: " + producto.getPrecio());
        holder.txtCantidad.setText("Cantidad: " + producto.getCantidad());
        holder.txtEstatus.setText("Estatus: " + producto.getEstatus());
        holder.txtSubfamilia.setText("Subfamilia: " + producto.getSubfamilia());

        // Evento de clic para abrir el formulario
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FormularioProductoActivity.class);
            intent.putExtra("producto", producto);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView txtIdProd, txtDescripcion, txtSku, txtSku2, txtPrecio, txtCantidad, txtEstatus, txtSubfamilia;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdProd = itemView.findViewById(R.id.idprod);
            txtDescripcion = itemView.findViewById(R.id.descripcion);
            txtSku = itemView.findViewById(R.id.sku1);
            txtSku2 = itemView.findViewById(R.id.sku2);
            txtPrecio = itemView.findViewById(R.id.precio);
            txtCantidad = itemView.findViewById(R.id.cantidad);
            txtEstatus = itemView.findViewById(R.id.estatus);
            txtSubfamilia = itemView.findViewById(R.id.subfamilia);
        }
    }
}
