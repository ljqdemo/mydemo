package com.ljq.mydemo.test;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GlobalData {

	private static int server_code = 10;

	// private static String base_protocol = "http";
	/** 发布平台服务器地址 */
	private static String base_cms_server_url = "";
	/** API服务器地址 */
	private static String base_api_server_url = "";
	/** 资源服务器地址 */
	private static String base_res_server_url = "";
	/** 数据库服务器地址 */
	private static String base_db_server_url = "192.168.1.201";
	/** 发布平台服务器端口 */
	private static String rest_admin_port = "";
	/** API服务器端口 */
	private static String rest_api_port = "";
	/** 资源服务器端口 */
	private static String res_dl_port = "8080";
	/** 数据库服务器端口 */
	private static String db_port = "4000";
	/** 平台城市代码 */
	private static String corpCode = "CS";
	/** admin访问url */
	private static String rest_admin_url = "";
	/** api访问url */
	private static String rest_api_url = "";
	/** 资源服务器访问url */
	public static String res_dl_url = "";
	/** 数据库服务器访问url */
	public static String db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
	/** 当前操作系统 Windows 7 , Mac OS X , Linux */
	public static String OS_NAME = System.getProperty("os.name");

	/** 下载文件保存路径 */
	// public static String dl_res_dir = "/Users/will/Downloads/123";
	public static String dl_res_dir = "";

	static {
		init(server_code);
		if (OS_NAME.startsWith("Win")) {
			dl_res_dir = "F:/123/";
		} else {
			dl_res_dir = "~/Downloads/123";
		}
	}

	public static String bj_cms_base_url = "http://portegezen.ddns.net:8081/api";


	// * UAT映射本地北京
	// * API: http://124.232.158.120:9080/rest-api/v1
	// * RES:http://124.232.158.120:9081

	// * UAT-BJ
	// * ADMIN: http://uatcmstv.svrsp.com:8000/bj-rest-admin/v1
	// * API: http://uatapitv.svrsp.com:8000/bj-rest-api/v1
	// * RES: http://uatdrtv.svrsp.com:8080/

	// * UAT-CS:
	// * ADMIN: http://uatcmstv.svrsp.com:8000/cs-rest-admin/v1
	// * API: http://uatapitv.svrsp.com:8000/cs-rest-api/v1
	// * RES: http://uatdrtv.svrsp.com:8080/

	// * BJ: ADMIN: http://bjcmstv.svrsp.com:10000/v1
	// * API: http://bjapitv.svrsp.com:11000/rest-api/v1
	// * RES: http://bjdrtv.svrsp.com:12000/

	/**
	 * 服务器接口地址初始化
	 * 
	 * @param server_code 服务器代码
	 */
	public static void init(int server_code) {
		switch (server_code) {
			case 0:
				base_cms_server_url = "192.168.1.145";
				base_api_server_url = "192.168.1.70";
				base_res_server_url = "192.168.1.188";
				rest_admin_port = "21113";
				rest_api_port = "21122";
				corpCode = "BJ";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				base_db_server_url = "192.168.1.188";
				db_port = "3306";
				db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
				break;
			case 8:
				base_cms_server_url = "192.168.1.200";
				base_api_server_url = "192.168.1.200";
				base_res_server_url = "192.168.1.201";
				rest_admin_port = "9080";
				rest_api_port = "9080";
				corpCode = "TV";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/om-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/om-rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				base_db_server_url = "192.168.1.188";
				db_port = "3306";
				db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
				break;
			case 9:
				base_cms_server_url = "192.168.1.145";
				base_api_server_url = "192.168.1.145";
				base_res_server_url = "192.168.1.188";
				rest_admin_port = "21111";
				rest_api_port = "8898";
				corpCode = "DY";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/daily-pager";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/rest-admin";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				base_db_server_url = "192.168.1.188";
				db_port = "3306";
				db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
				break;
			case 10:
				base_api_server_url = "192.168.1.200";
				rest_admin_port = "9080";
				rest_api_port = "9080";
				corpCode = "BJ";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				base_db_server_url = "192.168.1.201";
				db_port = "4000";
				db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
				break;
			case 11:
				base_cms_server_url = "124.232.158.120";
				base_api_server_url = "124.232.158.120";
				rest_admin_port = "9080";
				rest_api_port = "9080";
				res_dl_port = "9081";
				corpCode = "CS";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/cs-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/cs-rest-api";
				res_dl_url = "http://" + base_res_server_url + ":" + res_dl_port + "/";
				base_db_server_url = "192.168.1.222";
				db_port = "3306";
				db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
				break;
			case 12:
				corpCode = "BJ";
				base_cms_server_url = "124.232.158.120";
				base_api_server_url = "124.232.158.120";
				rest_admin_port = "9080";
				rest_api_port = "9080";
				res_dl_port = "9081";
				rest_admin_url = "http://" + base_cms_server_url + ":" + rest_admin_port + "/bj-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/bj-rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				base_db_server_url = "192.168.1.201";
				db_port = "4000";
				db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
				break;
			case 13:
				base_api_server_url = "192.168.1.200";
				rest_admin_port = "9080";
				rest_api_port = "9080";
				corpCode = "TV";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/tv-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/tv-rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				base_db_server_url = "192.168.1.201";
				db_port = "3306";
				db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
				break;

			case 14:
				base_api_server_url = "124.232.158.120";
				base_cms_server_url = "124.232.158.120";
				base_api_server_url = "124.232.158.120";
				rest_admin_port = "9080";
				rest_api_port = "9080";
				res_dl_port = "9081";
				corpCode = "TV";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/tv-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/tv-rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				base_db_server_url = "192.168.1.201";
				db_port = "3306";
				db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
				break;
			case 15:
				base_api_server_url = "124.232.158.120";
				base_cms_server_url = "124.232.158.120";
				base_api_server_url = "124.232.158.120";
				base_db_server_url = "192.168.1.201";
				db_port = "3306";
				rest_admin_port = "9080";
				rest_api_port = "9080";
				res_dl_port = "9081";
				corpCode = "DP";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/dp-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/dp-rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
				break;
			case 20:
				corpCode = "CS";
				base_cms_server_url = "uatcmstv.svrsp.com";
				base_api_server_url = "uatapitv.svrsp.com";
				base_res_server_url = "uatdrtv.svrsp.com";
				rest_admin_port = "8000";
				rest_api_port = "8000";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/cs-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/cs-rest-api";
				res_dl_url = "http://" + base_res_server_url + ":" + res_dl_port + "/";
				break;
			case 22:
				corpCode = "BJ";
				base_cms_server_url = "uatcmstv.svrsp.com";
				base_api_server_url = "uatapitv.svrsp.com";
				base_res_server_url = "uatdrtv.svrsp.com";
				rest_admin_port = "8000";
				rest_api_port = "8000";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/bj-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/bj-rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				base_db_server_url = "192.168.1.201";
				db_port = "4000";
				db_server_url = "jdbc:mysql://" + base_db_server_url + ":" + db_port;
				break;
			case 24:
				corpCode = "TV";
				base_cms_server_url = "uatcmstv.svrsp.com";
				base_api_server_url = "uatapitv.svrsp.com";
				base_res_server_url = "uatdrtv.svrsp.com";
				rest_admin_port = "8000";
				rest_api_port = "8000";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/tv-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/tv-rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				break;
			case 30:
				corpCode = "CS";
				base_api_server_url = "csapitv.svrsp.com";
				base_res_server_url = "csdrtv.svrsp.com";
				rest_admin_port = "10000";
				rest_api_port = "11000";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/";
				res_dl_url = "http://" + base_res_server_url + ":" + res_dl_port + "/";
				break;
			case 40:
				corpCode = "HF";
				base_api_server_url = "hfapitv.svrsp.com";
				rest_admin_port = "10000";
				rest_api_port = "11000";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				break;
			case 41:
				corpCode = "HF";
				base_cms_server_url = "hfcmstv.svrsp.com";
				base_api_server_url = "hfapitv.svrsp.com";
				base_res_server_url = "hfdrtv.svrsp.com";
				rest_admin_port = "20000";
				rest_api_port = "21000";
				res_dl_port = "22000";
				rest_admin_url = "http://" + base_cms_server_url + ":" + rest_admin_port;
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port;
				res_dl_url = "http://" + base_res_server_url + ":" + res_dl_port;
				break;
			case 50:
				corpCode = "BJ";
				base_cms_server_url = "bjcmstv.svrsp.com";
				base_api_server_url = "bjapitv.svrsp.com";
				base_res_server_url = "bjdrtv.svrsp.com";
				rest_admin_port = "10000";
				rest_api_port = "11000";
				res_dl_port = "12000";
				rest_admin_url = "http://" + base_cms_server_url + ":" + rest_admin_port + "/rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/rest-api";
				res_dl_url = "http://" + base_res_server_url + ":" + res_dl_port + "/";
				break;
			case 60:
				base_api_server_url = "124.232.158.120";
				rest_admin_port = "8000";
				rest_api_port = "8000";
				corpCode = "TV";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/tv-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/tv-rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				break;
			case 70:
				base_api_server_url = "172.16.209.4";
				rest_admin_port = "8090";
				rest_api_port = "8101";
				corpCode = "DY";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/dp-rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/dp-rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port;
				break;
			case 80:
				base_cms_server_url = "qdcmstv.svrsp.com";
				base_api_server_url = "qdapitv.svrsp.com";
				base_res_server_url = "qddrtv.svrsp.com";
				rest_admin_port = "10000";
				rest_api_port = "11000";
				res_dl_port = "12000";
				corpCode = "QD";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port;
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port;
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port;
				break;
			default:
				corpCode = "BJ";
				base_api_server_url = "192.168.1.200";
				rest_admin_port = "9080";
				rest_api_port = "9080";
				res_dl_port = "8080";
				rest_admin_url = "http://" + base_api_server_url + ":" + rest_admin_port + "/rest-admin";
				rest_api_url = "http://" + base_api_server_url + ":" + rest_api_port + "/rest-api";
				res_dl_url = "http://" + base_api_server_url + ":" + res_dl_port + "/";
				break;
		}
	}

	/** 发布平台免验证登录接口 */
	public static String rest_admin_login = "/v1/sign/login";
	/** 发布平台刷新token接口 */
	public static String rest_admin_refresh_token = "/v1/sign/refreshToken";

	/** 终端增、删、查、改接口 */
	public static String rest_admin_terminal = "/v1/terminal";
	/** 多终端批量修改接口 */
	public static String rest_admin_terminal_batch = "/v1/terminal/batch";
	/** 终端审核接口 */
	public static String rest_admin_terminal_audit = "/v1/terminal/audit";
	/** 终端基础信息接口 */
	public static String rest_admin_terminal_baseInfo = "/v1/terminal/baseInfo";
	/** 终端分页查询接口 */
	public static String rest_admin_terminal_page = "/v1/terminal/page";

	/** 发布平台终端分组接口 */
	public static String rest_admin_terminal_group = "/v1/terminal/group";
	/** 发布平台终端分组列表接口 */
	public static String rest_admin_terminal_group_list = "/v1/terminal/common/group/list";
	/** 发布平台终端分组带屏幕列表接口 */
	public static String rest_admin_terminal_group_list_screen = "/v1/terminal/common/group/list/screen";

	/** 标签相关 */
	public static String rest_admin_tag = "/v1/tag";
	/** 标签分页查询 */
	public static String rest_admin_tag_page = "/v1/tag/page";
	/** 标签类别相关 */
	public static String rest_admin_tagcategory = "/v1/tagcategory";
	/** 标签类别列表查询 */
	public static String rest_admin_tagcategory_list = "/v1/tagcategory/list";
	/** 终端分组标签 */
	public static String rest_admin_terminal_group_tag = "/v1/terminal/group/tag";
	/** 终端屏幕标签 */
	public static String rest_admin_terminal_screen_tag = "/v1/terminal/screen/tag";
	/** 终端屏幕标签-屏幕类型绑定标签 */
	public static String rest_admin_terminal_screen_tag_batch = "/v1/terminal/screen/tag/batch";
	/** 终端屏幕标签分页查询 */
	public static String rest_admin_terminal_screen_tag_page = "/v1/terminal/screen/tag/page";

	public static String rest_admin_screen;
	public static String rest_admin_screen_list;

	/** 免验证登录时，用户名 */
	public static String user_name = "willa";
	/** 免验证登录时，密码；需加密 */
	public static String user_pwd = "123456";
	public static String user_pwd_bj = "Zhou_16358";
	public static String user_scope = "PC";

	/** FAST获取文件下载token */
	public static String rest_api_res_url = rest_api_url + "/v1/terminal/token";
	/** 获取天气信息tgz文件接口 */
	public static String rest_api_weather_tgz_url = rest_api_url + "/v1/weather/baseInfo";

	/** 获取广告单信息 (TV是上播节目单) */
	public static String rest_api_advertisement = "/v1/advertisement";
	/** 终端注册 */
	public static String rest_api_dev_reg = "/v1/terminal/register";
	/** 终端上传文件 */
	public static String rest_api_upload = "/v1/upload";
	/** 获取弹幕节目单 */
	public static String rest_api_textbullet = "/v1/textbullet";
	/** 发布弹幕 */
	public static String rest_admin_api_text_bullet_publish = "/v1/api/text_bullet/publish";

	/** 节目增、删、查、改 */
	public static String rest_admin_program = "/v1/program";
	/** 节目审核 */
	public static String rest_admin_program_audit = "/v1/program/audit";
	/** 节目分页查询 */
	public static String rest_admin_program_page = "/v1/program/page";
	/** 节目另存为 */
	public static String rest_admin_program_save_as = "/v1/program/save/as";
	/** 获取节目xml信息 */
	public static String rest_admin_program_xml = "/v1/program/xml";

	/** 节目发布增、删、查、改 */
	public static String rest_admin_schedule = "/v1/schedule";
	/** 节目发布审核 */
	public static String rest_admin_schedule_audit = "/v1/schedule/audit";
	/** 导出节目发布单关联节目信息 */
	public static String rest_admin_schedule_export_by_ids = "/v1/schedule/exportPageByIds";
	/** 节目发布分页查询 */
	public static String rest_admin_schedule_page = "/v1/schedule/page";
	/** 移除节目发布信息 */
	public static String rest_admin_schedule_remove = "/v1/schedule/removeProgram";
	/** 撤销节目发布信息 */
	public static String rest_admin_schedule_revoke = "/v1/schedule/revoke";
	/** 获取节目发布xml信息 */
	public static String rest_admin_schedule_xml = "/v1/schedule/xml";

	/** 媒体增、删、查、改 */
	public static String rest_admin_media = "/v1/media";
	public static String rest_admin_media_save = "/v1/media/save";
	/** 媒体分页查询 */
	public static String rest_admin_media_page = "/v1/media/page";
	public static String rest_admin_media_uploading = "/v1/media/uploading";

	/** 媒体审核 */
	public static String rest_admin_media_audit = "/v1/media/audit/audit";
	/** 媒体审核分页查询 */
	public static String rest_admin_media_audit_page = "/v1/media/page";

	/** 获取垫片节目单 */
	public static String rest_api_shim = "/v1/shim";
	/** 获取手动节目单 */
	public static String rest_api_program = "/v1/program";

	/** 终端命令-清理播出文件 */
	public static String rest_admin_terminal_command_clear = "/v1/bj/terminal/command/clear";
	/** 终端布局相关接口 */
	public static String rest_admin_terminal_layout = "/v1/terminal/screen";
	/** 更新终端线路 */
	public static String rest_admin_terminal_update_line_no = "/v1/terminal/updateLineNo";
	/** 终端监控-主键查询 */
	public static String rest_admin_terminal_monitor_status = "/v1/terminal/status";
	/** 终端监控-清理文件 */
	public static String rest_admin_terminal_monitor_clear_file = "/v1/terminal/status/clear/file";
	/** 终端监控-终端命令分页查询 */
	public static String rest_admin_terminal_monitor_command_page = "/v1/terminal/status/command/page";
	/** 终端监控-获取终端动态信息 */
	public static String rest_admin_terminal_monitor_dynamic_info = "/v1/terminal/status/dynamicInfo";
	/** 终端监控-终端日志分页查询 */
	public static String rest_admin_terminal_monitor_log_page = "/v1/terminal/status/logs/page";
	/** 终端监控-分页查询 */
	public static String rest_admin_terminal_monitor_status_page = "/v1/terminal/status/page";
	/** 终端监控-播放监控 */
	public static String rest_admin_terminal_monitor_play = "/v1/terminal/status/play";
	/** 终端监控-终端截屏 */
	public static String rest_admin_terminal_monitor_printscreen = "/v1/terminal/status/printscreen";

	/** 模板增、删、查、改 */
	public static String rest_admin_template = "/v1/template";
	/** 模板审核 */
	public static String rest_admin_template_audit = "/v1/template/audit";
	/** 模板分页查询 */
	public static String rest_admin_template_page = "/v1/template/page";
	/** 模板关联终端 */
	public static String rest_admin_template_as_terminal = "/v1/template/terminal";
	/** 模板关联终端列表 */
	public static String rest_admin_template_as_terminal_list = "/v1/template/terminal/list";

	/** 获取省份列表 */
	public static String rest_admin_common_province_list = "/v1/province/list";
	/** 获取城市列表 */
	public static String rest_admin_common_city_list = "/v1/city/list";
	/** 保存或者修改天气 */
	public static String rest_admin_common_weather = "/v1/weather";
	/** 字典增、删、查、改 */
	public static String rest_admin_common_dictionary = "/v1/sys/dictionary";
	/** 获取字典子节点信息 */
	public static String rest_admin_common_dictionary_childrens = "/v1/sys/dictionary/childrens";
	/** API-终端清理指令 */
	public static String rest_admin_api_command_clear = "/v1/terminal/command/clear";
	/** API-终端网络校时指令 */
	public static String rest_admin_api_command_clock = "/v1/terminal/command/clock";
	/** API-终端播放日志指令 */
	public static String rest_admin_api_command_playlog = "/v1/terminal/command/playlog";
	/** API-终端开关机指令 */
	public static String rest_admin_api_command_switch = "/v1/terminal/command/switch";
	/** API-终端系统日志指令 */
	public static String rest_admin_api_command_syslog = "/v1/terminal/command/syslog";
	/** API-终端程序升级指令 */
	public static String rest_admin_api_command_upgrade = "/v1/terminal/command/upgrade";

	/** API-运维平台-开关机 */
	public static String rest_admin_api_ops_switch = "/v1/ops/terminal/switch";
	/** API-运维平台-终端信息接口 */
	public static String rest_admin_api_ops_terminal_info = "/v1/ops/terminal/terminalInfo";

	/** API-北京平台-终端基础信息分页查询 */
	public static String rest_admin_api_bj_terminal_baseinfo_page = "/v1//terminal/baseInfoPage";
	/** API-北京平台-终端动态信息分页查询 */
	public static String rest_admin_api_bj_terminal_dynamicinfo_page = "/v1/terminal/dynamicInfoPage";
	/** API-北京平台-终端基础信息批量更新 */
	public static String rest_admin_api_bj_terminal_update_baseinfo = "/v1/terminal/update/baseInfo";
	/** API-北京平台-终端批量升级 */
	public static String rest_admin_api_bj_terminal_upgrade = "/v1/terminal/upgrade";
	/** API-北京平台-终端批量设置音量 */
	public static String rest_admin_api_bj_terminal_volume = "/v1/terminal/volume";
	/** API-北京平台指令-获取文件列表 */
	public static String rest_admin_api_bj_command_filelist = "/v1/bj/terminal/command/filelist";
	/** API-北京平台指令-获取终端日志 */
	public static String rest_admin_api_bj_command_logfile = "/v1/bj/terminal/command/logfile";
	/** API-北京平台指令-获取终端截图 */
	public static String rest_admin_api_bj_command_screenshoot = "/v1/bj/terminal/command/screenshots";
	/** API-北京平台指令-一键DTMB */
	public static String rest_admin_api_bj_command_onekeydtmb = "/v1/bj/terminal/command/dtmb";
	/** API-北京平台指令-延长DTMB时长 */
	public static String rest_admin_api_bj_command_extend = "/v1/bj/terminal/command/extend";

	/** API-北京平台-获取终端增量日志 */
	public static String rest_admin_api_bj_terminal_logs_list = "/v1/bj/terminal/logs/list";
	/** 根据媒体重新生成节目单、广告单、发布单 */
	public static String rest_admin_api_bj_republish_by_media = "/v1/republish/media";
	/** 根据节目重新生成节目单、广告单、发布单 */
	public static String rest_admin_api_bj_republish_by_program = "/v1/republish/program";

	/** API-北京平台-删除节目 */
	public static String rest_admin_api_bj_program_del = "/v1/api/program";
	/** API-北京平台-节目发布 */
	public static String rest_admin_api_bj_program_publish = "/v1/api/program/publish";
	/** API-北京平台-播单发布 */
	public static String rest_admin_api_bj_program_timeline = "/v1/api/program/timeline";
	/** API-北京平台-节目暂停 */
	public static String rest_admin_api_bj_program_pause = "/v1/api/program/pause";
	/** API-北京平台-节目恢复 */
	public static String rest_admin_api_bj_program_resume = "/v1/api/program/resume";

	/** 日报-终端管理-指令发送-亮度设置 */
	public static String dailypager_device_command_send_brightness = "/daily-pager/device/command/send/brightness";
	/** 日报-终端管理-指令发送-上传日志 */
	public static String dailypager_device_command_send_log = "/daily-pager/device/command/send/log";
	/** 日报-终端管理-指令发送-截屏 */
	public static String dailypager_device_command_send_screenshot = "/daily-pager/device/command/send/screenshot";
	/** 日报-终端管理-指令发送-设置服务器地址 */
	public static String dailypager_device_command_send_server = "/daily-pager/device/command/send/server";
	/** 日报-终端管理-指令发送-重启、待机、重置 */
	public static String dailypager_device_command_send_switch = "/daily-pager/device/command/send/switch";
	/** 日报-终端管理-指令发送-音量设置 */
	public static String dailypager_device_command_send_volume = "/daily-pager/device/command/send/volume";

	/** 日报-终端管理-指令结果-日志 */
	public static String dailypager_device_command_result_log = "/daily-pager/device/command/result/log";
	/** 日报-终端管理-指令结果-截屏 */
	public static String dailypager_device_command_result_screenshot = "/daily-pager/device/command/result/screenshot";

	public static String dailypager_common_device_list = "/daily-pager/common/device/list";
	public static String dailypager_common_getNowTime = "/daily-pager/common/getNowTime";
	public static String dailypager_common_region_city = "/daily-pager/common/region/city";
	public static String dailypager_common_region_area = "/daily-pager/common/region/area";

	// operation manager about
	public static String om_business_saleLog = "/v1/saleLog";
	public static String om_business_saleLog_detail = "/v1/saleLog/getDetail";
	public static String om_business_saleLog_list = "/v1/saleLog/list";
	public static String om_business_saleLog_sendLog = "/v1/saleLog/sendLog";
	public static String om_business_saleLogAnnotation_list = "/v1/saleLogAnnotation/listAnnotation";
	public static String om_business_saleLogAnnotation_getDetail = "/v1/saleLogAnnotation/getDetail";
	public static String om_business_saleLogAnnotation_updateAnnotation = "/v1/saleLogAnnotation/updateAnnotation";
	public static String om_business_common_get_customer = "/v1/business/common/getJurisdictionUser";
	public static String om_business_customer_page = "/v1/customer/page";

	public static String om_business_material = "/v1/material";
	public static String om_business_material_page = "/v1/material/page";
	public static String om_business_audit_materiel = "/v1/material/audit";
	public static String om_business_audit_material_page = "/v1/material/audit/page";

	public static String om_business_product = "/v1/product";
	public static String om_business_product_detail = "/v1/product/detail";
	public static String om_business_product_list = "/v1/product/list";
	public static String om_business_product_updateStatus = "/v1/product/list";
	public static String om_business_productPackage_detail = "/v1/product/list";
	public static String om_business_productPackage_list = "/v1/product/list";
	public static String om_business_productPackage_updateStatus = "/v1/product/list";
	public static String om_business_productPackage = "/v1/product/list";


	private static void init_interface() {

		om_business_saleLog = rest_admin_url + "/v1/saleLog";
		om_business_saleLog_detail = rest_admin_url + "/v1/saleLog/getDetail";
		om_business_saleLog_list = rest_admin_url + "/v1/saleLog/list";
		om_business_saleLog_sendLog = rest_admin_url + "/v1/saleLog/sendLog";
		om_business_saleLogAnnotation_list = rest_admin_url + "/v1/saleLogAnnotation/listAnnotation";
		om_business_saleLogAnnotation_getDetail = rest_admin_url + "/v1/saleLogAnnotation/getDetail";
		om_business_saleLogAnnotation_updateAnnotation = rest_admin_url + "/v1/saleLogAnnotation/updateAnnotation";
		om_business_common_get_customer = rest_admin_url + "/v1/business/common/getJurisdictionUser";
		om_business_customer_page = rest_admin_url + "/v1/customer/page";
		om_business_material = rest_admin_url + "/v1/material";
		om_business_material_page = rest_admin_url + "/v1/material/page";

		dailypager_common_device_list = rest_admin_url + "/daily-pager/common/device/list";
		dailypager_common_getNowTime = rest_admin_url + "/daily-pager/common/getNowTime";
		dailypager_common_region_city = rest_admin_url + "/daily-pager/common/region/city";
		dailypager_common_region_area = rest_admin_url + "/daily-pager/common/region/area";
		dailypager_device_command_send_brightness = rest_admin_url + "/daily-pager/device/command/send/brightness";
		dailypager_device_command_send_log = rest_admin_url + "/daily-pager/device/command/send/log";
		dailypager_device_command_send_screenshot = rest_admin_url + "/daily-pager/device/command/send/screenshot";
		dailypager_device_command_send_server = rest_admin_url + "/daily-pager/device/command/send/server";
		dailypager_device_command_send_switch = rest_admin_url + "/daily-pager/device/command/send/switch";
		dailypager_device_command_send_volume = rest_admin_url + "/daily-pager/device/command/send/volume";
		dailypager_device_command_result_log = rest_admin_url + "/daily-pager/device/command/result/log";
		dailypager_device_command_result_screenshot = rest_admin_url + "/daily-pager/device/command/result/screenshot";

		rest_admin_api_bj_program_del = rest_admin_url + "/v1/api/program";
		rest_admin_api_bj_program_publish = rest_admin_url + "/v1/api/program/publish";
		rest_admin_api_bj_program_timeline = rest_admin_url + "/v1/api/program/timeline";
		rest_admin_api_bj_program_pause = rest_admin_url + "/v1/api/program/pause";
		rest_admin_api_bj_program_resume = rest_admin_url + "/v1/api/program/resume";
		rest_admin_api_bj_republish_by_media = rest_admin_url + "/v1/republish/media";
		rest_admin_api_bj_republish_by_program = rest_admin_url + "/v1/republish/program";
		rest_admin_api_bj_terminal_logs_list = rest_admin_url + "/v1/bj/terminal/logs/list";
		rest_admin_api_bj_command_filelist = rest_admin_url + "/v1/bj/terminal/command/filelist";
		rest_admin_api_bj_command_logfile = rest_admin_url + "/v1/bj/terminal/command/logfile";
		rest_admin_api_bj_command_screenshoot = rest_admin_url + "/v1/bj/terminal/command/screenshots";
		rest_admin_api_bj_command_onekeydtmb = rest_admin_url + "/v1/bj/terminal/command/dtmb";
		rest_admin_api_bj_command_extend = rest_admin_url + "/v1/bj/terminal/command/extend";
		rest_admin_api_bj_terminal_baseinfo_page = rest_admin_url + "/v1//terminal/baseInfoPage";
		rest_admin_api_bj_terminal_dynamicinfo_page = rest_admin_url + "/v1/terminal/dynamicInfoPage";
		rest_admin_api_bj_terminal_update_baseinfo = rest_admin_url + "/v1/terminal/update/baseInfo";
		rest_admin_api_bj_terminal_upgrade = rest_admin_url + "/v1/terminal/upgrade";
		rest_admin_api_bj_terminal_volume = rest_admin_url + "/v1/terminal/volume";

		rest_admin_api_ops_switch = rest_admin_url + "/v1/ops/terminal/switch";
		rest_admin_api_ops_terminal_info = rest_admin_url + "/v1/ops/terminal/terminalInfo";

		rest_admin_api_command_clear = rest_admin_url + "/v1/terminal/command/clear";
		rest_admin_api_command_clock = rest_admin_url + "/v1/terminal/command/clock";
		rest_admin_api_command_playlog = rest_admin_url + "/v1/terminal/command/playlog";
		rest_admin_api_command_switch = rest_admin_url + "/v1/terminal/command/switch";
		rest_admin_api_command_syslog = rest_admin_url + "/v1/terminal/command/syslog";
		rest_admin_api_command_upgrade = rest_admin_url + "/v1/terminal/command/upgrade";

		rest_admin_common_province_list = rest_admin_url + "/v1/province/list";
		rest_admin_common_city_list = rest_admin_url + "/v1/city/list";
		rest_admin_common_weather = rest_admin_url + "/v1/weather";
		rest_admin_common_dictionary = rest_admin_url + "/v1/sys/dictionary";
		rest_admin_common_dictionary_childrens = rest_admin_url + "/v1/sys/dictionary/childrens";

		// 模板相关
		rest_admin_template = rest_admin_url + "/v1/template";
		rest_admin_template_audit = rest_admin_url + "/v1/template/audit";
		rest_admin_template_page = rest_admin_url + "/v1/template/page";
		rest_admin_template_as_terminal = rest_admin_url + "/v1/template/terminal";
		rest_admin_template_as_terminal_list = rest_admin_url + "/v1/template/terminal/list";

		// 终端监控相关
		rest_admin_terminal_monitor_status = rest_admin_url + "/v1/terminal/status";
		rest_admin_terminal_monitor_clear_file = rest_admin_url + "/v1/terminal/status/clear/file";
		rest_admin_terminal_monitor_command_page = rest_admin_url + "/v1/terminal/status/command/page";
		rest_admin_terminal_monitor_dynamic_info = rest_admin_url + "/v1/terminal/status/dynamicInfo";
		rest_admin_terminal_monitor_log_page = rest_admin_url + "/v1/terminal/status/logs/page";
		rest_admin_terminal_monitor_status_page = rest_admin_url + "/v1/terminal/status/page";
		rest_admin_terminal_monitor_play = rest_admin_url + "/v1/terminal/status/play";
		rest_admin_terminal_monitor_printscreen = rest_admin_url + "/v1/terminal/status/printscreen";

		rest_admin_terminal_update_line_no = rest_admin_url + "/v1/terminal/updateLineNo";
		rest_admin_terminal_layout = rest_admin_url + "/v1/terminal/screen";

		rest_admin_terminal_command_clear = rest_admin_url + "/v1/bj/terminal/command/clear";

		// 获取天气信息相关接口
		rest_api_res_url = rest_api_url + "/v1/terminal/token";
		rest_api_weather_tgz_url = rest_api_url + "/v1/weather/baseInfo";
		// 终端管理相关
		rest_admin_terminal = rest_admin_url + "/v1/terminal";
		rest_admin_terminal_batch = rest_admin_url + "/v1/terminal/batch";
		rest_admin_terminal_audit = rest_admin_url + "/v1/terminal/audit";
		rest_admin_terminal_baseInfo = rest_admin_url + "/v1/terminal/baseInfo";
		rest_admin_terminal_page = rest_admin_url + "/v1/terminal/page";
		// 终端分组相关接口
		rest_admin_terminal_group = rest_admin_url + "/v1/terminal/group";
		rest_admin_terminal_group_list = rest_admin_url + "/v1/terminal/common/group/list";
		rest_admin_terminal_group_list_screen = rest_admin_url + "/v1/terminal/common/group/list/screen";
		// 标签相关
		rest_admin_tag = rest_admin_url + "/v1/tag";
		rest_admin_tag_page = rest_admin_url + "/v1/tag/page";
		rest_admin_tagcategory = rest_admin_url + "/v1/tagcategory";
		rest_admin_tagcategory_list = rest_admin_url + "/v1/tagcategory/list";
		rest_admin_terminal_group_tag = rest_admin_url + "/v1/terminal/group/tag";
		rest_admin_terminal_screen_tag = rest_admin_url + "/v1/terminal/screen/tag";
		rest_admin_terminal_screen_tag_batch = rest_admin_url + "/v1/terminal/screen/tag/batch";
		rest_admin_terminal_screen_tag_page = rest_admin_url + "/v1/terminal/screen/tag/page";
		// 节目管理
		rest_admin_program = rest_admin_url + "/v1/program";
		rest_admin_program_audit = rest_admin_url + "/v1/program/audit";
		rest_admin_program_page = rest_admin_url + "/v1/program/page";
		rest_admin_program_save_as = rest_admin_url + "/v1/program/save/as";
		rest_admin_program_xml = rest_admin_url + "/v1/program/xml";
		// 节目发布管理
		rest_admin_schedule = rest_admin_url + "/v1/schedule";
		rest_admin_schedule_audit = rest_admin_url + "/v1/schedule/audit";
		rest_admin_schedule_export_by_ids = rest_admin_url + "/v1/schedule/exportPageByIds";
		rest_admin_schedule_page = rest_admin_url + "/v1/schedule/page";
		rest_admin_schedule_remove = rest_admin_url + "/v1/schedule/removeProgram";
		rest_admin_schedule_revoke = rest_admin_url + "/v1/schedule/revoke";
		rest_admin_schedule_xml = rest_admin_url + "/v1/schedule/xml";

		rest_admin_media = rest_admin_url + "/v1/media";
		rest_admin_media_save = rest_admin_url + "/v1/media/save";
		rest_admin_media_page = rest_admin_url + "/v1/media/page";
		rest_admin_media_uploading = rest_admin_url + "/v1/media/uploading";

		rest_admin_media_audit = rest_admin_url + "/v1/media/audit/audit";
		rest_admin_media_audit_page = rest_admin_url + "/v1/media/audit/page";

		// 发布平台登录
		rest_admin_login = rest_admin_url + "/v1/sign/login";
		rest_admin_refresh_token = rest_admin_url + "/v1/sign/refreshToken";

		rest_api_advertisement = rest_api_url + "/v1/advertisement";
		if (corpCode.equals("SXRB")) {
			rest_api_dev_reg = rest_api_url + "/api/device/register";
		} else {
			rest_api_dev_reg = rest_api_url + "/v1/terminal/register";
		}

		rest_api_upload = rest_api_url + "/v1/upload";
		rest_api_textbullet = rest_api_url + "/v1/textbullet";
		rest_admin_api_text_bullet_publish = rest_admin_url + "/v1/api/text_bullet/publish";

		rest_api_shim = rest_api_url + "/v1/shim";
		rest_api_program = rest_api_url + "/v1/program";
	}

	/** 用户token， */
	private static String token = "";
	/** 是否使用固定token测试 */
	public static boolean IS_AUTHOR = true;

	/** 获取token */
	public static String getToken() {
		return token;
	}

	/** 设置token */
	public static void setToken(String token) {
		GlobalData.token = token;
	}

	/** 获取平台代码 */
	public static String getCorpCode() {
		return corpCode;
	}

	/** 设置平台代码 */
	public static void setCorpCode(String corpCode) {
		GlobalData.corpCode = corpCode;
	}

	/**
	 * 设置测试服务器类型代码，并初始化相关服务器地址
	 * 
	 * @param code 0，本地技术开发环境测试；
	 *  code 10，本地BJ测试；
	 *  code 11，本地CS测试；
	 *  code 12，UAT映射本地BJ测试；
	 *  code 13，本地TV测试；
	 *  code 14，UAT映射，TV测试；
	 *  code 20，UAT-CS测试；
	 *  code 22，UAT-BJ测试；
	 *  code 24，UAT-TV测试；
	 *  code 30，CS；
	 *  code 40，HF；
	 *  code 41，HF 新平台；
	 *  code 50，BJ；
	 *  code 60，TV，城市电视；
	 *  code 70，DY,山西日报；
	 *  code 80，QD；
	 */
	public static void setServerCodeAndInit(int code) {
		server_code = code;
		init(code);
		init_interface();
	}

	/** 全局MAC */
	public static String SS_MAC = "";

	/**
	 * 获取服务器代码
	 * 
	 * @return 服务器代码
	 */
	public static int get_server_code() {
		return server_code;
	}

	/** 是否使用固定mqtt服务器地址 */
	public static boolean IS_MQTT_STATIC_URL = false;
	/** 固定 mqtt 服务器地址 */
	public static String MQTT_STATIC_URL = "192.168.1.234";
	/** 固定 mqtt 端口地址 */
	public static String MQTT_STATIC_PORT = "3892";

	/** 是否使用mqtt管理员账号 */
	public static boolean IS_MQTT_ADMIN_ACCOUNT = false;
	/** mqtt管理员用户名 */
	public static String MQTT_ADMIN_USERNAME = "mqtt_system";
	/** mqtt管理员密码 */
	public static String MQTT_ADMIN_PASSWORD = "mqtt_system";

	/** 是否本地网络穿透到外网 */
	public static final boolean IS_NAT_TRAVERSE = false;
	/** 内网穿透时mqtt服务器地址(开启穿透，当服务器下发本地mqttt信息时使用) */
	public static final String base_mqtt_url = "tcp://124.232.158.120:8008";

	/** 是否开启本地网络代理 */
	public static boolean HTTP_PROXY_STATUS = false;
	public static String HTTP_PROXY_HOST = "192.168.1.249";
	public static int HTTP_PROXY_PORT = 8888;

	/** 是否下载节目数据 */
	public static boolean download_promgram_xml = false;
	/** 是否下载媒体素材 */
	public static boolean download_media_file = false;

	/** 当前是否办公室网络，用于控制数据库读取终端SN */
	public static boolean IS_OFFICE_NETWORK = true;

	/**
	 * 设置下载节目数据开关
	 * 
	 * @param b 是否下载保存节目xml
	 */
	public static void set_download_promgram_xml(boolean b) {
		download_promgram_xml = b;
	}

	/**
	 * 设置下载媒体数据开关
	 * 
	 * @param b 是否下载媒体素材文件
	 */
	public static void set_download_media_file(boolean b) {
		download_media_file = b;
	}

	/** 是否使用固定SN测试 */
	public static boolean IS_TEST_SN = false;
	/** 测试用SN */
	public static String TEST_SN = "12345";

	/**
	 * 测试已存在SN终端。
	 * 
	 * @param sn 待测试的SN码
	 * @param is 是否开启sn测试。开启后直接使用该SN值测试。
	 */
	public static void set_test_sn_info(String sn, boolean is) {
		TEST_SN = sn;
		IS_TEST_SN = is;
	}

	/** 开启屏幕状态测试，随机状态 */
	public static boolean TEST_SCREEN_STATUS = false;
	/** 屏幕状态信息 */
	public static JSONArray screen_monitor = new JSONArray();

	/** 屏幕方向，1 横屏，2 竖屏 */
	public static int screen_direction = 2;

	/**
	 * 设置物理屏485状态
	 * @param s_b 屏幕地址码11的状态
	 * @param s_c 屏幕地址码12的状态
	 * @param s_d 屏幕地址码13的状态
	 * @param s_e 屏幕地址码14的状态
	 * @param s_f 屏幕地址码15的状态
	 * */
	public static JSONArray set_screen_monitors(int s_b,int s_c,int s_d,int s_e,int s_f) {
		// 设置屏幕状态码
		JSONArray monitors = new JSONArray();
		String[] screen_addr = { "b", "c", "d", "e", "f" };

		Map<String, String> screen_status = new HashMap<>();
		if(s_b >= 0){
			screen_status.put("b", "" + s_b);
		}
		if(s_c >= 0){
			screen_status.put("c", "" + s_c);
		}
		if(s_d >= 0){
			screen_status.put("d", "" + s_d);
		}
		if(s_e >= 0){
			screen_status.put("e", "" + s_e);
		}
		if(s_f >= 0){
			screen_status.put("f", "" + s_f);
		}

		for (int i = 0; i < screen_status.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("addr", screen_addr[i]);
			obj.put("status", screen_status.get(screen_addr[i]));
			monitors.add(obj);
		}
		return monitors;
	}

	/**
	 * 随机设置物理屏485状态
	 * @return 返回物理屏状态对象
	 */
	public static JSONArray set_screen_monitors() {
		Random rm = new Random();
//		int[] screen_status_code = {0,1,2,3,4,50};
		int[] screen_status_code = {0,50};
		int len = screen_status_code.length;

		int b = screen_status_code[rm.nextInt(len)];
		int c = screen_status_code[rm.nextInt(len)];
		int d = screen_status_code[rm.nextInt(len)];
		int e = screen_status_code[rm.nextInt(len)];
		int f = screen_status_code[rm.nextInt(len)];
		return set_screen_monitors(b,c,d,e,f);
	}

	/** 终端分组根分组 */
	public static int TERMINAL_GROUP_ID_DEFAULT = -2;

	/** data目录路径 */
	public static String DATA_PATH = "./data";

	/** 发布单xml文件路径 */
	public static String schedule_path = "./data/schedule.xml";

	public static String TEST_TERMINAL_MODEL = "GTC2021";

	public static String get_rest_admin_url(){
		return rest_admin_url;
	}
	public static String get_rest_api_url(){
		return rest_api_url;
	}


	public static boolean POST_MAP_PARAM = false;

}
