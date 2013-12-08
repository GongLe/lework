package org.lework.core.common.taglib;

import org.lework.core.common.AppConfigConstant;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * 自定义jsp2.0标签，封装html link stylesheet标签。
 * <p>
 * 属性说明：
 * src 必填
 * type 非必填 默认值 text/css
 * media 非必填 默认值：all
 * rel 非必填 默认值：stylesheet
 * </p>
 * User:Gongle
 * Date: 2013-10-01 18:51:48
 */
public class AssetStylesheet extends SimpleTagSupport implements ITagResourceMeta {
    public static final String DEFAULT_TYPE = "text/css";
    public static final String DEFAULT_PATH_SYMBOL = "/";
    public static final String DEFAULT_MEDIA = "all";
    public static final String DEFAULT_OUT = "<link href=\"%s\" type=\"%s\" rel=\"stylesheet\" media=\"%s\" />";
    private String src;
    private String type=DEFAULT_TYPE;
    private String media = DEFAULT_MEDIA ;
    private BundleStylesheet parent;

    @Override
    public void doTag() throws JspException, IOException {

        if (getParent() != null && getParent() instanceof BundleStylesheet) {
            //添加到父标签@see AssetBundle
            parent = (BundleStylesheet) getParent();
            parent.addStylesheet(this);
        } else {
            //输出<link/>到页面
            getJspContext().getOut().write(buildOut());
        }

    }

    public String buildOut() {
        return String.format(DEFAULT_OUT, getRealPath(), getType(),   getMedia());
    }

    private String getRealPath() {
        return AppConfigConstant.CTX + (getSrc().startsWith(DEFAULT_PATH_SYMBOL) ? getSrc() : DEFAULT_PATH_SYMBOL + getSrc());
    }


    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }


}
