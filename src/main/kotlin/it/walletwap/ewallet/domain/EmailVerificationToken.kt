package it.walletwap.ewallet.domain

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "EVT")
class EmailVerificationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    var id: Long? = null,

    var expiryDate :Date,
    @Column(name = "token",nullable = false)
    var token:String,
    @Column(name = "username",nullable = false)
    var username: String
)