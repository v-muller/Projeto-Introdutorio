package entities;

import enums.Categoria;
import enums.Status;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Tarefa implements Serializable, Comparable<Tarefa> {
    private String nome;
    private String descricao;
    private Date dataDeTermino;
    private Categoria categoria;
    private Status status;

    public Tarefa(){}

    public Tarefa(String nome, String descricao,
                  Date dataDeTermino, Categoria categoria, Status status) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataDeTermino = dataDeTermino;
        this.categoria = categoria;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(Date dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private Integer verificaPrioridade(Tarefa tarefa){
        Map<Categoria, Integer> mapa = criaMapaCategoria();
        int prioridade = 0;
        if(tarefa.getStatus() == Status.DOING){
            if(mapa.get(tarefa.getCategoria()) == 1){
                prioridade = 1;
            }
            else if(mapa.get(tarefa.getCategoria()) == 2){
                prioridade = 2;
            }
            else  if(mapa.get(tarefa.getCategoria()) == 3){
                prioridade = 3;
            }
            else {prioridade = 4;}
        }
        else if(tarefa.getStatus() == Status.TO_DO){
            if(mapa.get(tarefa.getCategoria()) == 1){
                prioridade = 5;
            }
            else if(mapa.get(tarefa.getCategoria()) == 2){
                prioridade = 6;
            }
            else  if(mapa.get(tarefa.getCategoria()) == 3){
                prioridade = 7;
            }
            else {prioridade = 8;}
        }
        else if (tarefa.getStatus() == Status.DONE){
            if(mapa.get(tarefa.getCategoria()) == 1){
                prioridade = 9;
            }
            else if(mapa.get(tarefa.getCategoria()) == 2){
                prioridade = 10;
            }
            else  if(mapa.get(tarefa.getCategoria()) == 3){
                prioridade = 11;
            }
            else {prioridade = 12;}
        }

        return prioridade;
    }

    private Map<Categoria, Integer> criaMapaCategoria(){
        HashMap<Categoria, Integer> mapa = new HashMap<>();
        mapa.put(Categoria.EMERGENCIAL, 1);
        mapa.put(Categoria.URGENTE, 2);
        mapa.put(Categoria.NO_PRAZO, 3);
        mapa.put(Categoria.PLANEJADA, 4);
        return mapa;
    }

    @Override
    public int compareTo(Tarefa o) {
        if(verificaPrioridade(this) < verificaPrioridade(o) ){
            return -1;
        }
        else if(verificaPrioridade(this) > verificaPrioridade(o)) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s;%s;%s;%s;%s",
                getNome(), getDescricao(), sdf.format(getDataDeTermino()), getCategoria(), getStatus()));
        return sb.toString();
    }
}
