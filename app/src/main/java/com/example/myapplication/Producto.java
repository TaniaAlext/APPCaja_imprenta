package com.example.myapplication;

public class Producto {
    private String clave;
    private String descripcion;
    private String sku;
    private String precio;
    private String cantidad;
    private String subfamilia;
    private String estatus;

    public Producto(String clave, String descripcion, String sku, String precio, String cantidad, String subfamilia, String estatus) {
        this.clave = clave;
        this.descripcion = descripcion;
        this.sku = sku;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subfamilia = subfamilia;
        this.estatus = estatus;
    }

    public String getClave() { return clave; }
    public String getDescripcion() { return descripcion; }
    public String getSku() { return sku; }
    public String getPrecio() { return precio; }
    public String getCantidad() { return cantidad; }
    public String getSubfamilia() { return subfamilia; }
    public String getEstatus() { return estatus; }
}
