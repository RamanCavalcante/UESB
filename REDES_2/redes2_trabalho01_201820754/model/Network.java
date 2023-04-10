package model;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import gui.GuiPacket;
import util.Flood;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: Network
 * Funcao...........: classe que guarda todos os roteadores e acoes
 * ***************************************************************/

public class Network {

  private List<Router> listRouters;
  private Router originNode;
  private Router destinyNode;
  private Packet packet;
  private static ConcurrentLinkedQueue<Router> listThreadsOfRouters = new ConcurrentLinkedQueue<Router>();

  public static Flood flooding;

  public static AtomicInteger quantityOfPackets =  new AtomicInteger(0);
  public Network(){}

  public Network(List<Router> listRouters, Router originNode, Router destinyNode, Flood flooding, String msg, GuiPacket gui){
    this.listRouters = listRouters;
    this.originNode = originNode;
    this.destinyNode = destinyNode;
    this.flooding = flooding;
    this.packet = new Packet(msg, new PointsRoutes(originNode, destinyNode));
  }

  public List<Router> geListtRouters(){
    return this.listRouters;
  }

  public void starNetowrk(){
    originNode.receivedPacket(packet);
  }

  public List<Router> getListFlooding(Router route){
    return flooding.floodRouting(route);
  }

  public static void addThreadInList(Router route){
    listThreadsOfRouters.add(route);
  }

  public static void suicideThreadsAndCleanList(){
    listThreadsOfRouters
      .stream()
      .forEach( route->{
        route.setStayAlive(false);
      });

    listThreadsOfRouters.clear();
  }
}
