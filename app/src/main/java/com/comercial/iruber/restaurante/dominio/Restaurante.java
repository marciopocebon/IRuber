package com.comercial.iruber.restaurante.dominio;

import java.util.ArrayList;

public class Restaurante {
    private long idRestaurante;
    private double nota;
    private Empresa empresa;



    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<Prato> pratos;
    private ArrayList<Entregador> entregadores;

    public long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public ArrayList<Prato> getPratos() {
        return pratos;
    }

    public void setPratos(ArrayList<Prato> pratos) {
        this.pratos = pratos;
    }

    public ArrayList<Entregador> getEntregadores() {
        return entregadores;
    }

    public void setEntregadores(ArrayList<Entregador> entregadores) {
        this.entregadores = entregadores;
    }
}