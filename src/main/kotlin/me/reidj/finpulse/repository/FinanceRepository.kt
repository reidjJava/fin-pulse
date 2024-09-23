package me.reidj.finpulse.repository

import me.reidj.finpulse.dto.exchangerate.GetCurrencyResponse
import me.reidj.finpulse.dto.source.GetSourceResponse
import me.reidj.finpulse.exception.WebAppException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.sql.*
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.sql.DataSource

open class FinanceRepository(private val dataSource: DataSource) {

    private val logger = LoggerFactory.getLogger(FinanceRepository::class.java)

    companion object {
        private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }

    fun insertCurrency(response: GetCurrencyResponse) {
        val query = "INSERT INTO clickhousedb.currency (currency, price, date, time_last_updated) VALUES (?, ?, ?, ?)"
        val sqlDate = Date.valueOf(LocalDate.parse(response.date, DATE_FORMATTER))
        val timeLastUpdatedTimestamp = Timestamp.from(Instant.ofEpochSecond(response.timeLastUpdated))

        executeBatchInsert(query) { preparedStatement ->
            response.rates.forEach { (currency, rate) ->
                preparedStatement.setString(1, currency)
                preparedStatement.setBigDecimal(2, rate)
                preparedStatement.setDate(3, sqlDate)
                preparedStatement.setTimestamp(4, timeLastUpdatedTimestamp)
                preparedStatement.addBatch()
            }
        }
    }

    fun insertSource(response: GetSourceResponse, tableName: String) {
        val query = "INSERT INTO clickhousedb.$tableName (name, price) VALUES (?, ?)"
        executeBatchInsert(query) { preparedStatement ->
            response.securities.data.forEach {
                val title = it.getOrNull(0) as? String
                val price = it.getOrNull(1)?.toString()?.let { BigDecimal(it) }
                if (title != null && price != null) {
                    preparedStatement.setString(1, title)
                    preparedStatement.setBigDecimal(2, price)
                    preparedStatement.addBatch()
                } else {
                    logger.warn("Skipping invalid entry: $it")
                }
            }
        }
    }

    fun fetchLatestPrices(sources: List<String>): List<Pair<String, BigDecimal>> {
        val prices = mutableListOf<Pair<String, BigDecimal>>()
        for (source in sources) {
            val query = "SELECT name, price FROM clickhousedb.$source"
            prices.addAll(executeQuery(query) { resultSet ->
                val sourcePrices = mutableListOf<Pair<String, BigDecimal>>()
                while (resultSet.next()) {
                    val name = resultSet.getString("name")
                    val price = resultSet.getBigDecimal("price")
                    sourcePrices.add(name to price)
                }
                sourcePrices
            })
        }
        return prices
    }

    private fun executeBatchInsert(query: String, batchAction: (PreparedStatement) -> Unit) {
        executeWithPreparedStatement(query) { preparedStatement ->
            batchAction(preparedStatement)
            preparedStatement.executeBatch()
        }
    }

    private fun <T> executeQuery(query: String, resultAction: (ResultSet) -> T): T {
        return executeWithPreparedStatement(query) { preparedStatement ->
            preparedStatement.executeQuery().use { resultSet ->
                resultAction(resultSet)
            }
        }
    }

    private fun <T> executeWithPreparedStatement(query: String, statementAction: (PreparedStatement) -> T): T {
        return try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(query).use { preparedStatement ->
                    statementAction(preparedStatement)
                }
            }
        } catch (e: SQLException) {
            logger.error("SQL Error: ${e.message}", e)
            throw WebAppException(HttpStatus.INTERNAL_SERVER_ERROR, "Database operation error: ${e.message}")
        }
    }
}
