/* Generated by AN DISI Unibo */ 
package it.unibo.fridgetruck

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Fridgetruck ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outred("fridgetruck starts")
						request("newticket", "newticket(3)" ,"coldstorageservice" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t018",targetState="elabTicketAccepted",cond=whenReply("newticketaccepted"))
				}	 
				state("elabTicketAccepted") { //this:State
					action { //it:State
						CommUtils.outblack("QUI")
						if( checkMsgContent( Term.createTerm("newticketaccepted(TICKET)"), Term.createTerm("newticketaccepted(TICKET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 
													var CurrentTicketId = payloadArg(0)
													println(CurrentTicketId)
								request("ticketrequest", "ticketrequest($CurrentTicketId,3)" ,"coldstorageservice" )  
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}
