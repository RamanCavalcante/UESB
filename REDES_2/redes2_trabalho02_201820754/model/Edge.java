package model;

import javafx.scene.Group;


/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: Edge.java
 * Funcao...........: classe objeto da aretas de cada no do grafo
 * ***************************************************************/
public class Edge {

  private int weight;
  private Group group;
  private Node startNode;
  private Node endNode;

  /**
   * 
   * @param startNode
   * @param endNode
   * @param weight
   */
  public Edge(Node startNode, Node endNode, int weight){
    this.startNode = startNode;
    this.endNode = endNode;
    this.weight = weight;
    this.startNode.setPatner(endNode, this);
    this.endNode.setPatner(startNode, this);
  }

  public int getWeight() {
    return weight;
  }

  public Node getStartNode() {
    return this.startNode;
  }

  public Node getEndNode(){
    return this.endNode;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public void setGrouOfEges(Group group){
    this.group = group;
  }

  public Group getGroupEdges(){
    return this.group;
  }

}
