package gui;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Network;
import model.Packet;
import model.Router;
import view.Gallery;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: GuiPackets
 * Funcao...........: funcoes de animacao
 * ***************************************************************/

public class GuiPacket {
  private List<RadioButton>radioButton;
  private Stage primaryStage;
  private Label label;
  private TextArea textReport;
  public GuiPacket(List<RadioButton> radioButtons, Label label, TextArea textReport){
    this.radioButton = radioButtons;
    this.label = label;
    this.textReport = textReport;
  }
  /**
   *
   * @param current
   * @param next
   * @return
   */
  public HashMap<TranslateTransition, ImageView> movePacket(Router current, Router next){
    ImageView imageView = setImage(current, Gallery.ICON_MESSAGE,25);
    TranslateTransition move = new TranslateTransition(Duration.millis(3000), imageView);

    move.setFromX(current.getLayoutX());
    move.setFromY(current.getLayoutY());

    move.setByX(next.getLayoutX() - current.getLayoutX());
    move.setByY(next.getLayoutY() - current.getLayoutY());

    HashMap<TranslateTransition, ImageView> hashImage = new HashMap<TranslateTransition, ImageView>();

    move.play();
    hashImage.put(move, imageView);
    return hashImage;
  }

  /**
   *
   * @param current
   * @param path
   * @param size
   * @return
   */
  public ImageView setImage(Router current, Image path, int size){
    ImageView image = new ImageView(path);
    image.setFitHeight(size);
    image.setFitWidth(size);
    image.setTranslateX(current.getLayoutX());
    image.setTranslateY(current.getLayoutY());

    if(size <= 25){
      Platform.runLater(()->{
        Scene scene = primaryStage.getScene();
        AnchorPane root = (AnchorPane) scene.getRoot();
        root.getChildren().add(image);});
    }else{
      setImageFinaly(image);
    }

    return image;
  }
/**
 *
 * @param image
 */
  public void setImageFinaly(ImageView image){
      Timeline timeline = new Timeline(
          new KeyFrame(Duration.seconds(0), event -> {
              Scene scene = primaryStage.getScene();
              AnchorPane root = (AnchorPane) scene.getRoot();
              root.getChildren().add(image);
          }),
          new KeyFrame(Duration.seconds(3), event -> {
              Scene scene = primaryStage.getScene();
              AnchorPane root = (AnchorPane) scene.getRoot();
              root.getChildren().remove(image);
          })
      );
      timeline.play();
  }

  /***
   *
   * @param current
   * @param nextRoute
   * @return
   */
  public double calcDuration(Router current, Router nextRoute){
    double x = nextRoute.getLayoutX() - current.getLayoutX();
    double y = nextRoute.getLayoutY() - current.getLayoutY();
    return Math.abs((x+y)/2*50);
  }

  public void setStageInGui(Stage primaryStage){
    this.primaryStage = primaryStage;
  }

  /**
  *
  * @param image
  */
  public void deletPacket(ImageView image){
    Platform.runLater(() ->{
      Scene scene = primaryStage.getScene();
      AnchorPane root = (AnchorPane) scene.getRoot();
      root.getChildren().remove(image);
    });
  }

  /**
   *
   * @param packet
   * @param numberOfPacket
   */
  public void report(Packet packet, AtomicInteger numberOfPacket){
    String currentString = textReport.getText();
    String destiny = packet.getPoints().getDestiny().toString();
    String origin  = packet.getPoints().getCurrentRoute().toString();
    String protocol = Network.flooding.toString();
    String split = "\n-------------------------------\n";
    int packets = numberOfPacket.get();

    String reportString = String.format("%s\n %s %s - %d.00",
    protocol, origin, destiny, packets);
    reportString = split + reportString;
    reportString = currentString + reportString;
    textReport.setText(reportString);
  }

  public void showDialogOverHead(){

  }

  /**
   *
   * @param route
   */
  public void messageArrivied(Router route){
    setImage(route, Gallery.GIF_ARRIVIED,100);
  }

  /**
   * 
   * @param num
   */
  public void setTextLabel(Integer num){
    this.label.setText(Integer.toString(num));
  }
}
