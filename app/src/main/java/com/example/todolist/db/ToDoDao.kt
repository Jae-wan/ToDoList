package com.example.todolist.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDoEntitiy")
    fun getAll() : List<ToDoEntitiy>
        // 일전에 생성한 ToDoEntity에서 모든 데이터를 불러오는 쿼리 함수. getAll()

    @Insert
    fun insertTodo(todo : ToDoEntitiy)
        // ToDoEntity 객체를 테이블에 삽입하는 함수.

    @Delete
    fun deleteTodo(todo : ToDoEntitiy)
    // 특정 ToDoEntity 객체를 테이블에서 삭제하는 함수.
}