import alice.tuprolog.Struct
import alice.tuprolog.Term
import java.io.BufferedReader
import java.io.InputStreamReader
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.*
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils
import java.io.BufferedWriter
import java.io.DataOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.lang.Thread.sleep
import java.net.Socket

class ledSupport23 (name : String, scope: CoroutineScope, discardMessages: Boolean ) : ActorBasic( name ){
    lateinit var writer : BufferedWriter
    val socket : Socket
//@kotlinx.coroutines.ObsoleteCoroutinesApi

    init{
        //autostart
        //runBlocking{  autoMsg("ledstart","do") }
        socket = Socket("192.168.1.35", 6525)
        println("ledSupport23 sono attivo")
        //subscribeToLocalActor("ledobserver")
    }

    override suspend fun actorBody(msg : IApplMessage){
        //println("$tt $name | received  $msg "  )  //RICEVE GLI EVENTI!!!
        if( msg.msgId() == "ledstatuschange"){
            //println("sonarHCSR04Support23 STARTING") //AVOID SINCE pipe ...
            println(msg.msgContent())
            var data  = (Term.createTerm( msg.msgContent() ) as Struct).getArg(0).toString()
            println(data)
            try{
                socket.getOutputStream().write(data.toByteArray(Charsets.UTF_8))

            }catch( e : Exception){
                println("WARNING: $name does not find low-level code")
            }
        }
    }
}