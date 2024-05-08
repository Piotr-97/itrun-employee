package org.example;


import org.example.model.Person;
import org.example.model.PersonType;

import java.io.File;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {


        shouldRemoveInternalPersonFromDatabase();
        shouldNotRemovePersonFromDatabasePersonIdNotExists();
        shouldAddNewInternalEmployee();
        shouldAddNewExternalEmployee();
        shouldNotAddNewEmployeeBecauseIdPersonAlreadyExists();
        shouldFindPersonByPersonId();
        shouldFindPersonByFirstName();
        shouldFindPersonByLastName();
        shouldFindPersonByMobile();
        shouldFindPersonByEmail();
        shouldFindPersonByPesel();
        shouldModifyPerson();
        shouldNotModifyPerson();
    }

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
        //when
        try {
            personFacade.createNewPerson(person, PersonType.INTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldAddNewInternalEmployee : test went wrong " + e.getMessage());
        }

        if (!personFacade.getAllInternals().containsKey(person.getPersonId() + ".xml")) {
            throw new AssertionError("Person is not added to Database");
        }
        File file = new File(System.getProperty("user.dir") + File.separator + "internal" + File.separator + person.getPersonId() + ".xml");
        if (!file.exists()) {
            throw new AssertionError("Person file is not in the folder");
        }

        //clearing
        try {
            personFacade.removePerson("9999", PersonType.INTERNAL.getType());
        } catch (NotFoundPersonException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shouldAddNewExternalEmployee() {
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
        //when
        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldAddNewInternalEmployee : test went wrong " + e.getMessage());
        }

        if (!personFacade.getAllExternals().containsKey(person.getPersonId() + ".xml")) {
            throw new AssertionError("Person is not added to Database");
        }
        File file = new File(System.getProperty("user.dir") + File.separator + "external" + File.separator + person.getPersonId() + ".xml");
        if (!file.exists()) {
            throw new AssertionError("Person file is not in the folder");
        }

        //clearing
        try {
            personFacade.removePerson("9999", PersonType.EXTERNAL.getType());
        } catch (NotFoundPersonException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shouldNotAddNewEmployeeBecauseIdPersonAlreadyExists() {
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
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldAddNewInternalEmployee : test went wrong " + e.getMessage());
        }
        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldNotAddNewEmployeeBecauseIdPersonAlreadyExists : Test went correctly");
        }
        //cleaning
        try {
            personFacade.removePerson("9999", PersonType.EXTERNAL.getType());
        } catch (NotFoundPersonException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shouldNotAddNewEmployeeWithoutEnoughData() {

    }

    public static void shouldFindPersonByPersonId() {
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
        Person person1 = null;

        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldAddNewInternalEmployee : test went wrong " + e.getMessage());
        }

        try {
            person1 = personFacade.find(null, person.getPersonId(), null, null, null, null, null);
        } catch (NotFoundPersonException e) {
            throw new AssertionError(e.getMessage());
        }
        if (!person.equals(person1)) {
            throw new AssertionError("shouldFindPersonByPersonId : method find()  didn't work correctly");
        }
        //cleaning
        try {
            personFacade.removePerson("9999", PersonType.EXTERNAL.getType());
        } catch (NotFoundPersonException e) {
            throw new RuntimeException(e);
        }

    }

    public static void shouldFindPersonByFirstName() {
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
        Person person1 = null;

        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldFindPersonByFirstName : test went wrong " + e.getMessage());
        }

        try {
            person1 = personFacade.find(null, null, person.getFirstname(), null, null, null, null);
        } catch (NotFoundPersonException e) {
            throw new AssertionError(e.getMessage());
        }
        if (!person.getPersonId().equals(person1.getPersonId())) {
            throw new AssertionError("shouldFindPersonByFirstName : method find()  didn't work correctly");
        }
        //cleaning
        clearExternal(personFacade);


    }

    private static void clearExternal(PersonFacade personFacade) {
        try {
            personFacade.removePerson("9999", PersonType.EXTERNAL.getType());
        } catch (NotFoundPersonException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shouldFindPersonByLastName() {
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
        Person person1 = null;

        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldFindPersonByFirstName : test went wrong " + e.getMessage());
        }

        try {
            person1 = personFacade.find(null, null, null, person.getLastname(), null, null, null);
        } catch (NotFoundPersonException e) {
            throw new AssertionError(e.getMessage());
        }
        if (!person.getPersonId().equals(person1.getPersonId())) {
            throw new AssertionError("shouldFindPersonByLastName : method find()  didn't work correctly");
        }
        //cleaning
        clearExternal(personFacade);


    }

    public static void shouldFindPersonByMobile() {
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
        Person person1 = null;

        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldFindPersonByFirstName : test went wrong " + e.getMessage());
        }

        try {
            person1 = personFacade.find(null, null, null, null, person.getMobile(), null, null);
        } catch (NotFoundPersonException e) {
            throw new AssertionError(e.getMessage());
        }
        if (!person.getPersonId().equals(person1.getPersonId())) {
            throw new AssertionError("shouldFindPersonByMobile : method find()  didn't work correctly");
        }
        //cleaning
        clearExternal(personFacade);
    }

    public static void shouldFindPersonByEmail() {
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
        Person person1 = null;

        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldFindPersonByFirstName : test went wrong " + e.getMessage());
        }

        try {
            person1 = personFacade.find(null, null, null, null, null, person.getEmail(), null);
        } catch (NotFoundPersonException e) {
            throw new AssertionError(e.getMessage());
        }
        if (!person.getPersonId().equals(person1.getPersonId())) {
            throw new AssertionError("shouldFindPersonByEmail : method find()  didn't work correctly");
        }
        //cleaning
        clearExternal(personFacade);
    }

    public static void shouldFindPersonByPesel() {
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
        Person person1 = null;

        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldFindPersonByFirstName : test went wrong " + e.getMessage());
        }

        try {
            person1 = personFacade.find(null, null, null, null, null, null, person.getPesel());
        } catch (NotFoundPersonException e) {
            throw new AssertionError(e.getMessage());
        }
        if (!person.getPersonId().equals(person1.getPersonId())) {
            throw new AssertionError("shouldFindPersonByEmail : method find()  didn't work correctly");
        }
        //cleaning
        clearExternal(personFacade);
    }


    public static void shouldFindPersonByFirstnameAndLastname() {
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
        Person person1 = null;
        //when
        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldFindPersonByFirstName : test went wrong " + e.getMessage());
        }
        //then
        try {
            person1 = personFacade.find(null, null, person.getFirstname(), person.getLastname(), null, null, null);
        } catch (NotFoundPersonException e) {
            throw new AssertionError(e.getMessage());
        }
        if (!person.getPersonId().equals(person1.getPersonId())) {
            throw new AssertionError("shouldFindPersonByEmail : method find()  didn't work correctly");
        }
        //cleaning
        clearExternal(personFacade);
    }

    public static void shouldModifyPerson() {
        //given
        Person modPerson = null;
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
        Person newperson = new Person.Builder()
                .personId("9999")
                .firstname("Harry")
                .lastname("Potter")
                .mobile("1231234")
                .email("Harry.Potter@mail.com")
                .pesel("11111111111")
                .build();
        //when
        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldModifyPerson : test went wrong " + e.getMessage());
        }
        //then
        personFacade.updatePerson(newperson,PersonType.EXTERNAL.getType());

        try {
             modPerson = personFacade.find(null, "9999", null, null, null, null, null);
        } catch (NotFoundPersonException e) {
            System.out.println("shouldModifyPerson : test went wrong " + e.getMessage());
        }

        if(!person.getFirstname().equals(Objects.requireNonNull(modPerson).getFirstname())){
            throw new AssertionError("Person hasn't modified");
        }

    }

    public static void shouldNotModifyPerson() {
        //given
        Person modPerson = null;
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
        Person newperson = new Person.Builder()
                .personId("9998")
                .firstname("Harry")
                .lastname("Potter")
                .mobile("1231234")
                .email("Harry.Potter@mail.com")
                .pesel("11111111111")
                .build();
        //when
        try {
            personFacade.createNewPerson(person, PersonType.EXTERNAL.getType());
        } catch (AlreadyExistsPersonException e) {
            System.out.println("shouldModifyPerson : test went wrong " + e.getMessage());
        }
        //then
        personFacade.updatePerson(newperson,PersonType.EXTERNAL.getType());

        try {
            modPerson = personFacade.find(null, "9999", null, null, null, null, null);
        } catch (NotFoundPersonException e) {
            System.out.println("shouldModifyPerson : test went wrong " + e.getMessage());
        }

        if(person.getFirstname().equals(Objects.requireNonNull(modPerson).getFirstname())){
            throw new AssertionError("Throw if person was modified");
        }
        clearExternal(personFacade);

    }


}