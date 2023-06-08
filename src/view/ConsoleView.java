package view;

import interfaces.BasicInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.net.URLDecoder;

public class ConsoleView {
    protected Properties properties;

    public ConsoleView() throws IOException {


        String rootPath = Thread.currentThread().getContextClassLoader().getResource("resources/").getPath();
        //String appConfigPath = rootPath + "messages.xml";
        String decodedRootPath = URLDecoder.decode(rootPath, "UTF-8");
        String decodedAppConfigPath = decodedRootPath + "messages.xml";


        this.properties = new Properties();
        this.properties.loadFromXML(new FileInputStream(decodedAppConfigPath));
    }

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

    public void print(String[] data) {
        System.out.println("-------------------------------");
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }
    }

    public void printLine(String line) {
        System.out.print(line);
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public void printError(String line) {
        System.err.print(line);
    }
}
