package com.example.myapplication;

public class Producto {
    private String clave;
    private String descripcion;
    private String sku;

    public Producto(String clave, String descripcion, String sku) {
        this.clave = clave;
        this.descripcion = descripcion;
        this.sku = sku;
    }

    public String getClave() {
        return clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSku() {
        return sku;
    }
}