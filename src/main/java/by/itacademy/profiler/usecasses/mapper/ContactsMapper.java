package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Contacts;
import by.itacademy.profiler.usecasses.dto.ContactsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneCode", ignore = true)
    @Mapping(target = "phoneCode.id", source = "phoneCodeId")
    Contacts contactsDtoToContacts(ContactsDto contactsDto);
}
