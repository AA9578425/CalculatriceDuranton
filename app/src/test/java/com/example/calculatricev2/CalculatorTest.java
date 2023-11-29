package com.example.calculatricev2;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.calculatricev2.model.CalculatorModel;

public class CalculatorTest {
    CalculatorModel _calculatorModel;

    public CalculatorTest(){
        _calculatorModel = new CalculatorModel();
    }
    @Test
    public void testCalculFromStringToDouble() {
        assertEquals(String.valueOf(_calculatorModel.calcul("2+2")), String.valueOf(2.0 + 2.0));
    }

    @Test
    public void testCalculNotCorreclyFormated() {
        assertThrows(IllegalArgumentException.class, () -> _calculatorModel.calcul("2+"));
    }
}