package com.example.myapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiClient {
    public static String insertarUsuario(String nombre, String email, String telefono, String pass) {
        String urlString = "http://192.168.1.137:8080/app_m/insertar.php";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Datos a enviar
            String parametros = "nombre=" + URLEncoder.encode(nombre, "UTF-8") +
                    "&email=" + URLEncoder.encode(email, "UTF-8") +
                    "&telefono=" + URLEncoder.encode(telefono, "UTF-8") +
                    "&pass=" + URLEncoder.encode(pass, "UTF-8");

            // Enviar datos
            OutputStream os = conn.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();

            // Leer la respuesta
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                respuesta.append(linea);
            }
            reader.close();

            return respuesta.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al conectar con el servidor";
        }
    }
}
