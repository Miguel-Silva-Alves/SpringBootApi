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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", raca='" + raca + '\'' +
                ", pessoa=" + pessoa +
                '}';
    }
}
