package com.isaactanks;

import java.util.List;
import java.util.Scanner;

import com.isaactanks.manager.ArenaManager;
import com.isaactanks.model.ClasseTanque;
import com.isaactanks.model.Tanque;
import com.isaactanks.model.TipoPiloto;
import com.isaactanks.util.CSVHandler;

public class Main {
    private static ArenaManager arenaManager = new ArenaManager();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== ARENA ISAAC TANKS ===");
        
        try {
            arenaManager.cadastrarTanque(new Tanque("Furia", ClasseTanque.LEVE));
            arenaManager.cadastrarTanque(new Tanque("Tita", ClasseTanque.PESADO));
            arenaManager.cadastrarTanque(new Tanque("Guardiao", ClasseTanque.MEDIO));
        } catch (Exception e) {
            System.err.println("Erro ao inicializar tanques de exemplo: " + e.getMessage());
        }
        
        boolean running = true;
        while (running) {
            try {
                exibirMenu();
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println("Por favor, digite uma opcao.");
                    continue;
                }
                
                int opcao = Integer.parseInt(input);
                
                switch (opcao) {
                    case 1: cadastrarTanque(); break;
                    case 2: listarTanques(); break;
                    case 3: simularBatalha(); break;
                    case 4: exportarDados(); break;
                    case 5: importarDados(); break;
                    case 0: 
                        running = false; 
                        System.out.println("Saindo... Obrigado por jogar!");
                        break;
                    default: 
                        System.out.println("Opcao invalida! Use numeros de 0 a 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite apenas numeros!");
            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
            }
        }
        
