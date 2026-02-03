package com.example.unscramble.repository

import com.example.unscramble.datamodel.WordModel
import com.example.unscramble.localdatabase.WordsDAO
import kotlinx.coroutines.flow.Flow

/* Como ya se ha explicado anteriormente, los Repository sean porque saben
* como obetener la información, en este caso de la base de datos, esta
* es la clase que implenta las funciones dándole códgio que ya viene de la
* interfaz DAO, los repository pueden heredar de más de una interfaz (las clases en general)
* para obtener datos por otras interfaces aunque aquí solo es de 'WordsInterface' */
class WordsRepository(
    // wordsDAO -> Esta es la interfaz que contiene la lógica de las funciones
    val wordsDAO: WordsDAO
) : WordsInterface {

    // ALGUNAS SON SUSPEND FUN Y OTRAS VAL PORQUE UNAS SON FLOW SE QUEDAN ABIERTAS ESCUCHANDO Y LAS OTRAS NO

    override suspend fun insert(word: WordModel) {
        wordsDAO.insert(word)
    }

    override suspend fun insertWordList(wordList: List<WordModel>) {
        wordsDAO.insertWordList(wordList)
    }

    override suspend fun update(word: WordModel) {
        wordsDAO.update(word)
    }

    override suspend fun delete(word: WordModel) {
        wordsDAO.delete(word)
    }

    override suspend fun clearWords() {
        clearWords()
    }

    override val getAllWords: Flow<List<WordModel>> = wordsDAO.getAllWords()
    override val getSameRandomWords: (Int) -> Flow<List<WordModel>> =
        { wordsDAO.getSomeRandomWords(it) }
    override val getAllWordsByLanguage: (String) -> Flow<List<WordModel>> =
        { wordsDAO.getAllWordsByLanguage(it) }
    override val getSomeRandomWordsByLanguage: (String, Int) -> Flow<List<WordModel>> =
        { s, i -> wordsDAO.getSomeRandomWordsByLanguage(s, i) }

    override suspend fun getOnceSomeRandomWordsByLanguage(
        language: String,
        number: Int
    ): List<WordModel> {
        return wordsDAO.getOnceSomeRandomWordsByLanguage(language, number)
    }
}