package org.example.utils;

import org.example.exceptions.EmptyFieldPersonException;
import org.example.model.Person;

public class PersonValidator {


    public boolean checkPerson(Person person) throws EmptyFieldPersonException {
        if (person.getPersonId() == null || person.getPersonId().isEmpty() || person.getPersonId().isBlank())
            throw new EmptyFieldPersonException("personId is empty/blank/null");
        if (person.getFirstname() == null || person.getFirstname().isEmpty() || person.getFirstname().isBlank())
            throw new EmptyFieldPersonException("personId is empty/blank/null");
        if (person.getLastname() == null || person.getLastname().isEmpty() || person.getLastname().isBlank())
            throw new EmptyFieldPersonException("personId is empty/blank/null");
        if (person.getMobile() == null || person.getMobile().isEmpty() || person.getMobile().isBlank())
            throw new EmptyFieldPersonException("personId is empty/blank/null");
        if (person.getEmail() == null || person.getEmail().isEmpty() || person.getEmail().isBlank())
            throw new EmptyFieldPersonException("personId is empty/blank/null");
        if (person.getPesel() == null || person.getPesel().isEmpty() || person.getPesel().isBlank())
            throw new EmptyFieldPersonException("personId is empty/blank/null");

        return true;
    }
}
