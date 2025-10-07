package com.isaactanks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.isaactanks.manager.ArenaManager;
import com.isaactanks.manager.PartidaManager;
import com.isaactanks.model.ClasseTanque;
import com.isaactanks.model.ModoBatalha;
import com.isaactanks.model.Partida;
import com.isaactanks.model.Tanque;
import com.isaactanks.model.Terreno;
import com.isaactanks.model.TipoPiloto;
import com.isaactanks.util.CSVHandler;

public class Main {
    private static ArenaManager arenaManager = new ArenaManager();
    private static PartidaManager partidaManager = new PartidaManager();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("--- ARENA ISAAC TANKS ---");
        
        try {
            arenaManager.cadastrarTanque(new Tanque("Jupe", ClasseTanque.PESADO, TipoPiloto.HUMANO));
            arenaManager.cadastrarTanque(new Tanque("Pilera", ClasseTanque.PESADO, TipoPiloto.IA));
            arenaManager.cadastrarTanque(new Tanque("Souza", ClasseTanque.MEDIO, TipoPiloto.HUMANO));
            arenaManager.cadastrarTanque(new Tanque("Julia", ClasseTanque.LEVE, TipoPiloto.IA));
            arenaManager.cadastrarTanque(new Tanque("Alho", ClasseTanque.PESADO, TipoPiloto.HUMANO));
            arenaManager.cadastrarTanque(new Tanque("Vanni", ClasseTanque.MEDIO, TipoPiloto.IA));
            arenaManager.cadastrarTanque(new Tanque("Henrique", ClasseTanque.LEVE, TipoPiloto.IA));
            arenaManager.cadastrarTanque(new Tanque("Gelado", ClasseTanque.LEVE, TipoPiloto.IA));
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
                    case 3: simularBatalhaRapida(); break;
                    case 4: agendarBatalha(); break;
                    case 5: listarBatalhasAgendadas(); break;
                    case 6: simularBatalhaAgendada(); break;
                    case 7: gerenciarTanquesPartida(); break;
                    case 8: exportarDados(); break;
                    case 9: importarDados(); break;
                    case 0: 
                        running = false; 
                        System.out.println("Saindo... Obrigado por jogar!");
                        break;
                    default: 
                        System.out.println("Opcao invalida! Use numeros de 0 a 9.");
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
        System.out.println("\n" + "-".repeat(50));
        System.out.println("               MENU PRINCIPAL");
        System.out.println("-".repeat(50));
        System.out.println("1. Cadastrar Tanque");
        System.out.println("2. Listar Tanques");
        System.out.println("3. Simular Batalha Rapida");
        System.out.println("4. Agendar Batalha");
        System.out.println("5. Listar Batalhas Agendadas");
        System.out.println("6. Simular Batalha Agendada");
        System.out.println("7. Gerenciar Tanques em Partida");
        System.out.println("8. Exportar Dados");
        System.out.println("9. Importar Dados");
        System.out.println("0. Sair");
        System.out.println("-".repeat(50));
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
    
    private static void simularBatalhaRapida() {
        try {
            List<Tanque> tanques = arenaManager.getTanques();
            
            if (tanques.size() < 2) {
                System.out.println("E necessario pelo menos 2 tanques para batalhar!");
                return;
            }
            
            System.out.println("\n--- SIMULACAO DE BATALHA RAPIDA ---");
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
    
    private static void agendarBatalha() {
        System.out.println("\n--- AGENDAMENTO DE BATALHA ---");
        
        try {
            System.out.print("Nome da partida: ");
            String nome = scanner.nextLine().trim();
            
            if (nome.isEmpty()) {
                System.out.println("Erro: Nome da partida nao pode estar vazio!");
                return;
            }
            
            System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
            String dataHoraStr = scanner.nextLine().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);
            
            System.out.print("Duracao (minutos): ");
            int duracao = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Terreno (DESERTO/URBANO/CAMPO_ABERTO): ");
            String terrenoStr = scanner.nextLine().trim().toUpperCase();
            Terreno terreno = Terreno.valueOf(terrenoStr);
            
            System.out.print("Modo (TREINO/UM_VS_UM/TRES_VS_TRES): ");
            String modoStr = scanner.nextLine().trim().toUpperCase();
            ModoBatalha modo = ModoBatalha.valueOf(modoStr);
            
            Partida partida = partidaManager.agendarPartida(nome, dataHora, duracao, terreno, modo);
            System.out.println("Partida agendada com sucesso! ID: " + partida.getId());
            
            // Perguntar se quer adicionar tanques agora
            System.out.print("Deseja adicionar tanques a esta partida agora? (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (resposta.equals("S") || resposta.equals("SIM")) {
                adicionarTanquesPartida(partida.getId());
            }
            
        } catch (DateTimeParseException e) {
            System.out.println("Erro: Formato de data/hora invalido. Use dd/MM/yyyy HH:mm");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: Terreno ou modo invalido.");
        } catch (Exception e) {
            System.err.println("Erro inesperado ao agendar batalha: " + e.getMessage());
        }
    }
    
    private static void listarBatalhasAgendadas() {
        try {
            List<Partida> partidas = partidaManager.getPartidasAgendadas();
            System.out.println("\n--- BATALHAS AGENDADAS ---");
            
            if (partidas.isEmpty()) {
                System.out.println("Nenhuma batalha agendada.");
            } else {
                for (int i = 0; i < partidas.size(); i++) {
                    Partida p = partidas.get(i);
                    System.out.println(i + ". " + p);
                    System.out.println("   Tanques: " + p.getAllTanques().size() + "/" + 
                                     (p.getModo() == ModoBatalha.TRES_VS_TRES ? "6" : "2"));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar batalhas agendadas: " + e.getMessage());
        }
    }
    
    private static void simularBatalhaAgendada() {
        try {
            List<Partida> partidas = partidaManager.getPartidasAgendadas();
            
            if (partidas.isEmpty()) {
                System.out.println("Nenhuma batalha agendada para simular.");
                return;
            }
            
            System.out.println("\n--- SIMULAR BATALHA AGENDADA ---");
            System.out.println("Batalhas agendadas:");
            for (int i = 0; i < partidas.size(); i++) {
                Partida p = partidas.get(i);
                System.out.println(i + ". " + p);
            }
            
            System.out.print("Escolha a batalha (numero): ");
            int index = Integer.parseInt(scanner.nextLine().trim());
            
            if (index < 0 || index >= partidas.size()) {
                System.out.println("Indice invalido!");
                return;
            }
            
            Partida partida = partidas.get(index);
            
            if (!partida.podeIniciar()) {
                System.out.println("Esta partida nao tem tanques suficientes para iniciar!");
                System.out.println("Tanques necessarios: " + 
                                 (partida.getModo() == ModoBatalha.TRES_VS_TRES ? "6" : "2"));
                return;
            }
            
            // Iniciar partida
            if (partidaManager.iniciarPartida(partida.getId())) {
                System.out.println("Partida iniciada! Aplicando efeitos do terreno: " + partida.getTerreno());
                
                // Simular batalhas entre os times
                simularBatalhaEntreTimes(partida);
                
                // Concluir partida
                partidaManager.concluirPartida(partida.getId());
                System.out.println("Partida concluida!");
            } else {
                System.out.println("Erro ao iniciar partida!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um numero valido!");
        } catch (Exception e) {
            System.err.println("Erro inesperado ao simular batalha agendada: " + e.getMessage());
        }
    }
    
    private static void simularBatalhaEntreTimes(Partida partida) {
        List<Tanque> time1 = partida.getTanquesTime1();
        List<Tanque> time2 = partida.getTanquesTime2();
        
        System.out.println("\n=== INICIANDO BATALHA ===");
        System.out.println("Time 1: " + time1.size() + " tanques");
        for (Tanque t : time1) {
            System.out.println("  - " + t.getCodinome() + " [" + t.getClasse() + "]");
        }
        
        System.out.println("Time 2: " + time2.size() + " tanques");
        for (Tanque t : time2) {
            System.out.println("  - " + t.getCodinome() + " [" + t.getClasse() + "]");
        }
        
        System.out.println("\nTerreno: " + partida.getTerreno());
        System.out.println("Efeitos aplicados:");
        for (Tanque t : partida.getAllTanques()) {
            System.out.printf("  %s: Velocidade = %.1f (Original: %.1f)%n", 
                            t.getCodinome(), t.getVelocidade(), t.getVelocidadeOriginal());
        }
        
        // Simular rodadas de batalha
        int rodadas = 3; // Número fixo de rodadas para demonstração
        for (int rodada = 1; rodada <= rodadas; rodada++) {
            System.out.println("\n--- Rodada " + rodada + " ---");
            
            // Time 1 ataca Time 2
            for (Tanque atacante : time1) {
                if (atacante.getIntegridade() > 0 && !time2.isEmpty()) {
                    Tanque alvo = time2.get(0); // Simplificado: ataca primeiro tanque
                    double dano = atacante.atacar(alvo);
                    System.out.printf("%s atacou %s causando %.1f de dano!%n", 
                                    atacante.getCodinome(), alvo.getCodinome(), dano);
                    
                    if (alvo.getIntegridade() <= 0) {
                        System.out.println(alvo.getCodinome() + " foi DESTRUIDO!");
                    }
                }
            }
            
            // Time 2 ataca Time 1
            for (Tanque atacante : time2) {
                if (atacante.getIntegridade() > 0 && !time1.isEmpty()) {
                    Tanque alvo = time1.get(0); // Simplificado: ataca primeiro tanque
                    double dano = atacante.atacar(alvo);
                    System.out.printf("%s atacou %s causando %.1f de dano!%n", 
                                    atacante.getCodinome(), alvo.getCodinome(), dano);
                    
                    if (alvo.getIntegridade() <= 0) {
                        System.out.println(alvo.getCodinome() + " foi DESTRUIDO!");
                    }
                }
            }
        }
        
        System.out.println("\n=== RESULTADO FINAL ===");
        System.out.println("Time 1 - Integridade media: " + calcularIntegridadeMedia(time1) + "%");
        System.out.println("Time 2 - Integridade media: " + calcularIntegridadeMedia(time2) + "%");
    }
    
    private static double calcularIntegridadeMedia(List<Tanque> tanques) {
        if (tanques.isEmpty()) return 0;
        double total = 0;
        for (Tanque t : tanques) {
            total += t.getIntegridade();
        }
        return total / tanques.size();
    }
    
    private static void gerenciarTanquesPartida() {
        try {
            List<Partida> partidas = partidaManager.getPartidasAgendadas();
            
            if (partidas.isEmpty()) {
                System.out.println("Nenhuma batalha agendada para gerenciar.");
                return;
            }
            
            System.out.println("\n--- GERENCIAR TANQUES EM PARTIDA ---");
            System.out.println("Batalhas agendadas:");
            for (int i = 0; i < partidas.size(); i++) {
                Partida p = partidas.get(i);
                System.out.println(i + ". " + p);
            }
            
            System.out.print("Escolha a batalha (numero): ");
            int index = Integer.parseInt(scanner.nextLine().trim());
            
            if (index < 0 || index >= partidas.size()) {
                System.out.println("Indice invalido!");
                return;
            }
            
            Partida partida = partidas.get(index);
            adicionarTanquesPartida(partida.getId());
            
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um numero valido!");
        } catch (Exception e) {
            System.err.println("Erro inesperado ao gerenciar tanques: " + e.getMessage());
        }
    }
    
    private static void adicionarTanquesPartida(int partidaId) {
        try {
            Partida partida = partidaManager.buscarPartidaPorId(partidaId);
            if (partida == null) {
                System.out.println("Partida nao encontrada!");
                return;
            }
            
            List<Tanque> tanquesDisponiveis = arenaManager.getTanques();
            
            System.out.println("\nAdicionando tanques a partida: " + partida.getNome());
            System.out.println("Modo: " + partida.getModo());
            System.out.println("Tanques necessarios: " + 
                             (partida.getModo() == ModoBatalha.TRES_VS_TRES ? "6 (3 por time)" : "2 (1 por time)"));
            
            int maxPorTime = partida.getModo() == ModoBatalha.TRES_VS_TRES ? 3 : 1;
            
            // Time 1
            System.out.println("\n--- TIME 1 ---");
            for (int i = 0; i < maxPorTime; i++) {
                if (tanquesDisponiveis.isEmpty()) {
                    System.out.println("Nenhum tanque disponivel.");
                    break;
                }
                
                System.out.println("Tanques disponiveis:");
                for (int j = 0; j < tanquesDisponiveis.size(); j++) {
                    System.out.println(j + ". " + tanquesDisponiveis.get(j));
                }
                
                System.out.print("Escolha tanque para Time 1 (numero) ou -1 para pular: ");
                int escolha = Integer.parseInt(scanner.nextLine().trim());
                
                if (escolha == -1) break;
                
                if (escolha >= 0 && escolha < tanquesDisponiveis.size()) {
                    Tanque tanqueEscolhido = tanquesDisponiveis.get(escolha);
                    if (partidaManager.adicionarTanqueAPartida(partidaId, tanqueEscolhido, 1)) {
                        System.out.println("Tanque " + tanqueEscolhido.getCodinome() + " adicionado ao Time 1!");
                        tanquesDisponiveis.remove(escolha);
                    } else {
                        System.out.println("Erro ao adicionar tanque!");
                    }
                } else {
                    System.out.println("Escolha invalida!");
                    i--; // Repetir esta posicao
                }
            }
            
            // Time 2
            System.out.println("\n--- TIME 2 ---");
            for (int i = 0; i < maxPorTime; i++) {
                if (tanquesDisponiveis.isEmpty()) {
                    System.out.println("Nenhum tanque disponivel.");
                    break;
                }
                
                System.out.println("Tanques disponiveis:");
                for (int j = 0; j < tanquesDisponiveis.size(); j++) {
                    System.out.println(j + ". " + tanquesDisponiveis.get(j));
                }
                
                System.out.print("Escolha tanque para Time 2 (numero) ou -1 para pular: ");
                int escolha = Integer.parseInt(scanner.nextLine().trim());
                
                if (escolha == -1) break;
                
                if (escolha >= 0 && escolha < tanquesDisponiveis.size()) {
                    Tanque tanqueEscolhido = tanquesDisponiveis.get(escolha);
                    if (partidaManager.adicionarTanqueAPartida(partidaId, tanqueEscolhido, 2)) {
                        System.out.println("Tanque " + tanqueEscolhido.getCodinome() + " adicionado ao Time 2!");
                        tanquesDisponiveis.remove(escolha);
                    } else {
                        System.out.println("Erro ao adicionar tanque!");
                    }
                } else {
                    System.out.println("Escolha invalida!");
                    i--; // Repetir esta posicao
                }
            }
            
            System.out.println("\nPartida atualizada!");
            System.out.println("Time 1: " + partida.getTanquesTime1().size() + " tanques");
            System.out.println("Time 2: " + partida.getTanquesTime2().size() + " tanques");
            
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um numero valido!");
        } catch (Exception e) {
            System.err.println("Erro inesperado ao adicionar tanques: " + e.getMessage());
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