package com.example.capstone;

import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}
