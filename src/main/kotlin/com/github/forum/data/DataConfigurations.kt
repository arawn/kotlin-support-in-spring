package com.github.forum.data

import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.core.env.getProperty
import org.springframework.core.env.getRequiredProperty
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
        val type = environment.getRequiredProperty<EmbeddedDatabaseType>("forum.datasource.type")
        val scriptEncoding = environment.get("forum.datasource.scriptEncoding") ?: "utf-8"
        val separator = environment.get("forum.datasource.separator") ?: ";"
        val scripts = environment.getProperty<Array<String>>("forum.datasource.scripts")

        return EmbeddedDatabaseBuilder().apply {
            setType(type)
            setScriptEncoding(scriptEncoding)
            setSeparator(separator)
            addScripts(*scripts ?: emptyArray())
        }.build()
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
