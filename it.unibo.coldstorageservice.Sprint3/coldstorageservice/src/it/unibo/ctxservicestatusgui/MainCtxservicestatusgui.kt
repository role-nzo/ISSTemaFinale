/* Generated by AN DISI Unibo */ 
package it.unibo.ctxservicestatusgui
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	//System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
	QakContext.createContexts(
	        "192.168.1.35", this, "coldstorageservice.pl", "sysRules.pl", "ctxservicestatusgui"
	)
}

