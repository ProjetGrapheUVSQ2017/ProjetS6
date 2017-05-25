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
    private boolean selectDeuxSommet = false;
    private String algo_en_cours = new String();
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
                graphe.addSommet(new Point(300,400));
                


                graphe.addArc(graphe.getSommet(0),graphe.getSommet(2));
                graphe.addArc(graphe.getSommet(0),graphe.getSommet(1));
                graphe.addArc(graphe.getSommet(0),graphe.getSommet(3));
                graphe.addArc(graphe.getSommet(1),graphe.getSommet(3));
                graphe.addArc(graphe.getSommet(2),graphe.getSommet(3));
                graphe.addArc(graphe.getSommet(1), graphe.getSommet(4));
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

        g.setColor(Color.BLACK);
        String type_graphe = new String();
        if(graphe.getClass().getName()=="GrapheListe"){
            type_graphe="Liste";
        }else{
            type_graphe="Matrice";
        }
        g.drawString("Graphe de type: "+ type_graphe, 10,20);


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
                if(selectDeuxSommet==true){
                    SommetSelec.add(getSommetFromPoint(ptSouris));
                    getSommetFromPoint(ptSouris).setCouleur(Color.RED);
                    repaint();
                    if(SommetSelec.size()==2){
                        Boolean exec;
                        selectDeuxSommet=false;
                        if(algo_en_cours=="dijkstra"){
                            exec=graphe.dijkstra(SommetSelec.get(0),SommetSelec.get(1));
                        }else if(algo_en_cours=="ford"){
                            exec=graphe.ford_fulkerson(SommetSelec.get(0),SommetSelec.get(1));
                        }else{
                            exec=graphe.bellman_ford(SommetSelec.get(0),SommetSelec.get(1));
                        }
                        if(exec==false) {
                            JOptionPane jop = new JOptionPane();
                            if (algo_en_cours == "dijkstra") {
                                Boolean neg = false;
                                for (Arc act : graphe.get_liste_arc()) {
                                    if (act.getVarPoids() < 0) {
                                        neg = true;
                                        break;
                                    }
                                }
                                if (neg) {
                                    jop.showMessageDialog(f, "Un ou plusieurs Arcs du Graphe sont négatifs (coloriés en rouge), l'algorithme de Dijkstra ne peut s'executer.", "Information", JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    jop.showMessageDialog(f, "L'algorithme n'a pas pu s'executer, il n'existe pas de chemin entre les deux Sommets sélectionnés.", "Information", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else if(algo_en_cours == "bellman") {
                                jop.showMessageDialog(f, "L'algorithme n'a pas pu s'executer, il n'existe pas de chemin entre les deux Sommets sélectionnés ou il existe un cycle de poids négatif.", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }else{
                                jop.showMessageDialog(f, "L'algorithme n'a pas pu s'executer, il n'existe pas de chemin entre les deux Sommets sélectionnés.", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        algo_en_cours = null;
                        SommetSelec.clear();
                    }
                }else{
                    if(modeMouse.getSelectedItem()=="Selection"){
                        if (e.isShiftDown()) {
                            SommetSelec.add(getSommetFromPoint(ptSouris));
                        }else{
                            SommetSelec.clear();
                            SommetSelec.add(getSommetFromPoint(ptSouris));
                        }
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
                }
            }else{
                if(modeMouse.getSelectedItem()=="Selection"){
                    SommetSelec.clear();
                }
            }
            repaint();
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
                }
            }
            if(modeMouse.getSelectedItem()=="Selection"){
                rectangleSouris = new Rectangle();

            }
            repaint();
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

            }
            repaint();
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
        private Action action_transformation = new transformationAction("Changement de type de Graphe");

        private JMenuItem dsatur = new JMenuItem(action_dsatur);
        private JMenuItem dijkstra = new JMenuItem(action_dijkstra);
        private JMenuItem bellman_ford = new JMenuItem(action_bellman_ford);
        private JMenuItem ford_fulkerson = new JMenuItem(action_ford_fulkerson);
        private JMenuItem kruskall = new JMenuItem(action_kruskall);
        private JMenuItem welsh_powell = new JMenuItem(action_welsh_powell);
        private JMenuItem kosaraju = new JMenuItem(action_kosaraju);
        private JMenuItem tarjan = new JMenuItem(action_tarjan);



        private JMenuItem suppression_totale = new JMenuItem(action_supression_totale);
        private JMenuItem ouvrir = new JMenuItem(action_ouvrir);
        private JMenuItem sauvegarder = new JMenuItem(action_sauvegarder);
        private JMenuItem sousGraphe = new JMenuItem(action_CreerSousGraphe);
        private JMenuItem transformation = new JMenuItem(action_transformation);
        private JPopupMenu popup = new JPopupMenu();

        private JMenu fichier = new JMenu("Fichier");
        private JMenu analyse = new JMenu("Analyse de Graphe");


        MenuPanel() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setBackground(Color.lightGray);

            fichier.add(ouvrir);
            fichier.add(sauvegarder);
            fichier.add(transformation);
            fichier.add(sousGraphe);
            fichier.add(suppression_totale);
            this.add(fichier);


            analyse.add(dsatur);
            analyse.add(welsh_powell);
            analyse.add(kruskall);
            analyse.add(dijkstra);
            analyse.add(bellman_ford);
            analyse.add(ford_fulkerson);
            analyse.add(kosaraju);
            analyse.add(tarjan);
            this.add(analyse);

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
            }
            else if(a!=null){
                graphe.deleteArc(a.getId());
            }
            repaint();

        }
    }

    class transformationAction extends AbstractAction{
        public transformationAction(String name){super(name);}

        public void actionPerformed(ActionEvent e){
            graphe = graphe.changement_format();
            repaint();

        }
    }

    class dsaturAction extends AbstractAction{
        public dsaturAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.reset_couleur_graph();
            graphe.dsatur();
            repaint();
        }
    }

    class dijkstraAction extends AbstractAction{
        public dijkstraAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(f, "Veuillez selectionner le Sommet de Départ puis le Sommet d'arrivée via un simple clic.", "Information", JOptionPane.INFORMATION_MESSAGE);
            SommetSelec.clear();
            graphe.reset_couleur_graph();
            selectDeuxSommet=true;
            algo_en_cours = "dijkstra";
            repaint();
        }
    }

    class bellman_fordAction extends AbstractAction{
        public bellman_fordAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.reset_couleur_graph();
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(f, "Veuillez selectionner le Sommet de Départ puis le Sommet d'arrivée via un simple clic.", "Information", JOptionPane.INFORMATION_MESSAGE);
            SommetSelec.clear();
            selectDeuxSommet=true;
            algo_en_cours = "bellman";

            repaint();
        }
    }

    class ford_fulkersonAction extends AbstractAction{
        public ford_fulkersonAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.reset_couleur_graph();
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(f, "Veuillez selectionner le Sommet de Départ puis le Sommet d'arrivée via un simple clic.", "Information", JOptionPane.INFORMATION_MESSAGE);
            SommetSelec.clear();
            selectDeuxSommet=true;
            algo_en_cours = "ford";

            repaint();
        }
    }

    class kruskallAction extends AbstractAction{
        public kruskallAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.reset_couleur_graph();

            Boolean exec;
        	exec=graphe.kruskall();
        	if(exec==false){
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(f, "Ce Graphe ne contient aucun arc, la recherche d'un arbre couvrant est donc impossible.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
            repaint();
        }
    }

    class welsh_powellAction extends AbstractAction{
        public welsh_powellAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.reset_couleur_graph();
        	graphe.welsh_powell();
            repaint();
        }
    }

    class kosarajuAction extends AbstractAction{
        public kosarajuAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.reset_couleur_graph();
            graphe.kosaraju();
            repaint();
        }
    }

    class tarjanAction extends AbstractAction{
        public tarjanAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.reset_couleur_graph();
            graphe.tarjan();
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
                final JPanel p1 = new JPanel(new GridBagLayout());

                final ArrayList<TextField> liste_input = new ArrayList<TextField>();

                final ArrayList<JLabel> liste_Label = new ArrayList<JLabel>();

                final ArrayList<JButton> liste_delete_button = new ArrayList<JButton>();


                if(sommet){
                    VariableWindow.setSize(300, s.getList().size() * 100 + 200);
                    for (Variable v : s.getList()) {
                        liste_input.add(new TextField(v.toString()));
                    }
                }else{
                    VariableWindow.setSize(300, a.getList().size() * 100 + 200);
                    for (Variable v : a.getList()) {
                        liste_input.add(new TextField(v.toString()));
                    }
                }



                JPanel container = new JPanel();
                container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

                final GridBagConstraints gbc = new GridBagConstraints();
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.insets = new Insets(10, 10, 0, 10);



                for (int i = 0; i < liste_input.size(); i++) {
                    if(sommet){
                        liste_Label.add(new JLabel("Variable " + (i + 1) + " (" + s.getList().get(i).getTypeVar() + ") : "));
                        p1.add(liste_Label.get(i), gbc);
                        p1.add(liste_input.get(i), gbc);
                        liste_delete_button.add(new JButton("Supprimer"));
                        liste_delete_button.get(i).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                int j = liste_delete_button.indexOf((JButton)actionEvent.getSource());
                                p1.remove(liste_delete_button.get(j));
                                p1.remove(liste_input.get(j));
                                p1.remove(liste_Label.get(j));
                                liste_delete_button.remove(j);
                                liste_input.remove(j);
                                liste_Label.remove(j);
                                s.removeVar(j);
                                VariableWindow.setSize(300, s.getList().size() * 100 + 200);
                                VariableWindow.repaint();
                            }
                        });
                        p1.add(liste_delete_button.get(i), gbc);
                    }else{
                        if(i==0){
                            liste_Label.add(new JLabel("Poids " + " (" + a.getList().get(i).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(i), gbc);
                            p1.add(liste_input.get(i), gbc);
                        }else{
                            liste_Label.add(new JLabel("Variable " + (i + 1) + " (" + s.getList().get(i).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(i), gbc);
                            p1.add(liste_input.get(i), gbc);
                            liste_delete_button.add(new JButton("Supprimer"));
                            liste_delete_button.get(i).addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    int j = liste_delete_button.indexOf((JButton)actionEvent.getSource());
                                    p1.remove(liste_delete_button.get(j));
                                    p1.remove(liste_input.get(j));
                                    p1.remove(liste_Label.get(j));
                                    liste_delete_button.remove(j);
                                    liste_input.remove(j);
                                    liste_Label.remove(j);
                                    s.removeVar(j);
                                    VariableWindow.setSize(300, s.getList().size() * 100 + 200);
                                    VariableWindow.repaint();
                                }
                            });
                            p1.add(liste_delete_button.get(i), gbc);
                        }
                    }
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
                            liste_Label.add(new JLabel("Variable " + liste_input.size() + " (" + s.getList().get(liste_input.size() - 1).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(liste_Label.size()-1), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, s.getList().size() * 100 + 200);
                        }
                        else{
                            a.addVar(new VarInt(0));
                            liste_input.add(new TextField(a.getVar(a.getList().size() - 1).toString()));
                            liste_Label.add(new JLabel("Variable " + liste_input.size() + " (" + a.getList().get(liste_input.size() - 1).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(liste_Label.size()-1), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, a.getList().size() * 100 + 200);
                        }
                        liste_delete_button.add(new JButton("Supprimer"));
                        liste_delete_button.get(liste_delete_button.size()-1).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                int j = liste_delete_button.indexOf((JButton)actionEvent.getSource());
                                p1.remove(liste_delete_button.get(j));
                                p1.remove(liste_input.get(j));
                                p1.remove(liste_Label.get(j));
                                liste_delete_button.remove(j);
                                liste_input.remove(j);
                                liste_Label.remove(j);
                                s.removeVar(j);
                                VariableWindow.setSize(300, s.getList().size() * 100 + 200);
                                VariableWindow.repaint();
                            }
                        });
                        p1.add(liste_delete_button.get(liste_delete_button.size()-1), gbc);
                    }
                });

                addVariableFloat.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(s!=null){
                            s.addVar(new VarFloat(0));
                            liste_input.add(new TextField(s.getVar(s.getList().size() - 1).toString()));
                            liste_Label.add(new JLabel("Variable " + liste_input.size() + " (" + s.getList().get(liste_input.size() - 1).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(liste_Label.size()-1), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, s.getList().size() * 100 + 200);
                        }
                        else{
                            a.addVar(new VarFloat(0));
                            liste_input.add(new TextField(a.getVar(a.getList().size() - 1).toString()));
                            liste_Label.add(new JLabel("Variable " + liste_input.size() + " (" + a.getList().get(liste_input.size() - 1).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(liste_Label.size()-1), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, a.getList().size() * 100 + 200);
                        }
                        liste_delete_button.add(new JButton("Supprimer"));
                        liste_delete_button.get(liste_delete_button.size()-1).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                int j = liste_delete_button.indexOf((JButton)actionEvent.getSource());
                                p1.remove(liste_delete_button.get(j));
                                p1.remove(liste_input.get(j));
                                p1.remove(liste_Label.get(j));
                                liste_delete_button.remove(j);
                                liste_input.remove(j);
                                liste_Label.remove(j);
                                s.removeVar(j);
                                VariableWindow.setSize(300, s.getList().size() * 100 + 200);
                                VariableWindow.repaint();
                            }
                        });
                        p1.add(liste_delete_button.get(liste_delete_button.size()-1), gbc);
                    }
                });

                addVariableString.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(s!=null){
                            s.addVar(new VarString(" "));
                            liste_input.add(new TextField(s.getVar(s.getList().size() - 1).toString()));
                            liste_Label.add(new JLabel("Variable " + liste_input.size() + " (" + s.getList().get(liste_input.size() - 1).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(liste_Label.size()-1), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, s.getList().size() * 100 + 200);
                        }
                        else{
                            a.addVar(new VarString(" "));
                            liste_input.add(new TextField(a.getVar(a.getList().size() - 1).toString()));
                            liste_Label.add(new JLabel("Variable " + liste_input.size() + " (" + a.getList().get(liste_input.size() - 1).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(liste_Label.size()-1), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, a.getList().size() * 100 + 200);
                        }
                        liste_delete_button.add(new JButton("Supprimer"));
                        liste_delete_button.get(liste_delete_button.size()-1).addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                int j = liste_delete_button.indexOf((JButton)actionEvent.getSource());
                                p1.remove(liste_delete_button.get(j));
                                p1.remove(liste_input.get(j));
                                p1.remove(liste_Label.get(j));
                                liste_delete_button.remove(j);
                                liste_input.remove(j);
                                liste_Label.remove(j);
                                s.removeVar(j);
                                VariableWindow.setSize(300, s.getList().size() * 100 + 200);
                                VariableWindow.repaint();
                            }
                        });
                        p1.add(liste_delete_button.get(liste_delete_button.size()-1), gbc);
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
            while(graphe.getNbSommets()!=0){
            	graphe.deleteSommet(0);
            }
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
                graphe.creer_sous_graphe(SommetSelec);
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
        double min=Double.MAX_VALUE;
        Arc arc_min = null;
        for(Arc a : graphe.get_liste_arc()){
            Line2D l = new Line2D.Double(a.getSommetDepart().getPoint(), a.getSommetArrivee().getPoint());
            if(l.ptSegDist(p)<min){
                min = l.ptSegDist(p);
                arc_min = a;
            }
        }
        if(min<20){
            return arc_min;
        }
        return null;
    }
}
