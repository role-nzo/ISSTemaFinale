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
import it.unibo.kactor.sysUtil.createActor   //Sept2023
class Transporttrolley ( name: String, scope: CoroutineScope, isconfined: Boolean=false  ) : ActorBasicFsm( name, scope, confined=isconfined ){

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
				var Stopped = false
				var Limit = 10
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
					 transition(edgeName="t00",targetState="init",cond=whenReply("engagedone"))
					transition(edgeName="t01",targetState="waitrobotfree",cond=whenReply("engagerefused"))
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
					 transition(edgeName="t02",targetState="elabMoveIndoor",cond=whenDispatch("goMoveToIndoor"))
					transition(edgeName="t03",targetState="elabMoveColdRoom",cond=whenDispatch("goMoveToColdRoom"))
					transition(edgeName="t04",targetState="elabMoveHome",cond=whenDispatch("goMoveToHome"))
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
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t15",targetState="checkSonarData",cond=whenDispatch("stopevent"))
					transition(edgeName="t16",targetState="robothit",cond=whenReply("moverobotfailed"))
					transition(edgeName="t17",targetState="planFinishSwitch",cond=whenReply("moverobotdone"))
					transition(edgeName="t18",targetState="waitRobot",cond=whenDispatch("resumevent"))
				}	 
				state("robothit") { //this:State
					action { //it:State
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
								   if(AlarmCondition){ 
						emit("alarm", "alarm(stop)" ) 
						CommUtils.outblack("alarm emitted")
						}
								   else{
						CommUtils.outblack("Stop failed: not enough time from last stop")
						}			
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t39",targetState="stopped",cond=whenReply("moverobotfailed"))
					transition(edgeName="t310",targetState="planFinishSwitch",cond=whenReply("moverobotdone"))
				}	 
				state("stopped") { //this:State
					action { //it:State
						CommUtils.outblack("Sono fermo")
						 LastStopTime = java.time.Instant.now().epochSecond  
						updateResourceRep( "transporttrolleystatus(stopped)" 
						)
						updateResourceRep( "robotfree(fermo)" 
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t211",targetState="resuming",cond=whenDispatch("resumevent"))
				}	 
				state("resuming") { //this:State
					action { //it:State
						CommUtils.outblack("Resuming")
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
					 transition(edgeName="t112",targetState="elabWaitLoad",cond=whenRequest("waitLoad"))
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
					 transition(edgeName="t113",targetState="elabWaitLoad",cond=whenRequest("waitLoad"))
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
					 transition(edgeName="t514",targetState="elabMoveHome",cond=whenTimeout("local_tout_transporttrolley_planFinishColdRoom"))   
					transition(edgeName="t515",targetState="elabMoveIndoor",cond=whenDispatch("goMoveToIndoor"))
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
