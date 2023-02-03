package by.itacademy.profiler.usecasses;


import by.itacademy.profiler.usecasses.dto.ContactsDto;

public interface ContactsService {

    ContactsDto saveContacts(String uuid, ContactsDto contactsDto);
}
