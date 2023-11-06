/*
 sonarHCSR04Support23
 A CodedQactor that auto-starts.
 Launches a process p that activates sonar.py.
 Reads data from the InputStream of p and, for each value,
 emits the event   sonar : distance( V ).
 */
import alice.tuprologx.pj.model.Bool
import java.io.BufferedReader
import java.io.InputStreamReader
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext
import kotlinx.coroutines.*
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils
import java.lang.Thread.sleep
import java.net.ServerSocket
import java.net.Socket


class sonarHCSR04Support23 ( name : String, scope: CoroutineScope, discardMessages: Boolean ) : ActorBasic( name ) {
	var hostAddress = "192.168.1.141"
	lateinit var socket: Socket
	lateinit var serverSocket: ServerSocket
	lateinit var reader : BufferedReader
	//var coapSupport = javacode.CoapSupport("coap://localhost:8028","ctxsonarresource/sonarresource")
	init{
		//autostart
		serverSocket = ServerSocket(6527)
		socket = serverSocket.accept()
		runBlocking{  autoMsg("sonarstart","do") }
	}

	override suspend fun actorBody(msg : IApplMessage){
		//println("$tt $name | received  $msg "  )  //RICEVE GLI EVENTI!!!
		if( msg.msgId() == "sonarstart"){

			reader = BufferedReader(  InputStreamReader(socket.getInputStream() ))
			println("Do Read")
			doRead(   )
		}
	}

	suspend fun doRead(   ){
		var counter = 0
		//GlobalScope.launch{    //to allow message handling

		//QakContext.scope22.launch{
		while( true ){
			var data = reader.readLine()
			println(data)
			if( data != null ){
				try{
					forward( "sonardata","sonardata($data)", "sonarhandler" )        //not propagated to remote actors
					CommUtils.outyellow("sonarHCSR04Support23 doRead emits ${counter++}: sonardata(${data})"   )

				}catch(e: Exception){
					CommUtils.outred("sonarHCSR04Support23 doRead ERROR: $e "   )
				}
			}
			//delay( 250 )     //Avoid too fast generation
		}
		//}
	}
    /*override suspend fun actorBody(msg : IApplMessage){
 		//println("$tt $name | received  $msg "  )  //RICEVE GLI EVENTI!!!
		if( msg.msgId() == "sonarstart"){
			//println("sonarHCSR04Support23 STARTING") //AVOID SINCE pipe ...
			println("sonarHCSR04Support23 sono attivo")
			try{
				//val p  = Runtime.getRuntime().exec("sudo ./SonarAlone")
				val pythonEnrico = "C://Users//zacen//AppData//Local//Programs//Python//Python311//python "
				val eseguibileEnrico = "C://Users//zacen//OneDrive//Desktop//ISSTemaFinale//it.unibo.coldstorageservice.Sprint3//coldstorageservice//resources//SonarReceiver.py "

				val testLucaPython =  "/Library/Frameworks/Python.framework/Versions/3.8/bin/python3 "
				val testLucaSonarSimulator = "//Users//lucadominici//Desktop//IngegneriaSoftware//ProgettoFinale//ISSTemaFinale//it.unibo.coldstorageservice.Sprint3//coldstorageservice//resources//SonarSimulator.py"
				val p  = Runtime.getRuntime().exec(pythonEnrico + eseguibileEnrico )
				//val p  = Runtime.getRuntime().exec( testLucaPython + testLucaSonarSimulator )
				sleep(5000)
				reader = BufferedReader(  InputStreamReader(p.getInputStream() ))
				println("Do Read")
				doRead(   )
			}catch( e : Exception){
				println("WARNING: $name does not find low-level code")
			}
 		}
     }*/

		
	/*suspend fun doRead(   ){
 		var counter = 0
		var last = 0
		val limit = 10
		//GlobalScope.launch{	//to allow message handling

		//QakContext.scope22.launch{
		while( true ){
				var data = reader.readLine()

				if( data != null ){
					//println("SonarKt: " + data)
					try{
						val vd = data.toFloat()
						val v  = vd.toInt()
						//CommUtils.outyellow("$name with python: data = $data"   )
						if( v <= 300 ){	//A first filter ...
							//last rappreseta l'ultimo valore letto dal sonar fisico
							//v rappresenta il valore attuale letto dal sonar fisico
							//limit rappresenta DLIMT
							if((last <= limit && v > limit) || (last > limit && v <= limit)){
								//var res = if(v > limit) "HIGH" else "LOW"
								val m1 = "distance(${if(v > limit) "HIGH" else "LOW"})"
								//println("Sonarkt " + m1)
								//UTILIZZO con dataCleaner
								//val event = MsgUtil.buildEvent( "sonarHCSR04Support","sonardistance",m1)
								val event = MsgUtil.buildEvent( "sonarHCSR04Support","sonardata",m1)
								//emit( event )  //should be propagated also to the remote resource
								emitLocalStreamEvent( event )		//not propagated to remote actors
								CommUtils.outyellow("sonarHCSR04Support23 doRead emits ${counter++}: $event "   )
							}
							last = v
						}
					}catch(e: Exception){
						CommUtils.outred("sonarHCSR04Support23 doRead ERROR: $e "   )
					}
				}
				//delay( 250 ) 	//Avoid too fast generation
 		}
		//}
	}*/
}