package com.abiko.flows

import co.paralleluniverse.fibers.Suspendable
import com.abiko.contracts.WarrantyContract
import com.abiko.states.WarrantyState
import net.corda.core.contracts.Command
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********
@InitiatingFlow
@StartableByRPC
class WarrantyIssueFlow(val WarrantyID: String,
                        val ManufacturerID: Party,
                        val DistributorID: Party) : FlowLogic<Unit>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        // Initiator flow logic goes here.
        val notary = serviceHub.networkMapCache.notaryIdentities.first()

        val outputState = WarrantyState(WarrantyID,ManufacturerID, DistributorID)
        val command = Command(WarrantyContract.Commands.Issue(), ourIdentity.owningKey)

        val txBuilder = TransactionBuilder(notary = notary)
                .addOutputState(outputState,WarrantyContract.ID)
                .addCommand(command)

        val signedTx = serviceHub.signInitialTransaction(txBuilder)

        val otherPartySession = initiateFlow(DistributorID)

        subFlow(FinalityFlow(signedTx, otherPartySession))
    }
}

@InitiatedBy(Initiator::class)
class WarrantyIssueFlowResponder(private val otherPartySession: FlowSession) : FlowLogic<Unit>() {
    @Suspendable
    override fun call() {
        // Responder flow logic goes here.
        subFlow(ReceiveFinalityFlow(otherPartySession))
    }
}
