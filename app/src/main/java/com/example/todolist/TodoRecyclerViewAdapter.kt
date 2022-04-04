package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTodoBinding
import com.example.todolist.db.ToDoEntitiy
                        // listener는 길게 눌렀을 때의 동작 리스너이고, 메인 액티비티에서 구현체로 넘겨주어야 하므로 인수로 추가함.
class TodoRecyclerViewAdapter(private val todoList : ArrayList<ToDoEntitiy>, private val listener : OnItemLongClickListener)
                                : RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>() {
// 어댑터 객체를 생성하되, todoList를 인수로 받아줌.
// RecyclerView.Adapter<만들 뷰 홀더 클래스>를 상속함. (뷰홀더 패턴으로 구현)
// 뷰홀더 패턴 : 각 뷰 객체를 뷰 홀더에 보관하여 반복적인 Method 호출을 줄여 속도를 개선하는 패턴

// MyViewHolder 클래스를 생성하되, 내부 클래스로 생성하며 RecyclerView.ViewHolder 클래스를 상속한 뷰 홀더 클래스임.
    inner class MyViewHolder(binding : ItemTodoBinding) :
    RecyclerView.ViewHolder(binding.root) {
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle

        // viewBinding에서 기본적으로 제공하는 root 변수는 Layout의 root Layout을 의미함.
        val root = binding.root
    }

    // onCreateViewHolder : 뷰홀더 클래스에서 만든 뷰홀더 객체를 생성 해 줌.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                                    : MyViewHolder {
        // item_todo.xml 관련 뷰 바인딩 객체 생성
        val binding : ItemTodoBinding =
            ItemTodoBinding.inflate(LayoutInflater.
                                    from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // onBindViewHolder : 어댑터에서 받은 데이터를 onCreateViewHolder에서 만든 뷰홀더 객체에 어떻게 넣어줄 지 결정하는 함수
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]

        when(todoData.importance) {
            1 -> {
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2 -> {
                holder.tv_importance.setBackgroundResource(R.color.yellow)
            }
            3 -> {
                holder.tv_importance.setBackgroundResource(R.color.green)
            }
        }
        // 중요도에 따라 중요도 텍스트 (1, 2, 3) 변경
        holder.tv_importance.text = todoData.importance.toString()
        // when 분기에서 사용한 값을 그대로 .toString() 하여 변경

        // 할 일의 제목 변경
        holder.tv_title.text = todoData.title

        // 할 일이 길게 클릭되었을 때 리스너 함수를 실행함.
        holder.root.setOnLongClickListener {
            listener.onLongClick(position)
            false
        } // holder.root는 한 아이템 뷰의 루트 레이아웃임. 아이템 뷰 전체 레이아웃 범위를 뜻한다고 보면 됨.
        // setOnLongClickListener() 함수를 사용하여 길게 눌렀을 때 어떤 코드가 실행될지 ?
        // OnItemLongClickListener 구현체의 onLongClick() 함수를 실행 해 주었고, 인수로 position을 넘겨주어
        // 어떤 것이 눌렸는지 Listener에게 알려줌.
    }

    // getItemCount : 데이터가 몇 개인지 변환 해 주는 함수.
    override fun getItemCount(): Int {
        // 리사이클러뷰 아이템 개수는 할 일 리스트의 크기
        return todoList.size
    }
}