import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class gridListener(
    private val g: Graphics,
    private val player: testUI,
    private val label: JLabel,
    private val blackConut: JLabel,
    private val whiteConut: JLabel,
) : MouseAdapter() {
    // 紀錄滑鼠點擊的X,Y座標
    private var mouseClickX = 0
    private var mouseClickY = 0

    // 結束遊戲的條件
    private var spe = 0

    // 改變的棋子
    private var changePoint = 0


    override fun mouseClicked(event: MouseEvent) {
        mouseClickX = event.x
        mouseClickY = event.y

        for (i in 0 until testUI.rows - 1) {
            for (j in 0 until testUI.cols - 1) {
                val x2 = testUI.gridx + testUI.size / 2 + testUI.size * i
                val y2 = testUI.gridy + testUI.size / 2 + testUI.size * j

                if (Math.abs(mouseClickX - x2) < testUI.size / 4 && Math.abs(mouseClickY - y2) < testUI.size / 4) {
                    if (testUI.grid[i][j] == 0) {
                        testUI.grid[i][j] = stata

                        if (check(i, j, -1, 0) == null && check(i, j, 1, 0) == null &&
                            check(i, j, 0, -1) == null && check(i, j, 0, 1) == null &&
                            check(i, j, -1, 1) == null && check(i, j, 1, 1) == null &&
                            check(i, j, -1, -1) == null && check(i, j, 1, -1) == null
                        ) {
                            testUI.grid[i][j] = 0
                            player.update(g)
                            JOptionPane.showMessageDialog(null, "不能落子")
                        } else {
                            paintChange(i, j, -1, 0 ,check(i, j, -1, 0)  )
                            paintChange(i, j, 1, 0  ,check(i, j, 1, 0))
                            paintChange(i, j, 0, -1 ,check(i, j, 0, -1)  )
                            paintChange(i, j, 0, 1  ,check(i, j, 0, 1)  )
                            paintChange(i, j, -1, 1 ,check(i, j, -1, 1)  )
                            paintChange(i, j, 1, 1  ,check(i, j, 1, 1)  )
                            paintChange(i, j, -1, -1,check(i, j, -1, -1) )
                            paintChange(i, j, 1, -1 ,check(i, j, 1, -1) )

                            player.update(g)
                            // 還有幾個可以下的地方
                            blackConut.text = whoWin()[0].toString() + ""
                            whiteConut.text = whoWin()[1].toString() + ""
                            println("改變了 $changePoint 個棋子")
                            changePoint = 0

                            if (stata == 1) {
                                stata = 2
                                label.text = "白棋下"
                            } else if (stata == 2) {
                                stata = 1
                                label.text = "黑棋下"
                            }

                            if (typeCheck(stata) == 0 && full() == false) {
                                spe++
                                JOptionPane.showMessageDialog(null, "不能落子，跳過")
                                if (stata == 1) {
                                    stata = 2
                                    label.text = "白棋下"
                                } else if (stata == 2) {
                                    stata = 1
                                    label.text = "黑棋下"
                                }
                            }
                            if (stata == 1)println("黑棋 還有 ${typeCheck(stata)} 種下法")
                            if (stata == 2)println("白棋 還有 ${typeCheck(stata)} 種下法")
                            if (typeCheck(stata) == 0) spe++
                            else spe = 0
                        }
                        if (whoWin()[0] == 0) JOptionPane.showMessageDialog(null, "遊戲結束,白棋勝利")
                        else if (whoWin()[1] == 0) JOptionPane.showMessageDialog(null, "遊戲結束,黑棋勝利")

                        if (full()) {
                            if (whoWin()[0] > whoWin()[1]) JOptionPane.showMessageDialog(null, "黑棋數較多,黑棋勝利")
                            else if (whoWin()[0] < whoWin()[1]) JOptionPane.showMessageDialog(null, "白棋數較多,白棋勝利")
                            else if (whoWin()[0] == whoWin()[1]) JOptionPane.showMessageDialog(null, "和局")
                        }
                    }
                    return
                }
            }
        }
    }

    fun full(): Boolean {
        if (spe == 2) {
            return true
        } else {
            for (i in 0 until testUI.rows - 1) {
                for (j in 0 until testUI.cols - 1) {
                    //如果有一個地方是空的則返回false
                    if (testUI.grid[i][j] == 0) {
                        return false
                    }
                }
            }
        }
        return true
    }


    // 如果黑棋較多則黑棋獲勝，否則白棋獲勝
    fun whoWin(): IntArray {
        val count = IntArray(2)
        for (i in 0 until testUI.rows - 1) {
            for (j in 0 until testUI.cols - 1) {
                if (testUI.grid[i][j] == 1) {
                    count[0]++
                } else if (testUI.grid[i][j] == 2) {
                    count[1]++
                }
            }
        }
        return count
    }

    fun typeCheck(stata: Int): Int {
        var n = 0
        for (i in testUI.grid.indices) {
            for (j in testUI.grid[i].indices) {
                if (testUI.grid[i][j] != 0) {
                    continue
                } else {
                    testUI.grid[i][j] = stata
                    if (check(i, j, -1, 0) != null ||
                        check(i, j, 1, 0) != null ||
                        check(i, j, 0, -1) != null ||
                        check(i, j, 0, 1) != null ||
                        check(i, j, -1, 1) != null ||
                        check(i, j, 1, 1) != null ||
                        check(i, j, -1, -1) != null ||
                        check(i, j, 1, -1) != null
                    ) {
                        testUI.grid[i][j] = 0
                        n++ //如果有一個地方可以下子，則n+1
                    } else {
                        testUI.grid[i][j] = 0
                    }
                }
            }
        }
        return n
    }

    // 八個方向檢查
    fun check(x: Int, y: Int, x2: Int, y2: Int): IntArray? {
        var checkIsEmpty = false
        var checkaAjacentIsOwn = false
        var _y = y + y2
        var _x = x + x2
        var num = 0
        if (x == 0 && x2 == -1) return null
        if (x == 5 && x2 == 1) return null
        if (y == 0 && y2 == -1) return null
        if (y == 5 && y2 == 1) return null
        // 如果落子位置是空位
        if (testUI.grid[x][y] == stata) {
            //---------------------- 如果判斷第一個方向的子是 0 || 如果判斷第一個方向的子是自己 return null
            if (testUI.grid[_x][_y] == 0 || testUI.grid[_x][_y] == stata) return null
            checkIsEmpty = true
            num++
            while (_x <= 5 && _y <= 5) {
                _x += x2
                _y += y2
                num++
                if (_x >= 0 && _x <= 5 && _y >= 0 && _y <= 5) {
                    if (testUI.grid[_x][_y] == stata) {
                        checkaAjacentIsOwn = true
                        break
                    }
                } else break
            }
        } else return null
        return if (checkIsEmpty && checkaAjacentIsOwn) {
            intArrayOf(num)

        } else null
    }

    // 翻轉棋子
    fun paintChange(x: Int, y: Int, x2: Int, y2: Int, num: IntArray?) {
        var _x2 = x + x2
        var _y2 = y + y2
        if (num == null) return
        for (i in 1 until num[0]) {
            testUI.grid[_x2][_y2] = stata
            paintChange(_x2, _y2, -1, 0 ,check(_x2, _y2, -1, 0)  )
            paintChange(_x2, _y2, 1, 0  ,check(_x2, _y2, 1, 0))
            paintChange(_x2, _y2, 0, -1 ,check(_x2, _y2, 0, -1)  )
            paintChange(_x2, _y2, 0, 1  ,check(_x2, _y2, 0, 1)  )
            paintChange(_x2, _y2, -1, 1 ,check(_x2, _y2, -1, 1)  )
            paintChange(_x2, _y2, 1, 1  ,check(_x2, _y2, 1, 1)  )
            paintChange(_x2, _y2, -1, -1,check(_x2, _y2, -1, -1) )
            paintChange(_x2, _y2, 1, -1 ,check(_x2, _y2, 1, -1) )
            _x2 += x2
            _y2 += y2
            changePoint++
        }
    }
    companion object {
        var stata = 1
    }
}
