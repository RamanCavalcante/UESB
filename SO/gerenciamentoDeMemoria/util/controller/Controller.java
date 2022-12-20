package util.controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.ListProcesses;
import types.IndexMemory;
import types.Pointer;
import util.Gui;
import util.ManagementMemory;

 
/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 09/09/2022
 * Ultima alteracao.: 17/09/2022
 * Nome: ...........: Controller
 * Funcao...........: seta variaveis da interface graficas
 * ***************************************************************/

public class Controller implements Initializable{

    @FXML
    private Button btn_create_process;
    @FXML
    private Label lbl_00;
    @FXML
    private Label lbl_01;
    @FXML
    private Label lbl_02;
    @FXML
    private Label lbl_03;
    @FXML
    private Label lbl_04;
    @FXML
    private Label lbl_05;
    @FXML
    private Label lbl_06;
    @FXML
    private Label lbl_07;
    @FXML
    private Label lbl_08;
    @FXML
    private Label lbl_09;
    @FXML
    private Label lbl_10;
    @FXML
    private Label lbl_11;
    @FXML
    private Label lbl_12;
    @FXML
    private Label lbl_13;
    @FXML
    private Label lbl_14;
    @FXML
    private Label lbl_15;
    @FXML
    private Label lbl_16;
    @FXML
    private Label lbl_17;
    @FXML
    private Label lbl_18;
    @FXML
    private Label lbl_19;
    @FXML
    private Label lbl_20;
    @FXML
    private Label lbl_21;
    @FXML
    private Label lbl_22;
    @FXML
    private Label lbl_23;
    @FXML
    private Label lbl_24;
    @FXML
    private Label lbl_25;
    @FXML
    private Label lbl_26;
    @FXML
    private Label lbl_27;
    @FXML
    private Label lbl_28;
    @FXML
    private Label lbl_29;
    @FXML
    private Label lbl_border00;
    @FXML
    private Label lbl_border01;
    @FXML
    private Label lbl_border02;
    @FXML
    private Label lbl_border03;
    @FXML
    private Label lbl_border04;
    @FXML
    private Label lbl_border05;
    @FXML
    private Label lbl_border06;
    @FXML
    private Label lbl_border07;
    @FXML
    private Label lbl_border08;
    @FXML
    private Label lbl_border09;
    @FXML
    private Label lbl_border10;
    @FXML
    private Label lbl_border11;
    @FXML
    private Label lbl_border12;
    @FXML
    private Label lbl_border13;
    @FXML
    private Label lbl_border14;
    @FXML
    private Label lbl_border15;
    @FXML
    private Label lbl_border16;
    @FXML
    private Label lbl_border17;
    @FXML
    private Label lbl_border18;
    @FXML
    private Label lbl_border19;
    @FXML
    private Label lbl_border20;
    @FXML
    private Label lbl_border21;
    @FXML
    private Label lbl_border22;
    @FXML
    private Label lbl_border23;
    @FXML
    private Label lbl_border24;
    @FXML
    private Label lbl_border25;
    @FXML
    private Label lbl_border26;
    @FXML
    private Label lbl_border27;
    @FXML
    private Label lbl_border28;
    @FXML
    private Label lbl_border29;
    @FXML
    private Spinner<Integer> spinner_process;
    @FXML
    private VBox vbox_listFinish;
    @FXML
    private HBox hbox_listMemory;
    @FXML
    private VBox vbox_listProcesses;
    @FXML
    private CheckMenuItem[] items;
    @FXML
    private CheckMenuItem menu_absolute;
    @FXML
    private CheckMenuItem menu_contigous;
    @FXML
    private CheckMenuItem menu_realocavel;
    @FXML
    private MenuButton menuTypesAllocations;
    @FXML
    private Pane PaneMemory;

