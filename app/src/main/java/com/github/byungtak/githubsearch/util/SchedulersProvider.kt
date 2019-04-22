package com.github.byungtak.githubsearch.util

import io.reactivex.Scheduler

internal interface SchedulersProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}