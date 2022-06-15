package tasklist

import kotlinx.datetime.*

class Task(var date: String, var time: String, var priority: Char, val list: MutableList<String> = mutableListOf<String>()) {

    fun isNotEmpty() = list.isNotEmpty()
    fun add(str: String) = list.add(str)
    fun clear() = list.clear()

//    fun print(idx: Int) {
//        println("${"%-2d".format(idx)} $date $time $priority ${dueTag()}")
//        for(str in list) { println("   $str") }
//    }

    fun dueTag(): Char {
        val taskDate = LocalDate.parse(date)
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+0")).date
        val numberOfDays = currentDate.daysUntil(taskDate)
        when {
            numberOfDays < 0 -> return 'O'
            numberOfDays > 0 -> return 'I'
            else -> return 'T'
        }
    }
}