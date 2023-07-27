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
		  val planner = unibo.planner23.Planner23Util()
		    val MyName    = name //upcase var
		    //val MapName   = "mapEmpty23"
		    val MapName = "mapCompleteWithObst23ok"
		    val StepTime  = 200 //Era 345
		    var Plan      = ""
		    var TargetX   = ""
		    var TargetY   = ""
		    var Position = "" //Sapere posizione del robot da modificare su doMove (necessario sapere verso che direzione si muove il robot)
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						discardMessages = false
						CommUtils.outblue("transporttrolley starts")
						request("engage", "engage($MyName)" ,"basicrobot" )  
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
						request("moverobot", "moverobot(1,5)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("init") { //this:State
					action { //it:State
						
						           Plan = planner.planForGoal(""+1,""+6).toString()
						           Plan = planner.planCompacted(Plan) //Ottengo la stringa delle mosse da fare in forma compatta 
						request("doplan", "doplan($Plan,worker,$StepTime)" ,"basicrobot" )  
						 planner.initAI() //Per prendere il planner
							    planner.loadRoomMap(MapName) //Chiedo al planner di caricare la mappa
							    planner.showMap() //Chiedo al planner di visualizzare la mappa
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("waitclientrequest") { //this:State
					action { //it:State
						CommUtils.outblack("$name | waiting the client request...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t02",targetState="elabMove",cond=whenDispatch("domove"))
				}	 
				state("elabMove") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("domove(TARGET)"), Term.createTerm("domove(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val dest = payloadArg(0)
										    	//TODO 	
										    	
								CommUtils.outblack("${dest}")
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t03",targetState="planFinish",cond=whenReply("doplandone"))
				}	 
				state("elabGoToIndoor") { //this:State
					action { //it:State
						CommUtils.outblack("moveTheRobot to Indoor")
						CommUtils.outblue("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						
						               Plan = planner.planForGoal(""+1,""+6).toString()
						               Plan = planner.planCompacted(Plan) //Ottengo la stringa delle mosse da fare in forma compatta 
						request("doplan", "doplan($Plan,worker,$StepTime)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("elabClientRequest") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("moverobot(TARGETX,TARGETY)"), Term.createTerm("moverobot(X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 TargetX = payloadArg(0)
									      TargetY = payloadArg(1)
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="planTheRobotmoves", cond=doswitch() )
				}	 
				state("planTheRobotmoves") { //this:State
					action { //it:State
						CommUtils.outblack("moveTheRobot to $TargetX $TargetY")
						CommUtils.outblue("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						
						               Plan = planner.planForGoal(""+TargetX,""+TargetY).toString()
						               Plan = planner.planCompacted(Plan) //Ottengo la stringa delle mosse da fare in forma compatta 
						request("doplan", "doplan($Plan,worker,$StepTime)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t04",targetState="endok",cond=whenReply("doplandone"))
					transition(edgeName="t05",targetState="endko",cond=whenReply("doplanfailed"))
				}	 
				state("endok") { //this:State
					action { //it:State
						 planner.doPathOnMap(Plan)  
						 planner.showCurrentRobotState();  
						answer("moverobot", "moverobotdone", "moverobotdone(ok)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("endko") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("doplanfailed(PLANDONE,PLANTODO)"), Term.createTerm("doplanfailed(ARG)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 val PathTodo = payloadArg(0)  
								CommUtils.outred("pos NOT reached - PathTodo = ${PathTodo} vs. $Plan")
								CommUtils.outblue("${Plan.substring(0, Plan.lastIndexOf(PathTodo))}")
								   val PathDone = Plan.substring(0, Plan.lastIndexOf(PathTodo))
										
								 planner.doPathOnMap(PathDone)  
								 planner.showCurrentRobotState();  
								answer("moverobot", "moverobotfailed", "moverobotfailed($PathDone,$PathTodo)"   )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
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
				state("planFinish") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}
