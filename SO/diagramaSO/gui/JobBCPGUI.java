package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.JobBCP;
import model.ProcessStates;

public class JobBCPGUI {

  public VBox readyVBox;
  public VBox blockedVBox;
  public VBox finishVBox;
  public HBox hbox_process;
  public VBox vBox_runing;

  public JobBCPGUI(VBox readyVBox, VBox blockedVBox, VBox finishVBox, HBox hbox_process, VBox vBox_runing) {
    this.readyVBox = readyVBox;
    this.blockedVBox = blockedVBox;
    this.finishVBox = finishVBox;
    this.hbox_process = hbox_process;
    this.vBox_runing = vBox_runing;

  }

  public static void printProcessRuning(VBox vBox, JobBCP process) {
    VBox vBoxprocess = new VBox();
    vBox.setPadding(new Insets(5));
    ArrayList<Node> nodes = new ArrayList<>();

    HBox nameAndProgress = new HBox();
    nameAndProgress.setPadding(new Insets(10));

    ProgressIndicator progress = new ProgressIndicator();
    progress.setPrefHeight(40);
    progress.setProgress(0);
    progress.accessibleHelpProperty();
    progress.setMaxSize(150, 150);
    progress.setStyle("-fx-progress-color: green");

    Label label = new Label(process.getNameProcess());
    label.setPrefWidth(130);
    nodes.add(label);
    nodes.add(progress);
    nameAndProgress.getChildren().addAll(nodes);

    nodes.clear();

    vBoxprocess.setStyle("-fx-border-color: #9f9f9f");
    nodes.add(nameAndProgress);
    nodes.add(new Separator());
    nodes.add(new Label(String.format("%s %d", "Id:", process.getListOrder())));
    nodes.add(new Label(String.format("%s %d", "tempo de processo:", process.getRuntime())));
    nodes.add(new Label(String.format("%s %d", "blocked:", process.getBlockedProcess())));

    vBoxprocess.getChildren().addAll(nodes);
    Platform.runLater(() -> vBox.getChildren().add(0, vBoxprocess));

    new Thread(() -> {
      while (progress.getProgress() < 1) {
        Platform.runLater(() -> progress.setProgress(progress.getProgress() + 0.01));
        try {
          Thread.sleep((long) (0.01 * (ProcessStates.timeSlicing * 1000L)));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();

  }

  public static void printProcess(VBox vBox, JobBCP process) {
    VBox vBoxprocess = new VBox();
    vBox.setPadding(new Insets(5));
    ArrayList<Node> nodes = new ArrayList<>();
    String css = "-fx-border-color: #9f9f9f";

    vBoxprocess.setStyle(css);
    nodes.add(new Label(process.getNameProcess()));
    nodes.add(new Separator());
    nodes.add(new Label(String.format("%s %d", "Id :", process.getListOrder())));
    nodes.add(new Label(String.format("%s %d", "tempo de processo:", process.getRuntime())));
    nodes.add(new Label(String.format("%s %d", "Blocked:", process.getBlockedProcess())));

    vBoxprocess.getChildren().addAll(nodes);
    Platform.runLater(() -> vBox.getChildren().add(0, vBoxprocess));

  }

  public static void printListProcesses(VBox vBox, List<JobBCP> processes) {

    for (JobBCP process : processes) {

      VBox vBoxprocess = new VBox();
      vBox.setPadding(new Insets(5));
      ArrayList<Node> nodes = new ArrayList<>();
      String css = "-fx-border-color: #9f9f9f";

      vBoxprocess.setStyle(css);
      nodes.add(new Label(process.getNameProcess()));
      nodes.add(new Separator());
      nodes.add(new Label(String.format("%s %d", "Id:", process.getListOrder())));
      nodes.add(new Label(String.format("%s %d", "tempo de processo:", process.getRuntime())));
      nodes.add(new Label(String.format("%s %d", "blocked:", process.getBlockedProcess())));

      vBoxprocess.getChildren().addAll(nodes);
      Platform.runLater(() -> vBox.getChildren().add(0, vBoxprocess));

    }
  }

  /**
   * @param vBox
   * @param processes
   */
  public static void printListBlockedProcesses(VBox vBox, List<JobBCP> processes) {

    for (JobBCP process : processes) {

      VBox vBoxprocess = new VBox();
      vBox.setPadding(new Insets(5));
      ArrayList<Node> nodes = new ArrayList<>();

      HBox nameAndProgress = new HBox();
      nameAndProgress.setPadding(new Insets(10));

      ProgressIndicator progress = new ProgressIndicator();
      progress.setPrefHeight(40);
      progress.setProgress(0);
      progress.accessibleHelpProperty();
      progress.setMaxSize(150, 150);
      progress.setStyle("-fx-progress-color: red");

      Label label = new Label(process.getNameProcess());
      label.setPrefWidth(130);
      nodes.add(label);
      nodes.add(progress);
      nameAndProgress.getChildren().addAll(nodes);

      nodes.clear();

      vBoxprocess.setStyle("-fx-border-color: #9f9f9f");
      nodes.add(nameAndProgress);
      nodes.add(new Separator());
      nodes.add(new Label(String.format("%s %d", "Id:", process.getListOrder())));
      nodes.add(new Label(String.format("%s %d", "tempo de processo:", process.getRuntime())));
      nodes.add(new Label(String.format("%s %d", "blocked:", process.getBlockedProcess())));

      vBoxprocess.getChildren().addAll(nodes);
      Platform.runLater(() -> vBox.getChildren().add(0, vBoxprocess));

      new Thread(() -> {
        while (progress.getProgress() < 1) {
          Platform.runLater(() -> progress.setProgress(progress.getProgress() + 0.01));
          try {
            Thread.sleep((long) (0.01 * (process.getBlockedProcess() * 1000L)));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }).start();

    }
  }

  public static void resetBox(VBox vBox) {
    Platform.runLater(() -> vBox.getChildren().clear());

  }

  public HBox getHbox_process() {
    return hbox_process;
  }

  public void setHbox_process(HBox hbox_process) {
    this.hbox_process = hbox_process;
  }

  public VBox getvBox_runing() {
    return vBox_runing;
  }

  public void setvBox_runing(VBox vBox_runing) {
    this.vBox_runing = vBox_runing;
  }

  public VBox getReadyVBox() {
    return readyVBox;
  }

  public void setReadyVBox(VBox readyVBox) {
    this.readyVBox = readyVBox;
  }

  public VBox getBlockedVBox() {
    return blockedVBox;
  }

  public void setBlockedVBox(VBox blockedVBox) {
    this.blockedVBox = blockedVBox;
  }

  public VBox getFinishVBox() {
    return finishVBox;
  }

  public void setFinishVBox(VBox finishVBox) {
    this.finishVBox = finishVBox;
  }
}
