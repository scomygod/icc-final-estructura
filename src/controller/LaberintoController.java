package controller;

import models.LaberintoModel;
import view.LaberintoView;

import java.util.*;

public class LaberintoController {
    private LaberintoView vista;

    public LaberintoController(LaberintoView vista) {
        this.vista = vista;
    }

    public void setVista(LaberintoView vista) {
        this.vista = vista;
    }

    // Algoritmo BFS 
    public List<LaberintoModel> getPathBFS(boolean[][] laberinto, int inicioX, int inicioY, int finX, int finY) {
        int filas = laberinto.length;
        int columnas = laberinto[0].length;
        int[][] direcciones = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Queue<LaberintoModel> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        LaberintoModel inicio = new LaberintoModel(inicioX, inicioY, finX, finY);
        cola.add(inicio);
        visitados.add(inicioX + "," + inicioY);

        while (!cola.isEmpty()) {
            LaberintoModel actual = cola.poll();
            if (actual.getInicioX() == finX && actual.getInicioY() == finY) {
                return reconstruirCamino(actual);
            }
            for (int[] direccion : direcciones) {
                int nuevoX = actual.getInicioX() + direccion[0];
                int nuevoY = actual.getInicioY() + direccion[1];
                if (esValido(nuevoX, nuevoY, filas, columnas, laberinto, visitados)) {
                    LaberintoModel vecino = new LaberintoModel(nuevoX, nuevoY, finX, finY);
                    vecino.setPadre(actual);
                    cola.add(vecino);
                    visitados.add(nuevoX + "," + nuevoY);
                }
            }
        }
        return Collections.emptyList();
    }

    // Algoritmo DFS 
    public List<LaberintoModel> getPathDFS(boolean[][] laberinto, int inicioX, int inicioY, int finX, int finY) {
        int filas = laberinto.length;
        int columnas = laberinto[0].length;
        int[][] direcciones = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Stack<LaberintoModel> pila = new Stack<>();
        Set<String> visitados = new HashSet<>();
        LaberintoModel inicio = new LaberintoModel(inicioX, inicioY, finX, finY);
        pila.add(inicio);
        visitados.add(inicioX + "," + inicioY);

        while (!pila.isEmpty()) {
            LaberintoModel actual = pila.pop();
            if (actual.getInicioX() == finX && actual.getInicioY() == finY) {
                return reconstruirCamino(actual);
            }
            for (int[] direccion : direcciones) {
                int nuevoX = actual.getInicioX() + direccion[0];
                int nuevoY = actual.getInicioY() + direccion[1];
                if (esValido(nuevoX, nuevoY, filas, columnas, laberinto, visitados)) {
                    LaberintoModel vecino = new LaberintoModel(nuevoX, nuevoY, finX, finY);
                    vecino.setPadre(actual);
                    pila.add(vecino);
                    visitados.add(nuevoX + "," + nuevoY);
                }
            }
        }
        return Collections.emptyList();
    }

    // Algoritmo recursivo 
    public List<LaberintoModel> getPathRecursivo(boolean[][] laberinto, int inicioX, int inicioY, int finX, int finY) {
        Set<String> visitados = new HashSet<>();
        List<LaberintoModel> camino = new ArrayList<>();
        if (buscarRecursivo(laberinto, inicioX, inicioY, finX, finY, visitados, camino)) {
            return camino;
        }
        return Collections.emptyList();
    }

    private boolean buscarRecursivo(boolean[][] laberinto, int x, int y, int finX, int finY, Set<String> visitados, List<LaberintoModel> camino) {
        if (x == finX && y == finY) {
            camino.add(new LaberintoModel(x, y, finX, finY));
            return true;
        }

        int[][] direcciones = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        visitados.add(x + "," + y);
        camino.add(new LaberintoModel(x, y, finX, finY));

        for (int[] direccion : direcciones) {
            int nuevoX = x + direccion[0];
            int nuevoY = y + direccion[1];
            if (esValido(nuevoX, nuevoY, laberinto.length, laberinto[0].length, laberinto, visitados)) {
                if (buscarRecursivo(laberinto, nuevoX, nuevoY, finX, finY, visitados, camino)) {
                    return true;
                }
            }
        }

        camino.remove(camino.size() - 1);
        return false;
    }

    // Algoritmo con Caché
    public List<LaberintoModel> getPathCache(boolean[][] laberinto, int inicioX, int inicioY, int finX, int finY) {
        Set<String> visitados = new HashSet<>();
        Map<String, List<LaberintoModel>> cache = new HashMap<>();
        if (buscarConCache(laberinto, inicioX, inicioY, finX, finY, visitados, new ArrayList<>(), cache)) {
            return cache.get(finX + "," + finY);
        }
        return Collections.emptyList();
    }

    private boolean buscarConCache(boolean[][] laberinto, int x, int y, int finX, int finY, Set<String> visitados, List<LaberintoModel> camino, Map<String, List<LaberintoModel>> cache) {
        if (cache.containsKey(x + "," + y)) {
            camino.addAll(cache.get(x + "," + y));
            return true;
        }

        if (x == finX && y == finY) {
            camino.add(new LaberintoModel(x, y, finX, finY));
            cache.put(x + "," + y, new ArrayList<>(camino));
            return true;
        }

        int[][] direcciones = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        visitados.add(x + "," + y);
        camino.add(new LaberintoModel(x, y, finX, finY));

        for (int[] direccion : direcciones) {
            int nuevoX = x + direccion[0];
            int nuevoY = y + direccion[1];
            if (esValido(nuevoX, nuevoY, laberinto.length, laberinto[0].length, laberinto, visitados)) {
                if (buscarConCache(laberinto, nuevoX, nuevoY, finX, finY, visitados, camino, cache)) {
                    return true;
                }
            }
        }

        camino.remove(camino.size() - 1);
        return false;
    }

    // Verifica si hay como pasar por una celda
    public boolean esValido(int x, int y, int filas, int columnas, boolean[][] laberinto, Set<String> visitados) {
        return x >= 0 && x < filas && y >= 0 && y < columnas && !laberinto[x][y] && !visitados.contains(x + "," + y);
    }

    // Reconstruye el camino desde el nodo final hasta el nodo inicial
    private List<LaberintoModel> reconstruirCamino(LaberintoModel nodo) {
        List<LaberintoModel> camino = new ArrayList<>();
        while (nodo != null) {
            camino.add(nodo);
            nodo = nodo.getPadre();
        }
        Collections.reverse(camino);
        return camino;
    }
}