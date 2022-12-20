package modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import dados.FilmeDados;

/********************************************
 * Autor: Raman Melo Cavalcante Matricula: 201820754 Inicio: 25/07/2021 Ultima
 * alteracao: 01/08/2021 Nome: Filme Funcao: Classe do objeto Filme
 ********************************************/
public class Filme implements Serializable {
	private String nome;
	private String diretor;
	private String genero;
	private String comentario;
	private String nickNameF;

	public Filme() {
	}

	public Filme(String nome, String diretor, String genero, String comentario, String nickNameF) {
		this.nome = nome;
		this.diretor = diretor;
		this.genero = genero;
		this.comentario = comentario;
		this.nickNameF = nickNameF;
	}

	/******************************************
	 * Metodo: adicionarFilme Funcao: chama o metodo adicionarFilme da classe
	 * FilmeDados Parametros: Objto do tipo Filme Retorno: void
	 ******************************************/
	public void adicionarFilme(Filme objFilme) {
		FilmeDados fd = new FilmeDados();
		fd.adicionarFilme(objFilme);
	}

	/******************************************
	 * Metodo: listarFilme Funcao: chama o metodo listarFime da classe FilmeDados
	 * Parametros: não recebe parametros Retorno: ArrayList do Tipo Filme
	 ******************************************/
	public ArrayList<Filme> listarFilme() throws FileNotFoundException, ClassNotFoundException, IOException {
		FilmeDados fd = new FilmeDados();
		return fd.listarFilme();
	}

	public void setNome(String filme) {
		this.nome = filme;
	}

	public String getNome() {
		return this.nome;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getGenenro() {
		return genero;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setNickName(String nickNameF) {
		this.nickNameF = nickNameF;
	}

	public String getNickName() {
		return this.nickNameF;
	}
}
