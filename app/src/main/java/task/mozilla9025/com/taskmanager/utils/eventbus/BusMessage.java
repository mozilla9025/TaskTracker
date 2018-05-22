package task.mozilla9025.com.taskmanager.utils.eventbus;

import task.mozilla9025.com.taskmanager.models.Task;

public class BusMessage {

    public static final int ERROR_ID = 0;
    public static final int CREATE_TASK_ID = 1;
    public static final int EDIT_TASK_ID = 2;
    public static final int DELETE_TASK_ID = 3;
    public static final int PROJECT_CREATED_ID = 4;
    public static final int PROJECT_EDITED_ID = 5;
    public static final int PROJECT_DELETED_ID = 6;
    public static final int CLICK_TASK_ID = 7;

    private boolean error;
    private int eventId;
    private Task task;

    public BusMessage error() {
        this.eventId = ERROR_ID;
        this.error = true;
        return this;
    }

    public BusMessage projectCreated() {
        this.eventId = PROJECT_CREATED_ID;
        this.error = false;
        return this;
    }

    public BusMessage taskCreated() {
        this.eventId = CREATE_TASK_ID;
        this.error = false;
        return this;
    }

    public BusMessage taskEdited() {
        this.eventId = EDIT_TASK_ID;
        this.error = false;
        return this;
    }

    public BusMessage taskDeleted() {
        this.eventId = DELETE_TASK_ID;
        this.error = false;
        return this;
    }

    public BusMessage onTaskDelete(Task task) {
        this.eventId = DELETE_TASK_ID;
        this.error = false;
        this.task = task;
        return this;
    }

    public BusMessage onTaskClick(Task task) {
        this.eventId = CLICK_TASK_ID;
        this.error = false;
        this.task = task;
        return this;
    }

    public BusMessage onTaskEdit(Task task) {
        this.eventId = EDIT_TASK_ID;
        this.error = false;
        this.task = task;
        return this;
    }


    public BusMessage projectEdited() {
        this.eventId = PROJECT_EDITED_ID;
        this.error = false;
        return this;
    }

    public BusMessage projectDeleted() {
        this.eventId = PROJECT_DELETED_ID;
        this.error = false;
        return this;
    }

    public boolean isError() {
        return error;
    }

    public int getEventId() {
        return eventId;
    }

    public Task getTask() {
        return task;
    }
}
