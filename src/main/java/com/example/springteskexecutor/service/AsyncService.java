package com.example.springteskexecutor.service;

import com.example.springteskexecutor.MyThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TaskExecutor taskExecutor;

    @Async
    public void executeThread(int count) {
        LOGGER.info("Starting Thread");
        MyThread exampleThread = applicationContext.getBean(MyThread.class);
        exampleThread.setCount(count);
        taskExecutor.execute(exampleThread);
        LOGGER.info("Thread Started");
    }
}
