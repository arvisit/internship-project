package by.itacademy.profiler.usecasses.util;

public class ValidationConstants {
    public static final String REGEXP_VALIDATE_EMAIL = "^(?=.{6,50}$)(?![_!#$%&'*+/=?`{|}~^.-])+[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static final String REGEXP_VALIDATE_NAME = "^(?=.{1,50}$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*$";

    public static final String REGEXP_VALIDATE_SURNAME = "^(?=.{1,50}$)[a-zA-Z]+(?:[-' ][a-zA-Z]+)*$";

    public static final String REGEXP_VALIDATE_CELL_PHONE = "^(?=.{1,25}$)[0-9]*$";
}
