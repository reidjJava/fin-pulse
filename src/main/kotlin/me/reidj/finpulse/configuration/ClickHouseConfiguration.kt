package me.reidj.finpulse.configuration

import com.clickhouse.jdbc.ClickHouseDataSource
import me.reidj.finpulse.properties.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class ClickHouseConfiguration(
    private val dataSourceProperties: DataSourceProperties
) {

    @Bean
    fun dataSource(): DataSource {
        return ClickHouseDataSource(generateUrl())
    }

    private fun generateUrl(): String {
        return "${dataSourceProperties.url}?user=${dataSourceProperties.username}&password=${dataSourceProperties.password}"
    }
}