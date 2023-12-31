/* Generated by AN DISI Unibo */ 
package it.unibo.led23

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Led23 ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		var Msg = "" 
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("led23 | start  ")
						CoapObserverSupport(myself, "localhost","8022","ctxcoldstorageservice","transporttrolley")
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
					 transition(edgeName="t00",targetState="doObserve",cond=whenDispatch("coapUpdate"))
				}	 
				state("doObserve") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						Msg = currentMsg.msgContent().split(", ")[1] //.split("(")[1].split(")")[0]
								 println("-" + Msg.trim() + "-")
								 if(Msg.trim() != ")"){
								 	Msg = Msg.split("(")[1].split(")")[0]
								  
								 println(Msg)
								 println(currentMsg.msgContent())
								 if(Msg == "stopped"){
						emit("ledstatuschange", "ledstatuschange(stopped)" ) 
						
								}		
								 if(Msg == "moving"){
						emit("ledstatuschange", "ledstatuschange(moving)" ) 
						
								}else { 
						emit("ledstatuschange", "ledstatuschange(home)" ) 
						
								}} 
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
