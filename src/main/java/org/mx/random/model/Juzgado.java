/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.random.model;

/**
 *
 * @author Ricardo.Alvarez
 */
public class Juzgado {
    
    private Integer id;
    private String asunto;
    private String nombre;

    public Juzgado() {
    }

    public Juzgado(Integer id, String asunto, String nombre) {
        this.id = id;
        this.asunto = asunto;
        this.nombre = nombre;
    }   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
