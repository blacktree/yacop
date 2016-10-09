package org.apache.hadoop.yarn.applications.yacop.event;


public enum TaskEventType {
  TASK_SCHEDULE,
  TASK_LAUNCH,
  TASK_COMPLETED,
  TASK_KILL,
  TASK_SETUP,
  TASK_ERROR
}
