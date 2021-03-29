package it.walletwap.ewallet.services

import it.walletwap.ewallet.dto.Customer


interface CustomerService {
    /*
	 * Queries the database and returns the CustomerDto corresponding to the customerId passed as parameter.
	 * Throws an exception in case of invalid (negative) customerId
	 * Returns null if no customer is found with the given Id
	 */

    fun getCustomerById(customerId: Long): Customer

    /*
     * Stores the customer passed as parameter in the application database
     */
    fun saveCustomer(customer: Customer?): Boolean

    /*
     * Returns an ArrayList with all the Customers in the database.
     * Returns an empty ArrayList if no customer is registered in the database
     */
    val allCustomers: List<Any?>?

    /*
     * Deletes the customer with the given Id from the database.
     * Throws an exception in case of invalid (negative) customerId
   	 * Returns true if the customer is deleted, false otherwise
     */
    fun deleteCustomer(customerId: Int?): Boolean?


}