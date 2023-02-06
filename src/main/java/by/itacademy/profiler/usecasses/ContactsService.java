package by.itacademy.profiler.usecasses;


import by.itacademy.profiler.usecasses.dto.ContactsDto;

public interface ContactsService {

    ContactsDto saveContacts(String uuid, ContactsDto contactsDto);

    ContactsDto getContacts(String uuid);

    ContactsDto updateContacts(String uuid, ContactsDto contactsDto);
}
