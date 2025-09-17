package com.example.poo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.poo.ui.theme.POOTheme
import com.example.poo.Pokemon

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Creamos el objeto persona con constructor vacío
        var alberto: Person = Person("Alberto", "97234")
        // Creamos otra persona
        var anonimo: Person = Person()

        // Imprimimos si está vivo o no
        println(alberto.alive)

        // Matamos a la persona y volvemos a imprimir
        alberto.die()
        println(alberto.alive)

        // Imprimimos por pantella el nombre y el pasaporte
        println("Alberto:")
        println(alberto.name)
        println(alberto.passport)

        // Imprimimos el nombre y pasaporte del anónimo
        println("Anónimo:")
        println(anonimo.name)
        println(anonimo.passport)

        // Creamos un athlete
        var isi = Athlete("Isi Palazón", "398734", "Football")
        // Imprimos sus atributos
        println("Nombre: ${isi.name}, Pasaporte: ${isi.passport}, Deporte: ${isi.sport}" +
                "\nVivo: ${isi.alive}")
        isi.die()
        println("Vivo: ${isi.alive}")

        // Creamos un pokemon
        val pokemon = Pokemon()
        // Modificamos el valor de los atributos
        pokemon.setName("Carles")
        pokemon.setLife(40f)
        pokemon.setAttackPower(1f)
        // Imprimimos sus atributos
        println("Nombre: ${pokemon.getName()}, Ataque: ${pokemon.getAttackPower()}, Vida: ${pokemon.getLife()}")

        // Creamos  un pokemon de tierra
        var pokemonTierra = EarthPokemon("Pepe", 30.5f, 90.5f, 20, 3)
        pokemonTierra.despedirse()
    }
}