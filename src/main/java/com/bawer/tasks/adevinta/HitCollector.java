package com.bawer.tasks.adevinta;

import kotlin.sequences.Sequence;

import java.util.List;

@FunctionalInterface
public interface HitCollector {
    List<Hit> process(Sequence<String> words);
}
