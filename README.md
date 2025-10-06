BattleTanks

BattleTanks é um jogo desenvolvido em Java, com foco em combates entre tanques em arenas.
O projeto implementa movimentação, tiro, colisões e comportamentos básicos de inimigos.


📘 Descrição

O jogo consiste em batalhas entre tanques em diferentes mapas.
O jogador pode se mover, atirar e enfrentar inimigos controlados por IA.
O código está estruturado em módulos que separam a lógica principal, entidades e utilidades.


🗂 Estrutura do Projeto

IsaacBattleTanks/
│
├── src/ # Código-fonte principal
│ ├── main/ # Loop do jogo e execução principal
│ ├── entities/ # Classes de tanques, projéteis e inimigos
│ ├── map/ # Mapas e lógica de carregamento
│ ├── utils/ # Funções auxiliares e constantes
│ └── ui/ # Interface do jogo (menus, HUD, etc.)
│
├── assets/ # Recursos (imagens, sons, sprites)
├── out/ # Arquivos compilados (gerados automaticamente)
├── README.md
└── LICENSE

A estrutura pode variar conforme o progresso do projeto.


⚙️ Tecnologias

Linguagem: Java

Versão recomendada: Java 17 ou superior

Bibliotecas:

java.awt e javax.swing (interface e renderização)

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

Movimento e rotação do tanque do jogador

Disparo de projéteis

Colisões entre tanques, balas e obstáculos

IA básica de inimigos

Sistema de mapas e arenas


👥 Desenvolvido por

João Paulo Koury de Mendonça

Pedro Antônio de Souza Fernandes Filho

Pedro Andrade Gonçalves de Souza

Júlia Labad Jatene


📄 Licença

Este projeto está sob a licença especificada no arquivo LICENSE.
