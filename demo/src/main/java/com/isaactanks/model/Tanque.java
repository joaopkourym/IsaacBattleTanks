package com.isaactanks.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tanque {
    private static int nextId = 1;
    
    private int id;
    private String codinome;
    private ClasseTanque classe;
    private double blindagem;
    private double velocidade;
    private double poderDeFogo;
    private TipoPiloto piloto;
    private double integridade;
    private List<Modulo> modulos;
    
    public Tanque(String codinome, ClasseTanque classe, TipoPiloto piloto) {
        if (codinome == null || codinome.trim().isEmpty()) {
            throw new IllegalArgumentException("Codinome nao pode ser vazio");
        }
        if (classe == null) {
            throw new IllegalArgumentException("Classe nao pode ser nula");
        }
        if (piloto == null) {
            throw new IllegalArgumentException("Piloto nao pode ser nulo");
        }
        
        this.id = nextId++;
        this.codinome = codinome.trim();
        this.classe = classe;
        this.piloto = piloto;
        this.integridade = 100.0;
        this.modulos = new ArrayList<>();
        
        configurarPorClasse();
        equiparModulos();
    }
    
    // Construtor simplificado para compatibilidade
    public Tanque(String codinome, ClasseTanque classe) {
        this(codinome, classe, TipoPiloto.HUMANO);
    }
    
    private void configurarPorClasse() {
        switch (classe) {
            case LEVE:
                this.blindagem = 30;
                this.velocidade = 80;
                this.poderDeFogo = 60;
                break;
            case MEDIO:
                this.blindagem = 60;
                this.velocidade = 50;
                this.poderDeFogo = 70;
                break;
            case PESADO:
                this.blindagem = 90;
                this.velocidade = 30;
                this.poderDeFogo = 85;
                break;
            default:
                throw new IllegalStateException("Classe de tanque desconhecida: " + classe);
        }
    }
    
    private void equiparModulos() {
        modulos.add(new Canhao());
        if (classe == ClasseTanque.LEVE) {
            modulos.add(new Metralhadora());
        }
    }
    
    public double atacar(Tanque alvo) {
        if (alvo == null) {
            throw new IllegalArgumentException("Alvo nao pode ser nulo");
        }
        
        if (this.integridade <= 0) {
            System.out.println("Tanque " + this.codinome + " esta destruido e nao pode atacar!");
            return 0;
        }
        
        if (alvo.getIntegridade() <= 0) {
            System.out.println("Tanque " + alvo.getCodinome() + " ja esta destruido!");
            return 0;
        }
        
        if (modulos.isEmpty()) {
            System.out.println("Tanque " + this.codinome + " nao possui modulos de ataque!");
            return 0;
        }
        
        try {
            Random rand = new Random();
            Modulo modulo = modulos.get(rand.nextInt(modulos.size()));
            double distancia = 30 + rand.nextDouble() * 70;
            
            double dano = modulo.calcularDano(alvo, distancia);
            alvo.receberDano(dano);
            
            return dano;
        } catch (Exception e) {
            System.err.println("Erro durante o ataque: " + e.getMessage());
            return 0;
        }
    }
    
    public void receberDano(double dano) {
        if (dano < 0) {
            throw new IllegalArgumentException("Dano nao pode ser negativo");
        }
        
        if (this.integridade <= 0) {
            return; // Tanque ja destruido
        }
        
        try {
            // Garantir que blindagem esta em range valido
            double blindagemEfetiva = Math.max(0, Math.min(blindagem, 100));
            double danoEfetivo = dano * (1 - (blindagemEfetiva / 200));
            
            this.integridade -= danoEfetivo;
            this.integridade = Math.max(0, this.integridade); // Nao deixar negativo
            
        } catch (Exception e) {
            System.err.println("Erro ao calcular dano: " + e.getMessage());
        }
    }
    
    public double calcularRecarga(Modulo modulo) {
        if (modulo == null) {
            throw new IllegalArgumentException("Modulo nao pode ser nulo");
        }
        
        try {
            double recargaBase = modulo.getTempoRecarga();
            switch (classe) {
                case LEVE: return Math.max(0.1, recargaBase * 0.8); // Minimo 0.1s
                case PESADO: return recargaBase * 1.3;
                default: return recargaBase;
            }
        } catch (Exception e) {
            System.err.println("Erro ao calcular recarga: " + e.getMessage());
            return modulo.getTempoRecarga(); // Valor padrao em caso de erro
        }
    }
    
    // Getters com validacao
    public int getId() { 
        if (id <= 0) {
            throw new IllegalStateException("ID do tanque invalido");
        }
        return id; 
    }
    
    public String getCodinome() { 
        if (codinome == null || codinome.trim().isEmpty()) {
            return "TanqueSemNome";
        }
        return codinome; 
    }
    
    public ClasseTanque getClasse() { 
        if (classe == null) {
            throw new IllegalStateException("Classe do tanque nao definida");
        }
        return classe; 
    }
    
    public double getBlindagem() { return Math.max(0, blindagem); }
    public double getVelocidade() { return Math.max(0, velocidade); }
    public double getPoderDeFogo() { return Math.max(0, poderDeFogo); }
    public TipoPiloto getPiloto() { return piloto != null ? piloto : TipoPiloto.HUMANO; }
    public double getIntegridade() { return Math.max(0, Math.min(integridade, 100)); }
    public List<Modulo> getModulos() { return new ArrayList<>(modulos); }
    
    public boolean estaOperacional() {
        return integridade > 0;
    }
    
    @Override
    public String toString() {
        String status = estaOperacional() ? 
            String.format("%.1f%%", integridade) : "DESTRUIDO";
        
        return String.format("Tanque #%d: %s [%s] - %s", 
                           id, codinome, classe, status);
    }
}