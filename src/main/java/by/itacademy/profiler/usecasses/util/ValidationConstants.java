package by.itacademy.profiler.usecasses.util;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class ValidationConstants {
    public static final String REGEXP_VALIDATE_EMAIL = "^(?=.{6,}$)[\\s]*[a-zA-Z0-9]+([!\"#$%&'()*+,\\-.\\/:;<=>?\\[\\]\\^_{}][a-zA-z0-9]+)*@([\\w]+(-[\\w]+)?)(\\.[\\w]+[-][\\w]+)*(\\.[a-z]{2,})+[\\s]*$";

    public static final String REGEXP_VALIDATE_NAME = "^(?=.{1,}$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*$";

    public static final String REGEXP_VALIDATE_SURNAME = "^(?=.{1,}$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*$";

    public static final String REGEXP_VALIDATE_CELL_PHONE = "^(?=.{1,}$)[0-9]*$";

    public static final List<String> CONTENT_TYPES = Arrays.asList(IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE);

    public static final String REGEXP_VALIDATE_CITY = "^(?=.{1,50}$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*$";

    public static final String REGEXP_VALIDATE_NOT_BLANK_BUT_NULL = "^\\s*\\S[^]]*";
}