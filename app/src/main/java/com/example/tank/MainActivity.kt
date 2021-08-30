package com.example.tank

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.tank.drawers.ElementsDrawer
import com.example.tank.enums.Material
import com.example.tank.models.Coordinate
import kotlinx.android.synthetic.main.activity_main.*

const val CELL_SIZE = 50
const val VERTICAL_CELL_AMOUNT = 21
const val HORIZONTAL_CELL_AMOUNT = 31
const val VERTICAL_MAX_SIZE = CELL_SIZE * VERTICAL_CELL_AMOUNT
const val HORIZONTAL_MAX_SIZE = CELL_SIZE * HORIZONTAL_CELL_AMOUNT

class MainActivity : AppCompatActivity() {

    private var editMode = false // изначально игра не в режиме редактирования

    private val gridDrawer by lazy {
        GridDrawer(container)
    }

    private val elementsDrawer by lazy {
        ElementsDrawer(container)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container.layoutParams = FrameLayout.LayoutParams(1080,2340) // размер экрана
        editor_clear.setOnClickListener { elementsDrawer.currentMaterial = Material.EMPTY }
        editor_brick.setOnClickListener { elementsDrawer.currentMaterial = Material.BRICK }
        editor_concrete.setOnClickListener { elementsDrawer.currentMaterial = Material.CONCRETE }
        editor_grass.setOnClickListener { elementsDrawer.currentMaterial = Material.GRASS }

        container.setOnTouchListener { _, event ->
            elementsDrawer.onTouchContainer(event.x,event.y)
            return@setOnTouchListener true
        } // слушатель для экрана

    }

    // инфлейтим ( надуваем ) меню
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting,menu)
        return true
    }

    // на какое меню мы нажали
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                switchEditMode()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // переключение в режим редактирования
    private fun switchEditMode(){
        if (editMode){
            gridDrawer.removeGrid()
            materials_container.visibility = GONE
        }else{
            gridDrawer.drawGrid()
            materials_container.visibility = VISIBLE
        }
        editMode = !editMode
    }

    fun  up(view: View){
        ElementsDrawer(container).up(view,myTank)
    }
    fun  bottom(view: View){
        ElementsDrawer(container).bottom(view,myTank)
    }
    fun  left(view: View){
        ElementsDrawer(container).left(view,myTank)
    }
    fun  right(view: View){
        ElementsDrawer(container).right(view,myTank)
    }
}