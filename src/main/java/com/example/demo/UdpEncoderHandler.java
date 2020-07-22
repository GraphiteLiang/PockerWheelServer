package com.example.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.solution.ServerToClientData;

import java.net.InetSocketAddress;
import java.util.List;
// 将需要发送给UDP Client进行数据封装的handler
// ctx.writeAndFlush("hello word")会调用UdpEncoderHandler，
// 将数据进行decoder封装成DatagramPacket类型，发送给UDP Client
@Service
public class UdpEncoderHandler extends MessageToMessageEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(UdpEncoderHandler.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, List list) throws Exception {
        byte[] data = o.toString().getBytes();
        ByteBuf buf = ctx.alloc().buffer(data.length);
        buf.writeBytes(data);
        ServerToClientData tmp = (ServerToClientData)o;
        String address = tmp.getAddress()==null?"127.0.0.1":tmp.getAddress();
        InetSocketAddress inetSocketAddress = 
        		new InetSocketAddress(address, 1111);//指定客户端的IP及端口
        list.add(new DatagramPacket(buf, inetSocketAddress));
        LOGGER.info("{}发送消息{}:" + o.toString());
    }
}
