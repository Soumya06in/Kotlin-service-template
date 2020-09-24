package com.tcs.service.template

import com.tcs.service.template.model.User
import com.tcs.service.template.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.util.Assert
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@SpringBootApplication
class ServiceKotlinTemplateApplication : CommandLineRunner {

	@Autowired
	private val repository: UserRepository? = null

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<ServiceKotlinTemplateApplication>(*args)
		}
	}

	@Throws(Exception::class)
	override fun run(vararg args: String?) {
		println("Inside run")
		val testUser = User("3", "Soumya", "Basu", "Kolkata, WB")
		println("Saving user: {}" + testUser)

		// Save the User class to Azure CosmosDB database.
		val saveUserMono: Mono<User> = repository!!.save(testUser)
		val firstNameUserFlux: Flux<User?>? = repository.findByFirstName("testFirstName")

		//  Nothing happens until we subscribe to these Monos.
		//  findById will not return the user as user is not present.
		val findByIdMono: Mono<User?> = repository.findById(testUser.id!!)
		val findByIdUser: User? = findByIdMono.block()
		Assert.isNull(findByIdUser, "User must be null")
		val savedUser: User? = saveUserMono.block()
		Assert.state(savedUser != null, "Saved user must not be null")
		Assert.state(savedUser!!.firstName.equals(testUser.firstName), "Saved user first name doesn't match")
		println("Saved user")
		firstNameUserFlux!!.collectList().block()
		val optionalUserResult: Optional<User?> = repository.findById(testUser.id!!).blockOptional()
		Assert.isTrue(optionalUserResult.isPresent(), "Cannot find user.")
		val result: User = optionalUserResult.get()
		Assert.state(result.firstName.equals(testUser.firstName), "query result firstName doesn't match!")
		Assert.state(result.lastName.equals(testUser.lastName), "query result lastName doesn't match!")
		println("Found user by findById : {}" + result)
	}

	@PostConstruct
	fun setup() {
		println("Clear the database")
		repository!!.deleteAll().block()
	}

	@PreDestroy
	fun cleanup() {
		println("Cleaning up users")
		repository!!.deleteAll().block()
	}
}


