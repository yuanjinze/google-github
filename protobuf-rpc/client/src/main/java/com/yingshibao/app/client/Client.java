package com.yingshibao.app.client;

import java.util.concurrent.atomic.AtomicInteger;

import cc.devfun.pbrpc.MessagePrinter;
import com.google.protobuf.nano.MessageNano;
import com.yingshibao.app.idl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.devfun.pbrpc.Endpoint;
import cc.devfun.pbrpc.nio.NioClientEndpoint;
import cc.devfun.pbrpc.nio.NioClientSession;


public class Client {
    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost", 10000);
        client.doTest();
        client.stop();
    }


    final static int LOOP_COUNT = 1000;
    private NioClientEndpoint endpoint;
    private Logger logger = LoggerFactory.getLogger(getClass());
    String remoteAddr;
    int remotePort;
    AtomicInteger counter = new AtomicInteger(0);

    public Client(String addr, int port) throws Exception {
        this.remoteAddr = addr;
        this.remotePort = port;

        endpoint = new NioClientEndpoint(30);
        endpoint.registerService(new Push(new PushImpl()));
        endpoint.registerService(new UserManager());

    }

    public void doTest() throws Exception {
        endpoint.connect(remoteAddr, remotePort);
        endpoint.start();

        for (int i = 0; i < LOOP_COUNT; ++i) {
            testUnregisteredService();
            testRegisteredService();
        }
    }

    public void testRegisteredService() throws Exception {
        UserManager.Client client = new UserManager.Client(new NioClientSession(endpoint));
        UserInfo userInfo = new UserInfo();
        userInfo.channelName = "360应用商店";
        userInfo.phone = "13810773316";
//        userInfo.phone = null;
        userInfo.examType = 1;
        userInfo.nickName = "Johnn";

        counter.incrementAndGet();
        client.registerNewUser(userInfo, new Endpoint.Callback<RegisterResult>() {
            @Override
            public void onResponse(RegisterResult result) {
                logger.info("ASYNC:注册用户返回:" + MessagePrinter.print(result));
                counter.decrementAndGet();
            }

            @Override
            public void onError(Endpoint.RpcError error) {
                counter.decrementAndGet();
                if (error == Endpoint.RpcError.rpc_canceled) {
                    logger.error("ASYNC:RPC调用被取消");
                } else if (error == Endpoint.RpcError.service_not_exist) {
                    logger.error("ASYNC:对方endpoint不提供getCourseList服务");
                } else if (error == Endpoint.RpcError.service_exception) {
                    logger.error("ASNYC:对方服务处理异常");
                } else if (error == Endpoint.RpcError.illegal_argument) {
                    logger.error("ASNYC:请求参数UserInfo中有null值");
                } else {
                    logger.error("ASNYC:未知错误");
                }
            }
        });

        try {
            RegisterResult result = client.registerNewUser(userInfo);
            logger.info("SYNC:注册用户返回:" + MessagePrinter.print(result));
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public void testUnregisteredService() throws Exception {
        CourseType courseType = new CourseType();
        courseType.num = 10;
        courseType.pageNum = 1;
        courseType.courseType = 1;

        try {
            CourseManager.Client client = new CourseManager.Client(new NioClientSession(endpoint));
            CourseList courseList = client.getCourseList(courseType);
            logger.info("SYNC:注册用户返回:" + MessagePrinter.print(courseList));
        } catch (Exception e) {
            logger.error("SYNC: 获取课程列表出错。", e);
        }

        CourseManager.Client client = new CourseManager.Client(new NioClientSession(endpoint));
        counter.incrementAndGet();
        client.getCourseList(courseType, new Endpoint.Callback() {
            @Override
            public void onResponse(MessageNano response) {
                counter.decrementAndGet();
                CourseList courseList = (CourseList) response;
                logger.error("SYNC:注册用户返回:" + MessagePrinter.print(courseList));
            }

            @Override
            public void onError(Endpoint.RpcError error) {
                counter.decrementAndGet();
                if (error == Endpoint.RpcError.rpc_canceled) {
                    logger.error("ASYNC:RPC调用被取消");
                } else if (error == Endpoint.RpcError.service_not_exist) {
                    logger.error("ASYNC:对方endpoint不提供getCourseList服务");
                } else if (error == Endpoint.RpcError.service_exception) {
                    logger.error("ASNYC:对方服务处理异常");
                } else {
                    logger.error("ASNYC:未知错误");
                }
            }
        });

    }

    public void stop() throws Exception {
        while (counter.get() > 0) {
            Thread.sleep(1000L);
        }

        endpoint.destroy();
    }
}
