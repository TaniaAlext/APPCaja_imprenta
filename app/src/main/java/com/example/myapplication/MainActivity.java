package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductoAdapter adapter;
    private List<Producto> listaProductos;
    private SearchView searchView;
    private TextView txtConexion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        txtConexion = findViewById(R.id.txtConexion);

        // Verificar conexión con la base de datos
        new VerificarConexion().execute();

        // Lista de productos de ejemplo
        listaProductos = new ArrayList<>();
        listaProductos.add(new Producto("001", "Producto A", "SKU001"));
        listaProductos.add(new Producto("002", "Producto B", "SKU002"));
        listaProductos.add(new Producto("003", "Producto C", "SKU003"));
        listaProductos.add(new Producto("004", "Producto D", "SKU004"));

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductoAdapter(listaProductos);
        recyclerView.setAdapter(adapter);

        // Configurar búsqueda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filtrar(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filtrar(newText);
                return false;
            }
        });

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(view -> {
            new Thread(() -> {
                String respuesta = ApiClient.insertarUsuario("Juan Pérez", "juan@example.com", "123456789", "1234");
                runOnUiThread(() -> Toast.makeText(MainActivity.this, respuesta, Toast.LENGTH_SHORT).show());
            }).start();
        });
    }

    // Clase para verificar conexión con la base de datos
    private class VerificarConexion extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://192.168.1.137:8080/app_m/conexion.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                return result.toString();

            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            txtConexion.setText(result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}
