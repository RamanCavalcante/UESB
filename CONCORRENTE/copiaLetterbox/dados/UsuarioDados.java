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
import modelo.Usuario;

/********************************************
 * Autor: Raman Melo Cavalcante Matricula: 201820754 Inicio: 25/07/2021 Ultima
 * alteracao: 01/08/2021 Nome: UsuarioDados Funcao: Gravas objtos do tipo
 * Usuario no .ser, realiza pesquisa, e lista os objetos gravados
 ********************************************/
public class UsuarioDados implements Serializable {

	/******************************************
	 * Metodo: adicionarUsuario Funcao: gravas objeto do Usuario no arquivo .ser
	 * Parametros: Usuario objUsuario Retorno: void
	 ******************************************/
	public void adicionarUsuario(Usuario objUsuario) {
		ArrayList<Usuario> listUsuario = new ArrayList<>();
		try {
			listUsuario = listarUsuario();
		} catch (FileNotFoundException e) {
			File arquivo = new File("usuarios.ser");
			try {
				arquivo.createNewFile();
			} catch (IOException x) {
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		try {
			listUsuario.add(objUsuario);
			File endereco = new File("usuarios.ser");
			FileOutputStream fluxo = new FileOutputStream(endereco);
			ObjectOutputStream oos = new ObjectOutputStream(fluxo);
			oos.writeObject(listUsuario);
			oos.flush();
			fluxo.flush();
			oos.close();
			fluxo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/******************************************
	 * Metodo: listarUsaurio Funcao: lista os usario gravado no arquivo .ser
	 * Parametros: sem parametros Retorno: ArrayList<Usuario> listUsuario
	 ******************************************/
	public ArrayList<Usuario> listarUsuario() throws FileNotFoundException, IOException, ClassNotFoundException {
		ArrayList<Usuario> listUsuario = new ArrayList<>();
		File endereco = new File("Usuarios.ser");
		FileInputStream fluxo = new FileInputStream(endereco);
		ObjectInputStream lerObj = new ObjectInputStream(fluxo);
		try {
			listUsuario = (ArrayList<Usuario>) lerObj.readObject();
		} catch (ClassCastException e) {
			e.getMessage();
		}
		lerObj.close();
		return listUsuario;
	}

	/******************************************
	 * Metodo: pesquisaUsario Funcao: Pesquisa o Usuario Parametros: String nickName
	 * Retorno: Ususario objUsuario
	 ******************************************/
	public Usuario pesquisaUsuario(String nickName) throws FileNotFoundException, ClassNotFoundException, IOException {
		Usuario objUsuario = new Usuario();
		ArrayList<Usuario> listUsuario = listarUsuario();
		for (int i = 0; i < listUsuario.size(); i++) {
			if (listUsuario.get(i).getNickName().equals(nickName)) {
				objUsuario = listUsuario.get(i);
			}
		}
		return objUsuario;
	}
}
