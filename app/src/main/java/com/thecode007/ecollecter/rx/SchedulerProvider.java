
package com.thecode007.ecollecter.rx;

import io.reactivex.Scheduler;



public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
