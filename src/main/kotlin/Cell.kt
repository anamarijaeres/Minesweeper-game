import java.lang.Exception

open class Cell(var opened: Boolean=false, var type: Int=0 ) {



    fun openCell(){
        if(type==-1){
            println("You've stepped on a mine, try again!")
            throw Exception("Game over, you've lost.")
        }else{
            opened=true
        }

    }







}