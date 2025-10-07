package com.isaactanks.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Partida {
    private static int nextId = 1;
    
    private int id;
    private String nome;
    private LocalDateTime dataHora;
    private int duracaoMinutos;
    private Terreno terreno;
    private ModoBatalha modo;
    private StatusPartida status;
    private List<Tanque> tanquesTime1;
    private List<Tanque> tanquesTime2;
    
    public Partida(String nome, LocalDateTime dataHora, int duracaoMinutos, 
                  Terreno terreno, ModoBatalha modo) {
        this.id = nextId++;
        this.nome = nome;
        this.dataHora = dataHora;
        this.duracaoMinutos = duracaoMinutos;
        this.terreno = terreno;
        this.modo = modo;
        this.status = StatusPartida.AGENDADA;
        this.tanquesTime1 = new ArrayList<>();
        this.tanquesTime2 = new ArrayList<>();
    }
    
    // Aplica efeitos do terreno nos tanques
    public void aplicarEfeitosTerreno() {
        for (Tanque tanque : getAllTanques()) {
            switch (terreno) {
                case DESERTO:
                    // Tanques ficam 20% mais lentos no deserto
                    tanque.setVelocidade(tanque.getVelocidade() * 0.8);
                    break;
                case URBANO:
                    // Tanques ficam 15% mais rápidos em ambiente urbano
                    tanque.setVelocidade(tanque.getVelocidade() * 1.15);
                    break;
                case CAMPO_ABERTO:
                    // Sem modificações
                    break;
            }
        }
    }
    
    // Remove efeitos do terreno (para não afetar permanentemente os tanques)
    public void removerEfeitosTerreno() {
        // Em uma implementação real, precisaríamos armazenar os valores originais
        // Para simplificar, vamos apenas resetar para os valores padrão da classe
        for (Tanque tanque : getAllTanques()) {
            tanque.configurarPorClasse(); // Reseta para valores originais
        }
    }
    
    // Verifica se a partida está pronta para iniciar
    public boolean podeIniciar() {
        int tanquesNecessarios = getTanquesNecessarios();
        return tanquesTime1.size() + tanquesTime2.size() >= tanquesNecessarios;
    }
    
    // Retorna número de tanques necessários baseado no modo
    private int getTanquesNecessarios() {
        switch (modo) {
            case TREINO: return 1;
            case UM_VS_UM: return 2;
            case TRES_VS_TRES: return 6;
            default: return 2;
        }
    }
    
    // Adiciona tanque ao time especificado
    public boolean adicionarTanque(Tanque tanque, int time) {
        if (time == 1 && tanquesTime1.size() < getMaxTanquesPorTime()) {
            tanquesTime1.add(tanque);
            return true;
        } else if (time == 2 && tanquesTime2.size() < getMaxTanquesPorTime()) {
            tanquesTime2.add(tanque);
            return true;
        }
        return false;
    }
    
    // Retorna máximo de tanques por time baseado no modo
    private int getMaxTanquesPorTime() {
        switch (modo) {
            case TREINO: return 1;
            case UM_VS_UM: return 1;
            case TRES_VS_TRES: return 3;
            default: return 1;
        }
    }
    
    // Getter para todos os tanques
    public List<Tanque> getAllTanques() {
        List<Tanque> todosTanques = new ArrayList<>();
        todosTanques.addAll(tanquesTime1);
        todosTanques.addAll(tanquesTime2);
        return todosTanques;
    }
    
    // Getters
    public int getId() { 
        return id; 
    }
    
    public String getNome() { 
        return nome; 
    }

    public LocalDateTime getDataHora() { 
        return dataHora; 
    }

    public int getDuracaoMinutos() { 
        return duracaoMinutos; 
    }

    public Terreno getTerreno() {
        return terreno; 
    }

    public ModoBatalha getModo() {
        return modo;
    }

    public StatusPartida getStatus() { 
        return status; 
    }
    
    public List<Tanque> getTanquesTime1() { 
        return tanquesTime1; 
    }

    public List<Tanque> getTanquesTime2() {
        return tanquesTime2; 
    }
    
    // Setters
    public void setStatus(StatusPartida status) { this.status = status; }
    
    @Override
    public String toString() {
        return String.format("Partida #%d: %s [%s] - %s - %d min - %s", 
                           id, nome, modo, terreno, duracaoMinutos, status);
    }
}