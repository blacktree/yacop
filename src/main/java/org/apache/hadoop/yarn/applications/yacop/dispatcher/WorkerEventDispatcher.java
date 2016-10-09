package org.apache.hadoop.yarn.applications.yacop.dispatcher;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.yarn.applications.yacop.NAppMaster;
import org.apache.hadoop.yarn.applications.yacop.event.WorkerEvent;
import org.apache.hadoop.yarn.applications.yacop.worker.Worker;
import org.apache.hadoop.yarn.event.EventHandler;

public class WorkerEventDispatcher implements EventHandler<WorkerEvent> {

  private NAppMaster.AppContext context;
  private static final Log LOG = LogFactory.getLog(WorkerEventDispatcher.class);

  public WorkerEventDispatcher(NAppMaster.AppContext context) {
    this.context = context;
  }

  @Override
  public void handle(WorkerEvent workerEvent) {
    LOG.info("dispatch WorkerEvent: " + workerEvent);
    Worker worker = context.getJob().getTask(workerEvent.getWorkerId().getTaskId())
        .getWorker(workerEvent.getWorkerId());
    ((EventHandler<WorkerEvent>)worker).handle(workerEvent);
  }
}
