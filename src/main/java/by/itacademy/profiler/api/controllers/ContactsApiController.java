package by.itacademy.profiler.api.controllers;

import by.itacademy.profiler.usecasses.ContactsService;
import by.itacademy.profiler.usecasses.annotation.IsCvExists;
import by.itacademy.profiler.usecasses.dto.ContactsDto;
import by.itacademy.profiler.usecasses.dto.ContactsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cvs/{uuid}/contacts")
@Slf4j
@Validated
@Tag(name = "contact", description = "the Contact API")
public class ContactsApiController {

    private final ContactsService contactsService;

    @PostMapping
    public ResponseEntity<ContactsResponseDto> saveContacts(@PathVariable @IsCvExists String uuid, @RequestBody @Valid ContactsDto contacts) {
        log.debug("Input data for creating contact information section of CV with UUID {}: {}", uuid, contacts);
        ContactsResponseDto cvContacts = contactsService.saveContacts(uuid, contacts);
        return new ResponseEntity<>(cvContacts, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Find Contacts by cv uuid", description = "Name search by %name% format", tags = {"contact"})
    public ResponseEntity<ContactsResponseDto> getContacts(@PathVariable @IsCvExists String uuid) {
        ContactsResponseDto contacts = contactsService.getContacts(uuid);
        log.debug("Getting contacts information section of CV {} from database: {} ", uuid, contacts);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ContactsResponseDto> updateContacts(@PathVariable @IsCvExists String uuid, @RequestBody @Valid ContactsDto contacts) {
        ContactsResponseDto contactsDto = contactsService.updateContacts(uuid, contacts);
        log.debug("Update contacts information section of CV {} by the data: {}", uuid, contactsDto);
        return new ResponseEntity<>(contactsDto, HttpStatus.OK);
    }
}
