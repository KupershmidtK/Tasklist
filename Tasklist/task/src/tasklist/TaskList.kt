package tasklist

class TaskList(val taskList: MutableList<Task> = mutableListOf()) {

    fun add(task: Task) {
            taskList.add(task)
    }

//    fun printAll() {
//        var i = 1
//        for (task in taskList) {
//            task.print(i++)
//            println()
//        }
//    }

    fun isEmpty() = taskList.isEmpty()
    fun size() = taskList.size
    fun delete(idx: Int) = taskList.removeAt(idx)
}