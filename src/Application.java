/*
Application.java - Thomas

This interface represents a runnable Application, for example the main game. It has a method
called when the application launches for init, one for looping through logic, and one for
cleaning up everything when it is done. The ApplicationWrapper class exists to help launch Applications.
 */

public interface Application {
    void start();
    void loop();
    void stop();
}
