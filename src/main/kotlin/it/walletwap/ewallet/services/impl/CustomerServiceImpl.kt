package it.walletwap.ewallet.services.impl

import it.walletwap.ewallet.domain.Customer
import it.walletwap.ewallet.repositories.CustomerRepository
import it.walletwap.ewallet.services.CustomerService
import it.walletwap.ewallet.dto.CustomerDTO
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CustomerServiceImpl(var repository: CustomerRepository): CustomerService {
    override fun getCustomerById(customerId: Long): CustomerDTO? {
        val customer = repository.findById(customerId)
        return if (customer.isPresent) customer.get().toDto()
        else null
    }

    override fun saveCustomer(customerDTO: CustomerDTO): CustomerDTO? {
        val customer = Customer(name = customerDTO.name, surname = customerDTO.surname, deliveryAddress = customerDTO.deliveryAddress, email = customerDTO.email)
        return repository.save(customer).toDto()
    }

}


