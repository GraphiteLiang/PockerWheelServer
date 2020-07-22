package com.example.demo;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.demo.solution.*;
// 上一个decoder handler数据解析完成后
// 将数据传入UdpHandler
// 我们将消息分类，交由不同的service去做相应的处理
@Slf4j
@Service
public class UdpHandler extends ChannelInboundHandlerAdapter{
	private static final Logger log= 
			LoggerFactory.getLogger(UdpHandler.class);
	List<DataManager> dataManager = new ArrayList<DataManager>();
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String preHandlerAfferentMsg = (String)msg; //得到消息后，可根据消息类型分发给不同的service去处理数据
        log.info("{}preHandler传入的数据{}"+preHandlerAfferentMsg);
		ClientToSeverData ctsd = DataManager.jsonToData(preHandlerAfferentMsg);
        switch(ctsd.dataType) {
	        case create:
	        	dataManager.add(new DataManager());
	        	dataManager.get(0).addPlayer(ctsd.address);
	        	ServerToClientData d = dataManager.get(0).spawnData(Type.other, 0);
    			ctx.writeAndFlush(d);
	        	//返回tableid
	        	break;
	        case join:
	        	int id = dataManager.get(ctsd.tableId).addPlayer(ctsd.address);
	        	// 返回客户端playerid
	        	break;
	        case ready:
	        	dataManager.get(ctsd.tableId).letReady(ctsd.playerId);
	        	if(dataManager.get(ctsd.tableId).isAllReady()) {
	        		for(int i=0;i<4;i++) {
	        			ServerToClientData data = dataManager.get(ctsd.tableId).spawnData(Type.other, i);
	        			ctx.write(data);
	        		}
	        		ctx.flush();
	        	}
	        	break;
	        case game:
	        	dataManager.get(ctsd.tableId).translate(ctsd);
	        	for(int i=0;i<4;i++) {
        			ServerToClientData data = dataManager.get(ctsd.tableId).spawnData(Type.game, i);
        			ctx.write(data);
        		}
        		ctx.flush();
	        	break;
	        case quit:
	        	dataManager.get(ctsd.tableId).t.delPlayer(ctsd.playerId);
	        	break;
	        case over:
	        	break;
			default:
				break;
        }
        log.info("channelRead");
    }
	@Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered");
        ctx.fireChannelRegistered();
    }
	@Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelUnregistered");
        ctx.fireChannelUnregistered();
    }
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        ctx.fireChannelActive();
    }
	@Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive");
        ctx.fireChannelInactive();
    }
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete");
        ctx.fireChannelReadComplete();
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered");
        ctx.fireUserEventTriggered(evt);
    }
    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.info("channelWritabilityChanged");
        ctx.fireChannelWritabilityChanged();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.info("exceptionCaught");
        ctx.fireExceptionCaught(cause);
    }
}
