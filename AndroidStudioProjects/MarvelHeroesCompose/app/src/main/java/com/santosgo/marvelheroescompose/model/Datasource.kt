/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.santosgo.mavelheroes.data

import com.santosgo.marvelheroescompose.R
import com.santosgo.marvelheroescompose.model.Hero
import com.santosgo.marvelheroescompose.model.Technique

object Datasource {

    private val techniques = mapOf(
        "Capitán América" to mutableListOf(
            Technique("Escudo Vengador", 1, "Lanza su escudo con precisión letal."),
            Technique("Golpe Patriota", 2, "Un poderoso golpe con ideales americanos."),
            Technique("Carga Estrella", 3, "Ataca con un embate frontal cargado."),
            Technique("Defensa de Vibranium", 4, "Bloquea ataques con su escudo indestructible."),
            Technique("Arremetida Heroica", 5, "Embiste al enemigo con gran fuerza."),
            Technique("Llamado a la Libertad", 6, "Motiva a aliados aumentando su moral."),
            Technique("Ataque Giratorio", 7, "Realiza un giro rápido golpeando a múltiples enemigos."),
            Technique("Contraataque Estratégico", 8, "Usa tácticas para contraatacar eficazmente."),
            Technique("Salto Heroico", 9, "Salta largas distancias para sorprender al enemigo."),
            Technique("Puño de la Justicia", 10, "Un golpe certero representando la justicia.")
        ),
        "Iron Man" to listOf(
            Technique("Rayo Repulsor", 1, "Dispara un rayo de energía desde su armadura."),
            Technique("Misil Inteligente", 2, "Lanza un misil guiado hacia el enemigo."),
            Technique("Unirayo", 3, "Descarga máxima de energía desde el reactor."),
            Technique("Láser Cortante", 4, "Emite un láser capaz de cortar metal."),
            Technique("Ataque Aéreo", 5, "Ataca desde el cielo a alta velocidad."),
            Technique("Campo de Fuerza", 6, "Genera un escudo energético temporal."),
            Technique("Descarga Electromagnética", 7, "Emite una onda que desactiva dispositivos electrónicos."),
            Technique("Micro Misiles", 8, "Lanza una andanada de pequeños misiles."),
            Technique("Golpe Potenciado", 9, "Aumenta la fuerza de sus puños con energía."),
            Technique("Análisis Táctico", 10, "Escanea al enemigo para encontrar debilidades.")
        ),
        "Hulk" to listOf(
            Technique("Aplastar", 1, "Golpea el suelo causando temblores."),
            Technique("Grito Furioso", 2, "Emite un rugido que aturde al enemigo."),
            Technique("Lanzamiento de Roca", 3, "Lanza grandes rocas al oponente."),
            Technique("Golpe de Trueno", 4, "Golpea sus manos creando una onda de choque."),
            Technique("Salto Gigante", 5, "Salta y cae sobre el enemigo desde gran altura."),
            Technique("Puño Destructor", 6, "Un golpe devastador con su puño."),
            Technique("Resistencia Gamma", 7, "Reduce el daño recibido temporalmente."),
            Technique("Carga Irresistible", 8, "Corre hacia adelante arrasando con todo."),
            Technique("Desgarro de Metal", 9, "Destruye estructuras metálicas con facilidad."),
            Technique("Furia Incontenible", 10, "Aumenta su poder cuanto más enojado está.")
        ),
        "Viuda Negra" to listOf(
            Technique("Patada Giratoria", 1, "Una patada acrobática al enemigo."),
            Technique("Disparo Eléctrico", 2, "Dispara proyectiles que electrocutan al oponente."),
            Technique("Invisibilidad", 3, "Usa camuflaje para ocultarse."),
            Technique("Ataque Sigiloso", 4, "Ataca por sorpresa causando más daño."),
            Technique("Látigo Electrónico", 5, "Utiliza un látigo que genera electricidad."),
            Technique("Esquiva Ágil", 6, "Evade ataques con movimientos rápidos."),
            Technique("Explosivos Adhesivos", 7, "Coloca pequeñas bombas en el enemigo."),
            Technique("Hackeo Rápido", 8, "Desactiva dispositivos electrónicos cercanos."),
            Technique("Golpe de Precisión", 9, "Ataca puntos débiles del oponente."),
            Technique("Combate Cuerpo a Cuerpo", 10, "Usa artes marciales para incapacitar al enemigo.")
        ),
        "Thor" to listOf(
            Technique("Martillo Mjolnir", 1, "Golpea con su poderoso martillo."),
            Technique("Rayo de Trueno", 2, "Invoca un rayo para electrocutar al enemigo."),
            Technique("Vuelo Asgardiano", 3, "Vuela a altas velocidades."),
            Technique("Torbellino", 4, "Gira su martillo creando un tornado."),
            Technique("Golpe Divino", 5, "Ataca con la fuerza de un dios."),
            Technique("Llamado de la Tormenta", 6, "Controla el clima para desatar una tormenta."),
            Technique("Escudo de Rayos", 7, "Crea un escudo eléctrico protector."),
            Technique("Impacto Sónico", 8, "Genera ondas sónicas con su martillo."),
            Technique("Portal Bifrost", 9, "Se teletransporta a través del Bifrost."),
            Technique("Cólera de Odín", 10, "Libera un poder ancestral devastador.")
        ),
        "Capitana Marvel" to listOf(
            Technique("Explosión Fotónica", 1, "Dispara energía fotónica desde sus manos."),
            Technique("Vuelo Interestelar", 2, "Vuela a velocidades de la luz."),
            Technique("Golpe Cósmico", 3, "Usa energía cósmica en sus ataques."),
            Technique("Campo de Fuerza", 4, "Crea un escudo energético."),
            Technique("Absorción de Energía", 5, "Absorbe energía para fortalecerse."),
            Technique("Rayo Destructor", 6, "Dispara un rayo de alta potencia."),
            Technique("Golpe Estelar", 7, "Un poderoso puñetazo potenciado."),
            Technique("Explosión de Pulsar", 8, "Libera una explosión masiva de energía."),
            Technique("Visión Energética", 9, "Emite rayos de energía desde sus ojos."),
            Technique("Aura Cósmica", 10, "Aumenta su resistencia y poder temporalmente.")
        ),
        "Spiderman" to listOf(
            Technique("Telaraña Impacto", 1, "Lanza una bola de telaraña al enemigo."),
            Technique("Sentido Arácnido", 2, "Esquiva ataques con anticipación."),
            Technique("Patada Araña", 3, "Da una patada acrobática."),
            Technique("Red Envolvente", 4, "Atrapa al enemigo en una telaraña."),
            Technique("Golpe Rápido", 5, "Ataca rápidamente con los puños."),
            Technique("Balanceo", 6, "Se mueve rápidamente por el entorno."),
            Technique("Telaraña Eléctrica", 7, "Lanza telarañas que electrocutan."),
            Technique("Impacto Descendente", 8, "Cae desde arriba golpeando al enemigo."),
            Technique("Araña Robots", 9, "Despliega pequeños robots arácnidos."),
            Technique("Emboscada", 10, "Ataca desde un lugar oculto.")
        ),
        "Star Lord" to listOf(
            Technique("Disparo Elemental", 1, "Dispara elementos como fuego o hielo."),
            Technique("Jet Boost", 2, "Usa sus botas para maniobras rápidas."),
            Technique("Granada Gravitacional", 3, "Lanza una granada que altera la gravedad."),
            Technique("Ataque Sónico", 4, "Emite ondas sonoras para aturdir."),
            Technique("Evasión Ágil", 5, "Esquiva ataques con destreza."),
            Technique("Golpe Cósmico", 6, "Ataca con energía cósmica."),
            Technique("Control Remoto", 7, "Controla dispositivos a distancia."),
            Technique("Máscara de Visión", 8, "Utiliza su máscara para mejorar su puntería."),
            Technique("Llamado de la Milano", 9, "Solicita apoyo de su nave espacial."),
            Technique("Chiste Oportuno", 10, "Desconcentra al enemigo con humor.")
        ),
        "Gamora" to listOf(
            Technique("Espada Mortal", 1, "Ataca con su espada afilada."),
            Technique("Acrobacia Letal", 2, "Movimientos ágiles para atacar."),
            Technique("Sigilo Asesino", 3, "Se acerca sin ser detectada."),
            Technique("Lanzamiento de Dagas", 4, "Lanza dagas hacia el enemigo."),
            Technique("Defensa Evasiva", 5, "Bloquea y evade ataques."),
            Technique("Ataque Frenético", 6, "Realiza una serie de golpes rápidos."),
            Technique("Golpe Crítico", 7, "Ataca puntos vitales para mayor daño."),
            Technique("Salto Mortal", 8, "Ataca desde el aire."),
            Technique("Resistencia Titanes", 9, "Aumenta su resistencia temporalmente."),
            Technique("Conocimiento Asesino", 10, "Usa su entrenamiento para prever movimientos del enemigo.")
        ),
        "Doctor Strange" to listOf(
            Technique("Hechizo de Ilusión", 1, "Crea imágenes falsas para confundir."),
            Technique("Portal Dimensional", 2, "Aparece y desaparece a voluntad."),
            Technique("Escudo Místico", 3, "Genera una barrera mágica."),
            Technique("Llamas de Faltine", 4, "Invoca llamas místicas."),
            Technique("Lazo de Hoggoth", 5, "Inmoviliza al enemigo con energía mágica."),
            Technique("Ojo de Agamotto", 6, "Revela la verdad y debilidades."),
            Technique("Bandas Carmesí de Cyttorak", 7, "Atrapa al enemigo con bandas de energía."),
            Technique("Manipulación Temporal", 8, "Altera el flujo del tiempo."),
            Technique("Proyección Astral", 9, "Ataca desde el plano astral."),
            Technique("Anillo de Raggadorr", 10, "Aumenta su poder mágico temporalmente.")
        )
    )

