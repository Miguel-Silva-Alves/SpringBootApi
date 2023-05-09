package br.com.treinaweb.springbootAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @NotNull
    private String nome;

    private String raca;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id =  id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome =  nome;
    }

    public String getraca(){
        return raca;
    }

    public void setRaca(String raca){
        this.raca = raca;
    }

    @Override
    public String toString(){
        return "Animal{" +
                "id=" + id +
                ", nome=" + nome +
                ", raça=" + raca + '\'' +
                "}";
    }

}
