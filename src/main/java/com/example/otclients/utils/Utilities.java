package com.example.otclients.utils;

import com.example.otclients.exceptions.InvalidActionException;
import com.example.otclients.exceptions.ServerException;
import jakarta.persistence.Id;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.otclients.utils.GlobalConstants.UPLOADS_TEMP_FILES_PATH;

public class Utilities {

    public static Logger getLogger(Class<?> aClass) {
        return LoggerFactory.getLogger(aClass);
    }

    public static <T> String getIdName(Class<T> entityType) {
        if (entityType == null) {
            return null;
        }

        Field idField = getIdField(entityType);
        return idField != null ? idField.getName() : null;
    }

    public static <T> Field getIdField(Class<T> entityType) {
        Class<? super T> superclass = entityType.getSuperclass();
        if (superclass == null) {
            return null;
        }

        for (Field field : superclass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        return null;
    }

    public static <T> T setFieldValue(T entity, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field;
        try {
            field = entity.getClass().getSuperclass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException ignored) {
            try {
                field = entity.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                throw e;
            }
        }
        field.setAccessible(true);
        field.set(entity, value);
        return entity;
    }

    public static String capitalizeEachWord(String input) {

        if (input == null || StringUtils.isBlank(input))
            return "";
        return Arrays.stream(input.split("\\s+"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static boolean isValidUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            URI uri = url.toURI();
            // Optional: add further checks for specific schemes, hosts, etc. if needed
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    private static Path getWorkingDir() {
        return Path.of(System.getProperty("user.dir") + UPLOADS_TEMP_FILES_PATH);
    }

    public static File convertMultipartFileToTempFile(MultipartFile multipartFile) {
        // Create a new temporary file with a unique name
        Path tempFile;
        try {
            Path projectDir = getWorkingDir();

            tempFile = Files.createTempFile(projectDir, "my-temp-", ".tmp");

            //            tempFile =
            //                    File.createTempFile("upload-", multipartFile.getOriginalFilename());

            // Use transferTo to move the data
            multipartFile.transferTo(tempFile);
//            System.out.println("location: " + tempFile.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tempFile.toFile();
    }

    public static boolean deleteTempFile(String filename) {
        try {
            Path file = getWorkingDir()
                    .resolve(filename); // Resolve the file's path
            return Files.deleteIfExists(file); // Deletes the file if it exists
        } catch (IOException e) {
            // Handle the exception appropriately (e.g., log the error, throw a custom exception)
            throw new ServerException("Error deleting file: " + e.getMessage());
        }
    }

    public static boolean isCSVFile(MultipartFile file) {
        String contentType = file.getContentType();

        // Check if the content type is one of the allowed types
        return "text/csv".equals(contentType);
    }

    public static String cleanStringAndDeleteWhitespace(String string) {
        if (string == null || StringUtils.isBlank(string))
            return "";

        return StringUtils.deleteWhitespace(string);
    }

    public static String cleanString(String string) {
        if (string == null || StringUtils.isBlank(string))
            return "";

        string = string.trim();

        if (string.length() > 255) {
            throw new InvalidActionException(String.format("%s contains too many characters.", string));
        }

        return string;
    }

    public static String cleanEmail(String string) {
        if (string == null || StringUtils.isBlank(string))
            return "";

        string = string.trim();

        if (string.length() > 255) {
            throw new InvalidActionException(String.format("%s contains too many characters.", string));
        }

        return string.toLowerCase();
    }

    public static String cleanPhoneNumber(String string) {
        if (string == null || StringUtils.isBlank(string))
            return "";

        string = cleanStringAndDeleteWhitespace(string)
                .replaceAll("-", "")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "");

        if (string.length() > 20) {
            throw new InvalidActionException(String.format("%s contains too many characters.", string));
        }

        return string;
    }

    public static String cleanStringAndDeleteWhitespaceToLowerCase(String string) {
        if (string == null || StringUtils.isBlank(string))
            return "";

        return StringUtils.deleteWhitespace(string).toLowerCase();
    }

    public static boolean isValidEmail(String string) {
        // Regular expression to match valid email formats
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regex
        Pattern p = Pattern.compile(emailRegex);

        return string != null && p.matcher(string).matches();
    }

    public static String normalizeAddressFields(String value) {
        return value == null || value.trim().isEmpty() ? "" :
                Normalizer.normalize(value, Normalizer.Form.NFKC)
                .trim()
                .replaceAll("\\s+", " "); // Collapse multiple spaces
    }

    public static String cleanZipCode(String zip) {
        if (zip == null) {
            return "";
        }
        if (zip.length() > 25) {
            throw new InvalidActionException(String.format("%s contains too many characters.", "Zip Code"));
        }
        return zip.trim().toUpperCase();
    }

    //Only for E.164 format (recommended for international numbers)
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^\\+[1-9]\\d{1,14}$");
    }

    public static String normalizeFilter(String value) {
        return value == null || value.trim().isEmpty()
                ? ""
                : value.trim().toLowerCase();
    }
}
