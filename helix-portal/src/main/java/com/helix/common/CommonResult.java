package com.helix.common;

public class CommonResult {
    private int code;
    private String message;
    private Object data;

    public CommonResult() {}
    public CommonResult(int code, String message, Object data) {
        this.code = code; this.message = message; this.data = data;
    }
    public static CommonResult success(Object data) { return new CommonResult(200, "ok", data); }
    public static CommonResult failed(String message) { return new CommonResult(500, message, null); }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}
