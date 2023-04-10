package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import gui.GuiPacket;
import javafx.scene.Node;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: Packet
 * Funcao...........: classe dos pacotes com os pontos menssagem
 * ***************************************************************/
public class Packet {

  private String msg;
  private PointsRoutes routesPoints;
  private GuiPacket gui;
  private List<Router> listRoutesPassed = new ArrayList<>();
  private String serial;
  private int TLL;
  public Packet(String msg, PointsRoutes routesPoints){
    this.msg = msg;
    this.routesPoints = routesPoints;
    this.serial = ""+(int) (Math.random() * 900000) + 100000;
    this.TLL = 26;
  }

  public void decrementTTL(){
    this.TLL--;
  }

  public int getTTL(){
    return this.TLL;
  }

  public String getSerial(){
    return serial;
  }

  public List<Router> getListRoutesPassed(){
    return this.listRoutesPassed;
  }

  public void setCurrentroute(Router route){
      routesPoints.setCurrentRoute(route);
  }

  public void setPrevious(Router route){
    routesPoints.setPreviousRoute(route);
  }

  public Optional<Router> getPrevious(){
    if(isNull(routesPoints.getPreviousRoute())){
      return Optional.empty();
    }else{
      return Optional.of(routesPoints.getPreviousRoute());
    }
  }

  public void resetDestiny(){
    this.routesPoints.setDestiny(new Router());
  }

  public String getMsg(){
    return this.msg;
  }

  public PointsRoutes getPoints(){
    return this.routesPoints;
  }
  public boolean isNull(Object obj) {
    return obj == null;
  }
}
