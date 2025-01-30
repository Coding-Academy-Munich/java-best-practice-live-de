// -*- coding: utf-8 -*-
// %% [markdown]
//
// <div style="text-align:center; font-size:200%;">
//  <b>Clean Code: Funktionen (Teil 2)</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// ## Typ-Tags und Switch-Anweisungen

// %%
public enum EmployeeType {
    COMMISSIONED,
    HOURLY,
    SALARIED
}

// %%
public class EmployeeV1 {
    public EmployeeType type;

    public EmployeeV1(EmployeeType type) {
        this.type = type;
    }
}

// %%
public double calculatePay(EmployeeV1 e) {
    switch (e.type) {
        case EmployeeType.COMMISSIONED:
            return calculateCommissionedPay(e);
        case EmployeeType.HOURLY:
            return calculateHourlyPay(e);
        case EmployeeType.SALARIED:
            return calculateSalariedPay(e);
        default:
            throw new IllegalArgumentException("No valid employee type.");
    }
}

// %%
public float calculateCommissionedPay(EmployeeV1 e) {
    return 100.0f;
}

// %%
public double calculateHourlyPay(EmployeeV1 e) {
    return 120.0;
}

// %%
public float calculateSalariedPay(EmployeeV1 e) {
    return 80.0f;
}

// %%
EmployeeV1 e1 = new EmployeeV1(EmployeeType.HOURLY);
EmployeeV1 e2 = new EmployeeV1(EmployeeType.SALARIED);

// %%
System.out.println(calculatePay(e1));
System.out.println(calculatePay(e2));

// %% [markdown]
//
// - Switch-Anweisungen führen oft Operationen auf der gleichen Abstraktionsebene
//   aus. (für "Subtypen" anstelle des ursprünglichen Typs)
// - "Subtypen" werden oft durch Typ-Tags unterschieden
// - In Python wird das mit `if`-`elif`-`else`-Ketten realisiert, da es keine
//   `switch`-Anweisung gibt

// %% [markdown]
//
// ## Ersetze Switch-Anweisung durch Polymorphie
//
// Es ist oft besser, switch-Anweisungen durch Vererbung und Polymorphie zu
// ersetzen:

// %%
public interface EmployeeV2 {
    double calculatePay();
}

// %%
public class CommissionedEmployee implements EmployeeV2 {
    @Override
    public double calculatePay() {
        return 100.0;
    }
}

// %%
public class HourlyEmployee implements EmployeeV2 {
    @Override
    public double calculatePay() {
        return 120.0;
    }
}

// %%
public class SalariedEmployee implements EmployeeV2 {
    @Override
    public double calculatePay() {
        return 80.0;
    }
}

// %%
public EmployeeV2 createEmployeeV2(EmployeeType employeeType) {
    switch (employeeType) {
        case EmployeeType.COMMISSIONED:
            return new CommissionedEmployee();
        case EmployeeType.HOURLY:
            return new HourlyEmployee();
        case EmployeeType.SALARIED:
            return new SalariedEmployee();
        default:
            throw new IllegalArgumentException("Not a valid employee type.");
    }
}

// %%
EmployeeV2 e1 = createEmployeeV2(EmployeeType.HOURLY);
EmployeeV2 e2 = createEmployeeV2(EmployeeType.SALARIED);

// %%
System.out.println(e1.calculatePay());
System.out.println(e2.calculatePay());

// %% [markdown]
//
// ## Trade-Offs: Vererbungsvariante
//
// - Neue "Bezahlvarianten" könne ohne Änderung des bestehenden Codes hinzugefügt
//   werden
// - Potentiell Explosion von Unterklassen (bei mehreren Enumerationstypen)

// %% [markdown]
//
// ## Trade-Offs: Switch-Variante
//
// - Einfacher zu verstehen
// - Erleichtert die Definition von Funktionen, die auf die Enumeration zugreifen
//   - In Python gibt es aber `functools.singledispatch` für die Vererbungsvariante

// %% [markdown]
//
// ## Trade-Offs: Design
//
// - Spiegelt sich die Unterscheidung zwischen den Subtypen im Domänenmodell
//   wieder?

// %% [markdown]
//
// ## Ersetzen der Enumeration durch Vererbung
//
// - Polymorphie statt Enumeration
// - Nicht auf der Ebene der gesamten Klasse
// - Mildert die Nachteile der Vererbungsvariante

// %% [markdown]
//
// ### Beispiel
//
// - Abstrakte Klasse `PaymentCalculator` statt `EmployeeType`
// - Konkrete Unterklasse pro "Bezahlvariante"
// - `Employee` delegiert Berechnung an `PaymentCalculator`

// %%
public interface PaymentCalculator {
    double calculatePay();
}

// %%
public class CommissionedPaymentCalculator implements PaymentCalculator {
    @Override
    public double calculatePay() {
        return 100.0;
    }
}

// %%
public class HourlyPaymentCalculator implements PaymentCalculator {
    @Override
    public double calculatePay() {
        return 120.0;
    }
}

// %%
public class SalariedPaymentCalculator implements PaymentCalculator {
    @Override
    public double calculatePay() {
        return 80.0;
    }
}

// %%
public class EmployeeV3 {
    private PaymentCalculator paymentCalculator;

    public EmployeeV3(PaymentCalculator paymentCalculator) {
        this.paymentCalculator = paymentCalculator;
    }

    public double calculatePay() {
        return paymentCalculator.calculatePay();
    }
}

