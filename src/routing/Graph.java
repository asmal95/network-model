package routing;

import network.elements.PathElement;
import network.Network;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    boolean matrix[][];

    /**
     * Создает граф основанный на сети
     * @param network сеть
     */
    public Graph(Network network) {
        matrix = getMatrix(network);
    }

    /**
     * Возвращает число вершин графа
     * @return число вершин графа
     */
    public int countNode() {
        return matrix.length;
    }

    /**
     * Проверяет, соеденены ли две вершины ребром
     * @param i номер первой вершины
     * @param j номер второй вершины
     * @return true, если соеденены; false если не соеденены
     */
    public boolean isConnect(int i, int j) {
        return matrix[i][j] || matrix[j][i];
    }

    private boolean[][] getMatrix(Network network) {
        List<PathElement> elements = network.getElements();
        int len = elements.size();
        boolean matrix[][] = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (elements.get(i).isConnect(elements.get(j))) {
                    matrix[i][j] = true;
                } else {
                    matrix[i][j] = false;
                }
            }
        }
        return matrix;
    }
}
