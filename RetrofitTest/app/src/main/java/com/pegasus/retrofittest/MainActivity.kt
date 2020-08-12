package com.pegasus.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var userRepository: UserRepository

    private val component = DaggerMainActivityComponent
        .builder()
        .applicationModule(ApplicationModule())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)

        runBlocking {
            val job = async(Dispatchers.Default) {
                getUserInfo()
            }
            job.await()
        }
    }

    private fun getUserInfo() {
        val response = userRepository.getUser()
        Log.d("ActivityUserAPITag",response.body().toString())

        val user = response.body() as User

        val name = user.results[0].name
        text_name.text = name.title + " " + name.first + " " + name.last
        text_email.text = user.results[0].email
        text_gender.text = user.results[0].gender
        text_phone.text = user.results[0].phone
    }
}