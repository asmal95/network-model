package terminal;

import ip.IP;
import network.Network;
import network.Package;
import network.Packages;
import network.elements.*;
import routing.Path;
import routing.Routing;

import java.util.ArrayList;
import java.util.List;

public enum Commands implements Command {
    ROUTE("route") {
        //route package1 -chip
        @Override
        public void run(String[] args, Network network, Packages packages) throws IncorrectFormatCommandException {

            if (args.length < 3) throw new IncorrectFormatCommandException("command 'route' should a minimum of 3 words");
            Package pck = packages.getPackageByName(args[1]);
            IP ip1, ip2;
            ip1 = pck.getSenderIP();
            ip2 = pck.getRecipientIP();
            Routing r = new Routing(network);
            List<Path> paths;
            switch (args[2]) {
                case "-all":
                    paths = r.getRoute(ip1, ip2);
                    break;
                case "-cheap":
                    paths = r.getCheapRoute(ip1, ip2);
                    break;
                case "-fast":
                    paths = r.getLeastDelayRoute(ip1, ip2);
                    break;
                case "-short":
                    paths = r.getShortRoute(ip1, ip2);
                    break;
                default:
                    throw new IncorrectFormatCommandException("Incorrect key: " + args[2]);

            }
            for (Path path : paths) {
                System.out.println("----\nPath:: cost: " + path.getCost(pck) +", time delay: " + path.getTimeDelay(pck) + ", lenght: " + path.getLength());
                for (PathElement el : path.getElements()) {
                    System.out.println("  " + el.getInfo());
                }
            }
            System.out.println("===================");
        }
    },
    ELEMENTS("elements") {
        @Override
        public void run(String[] args, Network network, Packages packages) throws IncorrectFormatCommandException {
            List<PathElement> elements = network.getElements();
            for (PathElement el : elements) {
                System.out.println(el.getInfo() + " connect: " + el.getConnectInfo());
            }
        }
    },
    CONNECT("connect") {
        @Override
        public void run(String[] args, Network network, Packages packages) throws IncorrectFormatCommandException {
            //connect 1 4
            if (args.length < 3) throw new IncorrectFormatCommandException("command 'connect' should a minimum of 3 words");

            int id1 = -1;
            int id2 = -1;

            //красива и легальна ли вообще такая проверка??
            try {
                id1 = Integer.parseInt(args[1]);
                id2 = Integer.parseInt(args[2]);
            } catch (NumberFormatException ex) {  }

            if(id1 <= 0) throw new IncorrectFormatCommandException("Incorrect ID: " + args[1]);
            if(id2 <= 0) throw new IncorrectFormatCommandException("Incorrect ID: " + args[2]);

            network.connect(id1, id2);
        }
    },
    CREATE("create") {
        //create pc 12 32 33.22.44.22
        @Override
        public void run(String[] args, Network network, Packages packages) throws IncorrectFormatCommandException {
            if (args.length < 4) throw new IncorrectFormatCommandException("command 'create' should a minimum of 4 words");
            double cost = -1;
            double delay = -1;

            try {
                cost = Double.parseDouble(args[2]);
                delay = Double.parseDouble(args[3]);
            } catch (NumberFormatException ex) {  }


            if (cost <= 0) throw new IncorrectFormatCommandException("Incorrect argument: " + args[2]);
            if (delay <= 0) throw new IncorrectFormatCommandException("Incorrect argument: " + args[3]);

            String ip = null;
            if (args.length == 5)
                ip = args[4];

            switch (args[1]) {
                case "pc":
                    network.addElement(new PC(new IP(ip), cost, delay));
                    break;
                case "switch":
                    network.addElement(new Switch(new IP(ip), cost, delay));
                    break;
                case "router":
                    network.addElement(new Router(new IP(ip), cost, delay));
                    break;
                case "firewall":
                    network.addElement(new Router(new IP(ip), cost, delay));
                    break;
                case "cable":
                    network.addElement(new Cable(cost, delay));
                    break;
                case "hub":
                    network.addElement(new Hub(cost, delay));
                    break;
                default:
                    throw new IncorrectFormatCommandException("element " + args[1] + " not defined");
            }
        }
    },
    DELETE("delete") {
        @Override
        public void run(String[] args, Network network, Packages packages) throws IncorrectFormatCommandException {
            //delete 5
            if(args.length != 2) throw new IncorrectFormatCommandException("command 'delete' should of 2 words");

            int id = -1;
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {  }

            if (id <= 0) throw new IncorrectFormatCommandException("Incorrect id: " + args[1]);

            network.delete(id);
        }
    },
    DISCONNECT("disconnect") {
        @Override
        public void run(String[] args, Network network, Packages packages) throws IncorrectFormatCommandException {
            //disconnect 5 2
            if (args.length < 3) throw new IncorrectFormatCommandException("command 'disconnect' should a minimum of 3 words");

            int id1 = -1;
            int id2 = -1;

            //красива и легальна ли вообще такая проверка??
            try {
                id1 = Integer.parseInt(args[1]);
                id2 = Integer.parseInt(args[2]);
            } catch (NumberFormatException ex) {  }

            if(id1 <= 0) throw new IncorrectFormatCommandException("Incorrect ID: " + args[1]);
            if(id2 <= 0) throw new IncorrectFormatCommandException("Incorrect ID: " + args[2]);

            network.disconnect(id1, id2);
        }
    },
    PACKAGE("package") {
        @Override
        public void run(String[] args, Network network, Packages packages) throws IncorrectFormatCommandException {
            //package qwe 234 192.168.0.10 234 192.168.0.13
            if (args.length < 5) throw new IncorrectFormatCommandException("command 'disconnect' should of 5 words");
            String name = args[1];
            double size = Double.parseDouble(args[2]);
            IP ip1 = new IP(args[3]);
            IP ip2 = new IP(args[4]);
            packages.addPackage(new Package(name, size, ip1, ip2));
        }
    },
    PACKAGES("packages") {
        @Override
        public void run(String[] args, Network network, Packages packages) throws IncorrectFormatCommandException {
            for (Package p : packages.getPackges()) {
                System.out.println(p.getInfo());
            }
        }
    };
    /*COMMAND("command") {
        @Override
        public void run(String[] args, Network network, Packages packages) throws IncorrectFormatCommandException {

        }
    };*/

    String name;
    Commands(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    @Override
    public abstract void run(String[] args, Network network, Packages packages) throws IncorrectFormatCommandException;
}