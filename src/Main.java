import controller.RunController;
import view.ConsoleView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new RunController(new ConsoleView()).run();
    }
}
