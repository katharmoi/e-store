package com.cabify.cabifystore

import androidx.test.espresso.IdlingRegistry
import com.cabify.cabifystore.di.DaggerAppComponent
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class IdlingResourceRule : TestRule {

    var client: OkHttpClient = DaggerAppComponent.builder()
        .application(App.INSTANCE)
        .build()
        .getOkHttpClient()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                val resource = OkHttp3IdlingResource.create("okhttp", client)
                IdlingRegistry.getInstance().register(resource)
                base.evaluate()
                IdlingRegistry.getInstance().unregister(resource)
            }
        }
    }
}