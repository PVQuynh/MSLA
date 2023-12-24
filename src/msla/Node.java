/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msla;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class Node {

    private String STT; // Số thứ tự
    private int x; // toạ độ trục X
    private int y; // toạ độ trục Y
    private int w; // Tr�?ng số
    private String nutCha;
    private List<String> nutCon;

    public Node() {
        this.nutCon = new ArrayList();
    }

    public Node(Node n) {
        this.STT = n.STT;
        this.nutCha = n.nutCha;
        this.x = n.x;
        this.y = n.y;
        this.w = n.w;
        this.nutCon = new ArrayList();
    }

    public String getSTT() {
        return STT;
    }

    public void setSTT(String STT) {
        this.STT = STT;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public String getNutCha() {
        return nutCha;
    }

    public void setNutCha(String nutCha) {
        this.nutCha = nutCha;
    }

    public List<String> getNutCon() {
        return nutCon;
    }

    public boolean printNode() {
        System.out.println("STT: " + getSTT());
        System.out.println("Toa do x: "+getX());
        System.out.println("toa do y: "+getY());
        System.out.println("Trong so: "+getW());
        System.out.println("Nut cha: "+getNutCha());
        System.out.println("cac nut con: "+getNutCon());
        System.out.println("----------------");
        return false;
    }
}
