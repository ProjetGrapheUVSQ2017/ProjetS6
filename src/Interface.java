import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Classe héritée de JComponent qui permet d'afficher un fenêtre affichant un graphe isssu de la classe Graphe.
 * Cette fenêtre est composée d'un menu permettant d'acceder aux fonctions définies dans la classe Graphe, ainsi que d'une espace graphique le representant.
 * @see Graphe
 * @author Maxence
 *
 */

public class Interface extends JComponent {

    /**
     * largeur et hauteur donnent la taille de la fenêtre à son ouverture.
     */
    private static int largeur = 1000;
    private static int hauteur = 600;

    /**
     * SommetSelec contient la liste des Sommets qui ont été séléctionné par l'utilisateur.
     */
    private ArrayList<Sommet> SommetSelec = new ArrayList<Sommet>();

    /**
     * ptSouris la position de la souris dans l'espace.
     */
    private Point ptSouris;

    /**
     * rectangleSouris représente l'ensemble des points dans le plan que l'utilisateur est en train de selectionner.
     */
    private Rectangle rectangleSouris = new Rectangle();

    /**
     * selectionEnCours est un booléen permettant de savoir si l'utilisateur est en train de sélectionner des sommets.
     */
    private boolean selectionEnCours;

    /**
     * selectDeuxSommet est un booléen permettant de savoir si l'utilisateur est en train de sélectionner des sommets pour l'execution d'algorithme en nécessitant, tel que Dijkstra.
     */
    private boolean selectDeuxSommet = false;

    /**
     * algo_en_cours permet de savoir quel algorithme nécéssitant deux sommets à séléctionner est en cours.
     */
    private String algo_en_cours = new String();

    /**
     * control contient le Menu de l'Interface.
     */
    private MenuPanel control = new MenuPanel();

    /**
     * graphe contient le Graphe utilisé par l'Interface.
     */
    private static Graphe graphe;

    /**
     * rayon_sommet permet de définir la taille du rayon des cercles représentant les Sommets du Graphe.
     */
    private static int rayon_sommet = 15;

    /**
     * f contient la fenêtre du programme.
     */
    private JFrame f = new JFrame("Graphe");

    /**
     * modeMouse contient le systeme de séléction de Mode d'édition du Graphe.
     */
    private JComboBox modeMouse;


    /**
     * Fonction appelée au démarrage du programme.
     * ELle crée une Interface et un Graphe vide qui est par défaut de type Liste.
     */
    public static void main(String[] args) throws Exception{
        EventQueue.invokeLater(new Runnable() {

            public void run(){

                Interface inter = new Interface();
                graphe = new GrapheListe();
            }

        });
    }

    /**
     * Construteur de la classe Interface.
     * -Donne comme opération par défaut de fermeture de la fenêtre la fin du Programme.
     * -Ajoute le menu à l'interface et le place en haut de la fenêtre.
     * -Ajoute la zone de dessin au milieu de la fenêtre.
     * -Définis la taille de la fenêtre selon les attributs largeur et hauteur.
     * -Définis la taille minimum de la fenêtre selon les attributs largeur et hauteur afin de ne pas avoir des chevauchement dans le Menu du Programme.
     * -Modifie toutes les tailles des éléments composant l'Interface si nécessaire.
     * -Rends la Fenêtre visible.
     * -Rends la Fenêtre opaque.
     * -Ajoute la surveillance des clics de souris à l'Interface.
     * @see GestionSouris
     * -Ajoute la surveillance des mouvements de souris à l'Interface.
     * @see SelectionViaClic
     */
    public Interface(){
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(this.control,BorderLayout.NORTH);
        f.add(new JScrollPane(this),BorderLayout.CENTER);
        f.setPreferredSize(new Dimension(largeur, hauteur));
        f.setMinimumSize(new Dimension(largeur,hauteur));
        f.pack();
        f.setVisible(true);

        this.setOpaque(true);

        this.addMouseListener(new GestionSouris());
        this.addMouseMotionListener(new SelectionViaClic());

    }

