package com.example.safetakeaway

import androidx.fragment.app.Fragment

class AppData {
    companion object {
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var selectedOrder : Order? = null
        var selectedRestaurant : Restaurant? = null
        var selectedPlates : Plates? = null
    }
}