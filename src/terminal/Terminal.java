package terminal;

import ip.IPFormatException;
import network.IDNotFoundException;
import network.Network;
import ip.IPNotFoundException;
import network.Packages;

import java.util.ArrayList;
import java.util.Scanner;

public class Terminal {
    private Parameters parameters;

    /**
     * Создает терминал для работы над определенной сетью
     * @param parameters экземпляр класса, который содержит сети и пакеты
     */
    public Terminal(Parameters parameters) {
        if (parameters == null) throw new IllegalArgumentException("parameters is null");
        this.parameters = parameters;
    }

    /**
     * Запускает терминал.
     * Терминал начинаеи слушать консоль и запускает необходимые команды.
     */
    public void start() {
        String mess;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            mess = scanner.nextLine().toLowerCase();    //приводим все к нижнему регистру, пусть все будет регистро не зависимым.
            String[] commandStructure = mess.trim().split("[\\s]+");   //разбивает строку на массив строк, в качестве разделителя - 1 или более пробелов
            if (commandStructure.length == 0) {
                System.out.println("Incorrect command");
                continue;
            }
            String commandName = commandStructure[0];    //получаем имя комманды
            if ("exit".equals(commandName)) //если exit, то завершаем слушать консоль
                break;
            if ("help".equals(commandName)) {
                //тут по хорошему надо бы вызывать хелп для каждого элемента, но я не успел это сделать..
                System.out.println(
                        "elements : print all elements network\n" +
                        "route <name_package> <-all | -cheap | -fast | -short> : returned select paths the package in network\n" +
                        "connect <id1> <id2> : connects two devices\n" +
                        "create <cable | hub | pc | router | switch | firewall> <cost> <delay> [IP] (for ActiveElement) : create new devices\n"+
                        "delete <id> : delete devices\n" +
                        "disconnect <id1> <id2> : disconnect two devices\n" +
                        "package <name> <size> <IP1> <IP2> : create new package\n" +
                        "package : print all packages"
                );
            }
            Command command = null;

            try {
                command = Commands.valueOf(commandName.toUpperCase());  //получаем объект перечисления по имени
                command.run(commandStructure, nw, packages); //запускаем нужную нам команду
            } catch (IPFormatException ex) {
                System.out.println("Incorrect format IP: " + ex.getMessage());
            } catch (IPNotFoundException ex) {
                System.out.println("No devices found to IP: " + ex.getMessage());
            } catch(IDNotFoundException ex) {
                System.out.println("No devices found to ID: " + ex.getMessage());
            } catch (IncorrectFormatCommandException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                System.out.println("terminal.Command is not found...");
                continue;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}