package siosio.configuration

import com.zaxxer.hikari.*
import nablarch.core.db.dialect.*
import nablarch.core.exception.*
import nablarch.core.repository.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import javax.sql.*

internal class DatabaseConfigurationTest {

    private val sut = DatabaseConfiguration()

    @BeforeEach
    internal fun setUp() {
        SystemRepository.clear()
    }

    @AfterEach
    internal fun tearDown() {
        SystemRepository.clear()
    }

    @Test
    internal fun データベース名に対応するDialectが設定されること() {
        putSystemRepository("database.name", "H2")
        
        sut.configure()
        
        assertThat(SystemRepository.get<Dialect>("dialect"))
                .isInstanceOf(H2Dialect::class.java)
        assertThat(SystemRepository.get<org.seasar.doma.jdbc.dialect.Dialect>("domaDialect"))
                .isInstanceOf(org.seasar.doma.jdbc.dialect.H2Dialect::class.java)
        
    }

    @Test
    internal fun データソースが構築できること() {
        putSystemRepository("database.name", "h2")
        putSystemRepository("database.url", "jdbc:h2:mem:test")
        putSystemRepository("database.user", "sa")
        putSystemRepository("database.password", "password")
        putSystemRepository("database.max-pool-size", "50")
        
        sut.configure()
        
        assertThat(SystemRepository.get<DataSource>("dataSource"))
                .isInstanceOf(HikariDataSource::class.java)
    }

    @Test
    internal fun データベース名が不正な場合例外が送出されること() {
        putSystemRepository("database.name", "ora")
        
        assertThatThrownBy { sut.configure() }
                .isInstanceOf(IllegalConfigurationException::class.java)
                .hasMessageContaining("データベース名が不正です。")
    }
}