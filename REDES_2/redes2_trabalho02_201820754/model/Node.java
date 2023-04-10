package model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.RadioButton;
import javafx.scene.shape.Circle;



/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: Node.java
 * Funcao...........: classe objeto dos nos do grafo com parentes
 * ***************************************************************/

public class Node{

  private Map<Edge, Node> partnerMap;
  private String id;
  private List<Edge> listofEdges;
  private Node father;
  private Circle circle;
  private RadioButton rb;
  private int weightOfPath = 0;
  private boolean isOpen;
  private boolean hasFather = false;


  public Node(int id){
    this.id = String.valueOf(id);
    this.listofEdges = new ArrayList<>();
    this.isOpen = true;
    this.partnerMap = new HashMap<>();
  }


  public boolean getHasFather(){
    return hasFather;
  }
  public RadioButton getRb() {
    return rb;
  }

  public void setPatner(Node node, Edge edge){
    this.partnerMap.put(edge, node);
  }

  public Node getPartnerOfEdge(Edge edge){
    return partnerMap.get(edge);
  }

  /**
   * 
   * @param node
   * @return
   */
  public Edge getEdgeOfPartner(Node node){
    for(Map.Entry<Edge, Node> entry : partnerMap.entrySet()){
      if(entry.getValue() == node){
        return entry.getKey();
      }
    }
    return null;
  }

  /**
   *
   * @param partner
   * @return
   */
  public int getWeightEdgePartner(Node partner){

    for(Map.Entry<Edge, Node> entry : partnerMap.entrySet()){
      if(partner.equals(entry.getValue())){
        return entry.getKey().getWeight();
      }
    }
    return 0;
  }

  public void setRb(RadioButton rb) {
    this.rb = rb;
  }

  public boolean isOpen() {
    return isOpen;
  }

  public void setOpen(boolean isOpen) {
    this.isOpen = isOpen;
  }

  public void setRB(RadioButton radioButton){
    this.rb = radioButton;
  }

  public void setWeightOfPath(int weight){
    this.weightOfPath = weight;
  }

  public int getWeightOfPath(){
    return this.weightOfPath;
  }
  public RadioButton getRB(){
    return this.rb;
  }

  public void setFather(Node parentNode){
    if(parentNode == null){
      this.father = parentNode;
    }else{
      this.father = parentNode;
      this.hasFather = true;
    }
  }

  public Node getFather(){
    return this.father;
  }

  public void addEdges(Edge... edges){
    Arrays
      .stream(edges)
      .forEach(listofEdges :: add);
  }

  public void setCircle(Circle circle){
    this.circle = circle;
  }

  public Circle getCircle(){
    return this.circle;
  }

  public String getIdNode() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Edge> getListOfEdges() {
    return listofEdges;
  }

  public void setListOfEdges(List<Edge> listofEdges) {
    this.listofEdges = listofEdges;
  }

  public String toString(){
    return this.id;
  }
}
