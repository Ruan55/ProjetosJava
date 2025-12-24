import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaAgenda {

    private static final String ARQUIVO = "contatos.dat";
    private List<Contato> contatos;

    public SistemaAgenda(){

        this.contatos = carregarContatos();

    }

    // Método para salvar a lista inteira no arquivo
    public void salvarArquivos(){

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))){

            oos.writeObject(contatos);

        }

        catch (IOException e){

            System.err.println("Erro ao salvar contatos: " + e.getMessage());

        }

    }

    // Método para carregar a lista do arquivo ao iniciar o programa
    @SuppressWarnings("unchecked")
    private List<Contato> carregarContatos(){

        File file = new File(ARQUIVO);
        if(!file.exists()) return new ArrayList<>(); // Retorna lista vazia se arquivo não existir

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))){

            return (List<Contato>) ois.readObject();

        }

        catch (IOException | ClassNotFoundException e){

            return new ArrayList<>();

        }

    }

    public void adicionar(Contato c){

        contatos.add(c);
        salvarArquivos(); // Persiste logo após adicionar

    }

    public void listar(){

        if(contatos.isEmpty()){

            System.out.println("Agenda vazia.");

        }

        else{

            contatos.forEach(System.out::println);

        }

    }

    // --- MÉTODO PRINCIPAL (INTERAÇÃO COM USUÁRIO)
    public static void main(String[] args){

        SistemaAgenda agenda = new SistemaAgenda();
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do{

            System.out.println("\n--- AGENDA DE CONTATOS ---");
            System.out.println("1. Adicionar Contatos");
            System.out.println("2. Listar Contatos");
            System.out.println("3. Sair");
            System.out.print("Escolha: ");

            try{

                opcao = Integer.parseInt(scanner.nextLine());

            }

            catch (NumberFormatException e){

                opcao = 0;

            }

            switch (opcao){

                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String tel = scanner.nextLine();
                    agenda.adicionar(new Contato(nome, tel));
                    System.out.println("Contato salvo!");
                    break;

                case 2:
                    agenda.listar();
                    break;

                case 3:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");


            }

        }while(opcao != 3);

        scanner.close();

    }

}