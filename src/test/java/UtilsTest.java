import models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {
    private Utils util;
    private static final Alpha alpha = new Alpha(Location.A, 1);
    private static final Beta beta = new Beta(Location.B, 2);
    private static final Gamma gamma = new Gamma(Location.C, 3);

    @DisplayName("Find viable centers")
    @ParameterizedTest(name = "{index} {1}")//name = "{index} {3}"
    @MethodSource("FindViableCenters")
    void findViableCentres(List<Recycling> centers, Historic historicLocation, List<Recycling> expectedResponse) {
        //Arrange
        //var list =  new ArrayList<Recycling>(centers);
        //Act
        var response = util.findViableCentres(historicLocation, centers);
        //Assert
        Assertions.assertEquals(response, expectedResponse);
    }

    @ParameterizedTest
    @DisplayName("Finding optimal center with all 3 types")
    @MethodSource("FindOptimalCenters")
    void findOptimalCentre(List<Recycling> centers, Historic historicLocation, Integer arrayPlace) {
        //Arrange
        //Act
        var response = util.findOptimalCentre(historicLocation, centers);

        //Assert
        assertEquals(response, centers.get(arrayPlace));
    }

    //Test crashes when no Recycling center is given
    //This should fail
    @Test
    @DisplayName("Finding optimal center with there are 0 types")
    void findOptimalCentre_When_C_0Centers() {
        //Arrange
        var recyclingList = new ArrayList<Recycling>();
        var aa = new Historic(Location.C, 1251.0);
        var errorMessage = "";
        //Act
        util.findOptimalCentre(aa, recyclingList);
        //Assert
        //assertThrows(NoSuchElementException.class, () -> util.findOptimalCentre(aa,recyclingList));
    }

    @ParameterizedTest
    @DisplayName("Calculates travel duration")
    @MethodSource("CalculateTravelDuration")
    void calculateTravelDuration(Recycling type, Historic location, Double expectedResponse) {
        //Arrange
        //Act
        var response = util.calculateTravelDuration(location, type);
        //Assert
        assertEquals(response, expectedResponse);
    }

    @ParameterizedTest
    @DisplayName("Calculates process duration")
    @MethodSource("CalculateProcessDuration")
    void calculateProcessDuration(Recycling type, Historic location, Double expectedResponse) {
        //Arrange
        //Act
        var response = util.calculateProcessDuration(location, type);
        //Assert
        assertEquals(response, expectedResponse);
    }


    private static Stream<Arguments> CalculateTravelDuration() {
        return Stream.of(
                Arguments.of(new Alpha(Location.A, 1), new Historic(Location.A, 1251.0), 63.0),
                Arguments.of(new Alpha(Location.A, 1), new Historic(Location.B, 1251.0), 126.0),
                Arguments.of(new Alpha(Location.A, 1), new Historic(Location.C, 1251.0), 252.0)
        );
    }

    private static Stream<Arguments> CalculateProcessDuration() {
        return Stream.of(
                Arguments.of(new Alpha(Location.A, 1), new Historic(Location.A, 1251.0), 1251.0),
                Arguments.of(new Alpha(Location.A, 1), new Historic(Location.B, 1000.0), 1000.0),
                Arguments.of(new Alpha(Location.A, 1), new Historic(Location.C, 1000.0), 1000.0)
        );
    }

    private static Stream<Arguments> FindOptimalCenters() {
        return Stream.of(
                Arguments.of(new ArrayList<Recycling>(Arrays.asList(
                                new Beta(Location.B, 2),
                                new Alpha(Location.A, 1),
                                new Gamma(Location.C, 3))),
                        new Historic(Location.A, 1251.0),
                        1),
                Arguments.of(new ArrayList<Recycling>(Arrays.asList(
                                new Beta(Location.B, 2),
                                new Alpha(Location.A, 1),
                                new Gamma(Location.C, 3))),
                        new Historic(Location.B, 1251.0),
                        1),
                Arguments.of(new ArrayList<Recycling>(Arrays.asList(
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
                        new ArrayList<Recycling>(Arrays.asList(
                                alpha,
                                beta,
                                gamma)),
                        new Historic(Location.A, 1251.0),
                        new ArrayList<Recycling>(Arrays.asList(
                                alpha,
                                beta))),

                Arguments.of(
                        new ArrayList<Recycling>(Arrays.asList(
                               alpha,
                                beta,
                                gamma)),
                        new Historic(Location.B, 100.0),
                        new ArrayList<Recycling>(Arrays.asList(
                                alpha,
                                beta))),

                Arguments.of(
                        new ArrayList<Recycling>(),
                        new Historic(Location.C, 100.0),
                        new ArrayList<Recycling>())

        );
    }

}