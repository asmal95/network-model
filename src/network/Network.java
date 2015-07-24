package network;

import network.elements.PathElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Предвтавляет собой контейнер для устройств связанных между собой
 * Предоставляет методы для управления элементами в сети: соединение, разрыв связи, добавление, удаление..
 */
public class Network {
    private String name;
    private List<PathElement> elements;

    public Network(String name, ArrayList<PathElement> elements) {
        if (name == null) throw new IllegalArgumentException();
        this.name = name;
        this.elements = elements;
    }

    public Network(ArrayList<PathElement> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает имя сети
     * @return имя сети
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает все элементы сети
     * @return коллекция элементов сети - наследников PathElement
     */
    public List<PathElement> getElements() {
        return elements;
    }

    /**
     * Добавдяет новые элементы в сеть.
     * @param element экземпляр населедника класса PathElement
     */
    public void addElement(PathElement element) {
        if (element == null) throw new IllegalArgumentException("null value for elements unacceptable.");
        elements.add(element);
    }

    /**
     * Соединяет два элемента в сети
     * @param id1 ID первого элемента.
     * @param id2 ID второго элемента
     */
    public void connect(int id1, int id2) {
        PathElement first, second;
        first = getElementByID(id1);
        second = getElementByID(id2);
        first.connect(second);
    }

    /**
     * Разъединяет два элемента в сети
     * @param id1 ID первого элемента.
     * @param id2 ID второго элемента
     */
    public void disconnect(int id1, int id2) {
        PathElement first, second;
        first = getElementByID(id1);
        second = getElementByID(id2);
        first.disconnect(second);
    }

    /**
     * Удаляет элемент из сети.
     * Предварительно разъединяет все подключения с этим элементом
     * @param id ID удаляемого устройства
     */
    public void delete(int id) {
        PathElement element = getElementByID(id);

        //надо разорвать все подключения с этим элементом
        List<PathElement> adjacentElements = element.getConnections();

        while (adjacentElements.size() != 0) {
            PathElement adjacent = adjacentElements.get(0);
            adjacent.disconnect(element);
        }

        elements.remove(element);
    }


    private PathElement getElementByID(int id) {
        int index = -1;
        for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).getID() == id) index = i;

        }
        if (index == -1) { throw new IDNotFoundException(Integer.toString(id)); }
        return elements.get(index);
    }
}