package chinese.server;

import io.netty.channel.Channel;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/3/21 16:33
 * @Path: chinese.server
 */
public class UserInfo {
    private String userId;  // UID
    private String addr;    // 地址
    private Channel channel;// 通道

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}