// %%
public EmployeeV3 createEmployeeV3(EmployeeType employeeType) {
    switch (employeeType) {
        case EmployeeType.COMMISSIONED:
            return new EmployeeV3(new CommissionedPaymentCalculator());
        case EmployeeType.HOURLY:
            return new EmployeeV3(new HourlyPaymentCalculator());
        case EmployeeType.SALARIED:
            return new EmployeeV3(new SalariedPaymentCalculator());
        default:
            throw new IllegalArgumentException("Not a valid employee type.");
    }
}

// %%
EmployeeV3 e1 = createEmployeeV3(EmployeeType.HOURLY);
EmployeeV3 e2 = createEmployeeV3(EmployeeType.SALARIED);

// %%
System.out.println(e1.calculatePay());
System.out.println(e2.calculatePay());

// %% [markdown]
//
// ## Mini-Workshop: Ersetzen von Switch-Anweisungen
//
// Strukturieren Sie den folgenden Code so um, dass nur noch bei der Erzeugung
// der Objekte eine "switch-Anweisung" verwendet wird:

// %%
public enum ComputerType {
    COMPUTER_TYPE_PC,
    COMPUTER_TYPE_MAC,
    COMPUTER_TYPE_CHROMEBOOK
}

// %%
public class ComputerV1 {
    public ComputerType computerType;

    public ComputerV1(ComputerType computerType) {
        this.computerType = computerType;
    }
}

// %%
public void compileCode(ComputerV1 computer) {
    switch (computer.computerType) {
        case COMPUTER_TYPE_PC:
            System.out.println("Compiling code for PC.");
            break;
        case COMPUTER_TYPE_MAC:
            System.out.println("Compiling code for Mac.");
            break;
        case COMPUTER_TYPE_CHROMEBOOK:
            System.out.println("Compiling code for Chromebook.");
            break;
        default:
            throw new IllegalArgumentException("Don't know how to compile code for this computer.");
    }
}

// %%
ComputerV1 myPc = new ComputerV1(ComputerType.COMPUTER_TYPE_PC);
ComputerV1 myMac = new ComputerV1(ComputerType.COMPUTER_TYPE_MAC);
ComputerV1 myChromebook = new ComputerV1(ComputerType.COMPUTER_TYPE_CHROMEBOOK);

// %%
compileCode(myPc);
compileCode(myMac);
compileCode(myChromebook);

// %%
public interface ComputerV2 {
    void compileCode();
}

// %%
public class PC implements ComputerV2 {
    @Override
    public void compileCode() {
        System.out.println("Compiling code for PC.");
    }
}

// %%
public class Mac implements ComputerV2 {
    @Override
    public void compileCode() {
        System.out.println("Compiling code for Mac.");
    }
}

// %%
public class Chromebook implements ComputerV2 {
    @Override
    public void compileCode() {
        System.out.println("Compiling code for Chromebook.");
    }
}

// %%
public ComputerV2 createComputerV2(ComputerType type) {
    switch (type) {
        case COMPUTER_TYPE_PC:
            return new PC();
        case COMPUTER_TYPE_MAC:
            return new Mac();
        case COMPUTER_TYPE_CHROMEBOOK:
            return new Chromebook();
        default:
            throw new IllegalArgumentException("Don't know how to create computer of type " + type + ".");
    }
}

// %%
ComputerV2 myPcV2 = createComputerV2(ComputerType.COMPUTER_TYPE_PC);
ComputerV2 myMacV2 = createComputerV2(ComputerType.COMPUTER_TYPE_MAC);
ComputerV2 myChromebookV2 = createComputerV2(ComputerType.COMPUTER_TYPE_CHROMEBOOK);

// %%
myPcV2.compileCode();
myMacV2.compileCode();
myChromebookV2.compileCode();

// %%
public interface Compiler {
    void compileCode();
}

// %%
public class PCCompiler implements Compiler {
    @Override
    public void compileCode() {
        System.out.println("Compiling code for PC.");
    }
}

// %%
public class MacCompiler implements Compiler {
    @Override
    public void compileCode() {
        System.out.println("Compiling code for Mac.");
    }
}

// %%
public class ChromebookCompiler implements Compiler {
    @Override
    public void compileCode() {
        System.out.println("Compiling code for Chromebook.");
    }
}

// %%
public class ComputerV3 {
    private Compiler compiler;

    public ComputerV3(Compiler compiler) {
        this.compiler = compiler;
    }

    public void compileCode() {
        compiler.compileCode();
    }
}

// %%
public ComputerV3 createComputerV3(ComputerType type) {
    switch (type) {
        case COMPUTER_TYPE_PC:
            return new ComputerV3(new PCCompiler());
        case COMPUTER_TYPE_MAC:
            return new ComputerV3(new MacCompiler());
        case COMPUTER_TYPE_CHROMEBOOK:
            return new ComputerV3(new ChromebookCompiler());
        default:
            throw new IllegalArgumentException("Don't know how to create computer of type " + type + ".");
    }
}

// %%
ComputerV3 myPcV3 = createComputerV3(ComputerType.COMPUTER_TYPE_PC);
ComputerV3 myMacV3 = createComputerV3(ComputerType.COMPUTER_TYPE_MAC);
ComputerV3 myChromebookV3 = createComputerV3(ComputerType.COMPUTER_TYPE_CHROMEBOOK);

// %%
myPcV3.compileCode();
myMacV3.compileCode();
myChromebookV3.compileCode();
