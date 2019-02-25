package com.taotao.sso.service;

import com.taotao.common_pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {

	TaotaoResult checkData(String param, Integer type);

	TaotaoResult regist(TbUser user);

	TaotaoResult login(String username, String password);

	TaotaoResult getUserByToken(String token);

	TaotaoResult delUserRedis(String token);

}
