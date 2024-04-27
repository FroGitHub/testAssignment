Requirements:
1. It has the following fields:
    1.1. Email (required). Add validation against email pattern
    1.2. First name (required)
    1.3. Last name (required)
    1.4. Birth date (required). Value must be earlier than current date
    1.5. Address (optional)
    1.6. Phone number (optional)
2. It has the following functionality:
    2.1. Create user. It allows to register users who are more than [18] years old. The value [18] should be taken from properties file.
    2.2. Update one/some user fields
    2.3. Update all user fields
    2.4. Delete user
    2.5. Search for users by birth date range. Add the validation which checks that “From” is less than “To”.  Should return a list of objects       
3. Code is covered by unit tests using Spring 
4. Code has error handling for REST
5. API responses are in JSON format

6. Use of database is not necessary. The data persistence layer is not required.(I used)
7. Any version of Spring Boot. Java version of your choice(Spring 3.2.5, Java 17)
8. You can use Spring Initializer utility to create the project: Spring Initializr


Tests I made:
    createUser
    unsuccessfulCreate - less age
    unsuccessfulCreate - wrong email
    unsuccessfulCreate - null or empty fields
    
    updateUser - one field
    updateUser - some fields
    updateUser - all fields
    
    unsuccessfulUpdate - update to null
    unsuccessfulUpdate - less age
    unsuccessfulUpdate - wrong email
    
    searchUsers
    searchNoUsers
