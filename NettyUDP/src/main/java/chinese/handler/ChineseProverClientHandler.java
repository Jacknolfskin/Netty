package chinese.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/4/18 16:17
 * @Path: chinese.handler
 */
public class ChineseProverClientHandler extends
        SimpleChannelInboundHandler<DatagramPacket> {
    /**
     * DatagramPacket的详细介绍，看服务器的代码注释，这里不重复了。
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg)
            throws Exception {
        String response = msg.content().toString(CharsetUtil.UTF_8);
        if (response.startsWith("谚语查询结果：")) {
            System.out.println(response);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
