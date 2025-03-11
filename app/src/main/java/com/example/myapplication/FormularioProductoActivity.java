package com.example.myapplication;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
public class FormularioProductoActivity extends AppCompatActivity {
    private TextView txtIdProd, txtDescripcion, txtSku, txtDescripAbreviada;
    private Button btnEditar, btnCancelar;
    private Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_producto);

        // Inicializar vistas
        txtIdProd = findViewById(R.id.txtIdProd);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtSku = findViewById(R.id.txtSku);
        txtDescripAbreviada = findViewById(R.id.txtDescripAbreviada);
        btnEditar = findViewById(R.id.btnEditar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Obtener datos enviados desde la lista
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("producto")) {
            producto = (Producto) intent.getSerializableExtra("producto");

            // Mostrar la información en los TextView
            txtIdProd.setText("ID: " + producto.getIdprod());
            txtDescripcion.setText("Descripción: " + producto.getDescripcion());
            txtSku.setText("SKU: " + producto.getSku1());
            txtDescripAbreviada.setText("Descripción. Abreviada: " + producto.getDescripabreviada());
        }

        // Botón Editar: Abrir nueva actividad para editar
        btnEditar.setOnClickListener(v -> {
            Intent intentEditar = new Intent(FormularioProductoActivity.this, EditarProductoActivity.class);
            intentEditar.putExtra("producto", producto);
            startActivityForResult(intentEditar, 1);
        });


        btnCancelar.setOnClickListener(v -> {
            Intent intentCancelar = new Intent(FormularioProductoActivity.this, MainActivity.class);
            intentCancelar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intentCancelar);
            finish();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Producto productoActualizado = (Producto) data.getSerializableExtra("producto");

            if (productoActualizado != null) {
                this.producto = productoActualizado;

                // Redirigir a la lista de productos
                Intent intentListaProductos = new Intent(FormularioProductoActivity.this, MainActivity.class);
                intentListaProductos.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentListaProductos);
                finish();
            }
        }
    }

}
