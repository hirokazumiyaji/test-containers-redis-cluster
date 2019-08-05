package com.github.hirokazumiyaji.testcontainers

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.time.Instant
import kotlin.random.Random

class RedisContainer : GenericContainer<RedisContainer> {
    private val startPort = Random(Instant.now().toEpochMilli()).nextInt(6379, 56379)

    val ports: List<Int>
        get() = (startPort..startPort + 5).toList()

    constructor() : super("quay.io/hirokazumiyaji/redis-cluster:5.0.5") {
        portBindings = ports.map { "$it:$it" }
        withExposedPorts(*ports.toTypedArray())
        withEnv("REDIS_CLUSTER_PORTS", ports.joinToString(" "))
        waitingFor(Wait.forLogMessage(".*Redis cluster is already accepted connections.*", 1))
        withCommand("yes", "Y")
        start()
    }
}