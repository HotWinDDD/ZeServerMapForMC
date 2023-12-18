package com.qq.oldkingok.zombieescapemapssystem.sourceapi;

import com.qq.oldkingok.zombieescapemapssystem.ZombieEscapeMapsSystem;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class SourceAPI {
    public static void main(String[] args) throws IOException {
        GameInfo gameInfo = GameInfo.fromString("43.137.50.243:27015");
        updateInfo(gameInfo);
        System.out.println(gameInfo);
    }

    /**
     * 更新玩家在线人数和最大人数信息
     * @param gameInfo
     * @return 是否换图了
     * @throws IOException
     */
    public static boolean updateInfo(GameInfo gameInfo) throws IOException {
        var ip = gameInfo.ip;
        var port = gameInfo.port;
        var timeout = ZombieEscapeMapsSystem.getInstance()
                .configManager.okConfig.getConfig().getInt("time-out");

        // 获取服务器概览信息
        int[] intData = {0xFF,0xFF,0xFF,0xFF,
                0x54,0x53,0x6F,0x75,0x72,0x63,0x65,0x20,0x45,0x6E,0x67,0x69,
                0x6E,0x65,0x20,0x51,0x75,0x65,0x72,0x79,0x00};
        ByteBuffer byteBuffer = ByteBuffer.allocate(intData.length);
        for (int intDatum : intData) {
            byteBuffer.put((byte) intDatum);
        }
        byte[] sendData = byteBuffer.array();

        DatagramSocket clientSocket = new DatagramSocket();
        clientSocket.setSoTimeout(timeout);
        InetAddress IPAddress = InetAddress.getByName(ip);
        // 发包获取“Challenge Code” （标识符）
        byte[] receiveData = new byte[9];
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);

        var receiveBytes = receivePacket.getData();
        var newByteBuffer = ByteBuffer.allocate(intData.length + 4);
        newByteBuffer.put(byteBuffer.array());
        // 添加“Challenge Code”
        newByteBuffer.put(Arrays.copyOfRange(receiveBytes,5,9));

        // 再次发包收包
        sendData = newByteBuffer.array();
        receiveData = new byte[1024];
        sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        clientSocket.send(sendPacket);
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);

        // 打印
        var serverDataByte = receivePacket.getData();
        // String modifiedSentence = new String(serverDataByte);
        // System.out.println(modifiedSentence);

        // 解析
        var bais = new ByteArrayInputStream(serverDataByte);
        DataInputStream dis = new DataInputStream(bais);
        dis.readInt(); // 读取头部无意义的 FF FF FF FF
        dis.readByte(); // 读取标识符“I”
        dis.readByte(); // 读取协议版本

        var name = readStr(dis);
        var map = readStr(dis);
        var folder = readStr(dis);
        var game = readStr(dis);

        dis.readShort(); // Steam Application ID of game.
        var players = dis.readByte(); // Number of players on the server.
        var maxPlayer = dis.readByte(); // Maximum number of players the server reports it can hold.

        dis.close();
        bais.close();
        clientSocket.close();

        // 更新
        var isChangeMap = !map.equalsIgnoreCase(gameInfo.mapName);
        gameInfo.mapName = map;
        gameInfo.mapNameZh = ZombieEscapeMapsSystem.getInstance().translation.getTrans(map);
        gameInfo.players = players;
        gameInfo.maxPlayer = maxPlayer;
        gameInfo.canDetect = true;

        return isChangeMap;
    }

    /**
     * 读取以"0x00"结尾的字符串
     * @param stream
     * @return
     * @throws IOException
     */
    private static String readStr(DataInputStream stream) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte tempByte = 1;
        while ((tempByte = stream.readByte()) != (byte) 0x00) {
            buffer.put(tempByte);
        }
        return new String(Arrays.copyOfRange(buffer.array(), 0, buffer.position()));
    }
}
