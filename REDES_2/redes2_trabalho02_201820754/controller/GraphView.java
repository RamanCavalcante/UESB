package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Edge;
import model.Graph;
import model.Node;
import view.Colors;


/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: GraphView.java
 * Funcao...........: realiza alterações no grafico em relacao com grafo
 * ***************************************************************/

public class GraphView {

  private double mousePosX;
  private double mousePosY;
  private double circleCenterX;
  private double circleCenterY;
  private List<Group>listEgesEvent;
  private Map<Group, Edge> mapEgesAndComponents;
  public static Stage stage;
  private Scene scene;
  private AnchorPane root;
  private List<Group> listGroupCircles;
  private Graph graph;
  private List<Node> listSelectedNode = new ArrayList<>();
  private Long lastClickTime = 0L;
  private Long minClickDuration = 300L;
  private Controller controller;

  public GraphView(Controller controller) {
    this.controller = controller;
    this.scene = this.stage.getScene();
    this.root = (AnchorPane) this.scene.getRoot();
    mapEgesAndComponents = new HashMap<>();

  }

  /**
   *
   * @param graph
   */
  public void drawNodes(Graph graph) {

    this.graph = graph;
    int numNomes = 8;
    double centerX = 400;
    double centerY = 250;
    double radius = 200;
    double angle = 2 * Math.PI / numNomes;

    List<Node> listOfNodes = graph.getListOfNodes();

    listGroupCircles = IntStream
        .range(0, listOfNodes.size())
        .mapToObj(i -> {
          double x = centerX + radius * Math.cos(i * angle);
          double y = centerY + radius * Math.sin(i * angle);
          Circle circle = new Circle(x, y, 13);
          circle.setCursor(Cursor.MOVE);
          circle.setFill(Color.web(Colors.WHITE));
          Text text = setupTex(listOfNodes.get(i).getIdNode(), circle);
          Group group = new Group(circle, text);
          root.getChildren().add(group);
          listOfNodes.get(i).setCircle(circle);
          return group;
        })
        .collect(Collectors.toList());
    ActionsCircles();
    drawLines();
  }


  public void drawLines() {
    List<Edge> edges = graph.getListEdges();
    List<Node> nodes = graph.getListOfNodes();
    for (Edge edge : edges) {
      String fromIndex = edge.getStartNode().getIdNode();
      String toIndex = edge.getEndNode().getIdNode();
      Circle fromCircle = findGroupNode(fromIndex, nodes);
      Circle toCircle = findGroupNode(toIndex, nodes);
      Line line = new Line(fromCircle.getCenterX(), fromCircle.getCenterY(), toCircle.getCenterX(),toCircle.getCenterY());

      line.setStrokeWidth(3);
      line.setStroke(Color.web(Colors.DEFAULT));
      Text text = setupTex(edge.getWeight(), fromCircle, toCircle, edge);
      Group group = new Group(line, text);
      root.getChildren().add(group);
      group.toBack();
      edge.setGrouOfEges(group);
      mapEgesAndComponents.put(group, edge);
    }
  }

  /**
   *
   * @param info
   * @param fromCircle
   * @param toCircle
   * @param edge
   * @return
   */
  public Text setupTex(int info, Circle fromCircle, Circle toCircle, Edge edge){
    Text text = new Text((fromCircle.getCenterX() + toCircle.getCenterX()) / 2, (fromCircle.getCenterY() + toCircle.getCenterY()) / 2, Integer.toString(info));
    text.setDisable(true);
    text.setFill(Color.BLACK);
    Font font = Font.font("Arial", 14);
    text.setFont(font);
    return text;
  }

  /**
   *
   * @param info
   * @param circle
   * @return
   */
  public Text setupTex(String info, Circle circle){
    Text text = new Text(circle.getCenterX(), circle.getCenterY(), info);
    text.setDisable(true);
    text.setFill(Color.BLACK);
    Font font = Font.font("Arial",10);
    text.setFont(font);
    return text;
  }


  /**
   *
   * @param id
   * @param nodes
   * @return
   */
  public Circle findGroupNode(String id, List<Node> nodes) {
    for (Node node : nodes) {
      if (node.getIdNode().equals(id)) {
        Circle circle = node.getCircle();
        return circle;
      }
    }
    return null;
  }

