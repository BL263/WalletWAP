package it.walletwap.ewallet.services.Impl

import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.services.CustomerService
import org.springframework.stereotype.Service

@Service
class CustomerServiceimpl(var repository: CustomerRepository) : CustomerService {

    override fun getCustomerById(customerId: Long): Customer {
       return repository.findById(customerId).get()
    }

    override fun saveCustomer(customer: Customer?): Boolean {
        TODO("Not yet implemented")
    }


    override val allCustomers: List<Any?>?
        get() = TODO("Not yet implemented")

    override fun deleteCustomer(customerId: Int?): Boolean? {
        TODO("Not yet implemented")
    }
}