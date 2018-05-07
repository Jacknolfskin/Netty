package chinese.server;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/3/21 16:14
 * @Path: chinese.server
 */
public class UserInfoManager {
    private static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    private static ConcurrentMap<Channel, UserInfo> userInfos = new ConcurrentHashMap<>();
    /**
     * 登录注册 channel
     *
     *
     */
    public static void addChannel(Channel channel,String uid) {
        //String remoteAddr = NettyUtil.parseChannelRemoteAddr(channel);
        String remoteAddr = channel.remoteAddress().toString();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(uid);
        userInfo.setAddr(remoteAddr);
        userInfo.setChannel(channel);
        userInfos.put(channel, userInfo);
    }

    public static void removeChannel(Channel channel) {
        channel.close();
    }


    public static UserInfo getUserInfo(Channel channel) {
        UserInfo userInfo = userInfos.get(channel);
        return userInfo;
    }


    /**
     * 普通消息
     *
     * @param message
     */
    public static void broadcastMess(String uid,String message,String sender) {
        if (!StringUtil.isNullOrEmpty(message)) {
            try {
                rwLock.readLock().lock();
                Set<Channel> keySet = userInfos.keySet();
                for (Channel ch : keySet) {
                    UserInfo userInfo = userInfos.get(ch);
                    if (!userInfo.getUserId().equals(uid) ) continue;
                    String backmessage=sender+","+message;
                    ch.writeAndFlush(new TextWebSocketFrame(backmessage));
                    /*  responseToClient(ch,message);*/
                }
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }
}
