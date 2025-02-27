package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private List<Producto> listaProductos;
    private List<Producto> listaProductosFull;

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
        holder.txtClave.setText("Clave: " + producto.getClave());
        holder.txtDescripcion.setText(producto.getDescripcion());
        holder.txtSku.setText("SKU: " + producto.getSku());
        holder.txtPrecio.setText("Precio: $" + producto.getPrecio());
        holder.txtCantidad.setText("Cantidad: " + producto.getCantidad());
        holder.txtSubfamilia.setText("Subfamilia: " + producto.getSubfamilia());
        holder.txtEstatus.setText("Estatus: " + producto.getEstatus());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtClave, txtDescripcion, txtSku, txtPrecio, txtCantidad, txtSubfamilia, txtEstatus;
        CardView cardViewProducto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewProducto = itemView.findViewById(R.id.cardViewProducto);
            txtClave = itemView.findViewById(R.id.txtClave);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtSku = itemView.findViewById(R.id.txtSku);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtSubfamilia = itemView.findViewById(R.id.txtSubfamilia);
            txtEstatus = itemView.findViewById(R.id.txtEstatus);
        }
    }

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
