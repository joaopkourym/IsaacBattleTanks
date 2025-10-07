package com.isaactanks.manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.isaactanks.model.ModoBatalha;
import com.isaactanks.model.Partida;
import com.isaactanks.model.StatusPartida;
import com.isaactanks.model.Tanque;
import com.isaactanks.model.Terreno;

public class PartidaManager {
    private List<Partida> partidas;
    
    public PartidaManager() {
        this.partidas = new ArrayList<>();
    }
    
    public Partida agendarPartida(String nome, LocalDateTime dataHora, int duracaoMinutos,
                                 Terreno terreno, ModoBatalha modo) {
        Partida partida = new Partida(nome, dataHora, duracaoMinutos, terreno, modo);
        partidas.add(partida);
        return partida;
    }
    
    public List<Partida> getPartidasAgendadas() {
        return partidas.stream()
                .filter(p -> p.getStatus() == StatusPartida.AGENDADA)
                .collect(Collectors.toList());
    }
    
    public List<Partida> getPartidasPorStatus(StatusPartida status) {
        return partidas.stream()
                .filter(p -> p.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    public Partida buscarPartidaPorId(int id) {
        for (Partida partida : partidas) {
            if (partida.getId() == id) {
                return partida;
            }
        }
        return null;
    }
    
    public boolean adicionarTanqueAPartida(int partidaId, Tanque tanque, int time) {
        Partida partida = buscarPartidaPorId(partidaId);
        if (partida != null && partida.getStatus() == StatusPartida.AGENDADA) {
            return partida.adicionarTanque(tanque, time);
        }
        return false;
    }
    
    public boolean iniciarPartida(int partidaId) {
        Partida partida = buscarPartidaPorId(partidaId);
        if (partida != null && partida.getStatus() == StatusPartida.AGENDADA && partida.podeIniciar()) {
            partida.setStatus(StatusPartida.EM_ANDAMENTO);
            partida.aplicarEfeitosTerreno();
            return true;
        }
        return false;
    }
    
    public boolean concluirPartida(int partidaId) {
        Partida partida = buscarPartidaPorId(partidaId);
        if (partida != null && partida.getStatus() == StatusPartida.EM_ANDAMENTO) {
            partida.setStatus(StatusPartida.CONCLUIDA);
            partida.removerEfeitosTerreno();
            return true;
        }
        return false;
    }
    
    public List<Partida> getTodasPartidas() {
        return new ArrayList<>(partidas);
    }
}