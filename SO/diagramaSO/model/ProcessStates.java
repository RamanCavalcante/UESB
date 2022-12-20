package model;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Semaphore;
import gui.JobBCPGUI;
import javafx.application.Platform;
import javafx.scene.layout.HBox;

public class ProcessStates extends Thread {

  public HBox hbox_processo;
  private int numberOfProcesses, whyScheduling;
  private boolean blocked;
  public static boolean  stop = false;

  public static int timeSlicing = 3;
  private List<JobBCP> listJOBSready = new ArrayList<JobBCP>();
  private List<JobBCP> listJOBSblocked = new ArrayList<JobBCP>();
  private List<JobBCP> listJOBSruning = new ArrayList<>();
  private List<JobBCP> listJOBSclosed = new ArrayList<>();
  public JobBCPGUI jobGUI;
  Semaphore sem = new Semaphore(1);

  private String nameProcesses[] = { "Bananonina", "Me Want Banana", "Bee Do Bee Do Bee Do", "Baboi",
      "Tatata Bala Tu", "Poopaye", "Birosca", "Bugiganga", "Bulhufas",
      "Chumbrega", "Calvo de cria", "Mixuruquinha" };

  /**
   * @param numberOfProcesses
   * @param whyScheduling
   */
  public ProcessStates(int numberOfProcesses, int whyScheduling, boolean blocked, JobBCPGUI jobGUI) {

    this.numberOfProcesses = numberOfProcesses;
    this.whyScheduling = whyScheduling;
    this.blocked = blocked;
    this.jobGUI = jobGUI;
  }

