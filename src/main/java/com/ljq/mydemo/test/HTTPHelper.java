package com.ljq.mydemo.test;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


@Slf4j
public class HTTPHelper {

	//private static final log log = log.getlog(HTTPHelper.class);
	// private static String user_agent = "";

	private static final int http_connect_timeout = 5000;
	private static final int http_read_timeout = 120000;
	private static final String http_charset = "utf-8";

	public static String upload_file_of_post(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {
		return upload_file_of_post(urlStr,textMap,fileMap,false,0);
	}

	public static String upload_file_of_post(String urlStr, Map<String, String> textMap, Map<String, String> fileMap,boolean append,int position) {
		String res = "";
		HttpURLConnection conn = null;
		DataInputStream in = null;
		RandomAccessFile raf = null;
		// boundary就是request头和上传文件内容的分隔符
		String BOUNDARY = "--www123821742118716";
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(http_connect_timeout);
			conn.setReadTimeout(http_read_timeout);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			OutputStream out = new DataOutputStream(conn.getOutputStream());

			// text
			if (textMap != null) {
				StringBuffer strBuf = new StringBuffer();
				for (Entry<String, String> entry : textMap.entrySet()) {
					String inputName = entry.getKey();
					String inputValue = entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"").append(inputName).append("\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// file
			if (fileMap != null) {
				for (Entry<String, String> entry : fileMap.entrySet()) {
					String inputName = entry.getKey();
					String inputValue = entry.getValue();
					if (inputValue == null) {
						continue;
					}
					File file = new File(inputValue);
					String filename = file.getName();

					// 没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
					String contentType = new MimetypesFileTypeMap().getContentType(file);
					// contentType非空采用filename匹配默认的图片类型
					if (!"".equals(contentType)) {
						if (filename.endsWith(".png")) {
							contentType = "image/png";
						} else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")
								|| filename.endsWith(".jpe")) {
							contentType = "image/jpeg";
						} else if (filename.endsWith(".gif")) {
							contentType = "image/gif";
						} else if (filename.endsWith(".ico")) {
							contentType = "image/image/x-icon";
						}
					}
					if (contentType == null || "".equals(contentType)) {
						contentType = "application/octet-stream";
					}
					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"").append(inputName).append("\"; filename=\"").append(filename).append("\"\r\n");
					strBuf.append("Content-Type:").append(contentType).append("\r\n\r\n");
					out.write(strBuf.toString().getBytes());

					if(append){
						raf = new RandomAccessFile(entry.getValue(), "rw");
					}else{
						in = new DataInputStream(new FileInputStream(file));
					}

					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}
			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();
			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line ;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
		} catch (Exception e) {
			log.error("send POST exception. " + urlStr);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return res;
	}

	/**
	 * 从网络Url中下载文件
	 * 
	 * @param urlStr  文件路径
	 * @param fileName  文件名称
	 * @param savePath  文件保存路径
	 * @throws IOException  异常
	 */
	public static void downLoadFromUrl(String urlStr, String fileName, String savePath, String token)
			throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36)");

		log.info("文件地址：" + urlStr);
		// 得到输入流
		InputStream inputStream = conn.getInputStream();
		// 获取自己数组
		byte[] getData = readInputStream(inputStream);

		// 文件保存位置
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		File file = new File(saveDir + File.separator + fileName);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(getData);

		fos.close();
		inputStream.close();
		log.info("download success. url = " + urlStr);
	}

	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream  读取的目标输入流
	 * @return  return
	 * @throws IOException  异常
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	/**
	 * 支持的Http method
	 *
	 */
	private enum HttpMethod {
		POST, DELETE, GET, PUT, HEAD;
	};

