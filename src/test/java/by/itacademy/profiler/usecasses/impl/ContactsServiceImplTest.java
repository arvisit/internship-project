package by.itacademy.profiler.usecasses.impl;

import by.itacademy.profiler.persistence.model.Contacts;
import by.itacademy.profiler.persistence.repository.ContactsRepository;
import by.itacademy.profiler.persistence.repository.CurriculumVitaeRepository;
import by.itacademy.profiler.usecasses.dto.ContactsDto;
import by.itacademy.profiler.usecasses.dto.ContactsResponseDto;
import by.itacademy.profiler.usecasses.mapper.ContactsMapper;
import by.itacademy.profiler.usecasses.util.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static by.itacademy.profiler.util.ContactTestData.contactsResponseDto;
import static by.itacademy.profiler.util.ContactTestData.createContact;
import static by.itacademy.profiler.util.ContactTestData.createContactDto;
import static by.itacademy.profiler.util.CurriculumVitaeTestData.getValidCurriculumVitae;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactsServiceImplTest {

    @Mock
    private ContactsRepository contactsRepository;

    @Mock
    private CurriculumVitaeRepository curriculumVitaeRepository;

    @Mock
    private ContactsMapper contactsMapper;

    @Mock
    private AuthService authService;

    @InjectMocks
    private ContactsServiceImpl contactsService;

    private static final String DEFAULT_USERNAME = "default_username";
    private static final String CV_UUID = "0a5a28ca-e960-420c-af53-50e6f6e80bf2";

    @BeforeEach
    void setup() {
        when(authService.getUsername()).thenReturn(DEFAULT_USERNAME);
    }

    @Test
    void shouldReturnContactsResponseDtoAndInvokeBusinessMethodsWhenSaveContacts() {
        Contacts contacts = new Contacts();
        Contacts savedContacts = new Contacts();
        ContactsDto contactsDto = createContactDto().build();
        ContactsResponseDto expectedResponseDto = contactsResponseDto().build();

        when(curriculumVitaeRepository.findByUuidAndUsername(CV_UUID, DEFAULT_USERNAME))
                .thenReturn(getValidCurriculumVitae());
        when(contactsMapper.contactsDtoToContacts(contactsDto)).thenReturn(contacts);
        when(contactsRepository.save(contacts)).thenReturn(savedContacts);
        when(contactsMapper.contactsToContactsResponseDto(savedContacts))
                .thenReturn(expectedResponseDto);

        ContactsResponseDto actualResponseDto = contactsService.saveContacts(CV_UUID, contactsDto);

        assertEquals(expectedResponseDto, actualResponseDto);
        verify(authService, times(1)).getUsername();
        verify(curriculumVitaeRepository, times(1)).findByUuidAndUsername(CV_UUID, DEFAULT_USERNAME);
        verify(contactsMapper, times(1)).contactsDtoToContacts(contactsDto);
        verify(contactsRepository, times(1)).save(contacts);
        verify(contactsMapper, times(1)).contactsToContactsResponseDto(savedContacts);
    }


    @Test
    void shouldReturnContactsResponseDtoAndInvokeBusinessMethodsWhenGetContacts() {
        ContactsResponseDto contactsResponseDto = contactsResponseDto().build();
        Contacts contacts = createContact().build();

        when(contactsRepository.findByUuidAndUsername(CV_UUID, DEFAULT_USERNAME))
                .thenReturn(Optional.of(contacts));
        when(contactsMapper.contactsToContactsResponseDto(contacts)).thenReturn(contactsResponseDto);

        ContactsResponseDto result = contactsService.getContacts(CV_UUID);

        assertNotNull(result);
        assertEquals(contactsResponseDto, result);
        verify(authService, times(1)).getUsername();
        verify(contactsRepository, times(1)).findByUuidAndUsername(CV_UUID, DEFAULT_USERNAME);
        verify(contactsMapper, times(1)).contactsToContactsResponseDto(contacts);
    }

    @Test
    void shouldReturnContactsResponseDtoAndInvokeBusinessMethodsWhenUpdateContacts() {
        ContactsDto contactsDto = createContactDto().build();
        Contacts contacts = createContact().build();
        ContactsResponseDto expectedResponseDto = contactsResponseDto().build();

        when(contactsRepository.findByUuidAndUsername(CV_UUID, DEFAULT_USERNAME))
                .thenReturn(Optional.of(contacts));
        when(contactsRepository.save(contacts)).thenReturn(contacts);
        when(contactsMapper.contactsToContactsResponseDto(contacts)).thenReturn(expectedResponseDto);

        ContactsResponseDto actualResult = contactsService.updateContacts(CV_UUID, contactsDto);

        assertNotNull(actualResult);
        assertEquals(expectedResponseDto, actualResult);
        verify(authService, times(1)).getUsername();
        verify(contactsRepository, times(1)).findByUuidAndUsername(CV_UUID, DEFAULT_USERNAME);
        verify(contactsRepository, times(1)).save(contacts);
        verify(contactsMapper, times(1)).contactsToContactsResponseDto(contacts);
    }
}