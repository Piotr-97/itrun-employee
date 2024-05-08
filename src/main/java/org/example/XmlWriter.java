package org.example;

import org.example.model.Person;
import org.example.model.PersonType;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XmlWriter {

    private static final String pathToInternals = System.getProperty("user.dir") + File.separator + "internal";
    private static final String pathToExternals = System.getProperty("user.dir") + File.separator + "external";

    public void writePersonToFile(Person person, String type) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();

        Element personElement = doc.createElement("person");
        doc.appendChild(personElement);

        appendChildElement(doc, personElement, "personId", person.getPersonId());
        appendChildElement(doc, personElement, "firstName", person.getFirstname());
        appendChildElement(doc, personElement, "lastName", person.getLastname());
        appendChildElement(doc, personElement, "mobile", person.getMobile());
        appendChildElement(doc, personElement, "email", person.getEmail());
        appendChildElement(doc, personElement, "pesel", person.getPesel());

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = type.equals(PersonType.INTERNAL.getType()) ?
                new StreamResult(new File(pathToInternals + File.separator + person.getPersonId() + ".xml")) :
                new StreamResult(new File(pathToExternals + File.separator + person.getPersonId() + ".xml"));


        transformer.transform(source, result);

    }

    private void appendChildElement(Document doc, Element parentElement, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        Text textNode = doc.createTextNode(textContent);
        element.appendChild(textNode);
        parentElement.appendChild(element);
    }


    public void modifyPersonXml(Person person, String type) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String filePath = type.equals(PersonType.INTERNAL.getType()) ?
                (pathToInternals + File.separator + person.getPersonId() + ".xml") :
                (pathToExternals + File.separator + person.getPersonId() + ".xml");

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.parse(filePath);

        Element personElement = doc.getDocumentElement();

        NodeList personNodes = personElement.getChildNodes();

        updatePerson(person, personNodes);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }

    private static void updatePerson(Person newPerson, NodeList personNodes) {
        for (int i = 0; i < personNodes.getLength(); i++) {
            Node node = personNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String nodeName = element.getNodeName();
                switch (nodeName) {
                    case "firstName" -> element.setTextContent(newPerson.getFirstname());
                    case "lastName" -> element.setTextContent(newPerson.getLastname());
                    case "mobile" -> element.setTextContent(newPerson.getMobile());
                    case "email" -> element.setTextContent(newPerson.getEmail());
                    case "pesel" -> element.setTextContent(newPerson.getPesel());
                }
            }
        }
    }


}
