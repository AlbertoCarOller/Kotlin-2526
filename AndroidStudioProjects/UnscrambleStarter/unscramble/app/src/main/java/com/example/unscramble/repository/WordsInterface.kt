package com.example.unscramble.repository

import com.example.unscramble.datamodel.WordModel
import kotlinx.coroutines.flow.Flow

/* Las interfaces, en este caso estas sirven para definir las acciones
* que se pueden hacer, es un menú de las acciones que puede hacer, así si
* más adelante queremos cambiamos solo deben de cumplir el contrato del DAO,
* esta es la clase intermediaria, crea la fima de las funciones y la hereda el
* repository, después en el repository se le da código gracias a la interfaz DAO que
* ya contiene la lógica */
interface WordsInterface {

    // Se crean las firmas de las funciones
    suspend fun insert(word: WordModel)
    suspend fun update(word: WordModel)
    suspend fun delete(word: WordModel)
    suspend fun clearWords()
    val getAllWords: Flow<List<WordModel>>
    val getSameRandomWords: (Int) -> Flow<List<WordModel>>
    val getAllWordsByLanguage: (String) -> Flow<List<WordModel>>
    val getSomeRandomWordsByLanguage: (String, Int) -> Flow<List<WordModel>>
    suspend fun getOnceSomeRandomWordsByLanguage(language: String, number: Int): List<WordModel>
}