package model;

import java.util.ArrayList;
import java.util.List;


/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 01/10/2022
 * Ultima alteracao.: 09/10/2022
 * Nome: ...........: ListProcess
 * Funcao...........: lista de processos que guarda o processo no estado em que ele esta
 * todos processos sao adicionados em alguma lista
 * ***************************************************************/

public class ListProcesses {
  
  List<Job> listProcess;
  List<Job> listProcessBkp;
  List<Job> listProcesAux;
  List<Job> listProcessesFinish;
  List<Job> listMemory;
  
   public ListProcesses(){
    this.listMemory = new ArrayList<>();
    this.listProcess = new ArrayList<>();
    this.listProcessBkp = new ArrayList<>();
    this.listProcesAux = new ArrayList<>();
    this.listProcessesFinish = new ArrayList<>();
   }

  public List<Job> getListMemory() {
    return listMemory;
  }

  public void setListMemory(List<Job> listMemory) {
    this.listMemory = listMemory;
  }

  public List<Job> getListProcess() {
    return listProcess;
  }

  public void setListProcess(List<Job> listProcess) {
    this.listProcess = listProcess;
  }

  public List<Job> getListProcessBkp() {
    return listProcessBkp;
  }

  public void setListProcessBkp(List<Job> listProcessBkp) {
    this.listProcessBkp = listProcessBkp;
  }

  public List<Job> getListProcessesFinish() {
    return listProcessesFinish;
  }

  public void setListProcessesFinish(List<Job> listProcessesFinish) {
    this.listProcessesFinish = listProcessesFinish;
  }

  public List<Job> getListProcesAux() {
    return listProcesAux;
  }

  public void setListProcesAux(List<Job> listProcesAux) {
    this.listProcesAux = listProcesAux;
  }

}
