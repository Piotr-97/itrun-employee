# READ ME 
### Person searcher

A program supporting the use of a database with XML files.
The application's code allows for reading data of person objects from the database, their saving,
modification, and finding based on specific fields.

### Project assumptions

The database only accepts fully populated objects of the 'Person' class.

### Tests

The tests are located in the main class.

### Available methods
Through the facade, several methods are available.

| Name                                                                                                                    | Return type | short description                                               |
|-------------------------------------------------------------------------------------------------------------------------|-------------|-----------------------------------------------------------------|
| createNewPerson(PersonDto personDto)                                                                                    | void        | The method adds a new person object to the database.            |
| Person find(String type, String personId, String firstname, String lastname, String mobile, String email, String pesel) | Person      | It finds a person object when providing one or more arguments.  |
| updatePerson(PersonDto personDto)                                                                                       | void        | It modifies the specified object                                |
| removePerson(String idPerson, String type)                                                                              | void        | It deletes the object with the given personId from the database |
      
### Next steps
    Switching from manually creating file paths to using FilePathBuilder.
    Standardization of methods for internal and external persons to unify the code and reduce duplication
    



