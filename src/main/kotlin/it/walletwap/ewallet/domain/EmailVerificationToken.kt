package it.walletwap.ewallet.domain

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "EVT")
class EmailVerificationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    var id: Long?,
    var expiryDate: Date,

    @Column(nullable = false)
    var token: String,

    @Column(nullable = false)
    var username: String
)