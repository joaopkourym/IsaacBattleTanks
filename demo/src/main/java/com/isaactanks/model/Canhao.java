package com.isaactanks.model;

public class Canhao extends Modulo {
    public Canhao() {
        super("Canhão", 50, 3.0, 100, "PERFURANTE");
    }
    
    @Override
    public double calcularDano(Tanque alvo, double distancia) {
        double dano = danoBase;
        if (distancia > alcance) {
            dano *= 0.5;
        }
        switch (alvo.getClasse()) {
            case PESADO: dano *= 0.7; break;
            case MEDIO: dano *= 0.9; break;
            case LEVE: dano *= 1.2; break;
        }
        return dano;
    }
}