package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductoAdapter adapter;
    private List<Producto> listaProductos;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search_bar);
        Button btnAgregarProducto = findViewById(R.id.btnAgregarProducto);

        listaProductos = new ArrayList<>();
        listaProductos.add(new Producto("001", "Producto A", "SKU001", "100", "10", "Sub1", "Disponible"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductoAdapter(listaProductos);
        recyclerView.setAdapter(adapter);

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

        btnAgregarProducto.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormularioProductoActivity.class);
            startActivity(intent);
        });
    }
}
