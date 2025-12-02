package com.santosgo.marvelheroescompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.santosgo.marvelheroescompose.R
import com.santosgo.marvelheroescompose.model.Hero
import com.santosgo.marvelheroescompose.ui.theme.extendedColors
import com.santosgo.marvelheroescompose.model.Datasource

@Composable
fun StandardInputTextComp(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) }
    )
}

@Composable
fun ImageComp(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    drawable: Int,
    contentDesc: String = "",
    height: Int = 0,
    width: Int = 0
) {
    val contentDescription =
        if (contentDesc == "")
            stringResource(id = R.string.default_content_descrip)
        else
            contentDesc
    if (height != 0 && width != 0) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = contentDescription,
            modifier
                .height(height.dp)
                .width(width.dp),
            contentScale = contentScale
        )
    } else {
        Image(
            modifier = modifier,
            painter = painterResource(id = drawable),
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    }
}

@Composable
fun StandardButtonComp(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .padding(8.dp),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(text = label)
    }
}

@Composable
fun LabelAndValueComp(label: String, modifier: Modifier = Modifier, value: String = "") {
    Text(
        modifier = modifier,
        text = "$label = $value"
    )
}

@Composable
fun StandardTextComp(
    text: String,
    modifier: Modifier = Modifier,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyMedium
) {
    Text(
        modifier = modifier,
        text = text,
        style = style
    )
}

/**
 * Esta función va a definir la imagen del héroe con su descripción,
 * el tamaño de la imagen y modifier si se quisiera
 */
@Composable
fun StandardImage(
    modifier: Modifier = Modifier,
    height: Int,
    width: Int,
    hero: Hero
) {
    Image(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        contentDescription = hero.photo,
        painter = painterResource(id = Datasource.getDrawableIdByName(hero.photo)),
    )
}

/**
 * Este composable representa la cabecera del título de la app
 */
