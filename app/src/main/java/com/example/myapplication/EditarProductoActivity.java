package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditarProductoActivity extends AppCompatActivity {
    private EditText edtIdProd, edtDescripcion, edtSku, edtSku2, edtDescripAbreviada;
    private Button btnGuardar, btnCancelarEdicion;
    private Producto producto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        // Inicializar vistas
        edtIdProd = findViewById(R.id.edtIdProd);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtSku = findViewById(R.id.edtSku);
        edtSku2 = findViewById(R.id.edtSku2);
        edtDescripAbreviada = findViewById(R.id.edtDescripAbreviada);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelarEdicion = findViewById(R.id.btnCancelar);

        // Obtener datos enviados
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("producto")) {
            producto = (Producto) intent.getSerializableExtra("producto");

            if (producto != null) {
                edtIdProd.setText(String.valueOf(producto.getIdprod()));
                edtDescripcion.setText(producto.getDescripcion());
                edtSku.setText(producto.getSku());
                edtSku2.setText(producto.getSku2());
                edtDescripAbreviada.setText(producto.getDescripabreviada());
            }
        }

        // Configurar botón de cancelar
        btnCancelarEdicion.setOnClickListener(v -> {
            Intent intentCancelar = new Intent(EditarProductoActivity.this, FormularioProductoActivity.class);
            intentCancelar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intentCancelar);
            finish();
        });

    }
}
