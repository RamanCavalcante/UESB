package util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Job;
import model.ListProcesses;
import types.Pointer;


/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 09/09/2022
 * Ultima alteracao.: 17/09/2022
 * Nome: ...........: Gui
 * Funcao...........: funcoes da interface 
 * ***************************************************************/
public class Gui {

  Label [] indexBox;
  Label [] bordersLBL;
  VBox listProcessesBOX;
  VBox listFinishBOX;
  HBox listMemoryBOX;
  String colorPattern = "#d8f8e1";
  Map<VBox, List<Job>> map;
  ListProcesses list;
  String [] colorsBordersPartition = { "#e7c500", "#0022e7", "#00e751","#e70095","#00e7c5"};
  
  /**
   * @param indexBox
   * @param listMemory
   * @param listProcesses
   * @param listFinish
   */
  public Gui(Label [] indexBox,Label [] bordersLBL, HBox listMemoryBOX, VBox listProcessesBOX, VBox listFinishBOX, ListProcesses list){
    this.bordersLBL = bordersLBL;
    this.indexBox = indexBox;
    this.listProcessesBOX = listProcessesBOX;
    this.listFinishBOX = listFinishBOX;
    this.listMemoryBOX = listMemoryBOX;
    this.list = list;
    
    hashList();
  }

  public void hashList(){
    map = new HashMap<>();
    map.put(this.listProcessesBOX, list.getListProcess());
    map.put(this.listFinishBOX, list.getListProcessesFinish());
  }

  public void updateLists(){
    // lock.lock();
    resetBox(listProcessesBOX);
    resetBox(listFinishBOX);
    resetBox(listMemoryBOX);
    
    updateListMemory();
    for(Map.Entry<VBox, List<Job>> entry : map.entrySet()){
      
      for (Job processV : entry.getValue()) {
        VBox vBoxProcess = new VBox();
        ArrayList<Node> nodes = new ArrayList<>();
        HBox boxTitle = new HBox();
        
        Label label = new Label(String.format("%s %d", "Id: ", processV.getId()));
        
        nodes.add(label);
        nodes.add(new Separator(Orientation.VERTICAL));
        Label labelRunTime = new Label(String.format("%s %d ","runTime: ",processV.getRuntime()));
        labelRunTime.setAlignment(Pos.CENTER_RIGHT);
        nodes.add(labelRunTime);
        
        boxTitle.getChildren().addAll(nodes);
        vBoxProcess.setStyle("-fx-border-color: #9f9f9f");
        vBoxProcess.setStyle(String.format("-fx-background-color: %s", processV.getColor()));
        nodes.clear();
        nodes.add(boxTitle);
        nodes.add(new Separator());
        nodes.add(new Label(String.format("%s %d", "tamanho: ", processV.getSizeProcess())));
        
        vBoxProcess.getChildren().addAll(nodes);
        Platform.runLater(() ->  entry.getKey().getChildren().add(0, vBoxProcess));
      }}
    }


    public void updateListMemory(){
      
      for(Job processH : list.getListMemory()){

        VBox vBoxProcess = new VBox();
        ArrayList<Node> nodes = new ArrayList<>();
        HBox boxTitle = new HBox();
        
        Label label = new Label(String.format("%s %d", "Id: ", processH.getId()));
        
        nodes.add(label);
        nodes.add(new Separator(Orientation.VERTICAL));
        Label labelRunTime = new Label(String.format("%s %d ","runTime: ",processH.getRuntime()));
        labelRunTime.setAlignment(Pos.CENTER_RIGHT);
        nodes.add(labelRunTime);
        
        boxTitle.getChildren().addAll(nodes);
        vBoxProcess.setStyle("-fx-border-color: #9f9f9f");
        vBoxProcess.setStyle(String.format("-fx-background-color: %s", processH.getColor()));
        nodes.clear();
        nodes.add(boxTitle);
        nodes.add(new Separator());
        nodes.add(new Label(String.format("%s %d", "tamanho: ", processH.getSizeProcess())));
        
        vBoxProcess.getChildren().addAll(nodes);
        Platform.runLater(() ->  listMemoryBOX.getChildren().add(0, vBoxProcess));
      }    // lock.unlock();
    }

  /**
   * 
   * @param pointsPartition
   */
  public void paintPartitions(Pointer... pointsPartition){
    for(int i = 0; i < pointsPartition.length; i++){
      int indexStart = pointsPartition[i].getPointerStart().getIndex();
      int indexEnd = pointsPartition[i].getPointerEnd().getIndex();
      String css = "-fx-border-radius: 5; -fx-border-width: 5;";
      for(int j = indexStart; j < indexEnd; j++){
        bordersLBL[j].setStyle(String.format("-fx-border-color: %s;"+css, colorsBordersPartition[i]));
      }
    }
  }

  /**
   * 
   * @param color
   * @param pointsPartition
   */

  public void paintPartitions(String color, Pointer... pointsPartition){
        
    for(int i = 0; i < pointsPartition.length; i++){
      int indexStart = pointsPartition[i].getPointerStart().getIndex();
      int indexEnd = pointsPartition[i].getPointerEnd().getIndex();
      String css = "-fx-border-radius: 5;";
      for(int j = indexStart; j < indexEnd; j++){
        bordersLBL[j].setStyle(String.format("-fx-border-color: %s;"+css, color));
      }
    }  
  }

  public void resetBox(VBox vBox) {
    Platform.runLater(() -> vBox.getChildren().clear());
  }
  public void resetBox(HBox hBox){
    Platform.runLater(() -> hBox.getChildren().clear());
  }


  public void addProcess(Job process){
    int indexStart = process.getPointers().getPointerStart().getIndex();
    int indexEnd = process.getPointers().getPointerEnd().getIndex();
    
    for(int i = indexStart; i <= indexEnd; i++){
      indexBox[i].setPadding(new Insets(5));
      Label  lb = indexBox[i];
      Platform.runLater(() ->
        lb.setStyle(String.format("-fx-background-color: %s", process.getColor())));
    }   
}
  public void clearMemory(Job process){
    int indexStart = process.getPointers().getPointerStart().getIndex();
    int indexEnd = process.getPointers().getPointerEnd().getIndex();

    for(int i = indexStart; i <= indexEnd; i ++){
      indexBox[i].setPadding(new Insets(5));
      Label  lb = indexBox[i];
      Platform.runLater(() ->
        lb.setStyle(String.format("-fx-background-color: %s", colorPattern)));
    }
  }

}
