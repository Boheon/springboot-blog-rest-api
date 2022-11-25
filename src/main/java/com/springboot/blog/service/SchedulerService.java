package com.springboot.blog.service;

public interface SchedulerService {
    public void startScheduler();//스케쥴러시작

    public void changeCronSet(String cron); // 크론변경

    public void stopScheduler();// 스케줄러 죽이기

}
