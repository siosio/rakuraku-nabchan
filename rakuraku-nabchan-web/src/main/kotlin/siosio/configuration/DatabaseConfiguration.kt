package siosio.configuration

import com.zaxxer.hikari.*
import nablarch.core.db.dialect.*
import nablarch.core.db.dialect.Dialect
import nablarch.core.db.dialect.H2Dialect
import nablarch.core.db.dialect.OracleDialect
import nablarch.core.exception.*
import nablarch.core.repository.*
import org.seasar.doma.jdbc.dialect.*
import javax.sql.*

/**
 * データベースに関する設定
 */
class DatabaseConfiguration {

    fun configure() {
        SystemRepository.load({
            mapOf<String, Any>("dataSource" to createDataSource()) + getDialect()
        })
    }
    
    private fun createDataSource() : DataSource {
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = SystemRepository.getString("database.url")
        dataSource.username = SystemRepository.getString("database.user")
        dataSource.password = SystemRepository.getString("database.password")
        dataSource.maximumPoolSize = SystemRepository.getString("database.max-pool-size")?.toInt() ?: 10
        return dataSource
    }

    private fun getDialect(): Map<String, out Any> {
        val databaseName: String = SystemRepository.getString("database.name")?.toUpperCase() ?: ""

        return try {
            DB.valueOf(databaseName)
        } catch (e: IllegalArgumentException) {
            throw IllegalConfigurationException(
                    "データベース名(key:database.name)が不正です。${DB::class.qualifiedName}にあるデータベース名を指定してください。", e)
        }.let {
            mapOf(
                    "dialect" to it.nablarchDialect,
                    "domaDialect" to it.domaDialect
            )
        }
    }

    private enum class DB(val nablarchDialect: Dialect,
                          val domaDialect: org.seasar.doma.jdbc.dialect.Dialect) {
        H2(H2Dialect(), org.seasar.doma.jdbc.dialect.H2Dialect()),
        ORACLE(OracleDialect(), org.seasar.doma.jdbc.dialect.OracleDialect()),
        DB2(DB2Dialect(), Db2Dialect()),
        SQLSERVER(SqlServerDialect(), MssqlDialect()),
        POSTGRESQL(PostgreSQLDialect(), PostgresDialect())
    }
}