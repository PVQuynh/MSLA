/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msla;

/**
 *
 * @author acer
 */
public class Cost {

    Node a;
    Node b;
    double value;

    public Node getA() {
        return a;
    }

    public void setA(Node a) {
        this.a = a;
    }

    public Node getB() {
        return b;
    }

    public void setB(Node b) {
        this.b = b;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void printCost() {
        System.out.println("Node a:" + getA());
        a.printNode();
        System.out.println("Node b:" + getB());
        b.printNode();
        int value = (int) getValue();
        System.out.println("Value:" + value);
        System.out.println("Value 1:" + value*0.2);
        System.out.println("Value 2:" + value*0.3);
        System.out.println("Value 3:" + value*0.5);
        System.out.println("---------------");
        return;
    }
}
