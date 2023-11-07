/* Generated by AN DISI Unibo */

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import it.unibo.kactor.sysUtil.createActor   //Sept2023
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Thread.sleep

class Sonar ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

	lateinit var reader : BufferedReader

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
				return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblue("sonar starts")
						//genTimer( actor, state )

						try{
							//val p  = Runtime.getRuntime().exec("sudo ./SonarAlone")
							/*
							val pythonEnrico = "C://Users//zacen//AppData//Local//Programs//Python//Python311//python "
							val eseguibileEnrico = "C://Users//zacen//OneDrive//Desktop//ISSTemaFinale//it.unibo.coldstorageservice.Sprint3//coldstorageservice//resources//SonarReceiver.py "

							val p  = Runtime.getRuntime().exec(pythonEnrico + eseguibileEnrico )
							//val p  = Runtime.getRuntime().exec( testLucaPython + testLucaSonarSimulator )
*/
							reader = BufferedReader(  InputStreamReader (System.`in`))
							println("Do Read")
						}catch( e : Exception){
							println("WARNING: $name does not find low-level code")
						}


					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="read", cond=doswitch() )
				}
				state("read") { //this:State
					action { //it:State

						System.out.println("Sonar: attendo dati in input")
						var data = reader.readLine()
						println(data)
						if( data != null ){

							if(data.equals("HIGH")){

								forward("resumevent", "resumevent(_)" ,"transporttrolley" )

							} else if(data.equals("LOW")){

								forward("stopevent", "stopevent(_)" ,"transporttrolley" )

							}
						}

						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}
					transition( edgeName="goto",targetState="read", cond=doswitch() )
				}
			}
		}
} 