  public void ActionsCircles() {

    EventHandler<MouseEvent> eventPressed = (event) -> {
      listEgesEvent = setActionInLines((Circle)event.getSource());
      mousePosX = event.getSceneX();
      mousePosY = event.getSceneY();
      circleCenterX = ((Circle) event.getSource()).getCenterX();
      circleCenterY = ((Circle) event.getSource()).getCenterY();
    };

    EventHandler<MouseEvent> eventDoubleClick = (event) ->{
      if(event.getClickCount() == 2){
        if(System.currentTimeMillis() - lastClickTime > minClickDuration){
          System.out.println("CLICK");
          Circle circle = (Circle) event.getSource();
          Optional<Node> node = findNodeSelected(circle);
          node.ifPresent(n ->{
            if(listSelectedNode.size() == 1 && !listSelectedNode.contains(node.get())){
              circle.setFill(Color.web(Colors.SEA));
              listSelectedNode.add(n);
              controller.startDijkstra(listSelectedNode.get(0), listSelectedNode.get(1));
              listSelectedNode.clear();
            }else if(listSelectedNode.contains(node.get())){
                listSelectedNode.get(0).getCircle().setFill(Color.web(Colors.WHITE));
                listSelectedNode.clear();
            }else{
              circle.setFill(Color.web(Colors.LIME_GREEN));
              listSelectedNode.add(n);
            }
          });
        }
        lastClickTime = System.currentTimeMillis();
      }
    };

    EventHandler<MouseEvent> eventDragged = (event) -> {
      Controller.img_drag.setVisible(false);
      double deltaX = event.getSceneX() - mousePosX;
      double deltaY = event.getSceneY() - mousePosY;

      ((Circle) event.getSource()).setCenterX(circleCenterX + deltaX);
      ((Circle) event.getSource()).setCenterY(circleCenterY + deltaY);

      Circle circle =(Circle) event.getSource();
      changePosition(findText(circle), circle);
      for (Group group : listEgesEvent) {
        Edge edge = mapEgesAndComponents.get(group);
        Line line = (Line)group.getChildren().get(0);
        Text text = (Text)group.getChildren().get(1);
        changePosition(text, line);
        Circle startNode = edge.getStartNode().getCircle();
        Circle endNode = edge.getEndNode().getCircle();

        if(startNode.equals(event.getSource())){
          line.setStartX(circleCenterX + deltaX);
          line.setStartY(circleCenterY + deltaY);
        }

        if(endNode.equals(event.getSource())){
          line.setEndX(circleCenterX + deltaX);
          line.setEndY(circleCenterY + deltaY);
        }
        group.requestLayout();
      }
    };

    EventHandler<MouseEvent> eventReleased = (event) -> {
      for (Group group : listEgesEvent) {
          group.requestLayout();
      }
  };

    for (Group group : listGroupCircles) {
      Circle circle = (Circle) group.getChildren().get(0);
      circle.setOnMousePressed(eventPressed);
      circle.setOnMouseDragged(eventDragged);
      circle.setOnMouseReleased(eventReleased);
      circle.setOnMouseClicked(eventDoubleClick);
    }
  }

  public void changePosition(Text text, Line line){
    double endPointX = line.getEndX();
    double endPointY = line.getEndY();
    double startPointX = line.getStartX();
    double startPointY = line.getStartY();

    text.setX((startPointX + endPointX) / 2);
    text.setY((startPointY + endPointY) / 2);
  }

  public Text findText(Circle circle){
    for (Group group : listGroupCircles) {
      if(group.getChildren().get(0).equals(circle)){
        return (Text)group.getChildren().get(1);
      }
    }
    return null;
  }
  public void changePosition(Text text, Circle circle){
    double pointX = circle.getCenterX();
    double pointY = circle.getCenterY();

    text.setX(pointX);
    text.setY(pointY);
  }

  /**
   * 
   * @param circle
   * @return
   */
  public List<Group> setActionInLines(Circle circle){
    List<Group> listReturn = new ArrayList<>();
    List<Node> listofNodes = graph.getListOfNodes();
    for (Node node : listofNodes) {
      if(node.getCircle().equals(circle)){
        for (Edge listEdges : node.getListOfEdges()) {
          listReturn.add(listEdges.getGroupEdges());
        }
      }
    }
    return listReturn;
  }

  public void desableCircles(){

  }

  /**
   *
   * @param circle
   * @return
   */
  public Optional<Node> findNodeSelected(Circle circle){
    return graph.getListOfNodes()
      .stream()
      .filter(node -> node.getCircle().equals(circle))
      .findFirst();
    }
}
