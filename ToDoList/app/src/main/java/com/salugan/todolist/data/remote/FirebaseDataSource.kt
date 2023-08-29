package com.salugan.todolist.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.salugan.todolist.data.Result
import com.salugan.todolist.model.Book

class FirebaseDataSource {

    private val database = Firebase.database("https://eureka-todolist-default-rtdb.asia-southeast1.firebasedatabase.app")
    private val ref = database.reference.child("buku")

    fun getListBook(): LiveData<Result<List<Book>>> {
        val liveData = MutableLiveData<Result<List<Book>>>()

        liveData.value = Result.Loading
        val bookListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listBook = mutableListOf<Book>()
                dataSnapshot.children.forEach { snapshot ->
                    val book = snapshot.getValue(Book::class.java)
                    book?.let {
                        listBook.add(it)
                    }
                }

                if (listBook.isEmpty()) liveData.postValue(Result.Empty)
                liveData.value = Result.Success(listBook)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                liveData.value = Result.Error(databaseError.message)
                Log.w("wokwodka", "loadPost:onCancelled", databaseError.toException())
            }
        }

        ref.addValueEventListener(bookListener)
        return liveData
    }

    fun getSpecificBook(bookId: String): LiveData<Result<Book>> {
        val liveData = MutableLiveData<Result<Book>>()

        liveData.value = Result.Loading
        val bookListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val book = dataSnapshot.getValue(Book::class.java)
                book?.let {
                    liveData.value = Result.Success(it)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                liveData.value = Result.Error(databaseError.message)
                Log.w("wokwodka", "loadPost:onCancelled", databaseError.toException())
            }
        }

        ref.child(bookId).addValueEventListener(bookListener)
        return liveData
    }

    fun addBook(book: Book) {
        val bookId = ref.push().key
        bookId?.let {
            book.id = bookId
            ref.child(bookId).setValue(book)
        }
    }

    fun editBook(book: Book) {
        book.id?.let {
            ref.child(it).setValue(book)
        }
    }

    fun deleteBook(book: Book) {
        book.id?.let {
            ref.child(it).removeValue()
        }
    }

}