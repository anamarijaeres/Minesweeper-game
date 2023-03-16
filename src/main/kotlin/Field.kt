import java.lang.Exception
import java.lang.NumberFormatException
import java.util.*

class Field(var dimension: Int=4){
    private var board=Array(dimension){Array(dimension){ Cell() } }
    private var minesInt:Int? = null


    init {
        println("Initializing the field... with dimension $dimension")
    }

    private fun initializeBoard() {
        for (i in 0 until dimension){
            for(j in 0 until dimension){
                board[i][j]=Cell()
            }
        }


        //generate randomly where the mine will be and set all adjacent cells
        val s: MutableSet<Int> = mutableSetOf()
        while (s.size<minesInt!!){ s.add((0 until dimension*dimension).random())}
        val randomList=s.toList()
        for(item in randomList){
            val row=item/dimension
            var col=item%dimension-1
            //last column
            if(col==-1){
                col=dimension-1
            }


            for (k in -1..1) {
                for (l in -1..1) {
                    // The current cell
                    if (k == 0 && l == 0) {
                        board[row][col].type=-1
                        continue
                    }
                    // Out of the board
                    if (row + k < 0 || col + l < 0 || row + k >= dimension || col + l >= dimension) {
                        continue
                    }else{
                        if(board[row+k][col+l].type==-1){
                            continue
                        }
                        board[row+k][col+l].type+=1
                    }
                }
            }


        }

        printBoard()

    }
    fun printSolution(){
        for (i in 0 until dimension){
            for(j in 0 until dimension){
                if(board[i][j].type==-1){
                    print("* ")
                }else {
                    if(board[i][j].type==0){
                        print("  ")
                    }else {
                        print(board[i][j].type.toString() + " ")
                    }
                }
            }
            println()
        }
    }

    private fun printBoard(){
        //print the board
        for (i in 0 until dimension+2){
            for(j in 0 until dimension+2){
                if(i==0){
                    if(j!=0&& j!=1){
                        print((j-1).toString()+" ")
                    }else{
                        print("  ")
                    }
                }
                else if(j==0){
                    if(i!=1){
                        print((i-1).toString()+" ")
                    } else{
                        print(" ")
                    }
                }
                else if(i==1){
                    print("--")
                }
                else if(j==1){
                    print("| ")
                }
                else if(board[i-2][j-2].opened){
                    if(board[i-2][j-2].type==0){
                        print("  ")
                    }else {
                        print(board[i - 2][j - 2].type.toString() + " ")
                    }
                }else{
                    print("_ ")
                }
            }
            println()
        }
    }

    fun isEnd():Boolean{
        for(i in 0 until dimension){
            for (j in 0 until dimension){
                if(board[i][j].type!=-1) {
                    if (!board[i][j].opened) {
                        //some free cell is closed
                        return false
                    }
                }
            }
        }
        return true
    }

    fun inputMines(){

        println("How many mines do you want on the field?")
        val sc= Scanner(System.`in`)
        while(true){
            val mines:String?=sc.next()
            try{
                minesInt=mines?.toInt()
                if(minesInt!!>=dimension*dimension || minesInt!! <=0){
                    throw Exception("No of mines is invalid.")
                }
                break
            }catch (e: NumberFormatException){
                println("The number of mines should be an integer.")
            }catch (e: Exception){
                println("The number of mines should be less than dim*dim and more than 0.")
            }
        }
        println(minesInt)
        initializeBoard()
    }

    fun makeATurn(){
        println("I want to click on row:")
        val row=readArg()

        println("I want to click on col:")
        val col=readArg()


        board[row][col].openCell()
        if(board[row][col].type==0 ){
            exploreAround(row, col)
        }
        printBoard()
    }

    private fun readArg(): Int {
        var arg: Int?
        val sc= Scanner(System.`in`)
        while(true){
            val moveRow:String?=sc.next()
            try{
                arg=moveRow?.toInt()
                if(arg!!>dimension || arg <=0){
                    throw Exception("Row is invalid.")
                }
                break
            }catch (e: NumberFormatException){
                println("The mine should be should be an integer.")
            }catch (e:Exception){
                println(e.localizedMessage)
            }
        }
        return arg!!-1
    }

    private fun exploreAround(moveRowInt: Int, moveColInt: Int) {
        for (k in -1..1) {
            for (l in -1..1) {
                // The current cell
                if (k == 0 && l == 0) {
                    continue
                }
                // Out of the board
                if (moveRowInt + k < 0 || moveColInt + l < 0 || moveRowInt+ k >= dimension || moveColInt + l >= dimension) {
                    continue
                }

                if(!board[moveRowInt + k][moveColInt+l].opened){
                    board[moveRowInt + k][moveColInt+l].openCell()
                    if(board[moveRowInt + k][moveColInt+l].type==0 ){
                        exploreAround(moveRowInt + k,moveColInt+l)
                    }
                }
            }
        }
    }
}