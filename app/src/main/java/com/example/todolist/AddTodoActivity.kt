package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolist.databinding.ActivityAddTodoBinding
import com.example.todolist.db.AppDatabase
import com.example.todolist.db.ToDoDao
import com.example.todolist.db.ToDoEntitiy

class AddTodoActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddTodoBinding
    lateinit var db : AppDatabase
    lateinit var todoDao : ToDoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTodoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnCompletion.setOnClickListener {
            insertTodo()
        }

    }
    // 할 일을 추가하는 Insert 함수수
    private fun insertTodo() {

        val todoTitle = binding.edtTitle.text.toString() // 할 일의 제목을 담는 editText
        var todoImportance = binding.radioGroup.checkedRadioButtonId
                // 할 일의 중요도 선택하는 라디오그룹 내 라디오버튼

        // 어떤 버튼이 눌렸는지 확인하고 값을 지정하기 위한 when 분기
        when(todoImportance) {
            R.id.btn_high -> {
                todoImportance = 1
            }
            R.id.btn_middle -> {
                todoImportance = 2
            }
            R.id.btn_low -> {
                todoImportance = 3
            }
            // 중요도가 선택되지 않았을 때.
            else -> {
                todoImportance = -1
            }
        }
        // 중요도가 선택되지 않았을 때 todoImportance의 값 -1을 판단하기 위한 if 분기
        if(todoImportance == -1 || todoTitle.isBlank()) {
            Toast.makeText(this, "작성되지 않은 항목이 있습니다 !",
            Toast.LENGTH_SHORT).show()
        } else {
            Thread { // DB 관련 작업이므로 Background 스레드를 실행하여 비동기 처리를 하였고
                todoDao.insertTodo(ToDoEntitiy(null, todoTitle, todoImportance))
                runOnUiThread { // 아래 작업들은 UI 관련이므로 잠시 UI 스레드를 또 실행시켜 줌.
                    Toast.makeText(this, "추가 되었습니다.",
                    Toast.LENGTH_SHORT).show()
                    finish() // AddTodoActivity 종료하고 MainActivity로 돌아감.
                }
            }.start()
        }
    }
}