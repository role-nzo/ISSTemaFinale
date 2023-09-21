/* Generated by AN DISI Unibo */ 
package it.unibo.ticketservice

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Ticketservice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 var ticketList = mutableListOf<Ticket>() 
			   var currentWeightVirtual = 0 
			   var maxWeight = 100
			   var TimeMax = 300
			   var Ticketsrejected = 0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outyellow("$name | wait for request")
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t017",targetState="elabNewTicket",cond=whenRequest("newticket"))
					transition(edgeName="t018",targetState="elabStoreFood",cond=whenRequest("storefood"))
					transition(edgeName="t019",targetState="elabUpdateVirtualWeight",cond=whenDispatch("updatevirtualweight"))
				}	 
				state("elabNewTicket") { //this:State
					action { //it:State
						CommUtils.outyellow("$name | elab new ticket")
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						if( checkMsgContent( Term.createTerm("newticket(FW)"), Term.createTerm("newticket(FW)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
											val currentTime = java.time.Instant.now().epochSecond 
											for( t in ticketList){
												if((currentTime - t.creationTime) > TimeMax){
													currentWeightVirtual -= t.fw
													ticketList.remove(t)
												}
											}
											var fw = payloadArg(0).toInt()
											if(currentWeightVirtual + fw <= maxWeight){
												currentWeightVirtual += fw
												var Id = Ticket.getRandomId() 
												var found = false
												while(found){
												found = false
												
													for (t in ticketList){
														if(t.id==Id){
															Id = Ticket.getRandomId()
															found = true
															break
														}
													}
												}
												
												var ticket = Ticket(Id, java.time.Instant.now().epochSecond, fw)
												
												ticketList.add(ticket)
												
												println(ticket)
												
											
								answer("newticket", "newticketaccepted", "newticketaccepted($Id)"   )  
								
												} else {
								answer("newticket", "newticketrefused", "newticketrefused(Peso)"   )  
								
												}
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("elabUpdateVirtualWeight") { //this:State
					action { //it:State
						CommUtils.outyellow("$name | virtual weight update")
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						if( checkMsgContent( Term.createTerm("updatevirtualweight(FW)"), Term.createTerm("updatevirtualweight(FW)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
													currentWeightVirtual = currentWeightVirtual - payloadArg(0).toInt()
						}
						CommUtils.outyellow("$name | current virtual weight: $currentWeightVirtual")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("elabStoreFood") { //this:State
					action { //it:State
						CommUtils.outyellow("$name | elab ticket request")
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						if( checkMsgContent( Term.createTerm("storefood(TICKET,FW)"), Term.createTerm("storefood(TICKET,FW)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												val currentTime = java.time.Instant.now().epochSecond 
										   	   	val idTicket = payloadArg(0)
										   	   	val foodWeight = payloadArg(1).toInt()
										   	   
										   	   	var ticket : Ticket? = null
										   	   
										   	   	for (t in ticketList) {
													//ciclo for per trovare il biglietto del driver nella lista
										   	   	  	if(t.id == idTicket) {
										   	   	  	
										   	   	  		ticket = t
										   	   	  		break
										   	   	  	}
										   	   	}
										   	   
										   	   	if(ticket==null) { 
										   	   		println("Not found")
								answer("storefood", "storefoodrejected", "storefoodrejected(invalid)"   )  
								
										   	   	} else {   
											   	  	
												   	if(foodWeight<ticket!!.fw){
														currentWeightVirtual -= (ticket!!.fw-foodWeight)
												   	}
												   	   
													ticketList.remove(ticket)
												   	   
													println(currentTime - ticket!!.creationTime)
												   	   
													if((currentTime - ticket!!.creationTime) < TimeMax) {	
												   	   	println("ticket valid")	
								answer("storefood", "storefoodaccepted", "storefoodaccepted(valid)"   )  
								
													} else{
														currentWeightVirtual -= ticket!!.fw
														println("ticketservice " + Ticketsrejected)
														//TODO: "Ticketsrejected" non serve su "coldstorageservice" ma solo su "statusservice"
														Ticketsrejected++
								updateResourceRep( " ticketrejected($Ticketsrejected)"  
								)
								answer("storefood", "storefoodrejected", "storefoodrejected(invalid)"   )  
								
											  		}
										  		}
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
			}
		}
}
