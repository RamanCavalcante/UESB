package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Graph;
import model.Node;
import util.DijkstraFunctions;
import util.GeneratingGraph;
import util.SweepingGraph;
import view.Colors;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: Controller.java
 * Funcao...........: gerencia botoes da inteface e relatorios
 * ***************************************************************/

public class Controller implements Initializable {

  private Graph graph;
  private GeneratingGraph newGraph;
  private GraphView graphView;
  private DijkstraFunctions dk;

  @FXML
  private Button btn_network, btn_reset;

  @FXML
  private ImageView img_touch_drag;

  @FXML
  private ImageView img_touch_start;

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private VBox listPaths_Vbox;

  public static ImageView img_start, img_drag;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    img_drag = img_touch_drag;
    img_start = img_touch_start;
    img_drag.setVisible(false);
    btn_reset.setVisible(false);
    btn_reset.setCursor(Cursor.HAND);
    img_start.setDisable(true);
    // EVENTS
    EventHandler<ActionEvent> setupGraph = e -> {
      btn_reset.setVisible(true);
      btn_network.setVisible(false);
      img_start.setVisible(false);
      img_drag.setVisible(true);

      graphView = new GraphView(this);
      graph = makeGraph();
      graphView.drawNodes(graph);
      noticeTutor();

    };

    EventHandler<ActionEvent> resetGraph = e -> {
      resetNodes();
      resetLines();
    };

    // ACTIONS
    btn_network.setOnAction(setupGraph);
    btn_reset.setOnAction(resetGraph);
  }

  public void resetNodes() {
    graph.getListOfNodes()
        .stream()
        .forEach(node -> {
          node.setWeightOfPath(0);
          node.setOpen(true);
          node.setFather(null);
          node.getCircle().setFill(Color.web(Colors.WHITE));

        });
  }

  public void resetLines() {
    graph.getListEdges()
        .stream()
        .forEach(edge -> {
          Line line = (Line) edge.getGroupEdges().getChildren().get(0);
          line.setStroke(Color.web(Colors.DEFAULT));
        });
  }

  /**
   *
   * @param origin
   * @param destiny
   */
  public void startDijkstra(Node origin, Node destiny) {
    dk = new DijkstraFunctions(origin, destiny, graph.getListOfNodes(), this);
    SweepingGraph sweep = new SweepingGraph(dk);
    sweep.run();
  }

  public Graph makeGraph() {
    Graph graph = new Graph();
    newGraph = new GeneratingGraph(graph);
    Optional<List<Node>> listNodes = Optional.empty();
    try {
      listNodes = newGraph.loadBackBone();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return graph;
  }

  /**
   *
   * @param stage
   */
  public void setStage(Stage stage) {
    GraphView.stage = stage;
    DijkstraView.stage = stage;
  }

  /**
   *
   * @param color
   * @param path
   * @param sizePath
   */
  public void addReportInVbox(Color color, String path, int sizePath) {
    HBox hbox = new HBox();
    Line line = new Line(0, 0, 10, 0);
    line.setStrokeWidth(7);
    line.setStroke(color);
    Text pathText = new Text(path+"="+sizePath);
    hbox.getChildren().addAll(line, pathText);
    listPaths_Vbox.getChildren().add(hbox);
  }

  public void noticeTutor() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Titulo da caixa de aviso");
    alert.setHeaderText(null);
    alert.setContentText("De dois clicks no ponto para selecionar a origem e destino, e pode arrastar tambem se quiser");

    alert.showAndWait();
  }
}
