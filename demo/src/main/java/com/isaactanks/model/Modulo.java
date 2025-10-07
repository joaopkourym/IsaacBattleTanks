package com.isaactanks.model;

public abstract class Modulo {
    protected String nome;
    protected double danoBase;
    protected double tempoRecarga;
    protected double alcance;
    protected String tipoMunicao;
    
    public Modulo(String nome, double danoBase, double tempoRecarga, double alcance, String tipoMunicao) {
        this.nome = nome;
        this.danoBase = danoBase;
        this.tempoRecarga = tempoRecarga;
        this.alcance = alcance;
        this.tipoMunicao = tipoMunicao;
    }
    
    public abstract double calcularDano(Tanque alvo, double distancia);
    
    public String getNome() { 
        return nome; 
    }

    public double getDanoBase() { 
        return danoBase; 
    }

    public double getTempoRecarga() { 
        return tempoRecarga; 
    }

    public double getAlcance() { 
        return alcance; 
    }
    public String getTipoMunicao() { 
        return tipoMunicao; 
    }
}