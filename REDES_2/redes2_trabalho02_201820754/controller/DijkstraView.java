package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Edge;
import model.Node;
import view.Colors;
import view.Gallery;


/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: DijkstraView.java
 * Funcao...........: realiza alterações no grafico em ralacao com o algoritmo
 * ***************************************************************/

public class DijkstraView {

  public static Stage stage;
  private static int gambX = 7;
  private static int gambY = 16;

  /**
   *
   * @param currentNode
   * @param nextNode
   * @return
   */
  public static Map<TranslateTransition, ImageView> sendPing(Node currentNode, Node nextNode){
    Map<TranslateTransition, ImageView> mapMove = new HashMap<>();
    ImageView image = setImage(currentNode, Gallery.GIF_PING, 25);
    TranslateTransition move = new TranslateTransition(Duration.millis(6000), image);
    move.setFromX(currentNode.getCircle().getCenterX());
    move.setFromY(currentNode.getCircle().getCenterY());
    move.setByX((nextNode.getCircle().getCenterX() - currentNode.getCircle().getCenterX()) - gambX);
    move.setByY(nextNode.getCircle().getCenterY() - currentNode.getCircle().getCenterY() - gambY);
    mapMove.put(move, image);
    return mapMove;
  }

  /**
   *
   * @param indexInPath
   * @param path
   * @param origin
   * @param controller
   */
  public static void showPath(int indexInPath, List<Node> path, Node origin, Controller controller){
    path
    .stream()
    .filter(node -> true == node.getHasFather())
    .forEach(node ->{
      Edge edge = node.getEdgeOfPartner(node.getFather());
      paintLineEdge(edge, indexInPath, controller);
    });
  }

  /**
   *
   * @param edge
   * @param id
   * @param controller
   */
  private static void paintLineEdge(Edge edge, int id, Controller controller){
    Line line = (Line) edge.getGroupEdges().getChildren().get(0);
    line.setStroke(Color.web(Colors.colorsVet[id]));
  }

/**
 *
 * @param color
 * @param path
 * @param sizePath
 * @param controller
 */
  public static void report(Color color, String path, int sizePath, Controller controller){
    controller.addReportInVbox(color, path, sizePath);
  }

  /**
   *
   * @param current
   * @param path
   * @param size
   * @return
   */
  private static ImageView setImage(Node current, Image path, int size){
    ImageView image = new ImageView(path);
    image.setFitHeight(size);
    image.setFitWidth(size);
    image.setTranslateX(current.getCircle().getCenterX());
    image.setTranslateY(current.getCircle().getCenterY());

    if(size <= 25){
      Platform.runLater(()->{
        Scene scene = stage.getScene();
        AnchorPane root = (AnchorPane) scene.getRoot();
        root.getChildren().add(image);});
    }else{
      // setImageFinaly(image);
    }
    return image;
  }

/**
 * 
 * @param object
 */
  public static void deletObjetInView(Object object){
    Platform.runLater(() ->{
      Scene scene = stage.getScene();
      AnchorPane root = (AnchorPane) scene.getRoot();
      root.getChildren().remove(object);
    });
  }
}
