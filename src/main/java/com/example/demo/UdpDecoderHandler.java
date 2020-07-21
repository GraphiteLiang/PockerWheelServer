package com.example.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.slf4j.*;
import org.springframework.stereotype.Service;

@Service
public class UdpDecoderHandler extends MessageToMessageDecoder<DatagramPacket>{
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(UdpDecoderHandler.class);
	@Override
	protected void decode(ChannelHandlerContext ctx,
			DatagramPacket msg, List<Object> out) throws Exception {
		 ByteBuf byteBuf = msg.content();
	     byte[] data = new byte[byteBuf.readableBytes()];
	     byteBuf.readBytes(data);
	     String msgText = new String(data);
	     LOGGER.info("{}收到消息{}:" + msgText);
	     out.add(msgText); //将数据传入下一个handler
	}
	
	
}
