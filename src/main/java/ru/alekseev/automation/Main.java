package ru.alekseev.automation;

import org.apache.commons.math3.complex.Complex;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Calculation calculation = new Calculation();
        calculation.calculate_Ws();
        System.out.println(calculation.solve());
        System.out.println("___________________");
        System.out.println(calculation.findA(calculation.solve()));
        System.out.println("___________________");
        //calculation.finallyPrint(calculation.findA(calculation.solve()), calculation.solve());

        //System.out.println(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));

        PrintClass print = new PrintClass("fff",
                calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));

        print.setSize(800, 600);
        print.setLocationRelativeTo(null);
        print.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        print.setVisible(true);
    }
}
