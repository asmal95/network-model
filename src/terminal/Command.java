package terminal;

import network.Network;
import network.Packages;

public interface Command {
    /**
     * Возвращает имя команды
     * @return имя команды
     */
    String getName();

    /**
     * Запускает на выполнение команду.
     * @param args аргументы необходимые для выполнение команды. 1 аргумент - имя команды. Обычно игнорируется
     * @param parameters содержит сети и пакеты
     * @throws IncorrectFormatCommandException Выбрасывается, когда команду не возможно выполнить.
     */
    void run(String[] args, Parameters parameters) throws IncorrectFormatCommandException;
}
