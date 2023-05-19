package br.com.treinaweb.springbootAPI.entity;


import br.com.treinaweb.springbootAPI.repositorry.PessoaRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Pessoa
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String nome;

    private String cpf;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean validate_cpf(String cpf){
        String ponto = ".";
        String tracinho = "-";
        if (cpf.length() != 14) {
            return false;
        }
        //reparte a string em partes verificando se cada uma possui os caracteres possiveis

        //1 - verifica se os 3 primeiros caracteres são numeros
        if (!cpf.substring(0,3).matches("[0-9]*")){
            return false;
        }
        //2 - verifica se o primeiro ponto está no lugar correto
        String ponto1 = String.valueOf(cpf.charAt(3));
        if (!ponto1.equals(ponto)){
            return false;
        }

        //3 - verifica se os 3 próximos caracteres são números
        if (!cpf.substring(4,7).matches("[0-9]*")){
            return false;
        }

        //4 - verifica se o segundo ponto está no lugar certo
        String ponto2 = String.valueOf(cpf.charAt(7));
        if (!ponto2.equals(ponto)){
            return false;
        }

        //5 - verifica se os 3 proximos caracteres são numeros
        if (!cpf.substring(8,11).matches("[0-9]*")){
            return false;
        }

        //6 - verifica se o tracinho esta no lugar certo
        String tracinhoIndx = String.valueOf(cpf.charAt(11));
        if (!tracinhoIndx.equals(tracinho)){
            return false;
        }

        //7 - verifica se os 2 proximos caracteres são numeros
        if (!cpf.substring(12).matches("[0-9]*")){
            return false;
        }
        //8 - verifica se os caracteres verificados no anterior seguem o padrao
        return true;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}