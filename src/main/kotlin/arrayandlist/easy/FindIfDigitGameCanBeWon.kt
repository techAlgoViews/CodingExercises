package main.kotlin.arrayandlist.easy

abstract class FindIfDigitGameCanBeWon {

    abstract fun canAliceWin(nums: IntArray): Boolean
}

class FindIfDigitGameCanBeWonImpl: FindIfDigitGameCanBeWon() {
    override fun canAliceWin(nums: IntArray): Boolean {
        var singleDigit = 0
        var doubleDigit = 0
        nums.forEach {
            if (it <= 9) singleDigit += it
            else doubleDigit += it
        }
        if (singleDigit > doubleDigit || doubleDigit > singleDigit) return true;
        return false
    }

}