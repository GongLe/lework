package org.lework.core.common.taglib;

import org.lework.runner.utils.Collections3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义jsp2.0标签,绑定标签
 * 绑定同类型子标签css到单独文件
 * <p>
 * 属性说明：
 * name
 * </p>
 * User:Gongle
 * Date: 2013-10-01 18:51:48
 */
public class BundleStylesheet extends BodyTagSupport {
    private static Logger logger = LoggerFactory.getLogger(BundleStylesheet.class);
    public static final String DEFAULT_PATH_SYMBOL = "/";

    private String name;
    private String media="all";
    private List<AssetStylesheet> stylesheets = new ArrayList<AssetStylesheet>();

    @Override
    public int doAfterBody() throws JspException {
        BodyContent bc = getBodyContent();
        final JspWriter out = bc.getEnclosingWriter();
        if(Collections3.isNotEmpty(stylesheets)){

        }
        return SKIP_BODY;
    }

    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //添加一个StyleSheet标签
    public void addStylesheet(AssetStylesheet stylesheet) {
        stylesheets.add(stylesheet);
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
