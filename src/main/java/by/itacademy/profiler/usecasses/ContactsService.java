package by.itacademy.profiler.usecasses;


import by.itacademy.profiler.usecasses.dto.ContactsDto;
import by.itacademy.profiler.usecasses.dto.ContactsResponseDto;

public interface ContactsService {

    ContactsResponseDto saveContacts(String uuid, ContactsDto contactsDto);

    ContactsResponseDto getContacts(String uuid);

    ContactsResponseDto updateContacts(String uuid, ContactsDto contactsDto);
}
