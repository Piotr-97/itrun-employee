package org.example;


import org.example.model.Person;
import org.example.model.PersonType;

import java.util.Map;

public class Main {
    public static void main(String[] args) {


        shouldRemoveInternalPersonFromDatabase();
        shouldNotRemovePersonFromDatabasePersonIdNotExists();

    }

    //Delete
    public static void shouldRemoveInternalPersonFromDatabase() {
        //given
        XmlReader xmlReader = new XmlReader();
        PersonFacade personFacade = new PersonFacade(new XmlWriter(), new FileDeleter(), new PersonRepository(xmlReader));
        Person person = new Person.Builder()
                .personId("9999")
                .firstname("Wlodzimierz")
                .lastname("NowyTEster")
                .mobile("1231234")
                .email("Wlodzimierz.test@mail.com")
                .pesel("11111111111")
                .build();
        try {
            personFacade.createNewPerson(person, PersonType.INTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            e.printStackTrace();
        }
        if (!personFacade.getAllInternals().containsKey(person.getPersonId() + ".xml")) {
            throw new AssertionError("shouldRemoveInternalPersonFromDatabase ERROR : Person is not in the database");
        }
        //when
        try {
            personFacade.removePerson("9999", PersonType.INTERNAL.getType());
        } catch (NotFoundPersonException e) {
            System.out.println("Test failed");
        }
        //then
        Map<String, Person> allInternals = personFacade.getAllInternals();
        if (allInternals.containsKey(person.getPersonId() + ".xml")) {
            throw new AssertionError("Person should be deleted");
        }


    }

    public static void shouldNotRemovePersonFromDatabasePersonIdNotExists() {
        //given
        XmlReader xmlReader = new XmlReader();
        PersonFacade personFacade = new PersonFacade(new XmlWriter(), new FileDeleter(), new PersonRepository(xmlReader));
        Person person = new Person.Builder()
                .personId("9999")
                .firstname("Wlodzimierz")
                .lastname("NowyTEster")
                .mobile("1231234")
                .email("Wlodzimierz.test@mail.com")
                .pesel("11111111111")
                .build();
        if (personFacade.getAllInternals().containsKey(person.getPersonId() + ".xml")) {
            throw new AssertionError("shouldRemoveInternalPersonFromDatabase ERROR : Person is in the database");
        }
        //when && then
        try {
            personFacade.removePerson("9999", PersonType.INTERNAL.getType());
        } catch (NotFoundPersonException e) {
            System.out.println("shouldNotRemovePersonFromDatabasePersonIdNotExists : Test went correctly");
        }


    }

    //Add
    public static void shouldAddNewInternalEmployee() {
        XmlReader xmlReader = new XmlReader();
        PersonFacade personFacade = new PersonFacade(new XmlWriter(), new FileDeleter(), new PersonRepository(xmlReader));
        Person person = new Person.Builder()
                .personId("9999")
                .firstname("Wlodzimierz")
                .lastname("NowyTEster")
                .mobile("1231234")
                .email("Wlodzimierz.test@mail.com")
                .pesel("11111111111")
                .build();


    }

    public void shouldAddNewExternalEmployee() {

    }

    public void shouldNotAddNewEmployeeBecauseIdPersonAlreadyExists() {

    }

    public void shouldNotAddNewEmployeeWithoutEnoughData() {

    }

    public void shouldFindPersonByPersonId() {
    }

    public void shouldFindPersonByFirstName() {
    }

    public void shouldFindPersonByLastName() {
    }

    public void shouldFindPersonByMobile() {
    }

    public void shouldFindPersonByEmail() {
    }

    public void shouldFindPersonByPesel() {
    }

    public void shouldFindPersonByFirstnameAndLastname() {
    }

    public void shouldModifyPerson() {
    }

    public void shouldNotModifyPerson() {
    }


}