/* Generated by AN DISI Unibo */ 
package it.unibo.transporttrolley

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Transporttrolley ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblue("transporttrolley starts")
						request("engage", "engage(transporttrolley)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t00",targetState="init",cond=whenReply("engagedone"))
					transition(edgeName="t01",targetState="waitrobotfree",cond=whenReply("engagerefused"))
				}	 
				state("waitrobotfree") { //this:State
					action { //it:State
						CommUtils.outred("transporttrolley engage refused")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("init") { //this:State
					action { //it:State
						CommUtils.outred("transporttrolley init")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitcoldstorageservicerequest", cond=doswitch() )
				}	 
				state("waitcoldstorageservicerequest") { //this:State
					action { //it:State
						CommUtils.outblack("$name | waiting the client request...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t02",targetState="elabMoveIndoor",cond=whenDispatch("goMoveToIndoor"))
					transition(edgeName="t03",targetState="elabMoveColdRoom",cond=whenDispatch("goMoveToColdRoom"))
					transition(edgeName="t04",targetState="elabMoveHome",cond=whenDispatch("goMoveToHome"))
				}	 
				state("elabMoveIndoor") { //this:State
					action { //it:State
						CommUtils.outblue("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						request("moverobot", "moverobot(0,4)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t05",targetState="planFinishIndoor",cond=whenReply("moverobotdone"))
					transition(edgeName="t06",targetState="waitcoldstorageservicerequest",cond=whenReply("moverobotfailed"))
				}	 
				state("elabMoveColdRoom") { //this:State
					action { //it:State
						CommUtils.outblue("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						request("moverobot", "moverobot(4,3)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t07",targetState="planFinishColdRoom",cond=whenReply("moverobotdone"))
					transition(edgeName="t08",targetState="waitcoldstorageservicerequest",cond=whenReply("moverobotfailed"))
				}	 
				state("elabMoveHome") { //this:State
					action { //it:State
						CommUtils.outblue("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						request("moverobot", "moverobot(0,0)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t09",targetState="planFinishHome",cond=whenReply("moverobotdone"))
					transition(edgeName="t010",targetState="waitcoldstorageservicerequest",cond=whenReply("moverobotfailed"))
				}	 
				state("stopped") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("resumed") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("planFinishIndoor") { //this:State
					action { //it:State
						CommUtils.outblack("$name | at Indoor")
						delay(3000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t111",targetState="elabWaitLoad",cond=whenRequest("waitLoad"))
				}	 
				state("planFailedIndoor") { //this:State
					action { //it:State
						CommUtils.outblack("$name | at Indoor")
						delay(3000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t112",targetState="elabWaitLoad",cond=whenRequest("waitLoad"))
				}	 
				state("elabWaitLoad") { //this:State
					action { //it:State
						answer("waitLoad", "waitLoadDone", "waitLoadDone(valid)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="elabMoveColdRoom", cond=doswitch() )
				}	 
				state("planFinishColdRoom") { //this:State
					action { //it:State
						CommUtils.outblack("$name | at ColdRoom")
						delay(3000) 
						forward("deposit", "deposit(0)" ,"coldstorageservice" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitcoldstorageservicerequest", cond=doswitch() )
				}	 
				state("planFinishHome") { //this:State
					action { //it:State
						CommUtils.outblack("$name | at home")
						delay(1000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitcoldstorageservicerequest", cond=doswitch() )
				}	 
			}
		}
}
