package ru.alekseev.automation;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Equation {

    private Double a;
    private Double b;
    private Double c;
    private Double d;

    public Equation(Double a, Double b) {
        this.a = a;
        this.b = b;
    }

    public Equation(Double a, Double b, Double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Equation(Double a, Double b, Double c, Double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public String toString() {
        return "{" + a + " " + b + " " + c + " " + d + "}";
    }
}