    val heroList: () -> MutableList<Hero> = {
        mutableListOf(
            Hero(
                name = "Capitán América",
                power = 10,
                intelligence = 4,
                photo = "capitan_america",
                description = "Líder militar experto y un gran estratega. Su escudo de Vibranium es prácticamente indestructible.",
                techniques = techniques["Capitán América"] ?: emptyList()
            ),
            Hero(
                name = "Iron Man",
                power = 6,
                intelligence = 8,
                photo = "iron_man",
                description = "Vengador blindado. Tony Stark es un genio que diseña soluciones de alta tecnología, como su armadura.",
                techniques = techniques["Iron Man"] ?: emptyList()
            ),
            Hero(
                name = "Hulk",
                power = 9,
                intelligence = 7,
                photo = "hulk",
                description = "Prefiere utilizar su inmenso poder para aplastar a las fuerzas del mal. ¡Es uno de los superhéroes más fuertes!",
                techniques = techniques["Hulk"] ?: emptyList()
            ),
            Hero(
                name = "Viuda Negra",
                power = 5,
                intelligence = 5,
                photo = "viuda_negra",
                description = "Superespía y una experta en artes marciales. Tiene una agilidad y una capacidad atlética excepcionales.",
                techniques = techniques["Viuda Negra"] ?: emptyList()
            ),
            Hero(
                name = "Thor",
                power = 9,
                intelligence = 3,
                photo = "thor",
                description = "¡Dios del Trueno! Controla el trueno y utiliza su martillo Mjolnir para proteger su hogar, Asgard, y la Tierra.",
                techniques = techniques["Thor"] ?: emptyList()
            ),
            Hero(
                name = "Capitana Marvel",
                power = 10,
                intelligence = 4,
                photo = "capitana_marvel",
                description = "Una de la superheroínas más poderosas. Puede volar y lanza ráfagas de energía de sus manos.",
                techniques = techniques["Capitana Marvel"] ?: emptyList()
            ),
            Hero(
                name = "Spiderman",
                power = 6,
                intelligence = 6,
                photo = "spiderman",
                description = "Tu vecino y amigo siempre está atento para protegerte. Es un chico muy pegajoso.",
                techniques = techniques["Spiderman"] ?: emptyList()
            ),
            Hero(
                name = "Star Lord",
                power = 4,
                intelligence = 7,
                photo = "star_lord",
                description = "Viaja por el cosmos en su nave espacial, la Milano, en busca de fortuna. Es el líder de los Guardianes de la Galaxia.",
                techniques = techniques["Star Lord"] ?: emptyList()
            ),
            Hero(
                name = "Gamora",
                power = 6,
                intelligence = 4,
                photo = "gamora",
                description = "Es una espadachina hábil y una guerrera famosa. Hija del villano Titán Thanos, aunque tiene su corazoncito.",
                techniques = techniques["Gamora"] ?: emptyList()
            ),
            Hero(
                name = "Doctor Strange",
                power = 3,
                intelligence = 8,
                photo = "doctor_strange",
                description = "Desarrolló sus poderes mediante antiguas técnicas asiáticas. Utiliza ilusiones, hechizos y engaños. ¡Puede abrir portales multidimensionales!",
                techniques = techniques["Doctor Strange"] ?: emptyList()
            )
        ).apply { shuffle() }
    }

