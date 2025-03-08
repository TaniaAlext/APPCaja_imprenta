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
    private Button btnEditar, btnAceptar, btnCancelar;
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
        btnAceptar = findViewById(R.id.btnAceptar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Obtener datos enviados desde la lista
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("producto")) {
            producto = (Producto) intent.getSerializableExtra("producto");

            // Mostrar la información en los TextView
            txtIdProd.setText("ID: " + producto.getIdprod());
            txtDescripcion.setText("Descripción: " + producto.getDescripcion());
            txtSku.setText("SKU: " + producto.getSku());
            txtDescripAbreviada.setText("Desc. Abreviada: " + producto.getDescripabreviada());
        }

        // Botón Editar: Abrir nueva actividad para editar
        btnEditar.setOnClickListener(v -> {
            Intent intentEditar = new Intent(FormularioProductoActivity.this, EditarProductoActivity.class);
            intentEditar.putExtra("producto", producto);
            startActivity(intentEditar);
        });

        btnCancelar.setOnClickListener(v -> {
            Intent intentCancelar = new Intent(FormularioProductoActivity.this, MainActivity.class);
            intentCancelar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intentCancelar);
            finish();
        });

    }
}
