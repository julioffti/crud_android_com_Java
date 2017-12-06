package com.example.julio.crud.modelo;

import java.io.Serializable;

/**
 * Created by Julio on 04/10/2017.
 */

public class Pessoa implements Serializable {
    private int id;
    private String nome;
    private int idade;
    private String endereco;
    private String telefone;

    public Pessoa() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String toString(){
        return nome.toString();
    }
}
