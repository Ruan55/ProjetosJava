import java.io.Serializable;

// Implementamos Serializable para permitir a gravação do objeto em arquivo
public class Contato implements Serializable{

    // O serialVersionUID garante a compatibilidade durante a leitura do arquivo
    private static final long serialVersionUID = 1L;

    private String nome;
    private String telefone;

    public Contato(String nome, String telefone){

        this.nome = nome;
        this.telefone = telefone;

    }

    // Getters e Setters
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }

    @Override
    public String toString(){

        return "Nome: " + nome + " | Telefone: " + telefone;

    }

}