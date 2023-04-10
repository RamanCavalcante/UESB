package util;
import java.util.List;
import java.util.stream.Collectors;

import model.Packet;
import model.Router;
/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: FloodRouting
 * Funcao...........: metodo de inunação com interface food
 * ***************************************************************/
public class FloodRoutingExceptPreviousTTL implements Flood{

  @Override
  public List<Router> floodRouting(Router route) {
    Packet packet = route.getListPackets().peek();
    packet.decrementTTL();

    List<Router> listNeighbors =  route.getNeighbors();
    List<Router> listRoutersPrevious = route.getListPackets().peek().getListRoutesPassed();

    List<Router> routersToSend = listNeighbors
      .stream()
      .filter(router -> !listRoutersPrevious.contains(router))
      .collect(Collectors.toList());


      return routersToSend;
  }

  public String toString(){
    return "Inundacao TTL Opcao 3";
  }
}
