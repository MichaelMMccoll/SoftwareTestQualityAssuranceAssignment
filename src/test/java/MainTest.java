import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

class MainTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to a ByteArrayOutputStream
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreSystemOut() {
        // Restore the original System.out after each test
        System.setOut(originalOut);
    }

    @Test
    void main() {
    //Arrange
    String[] args = {};
    String input = "3";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    //Act
        Main.main(args);
    //Assert
    var aa = Arrays.stream(outputStream.toString().split("\n")).toList();
        Assertions.assertTrue(outputStream.toString().contains("Exiting"));
    }




}