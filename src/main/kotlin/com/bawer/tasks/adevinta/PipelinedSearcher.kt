package com.bawer.tasks.adevinta

import java.util.*

internal class Hit(val word: String, val locations: TreeSet<Int>)
class Hits(val documentIndex: Int, val locations: TreeSet<Int>)

abstract class PipelinedSearcher(
        private val characterFilter: CharFilter = standardCharFilter,
        private val tokenizer: Tokenizer = standardTokenizer,
        private val hitCollector: HitCollector = standardHitCollector
) {
    private val invertedIndex = HashMap<String, LinkedList<Hits>>()

    val empty get() = invertedIndex.isEmpty()

    protected fun indexDocument (text: String, index: Int) {
        hitCollector.process(
                tokenizer.process(
                        characterFilter.process(text.toCharArray())
                )
        ).forEach {
            val list = invertedIndex[it.word] ?: LinkedList<Hits>().apply {
                invertedIndex[it.word] = this
            }
            list.add(Hits(index, it.locations))
        }
    }

    protected fun searchInvertedIndex(query: String) =
        tokenizer.process(
                characterFilter.process(query.toCharArray())
        ).map { it to invertedIndex[it] }.toMap()
}

