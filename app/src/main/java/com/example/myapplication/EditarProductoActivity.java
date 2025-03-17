package com.example.myapplication;
import android.annotation.SuppressLint;
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
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Map;

public class EditarProductoActivity extends AppCompatActivity {
    private EditText edtIdProd, edtDescripcion, edtSku1, edtSku2, edtDescripAbreviada;
    private Button btnGuardar, btnCancelarEdicion;
    private Producto producto;
    private ProgressBar progressBar;
    private static final String URL_PRODUCTOS = "http://192.168.1.137:8080/app_m/editar.php";

    private Button btnEscanearSku2;
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    edtSku2.setText(result.getContents());
                }
            }
    );

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        edtIdProd = findViewById(R.id.edtIdProd);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtSku1 = findViewById(R.id.edtSku1);
        edtSku2 = findViewById(R.id.edtSku2);
        edtDescripAbreviada = findViewById(R.id.edtDescripAbreviada);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelarEdicion = findViewById(R.id.btnCancelar);
        btnEscanearSku2 = findViewById(R.id.btnEscanearSku2);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("producto")) {
            Producto producto = (Producto) intent.getSerializableExtra("producto");

            if (producto != null) {
                edtIdProd.setText(String.valueOf(producto.getIdprod()));
                edtDescripcion.setText(producto.getDescripcion());
                edtSku1.setText(producto.getSku1());
                edtSku2.setText(producto.getSku2());
                edtDescripAbreviada.setText(producto.getDescripabreviada());
            }
        }

        btnGuardar.setOnClickListener(v -> actualizarProducto());
        btnCancelarEdicion.setOnClickListener(v -> finish());
        btnEscanearSku2.setOnClickListener(v -> iniciarEscaneo());
    }

    private void iniciarEscaneo() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Escanea el código de barras");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setCaptureActivity(CaptureActivity.class);
        barcodeLauncher.launch(options);
    }

    private void actualizarProducto() {
        String idProd = edtIdProd.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
        String sku1 = edtSku1.getText().toString();
        String sku2 = edtSku2.getText().toString();
        String descripAbreviada = edtDescripAbreviada.getText().toString();

        if (idProd.isEmpty() || descripcion.isEmpty() || sku1.isEmpty() || sku2.isEmpty() || descripAbreviada.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_PRODUCTOS,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    Log.d("API_RESPONSE", response);

                    if (response.contains("error")) {
                        Toast.makeText(this, "Error al actualizar producto.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Producto actualizado con éxito.", Toast.LENGTH_SHORT).show();

                        // Enviar datos actualizados de vuelta
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("idprod", idProd);
                        resultIntent.putExtra("descripcion", descripcion);
                        resultIntent.putExtra("sku1", sku1);
                        resultIntent.putExtra("sku2", sku2);
                        resultIntent.putExtra("descripabreviada", descripAbreviada);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error de conexión.", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Error: " + error.toString());
                }) {
            @Override
            protected Map<String, String> getParams() {
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
