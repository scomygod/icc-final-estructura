import javax.swing.*;
import view.LaberintoView;
import controller.LaberintoController;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crea una instancia de LaberintoController
            LaberintoController controller = new LaberintoController(null);

            // Inicializa la vista del laberinto con el tamaño y el controlador
            LaberintoView vista = new LaberintoView(10, controller);

            // Configura el controlador en la vista
            controller.setVista(vista);

            // Muestra la vista
            vista.setVisible(true);
        });
    }
}
