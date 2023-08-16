fun cekPalindrome(teks: String): Boolean {
    val panjang = teks.length
    for(i in 0 until panjang / 2){
        if (teks[i].lowercaseChar() != teks[panjang - i - 1].lowercaseChar()) return false
    }
    return true
}

fun main() {
    println(cekPalindrome("Katak"))
    println(cekPalindrome("ada"))
    println(cekPalindrome("orang"))
    println(cekPalindrome("kemarin"))
}