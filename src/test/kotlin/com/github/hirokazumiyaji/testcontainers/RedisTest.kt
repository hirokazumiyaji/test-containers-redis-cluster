package com.github.hirokazumiyaji.testcontainers

import com.google.common.flogger.FluentLogger
import io.lettuce.core.RedisURI
import io.lettuce.core.cluster.RedisClusterClient
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@ExtendWith(SpringExtension::class)
@Testcontainers
class RedisTest {

    companion object {
        val log = FluentLogger.forEnclosingClass()!!
        @Container
        val redis = RedisContainer()
    }

    private var commands: RedisAdvancedClusterCommands<String, String>? = null

    @BeforeEach
    fun setUp() {
        val uri = RedisURI.create(redis.containerIpAddress, redis.getMappedPort(6379))
        val client = RedisClusterClient.create(uri)
        val connection = client.connect()
        commands = connection.sync()
    }

    @Test
    fun initialize() {
        val cmd = commands ?: return
        val expected = "PONG"
        Assertions.assertEquals(expected, cmd.ping())
    }
}