package tech.crom.model.repository

import tech.crom.model.bumper.CromVersionBumper

data class CromRepoDetails(val cromRepo: CromRepo,
                           val cromVersionBumper: CromVersionBumper,
                           val public: Boolean,
                           val checkoutUrl: String?,
                           val description: String?)
