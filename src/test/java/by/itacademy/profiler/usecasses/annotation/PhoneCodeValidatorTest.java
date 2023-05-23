package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.usecasses.PhoneCodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneCodeValidatorTest {

    @Mock
    private PhoneCodeService phoneCodeServiceMock;
    @InjectMocks
    private PhoneCodeValidator phoneCodeValidator;

    @Test
    void shouldReturnTrueWhenExistingPhoneCode() {
        Long phoneCode = 1L;
        when(phoneCodeServiceMock.isPhoneCodeExist(phoneCode)).thenReturn(true);

        boolean isValid = phoneCodeValidator.isValid(phoneCode, null);

        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseWhenPhoneCodeNotExisting() {
        Long invalidPhoneCode = 500L;
        when(phoneCodeServiceMock.isPhoneCodeExist(invalidPhoneCode)).thenReturn(false);

        boolean isValid = phoneCodeValidator.isValid(invalidPhoneCode, null);

        assertFalse(isValid);
    }
}