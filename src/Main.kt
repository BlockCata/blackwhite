import java.awt.*
import java.awt.event.ActionListener
import javax.swing.*

class testUI : JFrame() {

    fun showFrame() {
        //---------遊戲介面
        val uiJp = JPanel()
        //------------組件介面
        val buttonJp = JPanel()
        //---按鈕,按鈕文字
        val blackBotton = JButton("黑棋")
        val whiteBotton = JButton("白棋")
        //------按鈕大小
        whiteBotton.preferredSize = Dimension(100, 100)
        blackBotton.preferredSize = Dimension(100, 100)

        val label = JLabel("黑棋下")
        label.font = Font("", Font.BOLD, 35)

        val blackConut = JLabel("2")
        val whiteConut = JLabel("2")

        blackConut.font = Font("", Font.BOLD, 40)
        whiteConut.font = Font("", Font.BOLD, 40)

        //---重置按鈕
        val restartBu = JButton("restart")
        restartBu.preferredSize = Dimension(100, 40)
        buttonJp.add(label)
        buttonJp.add(blackBotton)
        buttonJp.add(blackConut)
        buttonJp.add(whiteBotton)
        buttonJp.add(whiteConut)
        buttonJp.add(restartBu)
        layout = GridLayout(1, 2, 600, 0)
        add(uiJp)
        add(buttonJp)
        setSize(1000, 800)
        setLocationRelativeTo(null)
        title = "黑白棋程式"
        this.isVisible = true

        //-----畫布權限

        grid[2][2] = 1
        grid[3][3] = 1
        grid[2][3] = 2
        grid[3][2] = 2

        addMouseListener(gridListener(graphics, this, label, blackConut, whiteConut))

        val buttonLis = ActionListener {
            clear()
            repaint()
            grid[2][2] = 1
            grid[3][3] = 1
            grid[2][3] = 2
            grid[3][2] = 2

            gridListener.stata = 1

            label.text = "黑棋下"
            blackConut.text = "2"
            whiteConut.text = "2"
        }
        val blackLis = ActionListener {
            gridListener.stata = 1
            label.text = "黑棋下"
        }
        val whiteLis = ActionListener {
            gridListener.stata = 2
            label.text = "白棋下"
        }
        blackBotton.addActionListener(blackLis)
        whiteBotton.addActionListener(whiteLis)
        restartBu.addActionListener(buttonLis)
        defaultCloseOperation = 3
    }

    fun clear() {
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                grid[i][j] = 0
            }
        }
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        // 橫線
        for (i in 0..rows - 1) {
            g.color = Color.DARK_GRAY
            g.drawLine(
                gridx,
                gridy + i * Companion.size,
                gridx + Companion.size * (rows - 1),
                gridy + i * Companion.size
            )
        }

        // 垂直線
        for (i in 0..cols - 1) {
            g.color = Color.DARK_GRAY
            g.drawLine(
                gridx + i * Companion.size,
                gridy,
                gridx + i * Companion.size,
                gridy + Companion.size * (cols - 1)
            )

        }

        // 畫棋子
        for (i in 0 until rows - 1) {
            for (j in 0 until cols - 1) {
                val X = Companion.gridx + Companion.size / 2 + Companion.size * i
                val Y = Companion.gridy + Companion.size / 2 + Companion.size * j

                if (grid[i][j] == 1) {
                    g.color = Color.BLACK
                    g.fillOval(X - point_size / 2, Y - point_size / 2, point_size, point_size)
                } else if (grid[i][j] == 2) {
                    g.color = Color.WHITE
                    g.fillOval(X - point_size / 2, Y - point_size / 2, point_size, point_size)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            testUI().showFrame()
            EventQueue.invokeLater(::testUI)
        }
        const val gridx = 100 //棋盤初始點橫坐標
        const val gridy = 100 //棋盤初始點縱坐標
        const val rows = 7 //棋盤行數
        const val cols = 7 //棋盤列數
        const val size = 100 //棋盤格子大小
        const val point_size = 95 //棋子大小
        val grid = Array(rows - 1) { IntArray(cols - 1) } //定義一個6*6的數組，用來保存棋子
    }
}


