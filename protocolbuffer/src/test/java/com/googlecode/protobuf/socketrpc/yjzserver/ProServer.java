package com.googlecode.protobuf.socketrpc.yjzserver;

import java.util.concurrent.Executors;

import com.googlecode.protobuf.socketrpc.RpcServer;
import com.googlecode.protobuf.socketrpc.ServerRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;

public class ProServer {

	public static void main(String[] args) {
		int threadPoolSize=5;
		int port =8067;
		// Start server
		ServerRpcConnectionFactory rpcConnectionFactory = SocketRpcConnectionFactories
		    .createServerRpcConnectionFactory(port);
		RpcServer server = new RpcServer(rpcConnectionFactory, 
		    Executors.newFixedThreadPool(threadPoolSize), true);
		server.registerService(new MyServiceImpl()); // For non-blocking impl
		server.registerBlockingService(MyService
		    .newReflectiveBlockingService(new MyBlockingInterfaceImpl())); // For blocking impl
		server.run();

	}

}
