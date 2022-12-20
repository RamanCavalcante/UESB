package dados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import modelo.Filme;

/********************************************
 * Autor: Raman Melo Cavalcante Matricula: 201820754 Inicio: 25/07/2021 Ultima
 * alteracao: 01/08/2021 Nome: FilmeDados Funcao: Gravar objeto do tipo filme no
 * arquivo .ser e listar os objetos gravados
 ********************************************/

public class FilmeDados implements Serializable {

	/******************************************
	 * Metodo: aicionaFilme Funcao: gravas objeto do filme no arquivo .ser
	 * Parametros: Filme objFilme Retorno: void
	 ******************************************/
	public void adicionarFilme(Filme objFilme) {
		ArrayList<Filme> listFilme = new ArrayList<>();
		try {
			listFilme = listarFilme();
		} catch (FileNotFoundException e) {
			File arquivo = new File("filmes.ser");
			try {
				arquivo.createNewFile();
			} catch (IOException x) {
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		try {
			listFilme.add(objFilme);
			File endereco = new File("filmes.ser");
			FileOutputStream fluxo = new FileOutputStream(endereco);
			ObjectOutputStream oos = new ObjectOutputStream(fluxo);
			oos.writeObject(listFilme);
			oos.flush();
			fluxo.flush();
			oos.close();
			fluxo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/******************************************
	 * Metodo: listarFilme Funcao: acao do botao btnLogin, testa se o login e valido
	 * e chama PagUsuario.fxml Parametros: ActionEvent event Retorno:
	 * ArrayList<Filme> listFilme
	 ******************************************/
	public ArrayList<Filme> listarFilme() throws FileNotFoundException, IOException, ClassNotFoundException {
		ArrayList<Filme> listFilme = new ArrayList<>();
		File endereco = new File("filmes.ser");
		FileInputStream fluxo = new FileInputStream(endereco);
		ObjectInputStream lerObj = new ObjectInputStream(fluxo);
		try {
			listFilme = (ArrayList<Filme>) lerObj.readObject();
		} catch (ClassCastException e) {
			e.getMessage();
		}
		lerObj.close();
		return listFilme;
	}
}
