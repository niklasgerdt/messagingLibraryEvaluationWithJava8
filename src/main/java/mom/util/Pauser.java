package mom.util;

public class Pauser {
    private final long nanoPause;

    public Pauser(long nanoPause) {
        this.nanoPause = nanoPause;
    }

    // Busy wait for granularity
    public void pause() {
        long free = System.nanoTime() + nanoPause;
        while (System.nanoTime() < free) {}
    }
}
