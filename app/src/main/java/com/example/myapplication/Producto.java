package com.example.myapplication;

import java.io.Serializable;
public class Producto implements Serializable {
    private Long idprod;
    private String descripcion;
    private String sku;
    private String sku2;
    private String descripabreviada;
    private String precio;
    private String cantidad;
    private String subfamilia;
    private String estatus;

    public Producto(String idprod, String descripcion, String sku, String sku2, String descripabreviada, String precio, String cantidad, String subfamilia, String estatus) {
        this.idprod = Long.valueOf(idprod);
        this.descripcion = descripcion;
        this.sku = sku;
        this.sku2 = sku2;
        this.descripabreviada = descripabreviada;
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

    public String getSku2() {
        return sku2;
    }

    public String getDescripabreviada() {
        return descripabreviada;
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
