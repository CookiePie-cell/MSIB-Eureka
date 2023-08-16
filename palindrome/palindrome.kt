fun cekPalindrome(teks: String): String {
    val panjang = teks.length
    for(i in 0 until panjang / 2){
        if (teks[i].lowercaseChar() != teks[panjang - i - 1].lowercaseChar()) return "Bukan palindrom"
    }
    return "Palindrom"
}

fun main() {
    println(cekPalindrome("Katak"))
    println(cekPalindrome("ada"))
    println(cekPalindrome("orang"))
    println(cekPalindrome("kemarin"))
}