@Composable
fun MedHeaderComp(title: String) {
    Surface(
        // RoundedCornerShape para redondear los corner
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = MaterialTheme.extendedColors.customHeader.color,
        contentColor = MaterialTheme.extendedColors.customHeader.onColor
    ) {
        // Metemos el texto en un Box para poder centrar el título
        Box(modifier = Modifier.fillMaxWidth()) {
            StandardTextComp(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

/**
 * Esta función va a crear la pantalla de perfil para introducir un nombre
 * de usuario y entrar
 */
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navigateToHeroes: () -> Unit) {
    // Creamos el nombre
    var nombre by rememberSaveable { mutableStateOf("") }
    var apretado by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(bottom = 50.dp),
            value = nombre,
            onValueChange = { newValue -> nombre = newValue },
            label = {
                Text(stringResource(R.string.textFieldLabel))
            }
        )
        Image(
            // Para hacer que la imagen quepa en el círculo sin deformarse
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(bottom = 50.dp)
                .height(150.dp)
                .width(150.dp)
                // Para cambiar la forma de la imagen
                .clip(shape = CircleShape),
            painter = painterResource(R.drawable.mh_icono),
            contentDescription = stringResource(R.string.descripcionLoginImagen)
        )
        ElevatedButton(onClick = {
            if (nombre.isNotBlank()) {
                apretado = !apretado
                navigateToHeroes()
            }
        }) {
            if (!apretado) Text(stringResource(R.string.botonLogin))
            else Text(stringResource(R.string.botonLogout))
        }
    }
}

/**
 * Esta función va a mostrar la lista de héroes dependiendo del
 * tamaño de la pantalla (WindowWidthSizeClass)
 */
@Composable
fun HeroesScreen(
    modifier: Modifier,
    widowSize: WindowWidthSizeClass,
    goToDetails: (String) -> Unit
) {
    when (widowSize) {
        // Si el tamaño es compacto, elegimos ese composable
        WindowWidthSizeClass.Compact -> HeroListScreenCompact(
            heroes = Datasource.getListXtimes(10),
            modifier = modifier,
            // Subimos el valor del héroe, esto nos servirá para viajar a la pantalla detalles
            subirNombre = { nombre -> goToDetails(nombre) }
        )

        else -> HeroListScreenExp(
            heroes = Datasource.getListXtimes(10),
            modifier = modifier
        )
    }
}

/**
 * Esta pantalla va a mostrar la pantalla de héroes favoritos
 * con el formato correspondido dependiendo del tamaño de la pantalla
 */
@Composable
fun HeroScreenFav(modifier: Modifier, widowSize: WindowWidthSizeClass) {
    when (widowSize) {
        WindowWidthSizeClass.Compact -> HeroListFavCompact(
            Datasource.getSomeRandHeroes(4),
            modifier = modifier
        )

        else -> HeroListFavCompact(
            Datasource.getSomeRandHeroes(4),
            modifier = modifier
        )
    }
}

/**
 * Esta función representa el controlador de las rutas
 */
@Composable
fun NavigatorContent(modifier: Modifier = Modifier, windowSize: WindowWidthSizeClass) {
    // Creamos el controlador de la navegación
    val navController = rememberNavController()

    // Creamos el mapa de rutas posibles, la ruta inicial será la de 'profile'
    NavHost(navController = navController, startDestination = "profile") {
        // Definimos la ruta de 'profile'
        composable("profile") {
            ProfileScreen(modifier = modifier) { navController.navigate("heroes") }
        }
        // Definimos la ruta de 'heroes' para mostrar la lista de héroes
        composable("heroes") {
            HeroesScreen(modifier, windowSize) { nombreHeroe ->
                navController.navigate("detailsHeroe/$nombreHeroe")
            }
        }
        // Definimos la ruta de 'favHeroes' para mostrar la lista de héroes favoritos
        composable("favHeroes") {
            HeroScreenFav(modifier, windowSize)
        }
        // Definimos la ruta de 'detailsHeroe' pasándole el nombre del héroe
        composable("detailsHeroe/{heroeName}") { backStackEntry ->
            val nombreHeroe = backStackEntry.arguments?.getString("heroeName") ?: ""
            HeroDetailCompactScreen(nombreHeroe, modifier) { navController.popBackStack() }
        }
    }
}

/**
 * Esta función va a mostrar concretamente un héroe buscado por el nombre
 * en caso de que exista, se muestra como una información detallada de este
 */
@Composable
fun HeroDetailCompactScreen(hero_name: String, modifier: Modifier = Modifier, goBack: () -> Unit) {
    // El héroe buscado por su nombre (hero_name)
    val hero = Datasource.getHeroByName(hero_name)
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // En caso de que el héroe exista y por lo tanto 'hero' no sea null
        hero?.let {
            // La imagen del héroe encontrado
            ImageComp(
                // Imagen del héroe obtenida por su nombre
                drawable = Datasource.getDrawableIdByName(hero.photo),
                contentDesc = "${stringResource(R.string.descripcion)}: ${hero.name}",
                contentScale = ContentScale.FillWidth, // Esta propiedad hace que se ensanche la imagen tanto como pueda sin deformarse
                modifier = Modifier
                    .padding(16.dp) // Funciona como un margin
                    .widthIn(200.dp, 300.dp) // Esta propiedad establece el ancho mínimo y máximo
                    .fillMaxWidth(), // Ocupará los 300 píxeles
            )
            // Después de la imagen se muestra el nombre del héroe
            Spacer(modifier = Modifier.height(16.dp))
            StandardTextComp(
                text = hero.name,
                style = MaterialTheme.typography.headlineMedium
            )
            // Posteriormente en una fila se muestra su poder e inteligencia
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StandardTextComp(text = "${stringResource(R.string.nombrePoder)}: ${hero.power}")
                StandardTextComp(text = "${stringResource(R.string.nombreInteligencia)}: ${hero.intelligence}")
            }
            // Por último se muestra la descripción del héroe
            Spacer(modifier = Modifier.height(16.dp))
            StandardTextComp(
                text = hero.description,
                style = MaterialTheme.typography.bodyMedium
            )
            // En caso de que no se encuentre al héroe se muestra que no se ha encontrado
        } ?: StandardTextComp(
            stringResource(R.string.noEncontrado),
            style = MaterialTheme.typography.headlineMedium
        )
        // Se encuentre o no al héroe se mostrará el botón para volver atrás
        Spacer(modifier = Modifier.height(32.dp))
        StandardButtonComp(label = stringResource(R.string.botonAtras), onClick = { goBack() })
    }
}

@Preview
@Composable
fun HeroDetailCompactScreenPreview() {
    HeroDetailCompactScreen("Hulk", goBack = {})
}