    private ListProcesses list;
    ManagementMemory memory;
    public boolean start;
  
    
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    start = false;
    Label [] indexBox  = {lbl_00, lbl_01, lbl_02, lbl_03, lbl_04, lbl_05, lbl_06, lbl_07, lbl_08, lbl_09, lbl_10, lbl_11, lbl_12, lbl_13, lbl_14, lbl_15, lbl_16,
    lbl_17, lbl_18, lbl_19, lbl_20, lbl_21, lbl_22, lbl_23, lbl_24, lbl_25, lbl_26, lbl_27, lbl_28, lbl_29};
    
    Label [] borderLBL = {lbl_border00, lbl_border01, lbl_border02, lbl_border03, lbl_border04, lbl_border05, lbl_border06, lbl_border07, lbl_border08, lbl_border09,
       lbl_border10, lbl_border11, lbl_border12, lbl_border13, lbl_border14, lbl_border15, lbl_border16, lbl_border17, lbl_border18, lbl_border19, lbl_border20, 
       lbl_border21, lbl_border22, lbl_border23, lbl_border24, lbl_border25, lbl_border26, lbl_border27, lbl_border28, lbl_border29};
    
    items = new CheckMenuItem [] {menu_contigous, menu_absolute, menu_realocavel};
    
    list = new ListProcesses();
    memory = new ManagementMemory();
    memory.setAbsolutePartitions(instacePartitionsAbsolute());

    Gui gui = new Gui(indexBox, borderLBL, hbox_listMemory, vbox_listProcesses, vbox_listFinish, list);
    SpinnerValueFactory<Integer> valueProcess = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 10);
    spinner_process.setValueFactory(valueProcess);

    EventHandler<ActionEvent> eventCreateEvent = e -> {
      if(start == false){
        start = true;
        memory.setList(list);
        memory.setGui(gui);
        
        memory.setTypesAllocation(getSelectedIndex()
          .orElseGet(() -> { paint(0, gui); return 0;}));
        memory.createProcess(spinner_process.getValue());
        memory.start();
      }else{
        memory.createProcess(spinner_process.getValue());
        try {
          memory.callUpdates();
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
      }
    };

    EventHandler<ActionEvent> eventMenu = e -> {
      for(CheckMenuItem item : items){
        item.setSelected(false);
      }
      CheckMenuItem checkItem = (CheckMenuItem) e.getSource();
      checkItem.setSelected(true);
      memory.setTypesAllocation(getSelectedIndex().get());
      paint(getSelectedIndex().get(), gui);      
    };

    for(CheckMenuItem item : items){
      item.setOnAction(eventMenu);
    }
    btn_create_process.setOnAction(eventCreateEvent);
  }
  
  /**
   * 
   * @param i
   * @param gui
   */
  public void paint(int i, Gui gui){

    IndexMemory indexStart =  new IndexMemory(0, false);
    IndexMemory indexEnd =  new IndexMemory(30, false);
    
    switch(i){
      case 0:
        PaneMemory.setStyle("-fx-border-color: #e0f4f9; -fx-border-width: 5;");
        gui.paintPartitions(new Pointer(indexStart, indexEnd));
        break;
      case 1:
        PaneMemory.setStyle("-fx-border-color: #e0f4f9; -fx-border-width: 5;");
        gui.paintPartitions(
          memory.getAbsolutePartitions().get(0)
          ,memory.getAbsolutePartitions().get(1)
          ,memory.getAbsolutePartitions().get(2));
        break;
      case 2:
        gui.paintPartitions("#e0f4f9",new Pointer(indexStart, indexEnd));
        PaneMemory.setStyle("-fx-border-color: green; -fx-border-width: 5;");
        break;

      default:
        break;
    }
  }

  public Optional<Integer> getSelectedIndex(){
    Integer i = 0;
    for(CheckMenuItem item : items){
      if(item.isSelected()){
        return Optional.of(i);
      }
      i++;
    }
    return Optional.empty();
  }

    private List<Pointer> instacePartitionsAbsolute(){
    return Arrays.asList(
    new Pointer(new IndexMemory(0, false), new IndexMemory(5, false)),
    new Pointer(new IndexMemory(5, false), new IndexMemory(15, false)),
    new Pointer(new IndexMemory(15, false), new IndexMemory(30, false)));
  }
}
