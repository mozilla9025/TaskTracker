package task.mozilla9025.com.taskmanager.utils.eventbus;

public class BusMessage {

    private boolean error;

    public BusMessage error() {
        this.error = true;
        return this;
    }

}
