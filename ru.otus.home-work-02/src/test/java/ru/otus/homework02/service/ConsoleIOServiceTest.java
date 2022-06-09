package ru.otus.homework02.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleIOServiceTest {

    @Test
    void writeLineTest() {
        final String testLine = "Test";

        var baOutputStream = new ByteArrayOutputStream();
        var printStream = new PrintStream(baOutputStream);
        var savedSystemOut = System.out;
        System.setOut(printStream);

        IOService consoleService = new ConsoleIOService();
        consoleService.writeLine(testLine);

        System.setOut(savedSystemOut);

        Assertions.assertEquals(baOutputStream.toString(), testLine + System.lineSeparator());
    }

    @Test
    void writeErrorTest() {
        final String testError = "Error";

        var baOutputStream = new ByteArrayOutputStream();
        var printStream = new PrintStream(baOutputStream);
        var savedSystemErr = System.err;
        System.setErr(printStream);

        IOService consoleService = new ConsoleIOService();
        consoleService.writeError(testError);

        System.setErr(savedSystemErr);

        Assertions.assertEquals(baOutputStream.toString(), testError + System.lineSeparator());
    }

    @Test
    void readLineTest() {
        final String testInput = "Test Input";
        var myStream = new ByteArrayInputStream(testInput.getBytes());
        var savedSystemIn = System.in;
        System.setIn(myStream);

        IOService consoleService = new ConsoleIOService();
        var testLine = consoleService.readLine();

        System.setIn(savedSystemIn);

        Assertions.assertEquals(testLine, testInput);
    }
}