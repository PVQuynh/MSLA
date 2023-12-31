package msla;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;

public class MSLAProject extends JFrame {

    Random random = new Random(180);

    // 60 nut
    public List<Node> genNode80() {
        List<Node> lstN = new ArrayList<Node>();
        for (int i = 0; i < 80; i++) {
            boolean checkNoDup = false;
            int x = 0;
            int y = 0;
            Node n = new Node();
            while (!checkNoDup) {
                x = (int) ((random.nextFloat() * ((1001 - 100) + 1)) + 100); // 100 -> 1000
                y = (int) ((random.nextFloat() * ((881 - 200) + 1)) + 200); // 200 -> 881
                checkNoDup = noDup(x, y, lstN);
                n.setSTT("" + (i + 1));
                n.setX(x);
                n.setY(y);
                n.setW(1);
                n.setNutCha("0");
                lstN.add(n);
            }
        }

        // trong so
        getFromSTT("1", lstN).setW(2);
        getFromSTT("18", lstN).setW(2);
        getFromSTT("48", lstN).setW(2);

        getFromSTT("17", lstN).setW(12);
        getFromSTT("25", lstN).setW(12);
        getFromSTT("39", lstN).setW(12);

        getFromSTT("4", lstN).setW(8);
        getFromSTT("43", lstN).setW(8);
        getFromSTT("55", lstN).setW(8);

        getFromSTT("7", lstN).setW(3);
        getFromSTT("55", lstN).setW(3);
        getFromSTT("78", lstN).setW(3);

        getFromSTT("10", lstN).setSTT("0");
        for (int i = 0; i < lstN.size(); i++) {
            // lstN.get(i).printNode();
            if (!lstN.get(i).getSTT().equals("0")) {
                getFromSTT("0", lstN).getNutCon().add(lstN.get(i).getSTT());
            }
        }
        // for (int i = 0; i < lstN.size()-1; i++) {
        // for (int j = i+1; j < lstN.size(); j++) {
        // calCost(lstN.get(i),lstN.get(j)).printCost();
        // }
        // }
            System.out.println(lstN);
        return lstN;
    }

    public Cost calCost(Node a, Node b) {
        Cost cost = new Cost();
        int deltaX = a.getX() - b.getX();
        int deltaY = a.getY() - b.getY();
        double dist = Math.sqrt(pow(deltaX, 2) + pow(deltaY, 2));

        cost.setA(a);
        cost.setB(b);
        cost.setValue(dist);

        return cost;
    }

    public boolean noDup(int x, int y, List<Node> lstN) {
        for (int i = 0; i < lstN.size(); i++) {
            if (x == lstN.get(i).getX() && y == lstN.get(i).getY()) {
                return false;
            }
        }
        return true;
    }

    public Node getFromSTT(String STT, List<Node> lstN) {
        for (Node n : lstN) {
            if (n.getSTT().equals(STT)) {
                return n;
            }
        }
        return null;
    }

    public void showS(List<String> lstS) {
        for (int i = 0; i < lstS.size(); i++) {
            System.out.print(lstS.get(i) + ", ");
        }
        System.out.println("end");
    }

    public List<String> findChildNode(String n, List<Node> lstN) {
        List<String> lstNutCon = new ArrayList<>();
        lstNutCon.addAll(getFromSTT(n, lstN).getNutCon());
        List<String> tempRound = new ArrayList();
        tempRound.addAll(getFromSTT(n, lstN).getNutCon());
        boolean end = false;
        while (!end) {
            end = true;
            List<String> lstTemp = new ArrayList();
            for (int i = 0; i < tempRound.size(); i++) {
                if (!getFromSTT(tempRound.get(i), lstN).getNutCon().isEmpty()) {
                    lstTemp.addAll(getFromSTT(tempRound.get(i), lstN).getNutCon());
                    end = false;
                }
            }
            tempRound.clear();
            tempRound.addAll(lstTemp);
            lstNutCon.addAll(lstTemp);
        }
        return lstNutCon;
    }

    public List<String> findParent(String a, List<Node> lstN) {
        List<String> pre = new ArrayList<>();
        String temp = a;
        while (!temp.equals("0")) {
            pre.add(getFromSTT(temp, lstN).getNutCha());
            temp = getFromSTT(temp, lstN).getNutCha();
        }
        return pre;
    }

