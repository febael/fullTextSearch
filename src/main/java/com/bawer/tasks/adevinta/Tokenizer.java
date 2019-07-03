package com.bawer.tasks.adevinta;

import kotlin.sequences.Sequence;

@FunctionalInterface
public interface Tokenizer {
    Sequence<String> process(String text);
}
