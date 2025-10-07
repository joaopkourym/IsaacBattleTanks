# 🎮 BattleTanks - Sistema de Gerenciamento de Arenas

**BattleTanks** é um sistema completo desenvolvido em Java para gerenciamento de batalhas de tanques em arenas, com foco em combates estratégicos entre tanques autônomos (IA) e controlados por jogadores.

## 📋 Descrição

O sistema permite o cadastro e gerenciamento de tanques, agendamento de partidas, simulação de batalhas e análise de desempenho. Desenvolvido seguindo os princípios de Programação Orientada a Objetos, o projeto implementa herança, polimorfismo e encapsulamento em um sistema robusto e modular.

## 🗂 Estrutura do Projeto

```
BattleTanks/
│
├── data/                        # Arquivos de dados e persistência
│   └── tanques.csv              # Arquivo CSV para importação/exportação
│
├── src/
│   ├── main/
│   │   └── java/com/isaactanks/
│   │       ├── Main.java                    # Classe principal com menu interativo
│   │       ├── manager/
│   │       │   ├── ArenaManager.java        # Gerenciador de tanques e batalhas
│   │       │   └── PartidaManager.java      # Gerenciador de partidas agendadas
│   │       ├── model/
│   │       │   ├── Canhao.java              # Implementação do módulo Canhão
│   │       │   ├── ClasseTanque.java        # Enum: LEVE, MÉDIO, PESADO
│   │       │   ├── Metralhadora.java        # Implementação do módulo Metralhadora
│   │       │   ├── Modulo.java              # Classe abstrata para módulos
│   │       │   ├── Tanque.java              # Classe principal do tanque
│   │       │   ├── TipoPiloto.java          # Enum: HUMANO, IA
│   │       │   ├── Terreno.java             # Enum: DESERTO, URBANO, CAMPO_ABERTO
│   │       │   ├── ModoBatalha.java         # Enum: TREINO, UM_VS_UM, TRES_VS_TRES
│   │       │   └── StatusPartida.java       # Enum: AGENDADA, EM_ANDAMENTO, etc.
│   │       └── util/
│   │           └── CSVHandler.java          # Utilitário para manipulação de CSV
│   │
│   └── test/
│       └── java/com/isaactanks/
│           ├── CSVHandlerTest.java          # Testes para manipulação de CSV
│           └── TanqueTest.java              # Testes para a classe Tanque
│
├── target/                      # Saída de compilação do Maven
│   ├── classes/                 # Arquivos .compilados
│   └── test-classes/            # Testes compilados
│
└── pom.xml                      # Configuração do Maven
```

## ⚙️ Tecnologias

- **Linguagem**: Java 21
- **Gerenciador de Dependências**: Maven
- **Testes**: JUnit 5
- **Persistência**: Arquivos CSV
- **Princípios de POO**: Herança, Polimorfismo, Encapsulamento

## 🚀 Execução

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6 ou superior

### Compilação e Execução

```bash
# Navegar até o diretório do projeto
cd BattleTanks

# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn exec:java -Dexec.mainClass="com.isaactanks.Main"

# Executar testes unitários
mvn test

# Empacotar em JAR executável
mvn package
```

## 🎯 Funcionalidades

### Sistema de Tanques
- ✅ Cadastro de até 12 tanques simultâneos
- ✅ Três classes distintas: Leve, Médio e Pesado
- ✅ Sistema de módulos: Canhão e Metralhadora
- ✅ Pilotos: Humano ou IA
- ✅ Atributos dinâmicos: Blindagem, velocidade, poder de fogo

### Sistema de Batalhas
- ✅ Batalhas rápidas (combates individuais)
- ✅ Agendamento de partidas com data/hora
- ✅ Múltiplos modos: Treino, 1vs1, 3vs3
- ✅ Efeitos de terreno: Deserto, Urbano, Campo Aberto
- ✅ Sistema de times com distribuição automática

### Recursos Avançados
- ✅ Importação/Exportação em CSV
- ✅ Tratamento robusto de erros
- ✅ Testes unitários com JUnit
- ✅ Menu interativo completo

## 🎮 Como Jogar

### Menu Principal
```
=== ARENA ISAAC TANKS ===
1. Cadastrar Tanque
2. Listar Tanques
3. Simular Batalha Rápida
4. Agendar Batalha
5. Listar Batalhas Agendadas
6. Simular Batalha Agendada
7. Gerenciar Tanques em Partida
8. Exportar Dados
9. Importar Dados
0. Sair
```

### Exemplo de Uso
1. **Cadastrar tanques** com diferentes classes e pilotos
2. **Agendar partidas** definindo terreno, modo e duração
3. **Adicionar tanques** aos times conforme o modo de jogo
4. **Simular batalhas** com efeitos de terreno aplicados
5. **Exportar/Importar** dados para persistência

## 🧪 Testes

O projeto inclui testes unitários abrangentes:

```bash
# Executar todos os testes
mvn test

# Executar testes específicos
mvn test -Dtest=TanqueTest
mvn test -Dtest=CSVHandlerTest
```

## 📊 Características Técnicas

### Efeitos de Terreno
- **Deserto**: Tanques 20% mais lentos
- **Urbano**: Tanques 15% mais rápidos
- **Campo Aberto**: Sem modificações

### Modos de Jogo
- **Treino**: 1 tanque
- **1vs1**: 2 tanques (1 por time)
- **3vs3**: 6 tanques (3 por time)

### Sistema de Dano
- Cálculo polimórfico baseado em módulo e classe
- Modificadores por distância e blindagem
- Efeitos específicos por tipo de munição

## 👥 Time de desenvolvimento

- **João Paulo Koury de Mendonça**
- **Pedro Antônio de Souza Fernandes Filho**
- **Pedro Andrade Gonçalves de Souza**
- **Júlia Labad Jatene**

---

