package pl.damiankaplon.entity

interface EntityMapper<T, E> {
    fun to(t: T): E
    fun from(e: E): T
}