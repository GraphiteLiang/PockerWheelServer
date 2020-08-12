package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.demo.solution.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UdpOtherHandler extends ChannelInboundHandlerAdapter{
	private static final Logger log= 
			LoggerFactory.getLogger(UdpHandler.class);
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicData bd = (BasicData)msg;
		ClientToServerOtherData ctsd = (ClientToServerOtherData)bd;
        List<GameManager> gameManagers = UdpHandler.gameManagers;
        
        // 0创建，1加入，2退出，3刷新界面
        switch (ctsd.operation){
	    	case 0:
	    		gameManagers.add(new GameManager(ctsd.dataType, ctsd.address, ctsd.message));
	        	ServerToClientData d1 = gameManagers.
	        			get(gameManagers.size()-1).
	        			spawnData(Type.success, 0);
    			ctx.writeAndFlush(d1);
	        	break;
	    	case 1:
	    		GameManager gm = gameManagers.get(ctsd.tableId);
	    		int id = gm.addPlayer(ctsd.address);
	        	for(int i=0;i<=id;i++) {
	    			ServerToClientData data = gm.spawnData(Type.success, i);
	    			ctx.write(data);
	    		}
	        	ctx.flush();
	        	break;
	    	case 2:
	    		gm = gameManagers.get(ctsd.tableId);
	    		if(gm.delPlayer(ctsd.playerId) <= 0) {
	        		gameManagers.remove(ctsd.tableId);
	        	}
	        	break;
	    	case 3:
	    		RoomInfo roomlist = new RoomInfo();
	    		roomlist.address = ctsd.address;
	    		for(int i=0;i<gameManagers.size();i++) {
	    			GameManager g = gameManagers.get(i);
	    			roomlist.addRoom(i, g.roomname, g.playerCount());
	    		}
	    		ctx.writeAndFlush(roomlist);
    	}
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