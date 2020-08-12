package com.pegasus.retrofittest

data class User (val results: List<Result>)
data class Result (val name: Name, val email: String, val gender: String, val phone: String)
data class Name (val title: String, val first: String, val last: String)