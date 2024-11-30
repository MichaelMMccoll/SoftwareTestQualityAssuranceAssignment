import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

class MainTest {
    private final String[] args = {};

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final List<String> configureScenario = new ArrayList<String>(
                                                                Arrays.asList(
                                                                        "Configuring scenario (enter number to select):\r",
                                                                        "1. Add Historic.\r",
                                                                        "2. Add Recycling Centres.\r",
                                                                        "3. Run Scenario.\r"
                                                                )
    );
    private final List<String> addHistoric = new ArrayList<String>(
                                                            Arrays.asList(
                                                                    "Creating a historic site...\r",
                                                                    "Enter a location (A, B, or C):\r",
                                                                    "Enter the initial waste quantity at the historic site (in meters cubed):\r"
                                                            )
    );
    private final List<String> addRecycling = new ArrayList<String>(
                                                            Arrays.asList(
                                                                    "Creating a recycling centre...\r",
                                                                    "Enter a location (A, B, or C):\r",
                                                                    "Enter the number of years the recycling centre has been active for:\r",
                                                                    "Enter the generation of the recycling centre (Alpha, Beta, or Gamma):\r",
                                                                    "Recycling centre created. Would you like to create another recycling centre? (y/n)\r"
                                                            )
    );
    private final List<String> runningScenario = new ArrayList<String>(
                                                                Arrays.asList(
                                                                        "Running scenario...\r",
                                                                        "Scenario successfully completed. Results:\r"
                                                                )
    );
    private final List<String> exit = new ArrayList<String>(
                                                    Arrays.asList(
                                                            "Exiting...\r",
                                                            "End\r"
                                                    )
    );
    private final List<String> showOptions = new ArrayList<String>(
                                                            Arrays.asList(
                                                                    "Options (enter number to select):\r",
                                                                    "1. Configure/Run Scenario.\r",
                                                                    "2. About.\r",
                                                                    "3. Exit.\r"
                                                            )

    );
    private final List<String> showAbout = new ArrayList<String>(
                                                        Arrays.asList(
                                                            "About:\r",
                                                            "- This is a prototype of the Landfill Labs Worker Service.\r",
                                                            "- Use it to configure and run a single waste management scenario.\r",
                                                            "- To run multiple scenarios, you should run the aplicato multiple times.\r",
                                                            "- If you're confused, you may wish to consult the application specification or contact Four Walls Software.\r"));

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
    void Enter_About_Exit(){
        //Arrange
        var expectedOutput = Stream.of(showAbout,showOptions,exit)
                .flatMap(Collection::stream)
                .toList();
        String input ="2 3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        //Act
            Main.main(args);
        //Assert
        var output = Arrays.stream(outputStream.toString().split("\n")).toList();
        Assertions.assertTrue(output.containsAll(expectedOutput));
    }

    @Test
    void Enter_And_Exit() {
    //Arrange
        var expectedOutput = Stream.of(showOptions,exit)
                .flatMap(Collection::stream)
                .toList();
        String input = "3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    //Act
        Main.main(args);
    //Assert
        var output = Arrays.stream(outputStream.toString().split("\n")).toList();
        Assertions.assertTrue(output.containsAll(expectedOutput));
    }
//    @Test
//    void Running_basic_Scenario(){
//        //Arrange
//        Scanner scanner = Mockito.mock(Scanner.class);
//        var expectedOutput = Stream.of(showOptions,
//                                       exit,
//                                       configureScenario,
//                                       addHistoric,
//                                       runningScenario,
//                                       addRecycling)
//                .flatMap(Collection::stream)
//                .toList();
//
//        String input = "1 1";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        //Act
//        Main.main(args);
//        //Assert
//
//        var output = Arrays.stream(outputStream.toString().split("\n")).toList();
//        Assertions.assertTrue(output.containsAll(expectedOutput));
//    }
}