package com.example.praktikum6.data

data class OrderUIState(
    var nama: String="",
    var alamat: String="",
    var tlp: String="",
    val jumlah: Int =0,
    val rasa: String = "",
    val harga: String = ""
)
