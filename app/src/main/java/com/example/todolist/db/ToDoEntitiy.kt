package com.example.todolist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// 어떤 구성요소인지 알게 하려면 Annotation을 반드시 사용하여야 함.
@Entity // Annotation @를 이용하여 프로그램에 추가 정보를 제공, 컴파일러가 특정 오류를 억제하거나 실행 시 특정 행동을 하게 됨.
data class ToDoEntitiy ( // Kotlin에서 제공하는 Data Class 생성
    @PrimaryKey(autoGenerate = true)var id : Int? = null,
    @ColumnInfo(name="title") val title : String,
    @ColumnInfo(name="importance") val importance : Int
        )


// PrimaryKey => 모든 Table에는 기본 키(Primary Key)가 있어야 함.
// 각 정보를 식별하는 값으로, 중복되면 안되고 테이블의 기본 키로 id를 생성하고 새로운 값이
// 생성될 때 id가 자동으로 1씩 증가(autoGenerate = true)하면서 저장되게 설정하였음.

// 각 할 일에 저장되어야 할 내용들을(ColumnInfO) 내용(title)과 중요도(importance)를 각각 String, Int 형으로 생성
