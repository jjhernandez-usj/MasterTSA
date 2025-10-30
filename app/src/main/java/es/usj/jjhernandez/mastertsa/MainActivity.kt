package es.usj.jjhernandez.mastertsa

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.usj.jjhernandez.mastertsa.databinding.ActivityMainBinding

const val EMPTY = 0
const val BOMB = 1

data class Tile(var x: Int, var y: Int, var width: Int, var content: Int, var shown: Boolean) {
    fun isInside(newX: Int, newY: Int): Boolean {
        return newX >= x && newX <= x + width && newY >= y &&
                newY <= y + width
    }
}

fun Paint.isClicked(clicked: Boolean) {
    if(clicked)
        setARGB(153, 204, 204, 204)
    else
        setARGB(255, 153, 153, 153)
}

class Board(context: Context, private val size: Int): View(context) {

    private val tileBrush = Paint().apply {
        textSize = 50f
    }

    private val textBrush = Paint().apply {
        textSize = 50f
        setARGB(255,0, 0, 255)
        typeface = Typeface.DEFAULT_BOLD
    }

    private val bombBrush = Paint().apply {
        setARGB(255,255, 0, 0)
    }

    private val lineBrush = Paint().apply {
        setARGB(255,255, 255, 255)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRGB(0,0,0)
        val activity = context as MainActivity
        val width = minOf(activity.board.width, activity.board.height)
        val tileSize = width / size
        var currentRow = 0
        for (row in 0 until size) {
            for (column in 0 until size) {
                val tile = activity.tiles[row][column]
                tile.x = column * tileSize
                tile.y = currentRow
                tile.width = tileSize
                tileBrush.isClicked(tile.shown)

                canvas.drawRect((column * tileSize).toFloat(),
                    currentRow.toFloat(),
                    (column * tileSize + tileSize -2).toFloat(),
                    (currentRow + tileSize - 2).toFloat(), tileBrush)
                canvas.drawLine(
                    (column * tileSize).toFloat(),
                    currentRow.toFloat(),
                    (column * tileSize + tileSize).toFloat(),
                    currentRow.toFloat(),
                    lineBrush)
                canvas.drawLine(
                    (column * tileSize + tileSize - 1).toFloat(),
                    currentRow.toFloat(),
                    (column * tileSize + tileSize - 1).toFloat(),
                    (currentRow + tileSize).toFloat(),
                    lineBrush)
                if(tile.content in 1 until 9 && tile.shown) {
                    canvas.drawText("${tile.content}",
                        (column * tileSize + tileSize / 2 - 1 *
                                size).toFloat(),
                        (currentRow + tileSize / 2).toFloat(),
                        textBrush)
                }
                if(tile.content == BOMB && tile.shown) {
                    bombBrush.setARGB(255,255,0,0)
                    canvas.drawCircle((column * tileSize + tileSize /
                            2).toFloat(),
                        (currentRow + tileSize / 2).toFloat(),
                        size.toFloat(), bombBrush)
                }
                activity.tiles[row][column] = tile
            }
            currentRow += tileSize
        }
    }
}

class MainActivity : AppCompatActivity() {

    val size = 8
    val bombs = 10
    val width = 30

    private val view by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var board: Board

    var tiles : Array<Array<Tile>> = arrayOf()

    var active : Boolean = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view.root)
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        board = Board(this, size)

        board.setOnTouchListener { _, event ->
            if(active) {
                for (row in 0 until size) {
                    for (column in 0 until size) {
                        val tile = tiles[row][column]
                        if (tile.isInside(event.x.toInt(),
                                event.y.toInt())) {
                            tile.shown = true
                            if(tile.content == BOMB) {
                                Toast.makeText(this, "Booooooommmmmmm!!",
                                    Toast.LENGTH_SHORT).show()
                                active = false
                            } else if (tile.content == EMPTY) {
                                crawl(row, column)
                            }
                            board.invalidate()
                        }
                    }
                }
            }
            if(won() && active) {
                Toast.makeText(this, "You win!!!",
                    Toast.LENGTH_LONG).show()
                active = false
            }
            return@setOnTouchListener true
        }
        view.btnReset.setOnClickListener {
            resetTiles()
            active = true
            board.invalidate()
        }
        view.linearLayout.addView(board)
        resetTiles()
        supportActionBar?.hide()
    }

    private fun resetTiles() {
        tiles = Array(size) {
            Array(size) {
                Tile(0, 0, width, EMPTY, false)
            }
        }
        setBombs()
        countPerimeterBombs()
    }

    private fun setBombs() {
        var bombs = bombs
        do {
            val row = (Math.random() * size).toInt()
            val column = (Math.random() * size).toInt()
            if(tiles[row][column].content == EMPTY) {
                tiles[row][column].content = BOMB
                bombs--
            }
        } while (bombs != 0)
    }

    private fun countPerimeterBombs() {
        for (row in 0 until size) {
            for (column in 0 until size) {
                if(tiles[row][column].content == EMPTY) {
                    val neighbors = findBombsByTile(row,
                        column)
                    tiles[row][column].content = neighbors
                }
            }
        }
    }

    private fun won(): Boolean {
        var shownTiles = 0
        tiles.forEach {
            it.forEach { tile ->
                if (tile.shown) shownTiles++
            }
        }
        return size * size - shownTiles - bombs == 0
    }


    private fun crawl(row: Int, column: Int) {
        if (row in 0 until size && column >= 0 && column <
            size) {
            if (tiles[row][column].content == EMPTY) {
                tiles[row][column].shown = true
                tiles[row][column].content = 50
                crawl(row, column + 1)
                crawl(row, column - 1)
                crawl(row + 1, column)
                crawl(row - 1, column)
                crawl(row - 1, column - 1)
                crawl(row - 1, column + 1)
                crawl(row + 1, column + 1)
                crawl(row + 1, column - 1)
            } else if (tiles[row][column].content in 1..9) {
                tiles[row][column].shown = true
            }
        }
    }

    private fun findBombsByTile(row: Int, column: Int) : Int {
        var total = 0
        if (row - 1 >= 0 && column - 1 >= 0)
            if (tiles[row - 1][column - 1].content == BOMB)
                total++
        if (row - 1 >= 0)
            if (tiles[row - 1][column].content == BOMB)
                total++
        if (row - 1 >= 0 && column + 1 < size)
            if (tiles[row - 1][column + 1].content == BOMB)
                total++
        if (column + 1 < size)
            if (tiles[row][column + 1].content == BOMB)
                total++
        if (row + 1 < size && column + 1 < size)
            if (tiles[row + 1][column + 1].content == BOMB)
                total++
        if (row + 1 < size)
            if (tiles[row + 1][column].content == BOMB)
                total++
        if (row + 1 < size && column - 1 >= 0)
            if (tiles[row + 1][column - 1].content == BOMB)
                total++
        if (column - 1 >= 0)
            if (tiles[row][column - 1].content == BOMB)
                total++
        return total
    }
}

