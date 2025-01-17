package org.cpuscheduling.Scheduler;

import java.util.ArrayList;
import java.util.Queue;
import org.cpuscheduling.Process.Process;

public abstract class Scheduler {
    protected ArrayList<Process> processes;
    protected int contextSwitchTime;
    Scheduler(int contextSwitchTime) {
        processes = new ArrayList<>();
        this.contextSwitchTime = contextSwitchTime;
    }
    public abstract ArrayList<ExecutionRecord> run();
    public void addProcess(Process process) {
        processes.add(process);
    }
    public void setContextSwitchTime(int contextSwitchTime){
        this.contextSwitchTime = contextSwitchTime;
    }
    public int getContextSwitchTime(){ return contextSwitchTime; }
    public ArrayList<Process> getProcesses(){ return processes; }
    protected int addProcessesToQueue(Queue<Process> queue, int index, int currentTime) {
        while (index < processes.size() && currentTime >= processes.get(index).getProperty("arrivalTime"))
            queue.add(processes.get(index++));
        return index;
    }
    protected void addRecord(ArrayList<ExecutionRecord> records, Process process, int currentTime, int runningTime) {
        if (!records.isEmpty() && records.get(records.size()-1).processIndex == process.getProperty("index")){
            runningTime += records.get(records.size()-1).runningTime;
            records.remove(records.size()-1);
        }
        records.add(new ExecutionRecord(process.getProperty("index"), currentTime - runningTime, runningTime));
    }
}