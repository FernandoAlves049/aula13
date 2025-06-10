# Biblioteca Árvore B

Este projeto é um sistema simples de gerenciamento de livros utilizando uma Árvore B em Java. Permite inserir, buscar e listar livros de forma eficiente, mantendo-os organizados em ordem alfabética pelo título.

## Funcionalidades
- Inserção de livros (título, autor, ISBN)
- Busca de livros por ISBN
- Listagem de todos os livros em ordem alfabética (formato de tabela)
- Visualização da estrutura da Árvore B
- Livros clássicos já cadastrados ao iniciar o programa

## Como executar
1. Compile o projeto:
   ```sh
   javac -d bin src/BibliotecaArvoreB.java
   ```
2. Execute o programa:
   ```sh
   java -cp bin BibliotecaArvoreB
   ```

## Exemplo de uso
```
=== Menu Biblioteca ===
1. Inserir livro
2. Buscar livro por ISBN
3. Listar livros em ordem alfabética
4. Exibir estrutura da árvore
5. Sair
Escolha uma opção: 3
=== Lista de Livros em Ordem Alfabética ===
+------------------------------+------------------------------+----------------------+
| Título                      | Autor                        | ISBN                 |
+------------------------------+------------------------------+----------------------+
| 1984                        | George Orwell                | 9780451524935        |
| Cem Anos de Solidão         | Gabriel García Márquez       | 9780060883287        |
| ...                         | ...                          | ...                  |
+------------------------------+------------------------------+----------------------+
```

## Estrutura do Projeto
- `src/`: código-fonte Java
- `bin/`: arquivos compilados
- `lib/`: dependências (não utilizadas neste projeto)

## Requisitos
- Java 8 ou superior

---
Projeto acadêmico para fins de estudo de estruturas de dados (Árvore B) em Java.
