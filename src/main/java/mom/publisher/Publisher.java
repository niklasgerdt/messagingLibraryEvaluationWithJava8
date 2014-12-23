package mom.publisher;

import com.google.gson.Gson;
import mom.event.Event;

public abstract class Publisher {
    private final Gson gson = new Gson();

    public void pub(Event e) {
        String json = gson.toJson(e);
        pub(json);
    }

    abstract void pub(String notification);
}
