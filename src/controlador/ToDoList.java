package controlador;

import entities.Tarefa;
import enums.Categoria;
import enums.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToDoList {
    private List<Tarefa> tarefas = new ArrayList<Tarefa>();

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ToDoList(){}

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public boolean addTarefa(Tarefa tarefa){

        return tarefas.add(tarefa);
    }

    public void ordenaPorPrioridade(List<Tarefa> tarefas){
        Collections.sort(tarefas);
    }

    public boolean removeTarefa(String nome){
     int indice = 0;
     if(!tarefas.isEmpty()) {
         for (int i = 0; i < tarefas.size(); i++) {
             if(tarefas.get(i).getNome().toUpperCase().equals(nome.toUpperCase())){
                 indice = tarefas.indexOf(tarefas.get(i));
                 tarefas.remove(indice);
                 return true;
             }
         }
     }
     return false;
    }

    //Método a ser implementado posteriormente
    /*public boolean atualizaTarefa(Tarefa tarefa, Tarefa novaTarefa){
        int indice = tarefas.indexOf(tarefa);
        if(indice > -1) {
            tarefas.set(indice, novaTarefa);
            return true;
        }
        return false;
    }*/

    // guardar objeto no arquivo
    public void guardaArquivo(Path path, List<Tarefa> listaDeTarefas) {
        try (PrintWriter writer = new PrintWriter(Files.newOutputStream(path))) {
            for(Tarefa x : listaDeTarefas) {
                writer.format("%s\n", x);

            }
        }
        catch (IOException e1) {
            System.out.println(e1.getMessage());
        }
    }

    public void leArquivo(Path path) {
        try (BufferedReader br =  Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = br.readLine();
            //sc.useDelimiter("[;\\n]");
            //line = br.readLine();
            while (line != null) {
                String[] vect =  line.split(";");
                List<String> fields = Arrays.asList(vect);
                String nome = fields.get(0);
                String descricao = fields.get(1);
                Date dataTermino =sdf.parse(fields.get(2));
                Categoria categoria = Categoria.valueOf(fields.get(3));
                Status status = Status.valueOf(fields.get(4));

                tarefas.add(new Tarefa(nome, descricao, dataTermino, categoria, status));

                line = br.readLine();
            }
            System.out.println("Arquivo lido com sucesso. ");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
