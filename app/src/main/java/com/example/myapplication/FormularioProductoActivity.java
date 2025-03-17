package com.example.myapplication;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
public class FormularioProductoActivity extends AppCompatActivity {
    private TextView txtIdProd, txtDescripcion, txtSku, txtSku2, txtDescripAbreviada;
    private Button btnEditar, btnCancelar;
    private Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_producto);

        txtIdProd = findViewById(R.id.txtIdProd);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtSku = findViewById(R.id.txtSku);
        txtSku2 = findViewById(R.id.txtSku2);
        txtDescripAbreviada = findViewById(R.id.txtDescripAbreviada);
        btnEditar = findViewById(R.id.btnEditar);
        btnCancelar = findViewById(R.id.btnCancelar);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("producto")) {
            producto = (Producto) intent.getSerializableExtra("producto");

            txtIdProd.setText("ID: " + producto.getIdprod());
            txtDescripcion.setText("Descripción: " + producto.getDescripcion());
            txtSku.setText("SKU: " + producto.getSku1());
            txtSku2.setText("SKU2: "+ producto.getSku2());
            txtDescripAbreviada.setText("Descripción. Abreviada: " + producto.getDescripabreviada());
        }

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
            txtIdProd.setText("ID: " + data.getStringExtra("idprod"));
            txtDescripcion.setText("Descripción: " + data.getStringExtra("descripcion"));
            txtSku.setText("SKU: " + data.getStringExtra("sku1"));
            txtSku2.setText("SKU2: " + data.getStringExtra("sku2"));
            txtDescripAbreviada.setText("Descripción. Abreviada: " + data.getStringExtra("descripabreviada"));

            Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show();
        }
    }


}
