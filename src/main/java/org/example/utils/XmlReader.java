package org.example.utils;

import org.example.model.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlReader {

    public Person readPersonFromFile(String fileName){
        try {
            String path = System.getProperty("user.dir") + File.separator;
            File file = new File(path+fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("person");

            Node node = nodeList.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String personId =  element.getElementsByTagName("personId").item(0).getTextContent();
                String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                String mobile = element.getElementsByTagName("mobile").item(0).getTextContent();
                String email = element.getElementsByTagName("email").item(0).getTextContent();
                String pesel = element.getElementsByTagName("pesel").item(0).getTextContent();

            return new Person.Builder()
                        .personId(personId)
                        .firstname(firstName)
                        .lastname(lastName)
                        .mobile(mobile)
                        .email(email)
                        .pesel(pesel).build();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
