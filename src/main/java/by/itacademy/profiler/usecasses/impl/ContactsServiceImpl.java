package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Contacts;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.repository.ContactsRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.usecasses.ContactsService;
import by.itacademy.profiler.usecasses.dto.ContactsDto;
import by.itacademy.profiler.usecasses.mapper.ContactsMapper;
import by.itacademy.profiler.usecasses.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactsServiceImpl implements ContactsService {

    private final ContactsRepository contactsRepository;

    private final CurriculumVitaeRepository curriculumVitaeRepository;

    private final ContactsMapper contactsMapper;

    @Override
    @Transactional
    public ContactsDto saveContacts(String uuid, ContactsDto contactsDto) {
        String username = AuthUtil.getUsername();
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.findByUuidAndUsername(uuid, username);
        Contacts contacts = contactsMapper.contactsDtoToContacts(contactsDto);
        contacts.setId(curriculumVitae.getId());
        contactsRepository.save(contacts);
        return contactsDto;
    }
}
