package com.github.hirokazumiyaji.testcontainers

import io.lettuce.core.RedisURI
import io.lettuce.core.cluster.RedisClusterClient
import io.lettuce.core.cluster.api.sync.RedisClusterCommands
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.UUID

@ExtendWith(SpringExtension::class)
@Testcontainers
class RedisTest {

    companion object {
        @Container
        val redis = RedisContainer()
    }

    private var commands: RedisClusterCommands<String, String>? = null

    @BeforeEach
    fun setUp() {
        val uris = redis.ports.map {
            RedisURI.create(redis.containerIpAddress, redis.getMappedPort(it))
        }
        val client = RedisClusterClient.create(uris)
        val connection = client.connect()
        commands = connection.sync()
    }

    @Test
    fun ping() {
        val cmd = commands ?: return
        val expected = "PONG"
        Assertions.assertEquals(expected, cmd.ping())
    }

    @Test
    fun setAndGet() {
        val cmd = commands ?: return
        (1..100).forEach { _ ->
            val key = UUID.randomUUID().toString()
            val expected = UUID.randomUUID().toString()
            cmd.set(key, expected)
            val actual = cmd.get(key)
            Assertions.assertEquals(expected, actual)
        }
    }
}