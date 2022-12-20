package model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Scheduling {

  public List<JobBCP> fIFO(List<JobBCP> listJOBS) {
    List<JobBCP> listFIFO = listJOBS.stream()
        .sorted(Comparator.comparing((JobBCP::getListOrder)))
        .collect(Collectors.toList());
    return listFIFO;
  }

  public List<JobBCP> priority(List<JobBCP> listJOBS) {

    List<JobBCP> ListPriority = listJOBS.stream()
        .sorted(Comparator.comparing((JobBCP::getProcessPriority)))
        .collect(Collectors.toList());
    return ListPriority;
  }

  public List<JobBCP> shortestJobFirst(List<JobBCP> listJOBS) {
    List<JobBCP> lisJobsFirst = listJOBS.stream()
        .sorted(Comparator.comparing((JobBCP::getRuntime)))
        .collect(Collectors.toList());
    return lisJobsFirst;
  }

}
