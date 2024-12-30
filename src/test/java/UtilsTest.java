import models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {
    private static final Alpha alpha = new Alpha(Location.A, 1);
    private static final Beta beta = new Beta(Location.B, 2);
    private static final Gamma gamma = new Gamma(Location.C, 3);

    @DisplayName("Find viable centers")
    @ParameterizedTest(name = "{index} {0}")//name = "{index} {3}"
    @MethodSource("FindViableCenters")
    void findViableCentres(String Title, List<Recycling> centers, Historic historicLocation, List<Recycling> expectedResponse) {
        //Arrange
        //Act
        var response = Utils.findViableCentres(historicLocation, centers);
        //Assert
        Assertions.assertEquals(response, expectedResponse);
    }

    @ParameterizedTest(name = "{index} {0}")//name = "{index} {3}"
    @DisplayName("Finding optimal center with all 3 types")
    @MethodSource("FindOptimalCenters")
    void findOptimalCentre(String Title, List<Recycling> centers, Historic historicLocation, Integer arrayPlace) {
        //Arrange
        //Act
        var response = Utils.findOptimalCentre(historicLocation, centers);

        //Assert
        assertEquals(response, centers.get(arrayPlace));
    }

    //Test crashes when no Recycling center is given
    //This should fail
    @Test
    @DisplayName("Finding optimal center when there are 0 types")
    void Find_Optimal_Center_No_Recycling_016() {
        //Arrange
        var recyclingList = new ArrayList<Recycling>();
        var aa = new Historic(Location.C, 1251.0);

        //Act
        Utils.findOptimalCentre(aa, recyclingList);
        //Assert
     }

    @ParameterizedTest(name = "{index} {0}")
    @DisplayName("Calculates travel duration")
    @MethodSource("CalculateTravelDuration")
    void calculateTravelDuration_Tests(String Title, Recycling type, Historic location, Double expectedResponse) {
        //Arrange
        //Act
        var response = Utils.calculateTravelDuration(location, type);
        //Assert
        assertEquals(response, expectedResponse);
    }

    @ParameterizedTest(name = "{index} {0}")
    @DisplayName("Calculates process duration")
    @MethodSource("CalculateProcessDuration")
    void calculateProcessDuration_Tests(String Title, Recycling type, Historic location, Double expectedResponse) {
        //Arrange
        //Act
        var response = Utils.calculateProcessDuration(location, type);
        //Assert
        assertEquals(response, expectedResponse);
    }


    private static Stream<Arguments> CalculateTravelDuration() {
        return Stream.of(
                Arguments.of("A to A with 700 waste",new Alpha(Location.A, 1), new Historic(Location.A, 700.0), 35.0),
                Arguments.of("A to A with 1251 waste",new Alpha(Location.A, 1), new Historic(Location.A, 1251.0), 63.0),
                Arguments.of("A to B with 1251 waste",new Alpha(Location.A, 1), new Historic(Location.B, 1251.0), 126.0),
                Arguments.of("A to C with 1251 waste",new Alpha(Location.A, 1), new Historic(Location.C, 1251.0), 252.0)
        );
    }

    private static Stream<Arguments> CalculateProcessDuration() {
        return Stream.of(
                Arguments.of("A to A with 1251 waste",new Alpha(Location.A, 1), new Historic(Location.A, 1251.0), 1251.0),
                Arguments.of("A to B with 1000 waste",new Alpha(Location.A, 1), new Historic(Location.B, 1000.0), 1000.0),
                Arguments.of("B to C with 1000 waste",new Beta(Location.B, 1), new Historic(Location.C, 1000.0), 666.67),
                Arguments.of("C to C with 1000 waste",new Gamma(Location.C, 1), new Historic(Location.C, 1000.0), 583.34)
        );
    }

    private static Stream<Arguments> FindOptimalCenters() {
        return Stream.of(
                Arguments.of("Find optimal Center Location A",
                        new ArrayList<>(Arrays.asList(
                                new Beta(Location.B, 2),
                                new Alpha(Location.A, 1),
                                new Gamma(Location.C, 3))),
                        new Historic(Location.A, 1251.0),
                        1),
                Arguments.of("Find optimal Center Location B",
                        new ArrayList<>(Arrays.asList(
                                new Beta(Location.B, 2),
                                new Alpha(Location.A, 1),
                                new Gamma(Location.C, 3))),
                        new Historic(Location.B, 1251.0),
                        1),
                Arguments.of("Find optimal Center Location C",
                        new ArrayList<>(Arrays.asList(
                                new Beta(Location.B, 2),
                                new Alpha(Location.A, 1),
                                new Gamma(Location.C, 3))),
                        new Historic(Location.C, 1251.0),
                        0)
        );
    }

    private static Stream<Arguments> FindViableCenters() {
        return Stream.of(
                Arguments.of(
                        "Find Viable Center Location A",
                        new ArrayList<>(Arrays.asList(
                                alpha,
                                beta,
                                gamma)),
                        new Historic(Location.A, 1251.0),
                        new ArrayList<>(Arrays.asList(
                                alpha,
                                beta))),

                Arguments.of(
                        "Find Viable Center Location B",
                        new ArrayList<>(Arrays.asList(
                                alpha,
                                beta,
                                gamma)),
                        new Historic(Location.B, 100.0),
                        new ArrayList<>(Arrays.asList(
                                alpha,
                                beta))),

                Arguments.of(
                        "Find Viable Center Location C",
                        new ArrayList<Recycling>(),
                        new Historic(Location.C, 100.0),
                        new ArrayList<Recycling>())

        );
    }

}