import java.util.Scanner;

class Livro {
    private String titulo;
    private String autor;
    private String ISBN;

    public Livro(String titulo, String autor, String ISBN) {
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getISBN() {
        return ISBN;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", ISBN='" + ISBN + '\'' +
                '}';
    }
}

class NoB {
    static final int ORDEM = 4; // Ordem da Árvore B (mínimo 2)
    int numChaves;
    Livro[] chaves = new Livro[ORDEM - 1];
    NoB[] filhos = new NoB[ORDEM];
    boolean folha;

    public NoB(boolean folha) {
        this.folha = folha;
        this.numChaves = 0;
    }

    public void inserirChaveNaoCheia(Livro livro) {
        int i = numChaves - 1;

        if (folha) {
            // Encontra a posição correta para a nova chave
            while (i >= 0 && chaves[i].getTitulo().compareTo(livro.getTitulo()) > 0) {
                chaves[i + 1] = chaves[i];
                i--;
            }

            // Insere a nova chave na posição encontrada
            chaves[i + 1] = livro;
            numChaves++;
        } else {
            // Encontra o filho que vai receber a nova chave
            while (i >= 0 && chaves[i].getTitulo().compareTo(livro.getTitulo()) > 0) {
                i--;
            }

            // Verifica se o filho encontrado está cheio
            if (filhos[i + 1].numChaves == ORDEM - 1) {
                dividirFilho(i + 1, filhos[i + 1]);

                // Decide qual dos dois novos filhos vai receber a nova chave
                if (chaves[i + 1].getTitulo().compareTo(livro.getTitulo()) < 0) {
                    i++;
                }
            }

            filhos[i + 1].inserirChaveNaoCheia(livro);
        }
    }

    public void dividirFilho(int i, NoB y) {
        NoB z = new NoB(y.folha);
        z.numChaves = ORDEM / 2 - 1;

        // Copia as últimas (ORDEM/2 - 1) chaves de y para z
        for (int j = 0; j < ORDEM / 2 - 1; j++) {
            z.chaves[j] = y.chaves[j + ORDEM / 2];
        }

        // Se não for folha, copia os últimos (ORDEM/2) filhos de y para z
        if (!y.folha) {
            for (int j = 0; j < ORDEM / 2; j++) {
                z.filhos[j] = y.filhos[j + ORDEM / 2];
            }
        }

        y.numChaves = ORDEM / 2 - 1;

        // Abre espaço para o novo filho
        for (int j = numChaves; j >= i + 1; j--) {
            filhos[j + 1] = filhos[j];
        }

        // Conecta o novo filho a este nó
        filhos[i + 1] = z;

        // Move uma chave de y para este nó
        for (int j = numChaves - 1; j >= i; j--) {
            chaves[j + 1] = chaves[j];
        }

        chaves[i] = y.chaves[ORDEM / 2 - 1];
        numChaves++;
    }

    public Livro buscarPorISBN(String ISBN) {
        int i = 0;
        while (i < numChaves && chaves[i] != null) {
            if (chaves[i].getISBN().equals(ISBN)) {
                return chaves[i];
            }
            i++;
        }

        if (!folha) {
            for (i = 0; i <= numChaves; i++) {
                if (filhos[i] != null) {
                    Livro encontrado = filhos[i].buscarPorISBN(ISBN);
                    if (encontrado != null) {
                        return encontrado;
                    }
                }
            }
        }

        return null;
    }

    public void listarEmOrdem() {
        String borda = "+------------------------------+------------------------------+----------------------+";
        System.out.println(borda);
        System.out.printf("| %-28s | %-28s | %-20s |%n", "Título", "Autor", "ISBN");
        System.out.println(borda);
        listarEmOrdemFormatado();
        System.out.println(borda);
    }

    private void listarEmOrdemFormatado() {
        int i;
        for (i = 0; i < numChaves; i++) {
            if (!folha && filhos[i] != null) {
                filhos[i].listarEmOrdemFormatado();
            }
            System.out.printf("| %-28s | %-28s | %-20s |%n", chaves[i].getTitulo(), chaves[i].getAutor(), chaves[i].getISBN());
        }
        if (!folha && filhos[i] != null) {
            filhos[i].listarEmOrdemFormatado();
        }
    }

