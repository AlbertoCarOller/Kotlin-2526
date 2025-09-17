package com.example.poo

open class Pokemon(
    protected var name: String = "Pok",
    protected var attackPower: Float = 30f,
    protected var life: Float = 100f
) {

    // Creamos los get
    internal fun getName(): String {
        return name
    }

    internal fun getAttackPower(): Float {
        return attackPower
    }

    internal fun getLife(): Float {
        return life
    }

    // Creamos los set
    internal fun setName(n: String) {
        name = n
    }

    internal fun setAttackPower(aP: Float) {
        attackPower = aP
    }

    internal fun setLife(l: Float) {
        life = l
    }

    // Creamos una función que va a curar al pokemon, va a recuperar la vida al completo
    fun cure() {
        this.life = 100f
    }

    // Creamos una función para hacer que evolucione un pokemon
    fun envolve(n: String) {
        this.name = n
        this.attackPower *= 1.20f
    }
}

// Creamos un hijo de pokermon
class Waterpokemon(
    name: String,
    attackPower: Float,
    life: Float,
    private var maxResistance: Int = 500
) :
    Pokemon(name, attackPower, life) {

    private var summergedTime: Int = 0

    // Creamos los get y set
    fun getMaxResistance(): Int {
        return this.maxResistance
    }

    fun getSummergedTime(): Int {
        return this.summergedTime
    }

    fun setMaxResistance(mxResist: Int) {
        this.maxResistance = mxResist
    }


    // Creamos una función que va hacer que respire aire el pokemon
    fun breather() {
        this.summergedTime = 0
    }

    // Creamos una función que va a simular que el pokemon se ha sumergido en el agua
    fun inmerse() {
        this.summergedTime++
    }
}

class Firepokemon(
    name: String,
    attackPower: Float,
    life: Float,
    private var ballTemperature: Int = 90
) : Pokemon(name, attackPower, life)

class EarthPokemon(
    name: String, attackPower: Float, life: Float, private var depth: Int,
    override var dato: Int
) : Pokemon(name, attackPower, life), sayBye

// Creamos una interface
interface sayBye {

    // Creamos una variable
    var dato: Int

    // Creamos una función que va a imprimir una despedida
    fun despedirse() {
        println("Hasta luego papi")
    }
}