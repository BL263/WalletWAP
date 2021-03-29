package it.walletwap.ewallet.repositories

import it.walletwap.ewallet.dto.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: CrudRepository<Customer, Long> {


}