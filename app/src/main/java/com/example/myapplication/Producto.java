package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class Producto {
    private Long idprod;
    private String descripcion;
    private String sku;
    private String precio;
    private String cantidad;
    private String subfamilia;
    private String estatus;

    public Producto(String idprod, String descripcion, String sku, String precio, String cantidad, String subfamilia, String estatus) {
        this.idprod = Long.valueOf(idprod);
        this.descripcion = descripcion;
        this.sku = sku;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subfamilia = subfamilia;
        this.estatus = estatus;
    }

    public Long getIdprod() {
        return idprod;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSku() {
        return sku;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getSubfamilia() {
        return subfamilia;
    }

    public String getEstatus() {
        return estatus;
    }

}
