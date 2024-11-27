package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GammaTest {
    private Gamma gamma;
    @BeforeEach
        public void beforeEachTest() { gamma = new Gamma(Location.C,10); }
    @Test
    @DisplayName("Getting generation when Gamma")
    void getGeneration() {
        //Arrange
        //Act
        var response = gamma.getGeneration();
        //Assert
        assertEquals("Gamma", response);
    }

    @DisplayName("Getting Gamma Rates")
    @Test
    void getRates() {
        //Arrange
        List<Double> gammaList = List.of(1.5, 2.0, 3.0);
        //Act
        var response = gamma.getRates();
        //Assert
        assertEquals(gammaList, response);
    }
}