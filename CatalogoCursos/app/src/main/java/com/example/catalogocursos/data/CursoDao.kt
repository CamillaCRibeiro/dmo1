package com.example.catalogocursos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.catalogocursos.model.Curso

@Dao
interface CursoDao {

    @Insert
    suspend fun inserir(curso: Curso)

    @Query("SELECT * FROM curso ORDER BY nome ASC")
    suspend fun listarTodos(): List<Curso>

    @Update
    suspend fun atualizar(curso: Curso)

    @Delete
    suspend fun deletar(curso: Curso)
}