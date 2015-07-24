package routing;

import ip.IP;
import ip.IPNotFoundException;
import network.elements.ActiveElement;
import network.elements.PathElement;
import network.Network;

import java.util.ArrayList;
import java.util.List;


public class Routing {
	Network network;
	int[] buf;
	List<List<Integer>> paths;
	boolean[][] matrix;
	boolean[] include;	//включена ли точка в путь

	/**
	 * Конструктор, создающий объект для сети, через который можно получать различную информацию о путях сети
	 * @param network сеть, для которой планируется выполнять роутинг
	 */
	public Routing(Network network) {
		if (network == null) { throw new RuntimeException(); }
		this.network = network;
		buf = new int[network.getElements().size()];
		include = new boolean[network.getElements().size()];
		for (int i = 0; i < include.length; i++) { include[i] = false; }
	}

	/**
	 * Выполняет поиск самых дешевых путей в сети
	 * @param ip1 IP адрес перового устройства
	 * @param ip2 IP адрес второго устройства
	 * @return Коллекция путей (Path). В коллекции может быть несколько разных путей с одинаковой стоимостью передачи
	 */
	public List<Path> getCheapRoute(IP ip1, IP ip2) {
		List<Path> routes = getRoute(ip1, ip2);
		List<Path> resRoutes = new ArrayList<>();
		double costs[] = new double[routes.size()];
		int indexMinCost = 0;
		for (int i = 0; i < routes.size(); i++) {
			costs[i] = routes.get(i).getCost();
			if (costs[i] < costs[indexMinCost]) indexMinCost = i;	//сохраняем индекс элемента с наименьшей ценой.
		}
		for (int i = 0; i < costs.length; i++) {
			if (costs[i] == costs[indexMinCost])	//находим все элементы с минимальной ценой, ведь их может быть несколько с одной ценой.
				resRoutes.add(routes.get(i));
		}
		return resRoutes;
	}

	/**
	 * Выполняет поиск путей в сети с наименьшей скоростью передачи
	 * @param ip1 IP адрес перового устройства
	 * @param ip2 IP адрес второго устройства
	 * @return Коллекция путей (Path). В коллекции может быть несколько разных путей с одинаковой скоростью передачи
	 */
	public List<Path> getLeastDelayRoute(IP ip1, IP ip2) {
		List<Path> routes = getRoute(ip1, ip2);
		List<Path> resRoutes = new ArrayList<>();
		double delays[] = new double[routes.size()];
		int indexMinDelay = 0;
		for (int i = 0; i < routes.size(); i++) {
			delays[i] = routes.get(i).getTimeDelay();
			if (delays[i] < delays[indexMinDelay]) indexMinDelay = i;
		}
		for (int i = 0; i < delays.length; i++) {
			if (delays[i] == delays[indexMinDelay])
				resRoutes.add(routes.get(i));
		}
		return resRoutes;
	}

	/**
	 * Выполняет поиск самых коротких путей в сети (состоящих из наименьшего количества путей)
	 * @param ip1 IP адрес перового устройства
	 * @param ip2 IP адрес второго устройства
	 * @return Коллекция путей (Path). В коллекции может быть несколько разных путей с одинаковым количеством элементов
	 */
	public List<Path> getShortRoute(IP ip1, IP ip2) {
		List<Path> routes = getRoute(ip1, ip2);
		List<Path> resRoutes = new ArrayList<>();
		int lengths[] = new int[routes.size()];
		int indexMinLength = 0;
		for (int i = 0; i < routes.size(); i++) {
			lengths[i] = routes.get(i).getLength();
			if (lengths[i] < lengths[indexMinLength]) indexMinLength = i;
		}
		for (int i = 0; i < lengths.length; i++) {
			if (lengths[i] == lengths[indexMinLength])
				resRoutes.add(routes.get(i));
		}
		return resRoutes;
	}

	/**
	 * Возвращает все возможные пути от первого устройства до второго
	 * @param ip1 IP адрес перового устройства
	 * @param ip2 IP адрес второго устройства
	 * @return Коллекция путей (Path).
	 */
	public List<Path> getRoute(IP ip1, IP ip2) {
		paths = new ArrayList<>();
		int index1 = -1, index2 = -1;
		List<PathElement> elements = network.getElements();
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i) instanceof ActiveElement) {
				ActiveElement aEl = (ActiveElement)elements.get(i);
				if (aEl.getIP().equals(ip1)) index1 = i;
				if (aEl.getIP().equals(ip2)) index2 = i;
			}
		}

		//через исключения будем передавать имя не найденного IP. Получить это имя можно будет потом через getMessage
		if (index1 == -1) { throw new IPNotFoundException(ip1.toString()); }
		if (index2 == -1) { throw new IPNotFoundException(ip2.toString()); }

		matrix = this.getMatrix();

		search(index1, index2, 0);

		//теперь все пути добавленны в двумерный массив, смоделированный на ArrayList
		//Составим теперь коллекцию коллекций из элементов Path
		List<Path> collectionPath = new ArrayList<>();
		for (List<Integer> path : paths) {
			List<PathElement> pathElements = new ArrayList<>();
			pathElements.add(0, elements.get(index1));
			for (int i : path) {
				pathElements.add(elements.get(i));
			}
			collectionPath.add(new Path(pathElements));
		}
		return collectionPath;
	}

	private void search(int s, int f, int count) {
		if (s == f) {
			paths.add(new ArrayList<Integer>());
			for (int i = 0; i < count; i++) {
				paths.get(paths.size()-1).add(buf[i]);
			}
		} else {
			for (int i = 0; i<network.getElements().size(); i++) {
				if ((matrix[s][i] || matrix[i][s]) && !include[i]) {
					buf[count] = i;
					include[i] = true;
					search(i, f, count+1);
					include[i] = false;
					buf[count] = 0;
				}
			}
		}
	}

	/**
	 * создает матрицу смежности для сети, по которой выполняется роутинг
	 * @return двутерный массив целых значений представляющий матрицу смежности
	 */
	private boolean[][] getMatrix() {
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