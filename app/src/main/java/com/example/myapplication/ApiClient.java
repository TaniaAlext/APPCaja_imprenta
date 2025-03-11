package com.example.myapplication;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.137:8080/app_m/";

    public static ArrayList<Producto> obtenerProductos(int page, int pageSize) {
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            // URL con parámetros de paginación
            String urlString = BASE_URL + "mostrar.php?page=" + page + "&pageSize=" + pageSize;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                respuesta.append(linea);
            }
            reader.close();

            Log.d("API", "Respuesta JSON: " + respuesta.toString());

            // Convertir respuesta JSON en una lista de objetos Producto
            JSONArray jsonArray = new JSONArray(respuesta.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Producto producto = new Producto(
                        obj.getString("idprod"),
                        obj.getString("descripcion"),
                        obj.getString("sku1"),
                        obj.getString("sku2"),
                        obj.getString("descripabreviada"),
                        obj.getString("precio"),
                        obj.getString("cantidad"),
                        obj.getString("subfamilia"),
                        obj.getString("estatus")
                );
                productos.add(producto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }
}