package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private RequestQueue queue;
    private ProductoViewModel productoViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String URL_PRODUCTOS = "http://192.168.1.137:8080/app_m/mostrar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productoAdapter = new ProductoAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(productoAdapter);

        queue = Volley.newRequestQueue(this);
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);

        productoViewModel.getProductos().observe(this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                if (productos != null) {
                    productoAdapter.setProductos(productos);
                    productoAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }
        });

        productoViewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        productoViewModel.cargarProductos(queue);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            productoViewModel.cargarProductos(queue);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (productoList == null || productoList.isEmpty()) {
            productoViewModel.cargarProductos(queue);
        }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Producto productoActualizado = (Producto) data.getSerializableExtra("producto");

            if (productoActualizado != null) {
                productoViewModel.cargarProductos(queue);
            }
        }
    }

}