import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

public class Interface extends JComponent {

    private static int largeur = 1000;
    private static int hauteur = 600;
    private ArrayList<Sommet> SommetSelec = new ArrayList<Sommet>();
    private Point ptSouris;
    private Rectangle rectangleSouris = new Rectangle();
    private boolean selectionEnCours;
    private MenuPanel control = new MenuPanel();
    private static Graphe graphe;
    private static int rayon_sommet = 15;
    private Sommet sommet_selection;
    private JFrame f = new JFrame("Graphe");
    private JComboBox modeMouse;

    public static void main(String[] args) throws Exception{
        EventQueue.invokeLater(new Runnable() {

            public void run(){

                Interface inter = new Interface();
                graphe = new GrapheListe();
                graphe.addSommet(new Point(50,50));
                graphe.addSommet(new Point(250,50));
                graphe.addSommet(new Point(50,350));
                graphe.addSommet(new Point(250,350));


                graphe.addArc(graphe.getSommet(0),graphe.getSommet(2));
                graphe.addArc(graphe.getSommet(0),graphe.getSommet(1));
                graphe.addArc(graphe.getSommet(0),graphe.getSommet(3));
                graphe.addArc(graphe.getSommet(1),graphe.getSommet(3));
                graphe.addArc(graphe.getSommet(2),graphe.getSommet(3));
                //graphe.kruskall();
          

                graphe.dijkstra(graphe.getSommet(0), graphe.getSommet(3));
            }

        });
    }

    public Interface(){
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this.control,BorderLayout.NORTH);
        f.add(new JScrollPane(this),BorderLayout.CENTER);
        f.setPreferredSize(new Dimension(largeur, hauteur));
        f.pack();
        f.setVisible(true);

        this.setOpaque(true);

