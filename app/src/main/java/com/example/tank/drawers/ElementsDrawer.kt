package com.example.tank.drawers

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.tank.HORIZONTAL_MAX_SIZE
import com.example.tank.R
import com.example.tank.VERTICAL_MAX_SIZE
import com.example.tank.enums.Material
import com.example.tank.models.Coordinate
import com.example.tank.models.Element

class ElementsDrawer(val container:FrameLayout) {

    var currentMaterial = Material.EMPTY

    private val elementsOnContainer = mutableListOf<Element>()

    // добавляем материал внутрь клетки
    fun onTouchContainer(x: Float, y: Float) {
        val topMargin = y.toInt() - (y.toInt() % 50)
        val leftMargin = x.toInt() - (x.toInt() % 50)
        val coordinate = Coordinate(topMargin, leftMargin)

        if (currentMaterial == Material.EMPTY) eraseView(coordinate)
        else drawOrReplaceView(coordinate)
    }

    // рисовать view или изменять
    private fun drawOrReplaceView(coordinate: Coordinate){
        val viewOnCoordinate = getElementByCoordinates(coordinate)
        if (viewOnCoordinate == null){
            drawView(coordinate)
            return
        }
        if (viewOnCoordinate.material != currentMaterial) replaceView(coordinate)
    }

    // стираем старую рисуем новую
    private fun replaceView(coordinate: Coordinate){
        eraseView(coordinate)
        drawView(coordinate)
    }

    fun getElementByCoordinates(coordinate: Coordinate) =
        elementsOnContainer.firstOrNull{ it.coordinate == coordinate }

    // стирание view
    private fun eraseView(coordinate: Coordinate){
        val elementOnCoordinate = getElementByCoordinates(coordinate)
        if (elementOnCoordinate != null){
            val erasingView = container.findViewById<View>(elementOnCoordinate.viewId)
            container.removeView(erasingView)
            elementsOnContainer.remove(elementOnCoordinate)
        }
    }

    // размеры материала и выбранный материал
     fun drawView(coordinate: Coordinate){
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(50,50)
        when(currentMaterial){
            Material.EMPTY -> { }
            Material.BRICK -> view.setImageResource(R.drawable.brick)
            Material.CONCRETE -> view.setImageResource(R.drawable.concrete)
            Material.GRASS -> view.setImageResource(R.drawable.grass)
        }
        layoutParams.topMargin = coordinate.top  // координаты относительно верх и низ
        layoutParams.leftMargin = coordinate.left // координаты относительно право и лево

        val viewId = View.generateViewId() // генерация id для материалов
        view.id = viewId

        view.layoutParams = layoutParams
        container.addView(view)
        elementsOnContainer.add(Element(viewId,currentMaterial,coordinate))
    }
    // определяем координаты танка
    fun getTankCoordinates(topLeftCoordinate:Coordinate):List<Coordinate>{
        val coordinateList = mutableListOf<Coordinate>()
        coordinateList.add(topLeftCoordinate)
        coordinateList.add(Coordinate(topLeftCoordinate.top + 50,topLeftCoordinate.left))
        coordinateList.add(Coordinate(topLeftCoordinate.top,topLeftCoordinate.left + 50))
        coordinateList.add(Coordinate(topLeftCoordinate.top + 50,topLeftCoordinate.left + 50))
        return coordinateList
    }

    // проверка может ли танк двигаться дальше
    fun checkTankCanMoveTroughMaterial(coordinate: Coordinate): Boolean {
        getTankCoordinates(coordinate).forEach {
            val element = getElementByCoordinates(it)
            if (element != null && !element.material.tankCanGoThrough) {
                return false
            }
        }
        return true
    }

    // проверка не столкнулся ли танк с границами контейнера
    fun checkTankCanMoveTroughBorder(coordinate: Coordinate, myTank:View): Boolean{
        if (coordinate.top >= 0 &&
            coordinate.top + myTank.height <= HORIZONTAL_MAX_SIZE &&
            coordinate.left >= 0 &&
            coordinate.left + myTank.width <= VERTICAL_MAX_SIZE
        ) return true
        return false
    }

    fun up(view: View,myTank:View) {
        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
        val currentCoordinate = Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
        myTank.rotation = 0f
        (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += -50
        val nextCoordinate = Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
        if (checkTankCanMoveTroughBorder(nextCoordinate, myTank) && checkTankCanMoveTroughMaterial(nextCoordinate)
        ) {
            container.removeView(myTank)
            container.addView(myTank, 0)
        } else {
            (myTank.layoutParams as FrameLayout.LayoutParams).topMargin = currentCoordinate.top
            (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin = currentCoordinate.left
        }
    }
    fun right(view: View,myTank:View) {
        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
        val currentCoordinate = Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
        myTank.rotation = 90f
        (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += 50
        val nextCoordinate = Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
        if (checkTankCanMoveTroughBorder(nextCoordinate, myTank) && checkTankCanMoveTroughMaterial(nextCoordinate)
        ) {
            container.removeView(myTank)
            container.addView(myTank, 0)
        } else {
            (myTank.layoutParams as FrameLayout.LayoutParams).topMargin = currentCoordinate.top
            (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin = currentCoordinate.left
        }
    }
    fun bottom(view: View,myTank:View) {
        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
        val currentCoordinate = Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
        myTank.rotation = 180f
        (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += 50
        val nextCoordinate = Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
        if (checkTankCanMoveTroughBorder(nextCoordinate, myTank) && checkTankCanMoveTroughMaterial(nextCoordinate)
        ) {
            container.removeView(myTank)
            container.addView(myTank, 0)
        } else {
            (myTank.layoutParams as FrameLayout.LayoutParams).topMargin = currentCoordinate.top
            (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin = currentCoordinate.left
        }
    }
    fun left(view: View,myTank:View) {
        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
        val currentCoordinate = Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
        myTank.rotation = 270f
        (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += -50
        val nextCoordinate = Coordinate(layoutParams.topMargin,layoutParams.leftMargin)
        if (checkTankCanMoveTroughBorder(nextCoordinate, myTank) && checkTankCanMoveTroughMaterial(nextCoordinate)
        ) {
            container.removeView(myTank)
            container.addView(myTank, 0)
        } else {
            (myTank.layoutParams as FrameLayout.LayoutParams).topMargin = currentCoordinate.top
            (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin = currentCoordinate.left
        }
    }
}