	private static String do_request(String url, String paramsStr, Map<String,String> headers, int connectTimeout,
									 int readTimeout, String encoding, HttpMethod method,boolean postOfMap) {
		URL uUrl;
		HttpURLConnection conn = null;
		BufferedWriter out = null;
		BufferedReader in = null;

		if (connectTimeout == 0) {
			connectTimeout = http_connect_timeout;
		}
		if (readTimeout == 0) {
			readTimeout = http_read_timeout;
		}
		if ((encoding == null) || (encoding.equals(""))) {
			encoding = http_charset;
		}

		try {
			// 创建和初始化连接
			uUrl = new URL(url);
			if (GlobalData.HTTP_PROXY_STATUS) {
				InetSocketAddress address = new InetSocketAddress(GlobalData.HTTP_PROXY_HOST, GlobalData.HTTP_PROXY_PORT);
				Proxy proxy = new Proxy(Proxy.Type.HTTP, address); // HTTP代理
				conn = (HttpURLConnection) uUrl.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) uUrl.openConnection();
			}
			conn.setRequestMethod(method.toString());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 设置连接超时时间
			conn.setConnectTimeout(connectTimeout);
			// 设置读取超时时间
			conn.setReadTimeout(readTimeout);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
			// 指定请求header参数
			if ((headers != null) && (headers.size() > 0)) {
				Set<String> headerSet = headers.keySet();
				for (String key : headerSet) {
					conn.setRequestProperty(key, headers.get(key));
				}
			}

			conn.setRequestProperty("Authorization", GlobalData.getToken());

			if ( method == HttpMethod.POST  && !postOfMap) {
				// 发送请求参数
				out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), encoding));
				out.write(paramsStr);
				out.flush();
			}

			// Map<String, List<String>> map = conn.getHeaderFields();
			// // 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// // log.info(key + " : " + map.get(key));
			// }

			// 接收返回结果
			StringBuilder result = new StringBuilder();
			int rsp_code = conn.getResponseCode();
			// 读取响应
			if (rsp_code == HttpURLConnection.HTTP_NOT_FOUND) {
				log.error("404 not found!");

				return "";
			} else if ((rsp_code == HttpURLConnection.HTTP_OK) || (rsp_code == HttpURLConnection.HTTP_CREATED)
					|| (rsp_code == HttpURLConnection.HTTP_ACCEPTED)) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
			} else {
				in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), encoding));
			}
			if (in != null) {
				String line = "";
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
			}
			return result.toString();
		} catch (Exception e) {
			log.error("use interface [" + url + "] fail. request url ：" + url + ", params: " + paramsStr, e);
			return "";
			// 处理错误流，提高http连接被重用的几率
			// try {
			// byte[] buf = new byte[100];
			// InputStream es = conn.getErrorStream();
			// if (es != null) {
			// while (es.read(buf) > 0) {
			// ;
			// }
			// es.close();
			// }
			// } catch (Exception e1) {
			// e1.printStackTrace();
			// }
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 关闭连接
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	/**
	 * HTTP 请求(参数MAP)
	 * @param url            请求地址
	 * @param params         请求参数
	 * @param headers        请求头参数
	 * @param connectTimeout 连接超时时间
	 * @param readTimeout    读取数据流超时时间
	 * @param encoding       编码
	 * @param method         请求类型
	 * @param postOfMap      是否post方法参数为map类型
	 * @return  return
	 */
	private static String invokeUrl1(String url, Map<String, String> params, Map<String, String> headers,
									 int connectTimeout, int readTimeout, String encoding, HttpMethod method, boolean postOfMap) {

		// log.info("request api: " + url);
		StringBuilder paramsStr = new StringBuilder();
		if (params.size() > 0) {
			paramsStr = new StringBuilder();
			Set<Entry<String, String>> entries = params.entrySet();
			for (Entry<String, String> entry : entries) {

				if (entry.getKey().equals("ids_key")) {
					continue;
				}
				String value = (entry.getValue() != null) ? (entry.getValue()) : "";
				if (entry.getKey().equals("ids")) {
					String[] ids = value.split(",");
					String _key = entry.getKey();
					if (params.containsKey("ids_key")) {
						_key = params.get("ids_key");
					}
					for (String id : ids) {
						paramsStr.append(_key).append("=").append(id).append("&");
					}
				} else {
					paramsStr.append(entry.getKey()).append("=").append(value).append("&");
				}
			}

			paramsStr.replace(paramsStr.length() - 1, paramsStr.length(), "");

			// 只有POST方法才能通过OutputStream(即form的形式)提交参数
			if(postOfMap){
				url += "?" + paramsStr;
			}else if (method != HttpMethod.POST) {
				url += "?" + paramsStr;
			}
			// log.info("请求params: " + paramsStr.toString());
		}

		log.info("请求url: " + url + " ,参数params：" + paramsStr);

//		String params_encode = "";
//		try{
//			params_encode = URLEncoder.encode(paramsStr.toString(),"UTF-8");
//		}catch(Exception e){
//			e.printStackTrace();
//		}

		return do_request(url,paramsStr.toString(),headers,connectTimeout,readTimeout,encoding,method,postOfMap);
	}

	/**
	 * HTTP 请求(参数JSON)
	 * @param url            请求地址
	 * @param params         请求参数
	 * @param headers        请求头参数
	 * @param connectTimeout 连接超时时间
	 * @param readTimeout    读取数据流超时时间
	 * @param encoding       编码
	 * @param method         请求类型
	 * @param postOfMap      是否post方法参数为map类型
	 * @return return
	 */
	private static String invokeUrl2(String url, JSONObject params, Map<String, String> headers, int connectTimeout,
                                     int readTimeout, String encoding, HttpMethod method, boolean postOfMap) {

		log.info("request url = " + url + ", params = " + params.toString());

		return do_request(url, params.toString(),headers,connectTimeout,readTimeout,encoding,method,postOfMap);
	}


	public static String post_map(String url, Map<String, String> params, Map<String, String> headers) {
		return invokeUrl1(url, params, headers, http_connect_timeout, http_read_timeout, "UTF-8", HttpMethod.POST, true);
	}

	public static String post_map(String url, Map<String, String> params) {
		return invokeUrl1(url, params, new HashMap<>(), http_connect_timeout, http_read_timeout, "UTF-8",
				HttpMethod.POST, true);
	}


	public static String post(String url, JSONObject params, Map<String, String> headers, int connectTimeout,
			int readTimeout, String charset) {
		return invokeUrl2(url, params, headers, connectTimeout, readTimeout, charset, HttpMethod.POST, false);
	}

	public static String post(String url, JSONObject params) {
		return invokeUrl2(url, params, new HashMap<>(), http_connect_timeout, http_read_timeout, "UTF-8",
				HttpMethod.POST, false);
	}

	public static String post(String url, JSONObject params, Map<String, String> headers) {
		return invokeUrl2(url, params, headers, http_connect_timeout, http_read_timeout, "UTF-8", HttpMethod.POST, false);
	}

	public static String post(String url, JSONObject params, Map<String, String> headers, String token) {
		return invokeUrl2(url, params, headers, http_connect_timeout, http_read_timeout, "UTF-8", HttpMethod.POST,
				false);
	}

	public static String post(String url, JSONObject params, String token) {
		return invokeUrl2(url, params, new HashMap<>(), http_connect_timeout, http_read_timeout, "UTF-8",
				HttpMethod.POST, false);
	}
//
//	/**
//	 * 上传文件
//	 *
//	 * @param urlStr  接口地址
//	 * @param textMap 参数
//	 * @param fileMap 文件
//	 * @return
//	 */
//	public static String post_file(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {
//		String res = "";
//		HttpURLConnection conn = null;
//		// boundary就是request头和上传文件内容的分隔符
//		String BOUNDARY = "--www123821742118716";
//		try {
//			URL url = new URL(urlStr);
//			if (GlobalData.HTTP_PROXY_STATUS) {
//				InetSocketAddress addr = new InetSocketAddress(GlobalData.HTTP_PROXY_HOST, GlobalData.HTTP_PROXY_PORT);
//				Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // HTTP代理
//				conn = (HttpURLConnection) url.openConnection(proxy);
//			} else {
//				conn = (HttpURLConnection) url.openConnection();
//			}
//			conn.setConnectTimeout(5000);
//			conn.setReadTimeout(30000);
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setUseCaches(false);
//			conn.setRequestMethod(HttpMethod.POST.toString());
//			conn.setRequestProperty("Authorization", GlobalData.getToken());
//			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
//			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
//			OutputStream out = new DataOutputStream(conn.getOutputStream());
//
//			// text
//			if (textMap != null) {
//				StringBuffer strBuf = new StringBuffer();
//				Iterator<Entry<String, String>> iter = textMap.entrySet().iterator();
//				while (iter.hasNext()) {
//					Map.Entry<String, String> entry_text = (Map.Entry<String, String>) iter.next();
//					String inputName = entry_text.getKey();
//					String inputValue = entry_text.getValue();
//					if (inputValue == null) {
//						continue;
//					}
//					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
//					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
//					strBuf.append(inputValue);
//				}
//				out.write(strBuf.toString().getBytes());
//			}
//
//			// file
//			if (fileMap != null) {
//				Iterator<Entry<String, String>> iter = fileMap.entrySet().iterator();
//				while (iter.hasNext()) {
//					Map.Entry<String, String> entry = iter.next();
//					String inputName = entry.getKey();
//					String inputValue = entry.getValue();
//					if (inputValue == null) {
//						continue;
//					}
//					File file = new File(inputValue);
//					String filename = file.getName();
//
//					// 没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
//					String contentType = new MimetypesFileTypeMap().getContentType(file);
//					// contentType非空采用filename匹配默认的图片类型
//					if (!"".equals(contentType)) {
//						if (filename.endsWith(".png")) {
//							contentType = "image/png";
//						} else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")
//								|| filename.endsWith(".jpe")) {
//							contentType = "image/jpeg";
//						} else if (filename.endsWith(".gif")) {
//							contentType = "image/gif";
//						} else if (filename.endsWith(".ico")) {
//							contentType = "image/image/x-icon";
//						}
//					}
//					if (contentType == null || "".equals(contentType)) {
//						contentType = "application/octet-stream";
//					}
//					StringBuffer strBuf = new StringBuffer();
//					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
//					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename
//							+ "\"\r\n");
//					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
//					out.write(strBuf.toString().getBytes());
//					DataInputStream in = new DataInputStream(new FileInputStream(file));
//					int bytes = 0;
//					byte[] bufferOut = new byte[1024];
//					while ((bytes = in.read(bufferOut)) != -1) {
//						out.write(bufferOut, 0, bytes);
//					}
//					in.close();
//				}
//			}
//			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
//			out.write(endData);
//			out.flush();
//			out.close();
//			// 读取返回数据
//			StringBuffer strBuf = new StringBuffer();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				strBuf.append(line).append("\n");
//			}
//			res = strBuf.toString();
//			reader.close();
//			reader = null;
//		} catch (Exception e) {
//			log.error("uploading file exception. " + urlStr);
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				conn.disconnect();
//				conn = null;
//			}
//		}
//		return res;
//	}

	/**
	 * GET方法提交Http请求，语义为“查询”
	 * 
	 * @param url            资源路径（如果url中已经包含参数，则params应该为null）
	 * @param params         参数
	 * @param connectTimeout 连接超时时间（单位为ms）
	 * @param readTimeout    读取超时时间（单位为ms）
	 * @param charset        字符集（一般该为“utf-8”）
	 * @return return
	 */
	public static String get(String url, Map<String, String> params, int connectTimeout, int readTimeout,
			String charset) {
		return invokeUrl1(url, params, new HashMap<>(), connectTimeout, readTimeout, charset,
				HttpMethod.GET, false);
	}

	public static String get(String url, Map<String, String> params) {
		return invokeUrl1(url, params, new HashMap<>(), 0, 0, "", HttpMethod.GET, false);
	}

	/**
	 * GET方法提交Http请求，语义为“查询”
	 * 
	 * @param url            资源路径（如果url中已经包含参数，则params应该为null）
	 * @param params         参数
	 * @param headers        请求头参数
	 * @param connectTimeout 连接超时时间（单位为ms）
	 * @param readTimeout    读取超时时间（单位为ms）
	 * @param charset        字符集（一般该为“utf-8”）
	 * @return return
	 */
	public static String get(String url, Map<String, String> params, Map<String, String> headers, int connectTimeout,
			int readTimeout, String charset) {
		return invokeUrl1(url, params, headers, connectTimeout, readTimeout, charset, HttpMethod.GET, false);
	}

	public static String get(String url, Map<String, String> params, Map<String, String> headers) {
		return invokeUrl1(url, params, headers, 0, 0, "", HttpMethod.GET, false);
	}

	/**
	 * PUT方法提交Http请求，语义为“更改” <br/>
	 * 注意：PUT方法也是使用url提交参数内容而非body，所以参数最大长度收到服务器端实现的限制，Resin大概是8K
	 * 
	 * @param url            资源路径（如果url中已经包含参数，则params应该为null）
	 * @param params         参数
	 * @param connectTimeout 连接超时时间（单位为ms）
	 * @param readTimeout    读取超时时间（单位为ms）
	 * @param charset        字符集（一般该为“utf-8”）
	 * @return return
	 */
	public static String put(String url, Map<String, String> params, int connectTimeout, int readTimeout,
			String charset) {
		return invokeUrl1(url, params, new HashMap<>(), connectTimeout, readTimeout, charset,
				HttpMethod.PUT, false);
	}

	/**
	 * PUT方法提交Http请求，语义为“更改” <br/>
	 * 注意：PUT方法也是使用url提交参数内容而非body，所以参数最大长度收到服务器端实现的限制，Resin大概是8K
	 * 
	 * @param url            资源路径（如果url中已经包含参数，则params应该为null）
	 * @param params         参数
	 * @param headers        请求头参数
	 * @param connectTimeout 连接超时时间（单位为ms）
	 * @param readTimeout    读取超时时间（单位为ms）
	 * @param charset        字符集（一般该为“utf-8”）
	 * @return return
	 */
	public static String put(String url, Map<String, String> params, Map<String, String> headers, int connectTimeout,
			int readTimeout, String charset) {
		return invokeUrl1(url, params, headers, connectTimeout, readTimeout, charset, HttpMethod.PUT, false);
	}

	public static String put(String url, JSONObject params, Map<String, String> headers, int connectTimeout,
			int readTimeout, String charset) {
		return invokeUrl2(url, params, headers, connectTimeout, readTimeout, charset, HttpMethod.PUT, false);
	}

	public static String put(String url, JSONObject params, Map<String, String> headers) {
		return invokeUrl2(url, params, headers, 0, 0, "", HttpMethod.PUT, false);
	}

	public static String put(String url, JSONObject params) {
		return invokeUrl2(url, params, new HashMap<String, String>(), 0, 0, "", HttpMethod.PUT, false);
	}


	public static String delete(String url, Map<String, String> params, Map<String, String> headers) {
		return invokeUrl1(url, params, headers, 0, 0, "", HttpMethod.DELETE, false);
	}

	public static String delete(String url, Map<String, String> params) {
		return invokeUrl1(url, params, new HashMap<String, String>(), 0, 0, "", HttpMethod.DELETE, false);
	}


	/**
	 * DELETE方法提交Http请求，语义为“删除”
	 * 
	 * @param url            资源路径（如果url中已经包含参数，则params应该为null）
	 * @param params         参数
	 * @param connectTimeout 连接超时时间（单位为ms）
	 * @param readTimeout    读取超时时间（单位为ms）
	 * @param charset        字符集（一般该为“utf-8”）
	 * @return return
	 */
	public static String delete(String url, Map<String, String> params, int connectTimeout, int readTimeout,
			String charset) {
		return invokeUrl1(url, params, new HashMap<String, String>(), connectTimeout, readTimeout, charset,
				HttpMethod.DELETE, false);
	}

	/**
	 * DELETE方法提交Http请求，语义为“删除”
	 * 
	 * @param url            资源路径（如果url中已经包含参数，则params应该为null）
	 * @param params         参数
	 * @param headers        请求头参数
	 * @param connectTimeout 连接超时时间（单位为ms）
	 * @param readTimeout    读取超时时间（单位为ms）
	 * @param charset        字符集（一般该为“utf-8”）
	 * @return return
	 */
	public static String delete(String url, Map<String, String> params, Map<String, String> headers, int connectTimeout,
			int readTimeout, String charset) {
		return invokeUrl1(url, params, headers, connectTimeout, readTimeout, charset, HttpMethod.DELETE, false);
	}

	/**
	 * HEAD方法提交Http请求，语义同GET方法 <br/>
	 * 跟GET方法不同的是，用该方法请求，服务端不返回message body只返回头信息，能节省带宽
	 * 
	 * @param url            资源路径（如果url中已经包含参数，则params应该为null）
	 * @param params         参数
	 * @param connectTimeout 连接超时时间（单位为ms）
	 * @param readTimeout    读取超时时间（单位为ms）
	 * @param charset        字符集（一般该为“utf-8”）
	 * @return return
	 */
	public static String head(String url, Map<String, String> params, int connectTimeout, int readTimeout,
			String charset) {
		return invokeUrl1(url, params, null, connectTimeout, readTimeout, charset, HttpMethod.HEAD, false);
	}

	/**
	 * HEAD方法提交Http请求，语义同GET方法 <br/>
	 * 跟GET方法不同的是，用该方法请求，服务端不返回message body只返回头信息，能节省带宽
	 * 
	 * @param url            资源路径（如果url中已经包含参数，则params应该为null）
	 * @param params         参数
	 * @param headers        请求头参数
	 * @param connectTimeout 连接超时时间（单位为ms）
	 * @param readTimeout    读取超时时间（单位为ms）
	 * @param charset        字符集（一般该为“utf-8”）
	 * @return return
	 */
	public static String head(String url, Map<String, String> params, Map<String, String> headers, int connectTimeout,
			int readTimeout, String charset) {
		return invokeUrl1(url, params, headers, connectTimeout, readTimeout, charset, HttpMethod.HEAD, false);
	}

}
