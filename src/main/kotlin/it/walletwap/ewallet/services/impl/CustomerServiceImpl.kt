package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.services.CustomerService
import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.dto.CustomerDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class CustomerServiceImpl : CustomerService, Extensions() {
    @Autowired
    lateinit var repository: CustomerRepository
    override fun getCustomerById(customerId: Long): Optional<CustomerDTO>? {
        val customer = repository.findById(customerId)
        return if (customer.isPresent) customer.get().toDto().toOptional() else null
    }


    override fun saveCustomer(customerDTO: CustomerDTO?): Boolean {
        if (customerDTO != null) {
            val customerFound = repository.findByEmail(customerDTO.email)
            if (customerFound != null) {
                customerFound.name = customerDTO.name
               // customerFound.wallet = mutableSetOf(Wallet(null, BigDecimal.ZERO,customerFound))
                customerFound.deliveryAddress = customerDTO.deliveryAddress
                customerFound.email = customerDTO.email
                customerFound.surname = customerDTO.surname
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


