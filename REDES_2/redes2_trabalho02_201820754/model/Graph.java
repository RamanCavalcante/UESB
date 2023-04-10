package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: Graph.java
 * Funcao...........: classe objeto do grafo com lista de nos e arestas
 * ***************************************************************/
public class Graph {

  private List<Node> listOfNodes;
  private List<Edge> listOfEdges;

  public Graph(){
    this.listOfNodes = new ArrayList<>();
    this.listOfEdges = new ArrayList<>();
  }
  public void addNode(Node... nodes){
    Arrays
      .stream(nodes)
      .forEach(n -> this.listOfNodes.add(n));
  }

  public List<Node> getListOfNodes(){
    return this.listOfNodes;
  }

  /**
   *
   * @param currentNode
   * @return
   */
  public Node buildNode(Node currentNode){
    return listOfNodes
      .stream()
      .filter(node -> node.getIdNode().equals(currentNode.getIdNode()))
      .findFirst()
      .orElseGet(() ->{
        listOfNodes.add(currentNode);
        return currentNode;});
  }

  /**
   * 
   * @param edges
   * @return
   */
  public Edge setEdgesAndGet(Edge edges){
    this.listOfEdges.add(edges);
    return edges;
  }

  public List<Edge> getListEdges(){
    return this.listOfEdges;
  }
  public void setListOfNodes(List<Node> list){
    this.listOfNodes = list;
  }
}
