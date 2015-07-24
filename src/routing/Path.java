package routing;

import network.elements.PathElement;
import network.Package;

import java.util.ArrayList;
import java.util.List;


public class Path {

    private List<PathElement> elements;

    /**
     * Создает экземпляр пути на основе элементов пути
     * @param elements коллекция PathElement'ов пути
     */
    public Path(List<PathElement> elements) {
        this.elements = elements;
    }

    /**
     * Возвращает элементы пути
     * @return колелкция PathElement'ов пути
     */
    public List<PathElement> getElements() {
        return elements;
    }

    /**
     * Возвращает стоимость прохождения 1 байта информации
     * @return стоимость
     */
    public double getCost() {
        double cost = 0;
        for (PathElement el : elements) {
            cost += el.getCost();
        }
        return cost;
    }

    /**
     * Возвращает время в ms прохождения 1 байта информации
     * @return время ms
     */
    public double getTimeDelay() {
        double timeDelay = 0;
        for (PathElement el : elements) {
            timeDelay += el.getTimeDelay();
        }
        return timeDelay;
    }

    /**
     * Возвращает стоимость прохождения пакета по сети
     * @param pck сетевой пакет
     * @return стоимость
     */
    public double getCost(Package pck) {
        return getCost() * pck.getSize();
    }

    /**
     * Возвращает время в ms прохождения пакета по сети
     * @param pck сетевой пакет
     * @return время ms
     */
    public double getTimeDelay(Package pck) {
        return getTimeDelay() * pck.getSize();
    }

    /**
     * Возвращает число элементов в пути
     * @return число элементов
     */
    public int getLength() {
        return elements.size();
    }

    /*
        //тут можно проверять, возможно ли прохождение по сети.
        //првеку делать в файерволе. И если он запрещает пакеты с определенных IP, то блокировать прохождение пакета
    public boolean isPassagePackage(Package pck) {
        return true;
    }*/

}
