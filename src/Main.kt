const val white = 1
const val black = 2
var next = 1
fun main() {
    println("歡迎使用黑白棋系統")
    val cb = Array(8) { IntArray(8) }
    cb[3][3] = 1
    cb[4][4] = 1
    cb[4][3] = 2
    cb[3][4] = 2
    grid(cb)

    while (gameOvercheck(cb)) {
        println("請輸入落子位置: ")
        val input = readLine()!!.split(" ")
        val x = input[0].toInt() - 1
        val y = input[1].toInt() - 1

        if (cb[y][x] == 0) {
            if (allCheck(
                    leftCheck(cb, x, y, next), rightCheck(cb, x, y, next), upCheck(cb, x, y, next),
                    downCheck(cb, x, y, next), leftupCheck(cb, x, y, next), rightupCheck(cb, x, y, next),
                    leftdownCheck(cb, x, y, next), rightdownCheck(cb, x, y, next), cb, x, y
                )
            ) {
                cb[y][x] = next
                if (next == 1) {
                    next = 2
                    println("換黑子")
                } else {
                    next = 1
                    println("換白子")
                }
            }
        } else println("你不能在此落子")
        grid(cb)
    }
    println("遊戲結束")
}

fun grid(g: Array<IntArray>) {
    var num = 1
    println("  1 2 3 4 5 6 7 8")
    for (list in g) {
        print("$num ")
        for (point in list) {
            print(point)
            print(" ")
        }
        num++
        println(" ")
    }
}

fun leftCheck(grid: Array<IntArray>, x: Int, y: Int, next: Int): Boolean {
//    if (grid[y][x] != 0) return false
    var d = false
    var s = false
    var _x = x - 1
    while (_x >= 0) {
        val point = grid[y][_x]
        if (point == next) {
            s = true
            break
        } else if (point == 0) break
        else {
            d = true
        }
        _x--
    }
    if (d && s) for (newnext in _x..x) {
        grid[y][newnext] = next
        allCheck(
            leftCheck(grid, newnext, y, next),
            rightCheck(grid, newnext, y, next),
            upCheck(grid, newnext, y, next),
            downCheck(grid, newnext, y, next),
            leftupCheck(grid, newnext, y, next),
            rightupCheck(grid, newnext, y, next),
            leftdownCheck(grid, newnext, y, next),
            rightdownCheck(grid, newnext, y, next),
            grid,
            newnext,
            y
        )
    }
    return d && s
}

fun rightCheck(grid: Array<IntArray>, x: Int, y: Int, next: Int): Boolean {
//    if (grid[y][x] != 0) return false
    var d = false
    var s = false
    var _x = x + 1
    while (_x <= 7) {
        val point = grid[y][_x]

        if (point == next) {
            s = true
            break
        } else if (point == 0) break
        else {
            d = true
        }
        _x++
    }
    if (d && s) for (newnext in x.._x) {
        grid[y][newnext] = next
        allCheck(
            leftCheck(grid, newnext, y, next),
            rightCheck(grid, newnext, y, next),
            upCheck(grid, newnext, y, next),
            downCheck(grid, newnext, y, next),
            leftupCheck(grid, newnext, y, next),
            rightupCheck(grid, newnext, y, next),
            leftdownCheck(grid, newnext, y, next),
            rightdownCheck(grid, newnext, y, next),
            grid,
            newnext,
            y
        )
    }
    return d && s
}

fun upCheck(grid: Array<IntArray>, x: Int, y: Int, next: Int): Boolean {
//    if (grid[y][x] != 0) return false
    var d = false
    var s = false
    var _y = y - 1
    while (_y >= 0) {
//        println("檢查 ${x + 1} ${_y + 1} 現在是 ${grid[_y][x]}")
        val point = grid[_y][x]
        if (point == next) {
            s = true
            break
        } else if (point == 0) break
        else {
            d = true
        }
        _y--
    }
    if (d && s) for (newnext in _y..y) {
        grid[newnext][x] = next
        allCheck(
            leftCheck(grid, x, newnext, next),
            rightCheck(grid, x, newnext, next),
            upCheck(grid, x, newnext, next),
            downCheck(grid, x, newnext, next),
            leftupCheck(grid, x, newnext, next),
            rightupCheck(grid, x, newnext, next),
            leftdownCheck(grid, x, newnext, next),
            rightdownCheck(grid, x, newnext, next),
            grid,
            x,
            newnext
        )
    }
    return d && s
}

fun downCheck(grid: Array<IntArray>, x: Int, y: Int, next: Int): Boolean {
//    if (grid[y][x] != 0) return false
    var d = false
    var s = false
    var _y = y + 1
    while (_y <= 7) {
        val point = grid[_y][x]
        if (point == next) {
            s = true
            break
        } else if (point == 0) break
        else {
            d = true
        }
        _y++
    }
    if (d && s) for (newnext in y.._y) {
        grid[newnext][x] = next
        allCheck(
            leftCheck(grid, x, newnext, next),
            rightCheck(grid, x, newnext, next),
            upCheck(grid, x, newnext, next),
            downCheck(grid, x, newnext, next),
            leftupCheck(grid, x, newnext, next),
            rightupCheck(grid, x, newnext, next),
            leftdownCheck(grid, x, newnext, next),
            rightdownCheck(grid, x, newnext, next),
            grid,
            x,
            newnext
        )
    }
    return d && s
}

