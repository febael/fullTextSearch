package com.bawer.tasks.adevinta

private val whitespaceRegex by lazy { Regex("\\s") }

// split by whitespace, my produce empty strings in the middle
val standardTokenizer = Tokenizer {
    it.split(whitespaceRegex).asSequence()
}