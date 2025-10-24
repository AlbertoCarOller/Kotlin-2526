import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(TableroDeJuego());
}

class TableroDeJuego extends StatelessWidget {
  const TableroDeJuego({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "TableroDeJuego",
      home: Tablero(),
      theme: ThemeData(useMaterial3: true),
    );
  }
}

// Creo un widget propio que es el contenedor
class ContenedorGris extends StatelessWidget {
  const ContenedorGris({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.all(2),
      decoration: BoxDecoration(
        color: Color.fromARGB(255, 84, 110, 122),
        borderRadius: BorderRadius.circular(5),
      ),
      child: Center(
        child: Icon(Icons.shield, color: Color.fromARGB(255, 170, 183, 189)),
      ),
    );
  }
}

class Tablero extends StatelessWidget {
  const Tablero({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Tablero de juego táctico"),
        backgroundColor: Color.fromARGB(255, 38, 50, 56),
      ),
      // La columna de la estructura principal
      body: Column(
        children: [
          Expanded(
            flex: 3,
            // Container de la estructura completa de arriba
            child: Container(
              padding: EdgeInsets.all(5),
              color: Color.fromARGB(255, 55, 71, 79),
              // Fila de parte de arriba (gris)
              child: Row(
                children: [
                  // Creamos las filas
                  for (int i = 0; i < 4; i++)
                    Expanded(
                      flex: 1,
                      child: Row(
                        children: [
                          Expanded(
                            child: Column(
                              children: [
                                // Creamos las columnas
                                for (int i = 0; i < 4; i++)
                                  Expanded(child: ContenedorGris()),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  // Container, parte izquierza, columna con los dos jugadores
                  Expanded(
                    flex: 2,
                    child: Container(
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(5),
                        color: Color.fromARGB(255, 69, 90, 100),
                      ),
                      // Columna general
                      child: Expanded(
                        child: Column(
                          children: [
                            // Columna jugador azul
                            Expanded(
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  // Texto
                                  Text(
                                    "Jugador 1 (Azul)",
                                    style: TextStyle(color: Colors.white),
                                  ),
                                  // Icono jugador azul
                                  Icon(Icons.person, color: Colors.blue),
                                  Text(
                                    "Puntos: 120",
                                    style: TextStyle(
                                      fontSize: 8,
                                      color: Colors.white,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                            // Columna de jugador rojo
                            Expanded(
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  // Texto
                                  Text(
                                    "Jugador 2 (Rojo)",
                                    style: TextStyle(color: Colors.white),
                                  ),
                                  // Icono jugador rojo, _outline para que no tenga relleno
                                  Icon(Icons.person_outline, color: Colors.red),
                                  Text(
                                    "Puntos: 120",
                                    style: TextStyle(
                                      fontSize: 8,
                                      color: Colors.white,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
          // Creamos la parte de abajo, donde están los comentarios
          Expanded(
            child: Column(
              children: [
                Expanded(child: Text("Historial de Movimientos")),
                Expanded(
                  flex: 3,
                  child: ListView.separated(
                    itemCount: 20,
                    itemBuilder: (context, index) {
                      return Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Row(
                            children: [
                              // Círculo de avatar
                              Container(
                                margin: EdgeInsets.only(right: 8),
                                child: CircleAvatar(
                                  radius: 18,
                                  backgroundColor: (index % 2 == 0)
                                      ? Colors.blue
                                      : Colors.deepOrange,
                                  child: Text(
                                    "${index + 1}",
                                    style: TextStyle(color: Colors.white),
                                  ),
                                ),
                              ),
                              // Textos
                              Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    "Jugador ${index + 1} movió pieza",
                                    style: TextStyle(fontSize: 15),
                                  ),
                                  Text(
                                    "Caballero a la casilla C4",
                                    style: TextStyle(fontSize: 10),
                                  ),
                                ],
                              ),
                            ],
                          ),
                          Icon(Icons.history_sharp, size: 20),
                        ],
                      );
                    },
                    separatorBuilder: (BuildContext context, int index) {
                      return SizedBox(height: 15);
                    },
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
