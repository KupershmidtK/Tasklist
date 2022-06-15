package tasklist

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

class TaskListSaver(val fileName: String) {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val type = Types.newParameterizedType(MutableList::class.java, Task::class.java, MutableList::class.java)
    val taskListAdapter = moshi.adapter<MutableList<Task>>(type)

    fun save(list: TaskList) {
        val json = taskListAdapter.toJson(list.taskList)
        File(fileName).writeText(json)
    }

    fun load(): TaskList {
        val json = File(fileName).readText()
        return TaskList(taskListAdapter.fromJson(json)!!)
    }
}