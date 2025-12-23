public class SistemaBiblioteca {

    public static void main(String[] args){

        Biblioteca minhaBiblioteca = new Biblioteca();

        // Criando instâncias
        Livro livro1 = new Livro("Dom Casmurro", "L001", "Machado de Assis");
        Livro livro2 = new Livro("O Alquimista", "L002", "Paulo Coelho");

        // Adicionando ao sistema
        minhaBiblioteca.adicionarItem(livro1);
        minhaBiblioteca.adicionarItem(livro2);

        // Operações
        minhaBiblioteca.listarAcervo();
        minhaBiblioteca.emprestarItem("L001");
        minhaBiblioteca.listarAcervo();

    }

}