    public void exibirEstrutura(int nivel) {
        System.out.print("Nível " + nivel + ": ");
        for (int i = 0; i < numChaves; i++) {
            System.out.print(chaves[i].getTitulo() + " ");
        }
        System.out.println();

        if (!folha) {
            for (int i = 0; i <= numChaves; i++) {
                if (filhos[i] != null) {
                    filhos[i].exibirEstrutura(nivel + 1);
                }
            }
        }
    }
}

class ArvoreB {
    private NoB raiz;

    public ArvoreB() {
        raiz = null;
    }

    public void inserir(Livro livro) {
        if (raiz == null) {
            raiz = new NoB(true);
            raiz.inserirChaveNaoCheia(livro);
        } else {
            if (raiz.numChaves == NoB.ORDEM - 1) {
                NoB s = new NoB(false);
                s.filhos[0] = raiz;
                s.dividirFilho(0, raiz);

                int i = 0;
                if (s.chaves[0].getTitulo().compareTo(livro.getTitulo()) < 0) {
                    i++;
                }
                s.filhos[i].inserirChaveNaoCheia(livro);

                raiz = s;
            } else {
                raiz.inserirChaveNaoCheia(livro);
            }
        }
    }

    public Livro buscarPorISBN(String ISBN) {
        return (raiz == null) ? null : raiz.buscarPorISBN(ISBN);
    }

    public void listarEmOrdem() {
        if (raiz != null) {
            raiz.listarEmOrdem();
        }
    }

    public void exibirEstrutura() {
        if (raiz != null) {
            raiz.exibirEstrutura(0);
        }
    }
}

public class BibliotecaArvoreB {
    public static void main(String[] args) {
        ArvoreB arvore = new ArvoreB();
        // Livros pré-cadastrados
        arvore.inserir(new Livro("Dom Quixote", "Miguel de Cervantes", "9780156030433"));
        arvore.inserir(new Livro("1984", "George Orwell", "9780451524935"));
        arvore.inserir(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "9780618640157"));
        arvore.inserir(new Livro("Orgulho e Preconceito", "Jane Austen", "9780141439518"));
        arvore.inserir(new Livro("Cem Anos de Solidão", "Gabriel García Márquez", "9780060883287"));
        arvore.inserir(new Livro("Crime e Castigo", "Fiódor Dostoiévski", "9780486415871"));
        arvore.inserir(new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", "9780156012194"));
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Menu Biblioteca ===");
            System.out.println("1. Inserir livro");
            System.out.println("2. Buscar livro por ISBN");
            System.out.println("3. Listar livros em ordem alfabética");
            System.out.println("4. Exibir estrutura da árvore");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = -1;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida! Tente novamente.");
                continue;
            }
            switch (opcao) {
                case 1:
                    do {
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();
                        System.out.print("ISBN: ");
                        String isbn = scanner.nextLine();
                        arvore.inserir(new Livro(titulo, autor, isbn));
                        System.out.println("Livro inserido!");
                        System.out.print("Deseja inserir outro livro? (s/n): ");
                    } while (scanner.nextLine().trim().equalsIgnoreCase("s"));
                    break;
                case 2:
                    do {
                        System.out.print("Digite o ISBN para buscar: ");
                        String isbn = scanner.nextLine();
                        Livro encontrado = arvore.buscarPorISBN(isbn);
                        if (encontrado != null) {
                            System.out.println("Livro encontrado: " + encontrado);
                        } else {
                            System.out.println("Livro não encontrado para o ISBN informado.");
                        }
                        System.out.print("Deseja buscar outro livro? (s/n): ");
                    } while (scanner.nextLine().trim().equalsIgnoreCase("s"));
                    break;
                case 3:
                    System.out.println("=== Lista de Livros em Ordem Alfabética ===");
                    arvore.listarEmOrdem();
                    break;
                case 4:
                    System.out.println("=== Estrutura da Árvore B ===");
                    arvore.exibirEstrutura();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}