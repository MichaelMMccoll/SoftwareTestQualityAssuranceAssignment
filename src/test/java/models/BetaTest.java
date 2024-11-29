package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BetaTest {
    private Beta beta;
    @BeforeEach
        public void beforeEachTest() { beta = new Beta(Location.B,10); }
    @Test
    @DisplayName("Getting generation when Beta")
    void getGeneration() {
        //Arrange
        //Act
        var response = beta.getGeneration();
        //Assert
        assertEquals("Beta", response);
    }

    @DisplayName("Getting Beta Rates")
    @Test
    void getRates() {
        //Arrange
        List<Double> betaList = List.of(1.5, 1.5, 1.5);
        //Act
        var response = beta.getRates();
        //Assert
        assertEquals(betaList, response);
    }
}