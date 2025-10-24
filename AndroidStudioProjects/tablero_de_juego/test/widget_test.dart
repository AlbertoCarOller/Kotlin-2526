import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

// 1. IMPORTA SOLO TU WIDGET, el que sí existe
import 'package:tablero_de_juego/TableroDeJuego/TableroDeJuego.dart';

// 2. ELIMINA la línea que daba error:
// import 'package:tablero_de_juego/main.dart';

void main() {
  // 3. Cambia el nombre del test a algo que tenga sentido
  testWidgets('El tablero se muestra con el título y los escudos', (WidgetTester tester) async {

    // Construye tu app
    await tester.pumpWidget(const TableroDeJuego());

    // 4. Escribe tests que SÍ comprueban tu app

    // Verifica que el título de tu AppBar es correcto
    expect(find.text('Tablero de juego táctico'), findsOneWidget);

    // Verifica que NO están los widgets del contador
    expect(find.text('0'), findsNothing);
    expect(find.byIcon(Icons.add), findsNothing);

    // Verifica que SÍ aparecen tus iconos de escudo
    // (que están dentro de tus ContenedorGris)
    // Usamos 'findsWidgets' porque esperas encontrar más de uno.
    expect(find.byIcon(Icons.shield), findsWidgets);

    // Si sabes exactamente cuántos escudos debe haber (por ejemplo, 16 en una 4x4)
    // podrías ser más específico:
    // expect(find.byIcon(Icons.shield), findsNWidgets(16));
  });
}