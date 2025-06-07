package dao

interface DaoCrud<T> {
    fun insert(obj : T) : Boolean
    fun update(obj: T) : Boolean
    fun delete(obj: T) : Boolean
}//TODO update for new structure