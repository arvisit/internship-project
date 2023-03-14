package by.itacademy.profiler.usecasses.util;

import java.util.List;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public final class ValidationConstants {
    public static final String REGEXP_VALIDATE_DESCRIPTION = "^[a-zA-Z0-9!-\\/:-@\\[-`{-~ ’“”]+$";

    public static final String REGEXP_VALIDATE_EMAIL = "^(?=.{6,}$)[\\s]*[a-zA-Z0-9]+([!\"#$%&'()*+,\\-.\\/:;<=>?\\[\\]\\^_{}][a-zA-z0-9]+)*@([\\w]+(-[\\w]+)?)(\\.[\\w]+[-][\\w]+)*(\\.[a-z]{2,})+[\\s]*$";
    public static final List<String> CONTENT_TYPES = List.of(IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE);

    public static final String REGEXP_VALIDATE_NAME = "^(?=.{1,}$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*$";

    public static final String REGEXP_VALIDATE_SURNAME = "^(?=.{1,}$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*$";

    public static final String REGEXP_VALIDATE_CELL_PHONE = "^(?=.{0,}$)[0-9]*$";

    private ValidationConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String REGEXP_VALIDATE_CITY = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$";

    public static final String REGEXP_VALIDATE_NOT_BLANK_BUT_NULL = "^(?! *$).+";
}