        this.addMouseListener(new GestionSouris());
        this.addMouseMotionListener(new SelectionViaClic());

    }

    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());


        for(Sommet s : graphe.get_liste_de_sommet()){
            g.setColor(s.getCouleur());
            g.fillOval(s.getPoint().x-rayon_sommet, s.getPoint().y-rayon_sommet, rayon_sommet*2,rayon_sommet*2);
            int i =0;
            for(Variable v : s.getList()){
                g.setColor(Color.DARK_GRAY);
                g.drawString(v.toString(),s.getPoint().x, s.getPoint().y+rayon_sommet+12+(i*12));
                i++;
            }
        }
        for(Arc a : graphe.get_liste_arc()){
            g.setColor(a.getCouleur());

            int x1 = a.getSommetDepart().getPoint().x;
            int y1 = a.getSommetDepart().getPoint().y;
            int x2 = a.getSommetArrivee().getPoint().x;
            int y2 = a.getSommetArrivee().getPoint().y;

            drawArrow(g, x1, y1, x2, y2, a);
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        for(Sommet s : SommetSelec){
            g2.setColor(Color.GRAY);
            g2.drawRect(s.getPoint().x-rayon_sommet, s.getPoint().y-rayon_sommet, rayon_sommet*2, rayon_sommet*2);
        }

        g.setColor(Color.darkGray);
        g.drawRect(rectangleSouris.x, rectangleSouris.y, rectangleSouris.width, rectangleSouris.height);


    }

    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2, Arc a) {
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
        int dx_draw_value;
        if(dx>0){
            dx_draw_value=15;
        }
        else{
            dx_draw_value=-15;
        }

        int i =0;
        for(Variable v : a.getList()){
            g.setColor(Color.DARK_GRAY);
            if(i==0){
                g.setFont(new Font(g.getFont().getName(),Font.BOLD,dx_draw_value));
            }
            else{
                g.setFont(new Font(g.getFont().getName(),Font.PLAIN,dx_draw_value));
            }
            g.drawString(v.toString(),len/2, 20+(i*dx_draw_value));
            i++;
        }
    }

    private class GestionSouris extends MouseAdapter {
        Point depart;
        Point arrive;

        @Override
        public void mousePressed(MouseEvent e) {
            depart = e.getPoint();
            ptSouris = e.getPoint();
            if (e.isPopupTrigger()) {
                showPopup(e);
            }
            else if(getSommetFromPoint(ptSouris)!=null){
                if(modeMouse.getSelectedItem()=="Selection"){
                    SommetSelec.clear();
                    SommetSelec.add(getSommetFromPoint(ptSouris));
                }
                else if(modeMouse.getSelectedItem()=="Sommet"){
                    if(SommetSelec.isEmpty()){
                        SommetSelec.clear();
                        SommetSelec.add(getSommetFromPoint(ptSouris));
                    }
                    if(SommetSelec.size()==1){
                        SommetSelec.clear();
                        SommetSelec.add(getSommetFromPoint(ptSouris));
                    }
                }
            }else{
                if(modeMouse.getSelectedItem()=="Selection"){
                    SommetSelec.clear();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            arrive = e.getPoint();
            if (e.isPopupTrigger()) {
                showPopup(e);
            }
            if(modeMouse.getSelectedItem()=="Arcs"){
                if(getSommetFromPoint(depart) != getSommetFromPoint(arrive)){
                    graphe.addArc(getSommetFromPoint(depart), getSommetFromPoint(arrive));
                    repaint();
                }
            }
            if(modeMouse.getSelectedItem()=="Selection"){
                rectangleSouris = new Rectangle();
                repaint();
            }
        }

        private void showPopup(MouseEvent e) {
            control.popup.show(e.getComponent(), e.getX(), e.getY());
        }

    }

    private class SelectionViaClic extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            Point p = new Point();
            if(modeMouse.getSelectedItem()=="Sommet"){
                p.setLocation(e.getX() - ptSouris.x, e.getY() - ptSouris.y);
                for(Sommet s: SommetSelec){
                    s.getPoint().x += p.x;
                    s.getPoint().y += p.y;
                }
                ptSouris = e.getPoint();
                repaint();
            }
            if(modeMouse.getSelectedItem()=="Selection"){
                SommetSelec.clear();
                rectangleSouris.setBounds(Math.min(ptSouris.x, e.getX()), Math.min(ptSouris.y, e.getY()), Math.abs(ptSouris.x - e.getX()), Math.abs(ptSouris.y - e.getY()));
                for (Sommet s : graphe.get_liste_de_sommet()) {
                    if(rectangleSouris.contains(s.getPoint())){
                        if(!SommetSelec.contains(s)){
                            SommetSelec.add(s);
                        }
                    }
                }
                repaint();
            }
        }
    }

    public class ModeMouseComboBox implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(modeMouse.getSelectedItem()=="Arcs"){
                SommetSelec.clear();
                repaint();
            }
        }
    }

    class MenuPanel extends JMenuBar {
        private Action action_dsatur = new dsaturAction("Dsatur");
        private Action action_dijkstra = new dijkstraAction("Dijkstra");
        private Action action_bellman_ford = new bellman_fordAction("Bellman Ford");
        private Action action_ford_fulkerson = new ford_fulkersonAction("Ford Fulkerson");
        private Action action_kruskall = new kruskallAction("Kruskall");
        private Action action_welsh_powell = new welsh_powellAction("Welsh Powell");
        private Action action_kosaraju = new kosarajuAction("Kosaraju");
        private Action action_tarjan = new tarjanAction("Tarjan");

        private Action action_supression_totale = new suppressionTotaleAction("Tout Supprimer");
        private Action action_ouvrir = new ouvrirAction("Charger");
        private Action action_sauvegarder = new sauvegarderAction("Sauvegarder");
        private Action action_creerSommet = new creerSommetAction("Creer Sommet");
        private Action action_supprimerSommet = new supprimerSommetAction("Supprimer");
        private Action action_ModifierVariable = new modifierVariableAction("Modifier Variable");
        private Action action_CreerSousGraphe = new creerSousGrapheAction("Creer Sous Graphe");

        private JButton dsatur = new JButton(action_dsatur);
        private JButton dijkstra = new JButton(action_dijkstra);
        private JButton bellman_ford = new JButton(action_bellman_ford);
        private JButton ford_fulkerson = new JButton(action_ford_fulkerson);
        private JButton kruskall = new JButton(action_kruskall);
        private JButton welsh_powell = new JButton(action_welsh_powell);
        private JButton kosaraju = new JButton(action_kosaraju);
        private JButton tarjan = new JButton(action_tarjan);



        private JButton suppression_totale = new JButton(action_supression_totale);
        private JButton ouvrir = new JButton(action_ouvrir);
        private JButton sauvegarder = new JButton(action_sauvegarder);
        private JButton sousGraphe = new JButton(action_CreerSousGraphe);
        private JPopupMenu popup = new JPopupMenu();


        MenuPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setBackground(Color.lightGray);

            this.add(dsatur);
            this.add(suppression_totale);
            this.add(ouvrir);
            this.add(sauvegarder);
            this.add(sousGraphe);
            this.add(welsh_powell);

            this.popup.add(new JMenuItem(action_creerSommet));
            this.popup.add(new JMenuItem(action_supprimerSommet));
            this.popup.add(new JMenuItem(action_ModifierVariable));


            this.add(new JLabel("Mode d'édition"));
            String[] modeMouseString = { "Selection", "Arcs", "Sommet"};
            modeMouse = new JComboBox(modeMouseString);
            this.add(modeMouse);
            modeMouse.addActionListener(new ModeMouseComboBox());
        }

    }

    class creerSommetAction extends AbstractAction{
        public creerSommetAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.addSommet(ptSouris.getLocation());
            repaint();
        }
    }

    class supprimerSommetAction extends AbstractAction{
        public supprimerSommetAction(String name){super(name);}

        public void actionPerformed(ActionEvent e){
            Sommet s = getSommetFromPoint(ptSouris);
            Arc a = getArcFromPoint(ptSouris);

            if(s!=null){
                    if(SommetSelec.contains(s)){
                        SommetSelec.remove(s);
                    }
                    graphe.deleteSommet(s.getId());
                    repaint();
            }
            else if(a!=null){
                graphe.deleteArc(a.getId());
                repaint();
            }

        }
    }

    class dsaturAction extends AbstractAction{
        public dsaturAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.dsatur();
            repaint();
        }
    }

    class dijkstraAction extends AbstractAction{
        public dijkstraAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    class bellman_fordAction extends AbstractAction{
        public bellman_fordAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    class ford_fulkersonAction extends AbstractAction{
        public ford_fulkersonAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    class kruskallAction extends AbstractAction{
        public kruskallAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    class welsh_powellAction extends AbstractAction{
        public welsh_powellAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
        	graphe.welsh_powell();
            repaint();
        }
    }

    class kosarajuAction extends AbstractAction{
        public kosarajuAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    class tarjanAction extends AbstractAction{
        public tarjanAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }

    class modifierVariableAction extends AbstractAction{
        public modifierVariableAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {

            final JFrame VariableWindow = new JFrame();
            VariableWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            VariableWindow.setLocation(ptSouris);

            VariableWindow.setTitle("Modification des Variables");

            Boolean sommet=false;
            Boolean ne_rien_faire = false;
            final Sommet s = getSommetFromPoint(ptSouris);
            final Arc a = getArcFromPoint(ptSouris);

            if(s!=null){
                sommet=true;
            }
            else if(a!=null){
                sommet=false;
            }else{
                VariableWindow.setVisible(false); //you can't see me!
                VariableWindow.dispose();
                ne_rien_faire=true;
            }

            if(!ne_rien_faire) {

                final ArrayList<TextField> liste_input = new ArrayList<TextField>();

                if(sommet){
                    VariableWindow.setSize(300, s.getList().size() * 65 + 200);
                    for (Variable v : s.getList()) {
                        liste_input.add(new TextField(v.toString()));
                    }
                }else{
                    VariableWindow.setSize(300, a.getList().size() * 65 + 200);
                    for (Variable v : a.getList()) {
                        liste_input.add(new TextField(v.toString()));
                    }
                }



                JPanel container = new JPanel();
                container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

                final JPanel p1 = new JPanel(new GridBagLayout());

                final GridBagConstraints gbc = new GridBagConstraints();
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.insets = new Insets(10, 10, 0, 10);


                for (int i = 0; i < liste_input.size(); i++) {
                    if(sommet){
                        p1.add(new JLabel("Variable " + (i + 1) + " (" + s.getList().get(i).getTypeVar() + ") : "), gbc);
                    }else{
                        if(i==0){
                            p1.add(new JLabel("Poids " + " (" + a.getList().get(i).getTypeVar() + ") : "), gbc);
                        }else{
                            p1.add(new JLabel("Variable " + (i + 1) + " (" + a.getList().get(i).getTypeVar() + ") : "), gbc);
                        }
                    }

                    p1.add(liste_input.get(i), gbc);
                }

                JButton addVariableInt = new JButton("Ajouter une Variable(int)");
                JButton addVariableFloat = new JButton("Ajouter une Variable(float)");
                JButton addVariableString = new JButton("Ajouter une Variable(string)");
                JButton terminer = new JButton("Terminer");

                addVariableInt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(s!=null){
                            s.addVar(new VarInt(0));
                            liste_input.add(new TextField(s.getVar(s.getList().size() - 1).toString()));
                            p1.add(new JLabel("Variable " + liste_input.size() + " (" + s.getList().get(liste_input.size() - 1).getTypeVar() + ") : "), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, s.getList().size() * 65 + 200);
                        }
                        else{
                            a.addVar(new VarInt(0));
                            liste_input.add(new TextField(a.getVar(a.getList().size() - 1).toString()));
                            p1.add(new JLabel("Variable " + liste_input.size() + " (" + a.getList().get(liste_input.size() - 1).getTypeVar() + ") : "), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, a.getList().size() * 65 + 200);
                        }
                    }
                });

                addVariableFloat.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(s!=null){
                            s.addVar(new VarFloat(0));
                            liste_input.add(new TextField(s.getVar(s.getList().size() - 1).toString()));
                            p1.add(new JLabel("Variable " + liste_input.size() + " (" + s.getList().get(liste_input.size() - 1).getTypeVar() + ") : "), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, s.getList().size() * 65 + 200);
                        }
                        else{
                            a.addVar(new VarFloat(0));
                            liste_input.add(new TextField(a.getVar(a.getList().size() - 1).toString()));
                            p1.add(new JLabel("Variable " + liste_input.size() + " (" + a.getList().get(liste_input.size() - 1).getTypeVar() + ") : "), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, a.getList().size() * 65 + 200);
                        }
                    }
                });

                addVariableString.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(s!=null){
                            s.addVar(new VarString(" "));
                            liste_input.add(new TextField(s.getVar(s.getList().size() - 1).toString()));
                            p1.add(new JLabel("Variable " + liste_input.size() + " (" + s.getList().get(liste_input.size() - 1).getTypeVar() + ") : "), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, s.getList().size() * 65 + 200);
                        }
                        else{
                            a.addVar(new VarString(" "));
                            liste_input.add(new TextField(a.getVar(a.getList().size() - 1).toString()));
                            p1.add(new JLabel("Variable " + liste_input.size() + " (" + a.getList().get(liste_input.size() - 1).getTypeVar() + ") : "), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, a.getList().size() * 65 + 200);
                        }
                    }
                });

                terminer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        for (int i = 0; i < liste_input.size(); i++) {
                            if (s != null) {
                                if (s.getVar(i).getTypeVar() == "Int") {
                                    s.setVar(i, new VarInt(Integer.parseInt(liste_input.get(i).getText())));
                                }
                                if (s.getVar(i).getTypeVar() == "Float") {
                                    s.setVar(i, new VarFloat(Float.valueOf(liste_input.get(i).getText())));
                                }
                                if (s.getVar(i).getTypeVar() == "String") {
                                    s.setVar(i, new VarString(liste_input.get(i).getText()));
                                }
                            }
                            else{
                                if (a.getVar(i).getTypeVar() == "Int") {
                                    a.setVar(i, new VarInt(Integer.parseInt(liste_input.get(i).getText())));
                                }
                                if (a.getVar(i).getTypeVar() == "Float") {
                                    a.setVar(i, new VarFloat(Float.valueOf(liste_input.get(i).getText())));
                                    if(i==0){
                                        a.getVar(i).setPoids(true);
                                    }
                                }
                                if (a.getVar(i).getTypeVar() == "String") {
                                    a.setVar(i, new VarString(liste_input.get(i).getText()));
                                }
                            }

                        }
                        repaint();
                        VariableWindow.setVisible(false);
                        VariableWindow.dispose();
                    }
                });

                VariableWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        for (int i = 0; i < liste_input.size(); i++) {
                            if (s != null) {
                                if (s.getVar(i).getTypeVar() == "Int") {
                                    s.setVar(i, new VarInt(Integer.parseInt(liste_input.get(i).getText())));
                                }
                                if (s.getVar(i).getTypeVar() == "Float") {
                                    s.setVar(i, new VarFloat(Float.valueOf(liste_input.get(i).getText())));
                                }
                                if (s.getVar(i).getTypeVar() == "String") {
                                    s.setVar(i, new VarString(liste_input.get(i).getText()));
                                }
                            }
                            else{
                                if (a.getVar(i).getTypeVar() == "Int") {
                                    a.setVar(i, new VarInt(Integer.parseInt(liste_input.get(i).getText())));
                                }
                                if (a.getVar(i).getTypeVar() == "Float") {
                                    a.setVar(i, new VarFloat(Float.valueOf(liste_input.get(i).getText())));
                                    if(i==0){
                                        a.getVar(i).setPoids(true);
                                    }
                                }
                                if (a.getVar(i).getTypeVar() == "String") {
                                    a.setVar(i, new VarString(liste_input.get(i).getText()));
                                }
                            }
                        }
                        repaint();
                        VariableWindow.setVisible(false);
                        VariableWindow.dispose();
                    }
                });

                JPanel p2 = new JPanel(new GridBagLayout());

                p2.add(addVariableInt, gbc);
                p2.add(addVariableFloat, gbc);
                p2.add(addVariableString, gbc);
                p2.add(terminer, gbc);


                container.add(p1);
                container.add(p2);

                VariableWindow.add(container);


                VariableWindow.setVisible(true);
                repaint();
            }
        }
    }

    class suppressionTotaleAction extends AbstractAction{
        public suppressionTotaleAction(String name){
            super(name);
        }

        public void actionPerformed(ActionEvent e){
            graphe.get_liste_de_sommet().clear();
            graphe.get_liste_arc().clear();
            graphe.setNbSommets(0);
            graphe.setNbArcs(0);
            SommetSelec.clear();
            repaint();
        }
    }

    class ouvrirAction extends AbstractAction{
        public ouvrirAction(String name) {
            super(name);
        }
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Graphe", "graphe");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(Interface.this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                graphe = Graphe.charger(file);
                repaint();
            }
        }
    }

    class sauvegarderAction extends AbstractAction{
        public sauvegarderAction(String name) {
            super(name);
        }
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Graphe", "graphe");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showSaveDialog(Interface.this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = new File(chooser.getSelectedFile() + ".graphe");
                graphe.sauvegarder(file);
                repaint();
            }
        }
    }

    class creerSousGrapheAction extends AbstractAction{
        public creerSousGrapheAction(String name) {
            super(name);
        }
        public void actionPerformed(ActionEvent e) {
            if(!SommetSelec.isEmpty()){
                ListIterator<Sommet> i = graphe.get_liste_de_sommet().listIterator();
                while (i.hasNext()) {
                    Sommet s = i.next();
                    if(!SommetSelec.contains(s)){
                        i.remove();
                        graphe.setNbSommets(graphe.getNbSommets()-1);
                    }
                }
                ListIterator<Arc> j = graphe.get_liste_arc().listIterator();
                while (j.hasNext()) {
                    Arc a = j.next();
                    if(!SommetSelec.contains(a.getSommetDepart()) || !SommetSelec.contains(a.getSommetArrivee())){
                        j.remove();
                        graphe.setNbArcs(graphe.getNbArcs()-1);
                    }
                }
                SommetSelec.clear();
                repaint();
            }
        }
    }

    private static boolean isPointInSommet(Point p, Sommet s){
        if(Math.pow(p.x - s.getPoint().x, 2) + Math.pow(p.y - s.getPoint().y, 2) < Math.pow(rayon_sommet, 2)){
                return true;
            }
        return false;
    }

    private static Sommet getSommetFromPoint(Point p){
        for(Sommet s : graphe.get_liste_de_sommet()){
            if(isPointInSommet(p, s)){
                return s;
            }
        }
        return null;
    }

    private static Arc getArcFromPoint(Point p){
        for(Arc a : graphe.get_liste_arc()){
            Line2D l = new Line2D.Double(a.getSommetDepart().getPoint(), a.getSommetArrivee().getPoint());
            if(l.ptLineDist(p)<20){
                return a;
            }
        }
        return null;
    }
}
