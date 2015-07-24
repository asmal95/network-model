package xml;

import network.Network;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

public interface SavingNetwork {
    void saveNetwork(Network network, String path) throws ParserConfigurationException, FileNotFoundException, TransformerException;
}