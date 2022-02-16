package ui;

import controlador.ToDoList;
import entities.Tarefa;
import enums.Categoria;
import enums.Status;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private final SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy");
    ToDoList toDoList;

    public Menu(){
        toDoList = new ToDoList();
    }

    public void executa() {
        //int in = 0;
        Scanner sc = new Scanner(System.in);
        char opcao = ' ';
        System.out.println("Sistema de criação de tarefas. ");
        //Scanner entrada2 = new Scanner(System.in);
        //Scanner entrada = new Scanner(System.in);
        while (opcao != '0') {
            try {
                System.out.println("Opcoes: ");
                System.out.println("   [0] Sair.");
                System.out.println("   [1] Inserir Tarefas.");
                System.out.println("   [2] Consultar tarefas.");
                System.out.println("   [3] Remover Tarefa(s).");
                System.out.println("   [4] Carregar dados da última sessão.");
                System.out.println("   [5] Salvar dados.");
                System.out.print("Digite a opcao desejada: ");
                opcao = sc.next().charAt(0);
                sc.nextLine();
                switch (opcao) {
                    case '1':
                        System.out.println("Digite o nome da tarefa: ");
                        String nome = sc.nextLine();
                        sc.nextLine();
                        System.out.println("Digite a descrição da tarefa: ");
                        String descricao = sc.nextLine();
                        sc.nextLine();
                        System.out.println("Digite a data de término da tarefa, no formato dd/mm/aaaa");
                        String data = sc.nextLine();
                        Date dataTermino = sdf.parse(data);
                        sc.nextLine();
                        int opcaoCategoria = 0;
                        System.out.println("Categoria: ");
                        while(opcaoCategoria < 1 || opcaoCategoria > 4) {
                            System.out.println("Digite 1 para categoria \"emergencial\". ");
                            System.out.println("Digite 2 para categoria \"urgente\". ");
                            System.out.println("Digite 3 para categoria \"no prazo\". ");
                            System.out.println("Digite 4 para categoria \"planejada\". ");
                            opcaoCategoria = sc.nextInt();
                            if(opcaoCategoria < 1 || opcaoCategoria > 4) {
                                System.out.println("Dígito inválido, tente novamente.");
                            }
                        }
                        sc.nextLine();
                        Categoria categoria;
                        if(opcaoCategoria == 1){
                            categoria = Categoria.EMERGENCIAL;
                        }
                        else if(opcaoCategoria == 2){
                            categoria = Categoria.URGENTE;
                        }
                        else if(opcaoCategoria == 3){
                            categoria = Categoria.NO_PRAZO;
                        }
                        else {
                            categoria = Categoria.PLANEJADA;
                        }
                        System.out.println("Status: ");
                        int opcaoStatus = 0;
                        while(opcaoStatus < 1 || opcaoStatus > 3) {
                            System.out.println("Digite 1 para status \"TO DO\". ");
                            System.out.println("Digite 2 para status \"DOING\". ");
                            System.out.println("Digite 3 para status \"DONE\". ");
                            opcaoStatus = sc.nextInt();
                            if(opcaoStatus < 1 || opcaoStatus > 3) {
                                System.out.println("Dígito inválido, tente novamente.");
                            }
                        }
                        Status status;
                        if(opcaoCategoria == 1){
                            status = Status.TO_DO;
                        }
                        else if(opcaoCategoria == 2){
                            status = Status.DOING;
                        }
                        else {
                            status = Status.TO_DO;
                        }
                        if(toDoList.addTarefa(new Tarefa(nome, descricao, dataTermino, categoria, status))){
                            toDoList.ordenaPorPrioridade(toDoList.getTarefas());
                            System.out.println("Tarefa adicionada com sucesso!");
                        }
                        else{
                            System.out.println("A tarefa não pode ser adicionada.");
                        }
                        break;
                    case '2':
                        if(toDoList.getTarefas().isEmpty()){
                            System.out.println("Não há tarefas registradas. ");
                        }
                        else{
                            for(Tarefa tarefa : toDoList.getTarefas()){
                                System.out.println(tarefa.toString());
                            }
                        }
                        break;
                    case '3':
                        sc.nextLine();
                        System.out.println("Digite o nome da tarefas a ser removida: ");
                        String nomeTarefa = sc.nextLine();
                        if(toDoList.removeTarefa(nomeTarefa)){
                            System.out.println("Tarefa removida. ");
                        }
                        else{
                            System.out.println("Tarefa não encontrada. ");
                        }
                        break;
                    case '4':
                        Path arq2 = Paths.get("listaDeTarefas.txt");
                            toDoList.leArquivo(arq2);
                        break;
                    case '5':
                        if(!toDoList.getTarefas().isEmpty()) {
                            Path arq = Paths.get("listaDeTarefas.txt");
                            toDoList.guardaArquivo(arq, toDoList.getTarefas());
                        }
                        else{
                            System.out.println("Não existem tarefas para serem salvas. ");
                            System.out.println();
                        }
                        break;
                    case '0':
                        break;
                    default:
                        System.out.println("Opcao invalida! Redigite.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Erro: Insira apenas números inteiros. ");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Até breve.");
    }
}
