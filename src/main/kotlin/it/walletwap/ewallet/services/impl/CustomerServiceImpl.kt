package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.services.CustomerService
import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.CustomerDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerServiceImpl : CustomerService, Extensions() {
    @Autowired
    lateinit var repository: CustomerRepository
    override fun getCustomerById(customerId: Long): Optional<CustomerDto>? {
        val customer = repository.findById(customerId)
        return if (customer.isPresent) customer.get().toDto().toOptional() else null
    }


    override fun saveCustomer(customerDto: CustomerDto?): Boolean {
        if (customerDto != null) {
            val customerFound = repository.findByEmail(customerDto.email)
            if (customerFound != null) {
                customerFound.name = customerDto.name
                customerFound.wallet = mutableSetOf(Wallet())
                customerFound.deliveryAddress = customerDto.deliveryAddress
                customerFound.email = customerDto.email
                customerFound.surname = customerDto.surname
            }
            return true
        }
        return false
    }


    override val allCustomers: List<Any?>?
        get() = TODO("Not yet implemented")

    override fun deleteCustomer(customerId: Int?): Boolean? {
        TODO("Not yet implemented")
    }

}


