package com.example.otclients.utils;

import com.example.otclients.models.process.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class UtilitiesTest {

    @Test
    void testCapitilizeWords() {
        String string = "this is a nice sentence to capitalize.";

        Assertions.assertEquals("This Is A Nice Sentence To Capitalize.", Utilities.capitalizeEachWord(string));
    }

    @Test
    void testGetIdField() throws Exception {
        Field field = Client.class.getSuperclass().getDeclaredField("id");
        Assertions.assertEquals(field, Utilities.getIdField(Client.class));
    }

    @Test
    void testGetIdFieldName() throws Exception {
        Field field = Client.class.getSuperclass().getDeclaredField("id");
        Assertions.assertEquals(field.getName(), Utilities.getIdName(Client.class));
    }

    @Test
    void testSetFieldValue() throws Exception {
        Client test = Client.builder().build();
        Utilities.setFieldValue(test, "name", "name");
        Assertions.assertEquals("name", test.getName());
    }

    @Test
    void capitalizeEachWord() throws Exception {
        String test = "a b c d";
        Assertions.assertEquals("A B C D", Utilities.capitalizeEachWord(test));
        Assertions.assertEquals("", Utilities.capitalizeEachWord(null));
        Assertions.assertEquals("", Utilities.capitalizeEachWord(""));
    }

    @Test
    void isValidUrl() throws Exception {
        Assertions.assertTrue(Utilities.isValidUrl("https://google.com"));
        Assertions.assertTrue(Utilities.isValidUrl("https://storage.googleapis.com/covid19-open-data/v3/location/US.json"));
        Assertions.assertFalse(Utilities.isValidUrl("an url"));
    }

    @Test
    void isCSVFile() throws Exception {
        Assertions.assertTrue(Utilities.isCSVFile(new MultipartFile() {
            @Override
            public String getName() {
                return "file";
            }

            @Override
            public String getOriginalFilename() {
                return "file";
            }

            @Override
            public String getContentType() {
                return "text/csv";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        }));

        Assertions.assertFalse(Utilities.isCSVFile(new MultipartFile() {
            @Override
            public String getName() {
                return "file";
            }

            @Override
            public String getOriginalFilename() {
                return "file";
            }

            @Override
            public String getContentType() {
                return "application/json";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        }));
    }

    @Test
    void cleanString() throws Exception {
        Assertions.assertEquals("username clean", Utilities.cleanString("username clean         "));
        Assertions.assertEquals("a clean STring", Utilities.cleanString(" a clean STring  "));
    }

    @Test
    void cleanStringAndDeleteWhitespace() throws Exception {
        Assertions.assertEquals("usernameclean", Utilities.cleanStringAndDeleteWhitespace("username clean         "));
        Assertions.assertEquals("acleanSTring", Utilities.cleanStringAndDeleteWhitespace(" a clean STring  "));
    }

    @Test
    void cleanStringAndDeleteWhitespaceToLowerCase() throws Exception {
        Assertions.assertEquals("usernameclean", Utilities.cleanStringAndDeleteWhitespaceToLowerCase("useRname clean         "));
        Assertions.assertEquals("acleanstring", Utilities.cleanStringAndDeleteWhitespaceToLowerCase(" a clean STring  "));
    }

    @Test
    void isValidEmail() throws Exception {
        Assertions.assertTrue(Utilities.isValidEmail("mail1@mail.com"));
        Assertions.assertFalse(Utilities.isValidEmail("mail.com"));
    }

    @Test
    void cleanPhoneNumber() throws Exception {
        Assertions.assertEquals("+18095556655", Utilities.cleanPhoneNumber("+18095556655"));
        Assertions.assertEquals("+18095556655", Utilities.cleanPhoneNumber("+1 809 555 6655"));
        Assertions.assertEquals("+18095556655", Utilities.cleanPhoneNumber("+1 (809) 555 6655"));
        Assertions.assertEquals("+18095556655", Utilities.cleanPhoneNumber("+1 (809)-555-6655"));
    }
}
