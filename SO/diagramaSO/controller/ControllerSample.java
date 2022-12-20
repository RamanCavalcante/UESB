package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gui.JobBCPGUI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.JobBCP;
import model.ProcessStates;

public class ControllerSample implements Initializable {

	@FXML
	private Button btn_closesProcesses;

	@FXML
	private Button btn_startProcesses;

	@FXML
	private Spinner<Integer> spinner_NumeberProcesses;

	@FXML
	private Spinner<Integer> spinner_TimeSlicing;

	@FXML
	private VBox vBox_blocked;

	@FXML
	private VBox vBox_finished;

	@FXML
	private VBox vBox_ready;

	@FXML
	private VBox vBox_start;

	@FXML
	private VBox vBox_runing;

	@FXML
	private HBox hbox_process;
	
	@FXML
	private CheckBox check_block;

	@FXML
	private ChoiceBox<String> menu_Scheduling;
	private ArrayList<JobBCP> processesGUI;
	private ArrayList<ProcessStates> listStates = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String[] listMenu = { "FIFO", "PRIORIDADE", "SHORTESD JOB FIRST"};
		menu_Scheduling.setItems(FXCollections.observableArrayList(listMenu));
		menu_Scheduling.setValue(listMenu[0]);
		SpinnerValueFactory<Integer> valueTimeSlicing = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10);
		SpinnerValueFactory<Integer> valueNumberProcesses = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
		spinner_NumeberProcesses.setValueFactory(valueNumberProcesses);
		spinner_TimeSlicing.setValueFactory(valueTimeSlicing);

		processesGUI = new ArrayList<>();
	}

	@FXML
	void startProcesses(ActionEvent event) {
		ProcessStates.stop = false;
		JobBCPGUI jobGUI = new JobBCPGUI(vBox_ready, vBox_blocked, vBox_finished, hbox_process, vBox_runing);
		ProcessStates.timeSlicing = spinner_TimeSlicing.getValue();
		ProcessStates newProcesses = new ProcessStates(spinner_NumeberProcesses.getValue(),
				menu_Scheduling.getSelectionModel().getSelectedIndex(), redeem_value() , jobGUI);

		newProcesses.start();
		listStates.add(newProcesses);
	}

	@FXML
	void closeProcesses(ActionEvent event){
		System.out.println("lsls");
		ProcessStates processes = listStates.get(0);
		JobBCPGUI.resetBox(processes.jobGUI.getBlockedVBox());
		JobBCPGUI.resetBox(processes.jobGUI.getReadyVBox());
		JobBCPGUI.resetBox(processes.jobGUI.getvBox_runing());
		ProcessStates.stop = true;

	}

	@FXML
	public boolean redeem_value(){
		return check_block.selectedProperty().getValue();
	}
}