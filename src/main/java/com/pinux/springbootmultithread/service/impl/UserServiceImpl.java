package com.pinux.springbootmultithread.service.impl;

import com.pinux.springbootmultithread.enums.UserSexEnum;
import com.pinux.springbootmultithread.mapper.UserMapper;
import com.pinux.springbootmultithread.model.User;
import com.pinux.springbootmultithread.service.UserService;
import com.pinux.springbootmultithread.thread.UserThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author pinux
 * @Date 19-3-31 上午2:05
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);



    private CountDownLatch threadsSignal;
    //每个线程处理的数据量
    private static final int count=1000;
    //定义线程池数量为8,每个线程处理1000条数据
    private static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    @Override
    public String multiThread() {
        List<User> userList = new ArrayList<>();
        for (int i=0;i<1000000;i++){
            User user = new User();
            user.setId(UUID.randomUUID().toString().replace("-",""));
            user.setNickName(String.valueOf(i));
            user.setPassWord(String.valueOf(i));
            user.setUserName(String.valueOf(i));
            user.setUserSex(UserSexEnum.MAN);
            userList.add(user);
        }
        long start = System.currentTimeMillis();
        long end;
        logger.info("线程开始");
        if (userList.size()<count){
            threadsSignal=new CountDownLatch(1);
            pool.submit(new UserThread(threadsSignal,userList));
        }else {
            List<List<User>> lists= createList(userList,count);
            threadsSignal=new CountDownLatch(lists.size());
            for (List<User> users:lists){
                pool.submit(new UserThread(threadsSignal,users));
            }
        }

        try {
            threadsSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        end = System.currentTimeMillis();
        System.out.println("time:"+(end-start)+"ms");
        return "result";
    }

    /**
     * 数据拆分
     * @param targe
     * @param size
     * @return
     */
    public static List<List<User>>  createList(List<User> targe,int size) {
        List<List<User>> listArr = new ArrayList<List<User>>();
        //获取被拆分的数组个数
        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
        for(int i=0;i<arrSize;i++) {
            List<User>  sub = new ArrayList<User>();
            //把指定索引数据放入到list中
            for(int j=i*size;j<=size*(i+1)-1;j++) {
                if(j<=targe.size()-1) {
                    sub.add(targe.get(j));
                }
            }
            listArr.add(sub);
        }
        return listArr;
    }
}