package chinese.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/3/21 16:11
 * @Path: chinese.server
 */
public class imServer {
    private io.netty.channel.Channel channel;
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private  EventLoopGroup workerGroup = new NioEventLoopGroup();
    private static int port=9999;
    //线程池设计的定时任务类
    public imServer(int port) {

    }
    public void run() throws Exception {
        try {
            //创建ServerBootstrap实例
            ServerBootstrap b = new ServerBootstrap();
            //设置并绑定Reactor线程池
            b.group(bossGroup, workerGroup)
                    //设置并绑定服务端Channel
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("http-codec", new HttpServerCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler()); // WebSocket通信支持
                            pipeline.addLast(new chinese.server.SocketHandler());//自定义处理类

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
//          System.out.println("WebsocketChatServer Start:" + port);
            try {
                ChannelFuture f = b.bind(port).sync();//// 服务器异步创建绑定

                channel = f.channel();
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {

            }
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            channel.closeFuture().syncUninterruptibly();
//          System.out.println("WebsocketChatServer Stop:" + port);
        }
    }
    public static void main(String[] args) throws Exception {
        new imServer(port).run();
    }
}
