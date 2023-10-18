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
		
				var GoingTo = " "
				var LastStopTime = 0L
				var Mint = 5000
				var AlarmCondition = false
				var MoveAlarm = " "
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblue("transporttrolley starts")
						request("engage", "engage(transporttrolley,330)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t09",targetState="init",cond=whenReply("engagedone"))
					transition(edgeName="t010",targetState="waitrobotfree",cond=whenReply("engagerefused"))
				}	 
				state("waitrobotfree") { //this:State
					action { //it:State
						CommUtils.outblue("transporttrolley engage refused")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("init") { //this:State
					action { //it:State
						CommUtils.outblue("transporttrolley init")
						 subscribeToLocalActor("sonarhandler") 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitcoldstorageservicerequest", cond=doswitch() )
				}	 
				state("waitcoldstorageservicerequest") { //this:State
					action { //it:State
						CommUtils.outblue("$name | waiting the client request...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t011",targetState="elabMoveIndoor",cond=whenDispatch("goMoveToIndoor"))
					transition(edgeName="t012",targetState="elabMoveColdRoom",cond=whenDispatch("goMoveToColdRoom"))
					transition(edgeName="t013",targetState="elabMoveHome",cond=whenDispatch("goMoveToHome"))
				}	 
				state("elabMoveIndoor") { //this:State
					action { //it:State
						CommUtils.outblue("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						 GoingTo = "INDOOR" 
						request("moverobot", "moverobot(0,4)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitRobot", cond=doswitch() )
				}	 
				state("elabMoveColdRoom") { //this:State
					action { //it:State
						CommUtils.outblue("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						 GoingTo = "COLDROOM" 
						request("moverobot", "moverobot(4,3)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitRobot", cond=doswitch() )
				}	 
				state("elabMoveHome") { //this:State
					action { //it:State
						CommUtils.outblue("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						 GoingTo = "HOME" 
						request("moverobot", "moverobot(0,0)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitRobot", cond=doswitch() )
				}	 
				state("waitRobot") { //this:State
					action { //it:State
						CommUtils.outblack("Wait robot")
						updateResourceRep( "transporttrolleystatus(moving)" 
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t114",targetState="checkSonarData",cond=whenEvent("stopevent"))
					transition(edgeName="t115",targetState="robothit",cond=whenReply("moverobotfailed"))
					transition(edgeName="t116",targetState="planFinishSwitch",cond=whenReply("moverobotdone"))
				}	 
				state("robothit") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						CommUtils.outblack("Robot hit")
						updateResourceRep( "transporttrolleystatus(stopped)" 
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitcoldstorageservicerequest", cond=doswitch() )
				}	 
				state("planFinishSwitch") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="planFinishIndoor", cond=doswitchGuarded({GoingTo == "INDOOR" 
					}) )
					transition( edgeName="goto",targetState="planFinishSecondSwitch", cond=doswitchGuarded({! (GoingTo == "INDOOR" 
					) }) )
				}	 
				state("planFinishSecondSwitch") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="planFinishColdRoom", cond=doswitchGuarded({GoingTo == "COLDROOM" 
					}) )
					transition( edgeName="goto",targetState="planFinishHome", cond=doswitchGuarded({! (GoingTo == "COLDROOM" 
					) }) )
				}	 
				state("checkSonarData") { //this:State
					action { //it:State
						
								   val currentTime = java.time.Instant.now().epochSecond
								   AlarmCondition = (currentTime - LastStopTime)*1000 > Mint
								   if(AlarmCondition){ MoveAlarm = "STOP" 
						emit("alarm", "alarm(stop)" ) 
						CommUtils.outblack("alarm emitted")
						 println(AlarmCondition)}
								   else{
								   		MoveAlarm = "WAIT"
						forward("robotstopfailed", "robotstopfailed(_)" ,"sonarhandler" ) 
						CommUtils.outblack("Stop failed: not enough time from last stop")
						}			
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t317",targetState="stopped",cond=whenReply("moverobotfailed"))
					transition(edgeName="t318",targetState="planFinishSwitch",cond=whenReply("moverobotdone"))
				}	 
				state("stopped") { //this:State
					action { //it:State
						CommUtils.outblack("Sono fermo")
						LastStopTime = java.time.Instant.now().epochSecond 
						forward("robotstop", "robotstop(_)" ,"sonarhandler" ) 
						updateResourceRep( "transporttrolleystatus(stopped)" 
						)
						updateResourceRep( "robotfree(fermo)" 
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t219",targetState="resuming",cond=whenEvent("resumevent"))
				}	 
				state("resuming") { //this:State
					action { //it:State
						CommUtils.outblack("Resuming")
						forward("robotresume", "robotresume(_)" ,"sonarhandler" ) 
						if(GoingTo == "HOME"){ 
						updateResourceRep( "robotfree(libero)" 
						)
						}else{ 
						updateResourceRep( "robotfree(occupato)" 
						)
						} 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="elabMoveIndoor", cond=doswitchGuarded({GoingTo == "INDOOR" 
					}) )
					transition( edgeName="goto",targetState="secondResuming", cond=doswitchGuarded({! (GoingTo == "INDOOR" 
					) }) )
				}	 
				state("secondResuming") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="elabMoveColdRoom", cond=doswitchGuarded({GoingTo == "COLDROOM" 
					}) )
					transition( edgeName="goto",targetState="elabMoveHome", cond=doswitchGuarded({! (GoingTo == "COLDROOM" 
					) }) )
				}	 
				state("planFinishIndoor") { //this:State
					action { //it:State
						CommUtils.outblue("$name | at Indoor")
						delay(3000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t120",targetState="elabWaitLoad",cond=whenRequest("waitLoad"))
				}	 
				state("planFailedIndoor") { //this:State
					action { //it:State
						CommUtils.outblue("$name | at Indoor")
						delay(3000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t121",targetState="elabWaitLoad",cond=whenRequest("waitLoad"))
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
						CommUtils.outblue("$name | at ColdRoom")
						delay(1500) 
						forward("depositdone", "depositdone(0)" ,"coldstorageservice" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		stateTimer = TimerActor("timer_planFinishColdRoom", 
				 	 					  scope, context!!, "local_tout_transporttrolley_planFinishColdRoom", 4000.toLong() )
					}	 	 
					 transition(edgeName="t522",targetState="elabMoveHome",cond=whenTimeout("local_tout_transporttrolley_planFinishColdRoom"))   
					transition(edgeName="t523",targetState="elabMoveIndoor",cond=whenDispatch("goMoveToIndoor"))
				}	 
				state("planFinishHome") { //this:State
					action { //it:State
						CommUtils.outblue("$name | at home")
						updateResourceRep( "transporttrolleystatus(home)" 
						)
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
