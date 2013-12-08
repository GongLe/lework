package org.lework.core.common.taglib;

import org.lework.core.common.AppConfigConstant;
import org.lework.runner.utils.Collections3;
import org.lework.runner.utils.IOUtil;
import org.lework.runner.utils.Strings;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义jsp2.0标签,绑定标签
 * 绑定同类型子标签 javascript到单独文件
 * <p>
 * 属性说明：
 * name 名称
 * charset	charset	规定在外部脚本文件中使用的字符编码。	STF
 * src	URL	规定外部脚本文件的 URL。
 * </p>
 * User:Gongle
 * Date: 2013-10-01 18:51:48
 */
public class BundleScript extends BodyTagSupport {
    //private static Logger logger = LoggerFactory.getLogger(BundleScript.class);
    public static final String DEFAULT_OUT = "<script src=\"%s\" charset=\"%s\" ></script>\n";
    private String name;
    private String charset = "utf-8";


    private List<AssetJavascript> scripts = new ArrayList<AssetJavascript>();


    @Override
    public int doAfterBody() throws JspException {
        String jsfileNames;
        String jsMinUIR = AppConfigConstant.CTX + "/static";
        InputStream in;
        BodyContent bc = getBodyContent();
        JspWriter out = bc.getEnclosingWriter();
        String scriptTag;
        if (Collections3.isNotEmpty(scripts)) {
            jsfileNames = Collections3.extractToString(scripts, "src", ";");
            scriptTag = BundleCache.get(jsfileNames);
            if (Strings.isBlank(scriptTag)) {
                //处理stylesheets tag
                try {
                    in = BundleUtils.mergeResourceAsOne(scripts);
                    jsMinUIR += BundleUtils.compressorAndWrite(in, String.format("/%s.min.js", BundleUtils.buildSha1(jsfileNames)));
                    scriptTag = String.format(DEFAULT_OUT, jsMinUIR, getCharset());
                    IOUtil.closeQuietly(in);
                    out.print(scriptTag);
                    //写入缓存供下次使用.
                    BundleCache.update(jsfileNames, scriptTag);
                    scripts.clear();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    out.print(scriptTag);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return SKIP_BODY;
    }

    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    //添加一个Script标签
    public void addScript(AssetJavascript script) {
        scripts.add(script);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public List<AssetJavascript> getScripts() {
        return scripts;
    }

    public void setScripts(List<AssetJavascript> scripts) {
        this.scripts = scripts;
    }


}
