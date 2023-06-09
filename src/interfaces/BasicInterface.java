package interfaces;

// postawowy intrerfejs, rozszerzany przez pozostałe,
// w którym zadeklarowane są metody do pobierania i ustawiania id obiektów
public interface BasicInterface {
    String getId();
    BasicInterface setId(String id);
}
