package task.mozilla9025.com.taskmanager.utils.eventbus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class GlobalBus {

    private static Bus bus;

    public static Bus getBus() {
        if (bus == null) {
            bus = new Bus(ThreadEnforcer.ANY);
        }
        return bus;
    }
}
