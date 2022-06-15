package tasklist

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

fun main() {
    App.load()
    while(true) {
        println("Input an action (add, print, edit, delete, end):")
        when(readln()) {
            "add" -> App.add()
            "print" -> App.print()
            "edit" -> App.edit()
            "delete" -> App.delete()
            "end" -> {
                App.exit()
                break
            }
            else -> println("The input action is invalid")
        }
    }
}

object App {
    val FILE_NAME = "tasklist.json"
    lateinit var tasks: TaskList

    fun load() {
        try {
            tasks = TaskListSaver(FILE_NAME).load()
        } catch (e: Exception) {
            tasks = TaskList()
        }

    }

    fun exit() {
        TaskListSaver(FILE_NAME).save(tasks)
        println("Tasklist exiting!")
    }

    fun add() {
        val priority = inputPriority()
        val date = inputDate()
        val time = inputTime(date)
        val task = Task(date, time, priority)

        addTaskList(task)
        if(task.isNotEmpty()) {
            tasks.add(task)
        } else {
            println("The task is blank")
        }
    }

    private fun addTaskList(task: Task) {
        var str: String
        println("Input a new task (enter a blank line to end):")
        while (true) {
            str = readln().trim()
            if (str.isEmpty()) break
            task.add(str)
        }
    }

    private fun inputTime(dateStr: String): String {
        while (true) {
            println("Input the time (hh:mm):")
            try {
                val (hour, min) = readln().split(":")
                val date = LocalDate.parse(dateStr)
                val dateTime = LocalDateTime(date.year, date.monthNumber,date.dayOfMonth, hour.toInt(), min.toInt())
                return "%02d:%02d".format(dateTime.hour,dateTime.minute)
            } catch (e: Exception) {
                println("The input time is invalid")
            }
        }
    }

    private fun inputDate(): String {
        while (true) {
            println("Input the date (yyyy-mm-dd):")
            try {
                val (year, month, day) = readln().split("-")
                val date = LocalDate(year.toInt(), month.toInt(), day.toInt())
                return date.toString()
            } catch (e: Exception) {
                println("The input date is invalid")
            }
        }
    }

    private fun inputPriority(): Char {
        while (true) {
            println("Input the task priority (C, H, N, L):")
            val priority = readln().uppercase()
            if (priority.length == 1 && priority.first() in "CHNL")
                return priority.first()
        }
    }

    fun print() {
        if (tasks.isEmpty()) {
            println("No tasks have been input")
            return
        }
        TaskListPrinter.print(tasks)
//        tasks.printAll()
    }

    fun edit() {
        val idx = inputIdx()
        if (idx != -1) {
            val field: String = inputField()
            when (field) {
                "priority" -> editPriority(idx)
                "date" -> editDate(idx)
                "time" -> editTime(idx)
                "task" -> editTask(idx)
            }
            println("The task is changed")
        }
    }

    private fun editTask(idx: Int) {
        val task = tasks.taskList[idx]
        task.clear() 
        addTaskList(task)
    }

    private fun editTime(idx: Int) {
        val date = tasks.taskList[idx].date
        val time = inputTime(date)
        tasks.taskList[idx].time = time
    }

    private fun editDate(idx: Int) {
        val date = inputDate()
        tasks.taskList[idx].date = date
    }

    private fun editPriority(idx: Int) {
        val priority = inputPriority()
        tasks.taskList[idx].priority = priority
    }

    private fun inputField(): String {
        while (true) {
            println("Input a field to edit (priority, date, time, task):")
            val field = readln()
            if (field in listOf("priority", "date", "time", "task")) { return field }
            else { println("Invalid field") }
        }
    }

    fun delete() {
        val idx = inputIdx()
        if (idx != -1) {
            tasks.delete(idx)
            println("The task is deleted")
        }
    }

    private fun inputIdx(): Int {
        if (tasks.isEmpty()) {
            println("No tasks have been input")
            return -1
        }
        print()
        val size = tasks.size()
        var idx: Int
        while (true) {
            println("Input the task number (1-$size):")
            try {
                idx = readln().toInt()
                if (idx !in 1 .. size) {
                    throw Exception()
                }
                break
            } catch (e: Exception) {
                println("Invalid task number")
            }
        }
        return idx - 1
    }
}