BattleTanks

BattleTanks é um jogo desenvolvido em Java, com foco em combates entre tanques em arenas.
O projeto implementa movimentação, tiro, colisões e comportamentos básicos de inimigos.


📘 Descrição

O jogo consiste em batalhas entre tanques.
O jogador pode enfrentar inimigos controlados por IA.
O código está estruturado em módulos que separam a lógica principal e utilidades.


🗂 Estrutura do Projeto

IsaacBattleTanks/
│
├── data/                     # Arquivos de dados (ex: CSVs)
│   └── tanques.csv
│
├── demo/                     # Módulo principal do jogo
│   ├── pom.xml               # Arquivo de configuração Maven
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── isaactanks/
│   │   │   │           ├── Main.java
│   │   │   │           ├── manager/
│   │   │   │           │   └── ArenaManager.java
│   │   │   │           ├── model/
│   │   │   │           │   ├── Canhao.java
│   │   │   │           │   ├── ClasseTanque.java
│   │   │   │           │   ├── Metralhadora.java
│   │   │   │           │   ├── Modulo.java
│   │   │   │           │   ├── Tanque.java
│   │   │   │           │   └── TipoPiloto.java
│   │   │   │           └── util/
│   │   │   │               └── CSVHandler.java
│   │   └── test/
│   │       └── java/
│   │           └── battletanks/
│   │               ├── CSVHandlerTest.java
│   │               └── TanqueTest.java
│   └── target/               # Saída de build (classes compiladas)
│       ├── classes/
│       │   └── com/
│       │       └── isaactanks/
│       │           ├── Main.class
│       │           ├── manager/
│       │           │   └── ArenaManager.class
│       │           ├── model/
│       │           │   ├── Canhao.class
│       │           │   ├── ClasseTanque.class
│       │           │   ├── Metralhadora.class
│       │           │   ├── Modulo.class
│       │           │   ├── Tanque.class
│       │           │   └── TipoPiloto.class
│       │           └── util/
│       │               └── CSVHandler.class
│       └── test-classes/
│           └── battletanks/
│               ├── CSVHandlerTest.class
│               └── TanqueTest.class
│
├── README.md
└── LICENSE


A estrutura pode variar conforme o progresso do projeto.


⚙️ Tecnologias

Linguagem: Java

Versão recomendada: Java 17 ou superior

Bibliotecas:

Outras dependências específicas podem ser listadas no código


🚀 Execução
1. Clonar o repositório

git clone https://github.com/joaopkourym/IsaacBattleTanks.git

cd IsaacBattleTanks

2. Compilar o código

javac -d out src/**/*.java

3. Executar o jogo

java -cp out Main

Certifique-se de estar usando uma JDK configurada corretamente no PATH.


🔧 Funcionalidades

Disparo de projéteis

IA básica de inimigos

Sistema de agendamento


👥 Desenvolvido por

João Paulo Koury de Mendonça

Pedro Antônio de Souza Fernandes Filho

Pedro Andrade Gonçalves de Souza

Júlia Labad Jatene



