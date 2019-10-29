@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

fun wordToCharSet(word: String): Set<Char> {
    val res = mutableSetOf<Char>()
    for (i in 0 until word.length) {
        res += word[i]
    }
    return res
}

fun getSales(list: List<Pair<String, Double>>): List<String> {
    var set = setOf<String>()
    for (element in list) {
        set += element.first
    }
    return set.toList()
}

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val res = mutableMapOf<Int, MutableList<String>>()
    for ((key) in grades) {
        if (res.containsKey(grades[key]))res[grades[key]]?.plusAssign(key)
        else res[grades[key]!!] = mutableListOf(key)
    }
    return res
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((key, value) in a) {
        if ((a[key] != b[key]) || (a[value] != b[value])) return false
    }
    return true
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit {
    for ((char) in b) {
        if (a[char] == b[char]) a.remove(char)
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = (a.toSet().intersect(b.toSet())).toList()

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val max = mapA + mapB // Массив со всеми keys, т.е. со всеми службами
    val res = mutableMapOf<String, String>()
    var str: String
    for ((key) in max) {
        if (max[key] != mapA[key] && mapA[key] != null) {
            str = mapA[key] + ", " + max[key]
            res[key] = str
        } else res[key] = max[key]!! // max[key] не может быть равен null, т.к. у службы не может не быть номера
    }
    return res
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val salesName = getSales(stockPrices)
    var k = 0
    var count = 0
    var sum = 0.0
    val res = mutableMapOf<String, Double>()
    while (k < salesName.size) {
        for (i in 0 until stockPrices.size) {
            if (stockPrices[i].first == salesName[k]) {
                count++
                sum += stockPrices[i].second
            }
        }
        res[salesName[k]] = sum / count
        sum = 0.0
        count = 0
        k++
    }
    return res
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var min = Double.MAX_VALUE
    var cheapestStuff: String? = "null"
    for ((key, value) in stuff) {
        if (kind == value.first && value.second < min) {
            min = value.second
            cheapestStuff = key
        }
    }
    return if (cheapestStuff == "null") null
    else cheapestStuff
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val setWord = wordToCharSet(word)
    val setChars = chars.toSet()
    for (element in setWord) {
        if (element !in setChars) return false
    }
    return true
    //return setWord == setChars || word == ""
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val listFiltered = list.toSet().toMutableList()
    val result = mutableMapOf<String, Int>()
    var k = 0
    var count = 0
    while (k < listFiltered.size) {
        for (i in 0 until list.size) {
            if (list[i] == listFiltered[k]) count++
        }
        if (count > 1) result[listFiltered[k]] = count
        count = 0
        k++
    }
    return result
}


/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    var b = 1 // ввёл переменную для того, чтобы исключить случаи в цикле, когда element == words[i]
    for (element in words) {
        for (i in b until words.size) {
            if (((wordToCharSet(element).intersect(wordToCharSet(words[i])) == wordToCharSet(element)
                        || wordToCharSet(element).intersect(wordToCharSet(words[i])) == wordToCharSet(words[i]))
                        && (wordToCharSet(element).isNotEmpty() || wordToCharSet(words[i]).isNotEmpty())))
                return true
        }
        b++
    }
    return false
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val friendsMutable = friends.toMutableMap()
    val listOfKeys = mutableListOf<String>()

    for ((key) in friends) {
        listOfKeys += key
    }

    for ((key) in friends) {
        val res = friends[key]!!.toMutableList()
        for (i in 0 until friends.size) {
            if (friends.keys.elementAt(i) in friendsMutable[key]!!.toList() && friends.keys.elementAt(i) != key) {
                res += friends[friends.keys.elementAt(i)]!!.toList()
                friendsMutable[key] = res.filter { it != key }.toSet()
            }
            for (k in 0 until friends[friends.keys.elementAt(i)]!!.toList().size) {
                if (friends[friends.keys.elementAt(i)]!!.toList()[k] !in listOfKeys)
                    friendsMutable[friends[friends.keys.elementAt(i)]!!.toList()[k]] = setOf()
            }
        }
        for (i in 0 until friends.size) {
            if (friends.keys.elementAt(i) in friendsMutable[key]!!.toList() && friends.keys.elementAt(i) != key) {
                res += friends[friends.keys.elementAt(i)]!!.toList()
                friendsMutable[key] = res.filter { it != key }.toSet()
            }
            for (k in 0 until friends[friends.keys.elementAt(i)]!!.toList().size) {
                if (friends[friends.keys.elementAt(i)]!!.toList()[k] !in listOfKeys)
                    friendsMutable[friends[friends.keys.elementAt(i)]!!.toList()[k]] = setOf()
            }
        }
        // использовал один и тот же цикл 2 раза для полного перебора всех элементов в каждом кее
        res.clear()
    }
    return friendsMutable
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    var b = 1
    for (i in 0 until list.size) {
        for (k in b until list.size) {
            if (list[i] + list[k] == number && i != k) return (i to k)
            b++ // см. переменную b в fun hasAnagrams
        }
        b = 0
    }
    return (-1 to -1)
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> = TODO()
