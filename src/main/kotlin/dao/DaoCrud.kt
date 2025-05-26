package dao

interface DaoCrud<T> {
    fun insert(obj : T) : Boolean
}