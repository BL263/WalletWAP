package it.walletwap.ewallet.repositories

import it.walletwap.ewallet.domain.Transactions
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface TransactionsRepository: CrudRepository<Transactions, Long> {


}