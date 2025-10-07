![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-red?style=for-the-badge&logo=apache-maven&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-green?style=for-the-badge)

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

├── 📁 data/

│ └── tanques.csv # Dados dos tanques

│

├── 📁 demo/

│ ├── 📁 src/main/java/com/isaactanks/

│ │ ├── 🎯 Main.java

│ │ ├── 📁 manager/

│ │ │ └── ArenaManager.java

│ │ ├── 📁 model/

│ │ │ ├── Canhao.java

│ │ │ ├── Tanque.java

│ │ │ └── ...

│ │ └── 📁 util/

│ │ └── CSVHandler.java

│ └── 📁 src/test/java/

│ ├── CSVHandlerTest.java

│ └── TanqueTest.java

│

└── 📄 README.md



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



