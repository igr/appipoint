package domain

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import model.CitiesRepo
import model.DoctorsRepo
import model.TimeslotsRepo
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(DoctorsRepo, CitiesRepo, TimeslotsRepo)
        }
    }

    private fun hikari(): HikariDataSource {
        val db = System.getenv("PH_DATABASE_ADDRESS") ?: "localhost"
        val dbUser = System.getenv("PH_DATABASE_USER") ?: "phuser"
        val dbPass = System.getenv("PH_DATABASE_PASS") ?: "phpass1!"

        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://$db:5432/phdb"
        config.maximumPoolSize = 10
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.username = dbUser
        config.password = dbPass
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbtx(block: suspend () -> T): T =
        newSuspendedTransaction {
            block()
        }

}