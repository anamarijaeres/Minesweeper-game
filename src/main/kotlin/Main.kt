import java.lang.Exception
import java.lang.NumberFormatException
import java.util.*

fun main() {
    println("Input the dimensions for the board [3-10]:")
    var dim: Int?
    val sc= Scanner(System.`in`)
    while(true){
        var dimStr:String?=sc.next()
        try{
            dim=dimStr?.toInt()
            if(dim!!>10 || dim!!<3){
                throw Exception("Dimension is invalid.")
            }
            break
        }catch (e: NumberFormatException){
            println("Dimension should be should be an integer.")
        }catch (e:Exception){
            println(e.localizedMessage)
        }
    }

    //init board
    var f=Field(dimension=dim!!)
    //input mines
    f.inputMines()
    while(true){
        try {
            f.makeATurn()
            if(f.isEnd()){

                println("Solution:")
                f.printSolution()
                println("You won!")
                break
            }
        }catch (e: Exception){
            println("Solution:")
            f.printSolution()
            println(e.localizedMessage)
            break
        }
    }


}