package com.googlecode.protobuf.socketrpc.yjzclient;

import com.google.protobuf.BlockingRpcChannel;
import com.google.protobuf.RpcController;
import com.googlecode.protobuf.socketrpc.RpcChannels;
import com.googlecode.protobuf.socketrpc.RpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import com.googlecode.protobuf.socketrpc.SocketRpcController;
import com.googlecode.protobuf.socketrpc.TestProtos.TestService.BlockingInterface;

public class ProClient {

	public static void main(String[] args) {
		// Create channel
		RpcConnectionFactory connectionFactory = SocketRpcConnectionFactories.createRpcConnectionFactory(host, port);
		BlockingRpcChannel channel = RpcChannels.newBlockingRpcChannel(connectionFactory);

		// Call service
		BlockingInterface service = MyService.newBlockingStub(channel);
		RpcController controller = new SocketRpcController();
		MyResponse myResponse = service.myMethod(controller, myRequest);

		// Check success
		if (rpcController.failed()) {
			System.err.println(
					String.format("Rpc failed %s : %s", rpcController.errorReason(), rpcController.errorText()));
		}

	}

}
