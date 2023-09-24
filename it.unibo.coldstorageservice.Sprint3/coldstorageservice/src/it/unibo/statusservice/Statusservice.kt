/* Generated by AN DISI Unibo */ 
package it.unibo.statusservice

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Statusservice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				var Map = ""
				var PosX = 0
				var PosY = 0
				var RobotFree = "libero"
				var CurrentWeightReal = 0 
				var RejectedTickets = 0  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						
									CoapObserverSupport(myself, "localhost","8020","ctxbasicrobot","robotposendosimbiotico")	
									
						CoapObserverSupport(myself, "localhost","8022","ctxcoldstorageservice","coldstorageservice")
						forward("goMoveToHome", "goMoveToHome(0)" ,"transporttrolley" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="observing", cond=doswitch() )
				}	 
				state("observing") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t015",targetState="doObserve",cond=whenDispatch("coapUpdate"))
					transition(edgeName="t016",targetState="sendMap",cond=whenDispatch("sendmap"))
				}	 
				state("sendMap") { //this:State
					action { //it:State
						CommUtils.outblack("$Map")
						updateResourceRep( "status($Map, $PosX, $PosY, $RobotFree, $CurrentWeightReal, $RejectedTickets)"  
						)
						CommUtils.outblack("$Map")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="observing", cond=doswitch() )
				}	 
				state("doObserve") { //this:State
					action { //it:State
						CommUtils.outblack("print doObserve")
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						
									val msg = currentMsg.msgContent().drop(11).dropLast(1)
									val resource = msg.split(", ")[0]
									val value = msg.dropWhile { it != ',' }.drop(2)
												 
									if( resource == "coldstorageservice" ) {
										if( value.startsWith("robotfree") ) {
											RobotFree = value.split("(")[1].dropLast(1)
										} else if( value.startsWith("ticketrejected") ) {
											println("Status " + RejectedTickets)
											RejectedTickets++
										} else if( value.startsWith("weightUpdate") ) {
											CurrentWeightReal = value.split("(")[1].dropLast(1).toInt()
										}
									} else if( resource == "robotposendosimbiotico" ) {
										
										if( value.startsWith("|") ) {
											Map = value.substring(0, value.lastIndexOf("\n"))
											var temp = value.substring(value.lastIndexOf("\n")).split(")")[0].split("(")[1]
											PosX = temp.split(",")[0].toInt()
											PosY = temp.split(",")[1].toInt()
										}
									}
									
									println(resource)
									println(value) 
						updateResourceRep( "status($PosX, $PosY, $RobotFree, $CurrentWeightReal, $RejectedTickets)"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="observing", cond=doswitch() )
				}	 
			}
		}
}