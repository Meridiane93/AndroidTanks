package com.example.tank

import android.app.Activity
import android.view.View
import android.widget.FrameLayout

class GridDrawer(private val container: FrameLayout) {

    private val allLines = mutableListOf<View>() // кладём наши линии

    fun removeGrid(){  // удаление сетки и эл добавления
        allLines.forEach { container.removeView(it) }
    }

    fun drawGrid(){
        drawHorizontalLines()
        drawVerticalLines()
    }

    private fun drawHorizontalLines() { // создание горизонтальных линий
        var topMargin = 0
        while (topMargin <= container.layoutParams.height){
            val horizontalLine = View(container.context)
            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1)
            topMargin += CELL_SIZE
            layoutParams.topMargin = topMargin
            horizontalLine.layoutParams = layoutParams
            horizontalLine.setBackgroundColor(container.context.resources.getColor(android.R.color.white))
            allLines.add(horizontalLine)
            container.addView(horizontalLine)
        }
    }

    private fun drawVerticalLines() { // создание вертикальных линий
        var leftMargin = 0
        while (leftMargin <= container.layoutParams.width){
            val verticallLine = View(container.context)
            val layoutParams = FrameLayout.LayoutParams(1, FrameLayout.LayoutParams.MATCH_PARENT)
            leftMargin += CELL_SIZE
            layoutParams.leftMargin = leftMargin
            verticallLine.layoutParams = layoutParams
            verticallLine.setBackgroundColor(container.context.resources.getColor(android.R.color.white))
            allLines.add(verticallLine)
            container.addView(verticallLine)
        }
    }

}