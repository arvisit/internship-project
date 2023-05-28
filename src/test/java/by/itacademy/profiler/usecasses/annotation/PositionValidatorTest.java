package by.itacademy.profiler.usecasses.annotation;

import by.itacademy.profiler.usecasses.PositionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionValidatorTest {

    @Mock
    private PositionService positionService;
    @InjectMocks
    private PositionValidator positionValidator;

    @Test
    void shouldReturnTrueWhenPositionIsExist() {
        Long position = 1L;
        when(positionService.isPositionExist(position)).thenReturn(true);

        boolean isValid = positionValidator.isValid(position, null);

        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseWhenPositionIsNotExist() {
        Long invalidPosition = 10000L;
        when(positionService.isPositionExist(invalidPosition)).thenReturn(false);

        boolean isValid = positionValidator.isValid(invalidPosition, null);

        assertFalse(isValid);
    }
}
