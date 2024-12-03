package ru.alekseev.automation;

import org.apache.commons.math3.complex.Complex;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Calculation calculation = new Calculation();
        calculation.calculate_Ws();
//        System.out.println(calculation.solve());
//        System.out.println("___________________");
//        System.out.println(calculation.findA(calculation.solve()));
//        System.out.println("___________________");
        //calculation.finallyPrint(calculation.findA(calculation.solve()), calculation.solve());

        //System.out.println(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));


        List<List<Double>> arr = new ArrayList<>(); // Эталонный массив со значением
        arr.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        PrintClass print = new PrintClass("График переходного процесса",
                arr);

        print.setSize(800, 600);
        print.setLocationRelativeTo(null);
        print.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        print.setVisible(true);

        List<List<Double>> arrDChanges = new ArrayList<>(); // Изменение декремента затухания 
        calculation.setD(0.75);
        arrDChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setD(0.45);
        arrDChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setD(0.3);
        arrDChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setD(0.2);
        arrDChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setD(0.15);
        arrDChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setD(0.1);
        arrDChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));


        PrintClass printDChanges = new PrintClass("График переходного процесса с изменением декремента затухания",
                arrDChanges);

        printDChanges.setSize(800, 600);
        printDChanges.setLocationRelativeTo(null);
        printDChanges.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        printDChanges.setVisible(true);

        List<List<Double>> arrMChanges = new ArrayList<>(); // Изменение массы груза
        calculation.setD(0.45);
        calculation.setMr(5.0);
        arrMChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setMr(1.0);
        arrMChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setMr(0.5);
        arrMChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setMr(0.25);
        arrMChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setMr(0.1);
        arrMChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));


        PrintClass printMChanges = new PrintClass("График переходного процесса с изменением массы груза",
                arrMChanges);

        printMChanges.setSize(800, 600);
        printMChanges.setLocationRelativeTo(null);
        printMChanges.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        printMChanges.setVisible(true);

        List<List<Double>> arrCChanges = new ArrayList<>(); // Изменение массы груза
        calculation.setD(0.45);
        calculation.setMr(0.25);
        calculation.setC(500000.0);
        arrCChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setC(300000.0);
        arrCChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setC(245000.0);
        arrCChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));
        calculation.setC(100000.0);
        arrCChanges.add(calculation.finallyCalculated(calculation.findA(calculation.solve()), calculation.solve()));


        PrintClass printCChanges = new PrintClass("График переходного процесса с изменением жесткости пружины",
                arrCChanges);

        printCChanges.setSize(800, 600);
        printCChanges.setLocationRelativeTo(null);
        printCChanges.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        printCChanges.setVisible(true);
    }
}
