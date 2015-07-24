package network;

import java.util.ArrayList;
import java.util.List;

/**
 * Представляет хранилище для сетей
 * Может добавлять в себя новые сети.
 * Может вернуть коллекцию сетей.
 * Может вернуть сеть по имени
 */
public class Networks {
    List<Network> networks = new ArrayList<>();

    public void addNetwork(Network network) {
        if (network == null) throw new IllegalArgumentException("network is null");
        networks.add(network);
    }

    /**
     * Ищет и возвращает сеть по имени
     * @param name имя искомой сети
     * @return экземпляр Network
     */
    public Network getNetworkByName(String name) {
        if (name == null) throw new IllegalArgumentException("name is null");
        Network network = null;
        for (Network nw : networks) {
            if (name.equals(nw.getName())) {
                network = nw;
            }
        }
        if (network == null) throw new NotFoundNetworkException(name);
        return network;
    }

    /**
     * Удаляет указанную сеть
     * @param name имя удаляемой сети
     */
    public void deleteNetworkByName(String name) {
        if (name == null) throw new IllegalArgumentException("name is null");
        Network network = null;
        for (Network nw : networks) {
            if (name.equals(nw.getName())) {
                network = nw;
            }
        }
        if (network == null) throw new NotFoundNetworkException(name);
        networks.remove(network);
    }

}
