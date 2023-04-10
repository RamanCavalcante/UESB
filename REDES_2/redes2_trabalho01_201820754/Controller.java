import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import gui.GuiPacket;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.Network;
import model.Router;
import util.FlooRoutingExceptPreviousPing;
import util.Flood;
import util.FloodRouting;
import util.FloodRoutingExceptPrevious;
import util.FloodRoutingExceptPreviousTTL;
/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: Contoller
 * Funcao...........: classe da interface grafica
 * ***************************************************************/
public class Controller implements Initializable {

	@FXML
	private RadioButton routerA,routerB,routerC,routerD,routerE,routerF,
											routerG,routerH,routerI,routerJ;

	@FXML
	private Button reset_network;

	@FXML
	private TextArea textReport;

	@FXML
	private Label labelQuantityPackets;

	@FXML
	private ChoiceBox<Flood> menu_options;
	private Stage primaryStage;
	private List<Router> listNodes;
	private List<RadioButton>listRadioButtons;
	private List<RadioButton> listIsSelect;
	private GuiPacket gui;

	private Flood [] listMenu = {
		new FloodRouting(),
		new FloodRoutingExceptPrevious(),
		new FloodRoutingExceptPreviousTTL(),
		new FlooRoutingExceptPreviousPing()
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		listNodes = new ArrayList<>();
		listIsSelect = new ArrayList<>();
		listRadioButtons = Arrays.asList(routerA,routerB,routerC,
																		routerD,routerE,routerF,routerG,routerH,routerI,routerJ);
		gui = new GuiPacket(listRadioButtons, labelQuantityPackets, textReport);

		setMenuChoiceBox();
		setLabelQuantity();

		//EVENTOS
		EventHandler<ActionEvent> actionReset = (e) -> {
			resetNetWork();
		};
		EventHandler<ActionEvent> selectRadioButton = (e) ->{
			listNodes.clear();
			if(listIsSelect.size() == 1){
				System.out.println("printaa");
				createNodes();
				listIsSelect.add(findRadioButtonSelected(e.getSource()));
				deactivateRadioButtons();
				startNetwork(listIsSelect, showDiaglogTextMsg(), gui);
			}else{
				listIsSelect.add(findRadioButtonSelected(e.getSource()));
				System.out.println("Marque o local de destino");
			}
		};

		//ACTIONS
		reset_network.setOnAction(actionReset);
		setActionInRadioButton(selectRadioButton);

	}

	//METHODS

	public void setLabelQuantity(){

		StringProperty observableCounter = new SimpleStringProperty(Integer.toString(Network.quantityOfPackets.get()));

		observableCounter.addListener((obs, oldValue, newValue) -> {
			Platform.runLater(()->{
			labelQuantityPackets.setText(Integer.toString(Network.quantityOfPackets.get())); });
			});
	}

	public void setMenuChoiceBox(){
		menu_options.getItems().addAll(listMenu);
		menu_options.setValue(listMenu[0]);
	}

	public Optional<String> showDiaglogTextMsg(){
		TextInputDialog dialogInputText = new TextInputDialog();
		dialogInputText.setContentText("Digite a sua menssagem.");
		Optional<String> result = dialogInputText.showAndWait();
		return result;
	}

	public void showDialogStart(){
		Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
		dialogoInfo.setContentText("Escolha dois pontos, o de origem e o de destino");
		dialogoInfo.showAndWait();
	}

	public void startNetwork(List<RadioButton> listIsSelect, Optional<String> msg, GuiPacket gui){
		msg.ifPresent(msgString ->{
			Network network = new Network(listNodes,
				findNodeWithRadioButton(listIsSelect.get(0)),
				findNodeWithRadioButton(listIsSelect.get(1)),
				menu_options.getSelectionModel().getSelectedItem(),
				msgString, gui);

			System.out.println(listIsSelect.get(0).getId());
			network.starNetowrk();
		});

		if (!msg.isPresent()) {
			resetNetWork();
		}
	}

	public void resetNetWork(){

		activateRadioButton();
		for (RadioButton radioButton : listRadioButtons) {
			if (radioButton.isSelected()) {
				radioButton.setSelected(false);
			}
		}
		listIsSelect.clear();

		Network.suicideThreadsAndCleanList();
	}

	public Router findNodeWithRadioButton(RadioButton radioButton){
		for (Router node: listNodes) {
			if(node.getRadioButton().equals(radioButton)){
				return node;
			}
		}
		return null;
	}

	public RadioButton findRadioButtonSelected(Object object){
		for(RadioButton radioButton : listRadioButtons){
			if(object.equals(radioButton)){ return radioButton;}
		}
		return null;
	}

	public void setActionInRadioButton(EventHandler<ActionEvent> event){
		for (RadioButton radioButton : listRadioButtons) {
			radioButton.setOnAction(event);
		}
	}

	public void deactivateRadioButtons(){
		for (RadioButton radioButton : listRadioButtons) {
			radioButton.setDisable(true);
		}
	}

	public void activateRadioButton(){
		for (RadioButton radioButton : listRadioButtons) {
			radioButton.setDisable(false);
		}
	}

	public void setStage(Stage stage){
		this.primaryStage = stage;
		gui.setStageInGui(stage);
	}

	public void createNodes() {
		Router nodeA = new Router(routerA);
		listNodes.add(nodeA);
		Router nodeB = new Router(routerB);
		listNodes.add(nodeB);
		Router nodeC = new Router(routerC);
		listNodes.add(nodeC);
		Router nodeD = new Router(routerD);
		listNodes.add(nodeD);
		Router nodeE = new Router(routerE);
		listNodes.add(nodeE);
		Router nodeF = new Router(routerF);
		listNodes.add(nodeF);
		Router nodeG = new Router(routerG);
		listNodes.add(nodeG);
		Router nodeH = new Router(routerH);
		listNodes.add(nodeH);
		Router nodeI = new Router(routerI);
		listNodes.add(nodeI);
		Router nodeJ = new Router(routerJ);
		listNodes.add(nodeJ);


		nodeA.setNeighbors(nodeB, nodeC, nodeE, nodeD, nodeG);
		nodeB.setNeighbors(nodeA, nodeG);
		nodeC.setNeighbors(nodeG, nodeA, nodeE, nodeF, nodeH);
		nodeD.setNeighbors(nodeA, nodeE);
		nodeE.setNeighbors(nodeA, nodeD, nodeC, nodeF);
		nodeF.setNeighbors(nodeE, nodeC, nodeH, nodeI);
		nodeG.setNeighbors(nodeB, nodeA, nodeC, nodeJ);
		nodeH.setNeighbors(nodeF, nodeC, nodeI, nodeJ);
		nodeJ.setNeighbors(nodeG, nodeH, nodeI);
		nodeI.setNeighbors(nodeF, nodeH, nodeJ);

		setGuiPackets();
	}

	public void setGuiPackets(){
		listNodes
			.stream()
			.forEach((node)-> node.setGuiPackets(gui));
	}

}
