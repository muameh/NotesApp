package com.example.x1_todolist.utils

object PairConverter {

    public fun fromPair(pair: Pair<String, String>): String {
        return "${pair.first} ${pair.second}"
    }

    public fun toPair(value: String): Pair<String, String> {
        val split = value.split(",")
        return Pair(split[0], split[1])
    }



}