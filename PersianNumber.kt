import android.text.TextUtils
import java.math.BigDecimal

object PersianNumber {

    private var splitter: String? = null
    private val arraylistWord = ArrayList<ArrayList<String>>()

    private fun persianNumberChangeSetup() {
        var temp = ArrayList<String>()
        temp.add("")
        temp.add("یک")
        temp.add("دو")
        temp.add("سه")
        temp.add("چهار")
        temp.add("پنج")
        temp.add("شش")
        temp.add("هفت")
        temp.add("هشت")
        temp.add("نه")
        arraylistWord.add(temp)

        temp = ArrayList()
        temp.add("ده")
        temp.add("یازده")
        temp.add("دوازده")
        temp.add("سیزده")
        temp.add("چهارده")
        temp.add("پانزده")
        temp.add("شانزده")
        temp.add("هفده")
        temp.add("هجده")
        temp.add("نوزده")
        arraylistWord.add(temp)

        temp = ArrayList()
        temp.add("")
        temp.add("")
        temp.add("بیست")
        temp.add("سی")
        temp.add("چهل")
        temp.add("پنجاه")
        temp.add("شصد")
        temp.add("هفتاد")
        temp.add("هشتاد")
        temp.add("نود")
        arraylistWord.add(temp)

        temp = ArrayList()
        temp.add("")
        temp.add("یکصد")
        temp.add("دویست")
        temp.add("سیصد")
        temp.add("چهارصد")
        temp.add("پانصد")
        temp.add("ششصد")
        temp.add("هفتصد")
        temp.add("هشتصد")
        temp.add("نهصد")
        arraylistWord.add(temp)

        temp = ArrayList()
        temp.add("")
        temp.add(" هزار ")
        temp.add(" میلیون ")
        temp.add(" میلیارد ")
        temp.add(" بیلیون ")
        temp.add(" بیلیارد ")
        temp.add(" تریلیون ")
        temp.add(" تریلیارد ")
        temp.add(" کوآدریلیون ")
        temp.add(" کادریلیارد ")
        temp.add(" کوینتیلیون ")
        temp.add(" کوانتینیارد ")
        temp.add(" سکستیلیون ")
        temp.add(" سکستیلیارد ")
        temp.add(" سپتیلیون ")
        temp.add(" سپتیلیارد ")
        temp.add(" اکتیلیون ")
        temp.add(" اکتیلیارد ")
        temp.add(" نانیلیون ")
        temp.add(" نانیلیارد ")
        temp.add(" دسیلیون ")
        arraylistWord.add(temp)

        splitter = " و "
    }

    private fun prepareNumber(number: String): ArrayList<String>? {
        val length = number.length % 3
        var resultNumber = number
        if (length == 1) {
            resultNumber = "00$number"
        } else if (length == 2) {
            resultNumber = "0$number"
        }
        return splitStringBySize(resultNumber, 3) as ArrayList<String>?
    }

    private fun splitStringBySize(str: String, size: Int): Collection<String> {
        val split: ArrayList<String> = ArrayList()
        for (i in 0 until str.length / size) {
            split.add(str.substring(i * size, ((i + 1) * size).coerceAtMost(str.length)))
        }
        return split
    }

    private fun threeNumbersToLetter(num: String): String? {
        if ("" == num) {
            return ""
        }
        val parsedInt = num.toInt()
        if (parsedInt < 10) {
            return arraylistWord[0][parsedInt]
        }
        if (parsedInt < 20) {
            return arraylistWord[1][parsedInt - 10]
        }
        if (parsedInt < 100) {
            val one = parsedInt % 10
            val ten = (parsedInt - one) / 10
            return if (one > 0) {
                arraylistWord[2][ten] + splitter + arraylistWord[0][one]
            } else arraylistWord[2][ten]
        }
        val one = parsedInt % 10
        val hundreds = (parsedInt - parsedInt % 100) / 100
        val ten = (parsedInt - (hundreds * 100 + one)) / 10
        val out: ArrayList<String?> = ArrayList()
        out.add(arraylistWord[3][hundreds])
        val secondPart = ten * 10 + one
        if (secondPart > 0) {
            if (secondPart < 10) {
                out.add(arraylistWord[0][secondPart])
            } else if (secondPart < 20) {
                out.add(arraylistWord[1][secondPart - 10])
            } else {
                out.add(arraylistWord[2][ten])
                if (one > 0) {
                    out.add(arraylistWord[0][one])
                }
            }
        }
        return TextUtils.join(splitter!!, out)
    }

    fun getParsedString(input: String): String {
        persianNumberChangeSetup()
        return try {
            val zero = "صفر"
            if ("0" == input) {
                return zero
            }
            if (input.length > 66) {
                return ""
            }
            val processedInput: String = BigDecimal(input.replace("[^\\d.]".toRegex(), "")).toString()
            val splitterNumber = prepareNumber(processedInput)
            val result: ArrayList<String?> = ArrayList()
            val splitLength: Int = splitterNumber!!.size
            for (i in 0 until splitLength) {
                val sectionTitle: String = arraylistWord[4][splitLength - (i + 1)]
                val converted = threeNumbersToLetter(splitterNumber[i])
                if ("" != converted) {
                    result.add(converted + sectionTitle)
                }
            }
            TextUtils.join(splitter!!, result)
        } catch (e: Exception) {
            ""
        }
    }

    fun englishNumberToPersianNumber(number: String): String {
        return try {
            val charArray = number.toCharArray()
            for (i in charArray.indices) {
                when (charArray[i]) {
                    '0' -> charArray[i] = '۰'
                    '1' -> charArray[i] = '۱'
                    '2' -> charArray[i] = '۲'
                    '3' -> charArray[i] = '۳'
                    '4' -> charArray[i] = '۴'
                    '5' -> charArray[i] = '۵'
                    '6' -> charArray[i] = '۶'
                    '7' -> charArray[i] = '۷'
                    '8' -> charArray[i] = '۸'
                    '9' -> charArray[i] = '۹'
                }
            }
            String(charArray)
        } catch (e: Exception) {
            "خطا در تبدیل اعداد"
        }
    }

    fun persianNumberToEnglishNumber(number: String): String {
        return try {
            val charArray = number.toCharArray()
            for (i in charArray.indices) {
                when (charArray[i]) {
                    '۰' -> charArray[i] = '0'
                    '۱' -> charArray[i] = '1'
                    '۲' -> charArray[i] = '2'
                    '۳' -> charArray[i] = '3'
                    '۴' -> charArray[i] = '4'
                    '۵' -> charArray[i] = '5'
                    '۶' -> charArray[i] = '6'
                    '۷' -> charArray[i] = '7'
                    '۸' -> charArray[i] = '8'
                    '۹' -> charArray[i] = '9'
                }
            }
            String(charArray)
        } catch (e: Exception) {
            "Error Converting Numbers"
        }
    }

    fun get3DigitSeparator(strNumber: String): String {
        val sb = StringBuilder(strNumber)
        sb.reverse().toString()
        val strRev = sb.toString()
        val res = StringBuilder()
        var tmp = StringBuilder()
        for (i in strRev.indices) {
            tmp.append(strRev.substring(i, i + 1))
            if (tmp.length == 3 && i != strRev.length - 1) {
                res.insert(0, strRev.substring(i, i + 1))
                res.insert(0, ",")
                tmp = StringBuilder()
            } else {
                res.insert(0, strRev.substring(i, i + 1))
            }
        }
        return res.toString()
    }
}
