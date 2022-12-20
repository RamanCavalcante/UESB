package controle;

import java.io.FileNotFoundException;
import java.io.IOException;

import modelo.Usuario;

/********************************************
 * Autor: Raman Melo Cavalcante Matricula: 201820754 Inicio: 25/07/2021 Ultima
 * alteracao: 01/08/2021 Nome: UsuarioControle 
 * Funcao: recebe dados e chama
 * classes
 ********************************************/
public class UsuarioControle {
	/******************************************
	 * Metodo: cadastrarUsuario Funcao: chama o metodo cadastrarUsuario da classe
	 * Usuario Parametros: String nickName, String email, logn senha 
	 * Retorno: void
	 ******************************************/
	public void cadastrarUsuario(String nickName, String email, long senha) {
		Usuario objUser = new Usuario(nickName, email, senha);
		objUser.cadastrarUsuario(objUser);
	}

	/******************************************
	 * Metodo: pesquisaUsuario Funcao: chama o metodo pesquUsuario da classe Usuario
	 * Parametros: String NickName Retorno: objeto do tipo Usuario
	 ******************************************/
	public Usuario pesquisaUsuario(String nickName) throws FileNotFoundException, ClassNotFoundException, IOException {
		Usuario objUsuario = new Usuario();
		Usuario retornoPesq = objUsuario.pesquUsuario(nickName);
		return retornoPesq;
	}

	/******************************************
	 * Metodo: verificaLogin Funcao: verifica se o login e senha digitada
	 * Parametros: String NickName, long Retorno: boolean
	 ******************************************/
	public boolean verificaLogin(String nickName, long senha)
			throws FileNotFoundException, ClassNotFoundException, IOException {// metodo para verificar login do usuario
		Usuario retornoPesq = pesquisaUsuario(nickName);
		if (retornoPesq.getSenha() == senha) {
			return true;
		} else {
			return false;
		}
	}
}
