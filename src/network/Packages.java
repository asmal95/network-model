package network;

import java.util.ArrayList;
import java.util.List;

/**
 * Контейнер для пакетов. Реализует поиск и удаление пакетов по имени.
 * Используется для хранения различных пакетов, которые можно передавать по сети.
 */
public class Packages {
    private List<Package> packages = new ArrayList<>();

    /**
     * Добавляет новые элементы в храниелище пакетов
     * @param pck экземпляр Package
     */
    public void addPackage(Package pck) {
        if (pck == null) throw new IllegalArgumentException("null value for package unacceptable.");
        packages.add(pck);
    }

    /**
     * Удаляет пакет по имени
     * @param name имя удаляемого пакета
     */
    public void deletePackageByName(String name) {
        Package delPack = null;
        for (Package p : packages) {
            if (p.getName().equals(name)) {
                delPack = p;
            }
        }
        if (delPack == null) throw new NotFoundPackageException(name);
        packages.remove(delPack);
    }

    /**
     * Возвращает пакет по имени
     * @param name имя пакета
     * @return экземпляр Package
     */
    public Package getPackageByName(String name) {
        Package serPck = null;
        for (Package p : packages) {
            if (p.getName().equals(name)) {
                serPck = p;
            }
        }
        if (serPck == null) throw new NotFoundPackageException(name);
        return serPck;
    }

    /**
     * Возаращает все пакеты
     * @return колеекция элементов Package
     */
    public List<Package> getPackges() {
        return packages;
    }
}