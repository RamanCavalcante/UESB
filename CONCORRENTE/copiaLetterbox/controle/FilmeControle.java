package controle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Filme;

/********************************************
 * Autor: Raman Melo Cavalcante Matricula: 201820754 Inicio: 25/07/2021 Ultima
 * alteracao: 01/08/2021 Nome: FilmeControle Funcao: recebe dados e chama
 * metodos da classe Filme
 ********************************************/
public class FilmeControle {
	/******************************************
	 * Metodo: adicionarFilme Funcao: chama o metodo adicionarFilme da classe Filme
	 * Parametros: String nome, String diretor, String genero, Sring comentario,
	 * String nickName Retorno: void
	 ******************************************/
	public void adicionarFilme(String nome, String diretor, String genero, String comentario, String nickName) {
		Filme f = new Filme(nome, diretor, genero, comentario, nickName);
		f.adicionarFilme(f);
	}

	/******************************************
	 * Metodo: listarFilme Funcao: compara o nick para imprimir os filmes
	 * adicionados pelo usuario Parametros: String nick Retorno: Uma String com os
	 * filmes do usuario
	 ******************************************/
	public String listarFilme(String nick) throws FileNotFoundException, ClassNotFoundException, IOException {
		Filme f = new Filme();
		String listFilmes = "";
		ArrayList<Filme> retorno = f.listarFilme();

		for (int i = 0; i < retorno.size(); i++) {// guarda os filmes do usuario na string
			if (retorno.get(i).getNickName().equals(nick)) {
				listFilmes += "\n----------------------------------------------------------" + "\n" + retorno.get(i).getNome()
						+ " - Dirigio por: " + retorno.get(i).getDiretor() + "\n" + retorno.get(i).getComentario() + " \n"
						+ retorno.get(i).getNickName() + "\n----------------------------------------------------------";
			}
		}
		return listFilmes;
	}
}
