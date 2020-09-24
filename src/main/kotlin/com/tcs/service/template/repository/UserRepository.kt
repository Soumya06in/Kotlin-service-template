package com.tcs.service.template.repository

import com.microsoft.azure.spring.data.cosmosdb.repository.ReactiveCosmosRepository
import com.tcs.service.template.model.User
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface UserRepository : ReactiveCosmosRepository<User?, String?> {
    fun findByFirstName(firstName: String?): Flux<User?>?
}