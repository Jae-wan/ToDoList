package com.example.todolist

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.db.AppDatabase
import com.example.todolist.db.ToDoDao
import com.example.todolist.db.ToDoEntitiy

class MainActivity : AppCompatActivity(), OnItemLongClickListener {
                            // LongClickListener 인터페이스 구현
    private lateinit var binding : ActivityMainBinding

    private lateinit var db : AppDatabase
    private lateinit var todoDao : ToDoDao
    private lateinit var todoList : ArrayList<ToDoEntitiy>
    // DB 관련 변수 선언해주고, ToDoList를 담아 둘 todoList 변수 선언.

    private lateinit var adapter: TodoRecyclerViewAdapter
    // 리사이클러뷰 어댑터를 변수 adapter로 선언 해 줌.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }

        // AppDatabase 객체를 생성하여 ToDoDao를 불러옴.
        db = AppDatabase.getInstance(this)!! // DB 인스턴스를 가져옴
        todoDao = db.getTodoDao() // DB 작업을 할 수 있도록 DAO를 가져옴.
        // ToDoDao를 활용해서 MainActivity에서 할 일을 조회할꺼임.

        getAllTodoList() // 할 일 리스트 가져오기
    }

    // 할 일 리스트 가져오기
    private fun getAllTodoList() {
        Thread {    // background Thread에서 DB 관련 작업을 함.
            todoList = ArrayList(todoDao.getAll()) // getAll() : 일전에 생성한 ToDoEntity에서 모든 데이터를 불러오는 쿼리 함수.
            // 모든 할 일 리스트를 가져온 후 lateinit로 선언한 todoList에 할당 해 줌
            setRecyclerView()
        }.start()
    }

    private fun setRecyclerView() {
        // 리사이클러뷰 설정 / getAllTodoList( ) 함수에서 리스트를 가져온 후 호출되는 함수.

        runOnUiThread{ // UI 관련 작업이므로 UI스레드에서 실행함. setRecyclerView() 함수를 부르는 곳이
            // getAllTodoList() 함수인데, 여기가 백그라운드 스레드에서 실행되기 때문에 UI 스레드를 불러줘야 함.
            adapter = TodoRecyclerViewAdapter(todoList, this) // 어댑터 객체 할당
            // 리사이클러뷰 어댑터에서 todoList 리스트와 this(OnItemLongClickListener) 구현체를 인수로 받음.
            // 이미 해당 인터페이스를 메인 액티비티에 구현 해 주고 있기 때문에 this로 넘겨줌.
            binding.recyclerView.adapter = adapter
            // 리사이클러뷰 어댑터로 바로 윗줄에서 만든 어댑터를 설정
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            // 레이아웃 매니저 설정
        }
    }

    // Activity가 멈췄다가 다시 시작할 때 실행되는 함수. (액티비티 생명주기)
    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
        // AddTodoActivity 상태에서 다시 돌아왔을 때마다 리스트를 갱신 시켜주어야 하므로 Restart 시에 또 갱신시켜 줌.
    }

    // OnItemLongClickListener 인터페이스 구현부
    override fun onLongClick(position: Int) {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("목록 삭제")               // 제목 : Title 설정
        builder.setMessage("정말 삭제하시겠습니까?")  // 내용 : Message 설정
        builder.setNegativeButton("취소", null) // 취소 : NegativeButton
        builder.setPositiveButton("삭제",         // 삭제 : PositiveButton
                object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        deleteTodo(position)
                    }
                }
        )
        builder.show()
    }
    //onLongClick을 통해 길게 눌렀을 때 대화 상자가 뜨도록 하며, 알림창 Alert Dialog는 진행하기 전,
    // 사용자에게 의사를 물어볼 때 사용함.
    // 각 버튼에 클릭 리스너를 달 수 있으며, Positive & Negative Button을 활용하였음.
    // Positive 하여 삭제하면 deleteTodo() 함수가 실행됨.

    // deleteTodo 함수는 Room DB에서 해당 할 일 목록을 삭제하고 todoList를 업데이트함.
    private fun deleteTodo(position: Int) {
        Thread {
            todoDao.deleteTodo(todoList[position])  // DB에서 삭제
            todoList.removeAt(position)             // List에서 삭제
            runOnUiThread {         // UI 관련 작업은 UI 스레드에서
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "삭제 되었습니다.",
                    Toast.LENGTH_SHORT).show()
                // adapter.notifyDataSetChanged() 함수는 어댑터에게 데이터가 바뀌었음을 알려줌.
                // 하여금, 리사이클러뷰가 그에 맞춰 자동으로 업데이트 되게 함.
            }
        }.start()
    }
}