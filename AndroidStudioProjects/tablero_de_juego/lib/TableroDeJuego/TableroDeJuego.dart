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
            flex: 2,
            child: Container(
              padding: EdgeInsets.all(5),
              color: Color.fromARGB(255, 55, 71, 79),
              // Fila de parte de arriba (gris)
              child: Row(
                children: [
                  // Tablero 4x4
                  Expanded(
                    flex:3,
                    child: Container(
                      color: Colors.yellow,
                    ),
                  ),
                  Expanded(
                    flex: 2,
                    child: Container(
                      color: Colors.red,
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              color: Colors.black12,
            ),
          )
        ],
      ),
    );
  }
}
