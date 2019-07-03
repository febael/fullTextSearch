package com.bawer.tasks.adevinta

import java.util.*

val standardHitCollector = HitCollector { seq ->
    var index = 0
    seq.filter { it.isNotEmpty() }
            .map { it.toLowerCase() to index++ }
            .groupBy({ it.first }, {it.second})
            .entries
            .map { Hit(it.key, TreeSet(it.value)) }
}