    val getListXtimes : (Int) -> MutableList<Hero> = { times ->
        val list = mutableListOf<Hero>()
        for (i in 1..times) {
            list.addAll(heroList())
        }
        list.shuffle()
        list
    }

    val getHeroByName : (String) -> Hero? = { name ->
        heroList().find { it.name == name }
    }

    val getSomeRandHeroes : (Int) -> MutableList<Hero> = { num ->
        var heroes = heroList()
        if(num <= heroes.size) heroes = heroes.subList(0, num)
        heroes
    }

    fun getDrawableIdByName(name: String): Int {
        return when (name) {
            "iron_man" -> R.drawable.iron_man
            "capitana_marvel" -> R.drawable.capitana_marvel
            "viuda_negra" -> R.drawable.viuda_negra
            "thor" -> R.drawable.thor
            "spiderman" -> R.drawable.spiderman
            "star_lord" -> R.drawable.star_lord
            "gamora" -> R.drawable.gamora
            "doctor_strange" -> R.drawable.doctor_strange
            "capitan_america" -> R.drawable.capitan_america
            "hulk" -> R.drawable.hulk
            else -> R.drawable.mh_icono
        }
    }
}

enum class FightType(name : String) {
    POWER("power"),
    INTELLIGENCE("intelligence")
}