  public void run() {// fluxo de todos processos

    try {
      createProcesses();
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    } // processos criados e quardados na lista

    Scheduling objScheduling = new Scheduling();

    while (!listJOBSready.isEmpty() | !listJOBSblocked.isEmpty()) {
      
      if(stop == true){//encerra todos os processos
        listJOBSruning.clear();
        listJOBSready.clear();
        listJOBSblocked.clear();
        listJOBSclosed.clear();
      
        break;
      }
      JobBCPGUI.resetBox(jobGUI.getReadyVBox());// reseta VBox de pronto

      switch (whyScheduling) {// escolha de escalonamento
        case 0:
          listJOBSready = objScheduling.fIFO(listJOBSready);
          break;
        case 1:
          listJOBSready = objScheduling.priority(listJOBSready);
          break;
        case 2:
          listJOBSready = objScheduling.shortestJobFirst(listJOBSready);
          break;
        case 3:
          // listJOBSready = objScheduling.garanteed(listJOBSready);
          break;
        case 5:
          // listJOBSready = objScheduling.roundRobin(listJOBSready);
          break;
        case 7:
          // listJOBSready = objScheduling.multiLevelQueues(listJOBSready);
      }
      if (!listJOBSready.isEmpty()) { // caso lista de pronto nao conter processos
        try {
          sem.acquire();

          JobBCP process = listJOBSready.get(0);// processo removido da lista de pronto
          listJOBSready.remove(listJOBSready.get(0));
          JobBCPGUI.printListProcesses(jobGUI.getReadyVBox(), listJOBSready);// imprime todos os processos no estado de
          JobBCPGUI.printProcessRuning(jobGUI.getvBox_runing(), process);
          listJOBSruning.add(process);// adiciona na lista de executando
          int retornoRuningProcess = runProcesses(process);// processo adicionado ao estado de executando
          JobBCPGUI.resetBox(jobGUI.getvBox_runing());

          if (retornoRuningProcess == 0) {// processo finalizado
            listJOBSruning.remove(process);// remove da lista de executando
            processFinish(process);
          } else if (retornoRuningProcess == 1) {// processo voltou para o estado de pronto
            listJOBSruning.remove(process);// remove da lista de executando
            addProcessReady(process);
          } else if (retornoRuningProcess == 2) {// processo esta bloqueado
            listJOBSruning.remove(process);// remove da lista de executando
            addBlockProcesses(process);
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      sem.release();
    }
    
  }

  // estado de inicio
  private void createProcesses() throws InterruptedException {

    Random random = new Random();

    for (int i = numberOfProcesses; i > 0; i--) {// criacao de processo

      JobBCP process = new JobBCP();

      process.setNameProcess(nameProcesses[random.nextInt(nameProcesses.length)]); // nome do processo
      int runTime = random.nextInt((15 - 2) + 1) + 2;
      process.setRuntime(runTime); // tempo que o processo deve ser executado
      process.setDeadLine(random.nextInt((runTime - 2) + 1) + 2);// gera numero o deadline menor que runTime
      process.setProcessPriority(random.nextInt(5) + 1); // prioridade do processo 1 - 5
      process.setProcessMemory(random.nextInt() * 100); // memoria do processo
      process.setBlockedProcess(calcBlockingChance(random.nextInt(10) + 1));

      // process.setBlockedProcess(random.nextInt((100 - 10) + 1) + 10);
      addProcessReady(process);
      Thread.sleep(1000);
    }
  }

  // estado de pronto
  private void addProcessReady(JobBCP process) {

    listJOBSready.add(process);

    if (process.getListOrder() == 0)
      process.setListOrder(listJOBSready.size());

    JobBCPGUI.printProcess(jobGUI.getReadyVBox(), process);

    System.out.println(String.format("adding process %s - %d ",
        process.getNameProcess(),
        process.getListOrder()));

  }

  // estado de executando
  private int runProcesses(JobBCP process) throws InterruptedException {

    int timeSpent;

    System.out.println("RUNING");
    printProcess(process);

    if (process.getBlockedProcess() == 0) {// caso processo esteja bloqueado
      if (process.getRuntime() >= timeSlicing) {// processo sera executado
        Thread.sleep(timeSlicing * 1000);
        process.setRuntime(process.getRuntime() - timeSlicing);

        if (process.getRuntime() < timeSlicing) {// processo vai terminar

          Thread.sleep(process.getRuntime() * 1000);
          timeSpent = process.getRuntime();
          process.setRuntime(process.getRuntime() - timeSpent);

          return 0;
        } else {
          return 1;
        }
      } else {
        process.setRuntime(0);
        return 0;// processo finalizado
      }
    } else {// caso processo esteja bloqueado
      return 2;
    }

  }

  // estado de bloqueado
  private void addBlockProcesses(JobBCP process) throws InterruptedException {
    listJOBSblocked.add(process);
    JobBCPGUI.resetBox(jobGUI.getBlockedVBox());
    JobBCPGUI.printListBlockedProcesses(jobGUI.getBlockedVBox(), listJOBSblocked);

      Thread.sleep(process.getBlockedProcess() * 1000);
      
      process.setBlockedProcess(0);// desbloqueia processo
      listJOBSblocked.remove(process);
      JobBCPGUI.resetBox(jobGUI.getBlockedVBox());
      JobBCPGUI.printListBlockedProcesses(jobGUI.getBlockedVBox(), listJOBSblocked);
      addProcessReady(process);
  }

  public void processFinish(JobBCP process) {
    listJOBSclosed.add(process);
    JobBCPGUI.resetBox(jobGUI.getFinishVBox());
    JobBCPGUI.printListProcesses(jobGUI.getFinishVBox(), listJOBSclosed);
  }

  public void printListProcesses() {

    listJOBSready.stream().forEach(process -> System.out.println("Name : " + process.getNameProcess() + "\n" +
        "position in the list: " + process.getListOrder() + "\n" +
        "Runtime : " + process.getRuntime() + " Seconds\n" +
        "DeadLine: " + process.getDeadLine() + "Seconds\n" +
        "Priority : " + process.getProcessPriority() + "\n" +
        "Memorie : " + process.getProcessMemory() + "\n" +
        "\n=====================\n"));
  }

  public void printProcess(JobBCP process) {

    System.out.println("Name : " + process.getNameProcess() + "\n" +
        "position in the list: " + process.getListOrder() + "\n" +
        "Runtime : " + process.getRuntime() + " Seconds\n" +
        "DeadLine : " + process.getDeadLine() + " Seconds\n" +
        "Priority : " + process.getProcessPriority() + "\n" +
        "Memorie : " + process.getProcessMemory());
    System.out.println("\n=====================\n");
  }

  private int calcBlockingChance(int randomBlock) {
    int probability = 7;
    if(blocked == true)
      probability = 1;
    return (randomBlock > probability) ? randomBlock : 0;

  }


  public List<JobBCP> getListJOBSready() {
    return listJOBSready;
  }

  public void setListJOBSready(List<JobBCP> listJOBSready) {
    this.listJOBSready = listJOBSready;
  }

  public List<JobBCP> getListJOBSblocked() {
    return listJOBSblocked;
  }

  public void setListJOBSblocked(List<JobBCP> listJOBSblocked) {
    this.listJOBSblocked = listJOBSblocked;
  }

  public List<JobBCP> getListJOBSruning() {
    return listJOBSruning;
  }

  public void setListJOBSruning(List<JobBCP> listJOBSruning) {
    this.listJOBSruning = listJOBSruning;
  }

  public List<JobBCP> getListJOBSclosed() {
    return listJOBSclosed;
  }

  public void setListJOBSclosed(List<JobBCP> listJOBSclosed) {
    this.listJOBSclosed = listJOBSclosed;
  }
}