    public void path00(String a, String b, List<Node> lstN) {
        List<String> lstChaA = new ArrayList<>();

        lstChaA.addAll(findParent(a, lstN)); // lstChaA = [m,....,0]
        Collections.reverse(lstChaA); // lstChaA = [0,.....,m]
        lstChaA.add(a); // lstChaA = [0,n,...,m,a]
        lstChaA.add(b); // lstChaA = [0,n,...,m,a,b]

        getFromSTT("0", lstN).getNutCon().remove(lstChaA.get(1));

        // showS(getFromSTT("0", lstN).getNutCon());
        lstChaA.remove(0); // lstChaA = [n,...m,a,b]
        // showS(lstChaA);
        for (int i = 0; i < lstChaA.size(); i++) {
            if (i == 0) {

                getFromSTT(lstChaA.get(i), lstN).setNutCha(lstChaA.get(i + 1));

                getFromSTT(lstChaA.get(i), lstN).getNutCon().remove(lstChaA.get(i + 1));
            } else if (i == lstChaA.size() - 1) {

                getFromSTT(lstChaA.get(i), lstN).getNutCon().add(lstChaA.get(i - 1));
            } else {

                getFromSTT(lstChaA.get(i), lstN).setNutCha(lstChaA.get(i + 1));

                getFromSTT(lstChaA.get(i), lstN).getNutCon().remove(lstChaA.get(i + 1));

                getFromSTT(lstChaA.get(i), lstN).getNutCon().add(lstChaA.get(i - 1));
            }
        }
    }

    public int calW(Link l, List<Node> lstN) {
        int w = l.getA().getW();
        // List<Node> lstNutConN = timAnc(l.getA());
        List<String> lstNutCon = new ArrayList();
        lstNutCon.addAll(findChildNode(l.getA().getSTT(), lstN));
        for (int i = 0; i < lstNutCon.size(); i++) {
            w += getFromSTT(lstNutCon.get(i), lstN).getW();
        }
        return w;
    }

    // lien ket
    public double heSo(int w) {
        if (w <= 3) {
            return 0.2;
        } else if (3 < w && w <= 10) {
            return 0.4;
        } else if (10 < w && w <= 25) {
            return 0.6;
        }
        // câu 2
//         else if (25 < w && w <= 50) {
//             return 0.8;
//         }

        return 0;
    }

    public double calAllCost(List<Node> lstN) {
        double temp = 0;
        List<Link> lstLink = new ArrayList<>();
        lstLink.addAll(getLink(lstN));
        for (Link l : lstLink) {
            temp += C(l, lstN);
        }
        return temp;
    }

    public double C(Link l, List<Node> lstN) {
        int w = calW(l, lstN);
        double C = l.getDist() * heSo(w);
        return C;
    }

    public List<Link> getLink(List<Node> lstN) {
        List<Link> lstLink = new ArrayList<>();
        for (int i = 0; i < lstN.size(); i++) {
            if (!lstN.get(i).getSTT().equals("0")) {
                Link l = new Link();
                Node a = new Node(lstN.get(i));
                Node b = new Node(getFromSTT(lstN.get(i).getNutCha(), lstN));
                l.setA(a);
                l.setB(b);
                int deltaX = a.getX() - b.getX();
                int deltaY = a.getY() - b.getY();
                double dist = Math.sqrt(pow(deltaX, 2) + pow(deltaY, 2));
                l.setDist(dist);
                int W = calW(l, lstN);
                l.setW(W);
                l.setName("L" + a.getSTT() + "(" + b.getSTT() + ")");
                lstLink.add(l);
            }
        }
        return lstLink;
    }

    public String nutGanGoc(Node a, List<Node> lstN) {
        List<String> lstNode = new ArrayList<>();
        lstNode.addAll(findParent(a.getSTT(), lstN));
        lstNode.add(a.getSTT());
        for (String n : lstNode) {

            if (!n.equals("0")) {
                if (getFromSTT(n, lstN).getNutCha().equals("0")) {
                    return n;
                }
            }
        }
        return null;
    }

    public Cost tinhThoaHiep(String a, String b, List<Node> lstN) {
        List<Node> temp = new ArrayList<>();
        for (int i = 0; i < lstN.size(); i++) {
            temp.add(new Node(lstN.get(i)));
            for (String t : lstN.get(i).getNutCon()) {
                temp.get(i).getNutCon().add(t);
            }
        }
        path00(a, b, temp);
        Cost cost = new Cost();
        String n = nutGanGoc(getFromSTT(b, temp), temp);
        int w = 0;
        List<Node> lstNutCon = new ArrayList<>();
        List<String> lstString = findChildNode(n, temp);
        for (int i = 0; i < findChildNode(n, temp).size(); i++) {
            lstNutCon.add(getFromSTT(findChildNode(n, temp).get(i), temp));
        }

        
        for (int i = 0; i < lstNutCon.size(); i++) {
            w += lstNutCon.get(i).getW();
        }
        w += getFromSTT(n, lstN).getW();

        // Câu 1
        if (w > 25) {
            // System.out.println("W > 16 nen end");
            return null;
        }

        //Câu 2
//         if (w > 50) {
//             // System.out.println("W > 32 nen end");
//             return null;
//         }

        double value = calAllCost(temp);
        cost.setValue(value);
        cost.setA(getFromSTT(a, temp));
        cost.setB(getFromSTT(b, temp));
        // cost.printCost();
        return cost;
    }

