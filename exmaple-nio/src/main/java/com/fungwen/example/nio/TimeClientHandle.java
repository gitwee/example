package com.fungwen.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class TimeClientHandle implements  Runnable{

    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop = false;
    private final int PORT;
    private final String HOST;
    private final static String CHARSET = "UTF-8";
    private final static String ORDER = "QUERY TIME ORDER";
    private final static String BAD_ORDER = "BAD_ORDER";

    public TimeClientHandle(String host, int port) {
        this.HOST = host;
        this.PORT = port;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void init() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

    }

    public void  stop() {
        stop = true;
    }

    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        if(socketChannel != null) {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void  handleSelectionKey(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isValid()) {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            if (selectionKey.isConnectable()) {
                if (socketChannel.finishConnect()){
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    doWrite(socketChannel);
                }else {
                    // 连接失败，退出进程
                    System.exit(1);
                }
            } else if (selectionKey.isReadable()) {
                String respone = handleRead(socketChannel, selectionKey);
                handleResponse(respone);
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

    private void handleResponse(String respone) {
        if (respone == null) return;
        if (BAD_ORDER.equalsIgnoreCase(respone)) {
            System.out.println("Response is: " +respone);
        }else {
            System.out.println("Now is: " + respone);
        }
        stop();
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] bytes = ORDER.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        socketChannel.write(buffer);
        if (!buffer.hasRemaining()) {
            System.out.println("Send order to server success");
        }
    }

    private void  doConnect() throws IOException {
        if (socketChannel.connect(new InetSocketAddress(HOST, PORT))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }
}
