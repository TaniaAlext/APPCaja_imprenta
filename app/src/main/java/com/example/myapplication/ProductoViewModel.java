package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class ProductoViewModel extends ViewModel {
    private MutableLiveData<List<Producto>> productosLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<List<Producto>> getProductos() {
        return productosLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void cargarProductos(RequestQueue queue) {
        String URL_PRODUCTOS = "http://192.168.1.137:8080/app_m/mostrar.php";
        isLoading.setValue(true);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_PRODUCTOS, null,
                new Response.Listener<org.json.JSONArray>() {
                    @Override
                    public void onResponse(org.json.JSONArray response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Producto>>() {}.getType();
                        List<Producto> productos = gson.fromJson(response.toString(), listType);
                        productosLiveData.setValue(productos);
                        isLoading.setValue(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        isLoading.setValue(false);
                    }
                });

        queue.add(request);
    }
}

