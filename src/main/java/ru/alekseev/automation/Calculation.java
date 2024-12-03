package ru.alekseev.automation;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Calculation {

    public Calculation() {

    }

    private static final Double TIME = 100.0; // миллисекунды

    private static final Double K_R = 0.13;
    //private static final Double d = 0.45;
    //private static final Double mR = 0.25;
    //private static final Double c = 245000.0;
    private static final Double K = 1.0;


    private final Double i0 = 3438.15;//удельный пустотный импульс
    private final Double p0 = 1667.0; //кН
    private final Double km = 3.56;
    private final Double pa = 0.061;//МПа
    private final Double pk = 14.71;// МПа
    private final Double d_kr = 0.278;
    private final Double f_kr = getF_kr(); // квадратные метры
    private final Double r = 372.764;//Дж/кг*К
    private final Double t = 3668.0;//К


    private Double d = 0.45; // декремент затухания
    private Double mR = 0.25;// масса груза
    private Double c = 245000.0;// жесткость пружины

    //private final Double v_ks = 0.0927124; // объем кс
    private final Double v_ks = 0.114749; // объем кс

    private Equation wsZ = new Equation();
    private Equation ws_ch = new Equation();

    private Double getF_kr(){
        return (Math.PI*Math.pow(d_kr, 2)) / 4;
    }
    public void setD(Double d){
        this.d = d;
        calculate_Ws();
    }

    public void setMr(Double mR){
        this.mR = mR;
        calculate_Ws();
    }

    public void setC(Double c){
        this.c = c;
        calculate_Ws();
    }

    public void calculate_Ws() {
        System.out.println(getF_kr());
        Double betta;
        Double tau_pr;
        Double t_s;
        Double double_D_T;
        betta = (f_kr * pk * Math.pow(10, 6) * 4 * i0) / (p0 * Math.pow(10, 3));
        tau_pr = ((betta * v_ks) / (f_kr * r * t)) * 1000;
        //tau_pr = 5.1135;
        System.out.println(betta);
        System.out.println(tau_pr); // мс
        //System.out.println(a * b - a / b);
        t_s = (Math.sqrt(mR / c)) * 1000; // мс
        //t_s = 1.01;
        System.out.println(t_s);
        double_D_T = 2 * d * t_s;
        //double_D_T = 0.99;


        Equation w1Z = new Equation(tau_pr, 1.0);
        Equation w2Z = new Equation(Math.pow(t_s, 2), double_D_T, 1.0);

        Double a1a2 = w1Z.getA() * w2Z.getA();
        wsZ.setA(a1a2 / a1a2);
        wsZ.setB((w1Z.getA() * w2Z.getB() + w2Z.getA() * w1Z.getB()) / (a1a2));
        wsZ.setC((w1Z.getA() * w2Z.getC() + w1Z.getB() * w2Z.getB()) / a1a2);
        wsZ.setD((w1Z.getB() * w2Z.getC() + K_R) / a1a2);


        ws_ch.setA((Math.pow(t_s, 2) * K)/ a1a2);
        ws_ch.setB((double_D_T *K)/ a1a2);
        ws_ch.setC((1.0 * K)/ a1a2);

        System.out.println("----------------------------");
        System.out.println(ws_ch);
        System.out.println(wsZ);

    }

    public List<Complex> solve() {
        //ax^3 + bx^2 + cx + d = 0
        //Начинается с d
        //new PolynomialFunction(d, c, b, a);
        double[] odds = {
                wsZ.getD(),
                wsZ.getC(),
                wsZ.getB(),
                wsZ.getA()
        };
        PolynomialFunction polynomial = new PolynomialFunction(odds);
        LaguerreSolver solver = new LaguerreSolver();

        Complex[] roots = solver.solveAllComplex(polynomial.getCoefficients(), 0);

        return new ArrayList<Complex>(Arrays.asList(roots));
    }

    public List<Complex> findA(List<Complex> lambdas) {
        lambdas.addFirst(new Complex(0.0));
        List<Complex> aOdds = new ArrayList<>();
        aOdds.add(((lambdas.get(0)).multiply(ws_ch.getA()).add(lambdas.get(0).multiply(ws_ch.getB())).add(ws_ch.getC()))
                .divide(lambdas.get(1).multiply(lambdas.get(2)).multiply(lambdas.get(3))).negate()); // Ao

        aOdds.add(((lambdas.get(1)).pow(2.0).multiply(ws_ch.getA()).add(lambdas.get(1).multiply(ws_ch.getB())).add(ws_ch.getC()))
                .divide(lambdas.get(1).multiply(lambdas.get(1).add(lambdas.get(2).negate())).multiply(lambdas.get(1).add(lambdas.get(3).negate()))));//A1

        aOdds.add(((lambdas.get(2)).pow(2.0).multiply(ws_ch.getA()).add(lambdas.get(2).multiply(ws_ch.getB())).add(ws_ch.getC()))
                .divide(lambdas.get(2).multiply(lambdas.get(2).add(lambdas.get(1).negate())).multiply(lambdas.get(2).add(lambdas.get(3).negate()))));//A2

        aOdds.add(((lambdas.get(3)).pow(2.0).multiply(ws_ch.getA()).add(lambdas.get(3).multiply(ws_ch.getB())).add(ws_ch.getC()))
                .divide(lambdas.get(3).multiply(lambdas.get(3).add(lambdas.get(1).negate())).multiply(lambdas.get(3).add(lambdas.get(2).negate()))));//A3
        return aOdds;
    }


    public double[] complexToExponentialForm(Complex c) {
        double r = Math.sqrt(Math.pow(c.getReal(), 2) + Math.pow(c.getImaginary(), 2));
        double fi = Math.atan2(c.getImaginary(), c.getReal());
        //System.out.println(r + " * e^( " + fi + "* i)");
        return new double[]{r, fi};
    }


    public void finallyPrint(List<Complex> aOdds, List<Complex> lambdas) {
        //System.out.println(lambdas);
        System.out.println("y(t) = " +
                aOdds.get(0).getReal() + " " +
                aOdds.get(1).getReal() + "*e^" + lambdas.get(0).getReal() + "*t + " +
                2 * complexToExponentialForm(aOdds.get(2))[0] + "*e^" + lambdas.get(1).getReal() + "t * " +
                "cos(" + complexToExponentialForm(aOdds.get(3))[1] + " + " + lambdas.get(2).getImaginary() + "*t)");
    }

    public List<Double> finallyCalculated(List<Complex> aOdds, List<Complex> lambdas){
        //System.out.println(d + "\n----------------" );
        List<Double> y_t = new ArrayList<>();
        double i = 0.0;
        double step = 1.0;
        IntStream.range(0, (int)(TIME / step))
                        .forEach(j -> y_t.add(aOdds.get(0).getReal() +
                                aOdds.get(1).getReal() * Math.pow(Math.E, lambdas.get(0).getReal() * j) +
                                2 * complexToExponentialForm(aOdds.get(2))[0] * Math.pow(Math.E, lambdas.get(1).getReal() * j) *
                                        Math.cos(complexToExponentialForm(aOdds.get(3))[1] + lambdas.get(2).getImaginary() * j)));
        //System.out.println(y_t.size());
        return y_t;
    }
}
