package by.itacademy.profiler.util;

import by.itacademy.profiler.persistence.model.Contacts;
import by.itacademy.profiler.usecasses.dto.ContactsDto;
import by.itacademy.profiler.usecasses.dto.ContactsResponseDto;

import static by.itacademy.profiler.util.CurriculumVitaeTestData.CV_UUID;
import static by.itacademy.profiler.util.PhoneCodeTestData.createPhoneCode;

public final class ContactTestData {

    public static final String CONTACTS_URL_TEMPLATE = String.format("/api/v1/cvs/%s/contacts", CV_UUID);
    public static final String DEFAULT_CONTACT_EMAIL = "test@gmail.com";
    public static final String DEFAULT_CONTACT_PHONE_NUMBER = "291112233";
    public static final String DEFAULT_CONTACT_SKYPE = "live8:test";
    public static final String DEFAULT_CONTACT_LINKEDIN_LINK = "https://linkedin.com/rnd";
    public static final String DEFAULT_CONTACT_PORTFOLIO_LINK = "https://github.com/rnd";
    public static final Long DEFAULT_CONTACT_PHONE_CODE_ID = 1L;
    public static final int DEFAULT_CONTACT_PHONE_CODE = 375;

    public static final String FIELD_NOT_BE_EMPTY_ERROR = "Field must not be empty";
    public static final String FIELD_NOT_BE_NULL_ERROR = "Field must not be null";
    public static final String LINK_ERROR_FIELD_MUST_BE_FILLED_ERROR = "Field must be filled or null";
    public static final String REGEXP_VALIDATE_CELL_PHONE_ERROR = "Invalid cell phone number. Example of the correct variant: 29233XXXX";
    public static final String REGEXP_VALIDATE_EMAIL_ERROR = "Invalid email. Example of the correct variant: example@example.com";
    public static final String MAXLENGTH_PHONE_NUMBER_ERROR = "The user's phone number is too long, the max number of symbols is 25";
    public static final String MAXLENGTH_EMAIL_ERROR = "The email is too long, the max number of symbols is 50";
    public static final String MAXLENGTH_SKYPE_ADDRESS_ERROR = "The Skype address is too long, the max number of symbols is 50";
    public static final String MAXLENGTH_LINKEDIN_LINK_ERROR = "The LinkedIn link is too long, the max number of symbols is 255";
    public static final String MAXLENGTH_PORTFOLIO_LINK_ERROR = "The portfolio link is too long, the max number of symbols is 255";

    private ContactTestData() {

    }

    public static Contacts.ContactsBuilder createContact() {
        return Contacts.builder()
                .withId(1L)
                .withEmail(DEFAULT_CONTACT_EMAIL)
                .withLinkedin(DEFAULT_CONTACT_LINKEDIN_LINK)
                .withPhoneCode(createPhoneCode())
                .withPhoneNumber(DEFAULT_CONTACT_PHONE_NUMBER)
                .withSkype(DEFAULT_CONTACT_SKYPE)
                .withPortfolio(DEFAULT_CONTACT_PORTFOLIO_LINK);
    }

    public static ContactsDto.ContactsDtoBuilder createContactDto() {
        return ContactsDto.builder()
                .withPhoneCodeId(DEFAULT_CONTACT_PHONE_CODE_ID)
                .withPhoneNumber(DEFAULT_CONTACT_PHONE_NUMBER)
                .withEmail(DEFAULT_CONTACT_EMAIL)
                .withSkype(DEFAULT_CONTACT_SKYPE)
                .withLinkedin(DEFAULT_CONTACT_LINKEDIN_LINK)
                .withPortfolio(DEFAULT_CONTACT_PORTFOLIO_LINK);
    }

    public static ContactsResponseDto.ContactsResponseDtoBuilder contactsResponseDto() {
        return ContactsResponseDto.builder()
                .withPhoneCodeId(DEFAULT_CONTACT_PHONE_CODE_ID)
                .withPhoneCode(DEFAULT_CONTACT_PHONE_CODE)
                .withPhoneNumber(DEFAULT_CONTACT_PHONE_NUMBER)
                .withEmail(DEFAULT_CONTACT_EMAIL)
                .withSkype(DEFAULT_CONTACT_SKYPE)
                .withLinkedin(DEFAULT_CONTACT_LINKEDIN_LINK)
                .withPortfolio(DEFAULT_CONTACT_PORTFOLIO_LINK);
    }
}
