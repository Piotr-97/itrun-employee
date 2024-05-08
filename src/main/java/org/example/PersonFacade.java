package org.example;

import org.example.exceptions.AlreadyExistsPersonException;
import org.example.exceptions.EmptyFieldPersonException;
import org.example.exceptions.NotFoundPersonException;
import org.example.model.Person;
import org.example.model.PersonType;
import org.example.repository.PersonRepository;
import org.example.utils.FileDeleter;
import org.example.utils.PersonValidator;
import org.example.utils.XmlWriter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PersonFacade {
    public static final String XML_FILE_EXTENSION = ".xml";
    private final XmlWriter xmlWriter;

    private final PersonValidator personValidator;
    private final FileDeleter fileDeleter;
    private final PersonRepository personRepository;
    private static final String PATH_TO_INTERNALS = System.getProperty("user.dir") + File.separator + "internal";
    private static final String PATH_TO_EXTERNALS = System.getProperty("user.dir") + File.separator + "external";

    public PersonFacade(XmlWriter xmlWriter, PersonValidator personValidator, FileDeleter fileDeleter, PersonRepository personRepository) {
        this.xmlWriter = xmlWriter;
        this.personValidator = personValidator;
        this.fileDeleter = fileDeleter;
        this.personRepository = personRepository;
    }

    public void createNewPerson(Person person, String type) throws AlreadyExistsPersonException, EmptyFieldPersonException {
        personValidator.checkPerson(person);
        if (type.equals(PersonType.INTERNAL.getType())) {
            createNewInternalPerson(person, type);

        } else if (type.equals(PersonType.EXTERNAL.getType())) {
            createNewExternalPerson(person, type);
        }


    }

    private void createNewInternalPerson(Person person, String type) throws AlreadyExistsPersonException {
        if (personRepository.isInternalPersonAlreadyExists(person)) {
            throw new AlreadyExistsPersonException("Person already exists in database");
        }

        if (personRepository.isFileWithInternalPersonIdExits(person.getPersonId())) {
            throw new AlreadyExistsPersonException("File with personalId already exists");
        }
        personRepository.saveInternal(person);
        try {

            xmlWriter.writePersonToFile(person, type);
        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private void createNewExternalPerson(Person person, String type) throws AlreadyExistsPersonException {
        if (personRepository.isExternalPersonAlreadyExists(person)) {
            throw new AlreadyExistsPersonException("Person already exists in database");
        }

        if (personRepository.isFileWithExternalPersonIdExits(person.getPersonId())) {
            throw new AlreadyExistsPersonException("File with personalId already exists");
        }
        personRepository.saveExternal(person);
        try {

            xmlWriter.writePersonToFile(person, type);
        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public Person find(String type, String personId, String firstname, String lastname, String mobile, String email, String pesel) throws NotFoundPersonException {
        Person person = null;
        if (type == null) {
            person = personRepository.searchInInternals(personId, firstname, lastname, mobile, email, pesel);
            if (person != null) {
                return person;
            }
           person = personRepository.searchInExternals(personId, firstname, lastname, mobile, email, pesel);

        } else {
            person = type.equals(PersonType.INTERNAL.getType()) ?
                    personRepository.searchInInternals(personId, firstname, lastname, mobile, email, pesel) :
                    personRepository.searchInExternals(personId, firstname, lastname, mobile, email, pesel);
        }
        if (person == null) {
            throw new NotFoundPersonException("Person not found");
        }

        return person;
    }

    public void updatePerson(Person person, String type) {
        try {
            personRepository.update(person, type);
            xmlWriter.modifyPersonXml(person, type);

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            throw new RuntimeException(e);
        } catch (NotFoundPersonException e) {
            System.out.println(e.getMessage());
        }
    }


    public void removePerson(String idPerson, String type) throws NotFoundPersonException {
        if (personRepository.isFileWithInternalPersonIdExits(idPerson) || personRepository.isFileWithExternalPersonIdExits(idPerson)) {
            personRepository.removePerson(idPerson, type);
            String fileName = idPerson + XML_FILE_EXTENSION;
            String pathToFile = type.equals(PersonType.INTERNAL.getType()) ? PATH_TO_INTERNALS + File.separator + fileName :
                    (PATH_TO_EXTERNALS + File.separator + idPerson + XML_FILE_EXTENSION);

            fileDeleter.deleteFile(pathToFile);
            return;
        }
        throw new NotFoundPersonException("Couldn't find Person to delete");
    }

    public Map<String, Person> getAllInternals(){
       return personRepository.getInternalPersons();
    }


    public Map<String, Person> getAllExternals(){
        return personRepository.getExternalsPersons();
    }
}
