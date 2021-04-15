package it.walletwap.ewallet.repositories

import it.walletwap.ewallet.domain.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<Customer, Long> {
    fun findByEmail(email: String): Customer?
}