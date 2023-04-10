package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import controller.Controller;
import controller.DijkstraView;

import javafx.animation.TranslateTransition;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.Edge;
import model.Node;
import view.Colors;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: DijkstraFunctions.java
 * Funcao...........: funcoes que executam o algoritmo de dDijkstra
 * ***************************************************************/

public class DijkstraFunctions {
  private Node origin;
  private Node destiny;
  private List<Node> listNodes;
  private List<List<Node>> listPaths;
  private List<Node> listTemp;
  private int infinity = Integer.MAX_VALUE;
  private Controller controller;

  public DijkstraFunctions(Node originNode, Node destinyNode, List<Node> listNodes, Controller controller) {
    this.controller = controller;
    this.origin = originNode;
    this.destiny = destinyNode;
    this.listNodes = listNodes;
    this.listPaths = new ArrayList<>();
    this.listTemp = new ArrayList<>();
    setInfinityforAllNodes();
  }

  public Node startDijkstra() {

    relaxingCurrentNode(origin);
    return choiceNodeWithWeight(origin);
  }

  public void relaxingCurrentNode(Node node) {

    final int [] animationFinished = {0};
    final int [] animationStart = {countNodeOpen(node)};
    for (int i = 0; i < node.getListOfEdges().size(); i++) {


      Node partner = node.getPartnerOfEdge(node.getListOfEdges().get(i));
      if (partner.isOpen()) {
        Map.Entry<TranslateTransition, ImageView> transition = sendPing(node, partner);
        transition.getKey().play();
        transition.getKey().setOnFinished(envet -> {
          animationFinished[0] ++;
          System.out.println(animationFinished[0]);
          if (partner.getWeightOfPath() > node.getWeightOfPath() + node.getWeightEdgePartner(partner)) {
            partner.setWeightOfPath(node.getWeightOfPath() + node.getWeightEdgePartner(partner));
            partner.setFather(node);
            if (partner.equals(destiny)) {
              showShortestPath(printPath(partner));
              finalCheck();
            }
          }
             System.out.println( animationFinished[0]+" - "+animationStart[0]);
            if (animationFinished[0] == animationStart[0]) {
            endRelaxation(node);
          }
          DijkstraView.deletObjetInView(transition.getValue());
        });
      }
    }
  }

  public int countNodeOpen(Node node){
    int count = 0;
    for (Edge edge : node.getListOfEdges()) {
      Node nodePartner  =  node.getPartnerOfEdge(edge);
      if(nodePartner.isOpen()){
        count++;
      }
    }
    return count;
  }

  public void endRelaxation(Node node) {
    node.setOpen(false);
    Node nextNode = choiceNodeWithWeight(node);
    relaxingCurrentNode(nextNode);
  }

  public void finalCheck() {
    findNodeOpen().ifPresent(node -> relaxingCurrentNode(node));
    System.out.println("nehum no aberto");
  }

  public void showShortestPath(String pathString) {

    IntStream.range(0, listPaths.size())
        .forEach(index -> {
          DijkstraView.showPath(index, listPaths.get(index), destiny, controller);
          DijkstraView.report(Color.web(Colors.colorsVet[index]), pathString, calcWeightPath(listPaths.get(index)),
              controller);
        });
  }

  public int calcWeightPath(List<Node> listOfPath) {
    Node node = listOfPath.get(0);
    int acc = 0;
    while (!node.equals(origin)) {
      acc = acc + node.getWeightEdgePartner(node.getFather());
      node = node.getFather();
    }
    return acc;
  }

  public String printPath(Node node) {
    List<Node> path = new ArrayList<>();
    String pathString = "";
    Node current = node;
    path.add(current);
    while (!current.equals(origin)) {
      path.add(current.getFather());
      System.out.print(" - " + current.getFather().getIdNode());
      pathString = pathString + " - " + current.getFather().getIdNode();
      current = current.getFather();
    }
    listPaths.addAll(Arrays.asList(path));
    return pathString;
  }

  private Map.Entry<TranslateTransition, ImageView> sendPing(Node currentNode, Node nextNode) {
    Map<TranslateTransition, ImageView> move = DijkstraView.sendPing(currentNode, nextNode);
    return startPing(move);
  }

  private Map.Entry<TranslateTransition, ImageView> startPing(Map<TranslateTransition, ImageView> move) {
    Iterator<Map.Entry<TranslateTransition, ImageView>> iterator = move.entrySet().iterator();
    Map.Entry<TranslateTransition, ImageView> entry = null;
    if (iterator.hasNext()) {
      entry = iterator.next();
    }
    return entry;
  }

  public Node choiceNodeWithWeight(Node currentNode) {
    return currentNode.getListOfEdges()
        .stream()
        .map(currentNode::getPartnerOfEdge)
        .filter(node -> node.isOpen())
        .filter(node -> node.getWeightOfPath() < infinity)
        .min(Comparator.comparing(Node::getWeightOfPath))
        .orElseGet(() -> currentNode.getPartnerOfEdge(currentNode.getListOfEdges().get(0)));
  }

  public void setInfinityforAllNodes() {
    listNodes
        .stream()
        .filter(node -> !node.equals(origin))
        .forEach(node -> node.setWeightOfPath(infinity));
    origin.setWeightOfPath(0);
  }

  public Optional<Node> findNodeOpen() {
    return listNodes
        .stream()
        .filter(node -> node.isOpen() == false)
        .map(Optional::of)
        .findFirst()
        .orElse(Optional.empty());
  }

  public Optional<Node> findNodeOpen(Node currentNode) {
    return currentNode.getListOfEdges()
        .stream()
        .map(currentNode::getPartnerOfEdge)
        .filter(Node::isOpen)
        .map(Optional::of)
        .findFirst()
        .orElse(Optional.empty());
  }

}
