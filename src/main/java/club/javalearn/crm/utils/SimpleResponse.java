package club.javalearn.crm.utils;

/**
 * 简单响应的封装类
 *
 * @author king-pan
 * @date 2018-06-04
 **/
public class SimpleResponse {

    public SimpleResponse(Object content){
        this.content = content;
    }

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
