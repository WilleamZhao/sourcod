package org.sourcod.emnus;

import com.sourcod.model.ResponseData;
import com.sourcod.util.Tool12306Util;

/**
 * ErrorEmnu
 *
 * @author willeam
 * @time 16-8-31 下午8:04
 */
public enum ErrorEmnu {

    ERROR_PROPERTY_FORMAT("0", "00000", "操作成功", "操作成功"),
    ERROR_PROPERTY_VALUE("0", "00000", "操作成功", "操作成功"),
    ERROR_PROPERTY_PARSE("0", "00000", "操作成功", "操作成功"),
    ERROR_PROPERTY_NULL("0", "00000", "操作成功", "操作成功"),
    ERROR_PROPERTY_EXCEPTION("0", "00000", "操作成功", "操作成功"),
    ERROR_PARAMETER_VALUE("0", "00000", "操作成功", "操作成功"),
    ERROR_PARAMETER_FORMAT("0", "00000", "操作成功", "操作成功"),
    ERROR_PARAMETER_PARSE("0", "00000", "操作成功", "操作成功"),
    ERROR_PARAMETER_NULL("0", "00000", "操作成功", "操作成功"),
    ERROR_PARAMETER_EXCEPTION("0", "00000", "操作成功", "操作成功"),
    
    SUCCESS_ENUM("0", "00000", "操作成功", "操作成功"),

	FAIL_ENUM("0", "11111", "操作失败", "操作失败"),

	PARAM_RADNCODE_NULL_ENUM("1", "12001", "验证码为空", "验证码为空"),
	
	PARAM_COOKIE_NULL_ENUM("1", "12002", "cookie为空", "cookie为空"),
	
	PARAM_USERID_NULL_ENUM("1", "12003", "用户ID为空", "用户ID为空"),
	
	PARAM_URL_NULL_ENUM("1", "12004", "URL为空", "URL为空"),
	
	PARAM_SECRETSTR_NULL_ENUM("1", "12005", "SECRETSTR令牌为空", "SECRETSTR令牌为空"),
	
	PARAM_TRAINDATE_NULL_ENUM("1", "12006", "出发日期为空", "出发日期为空"),
	
	PARAM_BACKTRAINDATE_NULL_ENUM("1", "12007", "返程日期为空", "返程日期为空"),
	
	PARAM_TOURFLAG_NULL_ENUM("1", "12008", "单程返程信息为空", "单程返程信息为空"),
	
	PARAM_PURPOSECODES_NULL_ENUM("1", "12009", "车票类型为空", "车票类型为空"),
	
	PARAM_QUERYFROMSTATIONNAME_NULL_ENUM("1", "12010", "出发地址为空", "出发地址为空"),
	
	PARAM_QUERYTODSTATIONNAME_NULL_ENUM("1", "12011", "返程地址为空", "返程地址为空"),
	
	PARAM_TOKEN_NULL_ENUM("1", "12012", "TOKEN为空", "TOKEN为空"),
	
	PARAM_KEYCHECKISCHANGE_NULL_ENUM("1", "12013", "KEYCHECKISCHANGE令牌为空", "KEYCHECKISCHANGE令牌为空"),
	
	PARAM_LEFTTICKETSTR_NULL_ENUM("1", "12014", "LEFTTICKETSTR令牌为空", "LEFTTICKETSTR令牌为空"),
	
	PARAM_TRAINLOCATION_NULL_ENUM("1", "12015", "火车位置为空", "火车位置为空"),

	PARAM_OLDPASSENGERSTR_NULL_ENUM("1", "12016", "乘客信息为空", "乘客信息为空"),

	PARAM_PASSENGERTICKETSTR_NULL_ENUM("1", "12017", "乘客详细购票信息为空", "乘客详细购票信息为空"),
	
	PARAM_BEDLEVELORDERNUM_NULL_ENUM("1", "12018", "座位等级为空", "座位等级为空"),
	
	PARAM_CANCELFLAG_NULL_ENUM("1", "12019", "取消标志为空", "取消标志为空"),
	
	PARAM_STATIONTRAINCODE_NULL_ENUM("1", "12020", "车次为空", "车次为空"),
	
	PARAM_SEATTYPE_NULL_ENUM("1", "12021", "座位类型为空", "座位类型为空"),
	
	PARAM_FROMSTATIONTELECODE_NULL_ENUM("1", "12022", "始发站代码为空", "始发站代码为空"),
	
	PARAM_TOSTATIONTELECODE_NULL_ENUM("1", "12023", "终点站代码为空", "终点站代码为空"),
	
	PARAM_COOKIEKEY_NULL_ENUM("1", "12023", "cookieKey为空", "cookieKey为空"),
	
	FAIL_SUBMIT_ORDER_ENUM("2", "12001", "提交订单失败", "提交订单失败"),

	FAIL_VALIDATE_CODE_CONVERT_ENUM("2", "12002", "转换验证码失败", "转换验证码失败"),
	
	FAIL_VALIDATE_CODE_ENUM("2", "12003", "验证码验证失败", "验证码验证失败"),
	
	FAIL_CHECK_ORDER_ENUM("2", "12004", "检查订单失败", "检查订单失败"),
	
	FAIL_CONFIRM_ORDER_ENUM("2", "12005", "确认订单失败", "确认订单失败"),
	
	FAIL_CHECK_CONFIRM_ORDER_ENUM("2", "12006", "查看是否下单成功失败", "查看是否下单成功失败"),
	
	ERR_SUBMIT_ORDER_ENUM("3", "13001", "提交订单错误", "提交订单错误"),
	
	ERR_VALIDATE_ORDER_INFO_ENUM("3", "13002", "检查订单信息错误", "检查订单信息错误"),
	
	ERR_VALIDATE_ORDER_ENUM("3", "13003", "检查订单错误", "检查订单错误"),
	
	ERR_QUEUE_COUNT_ENUM("3", "13004", "检查排队错误", "检查排队错误"),
	
	ERR_CONFIRM_SINGLE_ENUM("3", "13005", "确认订单错误", "确认订单错误"),
	
	ERR_DATA_NULL_ENUM("2", "13006", "获取信息为空", "获取信息为空");

	

	/**
	 * 调用结果
	 */
	private String callStatus;
	/**
	 * 错误码
	 */
	private String errorCode;
	/**
	 * 错误名称
	 */
	private String errorName;
	/**
	 * 错误描述
	 */
	private String errorDesc;

	private ErrorEmnu(String callStatus, String errorCode,
			String errorName, String errorDesc) {
		this.callStatus = callStatus;
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
		this.errorName = errorName;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}

	/**
	 * 获取返回数据
	 * 
	 * @param em
	 * @return
	 */

	public static String getResponseData(ErrorEmnu em) {
		ResponseData res = new ResponseData();
		res.setCallStatus(em.getCallStatus());
		res.setReturnCode(em.getErrorCode());
		res.setReturnName(em.getErrorName());
		res.setReturnDesc(em.getErrorDesc());
        String resStr = Tool12306Util.objectToJson(res);
		return resStr;
	}

}














