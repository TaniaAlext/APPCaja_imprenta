package com.example.myapplication;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditarProductoActivity extends AppCompatActivity {
    private EditText edtIdProd, edtDescripcion, edtSku1, edtSku2, edtDescripAbreviada;
    private Button btnGuardar, btnCancelarEdicion;
    private Producto producto;
    private ProgressBar progressBar;

    private static final String URL_PRODUCTOS = "http://192.168.1.137:8080/app_m/editar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        // Inicializar vistas
        edtIdProd = findViewById(R.id.edtIdProd);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtSku1 = findViewById(R.id.edtSku1);
        edtSku2 = findViewById(R.id.edtSku2);
        edtDescripAbreviada = findViewById(R.id.edtDescripAbreviada);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelarEdicion = findViewById(R.id.btnCancelar);
        progressBar = findViewById(R.id.progressBar);

        // Obtener datos enviados
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("producto")) {
            producto = (Producto) intent.getSerializableExtra("producto");

            if (producto != null) {
                edtIdProd.setText(String.valueOf(producto.getIdprod()));
                edtDescripcion.setText(producto.getDescripcion());
                edtSku1.setText(producto.getSku1());
                edtSku2.setText(producto.getSku2());
                edtDescripAbreviada.setText(producto.getDescripabreviada());
            }
        }

        // Configurar botón de guardar
        btnGuardar.setOnClickListener(v -> actualizarProducto());

        // Configurar botón de cancelar
        btnCancelarEdicion.setOnClickListener(v -> {
            Intent intentCancelar = new Intent(EditarProductoActivity.this, FormularioProductoActivity.class);
            intentCancelar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intentCancelar);
            finish();
        });

    }

    private void actualizarProducto() {
        String idProd = edtIdProd.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
        String sku1 = edtSku1.getText().toString();
        String sku2 = edtSku2.getText().toString();
        String descripAbreviada = edtDescripAbreviada.getText().toString();

        if (idProd.isEmpty() || descripcion.isEmpty() || sku1.isEmpty() || sku2.isEmpty() || descripAbreviada.isEmpty()) {
            Toast.makeText(EditarProductoActivity.this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar el ProgressBar mientras se realiza la solicitud
        progressBar.setVisibility(View.VISIBLE);

        // Crear una solicitud POST usando Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_PRODUCTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("API_RESPONSE", response);

                        if (response.contains("error")) {
                            Toast.makeText(EditarProductoActivity.this, "Error al actualizar producto.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditarProductoActivity.this, "Producto actualizado con éxito.", Toast.LENGTH_SHORT).show();

                            producto.setDescripcion(edtDescripcion.getText().toString());
                            producto.setSku1(edtSku1.getText().toString());
                            producto.setSku2(edtSku2.getText().toString());
                            producto.setDescripabreviada(edtDescripAbreviada.getText().toString());

                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("producto", producto);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditarProductoActivity.this, "Error de conexión.", Toast.LENGTH_SHORT).show();
                        Log.e("API_ERROR", "Error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Crear el mapa de parámetros para el POST
                Map<String, String> params = new HashMap<>();
                params.put("idprod", idProd);
                params.put("descripcion", descripcion);
                params.put("sku1", sku1);
                params.put("sku2", sku2);
                params.put("descripabreviada", descripAbreviada);
                return params;
            }
        };

        queue.add(request);
    }

}
