package in.showoffs.showoffs.utils;

import in.showoffs.showoffs.activities.Dashboard;

public class LoginDispatcher extends ShowOffSLoginDispatcher {

    @Override
    protected Class<?> getTargetClass() {
        return Dashboard.class;
    }


}
