package com.qq.oldkingok.zombieescapemapssystem;

import com.qq.oldkingok.okapi.OkConfigAPI;

import java.util.List;

public class Message {

    private ZombieEscapeMapsSystem instance;
    private OkConfigAPI.OkConfig msg;

    public Message(ZombieEscapeMapsSystem instance) {
        this.instance = instance;
        msg = instance.configAPI.getOkConfig("message.yml");
    }

    public String getMsg(String node) {
        return msg.getStr(node);
    }

    public List<String> getMsgList(String node) {
        return msg.getStrList(node);
    }
}
