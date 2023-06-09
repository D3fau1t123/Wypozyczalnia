import controller.RunController;
import view.ConsoleView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // utworzenie obiektu kontrolera i wykonanie metody run - start programu
        new RunController(new ConsoleView()).run();
    }
}