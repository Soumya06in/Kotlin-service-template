package com.tcs.service.template.model

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document
import com.microsoft.azure.spring.data.cosmosdb.core.mapping.PartitionKey
import org.springframework.data.annotation.Id

@Document(collection = "mycollection")
class User {
    @Id
    var id: String? = null
    var firstName: String? = null

    @PartitionKey
    var lastName: String? = null
    var address: String? = null

    constructor(id: String?, firstName: String?, lastName: String?, address: String?) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.address = address
    }

    constructor() {}

    override fun toString(): String {
        return String.format("%s %s, %s", firstName, lastName, address)
    }
}