        scanner.close();
        System.out.println("Sistema encerrado com sucesso!");
    }
    
    private static void exibirMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("          MENU PRINCIPAL");
        System.out.println("=".repeat(40));
        System.out.println("1. Cadastrar Tanque");
        System.out.println("2. Listar Tanques");
        System.out.println("3. Simular Batalha");
        System.out.println("4. Exportar Dados");
        System.out.println("5. Importar Dados");
        System.out.println("0. Sair");
        System.out.println("-".repeat(40));
        System.out.print("Sua escolha: ");
    }
    
    private static void cadastrarTanque() {
        System.out.println("\n--- CADASTRO DE TANQUE ---");
        
        try {
            System.out.print("Codinome: ");
            String codinome = scanner.nextLine().trim();
            
            if (codinome.isEmpty()) {
                System.out.println("Erro: Codinome nao pode estar vazio!");
                return;
            }
            
            if (codinome.length() > 20) {
                System.out.println("Erro: Codinome muito longo (max. 20 caracteres)!");
                return;
            }
            
            System.out.print("Classe (LEVE/MEDIO/PESADO): ");
            String classeStr = scanner.nextLine().trim().toUpperCase();
            
            ClasseTanque classe;
            try {
                classe = ClasseTanque.valueOf(classeStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Classe invalida! Use: LEVE, MEDIO ou PESADO");
                return;
            }
            
            System.out.print("Piloto (HUMANO/IA): ");
            String pilotoStr = scanner.nextLine().trim().toUpperCase();
            
            TipoPiloto piloto;
            try {
                piloto = TipoPiloto.valueOf(pilotoStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: Tipo de piloto invalido! Use: HUMANO ou IA");
                return;
            }
            
            Tanque tanque = new Tanque(codinome, classe, piloto);
            boolean sucesso = arenaManager.cadastrarTanque(tanque);
            
            if (sucesso) {
                System.out.println("Tanque cadastrado com sucesso! ID: " + tanque.getId());
            }
            
        } catch (Exception e) {
            System.err.println("Erro inesperado no cadastro: " + e.getMessage());
        }
    }
    
    private static void listarTanques() {
        try {
            List<Tanque> tanques = arenaManager.getTanques();
            System.out.println("\n--- TANQUES CADASTRADOS ---");
            
            if (tanques.isEmpty()) {
                System.out.println("Nenhum tanque cadastrado.");
            } else {
                for (int i = 0; i < tanques.size(); i++) {
                    System.out.println(i + ". " + tanques.get(i));
                }
                System.out.println("Total: " + tanques.size() + " tanques");
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar tanques: " + e.getMessage());
        }
    }
    
    private static void simularBatalha() {
        try {
            List<Tanque> tanques = arenaManager.getTanques();
            
            if (tanques.size() < 2) {
                System.out.println("E necessario pelo menos 2 tanques para batalhar!");
                return;
            }
            
            System.out.println("\n--- SIMULACAO DE BATALHA ---");
            System.out.println("Tanques disponiveis:");
            for (int i = 0; i < tanques.size(); i++) {
                Tanque t = tanques.get(i);
                String status = t.getIntegridade() <= 0 ? "DESTRUIDO" : t.getIntegridade() + "%";
                System.out.println(i + ". " + t.getCodinome() + " - " + status);
            }
            
            Tanque atacante = null;
            Tanque alvo = null;
            
            while (atacante == null) {
                try {
                    System.out.print("\nEscolha o tanque atacante (numero): ");
                    String input = scanner.nextLine().trim();
                    int indexAtacante = Integer.parseInt(input);
                    
                    if (indexAtacante < 0 || indexAtacante >= tanques.size()) {
                        System.out.println("Indice invalido! Use numeros de 0 a " + (tanques.size()-1));
                        continue;
                    }
                    
                    atacante = tanques.get(indexAtacante);
                    
                    if (atacante.getIntegridade() <= 0) {
                        System.out.println("Tanque destruido nao pode atacar!");
                        atacante = null;
                        continue;
                    }
                    
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, digite um numero valido!");
                }
            }
            
            while (alvo == null) {
                try {
                    System.out.print("Escolha o tanque alvo (numero): ");
                    String input = scanner.nextLine().trim();
                    int indexAlvo = Integer.parseInt(input);
                    
                    if (indexAlvo < 0 || indexAlvo >= tanques.size()) {
                        System.out.println("Indice invalido! Use numeros de 0 a " + (tanques.size()-1));
                        continue;
                    }
                    
                    alvo = tanques.get(indexAlvo);
                    
                    if (atacante == alvo) {
                        System.out.println("Um tanque nao pode atacar a si mesmo!");
                        alvo = null;
                        continue;
                    }
                    
                    if (alvo.getIntegridade() <= 0) {
                        System.out.println("Este tanque ja esta destruido! Escolha outro.");
                        alvo = null;
                    }
                    
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, digite um numero valido!");
                }
            }
            
            double dano = atacante.atacar(alvo);
            System.out.println("\n" + "---".repeat(10));
            System.out.printf("%s atacou %s causando %.1f de dano!%n", 
                             atacante.getCodinome(), alvo.getCodinome(), dano);
            System.out.println("Integridade do alvo: " + alvo.getIntegridade() + "%");
            
            if (alvo.getIntegridade() <= 0) {
                System.out.println(alvo.getCodinome() + " foi DESTRUIDO!");
            }
            System.out.println("---".repeat(10));
            
        } catch (Exception e) {
            System.err.println("Erro inesperado na batalha: " + e.getMessage());
        }
    }
    
    private static void exportarDados() {
        try {
            List<Tanque> tanques = arenaManager.getTanques();
            
            if (tanques.isEmpty()) {
                System.out.println("Nenhum tanque para exportar.");
                return;
            }
            
            System.out.print("Nome do arquivo para exportar (ex: tanques.csv): ");
            String nomeArquivo = scanner.nextLine().trim();
            
            if (nomeArquivo.isEmpty()) {
                nomeArquivo = "data/tanques.csv";
            }
            
            if (!nomeArquivo.endsWith(".csv")) {
                nomeArquivo += ".csv";
            }
            
            CSVHandler.exportarTanques(tanques, nomeArquivo);
            System.out.println("Dados exportados com sucesso para: " + nomeArquivo);
            System.out.println("Total de tanques exportados: " + tanques.size());
            
        } catch (SecurityException e) {
            System.err.println("Erro de permissao: Nao e possivel escrever no arquivo.");
        } catch (Exception e) {
            System.err.println("Erro ao exportar dados: " + e.getMessage());
        }
    }
    
    private static void importarDados() {
        try {
            System.out.print("Nome do arquivo para importar (ex: tanques.csv): ");
            String nomeArquivo = scanner.nextLine().trim();
            
            if (nomeArquivo.isEmpty()) {
                nomeArquivo = "data/tanques.csv";
            }
            
            List<Tanque> tanques = CSVHandler.importarTanques(nomeArquivo);
            
            if (tanques.isEmpty()) {
                System.out.println("Nenhum tanque encontrado no arquivo.");
                return;
            }
            
            int count = 0;
            int limiteAtingido = 0;
            
            for (Tanque tanque : tanques) {
                if (arenaManager.cadastrarTanque(tanque)) {
                    count++;
                } else {
                    limiteAtingido++;
                }
            }
            
            System.out.println(count + " tanques importados com sucesso!");
            
            if (limiteAtingido > 0) {
                System.out.println(limiteAtingido + " tanques nao importados (limite atingido).");
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao importar dados: " + e.getMessage());
        }
    }
}