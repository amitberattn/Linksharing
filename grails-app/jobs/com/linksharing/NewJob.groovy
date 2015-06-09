package com.linksharing



class NewJob {
    static triggers = {
        simple name: 'newSimpleTrigger', startDelay: 6000, repeatInterval: 1000
    }

    def execute() {
       // println("New job")
    }
}
