package controller;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import controle.FilmeControle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/********************************************
 * Autor: Raman Melo Cavalcante Matricula: 201820754 Inicio: 25/07/2021 Ultima
 * alteracao: 01/08/2021 
 * Nome: PagCadastroController 
 * Funcao: realiza o cadastro
 * do usuario
 ********************************************/
public class PagUsuarioController {

	@FXML
	private AnchorPane pagUsuario;

	@FXML
	private Label labeListFilmes;

	@FXML
	private Button btnAddFilme;

	@FXML
	private Button btnQuit;

	/******************************************
	 * Metodo: initialize Funcao: lista todos o filmes adicionados pelo usuario
	 * Parametros: Objto do tipo Usuario Retorno: void
	 ******************************************/
	@FXML
	void initialize() throws FileNotFoundException, ClassNotFoundException, IOException {
		try {
			FilmeControle objC = new FilmeControle();
			labeListFilmes.setText(objC.listarFilme(TelaInicialController.nick));// lista so filmes do usuario logado
		} catch (EOFException e) {// caso o usuario não tenha nenhum filme adicionado
			labeListFilmes.setText("Nenhum filme adicionado");
		}
	}

	@FXML
	/******************************************
	 * Metodo: padAddFilme Funcao: acao botao btnAddFilme, chama PagAddFilme.fxml
	 * Parametros: ActionEvent event Retorno: void
	 ******************************************/
	void pagAddFilme(ActionEvent event) throws IOException {
		PagAddFilmeController objAddFilme = new PagAddFilmeController();
		FXMLLoader usuarioLoader = new FXMLLoader(getClass().getResource("/layout/PagAddFilme.fxml"));
		Parent pagAddFilme = usuarioLoader.load();
		pagUsuario.getChildren().setAll(pagAddFilme);
	}

	@FXML
	/******************************************
	 * Metodo: sair Funcao: acao botao btnQuit, chama TelaInicial.fxml Parametros:
	 * ActionEvent event Retorno: void
	 ******************************************/
	void sair(ActionEvent event) throws IOException {
		FXMLLoader usuarioLoader = new FXMLLoader(getClass().getResource("/layout/TelaInicial.fxml"));
		Parent pagCadastro = usuarioLoader.load();
		pagUsuario.getChildren().setAll(pagCadastro);
	}
}
