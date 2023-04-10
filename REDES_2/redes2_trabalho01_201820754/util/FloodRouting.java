package util;

import java.util.List;
import model.Router;
/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: FloodRouting
 * Funcao...........: metodo de inunação com interface food
 * ***************************************************************/
public class FloodRouting implements Flood{

  @Override
  public List<Router> floodRouting(Router route) {
    route.getListPackets().peek().resetDestiny();
    return route.getNeighbors();
  }

  public String toString(){
    return "Inundacao Opcao 1";
  }
}
