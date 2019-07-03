package com.bawer.tasks.adevinta

// removes every character except letters, digits or whitespace
val standardCharFilter = CharFilter {
    String( it.filter { char -> char.isLetterOrDigit() || char.isWhitespace() }.toCharArray() )
}