package controller;

import java.io.IOException;

import controle.UsuarioControle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

/********************************************
 * Autor: Raman Melo Cavalcante Matricula: 201820754 Inicio: 25/07/2021 Ultima
 * alteracao: 01/08/2021 Nome: PagCadastroController Funcao: realiza o cadastro
 * do usuario
 ********************************************/
public class PagCadastroController {

	@FXML
	private AnchorPane telaCadastro;

	@FXML
	private TextField TextFieldNickName;

	@FXML
	private TextField TextFieldEmail;

	@FXML
	private TextField TextFieldSenha;

	@FXML
	private Button btnCadastrarUser;

	@FXML
	private Button btnQuit;

	/******************************************
	 * Metodo: cadastroUser Funcao: acao do btnCadastrarUser, cadastro usuario
	 * Parametros: ActionEvent event Retorno: void
	 ******************************************/
	@FXML
	void cadastroUser(ActionEvent event) throws IOException {
		try {
			UsuarioControle objC = new UsuarioControle();
			String verificaNickName = TextFieldNickName.getText();
			String verificaEmail = TextFieldEmail.getText();
			String verificaSenha = TextFieldSenha.getText();

			if (verificaSenha.length() == 0 | verificaEmail.length() == 0 | verificaNickName.length() == 0) {
				// verifica se o espaço na caixa da senha foi preenchido
				Alert alerta = new Alert(AlertType.INFORMATION);
				alerta.setTitle("Error");
				alerta.setHeaderText(null);
				alerta.setContentText("preencha todos os campos!");
				alerta.showAndWait();

			} else {// mensagem de alerta e chamando metedo para abrir TelaInicial
				String nickName = verificaNickName;
				String email = verificaEmail;
				long senha = Long.parseLong(verificaSenha);
				objC.cadastrarUsuario(nickName, email, senha);
				Alert alerta = new Alert(AlertType.INFORMATION);
				alerta.setTitle("Error");
				alerta.setHeaderText(null);
				alerta.setContentText("Usuario cadastrado com sucesso!!");
				alerta.showAndWait();
				openInicial(event);
			}
		} catch (NumberFormatException e) {// caso o usuario digite um dados do tipo nao aceito
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Error");
			alerta.setHeaderText(null);
			alerta.setContentText("A senha deve conter apenas numeros");
			alerta.showAndWait();
			TextFieldSenha.setText("");
		}
	}

	/******************************************
	 * Metodo: sair Funcao: acao do btnQuit, chama TelaInicial.fxml Parametros:
	 * ActionEvent event Retorno: void
	 ******************************************/
	@FXML
	void sair(ActionEvent event) throws IOException {
		FXMLLoader usuarioLoader = new FXMLLoader(getClass().getResource("/layout/TelaInicial.fxml"));
		Parent PagInicial = usuarioLoader.load();
		telaCadastro.getChildren().setAll(PagInicial);
	}

	/******************************************
	 * Metodo: sair Funcao: acao logo depois que o cadastro do usuario ser efetuado
	 * Parametros: ActionEvent event Retorno: void
	 ******************************************/
	@FXML
	void openInicial(ActionEvent event) throws IOException {// Esse evento retorna para a pagina inicial
		FXMLLoader usuarioLoader = new FXMLLoader(getClass().getResource("/layout/TelaInicial.fxml"));
		Parent PagInicial = usuarioLoader.load();
		telaCadastro.getChildren().setAll(PagInicial);
	}

}
