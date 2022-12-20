package controller;

import java.io.IOException;

import controle.UsuarioControle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

/********************************************
 * Autor: Raman Melo Cavalcante Matricula: 201820754 Inicio: 25/07/2021 Ultima
 * alteracao: 01/08/2021 Nome: PagCadastroController Funcao: realiza o cadastro
 * do usuario
 ********************************************/
public class TelaInicialController {

	@FXML
	private AnchorPane telaInicialID;

	@FXML
	private Button btnLogin;

	@FXML
	private TextField textFieldNickName;

	@FXML
	private PasswordField textFieldSenha;

	@FXML
	private Button btnCadastro;

	public static String nick;

	/******************************************
	 * Metodo: login Funcao: acao do botao btnLogin, testa se o login e valido e
	 * chama PagUsuario.fxml Parametros: ActionEvent event Retorno: void
	 ******************************************/
	@FXML
	void login(ActionEvent event) throws IOException, ClassNotFoundException {
		PagUsuarioController UserControle = new PagUsuarioController();
		UsuarioControle controll = new UsuarioControle();

		try {
			String nickName = textFieldNickName.getText();
			long senha = Long.parseLong(textFieldSenha.getText());
			nick = nickName;
			boolean verificado = controll.verificaLogin(nickName, senha);// chama o metodo para verificar o login da
																																		// UsuarioControle
			if (verificado == false) {// caso o usuario erre a senha
				Alert alerta = new Alert(AlertType.INFORMATION);
				alerta.setTitle("Error");
				alerta.setHeaderText(null);
				alerta.setContentText("Senha ou usuario incorreto");
				alerta.showAndWait();
			} else {// caso o usuario acerte o login
				FXMLLoader usuarioLoader = new FXMLLoader(getClass().getResource("/layout/PagUsuario.fxml"));
				Parent pagUsuario = usuarioLoader.load();
				telaInicialID.getChildren().setAll(pagUsuario);
			}
		} catch (NullPointerException e) {// caso o usuario digite um nikName que não existe
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Error");
			alerta.setHeaderText(null);
			alerta.setContentText("Usuario nao encontrado. Tente novamente");
			alerta.showAndWait();
			textFieldSenha.setText("");
		} catch (NumberFormatException x) {// caso o usuario digite um tipo nao aceito
			Alert alerta = new Alert(AlertType.INFORMATION);
			alerta.setTitle("Error");
			alerta.setHeaderText(null);
			alerta.setContentText("Login digitado nao valido. Tente novamente");
			alerta.showAndWait();
			textFieldNickName.setText("");
			textFieldSenha.setText("");
		}
	}

	/******************************************
	 * Metodo: openCadastro Funcao: acao do botao btnCadastro, chama
	 * PagCadastro.fxml Parametros: ActionEvent event Retorno: void s
	 ******************************************/
	@FXML
	void openCadastro(ActionEvent event) throws IOException {
		PagCadastroController pagCad = new PagCadastroController();
		FXMLLoader usuarioLoader = new FXMLLoader(getClass().getResource("/layout/PagCadastro.fxml"));
		Parent pagCadastro = usuarioLoader.load();
		telaInicialID.getChildren().setAll(pagCadastro);
	}
}
