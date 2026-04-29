public class ApplicationWrapper {
    private Application app;

    public ApplicationWrapper(Application a) {
        app = a;
    }

    public void launch() {
        app.start();
        app.loop();
        app.stop();
    }
}
