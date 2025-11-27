package com.example.laboratorioaspectosbasicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorioaspectosbasicos.ui.theme.LaboratorioAspectosBasicosTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaboratorioAspectosBasicosTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    CenterAlignedTopAppBar(
                        /* Para poder ponerle un color al topAppBar hay que definir todos sus atributos
                        * titleContentColor -> color del título
                        * containerColor -> Color de fondo del topAppBar
                        * actionIconContentColor -> Controla el color de los iconos de acción situados a
                        * la derecha de la barra (como el icono de buscar, ajustes o el menú de tres puntos)
                        * navigationIconContentColor -> Define el color del icono situado a la izquierda
                        * del título. Usualmente es la flecha de "volver atrás" o el icono de menú (hamburguesa)
                        * navigarionIconContentColor -> Es el color de fondo que adopta la barra solo cuando
                        * haces scroll y el contenido pasa por debajo de ella. Sirve para dar
                        * una señal visual de que la lista continúa hacia arriba*/
                        colors = TopAppBarColors(
                            titleContentColor = colorResource(R.color.white),
                            containerColor = colorResource(R.color.purple_200),
                            actionIconContentColor = colorResource(R.color.white),
                            scrolledContainerColor = colorResource(R.color.white),
                            navigationIconContentColor = colorResource(R.color.white)
                        ),
                        title = {
                            Text(
                                "Pruebas varias",
                                style = TextStyle(
                                    fontFamily = FontFamily.Cursive,
                                    fontSize = 28.sp
                                ),
                            )
                        },
                    )
                }) { innerPadding ->
                    columnaPersonalizada(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun columnaPersonalizada(
    // Lista con 1000 elementos, su contenido es el índice como String
    names: List<String> = List(1000) { "$it" },
    modifier: Modifier = Modifier
) {
    var continuar by rememberSaveable { mutableStateOf(false) }
    if (continuar) {
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(items = names) { index ->
                saludo(index = index)
            }
        }
    } else {
        OnboardingScreen(modifier = modifier, subirValor = { newValue -> continuar = newValue })
    }
}

@Composable
fun saludo(modifier: Modifier = Modifier, index: String) {
    // Hacemos que se recomponga la activity con el mutableStateOf()
    var expanded by rememberSaveable { mutableStateOf(false) }
    // ExtraPadding -> Una varible que va a expandir nuestra fila
    /*val extraPadding by animateDpAsState(
        if (!expanded) 0.dp
        else 48.dp,
        // animationSpec -> Para darle una animación a animateDpAsState que es para cambiar la densidad de píxeles
        animationSpec = spring(
            // Esta propiedad define un rebote, en este caso medio
            dampingRatio = Spring.DampingRatioMediumBouncy,
            // Esta propiedad define cuanto tarda en volver a su sitio, le decimos que sea lenta la transformación de dp
            stiffness = Spring.StiffnessLow,
        )
    )*/
    // Define el color, dependiendo de si se expande o no, será de un color u otro
    val color by animateColorAsState(
        if (!expanded) MaterialTheme.colorScheme.primary
        else colorResource(R.color.purple_500)
    )
    /* Surface es como una hija de estilos para elemntos como botones, textos y demás, para darle color, etc.
    * Lo he sustituido por card para dejar unos bordes redondeados */
    Card(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            /*.border(color = colorResource(R.color.black), width = 5.dp)*/,
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .animateContentSize(
                        // Aquí ya no es necesario los coerce, de esta forma ya es segura la animación
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                /* coerceAtLeast(0.dp) -> Esto evita que el efecto rebote haga que sea negtivo
                 el valor del padding */
                /*.padding(bottom = extraPadding.coerceAtLeast(0.dp))*/
            ) {
                Text("Hola")
                Text(
                    /* Le doy de MaterialTheme una cabezera mediana al texto en negrita,
                     con copy, ya que este permite modificar MaterialTheme porque es
                     una copia*/
                    index, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                if (expanded) for (i in 0..5) {
                    Text(stringResource(R.string.contenido))
                }
            }
            // IconButton para mostrar el contenido
            IconButton(
                onClick = {
                    expanded = !expanded
                },
            ) {
                Icon(
                    if (!expanded) Icons.Filled.ExpandMore else Icons.Filled.ExpandLess,
                    contentDescription = if (!expanded) stringResource(R.string.botonMostrarMas)
                    else stringResource(R.string.botonMostrarMenos)
                )
            }
        }
    }
}

@Composable
fun OnboardingScreen(modifier: Modifier, subirValor: (Boolean) -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            onClick = {
                subirValor(true)
            }, modifier = Modifier.padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.purple_200))
        ) {
            Text("Continue")
        }
    }
}