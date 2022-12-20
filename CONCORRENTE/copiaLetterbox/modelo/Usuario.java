package modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import dados.UsuarioDados;

/********************************************
 * Autor: Raman Melo Cavalcante Matricula: 201820754 Inicio: 25/07/2021 Ultima
 * alteracao: 01/08/2021 Nome: Usuario Funcao: Classe do objeto Usuario
 ********************************************/
public class Usuario implements Serializable {
	private String nickName;
	private String email;
	private long senha;

	public Usuario() {
	}

	public Usuario(String nickName, String email, long senha) {
		this.nickName = nickName;
		this.email = email;
		this.senha = senha;
	}

	/******************************************
	 * Metodo: cadastrarUsuario Funcao: chama o metodo adicionarUsuario da classe
	 * UsuarioDados Parametros: Objto do tipo Usuario Retorno: void
	 ******************************************/
	public void cadastrarUsuario(Usuario objUser) {
		UsuarioDados objUserDados = new UsuarioDados();
		objUserDados.adicionarUsuario(objUser);
	}

	/******************************************
	 * Metodo: pesquUsuario Funcao: chama o metodo pesquisaUsuario da classe
	 * UsuarioDados Parametros: nickName do tipo String Retorno: objeto do tipo
	 * Usuario
	 ******************************************/
	public Usuario pesquUsuario(String nickName) throws FileNotFoundException, ClassNotFoundException, IOException {
		UsuarioDados userD = new UsuarioDados();
		Usuario retorno = userD.pesquisaUsuario(nickName);
		return retorno;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setSenha(long senha) {
		this.senha = senha;
	}

	public long getSenha() {
		return this.senha;
	}
}
