package com.cabify.domain.error

import java.lang.RuntimeException

open class ApplicationException : RuntimeException{
    constructor():super()
    constructor(msg:String) : super(msg)
}