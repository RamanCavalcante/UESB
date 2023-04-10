package model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import gui.GuiPacket;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: Router
 * Funcao...........: Classe que gerencia cada pacotes contidos no roteador
 * ***************************************************************/
public class Router extends Thread{
  private String name;
  private LayoutXAndY layoutXandY;
  private RadioButton radioButton;
  private List <Router> neighborsNode;
  private ConcurrentLinkedQueue<Packet> listOfpackets;
  private List<Router> listNeighborsToSend;
  private GuiPacket gui;
  private boolean destinty;
  private boolean isPreviouNode;
  private boolean stayAlive;
  /**
   *
   * @param radioButton
   */

  public Router(){}
  public Router(RadioButton radioButton){
    this.name = radioButton.getId();
    this.layoutXandY = new LayoutXAndY(radioButton.getLayoutX(), radioButton.getLayoutY());
    this.radioButton = radioButton;
    this.listOfpackets = new ConcurrentLinkedQueue<Packet>();
    this.neighborsNode = new ArrayList<>();
    this.stayAlive = true;

  }

  @Override
  public void run() {

  while(stayAlive){
    if (!listOfpackets.isEmpty()) {
      listNeighborsToSend = Network.flooding.floodRouting(this);
      Packet packet = listOfpackets.poll();
        for (Router nextRouter : listNeighborsToSend) {
            movePacketAndCallReceived(this, nextRouter, packet);
        }
      }
    }
  }

  /**
   *
   * @param currentRoute
   * @param nextRoute
   * @param packet
   */
  public void movePacketAndCallReceived(Router currentRoute, Router nextRoute, Packet packet){
    Platform.runLater(()->{
      gui.setTextLabel(Network.quantityOfPackets.incrementAndGet());});
    updatePointsOfPacket(currentRoute, nextRoute, packet);
    routerPassed(packet, currentRoute);
    packet.decrementTTL();

    HashMap<TranslateTransition, ImageView> move = gui.movePacket(currentRoute, nextRoute);
    Iterator<Map.Entry<TranslateTransition, ImageView>> iterator = move.entrySet().iterator();
    if(iterator.hasNext()){
        Map.Entry<TranslateTransition, ImageView> entry = iterator.next();
        entry.getKey().setOnFinished(envet ->{
          nextRoute.receivedPacket(packet);
          gui.deletPacket(entry.getValue());
          arrivedAtDestiny(packet, nextRoute);
        });
        entry.getKey().play();
    }
  }

  /**
   *
   * @param packet
   * @param route
   */
  public void arrivedAtDestiny(Packet packet, Router route){
    if(packet.getPoints().getDestiny().equals(route)){
      generatingReport(packet, Network.quantityOfPackets);
      Network.suicideThreadsAndCleanList();
      Network.quantityOfPackets = new AtomicInteger(0);
      gui.messageArrivied(route);
    }
  }

  /**
   *
   * @param packet
   * @param numberOfPacket
   */
  public void generatingReport(Packet packet, AtomicInteger numberOfPacket){
    gui.report(packet, numberOfPacket);
  }

  /**
   *
   * @param packet
   * @param route
   */
  public void routerPassed(Packet packet, Router route){
      packet.getListRoutesPassed().add(route);
  }

  /**
   *
   * @param currentRoute
   * @param nextRoute
   * @param packet
   */
  public void updatePointsOfPacket(Router currentRoute, Router nextRoute, Packet packet){
    packet.setPrevious(currentRoute);
    packet.setCurrentroute(nextRoute);
  }

  public void setStayAlive(Boolean alive){
    this.stayAlive = alive;
  }

  /**
   *
   * @param packet
   */
  public void receivedPacket(Packet packet){
    addnewPacket(packet);
      if(!isAlive() && stayAlive){
        Network.addThreadInList(this);
        start();
        System.out.printf("Pacote add %s \n", this.name);
      }
  }

  public void addnewPacket(Packet packet){
    if (!findSerial(packet)) {
        this.listOfpackets.add(packet);
    }
  }

  /**
   *
   * @param packet
   * @return
   */
  public boolean findSerial(Packet packet){
    if(listOfpackets.stream().anyMatch( p -> p.getSerial().equals(packet.getSerial()))){
      return true;
    }else{
      return false;
    }
  }

  public String toString(){
    return this.name;
  }
  public ConcurrentLinkedQueue<Packet> getListPackets(){
    return this.listOfpackets;
  }

  public void setGuiPackets(GuiPacket gui){
    this.gui = gui;
  }

  public double getLayoutX(){
    return layoutXandY.getX();
  }

  public double getLayoutY(){
    return layoutXandY.getY();
  }

  public void addPackets(Packet packet){
    listOfpackets.add(packet);
  }

  public void setNeighbors(Router... nodes){
    neighborsNode.addAll(Arrays.asList(nodes));
  }

  public List<Router> getNeighbors(){
    return this.neighborsNode;
  }

  public boolean isDestinty() {
    return destinty;
  }

  public void setDestinty(boolean destinty) {
    this.destinty = destinty;
  }

  public RadioButton getRadioButton(){
    return this.radioButton;
  }

}
