import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    private List<ItemAcervo> acervo = new ArrayList<>();

    public void adicionarItem(ItemAcervo item){

        acervo.add(item);

    }

    public void emprestarItem(String id){

        for(ItemAcervo item : acervo){

            if(item.getId().equals(id)){

                if(!item.isEmprestado()){

                    item.setEmprestado(true);
                    System.out.println("Empréstimo realizado: " + item.getTitulo());

                }

                else{

                    System.out.println("Erro: Item já está emprestado.");

                }

                return;

            }

        }

        System.out.println("Erro: Item não encontrado.");

    }

    public void listarAcervo(){

        System.out.println("\n--- ACERVO DA BIBLIOTECA ---");
        for(ItemAcervo item : acervo){

            item.exibirDetalhes(); // Polimorfismo em ação

        }

    }

}