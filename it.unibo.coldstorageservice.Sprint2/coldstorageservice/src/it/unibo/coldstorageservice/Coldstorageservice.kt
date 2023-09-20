/* Generated by AN DISI Unibo */ 
package it.unibo.coldstorageservice

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Coldstorageservice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 var CurrentWeightReal = 0
			   var CurrentTicketFW = 0
			   var Ticketsrejected = 0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outgreen("coldstorageservice starts")
						delegate("newticket", "ticketservice") 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("waitclientrequest") { //this:State
					action { //it:State
						CommUtils.outgreen("$name | waiting the client request...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t019",targetState="elabStoreFood",cond=whenRequest("storefood"))
					transition(edgeName="t020",targetState="elabClearColdRoom",cond=whenRequest("clearColdRoom"))
				}	 
				state("elabStoreFood") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						if( checkMsgContent( Term.createTerm("storefood(TICKET,FW)"), Term.createTerm("storefood(TICKET,FW)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
										   	   	val IDTicket = payloadArg(0)
										   	   	val FoodWeight = payloadArg(1)   	
								request("storefood", "storefood($IDTicket,$FoodWeight)" ,"ticketservice" )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t121",targetState="elabStoreFoodAccepted",cond=whenReply("storefoodaccepted"))
					transition(edgeName="t122",targetState="elabStoreFoodRejected",cond=whenReply("storefoodrejected"))
				}	 
				state("elabStoreFoodAccepted") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						answer("storefood", "storefoodaccepted", "storefoodaccepted(valid)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitLoadDoneRequest", cond=doswitch() )
				}	 
				state("elabStoreFoodRejected") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						 Ticketsrejected++  
						answer("storefood", "storefoodrejected", "storefoodrejected(invalid)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("waitLoadDoneRequest") { //this:State
					action { //it:State
						CommUtils.outgreen("$name | wait load request")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t223",targetState="elabLoadDone",cond=whenRequest("loaddone"))
				}	 
				state("elabLoadDone") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						CommUtils.outgreen("$name | elab load done")
						if( checkMsgContent( Term.createTerm("loaddone(FW)"), Term.createTerm("loaddone(FW)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
													CurrentTicketFW = payloadArg(0).toInt()
								forward("goMoveToIndoor", "goMoveToIndoor(0)" ,"transporttrolley" ) 
								request("waitLoad", "waitLoad(0)" ,"transporttrolley" )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t324",targetState="elabChargeTaken",cond=whenReply("waitLoadDone"))
				}	 
				state("elabChargeTaken") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						CommUtils.outgreen("$name | elab charge taken")
						answer("loaddone", "chargetaken", "chargetaken(0)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="elabWaitDepositDone", cond=doswitch() )
				}	 
				state("elabWaitDepositDone") { //this:State
					action { //it:State
						CommUtils.outgreen("$name | elab wait load done")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t425",targetState="elabDepositDone",cond=whenDispatch("depositdone"))
				}	 
				state("elabDepositDone") { //this:State
					action { //it:State
						CommUtils.outgreen("$name | elab deposit")
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						
									CurrentWeightReal += CurrentTicketFW	
						CommUtils.outgreen("Deposit - Current weight real: $CurrentWeightReal")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("moveRobotHome") { //this:State
					action { //it:State
						forward("goMoveToHome", "goMoveToHome(0)" ,"transporttrolley" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
				state("elabClearColdRoom") { //this:State
					action { //it:State
						CommUtils.outgreen("$name | empty coldRoom")
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						forward("updatevirtualweight", "updatevirtualweight($CurrentWeightReal)" ,"ticketservice" ) 
						
									CurrentWeightReal = 0	
						CommUtils.outgreen("$name | coldRoom cleared - current weight real: $CurrentWeightReal")
						answer("clearColdRoom", "coldRoomCleared", "coldRoomCleared(0)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="waitclientrequest", cond=doswitch() )
				}	 
			}
		}
}
