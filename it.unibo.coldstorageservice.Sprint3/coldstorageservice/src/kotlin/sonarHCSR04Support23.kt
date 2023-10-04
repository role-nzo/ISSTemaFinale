/*
 sonarHCSR04Support23
 A CodedQactor that auto-starts.
 Launches a process p that activates sonar.py.
 Reads data from the InputStream of p and, for each value,
 emits the event   sonar : distance( V ).
 */
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic		 
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.runBlocking
import unibo.basicomm23.interfaces.IApplMessage
import unibo.basicomm23.utils.CommUtils
import java.lang.Thread.sleep


class sonarHCSR04Support23 ( name : String ) : ActorBasic( name ) {
	lateinit var reader : BufferedReader
	//var coapSupport = javacode.CoapSupport("coap://localhost:8028","ctxsonarresource/sonarresource")
	init{
		//autostart
		runBlocking{  autoMsg("sonarstart","do") }
	}
    override suspend fun actorBody(msg : IApplMessage){
 		//println("$tt $name | received  $msg "  )  //RICEVE GLI EVENTI!!!
		if( msg.msgId() == "sonarstart"){
			//println("sonarHCSR04Support23 STARTING") //AVOID SINCE pipe ...
			println("sonarHCSR04Support23 sono attivo")
			try{
				//val p  = Runtime.getRuntime().exec("sudo ./SonarAlone")
				val p  = Runtime.getRuntime().exec("C://Users//zacen//AppData//Local//Programs//Python//Python311//python C://Users//zacen//OneDrive//Desktop//ISSTemaFinale//it.unibo.coldstorageservice.Sprint3//coldstorageservice//resources//SonarReceiver.py ")
				sleep(5000)
				reader = BufferedReader(  InputStreamReader(p.getInputStream() ))
				println("Do Read")
				doRead(   )
			}catch( e : Exception){
				println("WARNING: $name does not find low-level code")
			}
 		}
     }
		
	suspend fun doRead(   ){
 		var counter = 0
		//GlobalScope.launch{	//to allow message handling
		scope.launch{
		while( true ){
				var data = reader.readLine()

				if( data != null ){
					println(data)
					try{
						val vd = data.toFloat()
						val v  = vd.toInt()
						//CommUtils.outyellow("$name with python: data = $data"   )
						if( v <= 300 ){	//A first filter ...
							val m1 = "distance( ${v} )"
							val event = MsgUtil.buildEvent( "sonarHCSR04Support","sonardistance",m1)
							//emit( event )  //should be propagated also to the remote resource
							emitLocalStreamEvent( event )		//not propagated to remote actors
							CommUtils.outyellow("sonarHCSR04Support23 doRead emits ${counter++}: $event "   )
						}
					}catch(e: Exception){
						CommUtils.outred("sonarHCSR04Support23 doRead ERROR: $e "   )
					}
				}
				//delay( 250 ) 	//Avoid too fast generation
 		}
		}
	}
}