import ip.IP;
import network.Packages;
import network.elements.*;
import network.Network;
import routing.Path;
import routing.Routing;
import terminal.Command;
import terminal.Commands;
import terminal.IncorrectFormatCommandException;
import terminal.Terminal;
import xml.NetworkAsXML;

import java.util.ArrayList;
import java.util.List;

public class TestStart {

    public static void main(String[] args) {
        Network nw = null;
        NetworkAsXML a = new NetworkAsXML("network.xml");  //создаем класс, который будет предоставлять работу с сетью чез XML
        try {
            nw = a.getNetwork();    //получаем сеть
        } catch (Exception ex) {
            ex.printStackTrace();
            return;                     //пока что такая затычка
        }

        /*try {
            a.saveNetwork(nw); //сохраняем сеть..
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        Terminal ter = new Terminal(nw, new Packages());
        /*//определим комманды для консоли
        //elements
        ter.addCommand(Commands.ELEMENTS); //выводит все элементы сети и информацию о них
        //route <имя_пакета> <-all | -cheap | -fast | -short>
        ter.addCommand(Commands.ROUTE); //все доступные пути для пакета
        //connect <id1> <id2>
        ter.addCommand(Commands.CONNECT); //соединяет два устройства сети
        //create <cable | hub | pc | router | switch | firewall> <cost> <delay> [IP] (for ActiveElement)
        ter.addCommand(Commands.CREATE); //создает новое устройство сети
        //delete <id>
        ter.addCommand(Commands.DELETE); //удаляет элемент из сети
        //disconnect <id1> <id2>
        ter.addCommand(Commands.DISCONNECT); //разрывает соединение между двумя устройствами
        //package <name> <size> <IP1> <IP2>
        ter.addCommand(Commands.PACKAGE); //создает пакет для отправки между элементами сети
        //package
        ter.addCommand(Commands.PACKAGES); //выводит описание всех пакетов*/

        //запустим терминал
        ter.start();
    }
}
