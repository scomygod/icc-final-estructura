package view;

import javax.swing.*;
import java.awt.*;
import controller.LaberintoController;
import models.LaberintoModel;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class LaberintoView extends JFrame {
    // Paneles y componentes de la vista
    private JPanel gridPanel;
    private boolean[][] laberinto; // Matriz que representa el laberinto
    private LaberintoController controller; // Controlador para manejar la lógica
    private int size; // Tamaño del laberinto
    private JTextField rowsField; // Campo de texto para ingresar el número de filas
    private JTextField colsField; // Campo de texto para ingresar el número de columnas
    private JLabel timeLabel; // Etiqueta para mostrar el tiempo de resolución
    private JButton[][] botones; // Botones para representar el laberinto en la vista

    public LaberintoView(int size, LaberintoController controller) {
        this.size = size;
        this.controller = controller;
        laberinto = new boolean[size][size]; // Inicializa la matriz del laberinto
        initializeUI(); // Configura la interfaz de usuario
    }

    public void setController(LaberintoController controller) {
        this.controller = controller;
    }

    private void initializeUI() {
        // Configura la ventana principal
        setTitle("Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Panel de entrada para filas y columnas
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        rowsField = new JTextField(5);
        colsField = new JTextField(5);
        JButton updateButton = new JButton("Actualizar");
        JButton clearButton = new JButton("Limpiar");

        inputPanel.add(new JLabel("Filas:"));
        inputPanel.add(rowsField);
        inputPanel.add(new JLabel("Columnas:"));
        inputPanel.add(colsField);
        inputPanel.add(updateButton);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.NORTH);

        // Panel que representa el laberinto
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(size, size));
        add(gridPanel, BorderLayout.CENTER);

        // Panel de botones para resolver el laberinto
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton bfsButton = new JButton("Resolver con BFS");
        JButton dfsButton = new JButton("Resolver con DFS");
        JButton recursiveButton = new JButton("Resolver con Recursivo");
        JButton cacheButton = new JButton("Resolver con Cache");

        buttonPanel.add(bfsButton);
        buttonPanel.add(dfsButton);
        buttonPanel.add(recursiveButton);
        buttonPanel.add(cacheButton);

        timeLabel = new JLabel("Tiempo: ");
        buttonPanel.add(timeLabel);

        // Inicializa los botones para el laberinto
        botones = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setBackground(Color.WHITE);
                botones[i][j].setOpaque(true);
                botones[i][j].setBorderPainted(true);
                final int x = i;
                final int y = j;
                botones[i][j].addActionListener(e -> toggleCell(x, y));
                gridPanel.add(botones[i][j]);
            }
        }

        // Agrega los listeners a los botones
        bfsButton.addActionListener(e -> solveMaze("BFS"));
        dfsButton.addActionListener(e -> solveMaze("DFS"));
        recursiveButton.addActionListener(e -> solveMaze("Recursive"));
        cacheButton.addActionListener(e -> solveMaze("Cache"));
        updateButton.addActionListener(e -> updateMaze());
        clearButton.addActionListener(e -> clearMaze());
    }

    // Alterna el estado de una celda (pared o camino) y actualiza su color
    private void toggleCell(int x, int y) {
        laberinto[x][y] = !laberinto[x][y];
        botones[x][y].setBackground(laberinto[x][y] ? Color.BLACK : Color.WHITE);
    }

    // Resuelve el laberinto utilizando el método especificado
    private void solveMaze(String method) {
        // Limpia la solución anterior
        for (Component component : gridPanel.getComponents()) {
            JButton cell = (JButton) component;
            if (cell.getBackground() != Color.BLACK) {
                cell.setBackground(Color.WHITE);
            }
        }

        // Establece las paredes en el laberinto
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (laberinto[i][j]) {
                    botones[i][j].setBackground(Color.BLACK);
                }
            }
        }

        // Define los puntos de inicio y fin
        int inicioX = 0;
        int inicioY = 0;
        int finX = size - 1;
        int finY = size - 1;

        long startTime = System.nanoTime(); // Inicia la medición del tiempo
        List<LaberintoModel> path = null;

        // Resuelve el laberinto con el método seleccionado
        switch (method) {
            case "BFS":
                path = controller.getPathBFS(laberinto, inicioX, inicioY, finX, finY);
                break;
            case "DFS":
                path = controller.getPathDFS(laberinto, inicioX, inicioY, finX, finY);
                break;
            case "Recursive":
                path = controller.getPathRecursivo(laberinto, inicioX, inicioY, finX, finY);
                break;
            case "Cache":
                path = controller.getPathCache(laberinto, inicioX, inicioY, finX, finY);
                break;
        }

        long endTime = System.nanoTime(); // Finaliza la medición del tiempo
        double elapsedTime = (endTime - startTime) / 1_000_000_000.0; // Calcula el tiempo en segundos

        timeLabel.setText(String.format("%.4f segundos", elapsedTime)); // Muestra el tiempo en la etiqueta

        // Pinta el camino en verde si se encontró una solución
        if (path != null) {
            pintarCaminoVerde(path);
        }
    }

    // Pinta el camino en verde en la vista usando Timer para gestionar el retraso
    private void pintarCaminoVerde(List<LaberintoModel> camino) {
        Queue<LaberintoModel> queue = new LinkedList<>(camino);
        Timer timer = new Timer(100, e -> {
            if (!queue.isEmpty()) {
                LaberintoModel nodo = queue.poll();
                int x = nodo.getInicioX();
                int y = nodo.getInicioY();
                if (botones[x][y].getBackground() == Color.WHITE) {
                    botones[x][y].setBackground(Color.GREEN);
                }
            } else {
                ((Timer) e.getSource()).stop(); // Detiene el timer cuando el camino se ha pintado
            }
        });
        timer.start();
    }

    // Actualiza el laberinto con el número de filas y columnas especificado
    private void updateMaze() {
        try {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());
            this.size = Math.max(rows, cols); // Asegura que el tamaño sea suficiente para el laberinto
            this.laberinto = new boolean[size][size];
            gridPanel.removeAll();
            gridPanel.setLayout(new GridLayout(size, size));
            botones = new JButton[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    botones[i][j] = new JButton();
                    botones[i][j].setBackground(Color.WHITE);
                    botones[i][j].setOpaque(true);
                    botones[i][j].setBorderPainted(true);
                    final int x = i;
                    final int y = j;
                    botones[i][j].addActionListener(e -> toggleCell(x, y));
                    gridPanel.add(botones[i][j]);
                }
            }

            gridPanel.revalidate();
            gridPanel.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese números válidos para filas y columnas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpia el laberinto y restablece todas las celdas a blanco
    private void clearMaze() {
        for (Component component : gridPanel.getComponents()) {
            JButton cell = (JButton) component;
            cell.setBackground(Color.WHITE);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                laberinto[i][j] = false;
            }
        }
    }
}