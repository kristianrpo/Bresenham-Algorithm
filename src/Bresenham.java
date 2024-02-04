/*
 * Este programa es una aplicación gráfica simple en Java que permite al usuario dibujar líneas.
 * Se utiliza el patrón de diseño de escuchador de eventos del mouse para detectar clics y movimientos del ratón.
 * Se utiliza una variación del algoritmo de bresenham para pintar lineas en los 8 octantes.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 * La clase Bresenham extiende JPanel y implementa MouseListener para manejar eventos del mouse.
 * Representa un componente gráfico que permite al usuario dibujar líneas.
 */
public class Bresenham extends JPanel implements MouseListener {
    Line2D.Double linea1; // Variable que almacena la línea que se crea, es decir, los puntos iniciales y finales

    /**
     * Constructor de la clase. Inicializa la variable de la línea y configura el objeto como escuchador de eventos del mouse.
     */
    public Bresenham() {
        linea1 = new Line2D.Double(); // Inicialización de la variable de la línea
        this.addMouseListener(this); // Consideración del objeto como un escuchador de los eventos del ratón
    }

    /**
     * Método paintComponent que se llama automáticamente para dibujar en el componente.
     * Dibuja los ejes y la línea en el panel.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Llama a las funciones relacionadas con el dibujo antes de realizar la acción

        Graphics2D g2d = (Graphics2D) g; // Crea un objeto de tipo gráfico que permite configurar el dibujo en el frame

        // Dibuja el plano XY en el centro del panel
        int centerX = getWidth() / 2; // Obtiene el punto x central del panel
        int centerY = getHeight() / 2; // Obtiene el punto y central del panel
        int length = 300; // Longitud de los ejes X e Y

        // Dibuja el eje X en rojo
        g2d.setColor(Color.RED);
        plotLine(g2d,centerX - length/2, centerY, centerX + length/2, centerY);

        // Dibuja el eje Y en verde
        g2d.setColor(Color.GREEN);
        plotLine(g2d,centerX, centerY - length/2, centerX, centerY + length / 2);

        g2d.setColor(Color.BLUE); // Establece el color de la línea generada con el mouse
        plotLine(g2d,(int) linea1.x1,(int) linea1.y1,(int) linea1.x2,(int) linea1.y2); // Pinta la línea

    }

    /**
     * Método para dibujar una línea utilizando el algoritmo de Bresenham.
     * @param g2d      El objeto Graphics2D utilizado para el dibujo.
     * @param x1       La coordenada x del punto inicial de la línea.
     * @param y1       La coordenada y del punto inicial de la línea.
     * @param x2       La coordenada x del punto final de la línea.
     * @param y2       La coordenada y del punto final de la línea.
     */
    public void plotLine(Graphics2D g2d,int x1, int y1, int x2, int y2){
        int dx= Math.abs(x2-x1); // obtengo el valor de que tanto se mueve en x la linea.
        int dy = Math.abs(y2-y1); // obtengo el valor de que tanto se mueve en y la linea.
        int x = x1; // almaceno valores iniciales de X en variable la cual va a cambiar su valor mientras pinta.
        int y= y1; // almaceno valores iniciales de Y en variable la cual va a cambiar su valor mientras pinta.
        int incrementDirectionX = x1 < x2 ? 1 : -1; // determino si el X se mueve en el eje positivo o negativo.
        int incrementDirectionY = y1 < y2 ? 1 : -1; // determino si el Y se mueve en el eje positivo o negativo.
        int P = 2*dy-dx; // defino la variable de decision.
        int temp = 0; // variable de control para definir cuando se pintó toda la linea.
        if ((dy==0)&&(dx==0)){ // Verificar si es solo un pixel, para permitir pintar puntos. Si es una linea, continua al ciclo.
            g2d.drawLine(x,y,x,y);
            return;
        }
        while ((dx < dy || temp != dx) && (dy < dx || temp != dy)) { // mientras que no se haya recorrido toda la distancia a pintar.
            g2d.drawLine(x, y, x, y); // pinto mi punto actual.
            if (dx >= dy) { // verifico si me estoy moviendo mas en x o en y. Si es en X:.
                x = x + incrementDirectionX; // mi X incrementa constamentemente de a 1.
                if (P < 0) { // si mi variable de decision es menor a 0, escojo el mismo Yk.
                    P = P + 2 * dy;
                } else { // de lo contrario, escojo el y+1 || y-1 (dependiendo la direccion donde nos estamos moviendo).
                    P = P + 2 * dy - 2 * dx;
                    y = y + incrementDirectionY;
                }
            } else { // si por el contrario me estoy moviendo mas en y que en X.
                y = y + incrementDirectionY;  // mi y incrementa constantemente de a 1.
                if (P < 0) { //si mi variable de decision es menor a 0 escojo Xk.
                    P = P + 2 * dx;
                } else { // de lo contrario, escojo x+1 || x-1 (dependiendo la direccion donde nos estamos moviendo).
                    P = P + 2 * dx - 2 * dy;
                    x = x + incrementDirectionX;
                }
            }
            temp++; // marco el pixel como recorrido al cambiar mi variable de control.
        }
    }

    /**
     * Métodos requeridos por la interfaz MouseListener.
     */

    @Override
    public void mouseClicked(MouseEvent e) {
        // No se utiliza en este ejemplo.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // No se utiliza en este ejemplo.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // No se utiliza en este ejemplo.
    }

    /**
     * Método llamado cuando se presiona el botón del mouse.
     * Obtiene el punto inicial donde se presionó el mouse.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        linea1.x1 = e.getX();
        linea1.y1 = e.getY();
    }

    /**
     * Método llamado cuando se suelta el botón del mouse.
     * Guarda el punto final donde el mouse se soltó y repinta el componente.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        linea1.x2 = e.getX();
        linea1.y2 = e.getY();
        repaint();
    }

    /**
     * Método principal que crea un JFrame y agrega una instancia de Bresenham a él.
     */
    public static void main(String[] args) {
        // crear un nuevo Frame, el cual es la ventana donde añadiremos varios objetos.
        JFrame frame = new JFrame("Bresenham");
        // configurar que al cerrar el frame pare la ejecución del programa.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // agregar un JPanel que se llama Points, es el contenedor donde tendremos los elementos.
        Bresenham ev = new Bresenham();
        frame.add(ev); // añadimos el contenedor al frame.
        frame.setSize(500, 500); // definimos el tamaño del frame.
        frame.setLocationRelativeTo(null); // ponemos el frame en el centro de la pantalla.
        frame.setVisible(true); // hacemos visible el frame al usuario.
    }
}
