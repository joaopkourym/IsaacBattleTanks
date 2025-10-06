package com.isaactanks.model;

public class Metralhadora extends Modulo {
    public Metralhadora() {
        super("Metralhadora", 15, 0.5, 50, "FRAGMENTACAO");
    }
    
    @Override
    public double calcularDano(Tanque alvo, double distancia) {
        double dano = danoBase;
        if (distancia > alcance * 0.5) {
            dano *= 0.3;
        }
        if (alvo.getClasse() == ClasseTanque.LEVE) {
            dano *= 1.3;
        }
        return dano;
    }
}