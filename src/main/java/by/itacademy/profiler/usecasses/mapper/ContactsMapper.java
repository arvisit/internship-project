package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Contacts;
import by.itacademy.profiler.usecasses.dto.ContactsDto;
import by.itacademy.profiler.usecasses.dto.ContactsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneCode", ignore = true)
    @Mapping(target = "phoneCode.id", source = "phoneCodeId")
    Contacts contactsDtoToContacts(ContactsDto contactsDto);

    @Mapping(target = "phoneCodeId", source = "phoneCode.id")
    @Mapping(target = "phoneCode", source = "phoneCode.code")
    ContactsResponseDto contactsToContactsResponseDto(Contacts contacts);
}
