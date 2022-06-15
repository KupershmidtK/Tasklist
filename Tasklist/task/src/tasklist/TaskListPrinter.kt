package tasklist

object TaskListPrinter {
    fun print(taskList: TaskList) {
        printHeader()
        printBody(taskList)

    }

    private fun printBody(list: TaskList) {
        for (idx in list.taskList.indices) {
            printTask(idx + 1, list.taskList[idx])
            printFooter()
        }
    }

    private fun printTask(idx: Int, task: Task) {
        for (i in task.list.indices) {
            var str: String
            if(i == 0) {
                val priorityColor =
                    when(task.priority) {
                        'C' -> "\u001B[101m \u001B[0m"
                        'H' -> "\u001B[103m \u001B[0m"
                        'N' -> "\u001B[102m \u001B[0m"
                        'L' -> "\u001B[104m \u001B[0m"
                        else -> " "
                    }

                val dueColor =
                    when(task.dueTag()) {
                        'I' -> "\u001B[102m \u001B[0m"
                        'T' -> "\u001B[103m \u001B[0m"
                        'O' -> "\u001B[101m \u001B[0m"
                        else -> " "
                    }
                str = "| $idx  | ${task.date} | ${task.time} | $priorityColor | $dueColor |" + multiLineTask(task.list[i])
            } else {
                str = "|    |            |       |   |   |" + multiLineTask(task.list[i])
            }
            print(str)
        }
    }

    private fun multiLineTask(s: String): String {
        val LEN_OF_STRING = 44
        val length = s.length
        if (length < LEN_OF_STRING) return "%-44s|\n".format(s)

        var str = ""
        var start = 0
        var end = LEN_OF_STRING
        while (start < length) {
            if (start == 0) {
                str += "%-44s|\n".format(s.substring(start, end))
            } else {
                str += "|    |            |       |   |   |%-44s|\n".format(s.substring(start, end))
            }

            start += LEN_OF_STRING
            end += LEN_OF_STRING
            if (end > length) end = length
        }
        return str
    }

    private fun printFooter() {
        val footer =
            "+----+------------+-------+---+---+--------------------------------------------+"
        println(footer)
    }

    private fun printHeader() {
        val header =
        "+----+------------+-------+---+---+--------------------------------------------+\n" +
        "| N  |    Date    | Time  | P | D |                   Task                     |\n" +
        "+----+------------+-------+---+---+--------------------------------------------+"

        println(header)
    }
}