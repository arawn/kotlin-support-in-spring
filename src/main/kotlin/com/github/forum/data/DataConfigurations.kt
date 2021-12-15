package com.github.forum.data

import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

@SpringBootConfiguration
@EnableJdbcRepositories
class DataConfigurations : AbstractJdbcConfiguration() {

    @Bean
    fun dataSource(environment: Environment): DataSource {
        val type = environment.getRequiredProperty("forum.datasource.type", EmbeddedDatabaseType::class.java)
        val scriptEncoding = environment.getProperty("forum.datasource.scriptEncoding", "utf-8")
        val separator = environment.getProperty("forum.datasource.separator", ";")
        val scripts = environment.getProperty("forum.datasource.scripts", List::class.java)?.map { it.toString() }?.toTypedArray()

        val builder = EmbeddedDatabaseBuilder()
        builder.setType(type)
        builder.setScriptEncoding(scriptEncoding)
        builder.setSeparator(separator)
        builder.addScripts(*scripts ?: emptyArray())
        return builder.build()
    }

    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Bean
    fun springJdbcPostQueryRepository(jdbcOperations: NamedParameterJdbcOperations): SpringJdbcPostQueryRepository {
        return SpringJdbcPostQueryRepository(jdbcOperations)
    }
}
