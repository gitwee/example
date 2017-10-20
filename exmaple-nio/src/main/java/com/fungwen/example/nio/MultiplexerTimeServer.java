package com.fungwen.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Set;

public class MultiplexerTimeServer implements  Runnable{

    private Selector selector;
    private ServerSocketChannel serverChannel;
    private volatile boolean stop = false;
    private final int port;
    private final static String CHARSET = "UTF-8";
    private final static String ORDER = "QUERY TIME ORDER";
    private final static String BAD_ORDER = "BAD_ORDER";

    public MultiplexerTimeServer(int port) {
        this.port = port;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void init() throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(port), 1024);
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("The time server start in port:" + port);
    }

    public void  stop() {
        stop = true;
    }

    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    handleSelectionKey(selectionKey);
                }
                selectionKeys.clear();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(serverChannel != null) {
            try {
                serverChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void  handleSelectionKey(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isValid()) {
            if (selectionKey.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else if (selectionKey.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                String order = handleRead(socketChannel, selectionKey);
                String response = handleOrder(order);
                doWrite(socketChannel, response);
            }
        }
    }

    private String handleRead(SocketChannel socketChannel, SelectionKey selectionKey) throws IOException {
        String readString = null;
        ByteBuffer buffer = ByteBuffer.allocate(512);
        int readBytes = socketChannel.read(buffer);
        if (readBytes > 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            readString = new String(bytes, CHARSET);
        } else if (readBytes <0 ){
            // 对端链路关闭
            selectionKey.channel();
            socketChannel.close();
        }else {
            // 读到 0byte 忽略
        }
        return  readString;
    }

    private String handleOrder(String order) {
        if (order == null) return null;
        System.out.println("The time server received order:" + order);
        String responseString = null;
        if (ORDER.equalsIgnoreCase(order)) {
            responseString = new Date().toString();
        }else {
            responseString = BAD_ORDER;
        }
        return  responseString;
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (response == null || response.trim().length() == 0) return;
        byte[] bytes = response.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        socketChannel.write(buffer);
    }
}