    /**
     * Méthode appelée par la fenêtre à son démarrage ainsi qu'à chaque appel de la fonction repaint().
     *
     * -Recolorie la fenêtre en Blanc.
     * -Affiche le type de Graphe utilisé actuellement en haut à gauche.
     * -Affiche chaque sommet selon sa Position et sa Couleur, ainsi que l'ensemble des ses variables associées.
     * -Affiche chaque arc selon son orientation et sa Couleur, ainsi que l'ensemble des ses variables associées. On utilise ici une méthode qui créer des fléches afin d'afficher le sens de l'arc entre deux Sommets, drawArrow().
     * -Affiche un carré gris autour de chaque sommet sélectionné.
     * -Affiche la Matrice si le graphe actuel est de type Matrice.
     * -Affiche le rectangle de selection.
     */

    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());

        g.setColor(Color.BLACK);
        String type_graphe;
        if(graphe.getClass().getName().equals("GrapheListe")){
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

        g.setColor(Color.BLACK);
        if(type_graphe.equals("Matrice")){
            if(graphe.getNbSommets()<8){
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(10,67, 35+15*graphe.getNbSommets(),67);
                g2.drawLine(22,55, 22,65+15*graphe.getNbSommets());
                g.drawString("Matrice d'adjacence",10,50);
                for(int i=0; i<graphe.getNbSommets(); i++){
                    g.drawString(String.valueOf(i),25+i*15,65);
                    g.drawString(String.valueOf(i), 10, 80+i*15);
                    for(int j=0; j<graphe.getNbSommets();j++){
                        if(graphe.getArc(graphe.getSommet(j), graphe.getSommet(i))!=null){
                            g.drawString("1", 25+i*15, 80+j*15);
                        }else{
                            g.drawString("0", 25+i*15, 80+j*15);
                        }
                    }
                }
            }
        }

        g.setColor(Color.darkGray);
        g.drawRect(rectangleSouris.x, rectangleSouris.y, rectangleSouris.width, rectangleSouris.height);


    }

    /**
     * Méthode appelée par paintCompoment afin d'afficher des flèches entre deux points.
     * @param g1 Graphics : Donne le composant de dessin de la fenêtre.
     * @param x1 int : Position sur l'axe X du point d'origine de la flèche.
     * @param y1 int : Position sur l'axe Y du point d'origine de la flèche.
     * @param x2 int : Position sur l'axe X du point d'arrivée de la flèche.
     * @param y2 int : Position sur l'axe Y du point d'arriveée de la flèche.
     * @param a Arc : L'arc que l'on souhaite dessiner afin d'en récuperer les variables.
     */

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

    /**
     * Sous-Classe de l'Interface définissant les actions à effectuer en cas de clic de la souris.
     * -Récupère la position du clic de souris et la place dans l'attribut ptSouris de l'Interface.
     * -Affiche le Menu Popup suite à un clic droit de la souris.
     * -Ajoute un sommet à la liste des Sommets Selectionnés si l'on clique sur celui çi. A chque nouveau clic sur un sommet, les autres sommets sont déselectionnés. Maj+Clic permet de ne pas les deselectionner.
     * -Gère si i l'utilisateur a lancé l'un des algorithmes nécessitant deux sommets pour s'executer, demande à l'utilisateur deux sommets puis lance les algorithmes. Affiche des fenêtre d'information en cas d'erreur sur l'execution de ceux çi.
     * -Ajoute un arc entre deux sommets si l'utilisateur a déplacé sa souris entre deux sommets en restant appuyé sur le clic gauche en Mode Arc.
     */

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
                                jop.showMessageDialog(f, "L'algorithme n'a pas pu s'executer, il n'existe pas de chemin entre les deux Sommets sélectionnés ou il existe un arc négatif.", "Information", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        algo_en_cours = null;
                        SommetSelec.clear();
                    }
                }else{
                    if(modeMouse.getSelectedItem()=="Mode Selection"){
                        if (e.isShiftDown()) {
                            SommetSelec.add(getSommetFromPoint(ptSouris));
                        }else{
                            SommetSelec.clear();
                            SommetSelec.add(getSommetFromPoint(ptSouris));
                        }
                    }
                    else if(modeMouse.getSelectedItem()=="Mode Sommet"){
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
                if(modeMouse.getSelectedItem()=="Mode Selection"){
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
            if(modeMouse.getSelectedItem()=="Mode Arc"){
                if(getSommetFromPoint(depart) != getSommetFromPoint(arrive)){
                    graphe.addArc(getSommetFromPoint(depart), getSommetFromPoint(arrive));
                }
            }
            if(modeMouse.getSelectedItem()=="Mode Selection"){
                rectangleSouris = new Rectangle();

            }
            repaint();
        }

        private void showPopup(MouseEvent e) {
            control.popup.show(e.getComponent(), e.getX(), e.getY());
        }

    }

    /**
     * Sous-Classe de l'Interface définissant les actions à effectuer en cas de déplacement de la souris tout en restant appuyé sur le clic gauche.
     * -Déplace un sommet sur lequel on a cliqué en Mode Sommet.
     * -Déplace les sommets contenus dans la liste des sommets selectionnés en Mode Sommet.
     * -Ajoute les sommets contenus dans le rectangle créer par l'utilisateur au cours de son mouvement à la liste des sommets sélectionnés en Mode Selection.
     */

    private class SelectionViaClic extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            Point p = new Point();
            if(modeMouse.getSelectedItem()=="Mode Sommet"){
                p.setLocation(e.getX() - ptSouris.x, e.getY() - ptSouris.y);
                for(Sommet s: SommetSelec){
                    s.getPoint().x += p.x;
                    s.getPoint().y += p.y;
                }
                ptSouris = e.getPoint();
            }
            if(modeMouse.getSelectedItem()=="Mode Selection"){
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

    /**
     * Sous-Classe de l'Interface définissant les actions à effectuer en cas de selection d'un nouvel élément dans le menu déroulant des Modes d'Edition.
     * -Si l'utilisateur passe en Mode Arc, la liste des sommets selectionnés est vidée.
     */

    public class ModeMouseComboBox implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(modeMouse.getSelectedItem()=="Mode Arc"){
                SommetSelec.clear();
                repaint();
            }
        }
    }

    /**
     * Sous-Classe de l'Interface qui créer le Menu permetant d'effectuer diverses opérations sur le Graphe. Cette classe est dérivée de la classe Java JMenuBar.
     */

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
        private Action action_supprimerSommet = new supprimerAction("Supprimer");
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

        private JButton information = new JButton();

        private JMenu fichier = new JMenu("Fichier");
        private JMenu analyse = new JMenu("Analyse de Graphe");

        /**
         * Construteur de la classe MenuPanel.
         * -Définis l'organisation des élements du Menu à gauche.
         * -Définis la couleur du fond du Menu en Gris.
         * -Ajoute le sous menu Fichier qui contient les actions Ouvrir, Sauvegarder, Transformation, Creation d'un Sous Graphe et Suppression totale sur le Graphe.
         * -Ajoute le sous menu Analyse de Graphe qui contient les actions d'analyse des graphes via différents algorithmes.
         * -Creer le Menu Popup contenant les actions d'ajout, de suppression, de modification des Arcs et des Sommets ainsi que de leur variables.
         * -Ajoute le Menu déroulant de selection du mode d'édition.
         * -Ajoute le bouton d'affichage de la fenêtre d'aide.
         */
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


            String[] modeMouseString = { "Mode Selection", "Mode Arc", "Mode Sommet"};
            modeMouse = new JComboBox(modeMouseString);
            modeMouse.addActionListener(new ModeMouseComboBox());
            this.add(modeMouse);




            information.setText("Mode d'emploi");
            information.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JTextArea textArea = new JTextArea("Mode d'emploi :\n" +
                            "\n" +
                            "Création/Modification/Suppression d'un sommet : Clic droit sur le sommet\n" +
                            "\n" +
                            "Création d'un arc : Mettez-vous en mode \"Arcs\" et avec le clic gauche, glissez la souris d'un sommet vers un autre.\n" +
                            "\n" +
                            "Modification/Suppression d'un arc : Clic droit sur l'arc\n" +
                            "\n" +
                            "Selection d'un sommet : Mettez-vous en mode \"Selection\" et cliquez sur un sommet. Pour en selectionner plusieurs, appuyez sur MAj et cliquez sur vos sommets. Vous pouvez également faire glisser la souris tout en restant appuyer pour créer un rectangle de sélection.\n" +
                            "\n" +
                            "Déplacer un sommet : Mettez-vous en mode \"Sommets\" et cliquez sur un sommet pour en déplacer. Pour déplacer plusieurs sommets, mettez-vous en mode \"selection\", selectionnez vos sommets avec Maj et passez en mode d'édition \"Sommets\".\n" +
                            "\n" +
                            "Pour charger un fichier : Choississez dans la liste \"Fichier\" le mode \"Charger\" et choississez le fichier à charger (les fichiers compatibles sont en /graphe)\n" +
                            "\n" +
                            "Pour sauvegarder un fichier : Choississez dans la liste \"Fichier\" le mode \"Sauvegarder\" et choississez le dossier de destination ainsi que le nom de votre fichier.\n" +
                            "\n" +
                            "Pour changer de type de graphe : Choississez dans la liste \"Fichier\" le mode \"Changement de type de graphe\".\n" +
                            "\n" +
                            "Pour créer un sous-graphe : Choississez les sommets que vous voulez garder avec le mode d'édition \"Selection\" et choississez dans la liste \"Fichier\" le mode \"Creer sous graphe\".\n" +
                            "\n" +
                            "Pour supprimer entièrement le graphe : Choississez dans la liste \"Fichier\" le mode \"Tout supprimer\".\n" +
                            "\n" +
                            "Pour lancer un algorithme sur le graphe : Choississez l'algorithme dans \"Analyse de graphe\". Si l'algorithme choisit est Dijsktra/Bellman-Ford/Ford-Fulkerson, le programme vous demandera un sommet de départ et un sommet d'arrivée.\n" +
                            "\n" +
                            "\n" +
                            "Algorithmes :\n" +
                            "\n" +
                            "Kruskal : Permet de trouver l'arbre couvrant de poids minimum dans un graphe\n" +
                            "\t   Compléxite : (arcs x log(arcs))\n" +
                            "Dijsktra : Permet de trouver le plus courts chemins entre 2 sommets d'un graphe\n" +
                            "\t   Compléxité : (arcs²)\n" +
                            "Bellman-Ford : Permet de trouver le plus courts chemins entre 2 sommets d'un graphe qui ont des arcs de poids négatifs\n" +
                            "\t   Compléxité : (sommets²)\n" +
                            "Ford-Fulkerson : Permet de trouver le flot maximum d'un point A à un point B\n" +
                            "\t   Complexité : (sommets x arcs²)\n" +
                            "Welsh-Powell : Permet de trouver la coloration la plus optimale dans un graphe (ne fonctionne pas à 100%)\n" +
                            "\t   Complexité : (sommets + arc)\n" +
                            "DSATUR : Permet de trouver la coloration la plus optimale dans un graphe (ne fonctionne pas à 100%)\n" +
                            "\t   Complexité : (sommets²)\n" +
                            "Kosaraju : Permet de trouver les composantes fortement connexes (les sommets de la même couleur son fortement connexe entre-eux)\n" +
                            "\t   Complexité : (sommets+arcs)\n" +
                            "Tarjan : Permet de trouver les points d'accumulations dans un graphe\n" +
                            "\t   Complexité : (sommets+arcs)\n");
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
                    JOptionPane.showMessageDialog(null, scrollPane, "Information",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });

            this.add(information);
        }

    }

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec creerSommetAction.
     * -Ajoute un sommet à la position donnée par ptSouris.
     */

    class creerSommetAction extends AbstractAction{
        public creerSommetAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.addSommet(ptSouris.getLocation());
            repaint();
        }
    }

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec supprimerAction.
     * -Récupère le sommet à la position donnée par ptSouris et le supprime.
     * -Récupère l'arc à la position donnée par ptSouris et le supprime.
     */

    class supprimerAction extends AbstractAction{
        public supprimerAction(String name){super(name);}

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec transformationAction.
     * -Change le format du Graphe. GrapheListe vers GrapheMatrice  et inversement.
     */

    class transformationAction extends AbstractAction{
        public transformationAction(String name){super(name);}

        public void actionPerformed(ActionEvent e){
            graphe = graphe.changement_format();
            repaint();

        }
    }

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec dsaturAction.
     * -Redéfinis toutes les couleurs du graphe par defaut.
     * -Applique l'algorithme DSatur de coloration de graphe.
     */

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec dsaturAction.
     * -Redéfinis toutes les couleurs du graphe par defaut.
     * -Affiche une fenêtre demandant à l'utilisateur de cliquer sur 2 Sommets du graphe.
     * -Définis l'algorithme à effectuer une fois les 2 sommets selectionnés à "dijkstra".
     */

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec bellman_fordAction.
     * -Redéfinis toutes les couleurs du graphe par defaut.
     * -Affiche une fenêtre demandant à l'utilisateur de cliquer sur 2 Sommets du graphe.
     * -Définis l'algorithme à effectuer une fois les 2 sommets selectionnés à "bellman".
     */

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec ford_ferkulsonAction.
     * -Redéfinis toutes les couleurs du graphe par defaut.
     * -Affiche une fenêtre demandant à l'utilisateur de cliquer sur 2 Sommets du graphe.
     * -Définis l'algorithme à effectuer une fois les 2 sommets selectionnés à "ford".
     */

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec krukallAction.
     * -Redéfinis toutes les couleurs du graphe par defaut.
     * -Applique l'algorithme de Krukall, qui recherche l'arbre couvrant de poid minimum sur le graphe.
     * -Affiche une fenêtre d'information si le graphe ne contient pas d'arc et empechant ainsi l'algorithme de s'executer.
     */

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec welsh_powellAction.
     * -Redéfinis toutes les couleurs du graphe par defaut.
     * -Applique l'algorithme de Welsh Powell de coloration sur le Graphe.
     */

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec kosarajuAction.
     * -Redéfinis toutes les couleurs du graphe par defaut.
     * -Applique l'algorithme de Kosaraju qui recherche les composantes fortement connexes du graphe.
     */

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec kosarajuAction.
     * -Redéfinis toutes les couleurs du graphe par defaut.
     * -Applique l'algorithme de Kosaraju qui recherche les points d'accumultation du graphe.
     * -Affiche une fenêtre d'information si il n'existe pas de poinrs d'accumulation dans le graphe.
     */

    class tarjanAction extends AbstractAction{
        public tarjanAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            graphe.reset_couleur_graph();

            if(!graphe.tarjan()){
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(f, "Il n'existe pas de point d'accumulation dans ce Graphe.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
            repaint();
        }
    }

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec modifierVariableAction.
     * -Affiche une fenêtre contenant les outils nécessaires à l'ajout, la modification et la suppression des variables associées aux arcs et sommets.
     * -A chaque variable est associé un zone d'édition du contenu de celle ci, un texte indiquant son type et un bouton de suppression.
     * -A la fermeture de la fenetre ou via le clic sur le bouton "Terminer", elle vérifie que chaque variable modifiée contient bien le bon type de donnée, sinon empehce la fermeture et affiche un message indiquant le problème.
     * -Bouton pour ajouter une variable de type Entier (Integer).
     * -Bouton pour ajouter une variable de type Decimale (Float).
     * -Bouton pour ajouter une variable de type Texte (String).
     * -La fenêtre change de taille dynamiquement selon le nombre de variable.
     */

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
                            liste_delete_button.add(new JButton());
                            liste_delete_button.get(0).setVisible(false);
                        }else{
                            liste_Label.add(new JLabel("Variable " + (i + 1) + " (" + a.getList().get(i).getTypeVar() + ") : "));
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
                                    a.removeVar(j);
                                    VariableWindow.setSize(300, a.getList().size() * 100 + 200);
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
                        }
                        else{
                            a.addVar(new VarInt(0));
                            liste_input.add(new TextField(a.getVar(a.getList().size() - 1).toString()));
                            liste_Label.add(new JLabel("Variable " + liste_input.size() + " (" + a.getList().get(liste_input.size() - 1).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(liste_Label.size()-1), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, a.getList().size() * 100 + 200);
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
                                    a.removeVar(j);
                                    VariableWindow.setSize(300, a.getList().size() * 100 + 200);
                                    VariableWindow.repaint();
                                }
                            });
                        }
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
                        }
                        else{
                            a.addVar(new VarFloat(0));
                            liste_input.add(new TextField(a.getVar(a.getList().size() - 1).toString()));
                            liste_Label.add(new JLabel("Variable " + liste_input.size() + " (" + a.getList().get(liste_input.size() - 1).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(liste_Label.size()-1), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, a.getList().size() * 100 + 200);
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
                                    a.removeVar(j);
                                    VariableWindow.setSize(300, a.getList().size() * 100 + 200);
                                    VariableWindow.repaint();
                                }
                            });
                        }
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
                        }
                        else{
                            a.addVar(new VarString(" "));
                            liste_input.add(new TextField(a.getVar(a.getList().size() - 1).toString()));
                            liste_Label.add(new JLabel("Variable " + liste_input.size() + " (" + a.getList().get(liste_input.size() - 1).getTypeVar() + ") : "));
                            p1.add(liste_Label.get(liste_Label.size()-1), gbc);
                            p1.add(liste_input.get(liste_input.size() - 1), gbc);
                            VariableWindow.setSize(300, a.getList().size() * 100 + 200);
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
                                    a.removeVar(j);
                                    VariableWindow.setSize(300, a.getList().size() * 100 + 200);
                                    VariableWindow.repaint();
                                }
                            });
                        }
                        p1.add(liste_delete_button.get(liste_delete_button.size()-1), gbc);
                    }
                });

                terminer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        boolean error=false;
                        for (int i = 0; i < liste_input.size(); i++) {
                            if (s != null) {
                                if (s.getVar(i).getTypeVar() == "Int") {
                                    try{
                                        int int_parse = Integer.parseInt(liste_input.get(i).getText());
                                        s.setVar(i, new VarInt(int_parse));
                                    }
                                    catch(NumberFormatException e) {
                                        error=true;
                                    }
                                }
                                if (s.getVar(i).getTypeVar() == "Float") {
                                    try{
                                        float float_parse = Float.valueOf(liste_input.get(i).getText());
                                        s.setVar(i, new VarFloat(float_parse));
                                    }
                                    catch(NumberFormatException e) {
                                        error=true;
                                    }
                                }
                                if (s.getVar(i).getTypeVar() == "String") {
                                    s.setVar(i, new VarString(liste_input.get(i).getText()));
                                }
                            }
                            else{
                                if (a.getVar(i).getTypeVar() == "Int") {
                                    try{
                                        int int_parse = Integer.parseInt(liste_input.get(i).getText());
                                        a.setVar(i, new VarInt(int_parse));
                                    }
                                    catch(NumberFormatException e) {
                                        error=true;
                                    }
                                }
                                if (a.getVar(i).getTypeVar() == "Float") {
                                    try{
                                        float float_parse = Float.valueOf(liste_input.get(i).getText());
                                        a.setVar(i, new VarFloat(float_parse));
                                    }
                                    catch(NumberFormatException e) {
                                        error=true;
                                    }
                                    if(i==0){
                                        a.getVar(i).setPoids(true);
                                    }
                                }
                                if (a.getVar(i).getTypeVar() == "String") {
                                    a.setVar(i, new VarString(liste_input.get(i).getText()));
                                }
                            }
                        }
                        if(error){
                            JOptionPane jop = new JOptionPane();
                            jop.showMessageDialog(VariableWindow, "Au moins une des Variables que vous avez entré n'est pas du bon type!", "Information", JOptionPane.INFORMATION_MESSAGE);
                            VariableWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        }else{
                            repaint();
                            VariableWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            VariableWindow.setVisible(false);
                            VariableWindow.dispose();
                        }
                    }
                });

                VariableWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        boolean error=false;
                        for (int i = 0; i < liste_input.size(); i++) {
                            if (s != null) {
                                if (s.getVar(i).getTypeVar() == "Int") {
                                    try{
                                        int int_parse = Integer.parseInt(liste_input.get(i).getText());
                                        s.setVar(i, new VarInt(int_parse));
                                    }
                                    catch(NumberFormatException e) {
                                        error=true;
                                    }
                                }
                                if (s.getVar(i).getTypeVar() == "Float") {
                                    try{
                                        float float_parse = Float.valueOf(liste_input.get(i).getText());
                                        s.setVar(i, new VarFloat(float_parse));
                                    }
                                    catch(NumberFormatException e) {
                                        error=true;
                                    }
                                }
                                if (s.getVar(i).getTypeVar() == "String") {
                                    s.setVar(i, new VarString(liste_input.get(i).getText()));
                                }
                            }
                            else{
                                if (a.getVar(i).getTypeVar() == "Int") {
                                    try{
                                        int int_parse = Integer.parseInt(liste_input.get(i).getText());
                                        a.setVar(i, new VarInt(int_parse));
                                    }
                                    catch(NumberFormatException e) {
                                        error=true;
                                    }
                                }
                                if (a.getVar(i).getTypeVar() == "Float") {
                                    try{
                                        float float_parse = Float.valueOf(liste_input.get(i).getText());
                                        a.setVar(i, new VarFloat(float_parse));
                                    }
                                    catch(NumberFormatException e) {
                                        error=true;
                                    }
                                    if(i==0){
                                        a.getVar(i).setPoids(true);
                                    }
                                }
                                if (a.getVar(i).getTypeVar() == "String") {
                                    a.setVar(i, new VarString(liste_input.get(i).getText()));
                                }
                            }
                        }
                        if(error){
                            JOptionPane jop = new JOptionPane();
                            jop.showMessageDialog(VariableWindow, "Au moins une des Variables que vous avez entré n'est pas du bon type!", "Information", JOptionPane.INFORMATION_MESSAGE);
                            VariableWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        }else{
                            repaint();
                            VariableWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            VariableWindow.setVisible(false);
                            VariableWindow.dispose();
                        }
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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec suppressionTotaleAction.
     * -Supprime tout les sommets (et donc les arcs associés
     */

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec ouvrirAction.
     * -Affiche une fenêtre demandant le fichier de type *.graphe que l'on souhaite ouvrir.
     * -Charge le nouveau graphe donné par le fichier.
     */

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec sauvegarderAction.
     * -Affiche une fenêtre demandant le nom et l'emplacement du fichier dans lequel on souhaite sauvegarder le graphe.
     * -Sauvegarde le graphe actuel dans un fichier de type *.graphe à l'emplacement donné.
     */

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

    /**
     * Sous-Classe de l'Interface qui définie l'action à effectuer avec creerSousGraphe.
     * -Creer un sous-graphe à partir de la liste des sommets selectinonés et les deselectionne.
     */

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

    /**
     * Méthode de la classe Interface qui permet de renvoyer true si un Point est dans un sommet du Graphe, false sinon.
     * @param p Point : Point que l'on souhaite vérifier.
     * @param s Sommet  Sommet que l'on souhaite vériier.
     */

    private static boolean isPointInSommet(Point p, Sommet s){
        if(Math.pow(p.x - s.getPoint().x, 2) + Math.pow(p.y - s.getPoint().y, 2) < Math.pow(rayon_sommet, 2)){
                return true;
            }
        return false;
    }

    /**
     * Méthode de la classe Interface qui permet de donner le premier Sommet qui contient le point donné.
     * @param p Point : Point que l'on souhaite vérifier.
     */

    private static Sommet getSommetFromPoint(Point p){
        for(Sommet s : graphe.get_liste_de_sommet()){
            if(isPointInSommet(p, s)){
                return s;
            }
        }
        return null;
    }

    /**
     * Méthode de la classe Interface qui permet de donner l'arc le plus proche et à moins de 20 pixel de distance du point donné.
     * @param p Point : Point que l'on souhaite vérifier.
     */

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
