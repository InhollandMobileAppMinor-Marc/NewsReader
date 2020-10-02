package nl.bouwman.marc.news.domain.models

sealed class Result<out V, out E> {
    abstract operator fun component1(): V?
    abstract operator fun component2(): E?

    class OK<out V>(val value: V) : Result<V, Nothing>() {
        override fun component1(): V = value
        override fun component2(): Nothing? = null
    }

    class Error<out E>(val error: E) : Result<Nothing, E>() {
        override fun component1(): Nothing? = null
        override fun component2(): E = error
    }
}