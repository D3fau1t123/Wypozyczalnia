package view;

import interfaces.BasicInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;

// klasa odpowiadająca za wyświetlanie danych i komunikatów
public class ConsoleView {
    protected Properties properties;

    public ConsoleView() throws IOException {
        // pobieranie treści komunikatów z pliku xml - wykorzystanie klasy Properties
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("resources/").getPath();
        //String appConfigPath = rootPath + "messages.xml";
        String decodedRootPath = URLDecoder.decode(rootPath, "UTF-8");
        String decodedAppConfigPath = decodedRootPath + "messages.xml";


        this.properties = new Properties();
        this.properties.loadFromXML(new FileInputStream(decodedAppConfigPath));
    }

    // metoda zamieniająca strukturę mapy na tablicę string i dodająca tytuł
    public String[] getMap(String title, Map<String, ? extends BasicInterface> map) {
        String[] result = new String[map.size()+1];
        result[0] = title;
        int i = 1;

        for (String key: map.keySet()) {
            result[i] = map.get(key).toString();
            i++;
        }

        return result;
    }


    // motoda pomocnicza do wypisywania danych
    public void print(String[] data) {
        System.out.println("-------------------------------");
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }
    }

    // motoda pomocnicza do wypisywania komunikatów
    public void printLine(String line) {
        System.out.print(line);
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    // motoda pomocnicza do wypisywania błędów
    public void printError(String line) {
        System.err.print(line);
    }
}
