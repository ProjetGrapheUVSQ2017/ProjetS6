import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Interface extends JComponent {

    private static int largeur = 640;
    private static int hauteur = 480;
    private ArrayList<Sommet> SommetSelec;
    private Point ptSouris;
    private Rectangle rectangleSouris;
    private boolean selectionEnCours;
    private MenuPanel control = new MenuPanel();
    private static Graphe graphe;
    private int rayon_sommet = 15;

    public static void main(String[] args) throws Exception{
        JFrame f = new JFrame("Graphe");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Interface inter = new Interface();
        f.add(inter.control, BorderLayout.NORTH);
        f.add(new JScrollPane(inter), BorderLayout.CENTER);
        f.setPreferredSize(new Dimension(largeur,hauteur));
        f.pack();
        f.setVisible(true);

        inter.run();
    }

    public Interface(){
        this.setOpaque(true);

    }

    public void run(){
        graphe = new GrapheListe();
        graphe.addSommet(new Point(50,50));
        graphe.addSommet(new Point(250,50));
        graphe.addSommet(new Point(50,350));
        graphe.addSommet(new Point(250,350));

        graphe.addArc(graphe.getSommet(0),graphe.getSommet(1));
        graphe.addArc(graphe.getSommet(0),graphe.getSommet(3));
        graphe.addArc(graphe.getSommet(3),graphe.getSommet(1));
        graphe.addArc(graphe.getSommet(0),graphe.getSommet(2));
        graphe.addArc(graphe.getSommet(3),graphe.getSommet(2));
        graphe.addArc(graphe.getSommet(1),graphe.getSommet(2));


    }

    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());


        for(Sommet s : graphe.get_liste_de_sommet()){
            g.setColor(s.getCouleur());
            g.fillOval(s.getPoint().x-rayon_sommet, s.getPoint().y-rayon_sommet, rayon_sommet*2,rayon_sommet*2);
        }
        for(Arc a : graphe.get_liste_arc()){
            g.setColor(a.getCouleur());

        int x1 = a.getSommetDepart().getPoint().x;
        int y1 = a.getSommetDepart().getPoint().y;
        int x2 = a.getSommetArrivee().getPoint().x;
        int y2 = a.getSommetArrivee().getPoint().y;

            drawArrow(g, x1, y1, x2, y2);;
        }

    }

    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        int ARR_SIZE = 12;
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy) - rayon_sommet;
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(rayon_sommet, 0, len, 0);
        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len}, new int[] {0, -ARR_SIZE/2, ARR_SIZE/2, 0}, 4);
    }

    public class MenuPanel extends JToolBar {
        private Action action_dsatur = new dsaturAction("Dsatur");
        private JButton dsatur = new JButton(action_dsatur);
        private JComboBox kindCombo = new JComboBox();
        private JPopupMenu popup = new JPopupMenu();


        MenuPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setBackground(Color.lightGray);

            this.add(dsatur);
        /* Pour me souvenir du menu popup
        popup.add(new JMenuItem(newNode));
        popup.add(new JMenuItem(color));
        popup.add(new JMenuItem(connect));
        popup.add(new JMenuItem(delete));
        JMenu subMenu = new JMenu("Kind");
        for (Kind k : Kind.values()) {
            kindCombo.addItem(k);
            subMenu.add(new JMenuItem(new KindItemAction(k)));
        }
        popup.add(subMenu);
        kindCombo.addActionListener(kind);
        */
        }

    }

    public class dsaturAction extends AbstractAction{
        public dsaturAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.dsatur();
            repaint();
        }
    }
}
