package terminal;

import network.*;

import java.lang.Package;
import java.util.List;

/**
 * Используется для передачи необходимых параметров терминалу и коммандам.
 * Может хранить: сети, пакеты, класс для загрузки и сохранения сети в XML
 */
public class Parameters {
    //List<Network> networks;
    Networks networks = new Networks();
    Packages packages = new Packages();

    public Networks getNetworks() {
        return networks;
    }

    public Packages getPackages() {
        return packages;
    }
}
