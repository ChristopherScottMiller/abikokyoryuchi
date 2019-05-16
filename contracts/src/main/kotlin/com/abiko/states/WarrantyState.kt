package com.abiko.states

import com.abiko.contracts.TemplateContract
import com.abiko.contracts.WarrantyContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party

// *********
// * State *
// *********
@BelongsToContract(com.abiko.contracts.WarrantyContract::class)
data class WarrantyState(
        val WarrantyID: String,
        val ManufacturerID: Party,
        val DistributorID: Party,
        override val participants: List<AbstractParty> = listOf(ManufacturerID,DistributorID)) : ContractState
