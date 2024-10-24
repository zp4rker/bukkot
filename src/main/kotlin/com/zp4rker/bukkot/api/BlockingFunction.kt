package com.zp4rker.bukkot.api

@RequiresOptIn("This function is blocking and should ideally be run in an async task")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
annotation class BlockingFunction
