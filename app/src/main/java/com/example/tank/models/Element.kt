package com.example.tank.models

import com.example.tank.enums.Material

// материал который добавить и куда
data class Element(
    val viewId: Int,
    val material: Material,
    val coordinate: Coordinate
)