package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common_pojo.TaotaoResult;
import com.taotao.common_utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.jedis.JedisClient;
import com.taotao.sso.service.UserService;

/**
 * 单点登录Service
 * @author 10309
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Value("${UER_SESSION}")
	private String UER_SESSION;
	@Value("${SESSION_TIME}")
	private Integer SESSION_TIME;

	@Autowired
	private TbUserMapper TbuserMapper;
	@Autowired
	private JedisClient jedisClient;
	
	/**
	 * 检查属性有效性
	 */
	@Override
	public TaotaoResult checkData(String param, Integer type) {
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//根据type不同设置不同条件
		//1、2、3分别代表username、phone、email
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if (type == 3) {
			criteria.andEmailEqualTo(param);
		}else {
			return TaotaoResult.build(400, "请求参数type有误,请检查");
		}
		List<TbUser> userList = TbuserMapper.selectByExample(example );
		//判断数据是否已存在
		if (userList != null && userList.size() > 0) {
			return TaotaoResult.ok(false);
		}
		return TaotaoResult.ok(true);
	}
	/**
	 * 用户注册
	 */
	@Override
	public TaotaoResult regist(TbUser user) {
		//判断数据有效性
		//username不能为空
		if (StringUtils.isBlank(user.getUsername())) {
			TaotaoResult.build(400, "用户名不能为空,请检查");
		}
		//username不能重复
		TaotaoResult username = this.checkData(user.getUsername(), 1);
		if (!(boolean) username.getData()) {
			return TaotaoResult.build(400, "用户名不能重复,请检查");
		}
		//密码不能为空
		if (StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(400, "用户密码不能为空,请检查");
		}
		//手机号不能重复
		if (StringUtils.isNotBlank(user.getPhone())) {
			TaotaoResult phone = this.checkData(user.getPhone(),2);
			if (!(boolean) phone.getData()) {
				return TaotaoResult.build(400, "手机号不能重复,,请检查");
			}
		}
		//邮箱不能重复
		if (StringUtils.isNotBlank(user.getEmail())) {
			TaotaoResult email = this.checkData(user.getEmail(),3);
			if (!(boolean) email.getData()) {
				return TaotaoResult.build(400, "邮箱地址不能重复,,请检查");
			}
		}
		//补全属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//密码加密
		String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pwd);
		//插入数据
		TbuserMapper.insert(user);
		return TaotaoResult.ok();
	}
	/**
	 * 用户登录
	 */
	@Override
	public TaotaoResult login(String username, String password) {
		//设置查询限制
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
		//查询数据库
		List<TbUser> list = TbuserMapper.selectByExample(example );
		//判断用户是否存在
		if (list == null || list.size() < 1) {
			return TaotaoResult.build(400, "用户名或密码错误!!!");
		}
		TbUser user = list.get(0);
		//使用生成token
		String token = UUID.randomUUID().toString();
		//清空密码
		user.setPassword(null);
		//写入redis
		jedisClient.set(UER_SESSION+":"+token, JsonUtils.objectToJson(user));
		//设置过期时间
		jedisClient.expire(UER_SESSION+":"+token, SESSION_TIME);
		//返回
		return TaotaoResult.ok(token);
	}
	/**
	 * 根据token取用户信息
	 */
	@Override
	public TaotaoResult getUserByToken(String token) {
		//查redis
		String string = jedisClient.get(UER_SESSION+":"+token);
		//判断是否存在
		if (StringUtils.isBlank(string)) {
			return TaotaoResult.build(400, "用户登录已过期");
		}
		//重设存活时间
		jedisClient.expire(UER_SESSION+":"+token, SESSION_TIME);
		//转成pojo
		TbUser user = JsonUtils.jsonToPojo(string, TbUser.class);
		//返回
		return TaotaoResult.ok(user);
	}
	/**
	 * 根据UER_SESSION+":"+token删除redis
	 */
	@Override
	public TaotaoResult delUserRedis(String token) {
		Long del = jedisClient.del(UER_SESSION+":"+token);
		if (del>0) {
			return TaotaoResult.ok();
		}
		return TaotaoResult.build(400, "用户未登录,请刷新重诗");
	}

}
