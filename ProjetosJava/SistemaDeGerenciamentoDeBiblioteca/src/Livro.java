// Livro herda do ItemAcervo
public class Livro extends ItemAcervo{

    private String autor;

    public Livro(String titulo, String id, String autor){

        super(titulo, id); // Chama o construtor da classe pai
        this.autor = autor;

    }

    @Override
    public void exibirDetalhes(){

        System.out.println("[LIVRO] ID: " + getId() + " | Título: " + getTitulo() +
                " | Autor: " + autor + " | Status: " +
                (isEmprestado() ? "Emprestado" : "Disponível"));

    }

}