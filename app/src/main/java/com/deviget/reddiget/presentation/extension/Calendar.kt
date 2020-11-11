import java.util.*

private const val MILLISECONDS_IN_A_SECOND = 1000

fun calendar(block: Calendar.() -> Unit = {}): Calendar =
    Calendar.getInstance().apply(block)

var Calendar.era: Int
    get() = get(Calendar.ERA)
    set(value) = set(Calendar.ERA, value)
var Calendar.year: Int
    get() = get(Calendar.YEAR)
    set(value) = set(Calendar.YEAR, value)
var Calendar.month: Int
    get() = get(Calendar.MONTH)
    set(value) = set(Calendar.MONTH, value)
var Calendar.weekOfYear: Int
    get() = get(Calendar.WEEK_OF_YEAR)
    set(value) = set(Calendar.WEEK_OF_YEAR, value)
var Calendar.weekOfMonth: Int
    get() = get(Calendar.WEEK_OF_MONTH)
    set(value) = set(Calendar.WEEK_OF_MONTH, value)
var Calendar.date: Int
    get() = get(Calendar.DATE)
    set(value) = set(Calendar.DATE, value)
var Calendar.dayOfMonth: Int
    get() = get(Calendar.DAY_OF_MONTH)
    set(value) = set(Calendar.DAY_OF_MONTH, value)
var Calendar.dayOfYear: Int
    get() = get(Calendar.DAY_OF_YEAR)
    set(value) = set(Calendar.DAY_OF_YEAR, value)
var Calendar.dayOfWeek: Int
    get() = get(Calendar.DAY_OF_WEEK)
    set(value) = set(Calendar.DAY_OF_WEEK, value)
var Calendar.dayOfWeekInMonth: Int
    get() = get(Calendar.DAY_OF_WEEK_IN_MONTH)
    set(value) = set(Calendar.DAY_OF_WEEK_IN_MONTH, value)
var Calendar.amPm: Int
    get() = get(Calendar.AM_PM)
    set(value) = set(Calendar.AM_PM, value)
var Calendar.hour: Int
    get() = get(Calendar.HOUR)
    set(value) = set(Calendar.HOUR, value)
var Calendar.hourOfDay: Int
    get() = get(Calendar.HOUR_OF_DAY)
    set(value) = set(Calendar.HOUR_OF_DAY, value)
var Calendar.minute: Int
    get() = get(Calendar.MINUTE)
    set(value) = set(Calendar.MINUTE, value)
var Calendar.second: Int
    get() = get(Calendar.SECOND)
    set(value) = set(Calendar.SECOND, value)
var Calendar.millisecond: Int
    get() = get(Calendar.MILLISECOND)
    set(value) = set(Calendar.MILLISECOND, value)
var Calendar.zoneOffset: Int
    get() = get(Calendar.ZONE_OFFSET)
    set(value) = set(Calendar.ZONE_OFFSET, value)
var Calendar.dstOffset: Int
    get() = get(Calendar.DST_OFFSET)
    set(value) = set(Calendar.DST_OFFSET, value)
var Calendar.timeInSeconds: Long
    get() = timeInMillis / MILLISECONDS_IN_A_SECOND
    set(value) {
        timeInMillis = value * MILLISECONDS_IN_A_SECOND
    }

fun todayAtMidnight() = calendar {
    hourOfDay = 0
    minute = 0
    second = 0
    millisecond = 0
}

fun Calendar.isToday() = calendar().let { today ->
    year == today.year && month == today.month && dayOfMonth == today.dayOfMonth
}

fun Calendar.isYesterday() = calendar { dayOfYear-- }.let { yesterday ->
    year == yesterday.year && month == yesterday.month && dayOfMonth == yesterday.dayOfMonth
}