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
	public static List<GameManager> gameManagers = new ArrayList<GameManager>();
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String preHandlerAfferentMsg = (String)msg; //得到消息后，可根据消息类型分发给不同的service去处理数据
        log.info("{}preHandler传入的数据{}"+preHandlerAfferentMsg);
        BasicData bd = GameManager.jsonToData(preHandlerAfferentMsg);
        switch(bd.dataType) {
	        case game:
	        case other:
	        case test:
	        	ctx.fireChannelRead(bd);
	        	break;
			default:
				break;
        }
        log.info("channelRead");
        /*
		ClientToSeverGameData ctsd = GameManager.jsonToData(preHandlerAfferentMsg);
		GameManager gm = gameManagers.get(ctsd.tableId);
        switch(ctsd.datatype) {
	        case create:
	        	gameManagers.add(new GameManager());
	        	gameManagers.get(gameManagers.size()-1).addPlayer(ctsd.address);
	        	ServerToClientData d1 = gameManagers.get(gameManagers.size()-1).spawnData(Type.other, 0);
    			ctx.writeAndFlush(d1);
	        	break;
	        case join:
	        	int id = gameManagers.get(ctsd.tableId).addPlayer(ctsd.address);
	        	ServerToClientData d2 = gameManagers.get(gameManagers.size()-1).spawnData(Type.other, id);
	        	ctx.writeAndFlush(d2);
	        	break;
	        case quit:
	        	if(gm.delPlayer(ctsd.playerId) <= 0) {
	        		gameManagers.remove(ctsd.tableId);
	        	}
	        	break;
	        case ready:
	        	gm.letReady(ctsd.playerId);
	        	if(gm.isAllReady()) {
	        		gm.startGame();
	        		ServerToClientData data = gm.spawnData(Type.permit, 0);
	        		for(int i=1;i<4;i++) {
	        			ctx.write(data);
	        			data = gm.spawnData(Type.game, i);
	        		}
	        		ctx.writeAndFlush(data);
	        	}
	        	break;
	        case game:
	        	if(gm.isPermited(ctsd.playerId)) {
	        		gm.translate(ctsd);
	        		int nextid = gm.getNextPlayer();
		        	for(int i=0;i<4;i++) {
		        		ServerToClientData data = gm.spawnData(Type.game, i);
		        		if(i == nextid) {
		        			data = gm.spawnData(Type.permit, i);
		        		}
	        			ctx.write(data);
	        		}
	        		ctx.flush();
	        	}
	        	break;
	        case test:
	        	gameManagers.add(new GameManager(Type.test));
	        	for(int i=0;i<4;i++) {
        			ServerToClientData data = gameManagers.get(ctsd.tableId).spawnData(Type.test, i);
        			ctx.write(data);
        		}
	        	ctx.flush();
	        	break;
			default:
				break;
        }
        
        */
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
