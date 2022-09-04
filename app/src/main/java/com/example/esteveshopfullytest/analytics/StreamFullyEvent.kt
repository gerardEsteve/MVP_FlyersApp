package com.example.esteveshopfullytest.analytics

interface StreamFullyEvent {
    val eventType: String
    val attributes: Map<String, Any>
}