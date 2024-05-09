package org.example.utils;

import org.example.model.Person;
import org.example.model.PersonDto;

public class PersonMapper {


    public Person mapDtoToPerson(PersonDto personDto) {
        return new Person.Builder()
                .personId(personDto.personId())
                .firstname(personDto.firstname())
                .lastname(personDto.lastname())
                .mobile(personDto.mobile())
                .email(personDto.email())
                .pesel(personDto.pesel())
                .build();
    }

    public PersonDto mapPersonToDto(Person person, String type) {
        return new PersonDto(type, person.getPersonId(), person.getFirstname(), person.getLastname(), person.getMobile(), person.getEmail(), person.getPesel());
    }
}
