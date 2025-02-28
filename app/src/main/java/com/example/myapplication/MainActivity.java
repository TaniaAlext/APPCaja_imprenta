package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductoAdapter productoAdapter;
    private List<Producto> productoList;
    private ProgressBar progressBar;
    private static final String URL_PRODUCTOS = "http://192.168.1.137:8080/app_m/mostrar.php"; // Ajusta según tu IP o API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productoList = new ArrayList<>();
        productoAdapter = new ProductoAdapter(productoList);
        recyclerView.setAdapter(productoAdapter);

        obtenerProductos();
    }

    private void obtenerProductos() {
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_PRODUCTOS, null,
                new Response.Listener<org.json.JSONArray>() {
                    @Override
                    public void onResponse(org.json.JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("API_RESPONSE", response.toString());

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Producto>>() {}.getType();
                        List<Producto> productos = gson.fromJson(response.toString(), listType);

                        productoList.clear();
                        productoList.addAll(productos);
                        productoAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
                        Log.e("API_ERROR", "Error: " + error.toString());
                    }
                });

        queue.add(request);
    }
}
