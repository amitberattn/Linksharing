package com.linksharing



class MyJob {
    static triggers = {
        simple name: 'mySimpleTrigger', startDelay: 60000, repeatInterval: 1000
    }

    def execute() {
       // println("My job")
    }
}