    public boolean hetThuatToan(double costCu, List<Cost> lstCost) {
        for (Cost c : lstCost) {
            if (c.getValue() < costCu) {
                return true;
            }
        }
        return false;
    }

    public Cost min(List<Cost> lstCost) {
        Cost costMin = new Cost();
        if (!lstCost.isEmpty()) {
            double min = lstCost.get(0).getValue();
            costMin = lstCost.get(0);
            for (Cost c : lstCost) {
                if (c.getValue() < min) {
                    costMin = c;
                    min = c.getValue();
                }
            }
        }
        return costMin;
    }

    public boolean isChild(String a, String b, List<Node> lstN) {
        List<String> lstNutCon = new ArrayList<>();
        lstNutCon.addAll(findChildNode(b, lstN));
        for (String nutCon : lstNutCon) {
            if (nutCon.equals(a)) {
                return true;
            }
        }
        return false;
    }

    public boolean isParent(String a, String b, List<Node> lstN) {
        List<String> lstNutCha = new ArrayList<>();
        lstNutCha.addAll(findParent(b, lstN));
        for (String nutCha : lstNutCha) {
            if (nutCha.equals(a)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFast(String a, String b, List<Node> lstN) {
        String a1 = nutGanGoc(getFromSTT(a, lstN), lstN);
        String b1 = nutGanGoc(getFromSTT(b, lstN), lstN);
        if (a1.equals(b1)) {
            return true;
        }
        return false;
    }

    public boolean condition(int i, int j, List<Node> lstN) {
        if (!lstN.get(i).getSTT().equals(lstN.get(j).getSTT())) {
            if (!lstN.get(i).getSTT().equals("0") && !lstN.get(j).getSTT().equals("0")) {
                String src = lstN.get(i).getSTT();
                String dest = lstN.get(j).getSTT();

                int condition = findChildNode(src, lstN).size() + findParent(src, lstN).size()
                        + findChildNode(dest, lstN).size() + findParent(dest, lstN).size();
                        
                if (condition <= 4) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Node> thucHienThuatToan(List<Node> lstN) {
        double init = calAllCost(lstN);
        System.out.println("cost ban dau: " + (int)init);
        boolean kTra = true;
        while (kTra) {
            List<Node> lstTemp = new ArrayList<>();
            
            List<Cost> lstCost = new ArrayList();
            
            for (int i = 0; i < lstN.size(); i++) {
                for (int j = 0; j < lstN.size(); j++) {
                    if (condition(i, j, lstN)) {
                        boolean laNutCon = isChild(lstN.get(i).getSTT(), lstN.get(j).getSTT(), lstN);
                        boolean laNutCha = isParent(lstN.get(i).getSTT(), lstN.get(j).getSTT(), lstN);
                        boolean cungNhanh = isFast(lstN.get(i).getSTT(), lstN.get(j).getSTT(), lstN);
                        if (!laNutCon) {
                            if (!laNutCha && !cungNhanh) {
                                
                                for (int k = 0; k < lstN.size(); k++) {
                                    lstTemp.add(new Node(lstN.get(k)));
                                    for (String t : lstN.get(k).getNutCon()) {
                                        lstTemp.get(k).getNutCon().add(t);
                                    }
                                }
                                Cost thoaHiep = new Cost();
                                thoaHiep = tinhThoaHiep(lstTemp.get(i).getSTT(), lstTemp.get(j).getSTT(), lstTemp);
                                if (thoaHiep == null) {
                                    lstTemp.clear();
                                    continue;
                                }
                                lstTemp.clear();
                                lstCost.add(thoaHiep);
                            }
                        }
                    }
                }
            }
            Cost temp = min(lstCost);
            int soAm = 0;
            // System.out.println("init cũ = " + init);
            for (Cost c : lstCost) {
                if (c.getValue() < init) {
                    soAm++;
                }

            }
            // System.out.println("số các th âm = " + soAm);
            kTra = hetThuatToan(init, lstCost);

            if (kTra) {
                System.out.println("noi nut " + temp.getA().getSTT() + " den nut " + temp.getB().getSTT());
                path00(temp.getA().getSTT(), temp.getB().getSTT(), lstN);
            }
            init = calAllCost(lstN);
            System.out.println("init = " +(int) init);
            System.out.println("het round");
        }
        return lstN;
    }

    public static void main(String[] args) {
        MSLAProject mslaProject = new MSLAProject();
        mslaProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Node> lstN = new ArrayList<>();
        lstN.addAll(mslaProject.genNode80());

        List<Node> ketQua = new ArrayList<>();
        ketQua.addAll(mslaProject.thucHienThuatToan(lstN));
        mslaProject.showS(mslaProject.findChildNode("0", lstN));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mslaProject.setTitle("mslaProject");
                mslaProject.setSize(1000, 1000);
                nNodePanel sub = new nNodePanel(lstN);
                mslaProject.add(sub);
                mslaProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mslaProject.setVisible(true);
            }
        });
    }

}
