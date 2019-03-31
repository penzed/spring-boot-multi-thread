package com.pinux.springbootmultithread.thread;

import com.pinux.springbootmultithread.mapper.UserMapper;
import com.pinux.springbootmultithread.model.User;
import com.pinux.springbootmultithread.util.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @ClassName UserThread
 * @Description TODO
 * @Author pinux
 * @Date 19-3-31 上午2:21
 * @Version 1.0
 */
public class UserThread implements Runnable {
    Logger logger = LoggerFactory.getLogger(UserThread.class);

    CountDownLatch cdl;
    List<User> userList;

    @Autowired
    private UserMapper userMapper;

    public UserThread(CountDownLatch cdl, List<User> userList) {
        this.cdl = cdl;
        this.userList = userList;
    }

    @Override
    public void run() {
        UserMapper userMapper = (UserMapper) SpringUtils.getBean("userMapper");
        userMapper.insertBatch(userList);
        logger.info("插入成功，当前线程是:"+Thread.currentThread().getName());
        cdl.countDown();
    }
}