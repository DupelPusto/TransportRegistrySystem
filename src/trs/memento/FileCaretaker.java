package trs.memento;

import java.io.*;

public class FileCaretaker {
    private static final String FILE_NAME = "registry_data.ser";

    public void saveState(SystemMemento memento) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(memento);
        } catch (IOException e) {
            System.err.println("Помилка збереження бази даних: " + e.getMessage());
        }
    }

    public SystemMemento loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (SystemMemento) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Помилка завантаження бази даних: " + e.getMessage());
            return null;
        }
    }
}