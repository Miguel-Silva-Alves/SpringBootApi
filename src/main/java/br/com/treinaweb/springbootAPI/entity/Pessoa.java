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
        //cria lista para guardar numeros
        int[] numeros_cpf = new int[10];

        //converte a string em numero e insere na lista
        int nmr1 = Integer.parseInt(String.valueOf(cpf.charAt(0)));
        numeros_cpf[0] = nmr1;

        int nmr2 = Integer.parseInt(String.valueOf(cpf.charAt(1)));
        numeros_cpf[1] = nmr2;

        int nmr3 = Integer.parseInt(String.valueOf(cpf.charAt(2)));
        numeros_cpf[2] = nmr3;

        //2 - verifica se o primeiro ponto está no lugar correto
        String ponto1 = String.valueOf(cpf.charAt(3));
        if (!ponto1.equals(ponto)){
            return false;
        }

        //3 - verifica se os 3 próximos caracteres são números
        if (!cpf.substring(4,7).matches("[0-9]*")){
            return false;
        }
        //converte a string em numero e insere na lista
        int nmr4 = Integer.parseInt(String.valueOf(cpf.charAt(4)));
        numeros_cpf[3] = nmr4;

        int nmr5 = Integer.parseInt(String.valueOf(cpf.charAt(5)));
        numeros_cpf[4] = nmr5;

        int nmr6 = Integer.parseInt(String.valueOf(cpf.charAt(6)));
        numeros_cpf[5] = nmr6;

        //4 - verifica se o segundo ponto está no lugar certo
        String ponto2 = String.valueOf(cpf.charAt(7));
        if (!ponto2.equals(ponto)){
            return false;
        }

        //5 - verifica se os 3 proximos caracteres são numeros
        if (!cpf.substring(8,11).matches("[0-9]*")){
            return false;
        }
        //converte a string em numero e insere na lista
        int nmr7 = Integer.parseInt(String.valueOf(cpf.charAt(8)));
        numeros_cpf[6] = nmr7;

        int nmr8 = Integer.parseInt(String.valueOf(cpf.charAt(9)));
        numeros_cpf[7] = nmr8;

        int nmr9 = Integer.parseInt(String.valueOf(cpf.charAt(10)));
        numeros_cpf[8] = nmr9;

        //6 - verifica se o tracinho esta no lugar certo
        String tracinhoIndx = String.valueOf(cpf.charAt(11));
        if (!tracinhoIndx.equals(tracinho)){
            return false;
        }

        //7 - verifica se os 2 proximos caracteres são numeros
        if (!cpf.substring(12).matches("[0-9]*")){
            return false;
        }
        //8 - verifica se o primeiro dentre os dois numeros segue o padrao

        //fazer uma lista com os numeros do cpf

        //multiplicar cada numero da lista pelos numeros necessarios
        //somar todos os resultados das multiplicações
        int soma1 = 0;
        int multiplicador1 = 10;
        int numero_index1 = 0;
        while (multiplicador1 >= 2){
            soma1 += numeros_cpf[numero_index1] * multiplicador1;
            multiplicador1--;
            numero_index1++;
        }
        System.out.println(soma1);

        //pegar o resto da divisão da soma por 11
        int resto_divisao1 = soma1 % 11;

        //fazer 11 - resto da divisão anterior
        int numero1_correto = 11 - resto_divisao1;

        //compara se o resultado da conta foi igual oq está inserido
        int nmr10 = Integer.parseInt(String.valueOf(cpf.charAt(12)));
        if (nmr10 != numero1_correto) {
            //retorna falso se o numero que foi inserido for diferente do numero que deveria ser segundo a regra
            return false;
        }
        //se o numero estiver correto vai salvar na lista
        numeros_cpf[9] = numero1_correto;

        //9 - verifica se o segundo numero segue as regras necessarias para ser valido

        //multiplicar cada numero da lista pelos numeros necessarios
        //somar todos os resultados das multiplicações
        int soma2 = 0;
        int multiplicador2 = 11;
        int numero_index2 = 0;
        while (multiplicador2 >= 2){
            soma2 += numeros_cpf[numero_index2] * multiplicador2;
            multiplicador2--;
            numero_index2++;
        }
        System.out.println(soma2);

        //pegar o resto da divisão da soma por 11
        int resto_divisão2 = soma2 % 11;

        //Se o resto da divisão for menor que 2, então o dígito é igual a 0
        int numero2_correto = 0 ;
        if (resto_divisão2 >= 2){
            numero2_correto = 11 - resto_divisão2;
        }

        //compara se o resultado da conta foi igual oq está inserido
        int nmr11 = Integer.parseInt(String.valueOf(cpf.charAt(13)));
        if (nmr11 != numero2_correto) {
            //retorna falso se o numero que foi inserido for diferente do numero que deveria ser segundo a regra
            return false;
        }
        
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