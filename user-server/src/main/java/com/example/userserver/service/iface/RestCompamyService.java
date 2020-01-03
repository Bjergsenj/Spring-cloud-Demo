package com.example.userserver.service.iface;

import java.io.IOException;

/**
 * description: TODO
 * create: 2020/1/3 16:16
 *
 * @author niemingxin
 */
public interface RestCompamyService {
    void add() throws IOException;

    Object searchByQuery();
}
