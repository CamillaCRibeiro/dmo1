package com.example.catalogocursos.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "curso")
data class Curso(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val descricao: String
) : Serializable