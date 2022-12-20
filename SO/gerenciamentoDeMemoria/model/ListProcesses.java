package model;

import java.util.ArrayList;
import java.util.List;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 09/09/2022
 * Ultima alteracao.: 17/09/2022
 * Nome: ...........: ListProcesses.java
 * Funcao...........: classe com listas dos processos
 * ***************************************************************/

public class ListProcesses {
  
  List<Job> listProcess;
  List<Job> listProcessBkp;
  List<Job> listProcessesFinish;
  List<Job> listMemory;
  
   public ListProcesses(){
    this.listMemory = new ArrayList<>();
    this.listProcess = new ArrayList<>();
    this.listProcessBkp = new ArrayList<>();
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
}
