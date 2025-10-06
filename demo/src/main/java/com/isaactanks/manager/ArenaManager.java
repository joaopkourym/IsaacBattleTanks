package com.isaactanks.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.isaactanks.model.Tanque;

public class ArenaManager {
    private List<Tanque> tanques;
    private static final int MAX_TANQUES = 12;
    
    public ArenaManager() {
        this.tanques = new ArrayList<>();
    }
    
    public boolean cadastrarTanque(Tanque tanque) {
        if (tanque == null) {
            System.err.println("Erro: Tanque nao pode ser nulo");
            return false;
        }
        
        try {
            if (tanques.size() >= MAX_TANQUES) {
                System.out.println("Limite maximo de " + MAX_TANQUES + " tanques atingido!");
                return false;
            }
            
            // Verificar se ja existe tanque com mesmo ID ou codinome
            for (Tanque t : tanques) {
                if (t.getId() == tanque.getId()) {
                    System.err.println("Erro: Ja existe um tanque com ID " + tanque.getId());
                    return false;
                }
                if (t.getCodinome().equalsIgnoreCase(tanque.getCodinome())) {
                    System.err.println("Erro: Ja existe um tanque com codinome '" + tanque.getCodinome() + "'");
                    return false;
                }
            }
            
            tanques.add(tanque);
            System.out.println("Tanque " + tanque.getCodinome() + " cadastrado com sucesso!");
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar tanque: " + e.getMessage());
            return false;
        }
    }
    
    public List<Tanque> getTanques() {
        return Collections.unmodifiableList(new ArrayList<>(tanques));
    }
    
    public Tanque buscarTanquePorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID deve ser positivo");
        }
        
        try {
            for (Tanque tanque : tanques) {
                if (tanque.getId() == id) {
                    return tanque;
                }
            }
            return null;
        } catch (Exception e) {
            System.err.println("Erro ao buscar tanque por ID: " + e.getMessage());
            return null;
        }
    }
    
    public boolean removerTanque(int id) {
        try {
            Tanque tanque = buscarTanquePorId(id);
            if (tanque != null) {
                tanques.remove(tanque);
                System.out.println("Tanque " + tanque.getCodinome() + " removido com sucesso!");
                return true;
            }
            System.out.println("Tanque com ID " + id + " nao encontrado");
            return false;
        } catch (Exception e) {
            System.err.println("Erro ao remover tanque: " + e.getMessage());
            return false;
        }
    }
    
    public int getQuantidadeTanques() {
        return tanques.size();
    }
    
    public boolean atingiuLimite() {
        return tanques.size() >= MAX_TANQUES;
    }
}