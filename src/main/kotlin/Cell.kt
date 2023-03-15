import java.lang.Exception

open class Cell(var opened: Boolean=false, var type: Int=0 ) {



    fun openCell(){
        if(type==-1){
            println("You've lost, try again!")
            throw Exception("Game over.")
        }else{
            opened=true
        }

    }







}