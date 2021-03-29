package it.walletwap.ewallet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EWalletApplication

fun main(args: Array<String>) {
    runApplication<EWalletApplication>(*args)
}
