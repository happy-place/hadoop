package com.bigdata.zookeeper.exec;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    /**
     * 自定义 zk 事物
     * new Transaction(..).create(..).setData(..)...commit()
     */
    private ZooKeeper zk;
    private List<Op> ops = new ArrayList<Op> ();

    protected Transaction( ZooKeeper zk) {
        this.zk = zk;
    }

    public Transaction create(final String path, byte data[], List<ACL> acl,
                              CreateMode createMode) {
        ops.add(Op.create(path, data, acl, createMode.toFlag()));
        return this;
    }

    public Transaction delete(final String path, int version) {
        ops.add(Op.delete(path, version));
        return this;
    }

    public Transaction check(String path, int version) {
        ops.add( Op.check(path, version));
        return this;
    }

    public Transaction setData(final String path, byte data[], int version) {
        ops.add(Op.setData(path, data, version));
        return this;
    }

    public List<OpResult> commit() throws InterruptedException, KeeperException {
        return zk.multi(ops);
    }
}