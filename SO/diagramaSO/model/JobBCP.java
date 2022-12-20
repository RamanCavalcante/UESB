package model;

import org.omg.PortableInterceptor.INACTIVE;

public class JobBCP {
    
    int runtime, processPriority, deadLine, blockedProcess, timeOfArrival; 
    String nameProcess, processMemory;
    int listOrder, average;

    


    public JobBCP() {}

    public JobBCP(String nameProcess, int runtime, int processPriority, int processMemory, 
                  int deadLine, int blockedProcess, int timeOfArrival){
        this.nameProcess = nameProcess;
        this.runtime = runtime;
        this.processPriority  = processPriority;
        this.processMemory = "MB "+processMemory;
        this.blockedProcess = blockedProcess;
        this.timeOfArrival = timeOfArrival;
        this.deadLine = deadLine;
        this.average = runtime/deadLine;
    }

    public int getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(int timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public int getListOrder() {
        return listOrder;
    }

    public void setListOrder(int listOrder) {
        this.listOrder = listOrder;
    }

    public int getBlockedProcess() {
        return blockedProcess;
    }

    public void setBlockedProcess(int blockedProcess) {
        this.blockedProcess = blockedProcess;
    }

    public void setProcessMemory(String processMemory) {
        this.processMemory = processMemory;
    }

    public void setNameProcess(String nameProcess){
        this.nameProcess = nameProcess;
    }

    public String getNameProcess(){
        return nameProcess;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getProcessPriority() {
        return processPriority;
    }

    public void setProcessPriority(int processPriority) {
        this.processPriority = processPriority;
    }

    public String getProcessMemory() {
        return processMemory;
    }

    public void setProcessMemory(int processMemory) {
        this.processMemory = "MB "+processMemory;
    }

    public int getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(int deadLine) {
        this.deadLine = deadLine;
    }

    public int getAverage() {
        return (int)(runtime/deadLine);
    }

    public void setAverage(int average) {
        this.average = average;
    }
}
