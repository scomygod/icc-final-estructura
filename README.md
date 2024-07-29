![image](https://github.com/user-attachments/assets/407ffc52-466e-46c8-8b12-080f514b51e7)
Informe y Documentación
Descripción del Problema
El problema que se aborda es la resolución de laberintos mediante algoritmos de búsqueda. La tarea consiste en encontrar un camino desde un punto de inicio hasta un punto final en el laberinto, evitando las paredes y seleccionando el mejor algoritmo para la resolución.

Marco Teórico
Programación Dinámica (Caché): Técnica que resuelve problemas dividiéndolos en partes más pequeñas y guardando los resultados para no hacer cálculos repetidos.
BFS (Breadth-First Search): Algoritmo que explora los nodos vecinos en orden de distancia desde el inicio. Ideal para encontrar el camino más corto en grafos sin pesos.
DFS (Depth-First Search): Algoritmo que explora profundamente a lo largo de cada rama antes de retroceder. No siempre encuentra el camino más corto tan rápido como BFS.
Recursivo: Método de búsqueda que usa la recursión para explorar caminos. Es fácil de implementar pero puede usar más tiempo y memoria.

Descripción de la Propuesta de Solución
Se implementaron los siguientes métodos de búsqueda para resolver el laberinto:
BFS: Utiliza una cola para explorar los nodos y encontrar el camino más corto.
DFS: Utiliza una pila para explorar los nodos profundamente antes de retroceder.
Recursivo: Utiliza recursión para encontrar el camino y es más sencillo de implementar pero menos eficiente.
Cache: Variante del método recursivo que usa un caché para almacenar caminos previamente explorados y evitar cálculos repetidos.

Se utilizó Java para el desarrollo, con las siguientes clases principales:
LaberintoModel: Representa las celdas del laberinto y sus conexiones.
LaberintoController: Contiene la lógica de los algoritmos de búsqueda.
LaberintoView: Proporciona la interfaz gráfica para interactuar con el laberinto.

Criterio por Integrante
Cada integrante del equipo contribuyó en diferentes áreas:

Gabriel contribuyó con: Diseño de la Interfaz: Creación de la interfaz gráfica para la visualización del laberinto y la interacción del usuario y el desarrollo de los algoritmos BFS y DFS.
Adrian contribuyó con: Desarrollo del Algoritmos: Implementación y optimización de los algoritmos de búsqueda recursiva y con caché. Tambien con la documentación del proyecto: Redacción de la documentación y la guía de usuario.

Capturas de la Implementación Final de la UI
![image](https://github.com/user-attachments/assets/19bc08a6-a7b3-41b2-800c-48a1fdb81484)
BFS:
![image](https://github.com/user-attachments/assets/0cf9fa25-2e7f-4610-bec5-08e0e4cf53ca)
DFS:
![image](https://github.com/user-attachments/assets/258b0679-40ac-4e17-bea5-b89c007af13c)
Recursivo:
![image](https://github.com/user-attachments/assets/cd990ad9-7814-4e6e-9bd9-657595583d1b)
Caché:
![image](https://github.com/user-attachments/assets/562b1748-e2fa-47ab-9313-566d92917da2)

Conclusiones
Entre los métodos de búsqueda implementados, el método recursivo resultó ser el más rápido y eficiente para nuestro caso específico. Aunque en general el algoritmo BFS es conocido por encontrar el camino más corto de manera efectiva, en este proyecto, el enfoque recursivo demostró ser más rápido. Esto puede deberse a características específicas del laberinto como su tamaño. En la mayoría de pruebas con la matriz de tamaño 10 x 10, el método Recursivo se mostró más rápido al encontrar el camino.

Consideraciones
Para futuras mejoras del proyecto, se podrían considerar:

Adrian: Optimización de Algoritmos, además de los métodos utilizados, se podrían explorar otros algoritmos de búsqueda y optimización, como el A* (A-star) que utiliza heurísticas para mejorar la eficiencia en la búsqueda de caminos, o el algoritmo de Dijkstra para laberintos ponderados.
Gabriel: Mejora de la Interfaz Gráfica, ampliar la interfaz gráfica para incluir características adicionales, como la visualización de la búsqueda en tiempo real, opciones de personalización para el usuario, de manera que se asemeje más a un juego.
