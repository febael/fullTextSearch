package com.bawer.tasks.adevinta

import java.io.File
import java.util.*
import kotlin.collections.ArrayList


private object StandardFileSystemSearcher : PipelinedSearcher() {

    private val files = ArrayList<File>()

    fun index(file: File) {
        if (file.isDirectory) {
            file.listFiles()?.forEach { index(it) }
        } else {
            indexDocument(file.readText(), files.size)
            files.add(file)
        }
    }

    fun search(query: String) = analyzeResult( super.searchInvertedIndex(query) )

    private fun analyzeResult(result: Map<String, LinkedList<Hits>?>): Map<String, Double> =
            result.values.filterNotNull().flatten().groupingBy { it.documentIndex }.eachCount().asSequence().map {
                files[it.key].canonicalPath to ((100*it.value).toDouble() / result.size)
            }.toMap()
}


fun main(args: Array<String>) {
    if (args.isEmpty()) throw IllegalArgumentException("Need a directory as an argument!")
    StandardFileSystemSearcher.index(File(args[0]))
    if (StandardFileSystemSearcher.empty) throw Exception("No words are found to index!")
    val keyboard = Scanner(System.`in`)
    loop@ while (true) {
        print("\nsearch> ")
        when (val query = keyboard.nextLine()) {
            "!!" -> break@loop
            else -> printResults( StandardFileSystemSearcher.search(query) )
        }
    }
}

fun printResults(results: Map<String, Double>) {
    if (results.isEmpty()) println("Not found!")
    else {
        println("results : ")
        results.forEach {
            println("File: '${it.key}', Trust: ${String.format("%.2f", it.value)}%")
        }
    }
}
