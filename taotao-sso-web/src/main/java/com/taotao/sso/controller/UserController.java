package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common_pojo.TaotaoResult;
import com.taotao.common_utils.CookieUtils;
import com.taotao.common_utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

/**
 * 单点登录Controller
 * @author 10309
 *
 */
@Controller
public class UserController {

	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;

	@Autowired
	public UserService userService;

	/**
	 * 检查数据有效性
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult checkData(@PathVariable String param, @PathVariable Integer type) {
		TaotaoResult result = userService.checkData(param, type);
		return result;
	}

	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult regist(TbUser user) {
		TaotaoResult result = userService.regist(user);
		return result;
	}

	/**
	 * 用法户登录
	 */
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = userService.login(username, password);
		if (result.getStatus() == 200) {
			CookieUtils.setCookie(request, response, TOKEN_KEY, result.getData().toString());
		}
		return result;
	}

	/**
	 * 根据token查询用户信息jsonp解决方案一
	 */
	@RequestMapping(value="/user/token/{token}",method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = userService.getUserByToken(token);
		if (StringUtils.isNotBlank(callback)) {
			return callback+"("+JsonUtils.objectToJson(result)+");";
		}
		return JsonUtils.objectToJson(result);
	}
	/**
	 * 根据token查询用户信息jsonp解决方案二(限制spring4.1以上版本)
	 */
	/*@RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = userService.getUserByToken(token);
		if (StringUtils.isNotBlank(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}*/

	/**
	 * 用户安全退出
	 */
	@RequestMapping(value = "/user/logout/{token}", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult logout(@PathVariable String token) {
		TaotaoResult result = userService.delUserRedis(token);
		return result;
	}
}
