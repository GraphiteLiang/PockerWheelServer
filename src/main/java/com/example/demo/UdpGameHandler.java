package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.solution.BasicData;
import com.example.demo.solution.ClientToServerGameData;
import com.example.demo.solution.ClientToServerOtherData;
import com.example.demo.solution.GameManager;
import com.example.demo.solution.ServerToClientData;
import com.example.demo.solution.Type;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UdpGameHandler extends ChannelInboundHandlerAdapter{
	private static final Logger log= 
			LoggerFactory.getLogger(UdpHandler.class);
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicData bd = (BasicData)msg;
        if(bd.dataType == Type.other) {
        	ctx.fireChannelRead(bd);
        	return;
        }
        ClientToServerGameData ctsd = (ClientToServerGameData)bd;
    	if(ctsd.dataType == Type.test) {
    		UdpHandler.gameManagers.add(new GameManager(Type.test));
        	for(int i=0;i<4;i++) {
    			ServerToClientData data = UdpHandler.gameManagers.get(ctsd.tableId).spawnData(Type.test, i);
    			ctx.write(data);
    		}
        	ctx.flush();
        	return;
    	}
    	GameManager gm = UdpHandler.gameManagers.get(ctsd.tableId);
    	if(ctsd.dataType == Type.ready || ctsd.dataType ==Type.skip) {
        	gm.letReady(ctsd.playerId, ctsd.dataType);
        	if(gm.isAllReady()) {
        		if(!gm.gameactive)gm.startGame();
        		else gm.nextEpoch();
        		ServerToClientData data = gm.spawnData(Type.permit, 0);
        		for(int i=1;i<4;i++) {
        			ctx.write(data);
        			data = gm.spawnData(Type.game, i);
        		}
        		ctx.writeAndFlush(data);
        	}
        	return;
    	}
    	// 接受calculate的消息代表轮末可以开始结算分数
    	if(ctsd.dataType == Type.calculate) {
    		int nextid = gm.endOfEpoch();
    		for(int i=0;i<4;i++) {
        		ServerToClientData data = gm.spawnData(Type.game, i);
        		if(i == nextid) {
        			data = gm.spawnData(Type.permit, i);
        		}
    			ctx.write(data);
    		}
    	}
		if(gm.isPermited(ctsd.playerId)) {
    		gm.translate(ctsd);
    		int nextid = gm.getNextPlayer();
    		Type sendtype = gm.t.turn >= gm.t.playerCount?Type.calculate:Type.game;
        	for(int i=0;i<4;i++) {
        		ServerToClientData data = gm.spawnData(sendtype, i);
        		if(i == nextid) {
        			data = gm.spawnData(Type.permit, i);
        		}
    			ctx.write(data);
    		}
    		ctx.flush();
    	}
		log.info("游戏执行");
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
