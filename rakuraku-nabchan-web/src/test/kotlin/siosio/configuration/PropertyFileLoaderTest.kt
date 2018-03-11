package siosio.configuration

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*

class PropertyFileLoaderTest {
    

    @BeforeEach
    internal fun setUp() {
        System.clearProperty("key")
        System.clearProperty("system.prop")
    }

    @Test
    internal fun プロパティファイルから情報がロードできること() {
        val actual = PropertyFileLoader.load("classpath:siosio/configuration/PropertyFileLoaderTest.properties")
        
        assertThat(actual)
                .extracting("key", "string")
                .contains("value", "あいうえお")
    }

    @Test
    internal fun システムプロパティの値がロードできること() {
        System.setProperty("system.prop", "system value")

        val actual = PropertyFileLoader.load("classpath:siosio/configuration/PropertyFileLoaderTest.properties")
        
        assertThat(actual)
                .extracting("system.prop")
                .contains("system value")
    }

    @Test
    internal fun システムプロパティの値でプロパティファイルの情報が上書きできると() {
        System.setProperty("key", "上書きできること")

        val actual = PropertyFileLoader.load("classpath:siosio/configuration/PropertyFileLoaderTest.properties")
        
        assertThat(actual)
                .extracting("key")
                .contains("上書きできること")
    }
}