package com.isaactanks.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.isaactanks.model.ClasseTanque;
import com.isaactanks.model.Tanque;
import com.isaactanks.model.TipoPiloto;

public class CSVHandler {
    
    public static void exportarTanques(List<Tanque> tanques, String arquivo) throws IOException {
        if (tanques == null) {
            throw new IllegalArgumentException("Lista de tanques nao pode ser nula");
        }
        
        if (arquivo == null || arquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do arquivo nao pode ser vazio");
        }
        
        // Criar diretorio se nao existir
        Path path = Paths.get(arquivo);
        Path parentDir = path.getParent();
        
        if (parentDir != null) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                throw new IOException("Nao foi possivel criar o diretorio: " + parentDir, e);
            }
        }
        
        // Verificar permissoes de escrita
        if (Files.exists(path) && !Files.isWritable(path)) {
            throw new SecurityException("Sem permissao para escrever no arquivo: " + arquivo);
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo, false))) {
            // Cabecalho
            writer.println("id,codinome,classe,blindagem,velocidade,poderDeFogo,piloto,integridade");
            
            int count = 0;
            for (Tanque tanque : tanques) {
                try {
                    if (tanque == null) {
                        System.err.println("Tanque nulo encontrado, pulando...");
                        continue;
                    }
                    
                    String linha = String.format("%d,%s,%s,%.1f,%.1f,%.1f,%s,%.1f",
                        tanque.getId(),
                        escapeCSV(tanque.getCodinome()),
                        tanque.getClasse(),
                        tanque.getBlindagem(),
                        tanque.getVelocidade(),
                        tanque.getPoderDeFogo(),
                        tanque.getPiloto(),
                        tanque.getIntegridade());
                    
                    writer.println(linha);
                    count++;
                    
                } catch (Exception e) {
                    System.err.println("Erro ao exportar tanque: " + e.getMessage());
                }
            }
            
            if (count == 0) {
                System.out.println("Nenhum tanque valido para exportar");
            } else {
                System.out.println("Exportados " + count + " tanques de " + tanques.size());
            }
            
        } catch (FileNotFoundException e) {
            throw new IOException("Arquivo nao encontrado: " + arquivo, e);
        } catch (SecurityException e) {
            throw new SecurityException("Acesso negado ao arquivo: " + arquivo, e);
        }
    }
    
    public static List<Tanque> importarTanques(String arquivo) throws IOException {
        if (arquivo == null || arquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do arquivo nao pode ser vazio");
        }
        
        Path path = Paths.get(arquivo);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Arquivo nao encontrado: " + arquivo);
        }
        
        if (!Files.isReadable(path)) {
            throw new SecurityException("Sem permissao para ler o arquivo: " + arquivo);
        }
        
        List<Tanque> tanques = new ArrayList<>();
        int linhaNumero = 0;
        int importados = 0;
        int erros = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            
            while ((linha = reader.readLine()) != null) {
                linhaNumero++;
                
                try {
                    if (linhaNumero == 1) {
                        // Pular cabecalho
                        continue;
                    }
                    
                    if (linha.trim().isEmpty()) {
                        continue; // Pular linhas vazias
                    }
                    
                    String[] dados = linha.split(",");
                    if (dados.length < 8) {
                        System.err.println("Linha " + linhaNumero + ": Dados insuficientes (" + dados.length + " campos)");
                        erros++;
                        continue;
                    }
                    
                    // Validar e processar dados
                    String codinome = unescapeCSV(dados[1].trim());
                    if (codinome.isEmpty()) {
                        System.err.println("Linha " + linhaNumero + ": Codinome vazio");
                        erros++;
                        continue;
                    }
                    
                    ClasseTanque classe;
                    try {
                        classe = ClasseTanque.valueOf(dados[2].trim());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Linha " + linhaNumero + ": Classe invalida: " + dados[2]);
                        erros++;
                        continue;
                    }
                    
                    TipoPiloto piloto;
                    try {
                        piloto = TipoPiloto.valueOf(dados[6].trim());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Linha " + linhaNumero + ": Piloto invalido: " + dados[6] + ", usando HUMANO como padrao");
                        piloto = TipoPiloto.HUMANO;
                    }
                    
                    // Criar tanque
                    Tanque tanque = new Tanque(codinome, classe, piloto);
                    tanques.add(tanque);
                    importados++;
                    
                } catch (Exception e) {
                    System.err.println("Erro na linha " + linhaNumero + ": " + e.getMessage());
                    erros++;
                }
            }
            
        } catch (IOException e) {
            throw new IOException("Erro ao ler arquivo " + arquivo + " na linha " + linhaNumero, e);
        }
        
        System.out.println("Importacao concluida: " + importados + " tanques importados, " + erros + " erros");
        return tanques;
    }
    
    // Metodos auxiliares para CSV
    private static String escapeCSV(String valor) {
        if (valor == null) return "";
        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }
    
    private static String unescapeCSV(String valor) {
        if (valor == null) return "";
        if (valor.startsWith("\"") && valor.endsWith("\"")) {
            return valor.substring(1, valor.length() - 1).replace("\"\"", "\"");
        }
        return valor;
    }
}