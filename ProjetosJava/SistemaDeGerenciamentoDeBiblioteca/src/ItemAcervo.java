// Classe abstrata: ninguém pode instanciar um "ItemAcervo" genérico,
// apenas tipos específicos como Livro
abstract class ItemAcervo{

    private String titulo;
    private String id;
    private boolean emprestado;

    public ItemAcervo(String titulo, String id){

        this.titulo = titulo;
        this.id = id;
        this.emprestado = false; // Todo item começa disponível

    }

    // Getters e Setters (Encapsulamento)
    public String getTitulo() { return titulo; }
    public String getId() { return id; }
    public boolean isEmprestado() { return emprestado; }

    public void setEmprestado(boolean status){

        this.emprestado = status;

    }

    // Método abstrato: cada tipo de item decide como exibir seus detalhes
    public abstract void exibirDetalhes();

}