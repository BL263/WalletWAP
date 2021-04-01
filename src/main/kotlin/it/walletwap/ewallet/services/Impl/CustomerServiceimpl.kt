package it.walletwap.ewallet.services.Impl

import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.services.CustomerService
import it.walletwap.ewallet.Extensions
import it.walletwap.ewallet.domain.Wallet
import it.walletwap.ewallet.dto.CustomerDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerServiceimpl() : CustomerService , Extensions() {
    @Autowired
    lateinit var repository: CustomerRepository
    override fun getCustomerById(customerId: Long): Optional<CustomerDto>? {
        var customer=  repository.findById(customerId)
        return if(!customer.isEmpty) {
            (customer.get().toDto()).toOptional()
        } else{
            null
        }
    }


    override fun saveCustomer(customerDto: CustomerDto?): Boolean {
        if (customerDto != null) {
            val customerfound=repository.findByEmail(customerDto.email)
            if(customerDto==null)
            {
                var customerentity=Customer()
                customerentity.name=customerDto.name
                customerentity.wallet= mutableSetOf(Wallet())
                customerentity.deliveryAddress=customerDto.deliveryAddress
                customerentity.email=customerDto.email
                customerentity.sureName=customerDto.sureName
                repository.save(customerentity)
            }
            else {
                if (customerfound != null) {
                    customerfound.name = customerDto.name
                    customerfound.wallet = mutableSetOf(Wallet())
                    customerfound.deliveryAddress = customerDto.deliveryAddress
                    customerfound.email = customerDto.email
                    customerfound.sureName = customerDto.sureName
                }
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


