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
            System.out.println("Aqui 1");
            return false;
        }
        //reparte a string em partes verificando se cada uma possui os caracteres possiveis

        //1 - verifica se os 3 primeiros caracteres são numeros
        if (!cpf.substring(0,3).matches("[0-9]*")){
            System.out.println("Aqui 2");
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
            System.out.println("Aqui 3");
            return false;
        }

        //3 - verifica se os 3 próximos caracteres são números
        if (!cpf.substring(4,7).matches("[0-9]*")){
            System.out.println("Aqui 4");
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
            System.out.println("Aqui 5");
            return false;
        }

        //5 - verifica se os 3 proximos caracteres são numeros
        if (!cpf.substring(8,11).matches("[0-9]*")){
            System.out.println("Aqui 6");
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
            System.out.println("Aqui 7");
            return false;
        }

        //7 - verifica se os 2 proximos caracteres são numeros
        if (!cpf.substring(12).matches("[0-9]*")){
            System.out.println("Aqui 8");
            return false;
        }

        int nmr10 = Integer.parseInt(String.valueOf(cpf.charAt(12)));
        int nmr11 = Integer.parseInt(String.valueOf(cpf.charAt(13)));


        //8 - verifica se o primeiro numero segue as regras necessarias para ser valido
        if (!compare_lastsNumbers(numeros_cpf,10,nmr10)){
            System.out.println("Aqui 9");
            return false;
        }

        //se o numero estiver correto vai salvar na lista para mandar para fazer a segunda validação
        numeros_cpf[9] = nmr10;


        //9 - verifica se o segundo numero segue as regras necessarias para ser valido
        if (!compare_lastsNumbers(numeros_cpf,11,nmr11)){
            System.out.println("Aqui 10");
            return false;
        }

        return true;
    }

    //METODO DE VERIFICAÇÃO DOS 2 ULTIMOS NUMEROS
    public boolean compare_lastsNumbers(int[] numeros_cpf, int multiplicador, int numero_comparado){
        int soma = 0;
        int numero_index = 0;
        while (multiplicador >= 2){
            soma += numeros_cpf[numero_index] * multiplicador;
            multiplicador--;
            numero_index++;
        }
        System.out.println(soma);
        // ok

        //pegar o resto da divisão da soma por 11
        int resto_divisao = soma % 11;

        //se for o segundo numero vai seguir outro padrao

        //se o resto da divisão for menor que 2, nmr = 0
        int numero_correto = 0;
        if (resto_divisao >= 2){
            numero_correto = 11 - resto_divisao;
        }

        //compara se o resultado da conta foi igual oq está inserido
        if (numero_comparado != numero_correto) {
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