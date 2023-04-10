package util;

import java.util.List;
import model.Router;
/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: Flood
 * Funcao...........: interface de flutuacao
 * ***************************************************************/
public interface Flood {
  List<Router> floodRouting(Router route);
  String toString();
}
