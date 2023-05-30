package br.com.treinaweb.springbootAPI.entity;


import br.com.treinaweb.springbootAPI.repositorry.PessoaRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Animal> animals;

    public Pessoa() {

    }

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Pessoa(long id, String nome, String cpf, List<Animal> animals) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.animals = animals;
    }

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

    public boolean validate_cpf_just_numbers(String cpf){
        //verificar se tudo dentro são numeros
        if (!cpf.substring(0).matches("[0-9]*")){
            return false;
        }

        //percorrer a string verificada e transformar a string em numero
        int percorredor_da_String = 0;
        int[] numeros_cpf_convertidos = new int [11];
        while(percorredor_da_String < cpf.length()){

            //convertendo string para numero e adcionando na lista
            numeros_cpf_convertidos[percorredor_da_String] = Integer.parseInt(String.valueOf(cpf.charAt(percorredor_da_String)));

            percorredor_da_String++;
        }
        //fazer as verificações dos dois ultimos numeros

        //penultimo
        int penultimo = numeros_cpf_convertidos[9];
        if (!compare_lastsNumbers(numeros_cpf_convertidos, 10, penultimo)){
            return false;
        }

        //ultimo
        int ultimo = numeros_cpf_convertidos[10];
        if (!compare_lastsNumbers(numeros_cpf_convertidos,11,ultimo)){
            return false;
        }

        return true;
    }
    public boolean validate_cpf(String cpf){

        if(cpf.length() == 11){
            return validate_cpf_just_numbers(cpf);
        }

        //Tamanho de strings com formatação e sem fromatação
        if (cpf.length() != 14) {
            return false;
        }

        //CASO 1 - se a string tiver length == 14 deve ser tratada pela formatação

        //reparte a string em partes verificando se cada uma possui os caracteres possiveis

        String ponto = ".";
        String tracinho = "-";

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

        int nmr10 = Integer.parseInt(String.valueOf(cpf.charAt(12)));
        int nmr11 = Integer.parseInt(String.valueOf(cpf.charAt(13)));


        //8 - verifica se o primeiro numero segue as regras necessarias para ser valido
        if (!compare_lastsNumbers(numeros_cpf,10,nmr10)){
            return false;
        }

        //se o numero estiver correto vai salvar na lista para mandar para fazer a segunda validação
        numeros_cpf[9] = nmr10;


        //9 - verifica se o segundo numero segue as regras necessarias para ser valido
        if (!compare_lastsNumbers(numeros_cpf,11,nmr11)){
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

    //calcular gasto com todos os animais sendo lavados
    public double calcular_gasto(){

        double gasto_total = 0;
        //percorrer lista de animal
        int i = 0;
        while(i < animals.size()){

            //pegar o animal do indice da lista e referenciar como o animal atual a ser analizado
            Animal animal = animals.get(i);
            //somar o preço no gasto_total
            gasto_total+= animal.getPreco();

            i++;
        }

        return gasto_total;
    }

    public double calcular_gasto_byID(List<Long> ids){

        double gasto_total = 0;
        //percorrer lista de ids
        int order_id =0;
        while(order_id < ids.size()){

            // pegar do id do animal
            Long id = ids.get(order_id);

            // metodo chamado get_animal_by_id (int id) : Animal (Optional) or none
            Animal animal = get_animal_by_id(id);

            gasto_total+= animal.getPreco();

        }

        return gasto_total;
    }

    private Animal get_animal_by_id(Long id) {
        // this.animals -> todos os animais

        // percorrer a lista de animais
        int i = 0;
        while(i < this.animals.size()){
            // pegar o animal
            Animal animal = this.animals.get(i);

            // comparar o id do animal com o id do parametro
            if (animal.getId() == id){
                // retornar o animal
                return animal;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}