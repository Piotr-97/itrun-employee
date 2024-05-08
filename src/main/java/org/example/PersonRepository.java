package org.example;

import org.example.model.Person;
import org.example.model.PersonType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PersonRepository {

    private final Map<String, Person> internalPersons = new HashMap<>();
    private final Map<String, Person> externalsPersons = new HashMap<>();
    private final XmlReader xmlReader;
    private static final String PATH_TO_INTERNALS = System.getProperty("user.dir") + File.separator + "internal";
    private static final String PATH_TO_EXTERNALS = System.getProperty("user.dir") + File.separator + "external";

    public PersonRepository(XmlReader xmlReader) {
        this.xmlReader = xmlReader;
        readExternalsDataFromXml();
        readInternalsDataFromXml();

    }

    public Person searchInInternals(String personId, String firstname, String lastname, String mobile, String email, String pesel) {
        for (Person person : internalPersons.values()) {
            if ((personId == null || personId.equals(person.getPersonId()))
                    && (firstname == null || firstname.equals(person.getFirstname()))
                    && (lastname == null || lastname.equals(person.getLastname()))
                    && (mobile == null || mobile.equals(person.getMobile()))
                    && (email == null || email.equals(person.getEmail()))
                    && (pesel == null || pesel.equals(person.getPesel()))) {
                return person;
            }
        }
        return null;
    }

    public Person searchInExternals(String personId, String firstname, String lastname, String mobile, String email, String pesel) {
        for (Person person : externalsPersons.values()) {
            if ((personId == null || personId.equals(person.getPersonId()))
                    && (firstname == null || firstname.equals(person.getFirstname()))
                    && (lastname == null || lastname.equals(person.getLastname()))
                    && (mobile == null || mobile.equals(person.getMobile()))
                    && (email == null || email.equals(person.getEmail()))
                    && (pesel == null || pesel.equals(person.getPesel()))) {
                return person;
            }
        }
        return null;
    }


    public void saveInternal(Person person) {
        internalPersons.put(person.getPersonId() + ".xml", person);
    }

    public void saveExternal(Person person) {
        externalsPersons.put(person.getPersonId() + ".xml", person);
    }

    public boolean isInternalPersonAlreadyExists(Person person) {
        return internalPersons.containsValue(person);
    }

    public boolean isFileWithInternalPersonIdExits(String personId) {
        return internalPersons.containsKey(personId + ".xml");
    }

    public boolean isFileWithExternalPersonIdExits(String personId) {
        return externalsPersons.containsKey(personId + ".xml");
    }

    public boolean isExternalPersonAlreadyExists(Person person) {
        return externalsPersons.containsValue(person);
    }

    public void removePerson(String idPerson, String type) {
        if (type.equals(PersonType.INTERNAL.getType())) {
            internalPersons.remove(idPerson + ".xml");
        } else {
            externalsPersons.remove(idPerson + ".xml");
        }
    }


    public void readExternalsDataFromXml() {
        Person person = null;
        File directory = new File(PATH_TO_EXTERNALS);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".xml")) {
                        person = xmlReader.readPersonFromFile("external" + File.separator + file.getName());
                        externalsPersons.put(file.getName(), person);
                    }
                }
            }
        }
    }

    public void readInternalsDataFromXml() {
        Person person = null;

        File directory = new File(PATH_TO_INTERNALS);
        System.out.println(PATH_TO_INTERNALS);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".xml")) {
                        person = xmlReader.readPersonFromFile("internal" + File.separator + file.getName());

                        internalPersons.put(file.getName(), person);
                    }
                }
            }
        } else {

            System.out.println("Podana ścieżka nie istnieje lub nie jest katalogiem.");
        }

    }

    public void update(Person newPerson, String type) throws NotFoundPersonException {

        Person oldPerson = type.equals(PersonType.INTERNAL.getType()) ? internalPersons.get(newPerson.getPersonId() + ".xml") : externalsPersons.get(newPerson.getPersonId() + ".xml");
        if (oldPerson == null) {
            throw new NotFoundPersonException("Person with provided person id doesn't exists");
        }
        if (!oldPerson.getFirstname().equals(newPerson.getFirstname())) {
            oldPerson.setFirstname(newPerson.getFirstname());
        }
        if (!oldPerson.getLastname().equals(newPerson.getLastname())) {
            oldPerson.setFirstname(newPerson.getLastname());
        }
        if (!oldPerson.getEmail().equals(newPerson.getEmail())) {
            oldPerson.setEmail(newPerson.getEmail());
        }
        if (!oldPerson.getMobile().equals(newPerson.getMobile())) {
            oldPerson.setMobile(newPerson.getMobile());
        }
        if (!oldPerson.getPesel().equals(newPerson.getPesel())) {
            oldPerson.setPesel(newPerson.getPesel());
        }

        if (type.equals(PersonType.INTERNAL.getType())) {
            internalPersons.put(oldPerson.getPersonId() + ".xml", oldPerson);
        } else if (type.equals(PersonType.EXTERNAL.getType())) {
            externalsPersons.put(oldPerson.getPersonId() + ".xml", oldPerson);
        }
    }

    public Map<String, Person> getInternalPersons() {
        return new HashMap<>(internalPersons);
    }

    public Map<String, Person> getExternalsPersons() {
        return new HashMap<>(externalsPersons);
    }
}