fun leftupCheck(grid: Array<IntArray>, x: Int, y: Int, next: Int): Boolean {
//    if (grid[y][x] != 0) return false
    var d = false
    var s = false
    var _x = x - 1
    var _y = y - 1
    while (_y >= 0 && _x >= 0) {
        val point = grid[_y][_x]
        if (point == next) {
            s = true
            break
        } else if (point == 0) break
        else {
            d = true
        }
        _y--
        _x--
    }
    var newX = x - 1
    var newY = y - 1
    if (d && s) while (newY >= _y || newX >= _x) {
        grid[newY][newX] = next
        allCheck(
            leftCheck(grid, newX, newY, next),
            rightCheck(grid, newX, newY, next),
            upCheck(grid, newX, newY, next),
            downCheck(grid, newX, newY, next),
            leftupCheck(grid, newX, newY, next),
            rightupCheck(grid, newX, newY, next),
            leftdownCheck(grid, newX, newY, next),
            rightdownCheck(grid, newX, newY, next),
            grid,
            newX,
            newY
        )
        newY--
        newX--
    }
    return d && s
}

fun rightupCheck(grid: Array<IntArray>, x: Int, y: Int, next: Int): Boolean {
//    if (grid[y][x] != 0) return false
    var d = false
    var s = false
    var _x = x + 1
    var _y = y - 1
    while (_y >= 0 && _x <= 7) {
        val point = grid[_y][_x]
        if (point == next) {
            s = true
            break
        } else if (point == 0) break
        else {
            d = true
        }
        _y--
        _x++
    }
    var newX = x + 1
    var newY = y - 1
    if (d && s) while (newY >= _y || newX <= _x) {
        grid[newY][newX] = next
        allCheck(
            leftCheck(grid, newX, newY, next),
            rightCheck(grid, newX, newY, next),
            upCheck(grid, newX, newY, next),
            downCheck(grid, newX, newY, next),
            leftupCheck(grid, newX, newY, next),
            rightupCheck(grid, newX, newY, next),
            leftdownCheck(grid, newX, newY, next),
            rightdownCheck(grid, newX, newY, next),
            grid,
            newX,
            newY
        )
        newY--
        newX++
    }
    return d && s
}

fun leftdownCheck(grid: Array<IntArray>, x: Int, y: Int, next: Int): Boolean {
//    if (grid[y][x] != 0) return false
    var d = false
    var s = false
    var _x = x - 1
    var _y = y + 1
    while (_y <= 7 && _x >= 0) {
        val point = grid[_y][_x]
        if (point == next) {
            s = true
            break
        } else if (point == 0) break
        else {
            d = true
        }
        _y++
        _x--
    }
    var newX = x - 1
    var newY = y + 1
    if (d && s) while (newY <= _y || newX >= _x) {
        grid[newY][newX] = next
        allCheck(
            leftCheck(grid, newX, newY, next),
            rightCheck(grid, newX, newY, next),
            upCheck(grid, newX, newY, next),
            downCheck(grid, newX, newY, next),
            leftupCheck(grid, newX, newY, next),
            rightupCheck(grid, newX, newY, next),
            leftdownCheck(grid, newX, newY, next),
            rightdownCheck(grid, newX, newY, next),
            grid,
            newX,
            newY
        )
        newY++
        newX--
    }
    return d && s
}

fun rightdownCheck(grid: Array<IntArray>, x: Int, y: Int, next: Int): Boolean {
//    if (grid[y][x] != 0) return false
    var d = false
    var s = false
    var _x = x + 1
    var _y = y + 1
    while (_y <= 7 && _x <= 7) {
        println("檢查 ${_x - 1} ${_y - 1} 現在是 ${grid[_y][_x]}")
        val point = grid[_y][_x]
        if (point == next) {
            s = true
            break
        } else if (point == 0) break
        else {
            d = true
        }
        _y++
        _x++
    }
    var newX = x + 1
    var newY = y + 1
    if (d && s) while (newY <= _y || newX <= _x) {
        grid[newY][newX] = next
        allCheck(
            leftCheck(grid, newX, newY, next),
            rightCheck(grid, newX, newY, next),
            upCheck(grid, newX, newY, next),
            downCheck(grid, newX, newY, next),
            leftupCheck(grid, newX, newY, next),
            rightupCheck(grid, newX, newY, next),
            leftdownCheck(grid, newX, newY, next),
            rightdownCheck(grid, newX, newY, next),
            grid,
            newX,
            newY
        )
        newY++
        newX++
    }
    return d && s
}


fun allCheck(
    l: Boolean, r: Boolean, u: Boolean, d: Boolean, lu: Boolean, ru: Boolean, ld: Boolean, rd: Boolean,
    grid: Array<IntArray>, x: Int, y: Int
): Boolean {
    println("${x + 1} ${y + 1} 現在是${grid[y][x]}")
    while (l || r || u || d || lu || ru || ld || rd) {
        println("可以落子")
        println("l:$l,r:$r,u:$u,d:$d,lu:$lu,ru:$ru,ld:$ld,rd:$rd")
        println("現在是${grid[y][x]} 要改成$next")
        return true
    }
    println("無法落子")
    println("l:$l,r:$r,u:$u,d:$d,lu:$lu,ru:$ru,ld:$ld,rd:$rd")
    return false
}

// 如果gameOvercheck = true 就結束
fun gameOvercheck(grid: Array<IntArray>): Boolean {
    for (list in grid)
        for (point in list)
            if (point == 0) return true
    return false
}