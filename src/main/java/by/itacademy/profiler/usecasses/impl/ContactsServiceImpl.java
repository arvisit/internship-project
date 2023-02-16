package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.api.exception.ContactsNotFoundException;
import by.itacademy.profiler.persistence.model.Contacts;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.persistence.repository.ContactsRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.persistence.repository.PhoneCodeRepository;
import by.itacademy.profiler.usecasses.ContactsService;
import by.itacademy.profiler.usecasses.dto.ContactsDto;
import by.itacademy.profiler.usecasses.dto.ContactsResponseDto;
import by.itacademy.profiler.usecasses.mapper.ContactsMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactsServiceImpl implements ContactsService {

    private final ContactsRepository contactsRepository;

    private final CurriculumVitaeRepository curriculumVitaeRepository;

    private final PhoneCodeRepository phoneCodeRepository;

    private final ContactsMapper contactsMapper;

    private final AuthService authService;

    @Override
    @Transactional
    public ContactsResponseDto saveContacts(String uuid, ContactsDto contactsDto) {
        String username = authService.getUsername();
        CurriculumVitae curriculumVitae = curriculumVitaeRepository.findByUuidAndUsername(uuid, username);
        Contacts contacts = contactsMapper.contactsDtoToContacts(contactsDto);
        contacts.setId(curriculumVitae.getId());
        return contactsMapper.contactsToContactsResponseDto(contactsRepository.save(contacts));
    }

    @Override
    @Transactional
    public ContactsResponseDto getContacts(String uuid) {
        String username = authService.getUsername();
        Contacts contacts = contactsRepository.findByUuidAndUsername(uuid, username).orElseThrow(() ->
                new ContactsNotFoundException(String.format("Contacts not available for CV UUID: %s of user %s", uuid, username)));
        return contactsMapper.contactsToContactsResponseDto(contacts);
    }

    @Override
    @Transactional
    public ContactsResponseDto updateContacts(String uuid, ContactsDto contactsDto) {
        String username = authService.getUsername();
        Contacts contacts = contactsRepository.findByUuidAndUsername(uuid, username).orElseThrow(() ->
                new ContactsNotFoundException(String.format("Contacts not available for CV UUID: %s of user %s", uuid, username)));
        updateContacts(contactsDto, contacts);
        Contacts updateContacts = contactsRepository.save(contacts);
        return contactsMapper.contactsToContactsResponseDto(updateContacts);
    }

    private void updateContacts(ContactsDto contactsDto, Contacts contacts) {
        if (!contacts.getPhoneNumber().equals(contactsDto.phoneNumber())) {
            contacts.setPhoneNumber(contactsDto.phoneNumber());
        }
        if (!contacts.getEmail().equals(contactsDto.email())) {
            contacts.setEmail(contactsDto.email());
        }
        if (!contacts.getLinkedin().equals(contactsDto.linkedin())) {
            contacts.setLinkedin(contactsDto.linkedin());
        }
        if (!contacts.getPhoneCode().getId().equals(contactsDto.phoneCodeId())) {
            phoneCodeRepository.findById(contactsDto.phoneCodeId())
                    .ifPresent(contacts::setPhoneCode);
        }
        contacts.setSkype(contactsDto.skype());
        contacts.setPortfolio(contactsDto.portfolio());
    }
}
